/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Christopher Kian
 */
@ExtendedObjectClassDefinition(
	category = "privacy", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	description = "product-analytics-configuration-description",
	id = "com.liferay.cookies.configuration.ProductAnalyticsConfiguration",
	localization = "content/Language",
	name = "product-analytics-configuration-name"
)
public interface ProductAnalyticsConfiguration {

	@Meta.AD(
		deflt = "true",
		description = "product-analytics-enable-user-preference-handling-help",
		name = "enabled", required = false
	)
	public boolean enabled();

}