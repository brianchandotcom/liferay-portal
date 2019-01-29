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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.search2;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch6.internal.highlight.HighlightTranslator;
import com.liferay.portal.search.engine.adapter.search2.SearchSearchRequest;
import com.liferay.portal.search.query.QueryTranslator;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortFieldTranslator;

import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = SearchSearchRequestAssembler.class)
public class SearchSearchRequestAssemblerImpl
	implements SearchSearchRequestAssembler {

	@Override
	public void assemble(
		SearchRequestBuilder searchRequestBuilder,
		SearchSearchRequest searchSearchRequest) {

		_commonSearchRequestBuilderAssembler.assemble(
			searchRequestBuilder, searchSearchRequest);

		if (searchSearchRequest.getFetchSource() != null) {
			searchRequestBuilder.setFetchSource(
				searchSearchRequest.getFetchSource());
		}

		if (searchSearchRequest.getHighlight() != null) {
			HighlightBuilder highlightBuilder = _highlightTranslator.translate(
				searchSearchRequest.getHighlight(), _queryTranslator);

			searchRequestBuilder.highlighter(highlightBuilder);
		}

		addPagination(
			searchRequestBuilder, searchSearchRequest.getStart(),
			searchSearchRequest.getSize());
		addPreference(searchRequestBuilder, searchSearchRequest);
		addSelectedFields(
			searchRequestBuilder, searchSearchRequest.getSelectedFieldNames());

		if (searchSearchRequest.getVersion() != null) {
			searchRequestBuilder.setVersion(searchSearchRequest.getVersion());
		}

		List<Sort> sorts = searchSearchRequest.getSorts();

		if (ListUtil.isNotEmpty(sorts)) {
			sorts.forEach(
				sort -> {
					SortBuilder sortBuilder = _sortFieldTranslator.translate(
						sort);

					searchRequestBuilder.addSort(sortBuilder);
				});
		}

		searchRequestBuilder.setTrackScores(
			searchSearchRequest.isScoreEnabled());
	}

	protected void addPagination(
		SearchRequestBuilder searchRequestBuilder, int start, int size) {

		searchRequestBuilder.setFrom(start);
		searchRequestBuilder.setSize(size);
	}

	protected void addPreference(
		SearchRequestBuilder searchRequestBuilder,
		SearchSearchRequest searchSearchRequest) {

		String preference = searchSearchRequest.getPreference();

		if (!Validator.isBlank(preference)) {
			searchRequestBuilder.setPreference(preference);
		}
	}

	protected void addSelectedFields(
		SearchRequestBuilder searchRequestBuilder,
		String[] selectedFieldNames) {

		if (ArrayUtil.isEmpty(selectedFieldNames)) {
			searchRequestBuilder.addStoredField(StringPool.STAR);
		}
		else {
			searchRequestBuilder.storedFields(selectedFieldNames);
		}
	}

	@Reference(unbind = "-")
	protected void setCommonSearchRequestBuilderAssembler(
		CommonSearchRequestBuilderAssembler
			commonSearchRequestBuilderAssembler) {

		_commonSearchRequestBuilderAssembler =
			commonSearchRequestBuilderAssembler;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setQueryTranslator(
		QueryTranslator<QueryBuilder> queryTranslator) {

		_queryTranslator = queryTranslator;
	}

	@Reference(unbind = "-")
	protected void setSortFieldTranslator(
		SortFieldTranslator<SortBuilder> sortFieldTranslator) {

		_sortFieldTranslator = sortFieldTranslator;
	}

	private CommonSearchRequestBuilderAssembler
		_commonSearchRequestBuilderAssembler;
	private final HighlightTranslator _highlightTranslator =
		new HighlightTranslator();
	private QueryTranslator<QueryBuilder> _queryTranslator;
	private SortFieldTranslator<SortBuilder> _sortFieldTranslator;

}