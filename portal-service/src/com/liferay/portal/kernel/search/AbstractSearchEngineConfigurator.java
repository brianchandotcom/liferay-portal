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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.concurrent.CallerRunsPolicy;
import com.liferay.portal.kernel.concurrent.RejectedExecutionHandler;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.InvokerMessageListener;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.ParallelDestination;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.search.messaging.BaseSearchEngineMessageListener;
import com.liferay.portal.kernel.search.messaging.SearchReaderMessageListener;
import com.liferay.portal.kernel.search.messaging.SearchWriterMessageListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public abstract class AbstractSearchEngineConfigurator
	implements SearchEngineConfigurator {

	@Override
	public void afterPropertiesSet() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			MessageBus.class, new MessageBusServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Override
	public void destroy() {
		for (SearchEngineRegistration searchEngineRegistration :
				_searchEngineRegistrations) {

			destroySearchEngine(searchEngineRegistration);
		}

		_searchEngineRegistrations.clear();

		if (Validator.isNotNull(_originalSearchEngineId)) {
			SearchEngineUtil.setDefaultSearchEngineId(_originalSearchEngineId);

			_originalSearchEngineId = null;
		}

		_serviceTracker.close();
	}

	@Override
	public void setSearchEngines(Map<String, SearchEngine> searchEngines) {
		_searchEngines = searchEngines;
	}

	protected void createSearchEngineListeners(
		String searchEngineId, SearchEngine searchEngine,
		Destination searchReaderDestination,
		Destination searchWriterDestination) {

		registerSearchEngineMessageListener(
			searchEngineId, searchEngine, searchReaderDestination,
			new SearchReaderMessageListener(), searchEngine.getIndexSearcher());

		registerSearchEngineMessageListener(
			searchEngineId, searchEngine, searchWriterDestination,
			new SearchWriterMessageListener(), searchEngine.getIndexWriter());
	}

	protected Destination createSearchReaderDestination(
		String searchReaderDestinationName) {

		SynchronousDestination synchronousDestination =
			new SynchronousDestination();

		synchronousDestination.setName(searchReaderDestinationName);

		return synchronousDestination;
	}

	protected Destination createSearchWriterDestination(
		String searchWriterDestinationName) {

		ParallelDestination parallelDestination = new ParallelDestination();

		parallelDestination.setName(searchWriterDestinationName);

		if (_INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE > 0) {
			parallelDestination.setMaximumQueueSize(
				_INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE);

			RejectedExecutionHandler rejectedExecutionHandler =
				new CallerRunsPolicy() {

					@Override
					public void rejectedExecution(
						Runnable runnable,
						ThreadPoolExecutor threadPoolExecutor) {

						if (_log.isWarnEnabled()) {
							StringBundler sb = new StringBundler(4);

							sb.append("The search index writer's task queue ");
							sb.append("is at its maximum capacity. The ");
							sb.append("current thread will handle the ");
							sb.append("request.");

							_log.warn(sb.toString());
						}

						super.rejectedExecution(runnable, threadPoolExecutor);
					}

				};

			parallelDestination.setRejectedExecutionHandler(
				rejectedExecutionHandler);
		}

		return parallelDestination;
	}

	protected void destroySearchEngine(
		SearchEngineRegistration searchEngineRegistration) {

		Destination searchReaderDestination = _messageBus.removeDestination(
			searchEngineRegistration.getSearchReaderDestinationName());

		searchReaderDestination.close(true);

		Destination searchWriterDestination = _messageBus.removeDestination(
			searchEngineRegistration.getSearchWriterDestinationName());

		searchWriterDestination.close(true);

		SearchEngineUtil.removeSearchEngine(
			searchEngineRegistration.getSearchEngineId());

		if (!searchEngineRegistration.isOverride()) {
			return;
		}

		SearchEngineProxyWrapper originalSearchEngineProxy =
			searchEngineRegistration.getOriginalSearchEngineProxyWrapper();

		SearchEngine originalSearchEngine =
			originalSearchEngineProxy.getSearchEngine();

		searchReaderDestination = getSearchReaderDestination(
			_messageBus, searchEngineRegistration.getSearchEngineId(),
			originalSearchEngine);

		registerInvokerMessageListener(
			searchReaderDestination,
			searchEngineRegistration.getOriginalSearchReaderMessageListeners());

		searchWriterDestination = getSearchWriterDestination(
			_messageBus, searchEngineRegistration.getSearchEngineId(),
			originalSearchEngine);

		registerInvokerMessageListener(
			searchWriterDestination,
			searchEngineRegistration.getOriginalSearchWriterMessageListeners());

		SearchEngineUtil.setSearchEngine(
			searchEngineRegistration.getSearchEngineId(),
			originalSearchEngineProxy);
	}

	protected abstract String getDefaultSearchEngineId();

	protected abstract IndexSearcher getIndexSearcher();

	protected abstract IndexWriter getIndexWriter();

	protected abstract ClassLoader getOperatingClassloader();

	protected Destination getSearchReaderDestination(
		MessageBus messageBus, String searchEngineId,
		SearchEngine searchEngine) {

		String searchReaderDestinationName =
			SearchEngineUtil.getSearchReaderDestinationName(searchEngineId);

		Destination searchReaderDestination = messageBus.getDestination(
			searchReaderDestinationName);

		if (searchReaderDestination == null) {
			searchReaderDestination = createSearchReaderDestination(
				searchReaderDestinationName);

			messageBus.addDestination(searchReaderDestination);
		}

		return searchReaderDestination;
	}

	protected Destination getSearchWriterDestination(
		MessageBus messageBus, String searchEngineId,
		SearchEngine searchEngine) {

		String searchWriterDestinationName =
			SearchEngineUtil.getSearchWriterDestinationName(searchEngineId);

		Destination searchWriterDestination = messageBus.getDestination(
			searchWriterDestinationName);

		if (searchWriterDestination == null) {
			searchWriterDestination = createSearchWriterDestination(
				searchWriterDestinationName);

			messageBus.addDestination(searchWriterDestination);
		}

		return searchWriterDestination;
	}

	protected void initialize() {
		Set<Entry<String, SearchEngine>> entrySet = _searchEngines.entrySet();

		for (Entry<String, SearchEngine> entry : entrySet) {
			initSearchEngine(entry.getKey(), entry.getValue());
		}

		String defaultSearchEngineId = getDefaultSearchEngineId();

		if (Validator.isNotNull(defaultSearchEngineId)) {
			_originalSearchEngineId =
				SearchEngineUtil.getDefaultSearchEngineId();

			SearchEngineUtil.setDefaultSearchEngineId(defaultSearchEngineId);
		}

		_searchEngines.clear();
	}

	protected void initSearchEngine(
		String searchEngineId, SearchEngine searchEngine) {

		SearchEngineRegistration searchEngineRegistration =
			new SearchEngineRegistration(searchEngineId);

		_searchEngineRegistrations.add(searchEngineRegistration);

		Destination searchReaderDestination = getSearchReaderDestination(
			_messageBus, searchEngineId, searchEngine);

		searchEngineRegistration.setSearchReaderDestinationName(
			searchReaderDestination.getName());

		Destination searchWriterDestination = getSearchWriterDestination(
			_messageBus, searchEngineId, searchEngine);

		searchEngineRegistration.setSearchWriterDestinationName(
			searchWriterDestination.getName());

		SearchEngine originalSearchEngine =
			SearchEngineUtil.getSearchEngineSilent(searchEngineId);

		if (originalSearchEngine != null) {
			searchEngineRegistration.setOverride(true);

			searchEngineRegistration.setOriginalSearchEngineProxyWrapper(
				(SearchEngineProxyWrapper)originalSearchEngine);

			savePreviousSearchEngineListeners(
				searchReaderDestination, searchWriterDestination,
				searchEngineRegistration);

			_messageBus.removeDestination(searchReaderDestination.getName());

			searchReaderDestination = getSearchReaderDestination(
				_messageBus, searchEngineId, originalSearchEngine);

			_messageBus.removeDestination(searchWriterDestination.getName());

			searchWriterDestination = getSearchWriterDestination(
				_messageBus, searchEngineId, originalSearchEngine);
		}

		createSearchEngineListeners(
			searchEngineId, searchEngine, searchReaderDestination,
			searchWriterDestination);

		SearchEngineProxyWrapper searchEngineProxyWrapper =
			new SearchEngineProxyWrapper(
				searchEngine, getIndexSearcher(), getIndexWriter());

		SearchEngineUtil.setSearchEngine(
			searchEngineId, searchEngineProxyWrapper);
	}

	protected void registerInvokerMessageListener(
		Destination destination,
		List<InvokerMessageListener> invokerMessageListeners) {

		for (InvokerMessageListener invokerMessageListener :
				invokerMessageListeners) {

			destination.register(
				invokerMessageListener.getMessageListener(),
				invokerMessageListener.getClassLoader());
		}
	}

	protected void registerSearchEngineMessageListener(
		String searchEngineId, SearchEngine searchEngine,
		Destination destination,
		BaseSearchEngineMessageListener baseSearchEngineMessageListener,
		Object manager) {

		baseSearchEngineMessageListener.setManager(manager);
		baseSearchEngineMessageListener.setMessageBus(_messageBus);
		baseSearchEngineMessageListener.setSearchEngine(searchEngine);
		baseSearchEngineMessageListener.setSearchEngineId(searchEngineId);

		destination.register(
			baseSearchEngineMessageListener, getOperatingClassloader());
	}

	protected void savePreviousSearchEngineListeners(
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

	private static final int _INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE =
		GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INDEX_SEARCH_WRITER_MAX_QUEUE_SIZE));

	private static final Log _log = LogFactoryUtil.getLog(
		AbstractSearchEngineConfigurator.class);

	private volatile MessageBus _messageBus;
	private String _originalSearchEngineId;
	private final List<SearchEngineRegistration> _searchEngineRegistrations =
		new ArrayList<>();
	private Map<String, SearchEngine> _searchEngines;
	private ServiceTracker<MessageBus, MessageBus> _serviceTracker;

	private class MessageBusServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<MessageBus, MessageBus> {

		@Override
		public MessageBus addingService(
			ServiceReference<MessageBus> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			_messageBus = registry.getService(serviceReference);

			initialize();

			return _messageBus;
		}

		@Override
		public void modifiedService(
			ServiceReference<MessageBus> serviceReference,
			MessageBus messageBus) {
		}

		@Override
		public void removedService(
			ServiceReference<MessageBus> serviceReference,
			MessageBus messageBus) {

			_messageBus = null;
		}

	}

	private class SearchEngineRegistration {

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