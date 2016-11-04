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

package com.liferay.portal.security.service.access.quota.persistence;

import com.liferay.portal.security.service.access.quota.metric.SAQContextMatcher;
import com.liferay.portal.security.service.access.quota.metric.SAQMetricMatcher;

import java.util.Iterator;

/**
 * @author Stian Sigvartsen
 */
public abstract class BaseIndexedSAQImpressionProvider
	implements SAQImpressionProvider {

	public abstract Iterator<String> findMetricValues(
		long companyId, String metricName);

	@Override
	public void findSAQImpressions(
		long companyId, final SAQContextMatcher saqContextMatcher,
		SAQImpressionConsumer saqImpressionConsumer) {

		for (final String metricName : saqContextMatcher.getMetricNames()) {
			findSAQImpressionsMatchingMetric(
				companyId, metricName,
				new SAQMetricMatcher() {

					@Override
					public boolean matches(String metricValue) {
						return saqContextMatcher.matches(
							metricName, metricValue);
					}

				},
				saqImpressionConsumer);
		}
	}

	public abstract void findSAQImpressions(
		long companyId, SAQImpressionConsumer saqImpressionConsumer);

	public abstract void findSAQImpressionsMatchingMetric(
		long companyId, String metric, SAQMetricMatcher saqMetricMatcher,
		SAQImpressionConsumer saqImpressionConsumer);

	public abstract int getSAQImpressionsCount(
		long companyId, long expiryIntervalMillis);

}