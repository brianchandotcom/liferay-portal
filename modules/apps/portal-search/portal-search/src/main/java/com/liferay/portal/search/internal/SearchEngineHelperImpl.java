/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.search.configuration.SearchEngineHelperConfiguration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.SearchEngineHelperConfiguration",
	immediate = true, service = SearchEngineHelper.class
)
public class SearchEngineHelperImpl implements SearchEngineHelper {

	@Override
	public String[] getEntryClassNames() {
		Set<String> assetEntryClassNames = new HashSet<>();

		for (Indexer<?> indexer : IndexerRegistryUtil.getIndexers()) {
			for (String className : indexer.getSearchClassNames()) {
				if (!_excludedEntryClassNames.contains(className)) {
					assetEntryClassNames.add(className);
				}
			}
		}

		return assetEntryClassNames.toArray(new String[0]);
	}

	@Override
	public SearchEngine getSearchEngine() {
		return _searchEngine;
	}

	@Override
	public synchronized void initialize(long companyId) {
		_searchEngine.initialize(companyId);
	}

	@Override
	public synchronized void removeCompany(long companyId) {
		_searchEngine.removeCompany(companyId);
	}

	@Override
	public void removeSearchEngine() {
		_searchEngine = null;
	}

	@Override
	public void setSearchEngine(SearchEngine searchEngine) {
		_searchEngine = searchEngine;

		for (Company company : _companyLocalService.getCompanies()) {
			searchEngine.initialize(company.getCompanyId());
		}
	}

	@Activate
	@Modified
	protected synchronized void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		SearchEngineHelperConfiguration searchEngineHelperConfiguration =
			ConfigurableUtil.createConfigurable(
				SearchEngineHelperConfiguration.class, properties);

		_excludedEntryClassNames.clear();

		Collections.addAll(
			_excludedEntryClassNames,
			searchEngineHelperConfiguration.excludedEntryClassNames());
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	private final Set<String> _excludedEntryClassNames = new HashSet<>();
	private volatile SearchEngine _searchEngine;

}