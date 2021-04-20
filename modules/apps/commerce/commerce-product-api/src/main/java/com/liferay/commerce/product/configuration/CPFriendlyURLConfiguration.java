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