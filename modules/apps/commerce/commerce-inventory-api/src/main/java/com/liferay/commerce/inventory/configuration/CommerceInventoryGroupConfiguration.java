/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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