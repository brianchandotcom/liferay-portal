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
public abstract class BaseIndexedSAQImpressionPersistence
	implements SAQImpressionPersistence {

	public abstract void findImpressionsMatchingMetric(
		long companyId, String metric, SAQMetricMatcher metricMatcher,
		SAQImpressionConsumer consumer);

	public abstract void findAllImpressions(
		long companyId, SAQImpressionConsumer consumer);

	public abstract int getImpressionsCount(
		long companyId, long expiryIntervalMillis);

	public abstract Iterator<String> findAllMetricValues(
		long companyId, String metricName);

	@Override
	public void findImpressions(
		long companyId, final SAQContextMatcher contextMetricsMatcher,
		SAQImpressionConsumer consumer) {

		for (final String metricName : contextMetricsMatcher.getMetrics()) {
			findImpressionsMatchingMetric(
				companyId, metricName,
				new SAQMetricMatcher() {

					@Override
					public boolean matches(String value) {
						return contextMetricsMatcher.matches(metricName, value);
					}

				},
				consumer);
		}
	}

}