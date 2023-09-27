/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.web.internal.system.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Arthur Chan
 * @author Marta Medio
 */
@ExtendedObjectClassDefinition(
	category = "multi-factor-authentication",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.multi.factor.authentication.web.internal.system.configuration.MFASystemConfiguration",
	localization = "content/Language", name = "mfa-system-configuration-name"
)
public interface MFASystemConfiguration {

	@Meta.AD(
		deflt = "false", description = "disable-globally-description",
		name = "disable-globally", required = false
	)
	public boolean disableGlobally();

}