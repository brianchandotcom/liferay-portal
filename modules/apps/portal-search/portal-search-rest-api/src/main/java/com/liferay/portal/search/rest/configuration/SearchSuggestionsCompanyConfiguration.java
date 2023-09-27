/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.rest.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Petteri Karttunen
 */
@ExtendedObjectClassDefinition(
	category = "search", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.portal.search.rest.configuration.SearchSuggestionsCompanyConfiguration",
	localization = "content/Language",
	name = "search-suggestions-company-configuration-name"
)
public interface SearchSuggestionsCompanyConfiguration {

	@Meta.AD(
		deflt = "true", description = "enable-suggestions-endpoint-help",
		name = "enable-suggestions-endpoint", required = false
	)
	public boolean enableSuggestionsEndpoint();

}