/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.cluster.messaging.ClusterBridgeMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.InvokerMessageListener;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.ParallelDestination;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.search.messaging.SearchReaderMessageListener;
import com.liferay.portal.kernel.search.messaging.SearchWriterMessageListener;

import java.util.List;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public abstract class AbstractSearchEngineConfigurator {
	public void afterPropertiesSet() {
		MessageBus messageBus = getMessageBus();

		for (SearchEngine searchEngine : _searchEngines) {

			Destination searchReaderDestination = getSearchReaderDestination(
				messageBus, searchEngine);

			Destination searchWriterDestination = getSearchWriterDestination(
				messageBus, searchEngine);

			SearchEngine originalSearchEngine =
				SearchEngineUtil.getSearchEngine(searchEngine.getName());

			if (originalSearchEngine == null) {
				createSearchEngineListeners(
					searchEngine, searchReaderDestination,
					searchWriterDestination);
			}
			else {
				overrideSearchEngineListeners(
					searchEngine, searchReaderDestination,
					searchWriterDestination);
			}

			SearchEngineProxyWrapper searchEngineProxyWrapper =
				new SearchEngineProxyWrapper(
					searchEngine, getIndexSearcher(), getIndexWriter());

			SearchEngineUtil.addSearchEngine(searchEngineProxyWrapper);
		}
	}

	private void createSearchEngineListeners(
		SearchEngine searchEngine, Destination searchReaderDestination,
		Destination searchWriterDestination) {

		SearchReaderMessageListener searchReaderMessageListener =
			new SearchReaderMessageListener();

		searchReaderMessageListener.setManager(searchEngine.getSearcher());
		searchReaderMessageListener.setMessageBus(getMessageBus());
		searchReaderMessageListener.setSearchEngine(searchEngine);

		searchReaderDestination.register(searchReaderMessageListener);

		SearchWriterMessageListener searchWriterMessageListener =
			new SearchWriterMessageListener();

		searchWriterMessageListener.setManager(searchEngine.getWriter());
		searchWriterMessageListener.setMessageBus(getMessageBus());
		searchWriterMessageListener.setSearchEngine(searchEngine);

		searchWriterDestination.register(searchWriterMessageListener);

		if (searchEngine.isClusteredWrite()) {
			ClusterBridgeMessageListener clusterBridgeMessageListener =
				new ClusterBridgeMessageListener();

			clusterBridgeMessageListener.setPriority(
				searchEngine.getClusteredWritePriority());

			searchWriterDestination.register(clusterBridgeMessageListener);
		}
	}

	private void overrideSearchEngineListeners(
		SearchEngine searchEngine,
		Destination searchReaderDestination,
		Destination searchWriterDestination) {

		Set<MessageListener> readerMessageListeners =
			searchReaderDestination.getMessageListeners();

		for (MessageListener readerMessageListener : readerMessageListeners) {

			if (readerMessageListener instanceof InvokerMessageListener) {

				InvokerMessageListener invokerMessageListener =
					(InvokerMessageListener)readerMessageListener;

				MessageListener delegateMessageListener =
					invokerMessageListener.getMessageListener();

				if (delegateMessageListener instanceof
						SearchReaderMessageListener) {

					SearchReaderMessageListener searchReaderMessageListener =
						(SearchReaderMessageListener) delegateMessageListener;

					searchReaderMessageListener.setSearchEngine(searchEngine);
					searchReaderMessageListener.setManager(
						searchEngine.getSearcher());
				}
			}
		}

		Set<MessageListener> writerMessageListeners =
			searchWriterDestination.getMessageListeners();

		for (MessageListener writerMessageListener : writerMessageListeners) {

			if (writerMessageListener instanceof InvokerMessageListener) {

				InvokerMessageListener invokerMessageListener =
					(InvokerMessageListener)writerMessageListener;

				MessageListener delegateMessageListener =
					invokerMessageListener.getMessageListener();

				if (delegateMessageListener instanceof
						SearchWriterMessageListener) {

					SearchWriterMessageListener searchWriterMessageListener =
						(SearchWriterMessageListener) delegateMessageListener;

					searchWriterMessageListener.setSearchEngine(searchEngine);
					searchWriterMessageListener.setManager(
						searchEngine.getWriter());
				}
				else if (
					(delegateMessageListener instanceof
						 ClusterBridgeMessageListener)) {

					ClusterBridgeMessageListener clusterBridgeMessageListener =
						(ClusterBridgeMessageListener) delegateMessageListener;

					if (searchEngine.isClusteredWrite()) {
						clusterBridgeMessageListener.setActive(true);

						Priority priority =
							searchEngine.getClusteredWritePriority();
						if (priority != null) {
							clusterBridgeMessageListener.setPriority(priority);
						}
					}
					else {
						clusterBridgeMessageListener.setActive(false);
					}
				}
			}
		}
	}

	protected Destination getSearchReaderDestination(
		MessageBus messageBus, SearchEngine searchEngine) {

		String searchEngineId = searchEngine.getName();

		String searchReaderDestinationName =
			SearchEngineUtil.getSearchReaderDestinationName(searchEngineId);

		Destination searchReaderDestination =
			messageBus.getDestination(searchReaderDestinationName);

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

		String searchEngineId = searchEngine.getName();

		String searchWriterDestinationName =
			SearchEngineUtil.getSearchWriterDestinationName(searchEngineId);

		Destination searchWriterDestination =
			messageBus.getDestination(searchWriterDestinationName);

		if (searchWriterDestination == null) {
			ParallelDestination parallelDestination =
				new ParallelDestination();

			parallelDestination.setName(searchWriterDestinationName);
			parallelDestination.open();

			searchWriterDestination = parallelDestination;

			messageBus.addDestination(searchWriterDestination);
		}

		return searchWriterDestination;
	}

	public void setSearchEngines(List<SearchEngine> searchEngines) {
		_searchEngines = searchEngines;
	}

	protected abstract IndexSearcher getIndexSearcher();
	protected abstract IndexWriter getIndexWriter();
	protected abstract MessageBus getMessageBus();

	private List<SearchEngine> _searchEngines;

}