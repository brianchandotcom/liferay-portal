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

package com.liferay.analytics.settings.rest.manager;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Riccardo Ferrari
 */
@ProviderType
public interface AnalyticsSettingsManager {

	public void deleteCompanyConfiguration(long companyId)
		throws ConfigurationException;

	public AnalyticsConfiguration getAnalyticsConfiguration(long companyId)
		throws ConfigurationException;

	public Long[] getCommerceChannelIds(
			String analyticsChannelId, long companyId)
		throws Exception;

	public Long[] getSiteIds(String analyticsChannelId, long companyId)
		throws Exception;

	public boolean isAnalyticsEnabled(long companyId) throws Exception;

	public String[] updateCommerceChannelIds(
			String analyticsChannelId, long companyId,
			Long[] dataSourceCommerceChannelIds)
		throws Exception;

	public void updateCompanyConfiguration(
			long companyId, Map<String, Object> properties)
		throws Exception;

	public String[] updateSiteIds(
			String analyticsChannelId, long companyId, Long[] dataSourceSiteIds)
		throws Exception;

}