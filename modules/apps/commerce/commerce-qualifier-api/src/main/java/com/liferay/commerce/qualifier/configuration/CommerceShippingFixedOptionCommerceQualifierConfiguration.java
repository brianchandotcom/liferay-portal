/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	id = "com.liferay.commerce.qualifier.configuration.CommerceShippingFixedOptionCommerceQualifierConfiguration",
	localization = "content/Language",
	name = "commerce-shipping-fixed-option-commerce-qualifier-configuration-name"
)
public interface CommerceShippingFixedOptionCommerceQualifierConfiguration
	extends CommerceQualifierConfiguration {

	@Meta.AD(
		deflt = "order-type", name = "allowed-target-keys", required = false
	)
	public String[] allowedTargetKeys();

	@Meta.AD(
		deflt = "order-type", name = "order-by-target-keys", required = false
	)
	public String[] orderByTargetKeys();

}