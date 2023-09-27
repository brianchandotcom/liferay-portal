/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Drew Brokke
 */
@ExtendedObjectClassDefinition(
	category = "users", scope = ExtendedObjectClassDefinition.Scope.COMPANY,
	strictScope = true
)
@Meta.OCD(
	id = "com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfiguration",
	localization = "content/Language",
	name = "anonymous-user-configuration-name"
)
public interface AnonymousUserConfiguration {

	@Meta.AD(
		deflt = "0", description = "user-id-description", name = "user-id",
		required = false
	)
	public long userId();

}