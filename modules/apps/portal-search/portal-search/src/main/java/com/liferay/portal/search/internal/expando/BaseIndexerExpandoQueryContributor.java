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

package com.liferay.portal.search.internal.expando;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ExpandoQueryContributor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.internal.indexer.KeywordQueryContributorsHolder;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = ExpandoQueryContributor.class)
public class BaseIndexerExpandoQueryContributor
	implements ExpandoQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery, String[] classNames,
		SearchContext searchContext) {

		Stream<KeywordQueryContributor> stream =
			keywordQueryContributorsHolder.getAll();

		stream.forEach(
			keywordQueryContributor -> keywordQueryContributor.contribute(
				searchContext.getKeywords(), booleanQuery,
				new KeywordQueryContributorHelper() {

					@Override
					public String getClassName() {
						return null;
					}

					@Override
					public Stream<String> getSearchClassNamesStream() {
						return Stream.of(classNames);
					}

					@Override
					public SearchContext getSearchContext() {
						return searchContext;
					}

				}));
	}

	@Reference
	protected KeywordQueryContributorsHolder keywordQueryContributorsHolder;

}