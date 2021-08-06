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

package com.liferay.layout.reports.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alejandro Tardín
 */
@ExtendedObjectClassDefinition(
	category = "pages", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	description = "layout-reports-google-pagespeed-configuration-description",
	id = "com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedGroupConfiguration",
	localization = "content/Language",
	name = "layout-reports-google-pagespeed-group-configuration-name"
)
public interface LayoutReportsGooglePageSpeedGroupConfiguration {

	@Meta.AD(deflt = "true", name = "enable-google-pagespeed", required = false)
	public boolean enabled();

	@ExtendedAttributeDefinition(
		descriptionArguments = "https://developers.google.com/speed/docs/insights/v5/get-started"
	)
	@Meta.AD(
		description = "get-your-api-key-at-x", name = "api-key",
		required = false
	)
	public String apiKey();

	@Meta.AD(
		deflt = "MOBILE", description = "strategy-description",
		name = "strategy", optionLabels = {"mobile", "desktop"},
		optionValues = {"MOBILE", "DESKTOP"}, required = false
	)
	public String strategy();

}