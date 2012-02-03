/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public abstract class AbstractSearchEngineConfigurator {

	public void afterPropertiesSet() {
		for (SearchEngine searchEngine : _searchEngines) {
			initSearchEngine(searchEngine);
		}

		_searchEngines.clear();
	}

	public void destroy() {
		for (SearchEngineRegistration searchEngineRegistration :
				_searchEngineRegistrations) {

			destroySearchEngine(searchEngineRegistration);
		}

		_searchEngineRegistrations.clear();
	}

	public void setSearchEngines(List<SearchEngine> searchEngines) {
		_searchEngines = searchEngines;
	}

	protected void createSearchEngineListeners(
		SearchEngine searchEngine, Destination searchReaderDestination,
		Destination searchWriterDestination) {

		registerSearchEngineMessageListener(
			searchEngine, searchReaderDestination,
			new SearchReaderMessageListener(), searchEngine.getIndexSearcher());

		registerSearchEngineMessageListener(
			searchEngine, searchWriterDestination,
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

		SearchEngine searchEngine = searchEngineRegistration.getSearchEngine();

		SearchEngineUtil.removeSearchEngine(searchEngine.getName());

		if (!searchEngineRegistration.isOverride()) {
			return;
		}

		SearchEngineProxyWrapper originalSearchEngineProxy =
			searchEngineRegistration.getOriginalSearchEngineProxyWrapper();

		SearchEngine originalSearchEngine =
			originalSearchEngineProxy.getSearchEngine();

		searchReaderDestination = getSearchReaderDestination(
			messageBus, originalSearchEngine);

		registerInvokerMessageListener(
			searchReaderDestination,
			searchEngineRegistration.getOriginalSearchReaderMessageListeners());

		searchWriterDestination = getSearchWriterDestination(
			messageBus, originalSearchEngine);

		registerInvokerMessageListener(
			searchWriterDestination,
			searchEngineRegistration.getOriginalSearchWriterMessageListeners());

		SearchEngineUtil.addSearchEngine(originalSearchEngineProxy);
	}

	protected abstract IndexSearcher getIndexSearcher();

	protected abstract IndexWriter getIndexWriter();

	protected abstract MessageBus getMessageBus();

	protected abstract ClassLoader getOperatingClassloader();

	protected Destination getSearchReaderDestination(
		MessageBus messageBus, SearchEngine searchEngine) {

		String searchReaderDestinationName =
			SearchEngineUtil.getSearchReaderDestinationName(
				searchEngine.getName());

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
		MessageBus messageBus, SearchEngine searchEngine) {

		String searchWriterDestinationName =
			SearchEngineUtil.getSearchWriterDestinationName(
				searchEngine.getName());

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

	protected void initSearchEngine(SearchEngine searchEngine) {
		SearchEngineRegistration searchEngineRegistration =
			new SearchEngineRegistration(searchEngine);

		_searchEngineRegistrations.add(searchEngineRegistration);

		MessageBus messageBus = getMessageBus();

		Destination searchReaderDestination = getSearchReaderDestination(
			messageBus, searchEngine);

		searchEngineRegistration.setSearchReaderDestinationName(
			searchReaderDestination.getName());

		Destination searchWriterDestination = getSearchWriterDestination(
			messageBus, searchEngine);

		searchEngineRegistration.setSearchWriterDestinationName(
			searchWriterDestination.getName());

		SearchEngine originalSearchEngine = SearchEngineUtil.getSearchEngine(
			searchEngine.getName());

		if (originalSearchEngine != null) {
			searchEngineRegistration.setOverride(true);

			searchEngineRegistration.setOriginalSearchEngineProxyWrapper(
				(SearchEngineProxyWrapper)originalSearchEngine);

			savePreviousSearchEngineListeners(
				searchReaderDestination, searchWriterDestination,
				searchEngineRegistration);

			messageBus.removeDestination(searchReaderDestination.getName());

			searchReaderDestination = getSearchReaderDestination(
				messageBus, originalSearchEngine);

			messageBus.removeDestination(searchWriterDestination.getName());

			searchWriterDestination = getSearchWriterDestination(
				messageBus, originalSearchEngine);
		}

		createSearchEngineListeners(
			searchEngine, searchReaderDestination, searchWriterDestination);

		SearchEngineProxyWrapper searchEngineProxyWrapper =
			new SearchEngineProxyWrapper(
				searchEngine, getIndexSearcher(), getIndexWriter());

		SearchEngineUtil.addSearchEngine(searchEngineProxyWrapper);
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
		SearchEngine searchEngine, Destination destination,
		BaseSearchEngineMessageListener baseSearchEngineMessageListener,
		Object manager) {

		baseSearchEngineMessageListener.setManager(manager);
		baseSearchEngineMessageListener.setMessageBus(getMessageBus());
		baseSearchEngineMessageListener.setSearchEngine(searchEngine);

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

	private List<SearchEngineRegistration> _searchEngineRegistrations =
		new ArrayList<SearchEngineRegistration>();
	private List<SearchEngine> _searchEngines;

	private class SearchEngineRegistration {

		private SearchEngineRegistration(SearchEngine searchEngine) {
			_searchEngine = searchEngine;
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

		public SearchEngine getSearchEngine() {
			return _searchEngine;
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
		private SearchEngine _searchEngine;
		private String _searchReaderDestinationName;
		private String _searchWriterDestinationName;

	}

}