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
import com.liferay.analytics.batch.exportimport.constants.AnalyticsDXPEntityBatchExporterConstants;
import com.liferay.analytics.settings.rest.resource.v1_0.AnalyticsDXPEntityBatchExporterResource;

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
	public void postAnalyticsDXPEntityBatchExporter() throws Exception {
		_analyticsDXPEntityBatchExporter.export(
			contextCompany.getCompanyId(),
			new String[] {
				AnalyticsDXPEntityBatchExporterConstants.
					DISPATCH_TRIGGER_NAME_ACCOUNT_ENTRY_DXP_ENTITIES,
				AnalyticsDXPEntityBatchExporterConstants.
					DISPATCH_TRIGGER_NAME_ORDER,
				AnalyticsDXPEntityBatchExporterConstants.
					DISPATCH_TRIGGER_NAME_PRODUCT,
				AnalyticsDXPEntityBatchExporterConstants.
					DISPATCH_TRIGGER_NAME_USER_DXP_ENTITIES
			});
	}

	@Reference
	private AnalyticsDXPEntityBatchExporter _analyticsDXPEntityBatchExporter;

}