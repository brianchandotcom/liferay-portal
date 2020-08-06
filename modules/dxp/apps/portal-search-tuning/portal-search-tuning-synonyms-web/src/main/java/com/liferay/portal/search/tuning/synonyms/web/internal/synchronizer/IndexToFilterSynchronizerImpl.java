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

package com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer;

import com.liferay.portal.search.tuning.synonyms.web.internal.filter.SynonymSetFilterWriter;
import com.liferay.portal.search.tuning.synonyms.web.internal.filter.name.SynonymSetFilterNameHolder;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexReader;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.name.SynonymSetIndexNameBuilder;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(service = IndexToFilterSynchronizer.class)
public class IndexToFilterSynchronizerImpl
	implements IndexToFilterSynchronizer {

	@Override
	public void copyToFilter(
		SynonymSetIndexName synonymSetIndexName, String companyIndexName) {

		updateFilters(
			companyIndexName, getSynonymFromIndex(synonymSetIndexName));
	}

	protected String[] getSynonymFromIndex(
		SynonymSetIndexName synonymSetIndexName) {

		List<SynonymSet> synonymSets = _synonymSetIndexReader.search(
			synonymSetIndexName);

		Stream<SynonymSet> stream = synonymSets.stream();

		return stream.map(
			SynonymSet::getSynonyms
		).toArray(
			String[]::new
		);
	}

	protected void updateFilters(String companyIndexName, String[] synonyms) {
		for (String filterName : _synonymSetFilterNameHolder.getFilterNames()) {
			_synonymSetFilterWriter.updateSynonymSets(
				companyIndexName, filterName, synonyms);
		}
	}

	@Reference
	private SynonymSetFilterNameHolder _synonymSetFilterNameHolder;

	@Reference
	private SynonymSetFilterWriter _synonymSetFilterWriter;

	@Reference
	private SynonymSetIndexNameBuilder _synonymSetIndexNameBuilder;

	@Reference
	private SynonymSetIndexReader _synonymSetIndexReader;

}