/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.user.associated.data.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Drew Brokke
 */
@ExtendedObjectClassDefinition(
	category = "users", factoryInstanceLabelAttribute = "companyId",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfiguration",
	localization = "content/Language",
	name = "anonymous-user-configuration-name"
)
public interface AnonymousUserConfiguration {

	@Meta.AD(
		deflt = "0", description = "company-id-description",
		name = "company-id", required = false
	)
	public long companyId();

	@Meta.AD(
		deflt = "0", description = "user-id-description", name = "user-id",
		required = false
	)
	public long userId();

}