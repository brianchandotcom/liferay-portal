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

package com.liferay.analytics.settings.configuration;

import java.util.Dictionary;

/**
 * @author Rachael Koestartyo
 */
public interface AnalyticsConfigurationTracker {

	public AnalyticsConfiguration getAnalyticsConfiguration(long companyId);

	public AnalyticsConfiguration getAnalyticsConfiguration(String pid);

	public Dictionary<String, Object> getAnalyticsConfigurationProperties(
		long companyId);

	public long getCompanyId(String pid);

}