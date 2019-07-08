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

package com.liferay.asset.categories.admin.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.asset.categories.admin.web.internal.constants.AssetCategoriesAdminDisplayStyleKeys;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Pavel Savinov
 */
@ExtendedObjectClassDefinition(category = "assets")
@Meta.OCD(
	id = "com.liferay.asset.categories.admin.web.internal.configuration.AssetCategoriesAdminWebConfiguration",
	localization = "content/Language",
	name = "asset-categories-admin-web-configuration-name"
)
public interface AssetCategoriesAdminWebConfiguration {

	@Meta.AD(
		deflt = AssetCategoriesAdminDisplayStyleKeys.DEFAULT,
		name = "category-navigation-display-style",
		optionLabels = {"default", "flattened-tree"},
		optionValues = {
			AssetCategoriesAdminDisplayStyleKeys.DEFAULT,
			AssetCategoriesAdminDisplayStyleKeys.FLATTENED_TREE
		},
		required = false
	)
	public String categoryNavigationDisplayStyle();

}