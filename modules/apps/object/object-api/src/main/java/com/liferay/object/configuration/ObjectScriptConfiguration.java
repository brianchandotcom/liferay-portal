/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Pedro Leite
 */
@ExtendedObjectClassDefinition(
	category = "objects", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.object.configuration.ObjectScriptConfiguration",
	localization = "content/Language", name = "object-script-configuration-name"
)
public interface ObjectScriptConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "allow-administrators-execute-script-help",
		name = "allow-administrators-execute-script", required = false
	)
	public boolean allowInstanceAdminExecuteCode();

}