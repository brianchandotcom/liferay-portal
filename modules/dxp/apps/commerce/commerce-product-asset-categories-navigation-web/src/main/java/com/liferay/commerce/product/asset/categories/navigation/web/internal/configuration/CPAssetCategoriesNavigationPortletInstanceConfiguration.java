/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.asset.categories.navigation.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marco Leo
 */
@ExtendedObjectClassDefinition(
	category = "commerce",
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

	@Meta.AD(deflt = "", name = "display-style", required = false)
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