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
 * @author Wade Cao
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.TitleFieldQueryBuilderConfiguration",
	localization = "content/Language",
	name = "title-field-query-builder-configuration-name"
)
@ProviderType
public interface TitleFieldQueryBuilderConfiguration {

	@Meta.AD(
		deflt = "2.0", description = "exact-match-boost-help",
		name = "exact-match-boost", required = false
	)
	public float exactMatchBoost();

	@Meta.AD(
		deflt = "50", description = "max-expansions-help",
		name = "max-expansions", required = false
	)
	public int maxExpansions();

}