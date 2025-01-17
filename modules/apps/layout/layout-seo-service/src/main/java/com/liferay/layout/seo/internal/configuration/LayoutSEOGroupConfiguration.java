/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	description = "seo-configuration",
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