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
 * @author Alec Sloan
 */
@ExtendedObjectClassDefinition(
	category = "channel", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.product.configuration.CPDisplayLayoutConfiguration",
	localization = "content/Language",
	name = "cp-display-layout-configuration-name"
)
public interface CPDisplayLayoutConfiguration {

	@Meta.AD(
		description = "asset-category-layout-uuid-help",
		name = "asset-category-layout-uuid", required = false
	)
	public String assetCategoryLayoutUuid();

	@Meta.AD(
		description = "product-layout-uuid-help", name = "product-layout-uuid",
		required = false
	)
	public String productLayoutUuid();

}