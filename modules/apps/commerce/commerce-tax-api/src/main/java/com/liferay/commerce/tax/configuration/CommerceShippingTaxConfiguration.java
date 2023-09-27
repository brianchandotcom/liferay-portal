/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Riccardo Alberti
 */
@ExtendedObjectClassDefinition(
	category = "shipping", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.tax.configuration.CommerceShippingTaxConfiguration",
	localization = "content/Language",
	name = "commerce-shipping-tax-configuration-name"
)
public interface CommerceShippingTaxConfiguration {

	@Meta.AD(deflt = "0", name = "tax-category-id", required = false)
	public long taxCategoryId();

}