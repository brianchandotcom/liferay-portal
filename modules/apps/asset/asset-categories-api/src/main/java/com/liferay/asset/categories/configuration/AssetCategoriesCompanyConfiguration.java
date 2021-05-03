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

package com.liferay.asset.categories.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Eudaldo Alonso
 */
@ExtendedObjectClassDefinition(
	category = "assets", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.asset.categories.configuration.AssetCategoriesCompanyConfiguration",
	localization = "content/Language",
	name = "categorization-configuration-name"
)
public interface AssetCategoriesCompanyConfiguration {

	@Meta.AD(
		deflt = "https://learn.liferay.com/dxp/7.x/en/content-authoring-and-management/tags-and-categories/defining-categories-and-vocabularies-for-content.html",
		description = "link-to-documentation-url-description",
		name = "link-to-documentation-url", required = false
	)
	public String linkToDocumentationURL();

}