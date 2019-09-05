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

package com.liferay.layout.seo.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Cristina González
 */
@ExtendedObjectClassDefinition(
	category = "pages", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "layout-seo-configuration-description",
	id = "com.liferay.layout.seo.internal.configuration.LayoutSEOCompanyConfiguration",
	localization = "content/Language", name = "layout-seo-configuration-name"
)
public interface LayoutSEOCompanyConfiguration {

	/**
	 * Sets the configuration type to use with the localized URL.
	 */
	@Meta.AD(
		deflt = "default-language-url",
		description = "layout-seo-configuration-canonical-url-description",
		name = "layout-seo-configuration-canonical-url",
		optionLabels = {
			"layout-seo-configuration-page-default-language-url",
			"layout-seo-configuration-page-localized-url"
		},
		optionValues = {"default-language-url", "localized-url"},
		required = false
	)
	public String canonicalURL();

}