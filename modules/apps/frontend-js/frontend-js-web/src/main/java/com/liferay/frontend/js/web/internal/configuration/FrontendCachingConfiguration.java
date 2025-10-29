/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Iván Zaera Avellón
 */
@ExtendedObjectClassDefinition(
	category = "infrastructure",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY, strictScope = true
)
@Meta.OCD(
	id = "com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration",
	localization = "content/Language",
	name = "frontend-caching-configuration-name"
)
public interface FrontendCachingConfiguration {

	@Meta.AD(
		deflt = "86400", description = "css-style-sheets-max-age-help",
		name = "css-style-sheets-max-age", required = false
	)
	public long cssStyleSheetsMaxAge();

	@ExtendedAttributeDefinition(
		visibilityControllerKey = "com.liferay.frontend.js.web.internal.configuration.admin.display.FrontendCachingConfigurationVisibilityController"
	)
	@Meta.AD(
		deflt = "86400", description = "es-modules-max-age-help",
		name = "es-modules-max-age", required = false
	)
	public long esModulesMaxAge();

	@Meta.AD(
		deflt = "false",
		description = "send-no-cache-for-css-style-sheets-help",
		name = "send-no-cache-for-css-style-sheets", required = false
	)
	public boolean sendNoCacheForCSSStyleSheets();

	@ExtendedAttributeDefinition(
		visibilityControllerKey = "com.liferay.frontend.js.web.internal.configuration.admin.display.FrontendCachingConfigurationVisibilityController"
	)
	@Meta.AD(
		deflt = "false", description = "send-no-cache-for-es-modules-help",
		name = "send-no-cache-for-es-modules", required = false
	)
	public boolean sendNoCacheForESModules();

}