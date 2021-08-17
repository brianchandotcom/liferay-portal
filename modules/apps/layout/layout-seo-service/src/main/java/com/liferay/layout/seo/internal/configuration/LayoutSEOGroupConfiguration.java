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
 * @author Alicia García
 */
@ExtendedObjectClassDefinition(
	category = "pages", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	description = "layout-seo-group-configuration-description",
	id = "com.liferay.layout.seo.internal.configuration.LayoutSEOGroupConfiguration",
	localization = "content/Language",
	name = "layout-seo-group-configuration-name"
)
public interface LayoutSEOGroupConfiguration {

	/**
	 * Sets the configuration to allow the site admins to configure if hreflang
	 * tags in pages are filled only for the translated languages.
	 *
	 * @review
	 */
	@Meta.AD(
		deflt = "false",
		description = "layout-seo-configuration-enable-only-translated-hreflang-description",
		name = "layout-seo-configuration-enable-only-translated-hreflang",
		required = false
	)
	public boolean enableLayoutTranslatedLanguages();

}