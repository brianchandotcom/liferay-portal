/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.search.results.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Kevin Tan
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	id = "com.liferay.portal.search.web.internal.search.results.configuration.SearchResultsWebTemplateConfiguration"
)
public interface SearchResultsWebTemplateConfiguration {

	@Meta.AD(
		deflt = "search-results-list-ftl",
		name = "search-results-template-key-default", required = false
	)
	public String searchResultsTemplateKeyDefault();

}