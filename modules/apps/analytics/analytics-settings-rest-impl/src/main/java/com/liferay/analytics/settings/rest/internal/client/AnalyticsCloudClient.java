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

package com.liferay.analytics.settings.rest.internal.client;

import com.liferay.analytics.settings.rest.internal.client.model.DataSource;

import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public interface AnalyticsCloudClient {

	public Map<String, Object> connectDataSource(
			long companyId, String connectionToken)
		throws Exception;

	public DataSource disconnectDataSource(long companyId) throws Exception;

}