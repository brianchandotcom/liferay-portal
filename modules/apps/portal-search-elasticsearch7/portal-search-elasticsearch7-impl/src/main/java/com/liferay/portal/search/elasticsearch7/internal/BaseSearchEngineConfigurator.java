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
import com.liferay.portal.kernel.messaging.InvokerMessageListener;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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
		_destroySearchEngine();

		for (List<ServiceRegistration<?>> destinationServiceRegistrations :
				_destinationServiceRegistrations.values()) {

			for (ServiceRegistration<?> serviceRegistration :
					destinationServiceRegistrations) {

				serviceRegistration.unregister();
			}
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
		_searchEngineRegistration = new SearchEngineRegistration(
			_searchEngineId);

		MessageBus messageBus = getMessageBus();

		Destination searchReaderDestination = _getSearchReaderDestination(
			messageBus, _searchEngineId, true);

		_searchEngineRegistration.setSearchReaderDestinationName(
			searchReaderDestination.getName());

		Destination searchWriterDestination = _getSearchWriterDestination(
			messageBus, _searchEngineId, true);

		_searchEngineRegistration.setSearchWriterDestinationName(
			searchWriterDestination.getName());

		SearchEngineHelper searchEngineHelper = getSearchEngineHelper();

		SearchEngine originalSearchEngine = searchEngineHelper.getSearchEngine(
			_searchEngineId);

		if (originalSearchEngine != null) {
			_searchEngineRegistration.setOverride(true);

			_searchEngineRegistration.setOriginalSearchEngineProxyWrapper(
				(SearchEngineProxyWrapper)originalSearchEngine);

			_savePreviousSearchEngineListeners(
				searchReaderDestination, searchWriterDestination,
				_searchEngineRegistration);

			searchReaderDestination.unregisterMessageListeners();
			searchWriterDestination.unregisterMessageListeners();
		}

		_createSearchEngineListeners(
			_searchEngineId, _searchEngine, searchReaderDestination,
			searchWriterDestination);

		SearchEngineProxyWrapper searchEngineProxyWrapper =
			new SearchEngineProxyWrapper(
				_searchEngine, getIndexSearcher(), getIndexWriter());

		_setSearchEngine(_searchEngineId, searchEngineProxyWrapper);
	}

	protected ServiceRegistration<Destination> registerDestination(
		Destination destination) {

		BundleContext bundleContext = getBundleContext();

		return bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	private void _createSearchEngineListeners(
		String searchEngineId, SearchEngine searchEngine,
		Destination searchReaderDestination,
		Destination searchWriterDestination) {

		_registerSearchEngineMessageListener(
			searchEngineId, searchEngine, searchReaderDestination,
			new SearchReaderMessageListener(), searchEngine.getIndexSearcher());

		_registerSearchEngineMessageListener(
			searchEngineId, searchEngine, searchWriterDestination,
			new SearchWriterMessageListener(), searchEngine.getIndexWriter());
	}

	private void _destroySearchEngine() {
		MessageBus messageBus = getMessageBus();

		Destination searchReaderDestination = _getSearchReaderDestination(
			messageBus, _searchEngineRegistration.getSearchEngineId(), false);

		if (searchReaderDestination != null) {
			searchReaderDestination.unregisterMessageListeners();
		}

		Destination searchWriterDestination = _getSearchWriterDestination(
			messageBus, _searchEngineRegistration.getSearchEngineId(), false);

		if (searchWriterDestination != null) {
			searchWriterDestination.unregisterMessageListeners();
		}

		SearchEngineHelper searchEngineHelper = getSearchEngineHelper();

		searchEngineHelper.removeSearchEngine(
			_searchEngineRegistration.getSearchEngineId());

		if (!_searchEngineRegistration.isOverride()) {
			List<ServiceRegistration<?>> destinationServiceRegistrations =
				_destinationServiceRegistrations.remove(
					_searchEngineRegistration.getSearchEngineId());

			if (destinationServiceRegistrations != null) {
				for (ServiceRegistration<?> serviceRegistration :
						destinationServiceRegistrations) {

					serviceRegistration.unregister();
				}
			}

			return;
		}

		SearchEngineProxyWrapper originalSearchEngineProxy =
			_searchEngineRegistration.getOriginalSearchEngineProxyWrapper();

		if (searchReaderDestination != null) {
			_registerInvokerMessageListener(
				searchReaderDestination,
				_searchEngineRegistration.
					getOriginalSearchReaderMessageListeners());
		}

		if (searchWriterDestination != null) {
			_registerInvokerMessageListener(
				searchWriterDestination,
				_searchEngineRegistration.
					getOriginalSearchWriterMessageListeners());
		}

		_setSearchEngine(
			_searchEngineRegistration.getSearchEngineId(),
			originalSearchEngineProxy);
	}

	private Destination _getSearchReaderDestination(
		MessageBus messageBus, String searchEngineId, boolean createIfAbsent) {

		String searchReaderDestinationName =
			SearchEngineHelperUtil.getSearchReaderDestinationName(
				searchEngineId);

		Destination searchReaderDestination = messageBus.getDestination(
			searchReaderDestinationName);

		if (createIfAbsent && (searchReaderDestination == null)) {
			searchReaderDestination = createSearchReaderDestination(
				searchReaderDestinationName);

			_registerSearchEngineDestination(
				searchEngineId, searchReaderDestination);
		}

		return searchReaderDestination;
	}

	private Destination _getSearchWriterDestination(
		MessageBus messageBus, String searchEngineId, boolean createIfAbsent) {

		String searchWriterDestinationName =
			SearchEngineHelperUtil.getSearchWriterDestinationName(
				searchEngineId);

		Destination searchWriterDestination = messageBus.getDestination(
			searchWriterDestinationName);

		if (createIfAbsent && (searchWriterDestination == null)) {
			searchWriterDestination = createSearchWriterDestination(
				searchWriterDestinationName);

			_registerSearchEngineDestination(
				searchEngineId, searchWriterDestination);
		}

		return searchWriterDestination;
	}

	private void _registerInvokerMessageListener(
		Destination destination,
		List<InvokerMessageListener> invokerMessageListeners) {

		for (InvokerMessageListener invokerMessageListener :
				invokerMessageListeners) {

			destination.register(
				invokerMessageListener.getMessageListener(),
				invokerMessageListener.getClassLoader());
		}
	}

	private void _registerSearchEngineDestination(
		String searchEngineId, Destination destination) {

		List<ServiceRegistration<?>> destinationServiceRegistrations =
			_destinationServiceRegistrations.computeIfAbsent(
				searchEngineId, key -> new ArrayList<>());

		destinationServiceRegistrations.add(registerDestination(destination));
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

	private void _savePreviousSearchEngineListeners(
		Destination searchReaderDestination,
		Destination searchWriterDestination,
		SearchEngineRegistration searchEngineRegistration) {

		Set<MessageListener> searchReaderMessageListeners =
			searchReaderDestination.getMessageListeners();

		for (MessageListener searchReaderMessageListener :
				searchReaderMessageListeners) {

			InvokerMessageListener invokerMessageListener =
				(InvokerMessageListener)searchReaderMessageListener;

			searchEngineRegistration.addOriginalSearchReaderMessageListener(
				invokerMessageListener);
		}

		Set<MessageListener> searchWriterMessageListeners =
			searchWriterDestination.getMessageListeners();

		for (MessageListener searchWriterMessageListener :
				searchWriterMessageListeners) {

			InvokerMessageListener invokerMessageListener =
				(InvokerMessageListener)searchWriterMessageListener;

			searchEngineRegistration.addOriginalSearchWriterMessageListener(
				invokerMessageListener);
		}
	}

	private void _setSearchEngine(
		String searchEngineId, SearchEngine searchEngine) {

		SearchEngineHelper searchEngineHelper = getSearchEngineHelper();

		searchEngineHelper.setSearchEngine(searchEngineId, searchEngine);

		searchEngine.initialize(CompanyConstants.SYSTEM);
	}

	private static final int _INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE =
		GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE));

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSearchEngineConfigurator.class);

	private final Map<String, List<ServiceRegistration<?>>>
		_destinationServiceRegistrations = new ConcurrentHashMap<>();
	private SearchEngine _searchEngine;
	private String _searchEngineId;
	private SearchEngineRegistration _searchEngineRegistration;

	private static class SearchEngineRegistration {

		public void addOriginalSearchReaderMessageListener(
			InvokerMessageListener messageListener) {

			_originalSearchReaderMessageListeners.add(messageListener);
		}

		public void addOriginalSearchWriterMessageListener(
			InvokerMessageListener messageListener) {

			_originalSearchWriterMessageListeners.add(messageListener);
		}

		public SearchEngineProxyWrapper getOriginalSearchEngineProxyWrapper() {
			return _originalSearchEngineProxyWrapper;
		}

		public List<InvokerMessageListener>
			getOriginalSearchReaderMessageListeners() {

			return _originalSearchReaderMessageListeners;
		}

		public List<InvokerMessageListener>
			getOriginalSearchWriterMessageListeners() {

			return _originalSearchWriterMessageListeners;
		}

		public String getSearchEngineId() {
			return _searchEngineId;
		}

		public String getSearchReaderDestinationName() {
			return _searchReaderDestinationName;
		}

		public String getSearchWriterDestinationName() {
			return _searchWriterDestinationName;
		}

		public boolean isOverride() {
			return _override;
		}

		public void setOriginalSearchEngineProxyWrapper(
			SearchEngineProxyWrapper searchEngineProxyWrapper) {

			_originalSearchEngineProxyWrapper = searchEngineProxyWrapper;
		}

		public void setOverride(boolean override) {
			_override = override;
		}

		public void setSearchReaderDestinationName(
			String searchReaderDestinationName) {

			_searchReaderDestinationName = searchReaderDestinationName;
		}

		public void setSearchWriterDestinationName(
			String searchWriterDestinationName) {

			_searchWriterDestinationName = searchWriterDestinationName;
		}

		private SearchEngineRegistration(String searchEngineId) {
			_searchEngineId = searchEngineId;
		}

		private SearchEngineProxyWrapper _originalSearchEngineProxyWrapper;
		private final List<InvokerMessageListener>
			_originalSearchReaderMessageListeners = new ArrayList<>();
		private final List<InvokerMessageListener>
			_originalSearchWriterMessageListeners = new ArrayList<>();
		private boolean _override;
		private final String _searchEngineId;
		private String _searchReaderDestinationName;
		private String _searchWriterDestinationName;

	}

}