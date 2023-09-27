/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.CategoryFacetFieldConfiguration",
	localization = "content/Language",
	name = "category-facet-field-configuration-name"
)
@ProviderType
public interface CategoryFacetFieldConfiguration {

	@Meta.AD(
		deflt = "assetCategoryIds", description = "category-facet-field-help",
		name = "category-facet-field",
		optionLabels = {"assetCategoryIds", "assetVocabularyCategoryIds"},
		optionValues = {"assetCategoryIds", "assetVocabularyCategoryIds"},
		required = false
	)
	public String categoryFacetField();

}