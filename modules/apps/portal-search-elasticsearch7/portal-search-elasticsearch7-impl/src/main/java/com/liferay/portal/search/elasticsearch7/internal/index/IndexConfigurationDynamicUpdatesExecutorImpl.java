/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexRequest;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.util.Collection;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = IndexConfigurationDynamicUpdatesExecutor.class)
public class IndexConfigurationDynamicUpdatesExecutorImpl
	implements IndexConfigurationDynamicUpdatesExecutor {

	@Override
	public void execute(long companyId) {
		String indexName = _indexNameBuilder.getIndexName(companyId);

		_executePutMappings(indexName);
		_executeUpdateSettings(indexName);
	}

	@Override
	public void executePutMappings(
		String indexName, Collection<String> mappings) {

		for (String mapping : mappings) {
			PutMappingIndexRequest putMappingIndexRequest =
				new PutMappingIndexRequest(new String[] {indexName}, mapping);

			try {
				_searchEngineAdapter.execute(putMappingIndexRequest);
			}
			catch (Exception exception) {
				_log.error("Unable to put mapping " + mapping, exception);
			}
		}
	}

	@Override
	public void executeUpdateSettings(
		String indexName, Collection<String> settings) {

		for (String setting : settings) {
			UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest =
				new UpdateIndexSettingsIndexRequest(indexName);

			updateIndexSettingsIndexRequest.setSettings(setting);

			try {
				_searchEngineAdapter.execute(updateIndexSettingsIndexRequest);
			}
			catch (Exception exception) {
				_log.error("Unable to put setting " + setting, exception);
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private void _executePutMappings(String indexName) {
		List<String> mappings = ResourceUtil.getResourcesAsStrings(
			_bundleContext, _MAPPINGS_UPDATES_DIRECTORY_NAME);

		executePutMappings(indexName, mappings);
	}

	private void _executeUpdateSettings(String indexName) {
		List<String> settings = ResourceUtil.getResourcesAsStrings(
			_bundleContext, _SETTINGS_UPDATES_DIRECTORY_NAME);

		executeUpdateSettings(indexName, settings);
	}

	private static final String _MAPPINGS_UPDATES_DIRECTORY_NAME =
		"/META-INF/mappings/updates";

	private static final String _SETTINGS_UPDATES_DIRECTORY_NAME =
		"/META-INF/settings/updates";

	private static final Log _log = LogFactoryUtil.getLog(
		IndexConfigurationDynamicUpdatesExecutorImpl.class);

	private BundleContext _bundleContext;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}