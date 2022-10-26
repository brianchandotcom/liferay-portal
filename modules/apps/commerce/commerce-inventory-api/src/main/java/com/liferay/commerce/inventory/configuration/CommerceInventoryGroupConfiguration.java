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

package com.liferay.commerce.inventory.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "inventory", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.inventory.configuration.CommerceInventoryGroupConfiguration",
	localization = "content/Language",
	name = "commerce-inventory-group-configuration-name"
)
public interface CommerceInventoryGroupConfiguration {

	@Meta.AD(
		deflt = "" + CommerceInventoryConstants.DEFAULT_METHOD_KEY,
		name = "inventory-method-key", required = false
	)
	public String inventoryMethodKey();

}