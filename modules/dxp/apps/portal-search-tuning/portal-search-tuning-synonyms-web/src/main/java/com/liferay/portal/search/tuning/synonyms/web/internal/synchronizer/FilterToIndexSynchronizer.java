/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer;

import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.filter.SynonymSetFilterReaderUtil;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;
import com.liferay.portal.search.tuning.synonyms.web.internal.storage.SynonymSetStorageAdapter;

import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * @author Adam Brandizzi
 */
public class FilterToIndexSynchronizer {

	public FilterToIndexSynchronizer(
		String[] filterNames, SearchEngineAdapter searchEngineAdapter,
		SynonymSetStorageAdapter synonymSetStorageAdapter) {

		_filterNames = filterNames;
		_searchEngineAdapter = searchEngineAdapter;
		_synonymSetStorageAdapter = synonymSetStorageAdapter;
	}

	public void copyToIndex(
		String companyIndexName, SynonymSetIndexName synonymSetIndexName) {

		for (String synonyms : _getSynonymsFromFilters(companyIndexName)) {
			_addSynonymSetToIndex(synonymSetIndexName, synonyms);
		}
	}

	private void _addSynonymSetToIndex(
		SynonymSetIndexName synonymSetIndexName, String synonyms) {

		SynonymSet.SynonymSetBuilder synonymSetBuilder =
			new SynonymSet.SynonymSetBuilder();

		synonymSetBuilder.synonyms(synonyms);

		_synonymSetStorageAdapter.create(
			synonymSetIndexName, synonymSetBuilder.build());
	}

	private String[] _getSynonymsFromFilters(String companyIndexName) {
		LinkedHashSet<String> synonyms = new LinkedHashSet<>();

		for (String filterName : _filterNames) {
			Collections.addAll(
				synonyms,
				SynonymSetFilterReaderUtil.getSynonymSets(
					_searchEngineAdapter, companyIndexName, filterName));
		}

		return synonyms.toArray(new String[0]);
	}

	private final String[] _filterNames;
	private final SearchEngineAdapter _searchEngineAdapter;
	private final SynonymSetStorageAdapter _synonymSetStorageAdapter;

}