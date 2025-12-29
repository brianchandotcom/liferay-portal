/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Iván Zaera Avellón
 */
@ExtendedObjectClassDefinition(
	category = "infrastructure",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY, strictScope = true
)
@Meta.OCD(
	id = "com.liferay.portal.url.builder.configuration.PortalURLBuilderConfiguration",
	localization = "content/Language",
	name = "portal-url-builder-configuration-name"
)
public interface PortalURLBuilderConfiguration {

	@Meta.AD(
		deflt = "true", description = "enable-es-modules-hashing-help",
		name = "enable-es-modules-hashing", required = false
	)
	public boolean enableESModulesHashing();

}