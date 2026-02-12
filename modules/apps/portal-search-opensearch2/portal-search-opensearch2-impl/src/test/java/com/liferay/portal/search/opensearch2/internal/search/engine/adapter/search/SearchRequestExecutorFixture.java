/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public class SearchRequestExecutorFixture {

	public SearchRequestExecutor getSearchRequestExecutor() {
		return _searchRequestExecutor;
	}

	public void setUp() {
		_searchRequestExecutor = _createSearchRequestExecutor(
			_openSearchConnectionManager);
	}

	protected void setOpenSearchConnectionManager(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	private MultisearchSearchRequestExecutor
		_createMultisearchSearchRequestExecutor(
			OpenSearchConnectionManager openSearchConnectionManager) {

		MultisearchSearchRequestExecutor multisearchSearchRequestExecutor =
			new MultisearchSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			multisearchSearchRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return multisearchSearchRequestExecutor;
	}

	private SearchRequestExecutor _createSearchRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		OpenSearchSearchRequestExecutor openSearchSearchRequestExecutor =
			new OpenSearchSearchRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			openSearchSearchRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		ReflectionTestUtil.setFieldValue(
			openSearchSearchRequestExecutor,
			"_multisearchSearchRequestExecutor",
			_createMultisearchSearchRequestExecutor(
				openSearchConnectionManager));

		ReflectionTestUtil.setFieldValue(
			openSearchSearchRequestExecutor, "_searchSearchRequestExecutor",
			_createSearchSearchRequestExecutor(openSearchConnectionManager));

		openSearchSearchRequestExecutor.activate();

		return openSearchSearchRequestExecutor;
	}

	private SearchSearchRequestExecutor _createSearchSearchRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		SearchSearchRequestExecutor searchSearchRequestExecutor =
			new SearchSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			searchSearchRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return searchSearchRequestExecutor;
	}

	private OpenSearchConnectionManager _openSearchConnectionManager;
	private SearchRequestExecutor _searchRequestExecutor;

}