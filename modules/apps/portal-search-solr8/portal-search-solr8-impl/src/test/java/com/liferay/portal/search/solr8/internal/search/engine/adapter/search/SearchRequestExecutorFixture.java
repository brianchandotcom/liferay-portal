/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;

/**
 * @author Bryan Engler
 */
public class SearchRequestExecutorFixture {

	public SearchRequestExecutor getSearchRequestExecutor() {
		return _searchRequestExecutor;
	}

	public void setUp() {
		_searchRequestExecutor = createSearchRequestExecutor(
			_solrClientManager);
	}

	protected SearchRequestExecutor createSearchRequestExecutor(
		SolrClientManager solrClientManager) {

		SolrSearchRequestExecutor solrSearchRequestExecutor =
			new SolrSearchRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			solrSearchRequestExecutor, "_solrClientManager", solrClientManager);

		ReflectionTestUtil.setFieldValue(
			solrSearchRequestExecutor, "_multisearchSearchRequestExecutor",
			new MultisearchSearchRequestExecutor());

		solrSearchRequestExecutor.activate();

		return solrSearchRequestExecutor;
	}

	protected void setSolrClientManager(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	private SearchRequestExecutor _searchRequestExecutor;
	private SolrClientManager _solrClientManager;

}