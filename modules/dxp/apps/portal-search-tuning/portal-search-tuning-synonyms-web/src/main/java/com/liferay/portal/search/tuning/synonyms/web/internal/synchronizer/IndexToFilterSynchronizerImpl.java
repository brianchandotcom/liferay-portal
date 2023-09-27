/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.filter.SynonymSetFilterWriter;
import com.liferay.portal.search.tuning.synonyms.web.internal.filter.name.SynonymSetFilterNameHolder;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexReader;

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
		SynonymSetIndexName synonymSetIndexName, String companyIndexName,
		boolean deletion) {

		for (String filterName : _synonymSetFilterNameHolder.getFilterNames()) {
			_synonymSetFilterWriter.updateSynonymSets(
				companyIndexName, filterName,
				TransformUtil.transformToArray(
					_synonymSetIndexReader.search(synonymSetIndexName),
					SynonymSet::getSynonyms, String.class),
				deletion);
		}
	}

	@Reference
	private SynonymSetFilterNameHolder _synonymSetFilterNameHolder;

	@Reference
	private SynonymSetFilterWriter _synonymSetFilterWriter;

	@Reference
	private SynonymSetIndexReader _synonymSetIndexReader;

}