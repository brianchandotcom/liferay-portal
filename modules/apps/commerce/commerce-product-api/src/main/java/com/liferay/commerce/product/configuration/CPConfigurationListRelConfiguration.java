/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.commerce.product.constants.CPConfigurationListConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Danny Situ
 */
@ExtendedObjectClassDefinition(
	category = "catalog", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.commerce.product.configuration.CPConfigurationListRelConfiguration",
	localization = "content/Language",
	name = "cp-configuration-list-rel-configuration-name"
)
public interface CPConfigurationListRelConfiguration {

	@Meta.AD(
		deflt = CPConfigurationListConstants.ORDER_BY_HIERARCHY,
		name = "product-configuration-discovery-method", required = false
	)
	public String cpConfigurationListDiscovery();

}