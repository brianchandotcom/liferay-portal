/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.filter.SynonymSetFilterWriterUtil;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexReader;

/**
 * @author Adam Brandizzi
 */
public class IndexToFilterSynchronizer {

	public IndexToFilterSynchronizer(
		String[] filterNames, SearchEngineAdapter searchEngineAdapter,
		SynonymSetIndexReader synonymSetIndexReader) {

		_filterNames = filterNames;
		_searchEngineAdapter = searchEngineAdapter;
		_synonymSetIndexReader = synonymSetIndexReader;
	}

	public void copyToFilter(
		SynonymSetIndexName synonymSetIndexName, String companyIndexName,
		boolean deletion) {

		SynonymSetFilterWriterUtil.updateSynonymSets(
			_searchEngineAdapter, companyIndexName, _filterNames,
			TransformUtil.transformToArray(
				_synonymSetIndexReader.search(synonymSetIndexName),
				SynonymSet::getSynonyms, String.class),
			deletion);
	}

	private final String[] _filterNames;
	private final SearchEngineAdapter _searchEngineAdapter;
	private final SynonymSetIndexReader _synonymSetIndexReader;

}