/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.service.access.quota.internal.persistence;

import com.liferay.portal.security.service.access.quota.metric.SAQMetricMatcher;
import com.liferay.portal.security.service.access.quota.persistence.BaseIndexedSAQImpressionPersistence;
import com.liferay.portal.security.service.access.quota.persistence.SAQImpression;
import com.liferay.portal.security.service.access.quota.persistence.SAQImpressionConsumer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.liferay.portal.security.service.access.quota.persistence.SAQImpressionPersistence;
import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component (service = SAQImpressionPersistence.class)
public class MemoryBasedSAQImpressionPersistence
	extends BaseIndexedSAQImpressionPersistence {

	@Override
	public void createImpression(
		long companyId, Map<String, String> metrics,
		long expiryIntervalMillis) {

		long bucketStartMillis = _getBucketStartMillis();

		List<SAQImpressionsBucket> buckets = _getBuckets(companyId);

		SAQImpressionsBucket currentBucket;

		if (buckets.isEmpty()) {
			currentBucket = null;
		}
		else {
			currentBucket = buckets.get(buckets.size() - 1);
		}

		if ((currentBucket == null) ||
			(bucketStartMillis != currentBucket.getStartMillis())) {

			currentBucket = new SAQImpressionsBucket(
				bucketStartMillis, bucketStartMillis + expiryIntervalMillis);

			buckets.add(currentBucket);
		}

		AggregateSAQImpression matchedImpression = _fetchCompleteMetricsMatch(
			metrics, currentBucket);

		if (matchedImpression != null) {
			matchedImpression.incrementWeight();
		}
		else {
			AggregateSAQImpression impression = new AggregateSAQImpression(
				String.valueOf(_nextKey), metrics, bucketStartMillis);

			currentBucket.indexImpression(impression);

			_nextKey++;
		}
	}

	@Override
	public void findImpressionsMatchingMetric(
		long companyId, String metricName, SAQMetricMatcher metricMatcher,
		SAQImpressionConsumer consumer) {

		for (SAQImpression impression :
				_findImpressions(companyId, metricName, metricMatcher)) {

			SAQImpressionConsumer.Status consume = consumer.consume(impression);

			if (consume.equals(SAQImpressionConsumer.Status.SATISFIED)) {
				return;
			}
		}
	}

	@Override
	public int getImpressionsCount(long companyId, long expiryIntervalMillis) {
		long nowMillis = System.currentTimeMillis();
		int totalWeight = 0;

		List<SAQImpressionsBucket> buckets = _getBuckets(companyId);

		Iterator<SAQImpressionsBucket> i = buckets.iterator();

		while (i.hasNext()) {
			SAQImpressionsBucket bucket = i.next();

			if (bucket.getExpiryMillis() < nowMillis) {
				i.remove();
			}
			else if ((bucket.getStartMillis() + expiryIntervalMillis) >
						nowMillis) {

				for (AggregateSAQImpression impression :
						bucket.getAllImpressions()) {

					totalWeight += impression.getWeight();
				}
			}
		}

		return totalWeight;
	}

	@Override
	public void findAllImpressions(
		long companyId, SAQImpressionConsumer consumer) {

		for (SAQImpression impression :
				_findImpressions(companyId, null, null)) {

			if (consumer.consume(
					impression).equals(
						SAQImpressionConsumer.Status.SATISFIED)) {

				return;
			}
		}
	}

	private AggregateSAQImpression _fetchCompleteMetricsMatch(
		Map<String, String> metrics, SAQImpressionsBucket currentBucket) {

		Set<AggregateSAQImpression> intersectSet = null;

		for (Map.Entry<String, String> entry : metrics.entrySet()) {
			Set<AggregateSAQImpression> metricImpressions =
				currentBucket.getImpressions(entry.getKey(), entry.getValue());

			if (metricImpressions.size() == 0) {
				return null;
			}
			else if (intersectSet == null) {
				intersectSet = metricImpressions;
			}
			else {
				intersectSet.retainAll(metricImpressions);
			}
		}

		if ((intersectSet != null) && !intersectSet.isEmpty()) {
			return intersectSet.iterator().next();
		}
		else {
			return null;
		}
	}

	@Override
	public Iterator<String> findAllMetricValues(
		long companyId, String metricName) {

		Set<String> metricValues = new HashSet<>();

		for (SAQImpressionsBucket bucket : _getBuckets(companyId)) {
			metricValues.addAll(bucket.getAllMetricValues(metricName));
		}

		return metricValues.iterator();
	}

	private Set<SAQImpression> _findImpressions(
		long companyId, String metricName, SAQMetricMatcher metricMatcher) {

		long nowMillis = System.currentTimeMillis();

		List<SAQImpressionsBucket> buckets = _getBuckets(companyId);

		Set<SAQImpression> union = new HashSet<>();

		Iterator<SAQImpressionsBucket> i = buckets.iterator();

		while (i.hasNext()) {
			SAQImpressionsBucket bucket = i.next();

			if (bucket.getExpiryMillis() < nowMillis) {
				i.remove();
			}
			else {
				if (metricMatcher == null) {
					union.addAll(bucket.getImpressions(metricName, null));
				}
				else {
					for (String value : bucket.getAllMetricValues(metricName)) {
						if (metricMatcher.matches(value)) {
							union.addAll(
								bucket.getImpressions(metricName, value));
						}
					}
				}
			}
		}

		return union;
	}

	private List<SAQImpressionsBucket> _getBuckets(long companyId) {
		Long companyIdLong = Long.valueOf(companyId);

		List<SAQImpressionsBucket> buckets = _buckets.get(companyIdLong);

		if (buckets == null) {
			LinkedList<SAQImpressionsBucket> newBuckets = new LinkedList<>();

			buckets = _buckets.putIfAbsent(companyIdLong, newBuckets);

			if (buckets == null) {
				buckets = newBuckets;
			}
		}

		return _buckets.get(companyIdLong);
	}

	private long _getBucketStartMillis() {

		// Round up current time millis to bucket boundary
		// So that the quota intervalMillis is honored (as minimum)

		long bucketStart = System.currentTimeMillis();

		return
			bucketStart - (bucketStart % _BUCKET_INTERVAL) + _BUCKET_INTERVAL;
	}

	private static final long _BUCKET_INTERVAL = 1000;

	private final ConcurrentMap<Long, List<SAQImpressionsBucket>> _buckets =
		new ConcurrentHashMap<>();
	private long _nextKey;

}