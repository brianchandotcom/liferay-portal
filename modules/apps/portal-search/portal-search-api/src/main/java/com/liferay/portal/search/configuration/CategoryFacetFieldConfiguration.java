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