/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adam Brandizzi
 * @author André de Oliveira
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.web.internal.configuration.SearchWebConfiguration",
	localization = "content/Language", name = "search-web-configuration-name"
)
public interface SearchWebConfiguration {

	@Meta.AD(
		description = "classic-search-widget-in-front-page-help",
		name = "classic-search-widget-in-front-page", required = false
	)
	public boolean classicSearchPortletInFrontPage();

}