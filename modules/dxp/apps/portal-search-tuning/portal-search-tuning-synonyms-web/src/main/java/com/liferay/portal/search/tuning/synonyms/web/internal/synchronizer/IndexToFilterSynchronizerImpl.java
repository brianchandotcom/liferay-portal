/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.configuration.SynonymsConfiguration;
import com.liferay.portal.search.tuning.synonyms.web.internal.filter.SynonymSetFilterWriterUtil;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexReader;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(
	configurationPid = "com.liferay.portal.search.tuning.synonyms.web.internal.configuration.SynonymsConfiguration",
	service = IndexToFilterSynchronizer.class
)
public class IndexToFilterSynchronizerImpl
	implements IndexToFilterSynchronizer {

	@Override
	public void copyToFilter(
		SynonymSetIndexName synonymSetIndexName, String companyIndexName,
		boolean deletion) {

		for (String filterName : _filterNames) {
			SynonymSetFilterWriterUtil.updateSynonymSets(
				_searchEngineAdapter, companyIndexName, filterName,
				TransformUtil.transformToArray(
					_synonymSetIndexReader.search(synonymSetIndexName),
					SynonymSet::getSynonyms, String.class),
				deletion);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		SynonymsConfiguration synonymsConfiguration =
			ConfigurableUtil.createConfigurable(
				SynonymsConfiguration.class, properties);

		_filterNames = synonymsConfiguration.filterNames();

		_synonymSetIndexReader = new SynonymSetIndexReader(
			_searchEngineAdapter);
	}

	private volatile String[] _filterNames;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	private volatile SynonymSetIndexReader _synonymSetIndexReader;

}