/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Stefano Motta
 */
@ExtendedObjectClassDefinition(
	category = "default-permissions", featureFlagKey = "LPD-21265",
	generateUI = false, scope = ExtendedObjectClassDefinition.Scope.GROUP,
	strictScope = true
)
@Meta.OCD(
	id = "com.liferay.portal.defaultpermissions.configuration.PortalDefaultPermissionsGroupConfiguration",
	localization = "content/Language",
	name = "default-permissions-group-configuration-name"
)
public interface PortalDefaultPermissionsGroupConfiguration {

	@Meta.AD(name = "default-permissions", required = false)
	public String defaultPermissions();

}