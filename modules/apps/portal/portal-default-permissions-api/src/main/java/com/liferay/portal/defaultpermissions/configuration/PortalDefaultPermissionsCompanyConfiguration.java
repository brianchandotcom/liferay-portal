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
	generateUI = false, scope = ExtendedObjectClassDefinition.Scope.COMPANY,
	strictScope = true
)
@Meta.OCD(
	id = "com.liferay.portal.defaultpermissions.configuration.PortalDefaultPermissionsCompanyConfiguration",
	localization = "content/Language",
	name = "default-permissions-company-configuration-name"
)
public interface PortalDefaultPermissionsCompanyConfiguration {

	@Meta.AD(name = "default-permissions", required = false)
	public String defaultPermissions();

}