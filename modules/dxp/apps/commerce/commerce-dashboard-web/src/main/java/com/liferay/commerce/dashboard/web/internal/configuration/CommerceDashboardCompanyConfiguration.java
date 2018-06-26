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

package com.liferay.commerce.dashboard.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.commerce.dashboard.web.internal.configuration.category.DashboardConfigurationCategory;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Andrea Di Giorgi
 */
@ExtendedObjectClassDefinition(
	category = DashboardConfigurationCategory.CATEGORY_KEY,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.commerce.dashboard.web.internal.configuration.CommerceDashboardCompanyConfiguration",
	localization = "content/Language",
	name = "commerce-dashboard-company-configuration-name"
)
public interface CommerceDashboardCompanyConfiguration {

	@Meta.AD(
		deflt = "#4B9BFF,#FFB46E,#FF5F5F,#50D2A0,#FF73C3,#9CE269,#AF78FF,#FFD76E,#5FC8FF",
		name = "chart-colors", required = false
	)
	public String[] chartColors();

}