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

package com.liferay.commerce.qualifier.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Riccardo Alberti
 */
@ExtendedObjectClassDefinition(
	category = "qualifiers", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.commerce.qualifier.configuration.CommerceChannelQualifierConfiguration",
	localization = "content/Language",
	name = "commerce-channel-qualifier-configuration-name"
)
public interface CommerceChannelQualifierConfiguration
	extends CommerceQualifierConfiguration {

	@Meta.AD(name = "allowed-target-key-groups", required = false)
	public String[] allowedTargetKeyGroupsArray();

	@Meta.AD(name = "order-by-target-key-groups", required = false)
	public String[] orderByTargetKeyGroupsArray();

}