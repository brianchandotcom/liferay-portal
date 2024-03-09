/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index.creation.instance.lifecycle;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.web.internal.configuration.SynonymsConfiguration;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexCreator;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexReader;
import com.liferay.portal.search.tuning.synonyms.web.internal.storage.SynonymSetStorageAdapter;
import com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer.FilterToIndexSynchronizer;

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
	service = PortalInstanceLifecycleListener.class
)
public class SynonymSetIndexPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!_searchCapabilities.isSynonymsSupported()) {
			return;
		}

		SynonymSetIndexName synonymSetIndexName =
			_synonymSetIndexNameBuilder.getSynonymSetIndexName(
				company.getCompanyId());

		if (!_synonymSetIndexReader.isExists(synonymSetIndexName)) {
			_synonymSetIndexCreator.create(synonymSetIndexName);

			_filterToIndexSynchronizer.copyToIndex(
				_indexNameBuilder.getIndexName(company.getCompanyId()),
				synonymSetIndexName);
		}
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
		if (!_searchCapabilities.isSynonymsSupported()) {
			return;
		}

		_synonymSetIndexCreator.deleteIfExists(
			_synonymSetIndexNameBuilder.getSynonymSetIndexName(
				company.getCompanyId()));
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_synonymSetIndexCreator = new SynonymSetIndexCreator(
			_searchEngineAdapter);
		_synonymSetIndexReader = new SynonymSetIndexReader(
			_searchEngineAdapter);

		modified(properties);
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		SynonymsConfiguration synonymsConfiguration =
			ConfigurableUtil.createConfigurable(
				SynonymsConfiguration.class, properties);

		_filterToIndexSynchronizer = new FilterToIndexSynchronizer(
			synonymsConfiguration.filterNames(), _searchEngineAdapter,
			_synonymSetStorageAdapter);
	}

	private volatile FilterToIndexSynchronizer _filterToIndexSynchronizer;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private SearchCapabilities _searchCapabilities;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	private SynonymSetIndexCreator _synonymSetIndexCreator;

	@Reference
	private SynonymSetIndexNameBuilder _synonymSetIndexNameBuilder;

	private SynonymSetIndexReader _synonymSetIndexReader;

	@Reference
	private SynonymSetStorageAdapter _synonymSetStorageAdapter;

}