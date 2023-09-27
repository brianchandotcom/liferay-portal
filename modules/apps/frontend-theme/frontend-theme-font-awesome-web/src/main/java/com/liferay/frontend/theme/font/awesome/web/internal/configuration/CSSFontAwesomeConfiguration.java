/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	@Meta.AD(deflt = "false", name = "enable-font-awesome", required = false)
	public boolean enableFontAwesome();

}