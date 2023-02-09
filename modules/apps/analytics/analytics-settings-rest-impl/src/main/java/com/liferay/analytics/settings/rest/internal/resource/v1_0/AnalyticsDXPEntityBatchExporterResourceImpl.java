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