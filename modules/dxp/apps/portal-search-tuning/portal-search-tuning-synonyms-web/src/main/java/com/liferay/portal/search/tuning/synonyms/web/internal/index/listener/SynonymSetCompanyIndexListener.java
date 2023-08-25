/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index.listener;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.spi.index.listener.CompanyIndexListener;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.configuration.SynonymsConfiguration;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexReader;
import com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer.IndexToFilterSynchronizer;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(
	configurationPid = "com.liferay.portal.search.tuning.synonyms.web.internal.configuration.SynonymsConfiguration",
	service = CompanyIndexListener.class
)
public class SynonymSetCompanyIndexListener implements CompanyIndexListener {

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

	@Activate
	protected void activate(Map<String, Object> properties) {
		_synonymSetIndexReader = new SynonymSetIndexReader(
			_searchEngineAdapter);

		modified(properties);
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		SynonymsConfiguration synonymsConfiguration =
			ConfigurableUtil.createConfigurable(
				SynonymsConfiguration.class, properties);

		_indexToFilterSynchronizer = new IndexToFilterSynchronizer(
			synonymsConfiguration.filterNames(), _searchEngineAdapter,
			_synonymSetIndexReader);
	}

	protected static final String SYNONYMS_INDEX_NAME_SUFFIX =
		"search-tuning-synonyms";

	private volatile IndexToFilterSynchronizer _indexToFilterSynchronizer;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private SearchEngineInformation _searchEngineInformation;

	private SynonymSetIndexReader _synonymSetIndexReader;

}