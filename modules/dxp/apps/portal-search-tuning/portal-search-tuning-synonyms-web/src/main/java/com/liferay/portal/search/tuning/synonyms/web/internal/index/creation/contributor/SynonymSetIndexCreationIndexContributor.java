/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index.creation.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.spi.model.index.contributor.IndexContributor;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexReader;
import com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer.IndexToFilterSynchronizer;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(service = IndexContributor.class)
public class SynonymSetIndexCreationIndexContributor
	implements IndexContributor {

	@Override
	public void onAfterCreate(String companyIndexName) {
		if (Objects.equals(
				_searchEngineInformation.getVendorString(), "Solr")) {

			return;
		}

		SynonymSetIndexName synonymSetIndexName =
			() ->
				companyIndexName + StringPool.DASH + SYNONYMS_INDEX_NAME_SUFFIX;

		if (!_synonymSetIndexReader.isExists(synonymSetIndexName)) {
			return;
		}

		_indexToFilterSynchronizer.copyToFilter(
			synonymSetIndexName, companyIndexName, false);
	}

	protected static final String SYNONYMS_INDEX_NAME_SUFFIX =
		"search-tuning-synonyms";

	@Reference
	private IndexToFilterSynchronizer _indexToFilterSynchronizer;

	@Reference
	private SearchEngineInformation _searchEngineInformation;

	@Reference
	private SynonymSetIndexReader _synonymSetIndexReader;

}