/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.legacy.searcher;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.searcher.SearchResponseBuilder;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Creates a search response builder for building a search response from a
 * legacy search context. This is for backward compatibility only; new code
 * should use {@link
 * com.liferay.portal.search.searcher.SearchResponseBuilderFactory}.
 *
 * @author André de Oliveira
 */
@ProviderType
public interface SearchResponseBuilderFactory {

	/**
	 * Creates a search response builder from a legacy search context.
	 *
	 * @param  searchContext the search context
	 * @return the search response builder
	 */
	public SearchResponseBuilder builder(SearchContext searchContext);

}