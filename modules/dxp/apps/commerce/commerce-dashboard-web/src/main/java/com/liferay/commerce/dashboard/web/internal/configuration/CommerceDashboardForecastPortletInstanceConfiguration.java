/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.dashboard.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Riccardo Ferrari
 */
@ExtendedObjectClassDefinition(
	category = "users",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.commerce.dashboard.web.internal.configuration.CommerceDashboardForecastPortletInstanceConfiguration",
	localization = "content/Language",
	name = "commerce-dashboard-forecast-portlet-instance-configuration-name"
)
public interface CommerceDashboardForecastPortletInstanceConfiguration {

	@Meta.AD(name = "asset-category-ids", required = false)
	public String assetCategoryIds();

}