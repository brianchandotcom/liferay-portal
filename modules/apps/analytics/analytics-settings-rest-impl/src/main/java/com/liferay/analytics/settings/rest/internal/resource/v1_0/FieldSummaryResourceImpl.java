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

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.dto.v1_0.FieldSummary;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.FieldSummaryResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Ferrari
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/field-summary.properties",
	scope = ServiceScope.PROTOTYPE, service = FieldSummaryResource.class
)
public class FieldSummaryResourceImpl extends BaseFieldSummaryResourceImpl {

	@Override
	public FieldSummary getField() throws Exception {
		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextCompany.getCompanyId());

		String[] syncedAccountFieldNames =
			analyticsConfiguration.syncedAccountFieldNames();
		String[] syncedCategoryFieldNames =
			analyticsConfiguration.syncedCategoryFieldNames();
		String[] syncedContactFieldNames =
			analyticsConfiguration.syncedContactFieldNames();
		String[] syncedOrderFieldNames =
			analyticsConfiguration.syncedOrderFieldNames();
		String[] syncedOrderItemFieldNames =
			analyticsConfiguration.syncedOrderItemFieldNames();
		String[] syncedProductFieldNames =
			analyticsConfiguration.syncedProductFieldNames();
		String[] syncedProductChannelFieldNames =
			analyticsConfiguration.syncedProductChannelFieldNames();
		String[] syncedUserFieldNames =
			analyticsConfiguration.syncedUserFieldNames();

		return new FieldSummary() {
			{
				account = syncedAccountFieldNames.length;
				order =
					syncedOrderFieldNames.length +
						syncedOrderItemFieldNames.length;
				people =
					syncedContactFieldNames.length +
						syncedUserFieldNames.length;
				product =
					syncedCategoryFieldNames.length +
						syncedProductFieldNames.length +
							syncedProductChannelFieldNames.length;
			}
		};
	}

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

}