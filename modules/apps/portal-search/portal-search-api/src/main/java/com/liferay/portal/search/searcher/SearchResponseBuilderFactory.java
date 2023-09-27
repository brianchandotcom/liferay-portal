/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.searcher;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Creates a search response builder for building a search response object from
 * the search engine's response to an executed search request. This interface's
 * usage is intended for the Liferay Search Framework only.
 *
 * @author André de Oliveira
 */
@ProviderType
public interface SearchResponseBuilderFactory {

	/**
	 * Instantiates a new search response builder.
	 *
	 * @return the search response builder
	 */
	public SearchResponseBuilder builder();

}