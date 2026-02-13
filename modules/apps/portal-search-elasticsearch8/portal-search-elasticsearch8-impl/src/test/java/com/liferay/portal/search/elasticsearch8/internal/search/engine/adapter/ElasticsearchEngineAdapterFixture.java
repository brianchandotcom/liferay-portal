/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;

import java.util.Collections;

/**
 * @author Michael C. Han
 */
public class ElasticsearchEngineAdapterFixture {

	public SearchEngineAdapter getSearchEngineAdapter() {
		return _searchEngineAdapter;
	}

	public void setUp() {
		_searchEngineAdapter = createSearchEngineAdapter(
			_elasticsearchClientResolver);
	}

	protected static SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		ElasticsearchSearchEngineAdapterImpl
			elasticsearchSearchEngineAdapterImpl =
				new ElasticsearchSearchEngineAdapterImpl() {
					{
						setThrowOriginalExceptions(true);
					}
				};

		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchEngineAdapterImpl,
			"_elasticsearchClientResolver", elasticsearchClientResolver);

		elasticsearchSearchEngineAdapterImpl.activate(Collections.emptyMap());

		return elasticsearchSearchEngineAdapterImpl;
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private SearchEngineAdapter _searchEngineAdapter;

}