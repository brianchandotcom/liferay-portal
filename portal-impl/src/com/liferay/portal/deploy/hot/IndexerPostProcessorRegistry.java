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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.util.StringPlus;

import java.util.List;

/**
 * @author Raymond Augé
 */
public class IndexerPostProcessorRegistry {

	public IndexerPostProcessorRegistry() {
		Registry registry = RegistryUtil.getRegistry();

		Filter indexerPostProcessorFilter = registry.getFilter(
			"(&(indexer.class.name=*)(objectClass=" +
				IndexerPostProcessor.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			indexerPostProcessorFilter,
			new IndexerPostProcessorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void close() {
		_serviceTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerPostProcessorRegistry.class);

	private final ServiceTracker<IndexerPostProcessor, IndexerPostProcessor>
		_serviceTracker;

	private class IndexerPostProcessorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<IndexerPostProcessor, IndexerPostProcessor> {

		@Override
		public IndexerPostProcessor addingService(
			ServiceReference<IndexerPostProcessor> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			IndexerPostProcessor indexerPostProcessor = registry.getService(
				serviceReference);

			List<String> indexerClassNames = StringPlus.asList(
				serviceReference.getProperty("indexer.class.name"));

			for (String indexerClassName : indexerClassNames) {
				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					indexerClassName);

				if (indexer == null) {
					_log.error(
						"No indexer for " + indexerClassName + " was found");

					continue;
				}

				indexer.registerIndexerPostProcessor(indexerPostProcessor);
			}

			return indexerPostProcessor;
		}

		@Override
		public void modifiedService(
			ServiceReference<IndexerPostProcessor> serviceReference,
			IndexerPostProcessor indexerPostProcessor) {
		}

		@Override
		public void removedService(
			ServiceReference<IndexerPostProcessor> serviceReference,
			IndexerPostProcessor indexerPostProcessor) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			List<String> indexerClassNames = StringPlus.asList(
				serviceReference.getProperty("indexer.class.name"));

			for (String indexerClassName : indexerClassNames ) {
				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					indexerClassName);

				if (indexer == null) {
					_log.error(
						"No indexer for " + indexerClassName + " was found");

					continue;
				}

				indexer.unregisterIndexerPostProcessor(indexerPostProcessor);
			}
		}

	}

}