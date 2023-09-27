/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.login.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Erick Monteiro
 */
@ExtendedObjectClassDefinition(
	category = "login", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.login.web.internal.configuration.AuthLoginConfiguration",
	localization = "content/Language", name = "login-configuration-name"
)
public interface AuthLoginConfiguration {

	@Meta.AD(
		deflt = "false", description = "prompt-enabled-description",
		name = "prompt-enabled-name", required = false
	)
	public boolean promptEnabled();

}