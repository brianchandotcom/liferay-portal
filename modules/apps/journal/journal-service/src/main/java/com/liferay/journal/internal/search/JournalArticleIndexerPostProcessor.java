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

package com.liferay.journal.internal.search;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jesse Yeh
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.journal.model.JournalArticle",
	service = IndexerPostProcessor.class
)
public class JournalArticleIndexerPostProcessor
	implements IndexerPostProcessor {

	@Override
	public void postProcessContextBooleanFilter(
		BooleanFilter booleanFilter, SearchContext searchContext) {
	}

	@Override
	public void postProcessDocument(Document document, Object object) {
	}

	@Override
	public void postProcessFullQuery(
		BooleanQuery fullQuery, SearchContext searchContext) {
	}

	@Override
	public void postProcessSearchQuery(
		BooleanQuery searchQuery, BooleanFilter booleanFilter,
		SearchContext searchContext) {
	}

	@Override
	public void postProcessSummary(
		Summary summary, Document document, Locale locale, String snippet) {
	}

}