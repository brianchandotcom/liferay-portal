/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index.creation.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.spi.model.index.contributor.IndexContributor;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexReader;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer.IndexToFilterSynchronizer;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = IndexContributor.class)
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
			synonymSetIndexName, companyIndexName);
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