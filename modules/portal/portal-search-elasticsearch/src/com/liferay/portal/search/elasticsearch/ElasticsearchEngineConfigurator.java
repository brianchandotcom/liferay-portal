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

package com.liferay.portal.search.elasticsearch;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.search.AbstractSearchEngineConfigurator;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineConfigurator;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnection;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnectionManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = SearchEngineConfigurator.class)
public class ElasticsearchEngineConfigurator
	extends AbstractSearchEngineConfigurator {

	@Override
	public void destroy() {
		ElasticsearchConnection elasticsearchConnection =
			_elasticsearchConnectionManager.getElasticsearchConnection();

		elasticsearchConnection.close();

		super.destroy();
	}

	@Reference
	public void setElasticsearchConnectionManager(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	@Reference
	public void setIndexSearcher(IndexSearcher indexSearcher) {
		_indexSearcher = indexSearcher;
	}

	@Reference
	public void setIndexWriter(IndexWriter indexWriter) {
		_indexWriter = indexWriter;
	}

	@Reference
	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	@Activate
	protected void activate() {
		setSearchEngines(_searchEngines);
	}

	@Override
	protected Destination createSearchReaderDestination(
		String searchReaderDestinationName) {

		if (!PortalRunMode.isTestMode()) {
			return super.createSearchReaderDestination(
				searchReaderDestinationName);
		}

		SynchronousDestination synchronousDestination =
			new SynchronousDestination();

		synchronousDestination.setName(searchReaderDestinationName);

		return synchronousDestination;
	}

	@Override
	protected Destination createSearchWriterDestination(
		String searchWriterDestinationName) {

		if (!PortalRunMode.isTestMode()) {
			return super.createSearchReaderDestination(
				searchWriterDestinationName);
		}

		SynchronousDestination synchronousDestination =
			new SynchronousDestination();

		synchronousDestination.setName(searchWriterDestinationName);

		return synchronousDestination;
	}

	@Override
	protected String getDefaultSearchEngineId() {
		return SearchEngineUtil.SYSTEM_ENGINE_ID;
	}

	@Override
	protected IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	@Override
	protected IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	@Override
	protected MessageBus getMessageBus() {
		return _messageBus;
	}

	@Override
	protected ClassLoader getOperatingClassloader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	@Reference(target = "(search.engine.id=SYSTEM_ENGINE)")
	protected void setSearchEngine(
		SearchEngine searchEngine, Map<String, Object> properties) {

		String searchEngineId = MapUtil.getString(
			properties, "search.engine.id");

		_searchEngines.put(searchEngineId, searchEngine);
	}

	protected void unsetSearchEngine(
		SearchEngine searchEngine, Map<String, Object> properties) {

		String searchEngineId = MapUtil.getString(
			properties, "search.engine.id");

		if (Validator.isNull(searchEngineId)) {
			return;
		}

		_searchEngines.remove(searchEngineId);
	}

	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;
	private MessageBus _messageBus;
	private final Map<String, SearchEngine> _searchEngines =
		new ConcurrentHashMap<String, SearchEngine>();

}