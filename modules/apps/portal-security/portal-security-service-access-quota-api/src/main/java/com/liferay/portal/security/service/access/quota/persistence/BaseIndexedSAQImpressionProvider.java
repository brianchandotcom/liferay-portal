/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.quota.persistence;

import com.liferay.portal.security.service.access.quota.metric.SAQContextMatcher;
import com.liferay.portal.security.service.access.quota.metric.SAQMetricMatcher;

/**
 * @author Stian Sigvartsen
 */
public abstract class BaseIndexedSAQImpressionProvider
	implements SAQImpressionProvider {

	@Override
	public abstract int getSAQImpressionsCount(
		long companyId, long expiryIntervalMillis);

	@Override
	public void populateSAQImpressions(
		long companyId, final SAQContextMatcher saqContextMatcher,
		SAQImpressionConsumer saqImpressionConsumer) {

		for (final String metricName : saqContextMatcher.getMetricNames()) {
			populateSAQImpressionsMatchingMetric(
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

	@Override
	public abstract void populateSAQImpressions(
		long companyId, SAQImpressionConsumer saqImpressionConsumer);

	public abstract void populateSAQImpressionsMatchingMetric(
		long companyId, String metric, SAQMetricMatcher saqMetricMatcher,
		SAQImpressionConsumer saqImpressionConsumer);

}