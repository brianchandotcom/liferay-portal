/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.analytics.settings.rest.dto.v1_0.DataSourceToken;
import com.liferay.analytics.settings.rest.internal.client.AnalyticsCloudClient;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.DataSourceResource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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
		try {
			_analyticsCloudClient.disconnectAnalyticsDataSource(
				contextCompany.getCompanyId());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		_analyticsSettingsManager.deleteCompanyConfiguration(
			contextUser.getCompanyId());
	}

	@Override
	public void postDataSource(DataSourceToken dataSourceToken)
		throws Exception {

		Map<String, Object> properties =
			_analyticsCloudClient.connectAnalyticsDataSource(
				contextUser.getCompanyId(), dataSourceToken.getToken());

		properties.put("token", dataSourceToken.getToken());

		_analyticsSettingsManager.updateCompanyConfiguration(
			contextUser.getCompanyId(), properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataSourceResourceImpl.class);

	@Reference
	private AnalyticsCloudClient _analyticsCloudClient;

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

}