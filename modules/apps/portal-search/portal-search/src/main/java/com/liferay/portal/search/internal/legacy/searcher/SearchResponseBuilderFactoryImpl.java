/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.legacy.searcher;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponseBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = SearchResponseBuilderFactory.class)
public class SearchResponseBuilderFactoryImpl
	implements SearchResponseBuilderFactory {

	@Override
	public SearchResponseBuilder builder(SearchContext searchContext) {
		return new SearchResponseBuilderImpl(searchContext);
	}

}