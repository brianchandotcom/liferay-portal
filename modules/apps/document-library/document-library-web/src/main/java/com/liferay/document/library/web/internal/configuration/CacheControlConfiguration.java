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

package com.liferay.document.library.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.document.library.web.internal.configuration.CacheControlConfiguration",
	localization = "content/Language", name = "cache-control-configuration-name"
)
public interface CacheControlConfiguration {

	@Meta.AD(
		deflt = "private", description = "cache-control-description",
		name = "cache-control", optionLabels = {"private", "public"},
		optionValues = {"private", "public"}, required = false
	)
	public String cacheControl();

	@Meta.AD(
		deflt = "0", description = "max-age-description", name = "max-age",
		required = false
	)
	public int maxAge();

	@Meta.AD(
		description = "uncacheable-mime-types-description",
		name = "uncacheable-mime-types", required = false
	)
	public String[] notCacheableMimeTypes();

}