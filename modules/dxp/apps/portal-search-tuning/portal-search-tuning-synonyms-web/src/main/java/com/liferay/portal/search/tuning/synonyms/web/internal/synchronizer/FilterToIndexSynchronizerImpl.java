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

import com.liferay.portal.search.tuning.synonyms.web.internal.filter.SynonymSetFilterReader;
import com.liferay.portal.search.tuning.synonyms.web.internal.filter.name.SynonymSetFilterNameHolder;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexWriter;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.name.SynonymSetIndexNameBuilder;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(service = FilterToIndexSynchronizer.class)
public class FilterToIndexSynchronizerImpl
	implements FilterToIndexSynchronizer {

	@Override
	public void copyToIndex(
		String companyIndexName, SynonymSetIndexName synonymSetIndexName) {

		for (String synonyms : getSynonymsFromFilters(companyIndexName)) {
			addSynonymSetToIndex(synonymSetIndexName, synonyms);
		}
	}

	protected void addSynonymSetToIndex(
		SynonymSetIndexName synonymSetIndexName, String synonyms) {

		SynonymSet.SynonymSetBuilder synonymSetBuilder =
			new SynonymSet.SynonymSetBuilder();

		synonymSetBuilder.synonyms(synonyms);

		_synonymSetIndexWriter.create(
			synonymSetIndexName, synonymSetBuilder.build());
	}

	protected String[] getSynonymsFromFilters(String companyIndexName) {
		LinkedHashSet<String> synonyms = Stream.of(
			_synonymSetFilterNameHolder.getFilterNames()
		).map(
			filterName -> _synonymSetFilterReader.getSynonymSets(
				companyIndexName, filterName)
		).flatMap(
			Stream::of
		).collect(
			Collectors.toCollection(LinkedHashSet::new)
		);

		return synonyms.toArray(new String[0]);
	}

	@Reference
	private SynonymSetFilterNameHolder _synonymSetFilterNameHolder;

	@Reference
	private SynonymSetFilterReader _synonymSetFilterReader;

	@Reference
	private SynonymSetIndexNameBuilder _synonymSetIndexNameBuilder;

	@Reference
	private SynonymSetIndexWriter _synonymSetIndexWriter;

}