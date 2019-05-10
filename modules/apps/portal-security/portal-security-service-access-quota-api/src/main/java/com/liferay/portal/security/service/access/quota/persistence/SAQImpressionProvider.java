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