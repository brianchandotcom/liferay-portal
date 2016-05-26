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

package com.liferay.portal.security.service.access.quota.impl;

import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.security.service.access.quota.SAQContext;
import com.liferay.portal.security.service.access.quota.SAQMetricProvider;
import com.liferay.portal.security.service.access.quota.ServiceAccessQuota;
import com.liferay.portal.security.service.access.quota.ServiceAccessQuotaMetricConfig;
import com.liferay.portal.security.service.access.quota.metric.SAQContextMatcher;
import com.liferay.portal.security.service.access.quota.persistence.SAQImpression;
import com.liferay.portal.security.service.access.quota.persistence.SAQImpressionConsumer;
import com.liferay.portal.security.service.access.quota.persistence.SAQImpressionPersistence;

import java.lang.reflect.Method;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Stian Sigvartsen
 */
public class SAQContextImpl implements SAQContextMatcher, SAQContext {

	public static SAQContextImpl buildContext(
		List<ServiceAccessQuota> quotas,
		Map<String, SAQMetricProvider> metricProviders,
		AccessControlContext accessControlContext, Method method) {

		List<ServiceAccessQuota> relevantQuotas = new LinkedList<>();

		Map<String, String> metrics = new HashMap<>(metricProviders.size());

		Set<String> requiredMetric = new HashSet<>();

		for (ServiceAccessQuota quota : quotas) {
			boolean metricPatternsMatched = true;

			for (ServiceAccessQuotaMetricConfig metric : quota.getMetrics()) {
				SAQMetricProvider metricProvider = metricProviders.get(
					metric.getName());

				String metricValue;

				if (metrics.containsKey(metric.getName())) {
					metricValue = metrics.get(metric.getName());
				}
				else {
					metricValue = metricProvider.getMetricValue(
						accessControlContext, method);

					metrics.put(metric.getName(), metricValue);
				}

				if (metric.getPattern() != null) {
					if (!metricProvider.matches(
							metricValue, metric.getPattern())) {

						metricPatternsMatched = false;
						break;
					}
				}
			}

			if (metricPatternsMatched) {
				relevantQuotas.add(quota);

				for (ServiceAccessQuotaMetricConfig metric :
						quota.getMetrics()) {

					requiredMetric.add(metric.getName());
				}
			}
		}

		// Remove metrics that are not relevant
		// because of failed pattern matching

		Set<String> keySet = metrics.keySet();

		keySet.retainAll(requiredMetric);

		long nowMillis = System.currentTimeMillis();

		return new SAQContextImpl(
			metrics, relevantQuotas, metricProviders, nowMillis);
	}

	@Override
	public Set<String> getMetrics() {
		return _metrics.keySet();
	}

	@Override
	public Map<String, String> getMetricsMap() {
		return _metrics;
	}

	@Override
	public List<ServiceAccessQuota> getQuotas() {
		return _relevantQuotas;
	}

	public Set<ServiceAccessQuota> matches(SAQImpression impression) {
		_matchedQuotas.clear();

		for (ServiceAccessQuota quota : _relevantQuotas) {
			if ((impression.getCreatedMillis() +
					quota.getIntervalMillis())
						< _nowMillis) {

				continue;
			}

			_impressionMetrics.clear();

			_impressionMetrics.putAll(impression.getMetrics());

			boolean allMetricsMatch = true;

			for (ServiceAccessQuotaMetricConfig quotaMetric :
					quota.getMetrics()) {

				String impressionMetricValue = _impressionMetrics.get(
					quotaMetric.getName());

				if (impressionMetricValue == null) {
					allMetricsMatch = false;

					break;
				}
				else {
					if (quotaMetric.getPattern() != null) {
						if (!_metricProviders.get(
								quotaMetric.getName()).matches(
									impressionMetricValue,
									quotaMetric.getPattern())) {

							allMetricsMatch = false;

							break;
						}
					}
					else {
						if (!_metrics.get(
								quotaMetric.getName()).equals(
									impressionMetricValue)) {

							allMetricsMatch = false;
							break;
						}
					}
				}
			}

			if (allMetricsMatch) {
				_matchedQuotas.add(quota);
			}
		}

		return _matchedQuotas;
	}

