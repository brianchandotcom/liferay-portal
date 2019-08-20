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

package com.liferay.frontend.theme.font.awesome.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Chema Balsas
 * @review
 */
@ExtendedObjectClassDefinition(category = "third-party")
@Meta.OCD(
	description = "frontend-theme-font-awesome-description",
	id = "com.liferay.frontend.theme.font.awesome.web.internal.configuration.CSSFontAwesomeConfiguration",
	localization = "content/Language",
	name = "frontend-theme-font-awesome-configuration-name"
)
public interface CSSFontAwesomeConfiguration {

	/**
	 * Set this to <code>true</code> to enable Font Awesome usage.
	 *
	 * @return <code>true</code> if Liferay Font Awesome is enabled.
	 * @review
	 */
	@Meta.AD(deflt = "true", name = "enable-font-awesome", required = false)
	public boolean enableFontAwesome();

}