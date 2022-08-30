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
		deflt = "150",
		description = "maximum-number-of-categories-per-vocabulary-description",
		name = "maximum-number-of-categories-per-vocabulary", required = false
	)
	public int maximumNumberOfCategoriesPerVocabulary();

}