/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

import java.util.Collections;

/**
 * @author Michael C. Han
 */
public class OpenSearchEngineAdapterFixture {

	public SearchEngineAdapter getSearchEngineAdapter() {
		return _searchEngineAdapter;
	}

	public void setUp() {
		_searchEngineAdapter = createSearchEngineAdapter(
			_openSearchConnectionManager);
	}

	protected static SearchEngineAdapter createSearchEngineAdapter(
		OpenSearchConnectionManager openSearchConnectionManager) {

		OpenSearchSearchEngineAdapterImpl openSearchSearchEngineAdapterImpl =
			new OpenSearchSearchEngineAdapterImpl() {
				{
					setThrowOriginalExceptions(true);
				}
			};

		ReflectionTestUtil.setFieldValue(
			openSearchSearchEngineAdapterImpl, "_openSearchConnectionManager",
			openSearchConnectionManager);

		openSearchSearchEngineAdapterImpl.activate(Collections.emptyMap());

		return openSearchSearchEngineAdapterImpl;
	}

	protected void setOpenSearchConnectionManager(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	private OpenSearchConnectionManager _openSearchConnectionManager;
	private SearchEngineAdapter _searchEngineAdapter;

}