/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cluster.messaging.ClusterBridgeMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.InvokerMessageListener;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.ParallelDestination;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.search.messaging.BaseSearchEngineMessageListener;
import com.liferay.portal.kernel.search.messaging.SearchReaderMessageListener;
import com.liferay.portal.kernel.search.messaging.SearchWriterMessageListener;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public abstract class AbstractSearchEngineConfigurator {

	public void afterPropertiesSet() {
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
	}

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

		if (searchEngine.isClusteredWrite()) {
			ClusterBridgeMessageListener clusterBridgeMessageListener =
				new ClusterBridgeMessageListener();

			clusterBridgeMessageListener.setPriority(
				searchEngine.getClusteredWritePriority());

			searchWriterDestination.register(clusterBridgeMessageListener);
		}
	}

	protected void destroySearchEngine(
		SearchEngineRegistration searchEngineRegistration) {

		MessageBus messageBus = getMessageBus();

		Destination searchReaderDestination = messageBus.removeDestination(
			searchEngineRegistration.getSearchReaderDestinationName());

		searchReaderDestination.close(true);

		Destination searchWriterDestination = messageBus.removeDestination(
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
			messageBus, searchEngineRegistration.getSearchEngineId(),
			originalSearchEngine);

		registerInvokerMessageListener(
			searchReaderDestination,
			searchEngineRegistration.getOriginalSearchReaderMessageListeners());

		searchWriterDestination = getSearchWriterDestination(
			messageBus, searchEngineRegistration.getSearchEngineId(),
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

	protected abstract MessageBus getMessageBus();

	protected abstract ClassLoader getOperatingClassloader();

	protected Destination getSearchReaderDestination(
		MessageBus messageBus, String searchEngineId,
		SearchEngine searchEngine) {

		String searchReaderDestinationName =
			SearchEngineUtil.getSearchReaderDestinationName(searchEngineId);

		Destination searchReaderDestination = messageBus.getDestination(
			searchReaderDestinationName);

		if (searchReaderDestination == null) {
			SynchronousDestination synchronousDestination =
				new SynchronousDestination();

			synchronousDestination.setName(searchReaderDestinationName);

			synchronousDestination.open();

			searchReaderDestination = synchronousDestination;

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
			ParallelDestination parallelDestination = new ParallelDestination();

			parallelDestination.setName(searchWriterDestinationName);

			parallelDestination.open();

			searchWriterDestination = parallelDestination;

			messageBus.addDestination(searchWriterDestination);
		}

		return searchWriterDestination;
	}

	protected void initSearchEngine(
		String searchEngineId, SearchEngine searchEngine) {

		SearchEngineRegistration searchEngineRegistration =
			new SearchEngineRegistration(searchEngineId);

		_searchEngineRegistrations.add(searchEngineRegistration);

		MessageBus messageBus = getMessageBus();

		Destination searchReaderDestination = getSearchReaderDestination(
			messageBus, searchEngineId, searchEngine);

		searchEngineRegistration.setSearchReaderDestinationName(
			searchReaderDestination.getName());

		Destination searchWriterDestination = getSearchWriterDestination(
			messageBus, searchEngineId, searchEngine);

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

			messageBus.removeDestination(searchReaderDestination.getName());

			searchReaderDestination = getSearchReaderDestination(
				messageBus, searchEngineId, originalSearchEngine);

			messageBus.removeDestination(searchWriterDestination.getName());

			searchWriterDestination = getSearchWriterDestination(
				messageBus, searchEngineId, originalSearchEngine);
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
		baseSearchEngineMessageListener.setMessageBus(getMessageBus());
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

	private String _originalSearchEngineId;
	private List<SearchEngineRegistration> _searchEngineRegistrations =
		new ArrayList<SearchEngineRegistration>();
	private Map<String, SearchEngine> _searchEngines;

	private class SearchEngineRegistration {

		private SearchEngineRegistration(String searchEngineId) {
			_searchEngineId = searchEngineId;
		}

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

		private SearchEngineProxyWrapper _originalSearchEngineProxyWrapper;
		private List<InvokerMessageListener>
			_originalSearchReaderMessageListeners =
				new ArrayList<InvokerMessageListener>();
		private List<InvokerMessageListener>
			_originalSearchWriterMessageListeners =
				new ArrayList<InvokerMessageListener>();
		private boolean _override;
		private String _searchEngineId;
		private String _searchReaderDestinationName;
		private String _searchWriterDestinationName;

	}

}