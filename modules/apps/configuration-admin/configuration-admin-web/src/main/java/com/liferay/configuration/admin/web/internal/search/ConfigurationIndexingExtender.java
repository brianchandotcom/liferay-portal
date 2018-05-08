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

package com.liferay.configuration.admin.web.internal.search;

import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelIterator;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelRetriever;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchException;

import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Michael C. Han
 */
@Component(immediate = true)
public class ConfigurationIndexingExtender {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE,
			new ConfigurationModelsBundleTrackerCustomizer());

		_bundleTracker.open();
	}

	protected void commit(Indexer<ConfigurationModel> indexer) {
		try {
			_indexWriterHelper.commit(indexer.getSearchEngineId());
		}
		catch (SearchException se) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to commit", se);
			}
		}
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();

		_bundleTracker = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationIndexingExtender.class);

	private BundleTracker<ConfigurationModelIterator> _bundleTracker;

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

	@Reference
	private ConfigurationModelIndexer _configurationModelIndexer;

	@Reference
	private ConfigurationModelRetriever _configurationModelRetriever;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	private class ConfigurationModelsBundleTrackerCustomizer
		implements BundleTrackerCustomizer<ConfigurationModelIterator> {

		@Override
		public ConfigurationModelIterator addingBundle(
			Bundle bundle, BundleEvent bundleEvent) {

			if (!_clusterMasterExecutor.isMaster()) {
				return null;
			}

			Map<String, ConfigurationModel> configurationModels =
				_configurationModelRetriever.getConfigurationModels(bundle);

			_configurationModelIndexer.reindex(configurationModels.values());

			commit(_configurationModelIndexer);

			return new ConfigurationModelIterator(configurationModels.values());
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent bundleEvent,
			ConfigurationModelIterator configurationModelIterator) {

			removedBundle(bundle, bundleEvent, configurationModelIterator);

			addingBundle(bundle, bundleEvent);
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent bundleEvent,
			ConfigurationModelIterator configurationModelIterator) {

			if (!_clusterMasterExecutor.isMaster()) {
				return;
			}

			for (ConfigurationModel configurationModel :
					configurationModelIterator.getResults()) {

				try {
					_configurationModelIndexer.delete(configurationModel);
				}
				catch (SearchException se) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to reindex models", se);
					}
				}
			}

			commit(_configurationModelIndexer);
		}

	}

}