/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.analytics.batch.exportimport.AnalyticsDXPEntityBatchExporter;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.AnalyticsDXPEntityBatchExporterResource;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Ferrari
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/analytics-dxp-entity-batch-exporter.properties",
	scope = ServiceScope.PROTOTYPE,
	service = AnalyticsDXPEntityBatchExporterResource.class
)
public class AnalyticsDXPEntityBatchExporterResourceImpl
	extends BaseAnalyticsDXPEntityBatchExporterResourceImpl {

	@Override
	public void postConfigurationWizardMode() throws Exception {
		_analyticsSettingsManager.updateCompanyConfiguration(
			contextCompany.getCompanyId(),
			Collections.singletonMap("wizardMode", false));
	}

	@Reference
	private AnalyticsDXPEntityBatchExporter _analyticsDXPEntityBatchExporter;

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

}