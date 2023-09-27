/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.tuning.rankings.web.internal.BaseRankingsWebTestCase;

import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public abstract class BaseRankingsIndexTestCase
	extends BaseRankingsWebTestCase {

	protected Document setUpDocument(List<String> queryStrings) {
		Document document = Mockito.mock(Document.class);

		Mockito.doReturn(
			queryStrings
		).when(
			document
		).getStrings(
			Mockito.anyString()
		);

		return document;
	}

	protected void setUpQueries() {
		Mockito.doReturn(
			Mockito.mock(BooleanQuery.class)
		).when(
			queries
		).booleanQuery();
		Mockito.doReturn(
			Mockito.mock(TermsQuery.class)
		).when(
			queries
		).terms(
			Mockito.anyString()
		);
	}

	protected void setUpSearchEngineAdapter(boolean exist) {
		IndicesExistsIndexResponse indicesExistsIndexResponse = Mockito.mock(
			IndicesExistsIndexResponse.class);

		Mockito.doReturn(
			exist
		).when(
			indicesExistsIndexResponse
		).isExists();

		Mockito.doReturn(
			indicesExistsIndexResponse
		).when(
			searchEngineAdapter
		).execute(
			(IndicesExistsIndexRequest)Mockito.any()
		);
	}

	protected SearchHits setUpSearchHits(List<String> queryStrings) {
		Document document = setUpDocument(queryStrings);

		SearchHit searchHit = Mockito.mock(SearchHit.class);

		Mockito.doReturn(
			document
		).when(
			searchHit
		).getDocument();

		SearchHits searchHits = Mockito.mock(SearchHits.class);

		Mockito.doReturn(
			Arrays.asList(searchHit)
		).when(
			searchHits
		).getSearchHits();

		return searchHits;
	}

}