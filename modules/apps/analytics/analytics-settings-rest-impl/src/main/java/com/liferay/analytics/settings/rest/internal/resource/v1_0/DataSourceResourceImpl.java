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

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.analytics.settings.rest.dto.v1_0.DataSourceToken;
import com.liferay.analytics.settings.rest.internal.helper.v1_0.AnalyticsCloudClientHelper;
import com.liferay.analytics.settings.rest.internal.helper.v1_0.AnalyticsSettingsHelper;
import com.liferay.analytics.settings.rest.resource.v1_0.DataSourceResource;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Ferrari
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/data-source.properties",
	scope = ServiceScope.PROTOTYPE, service = DataSourceResource.class
)
public class DataSourceResourceImpl extends BaseDataSourceResourceImpl {

	@Override
	public void deleteDataSource() throws Exception {
		long companyId = contextCompany.getCompanyId();

		_analyticsSettingsHelper.deleteCompanyConfiguration(companyId);

		_analyticsCloudClientHelper.disconnectDataSource(companyId);
	}

	@Override
	public void postDataSource(DataSourceToken dataSource) throws Exception {
		JSONObject dataSourceJSONObject =
			_analyticsCloudClientHelper.connectDataSource(
				contextCompany.getCompanyId(), dataSource.getToken());

		Map<String, Object> connectionProperties = dataSourceJSONObject.toMap();

		connectionProperties.put("token", dataSource.getToken());

		_analyticsSettingsHelper.updateCompanyConfiguration(
			contextCompany.getCompanyId(), connectionProperties);
	}

	@Reference
	private AnalyticsCloudClientHelper _analyticsCloudClientHelper;

	@Reference
	private AnalyticsSettingsHelper _analyticsSettingsHelper;

}