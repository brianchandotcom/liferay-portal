/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.quota.persistence;

import com.liferay.portal.security.service.access.quota.metric.SAQContextMatcher;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Stian Sigvartsen
 */
@ProviderType
public interface SAQImpressionProvider {

	public void createSAQImpression(
		long companyId, Map<String, String> metrics, long expiryIntervalMillis);

	public int getSAQImpressionsCount(
		long companyId, long expiryIntervalMillis);

	public void populateSAQImpressions(
		long companyId, SAQContextMatcher saqContextMatcher,
		SAQImpressionConsumer saqImpressionConsumer);

	public void populateSAQImpressions(
		long companyId, SAQImpressionConsumer saqImpressionConsumer);

}