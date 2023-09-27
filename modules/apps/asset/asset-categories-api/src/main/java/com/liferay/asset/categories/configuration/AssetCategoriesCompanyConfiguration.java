/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

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
@ProviderType
public interface AssetCategoriesCompanyConfiguration {

	@Meta.AD(
		deflt = "true",
		description = "include-children-categories-when-searching-parent-categories-description",
		name = "include-children-categories-when-searching-parent-categories",
		required = false
	)
	public boolean includeChildrenCategoriesWhenSearchingParentCategories();

	@Meta.AD(
		deflt = "150",
		description = "maximum-number-of-categories-per-vocabulary-description",
		name = "maximum-number-of-categories-per-vocabulary", required = false
	)
	public int maximumNumberOfCategoriesPerVocabulary();

}