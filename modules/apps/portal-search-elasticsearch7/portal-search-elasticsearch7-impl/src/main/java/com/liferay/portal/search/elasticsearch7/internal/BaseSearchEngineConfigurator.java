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

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineConfigurator;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.search.SearchEngineProxyWrapper;
import com.liferay.portal.kernel.search.messaging.BaseSearchEngineMessageListener;
import com.liferay.portal.kernel.search.messaging.SearchReaderMessageListener;
import com.liferay.portal.kernel.search.messaging.SearchWriterMessageListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Michael C. Han
 */
public abstract class BaseSearchEngineConfigurator
	implements SearchEngineConfigurator {

	@Override
	public void afterPropertiesSet() {
	}

	@Override
	public void destroy() {
		SearchEngineHelper searchEngineHelper = getSearchEngineHelper();

		searchEngineHelper.removeSearchEngine(_searchEngineId);

		for (ServiceRegistration<?> serviceRegistration :
				_destinationServiceRegistrations) {

			serviceRegistration.unregister();
		}

		_destinationServiceRegistrations.clear();
	}

	@Override
	public void setSearchEngine(
		String searchEngineId, SearchEngine searchEngine) {

		_searchEngineId = searchEngineId;
		_searchEngine = searchEngine;
	}

	protected Destination createSearchReaderDestination(
		String searchReaderDestinationName) {

		DestinationConfiguration destinationConfiguration =
			DestinationConfiguration.createSynchronousDestinationConfiguration(
				searchReaderDestinationName);

		DestinationFactory destinationFactory = getDestinationFactory();

		return destinationFactory.createDestination(destinationConfiguration);
	}

	protected Destination createSearchWriterDestination(
		String searchWriterDestinationName) {

		DestinationConfiguration destinationConfiguration = null;

		if (PortalRunMode.isTestMode()) {
			destinationConfiguration =
				DestinationConfiguration.
					createSynchronousDestinationConfiguration(
						searchWriterDestinationName);
		}
		else {
			destinationConfiguration =
				DestinationConfiguration.createParallelDestinationConfiguration(
					searchWriterDestinationName);
		}

		if (_INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE > 0) {
			destinationConfiguration.setMaximumQueueSize(
				_INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE);

			RejectedExecutionHandler rejectedExecutionHandler =
				new ThreadPoolExecutor.CallerRunsPolicy() {

					@Override
					public void rejectedExecution(
						Runnable runnable,
						ThreadPoolExecutor threadPoolExecutor) {

						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"The search index writer's task queue is ",
									"at its maximum capacity. The current ",
									"thread will handle the request."));
						}

						super.rejectedExecution(runnable, threadPoolExecutor);
					}

				};

			destinationConfiguration.setRejectedExecutionHandler(
				rejectedExecutionHandler);
		}

		DestinationFactory destinationFactory = getDestinationFactory();

		return destinationFactory.createDestination(destinationConfiguration);
	}

	protected abstract BundleContext getBundleContext();

	protected abstract DestinationFactory getDestinationFactory();

	protected abstract IndexSearcher getIndexSearcher();

	protected abstract IndexWriter getIndexWriter();

	protected abstract MessageBus getMessageBus();

	protected abstract ClassLoader getOperatingClassLoader();

	protected abstract SearchEngineHelper getSearchEngineHelper();

	protected void initialize() {
		MessageBus messageBus = getMessageBus();

		_registerSearchEngineMessageListener(
			_searchEngineId, _searchEngine,
			_getSearchReaderDestination(messageBus, _searchEngineId),
			new SearchReaderMessageListener(),
			_searchEngine.getIndexSearcher());

		_registerSearchEngineMessageListener(
			_searchEngineId, _searchEngine,
			_getSearchWriterDestination(messageBus, _searchEngineId),
			new SearchWriterMessageListener(), _searchEngine.getIndexWriter());

		SearchEngineHelper searchEngineHelper = getSearchEngineHelper();

		SearchEngineProxyWrapper searchEngineProxyWrapper =
			new SearchEngineProxyWrapper(
				_searchEngine, getIndexSearcher(), getIndexWriter());

		searchEngineHelper.setSearchEngine(
			_searchEngineId, searchEngineProxyWrapper);

		searchEngineProxyWrapper.initialize(CompanyConstants.SYSTEM);
	}

	private Destination _getSearchReaderDestination(
		MessageBus messageBus, String searchEngineId) {

		String searchReaderDestinationName =
			SearchEngineHelperUtil.getSearchReaderDestinationName(
				searchEngineId);

		Destination searchReaderDestination = messageBus.getDestination(
			searchReaderDestinationName);

		if (searchReaderDestination == null) {
			searchReaderDestination = createSearchReaderDestination(
				searchReaderDestinationName);

			BundleContext bundleContext = getBundleContext();

			_destinationServiceRegistrations.add(
				bundleContext.registerService(
					Destination.class, searchReaderDestination,
					MapUtil.singletonDictionary(
						"destination.name",
						searchReaderDestination.getName())));
		}

		return searchReaderDestination;
	}

	private Destination _getSearchWriterDestination(
		MessageBus messageBus, String searchEngineId) {

		String searchWriterDestinationName =
			SearchEngineHelperUtil.getSearchWriterDestinationName(
				searchEngineId);

		Destination searchWriterDestination = messageBus.getDestination(
			searchWriterDestinationName);

		if (searchWriterDestination == null) {
			searchWriterDestination = createSearchWriterDestination(
				searchWriterDestinationName);

			BundleContext bundleContext = getBundleContext();

			_destinationServiceRegistrations.add(
				bundleContext.registerService(
					Destination.class, searchWriterDestination,
					MapUtil.singletonDictionary(
						"destination.name",
						searchWriterDestination.getName())));
		}

		return searchWriterDestination;
	}

	private void _registerSearchEngineMessageListener(
		String searchEngineId, SearchEngine searchEngine,
		Destination destination,
		BaseSearchEngineMessageListener baseSearchEngineMessageListener,
		Object manager) {

		baseSearchEngineMessageListener.setManager(manager);
		baseSearchEngineMessageListener.setMessageBus(getMessageBus());
		baseSearchEngineMessageListener.setSearchEngine(searchEngine);
		baseSearchEngineMessageListener.setSearchEngineId(searchEngineId);

		destination.register(
			baseSearchEngineMessageListener, getOperatingClassLoader());
	}

	private static final int _INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE =
		GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE));

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSearchEngineConfigurator.class);

	private final List<ServiceRegistration<?>>
		_destinationServiceRegistrations = new ArrayList<>();
	private SearchEngine _searchEngine;
	private String _searchEngineId;

}