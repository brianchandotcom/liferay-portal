/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Lourdes Fernández Besada
 */
@ExtendedObjectClassDefinition(
	category = "seo", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.site.internal.configuration.SitemapCompanyConfiguration",
	localization = "content/Language",
	name = "sitemap-company-configuration-name"
)
public interface SitemapCompanyConfiguration {

	@Meta.AD(
		deflt = "true", name = "include-categories-in-the-sitemap-xml",
		required = false
	)
	public boolean includeCategories();

	@Meta.AD(
		deflt = "true", name = "include-pages-in-the-sitemap-xml",
		required = false
	)
	public boolean includePages();

	@Meta.AD(
		deflt = "true", name = "include-web-content-in-the-sitemap-xml",
		required = false
	)
	public boolean includeWebContent();

}