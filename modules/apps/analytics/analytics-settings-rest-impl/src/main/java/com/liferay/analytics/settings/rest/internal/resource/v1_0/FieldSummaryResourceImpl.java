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
import com.liferay.analytics.settings.rest.constants.FieldAccountConstants;
import com.liferay.analytics.settings.rest.constants.FieldOrderConstants;
import com.liferay.analytics.settings.rest.constants.FieldPeopleConstants;
import com.liferay.analytics.settings.rest.constants.FieldProductConstants;
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

		String[] syncedAccountFieldNames = _getOrDefault(
			FieldAccountConstants.FIELD_ACCOUNT_DEFAULTS,
			analyticsConfiguration.syncedAccountFieldNames());
		String[] syncedCategoryFieldNames = _getOrDefault(
			FieldProductConstants.FIELD_CATEGORY_NAMES,
			analyticsConfiguration.syncedCategoryFieldNames());
		String[] syncedContactFieldNames = _getOrDefault(
			FieldPeopleConstants.FIELD_CONTACT_DEFAULTS,
			analyticsConfiguration.syncedContactFieldNames());
		String[] syncedOrderFieldNames = _getOrDefault(
			FieldOrderConstants.FIELD_ORDER_NAMES,
			analyticsConfiguration.syncedOrderFieldNames());
		String[] syncedOrderItemFieldNames = _getOrDefault(
			FieldOrderConstants.FIELD_ORDER_ITEM_NAMES,
			analyticsConfiguration.syncedOrderItemFieldNames());
		String[] syncedProductFieldNames = _getOrDefault(
			FieldProductConstants.FIELD_PRODUCT_CHANNEL_NAMES,
			analyticsConfiguration.syncedProductFieldNames());
		String[] syncedProductChannelFieldNames = _getOrDefault(
			FieldProductConstants.FIELD_PRODUCT_NAMES,
			analyticsConfiguration.syncedProductChannelFieldNames());
		String[] syncedUserFieldNames = _getOrDefault(
			FieldPeopleConstants.FIELD_USER_DEFAULTS,
			analyticsConfiguration.syncedUserFieldNames());

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

	private String[] _getOrDefault(
		String[] defaultFieldNames, String[] fieldNames) {

		if ((fieldNames != null) && (fieldNames.length > 0)) {
			return fieldNames;
		}

		return defaultFieldNames;
	}

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

}