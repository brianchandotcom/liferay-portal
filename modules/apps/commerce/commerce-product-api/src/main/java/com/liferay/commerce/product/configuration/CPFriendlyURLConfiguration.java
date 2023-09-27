/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Ivica Cardic
 */
@ExtendedObjectClassDefinition(
	category = "catalog", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.commerce.product.configuration.CPFriendlyURLConfiguration",
	localization = "content/Language",
	name = "cp-friendly-url-configuration-name"
)
public interface CPFriendlyURLConfiguration {

	@Meta.AD(
		deflt = "g", description = "asset-category-url-separator-help",
		name = "asset-category-url-separator", required = false
	)
	public String assetCategoryURLSeparator();

	@Meta.AD(
		deflt = "p", description = "product-url-separator-help",
		name = "product-url-separator", required = false
	)
	public String productURLSeparator();

}