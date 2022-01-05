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

package com.liferay.portal.search.internal.indexer.helper;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.internal.indexer.IndexerProvidedClausesUtil;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true, service = PostProcessSearchQueryContributorHelper.class
)
public class PostProcessSearchQueryContributorHelperImpl
	implements PostProcessSearchQueryContributorHelper {

	@Override
	public void contribute(
		BooleanQuery booleanQuery, BooleanFilter booleanFilter,
		Collection<Indexer<?>> indexers, SearchContext searchContext) {

		if (IndexerProvidedClausesUtil.shouldSuppress(searchContext)) {
			return;
		}

		_addIndexerProvidedClauses(
			booleanQuery, booleanFilter, indexers, searchContext);
	}

	protected IndexerPostProcessor[] getIndexerPostProcessors(
		Indexer<?> indexer) {

		try {
			return indexer.getIndexerPostProcessors();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			return new IndexerPostProcessor[0];
		}
	}

	private void _addIndexerProvidedClauses(
		BooleanQuery booleanQuery, BooleanFilter booleanFilter,
		Collection<Indexer<?>> indexers, SearchContext searchContext) {

		for (Indexer<?> indexer : indexers) {
			_addIndexerProvidedSearchTerms(
				booleanQuery, indexer, booleanFilter, searchContext);
		}
	}

	private void _addIndexerProvidedSearchTerms(
		BooleanQuery booleanQuery, Indexer<?> indexer,
		BooleanFilter booleanFilter, SearchContext searchContext) {

		boolean luceneSyntax = GetterUtil.getBoolean(
			searchContext.getAttribute(
				SearchContextAttributes.ATTRIBUTE_KEY_LUCENE_SYNTAX));

		if (!luceneSyntax) {
			_postProcessSearchQuery(
				booleanQuery, booleanFilter, searchContext, indexer);
		}

		for (IndexerPostProcessor indexerPostProcessor :
				getIndexerPostProcessors(indexer)) {

			_postProcessSearchQuery(
				booleanQuery, booleanFilter, searchContext,
				indexerPostProcessor);
		}
	}

	private void _postProcessSearchQuery(
		BooleanQuery booleanQuery, BooleanFilter booleanFilter,
		SearchContext searchContext, Indexer<?> indexer) {

		try {
			indexer.postProcessSearchQuery(
				booleanQuery, booleanFilter, searchContext);
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void _postProcessSearchQuery(
		BooleanQuery searchQuery, BooleanFilter booleanFilter,
		SearchContext searchContext,
		IndexerPostProcessor indexerPostProcessor) {

		try {
			indexerPostProcessor.postProcessSearchQuery(
				searchQuery, booleanFilter, searchContext);
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

}