	@Override
	public boolean matches(String metricName, String value) {
		if (value == null) {
			return false;
		}

		SAQMetricProvider metricProvider = _metricProviders.get(metricName);
		List<ServiceAccessQuotaMetricConfig> metricConfigs = _metricConfigs.get(
			metricName);

		for (ServiceAccessQuotaMetricConfig metricConfig : metricConfigs) {
			if (metricConfig.getPattern() != null) {
				if (metricProvider.matches(value, metricConfig.getPattern())) {
					return true;
				}
			}
			else {
				if (_metrics.get(metricName).equals(value)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public SAQContext.ProcessingResult process(
		long companyId, SAQImpressionPersistence impressionsPersistence) {

		final ProcessingResultImpl processingResult =
			new ProcessingResultImpl();

		// Fail fast when quotas are configured with no metrics

		if (_relevantQuotasWithoutMetrics.size() > 0) {
			for (ServiceAccessQuota quota : _relevantQuotasWithoutMetrics) {
				int count = impressionsPersistence.getImpressionsCount(
					companyId, quota.getIntervalMillis());

				if (count >= quota.getMax()) {
					processingResult.setStatus(
						ProcessingResult.Status.BREACHED_QUOTA);
					processingResult.setBreachedQuota(quota);

					return processingResult;
				}
			}
		}

		impressionsPersistence.findImpressions(
			companyId, this,
			new SAQImpressionConsumer() {

				@Override
				public SAQImpressionConsumer.Status consume(
					SAQImpression impression) {

					Set<ServiceAccessQuota> matchedQuotas = matches(impression);

					for (ServiceAccessQuota quota : matchedQuotas) {
						int count = _quotasCount.get(quota);

						count = count + impression.getWeight();

						if (count < quota.getMax()) {
							_quotasCount.put(quota, count);

							continue;
						}

						// If through impression matching a quota max is hit,
						// then adding the impression for the current request
						// later will breach it, so fail fast now

						processingResult.setBreachedQuota(quota);
						processingResult.setStatus(
							ProcessingResult.Status.BREACHED_QUOTA);

						return SAQImpressionConsumer.Status.SATISFIED;
					}

					return SAQImpressionConsumer.Status.HUNGRY;
				}

			});

		return processingResult;
	}

	private SAQContextImpl(
		Map<String, String> metricsMap, List<ServiceAccessQuota> relevantQuotas,
		Map<String, SAQMetricProvider> metricProviders, long nowMillis) {

		_metrics = metricsMap;
		_relevantQuotas = relevantQuotas;
		_metricProviders = metricProviders;
		_nowMillis = nowMillis;

		_quotasCount = new HashMap<>(relevantQuotas.size());

		for (Iterator<ServiceAccessQuota> it = relevantQuotas.iterator();
			 it.hasNext();) {

			_quotasCount.put(it.next(), 0);
		}

		_metricConfigs = new HashMap<>(metricsMap.size());

		_relevantQuotasWithoutMetrics = new LinkedList<>();

		for (ServiceAccessQuota quota : relevantQuotas) {
			List<ServiceAccessQuotaMetricConfig> quotaMetricConfigs =
				quota.getMetrics();

			if (quotaMetricConfigs.size() == 0) {
				_relevantQuotasWithoutMetrics.add(quota);
			}
			else {
				for (ServiceAccessQuotaMetricConfig metric :
						quotaMetricConfigs) {

					List<ServiceAccessQuotaMetricConfig> metricConfigs =
						_metricConfigs.get(metric.getName());

					if (metricConfigs == null) {
						metricConfigs = new LinkedList<>();

						_metricConfigs.put(metric.getName(), metricConfigs);
					}

					if (!metricConfigs.contains(metric)) {
						metricConfigs.add(metric);
					}
				}
			}
		}

		_impressionMetrics = new HashMap<>(_metricProviders.size());

		_matchedQuotas = new HashSet<>(relevantQuotas.size());

		// Sort relevantQuotasWithoutMetrics by their max property
		// Will then fail fast because quotas with lower max are
		// checked and reported first

		Collections.sort(
			_relevantQuotasWithoutMetrics,
			new Comparator<ServiceAccessQuota>() {

				@Override
				public int compare(
					ServiceAccessQuota o1, ServiceAccessQuota o2) {

					return o1.getMax() - o2.getMax();
				};

			});
	}

	private final Map<String, String> _impressionMetrics;
	private final Set<ServiceAccessQuota> _matchedQuotas;
	private final Map<String, List<ServiceAccessQuotaMetricConfig>>
		_metricConfigs;
	private final Map<String, SAQMetricProvider> _metricProviders;
	private final Map<String, String> _metrics;
	private final long _nowMillis;
	private final HashMap<ServiceAccessQuota, Integer> _quotasCount;
	private final List<ServiceAccessQuota> _relevantQuotas;
	private final List<ServiceAccessQuota> _relevantQuotasWithoutMetrics;

	private class ProcessingResultImpl implements ProcessingResult {

		public ProcessingResultImpl() {
			_status = ProcessingResult.Status.NO_BREACHED_QUOTA;
			_breachedQuota = null;
		}

		@Override
		public ServiceAccessQuota getBreachedQuota() {
			return _breachedQuota;
		}

		@Override
		public Status getStatus() {
			return _status;
		}

		public void setBreachedQuota(ServiceAccessQuota breachedQuota) {
			_breachedQuota = breachedQuota;
		}

		public void setStatus(ProcessingResult.Status status) {
			_status = status;
		}

		private ServiceAccessQuota _breachedQuota;
		private ProcessingResult.Status _status;

	}

}