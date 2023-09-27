/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alec Sloan
 */
@ExtendedObjectClassDefinition(
	category = "payment", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.tax.engine.fixed.configuration.CommerceTaxByAddressTypeConfiguration",
	localization = "content/Language",
	name = "commerce-tax-by-address-type-configuration-name"
)
public interface CommerceTaxByAddressTypeConfiguration {

	@Meta.AD(
		deflt = "false", name = "tax-applied-to-shipping-address",
		required = false
	)
	public boolean taxAppliedToShippingAddress();

}