/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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