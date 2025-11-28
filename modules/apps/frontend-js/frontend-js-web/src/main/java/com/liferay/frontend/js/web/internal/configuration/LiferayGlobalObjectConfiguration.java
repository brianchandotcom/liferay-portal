/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.configuration;

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
	id = "com.liferay.frontend.js.web.internal.configuration.LiferayGlobalObjectConfiguration",
	localization = "content/Language",
	name = "liferay-global-object-configuration-name"
)
public interface LiferayGlobalObjectConfiguration {

	@Meta.AD(
		deflt = "false", description = "disable-get-remote-methods-help",
		name = "disable-get-remote-methods", required = false
	)
	public boolean disableGetRemoteMethods();

}