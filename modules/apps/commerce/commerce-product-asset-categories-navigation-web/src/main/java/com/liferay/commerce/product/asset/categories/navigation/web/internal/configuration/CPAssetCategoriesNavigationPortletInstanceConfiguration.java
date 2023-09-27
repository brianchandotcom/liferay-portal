/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.asset.categories.navigation.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marco Leo
 */
@ExtendedObjectClassDefinition(
	category = "catalog",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.commerce.product.asset.categories.navigation.web.internal.configuration.CPAssetCategoriesNavigationPortletInstanceConfiguration",
	localization = "content/Language",
	name = "commerce-product-asset-categories-navigation-portlet-instance-configuration-name"
)
public interface CPAssetCategoriesNavigationPortletInstanceConfiguration {

	@Meta.AD(name = "asset-vocabulary-id", required = false)
	public String assetVocabularyId();

	@Meta.AD(name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(deflt = "0", name = "display-style-group-id", required = false)
	public long displayStyleGroupId();

	@Meta.AD(name = "root-asset-category-id", required = false)
	public String rootAssetCategoryId();

	@Meta.AD(
		deflt = "false", name = "use-category-from-request", required = false
	)
	public boolean useCategoryFromRequest();

	@Meta.AD(deflt = "false", name = "use-root-category", required = false)
	public boolean useRootCategory();

}