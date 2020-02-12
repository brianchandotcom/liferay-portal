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

package com.liferay.portal.search.elasticsearch7.internal.index.instant;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.index.IndexDefinitionsHolderImpl;
import com.liferay.portal.search.elasticsearch7.internal.index.IndexSynchronizationPortalInitializedListener;
import com.liferay.portal.search.elasticsearch7.internal.index.IndexSynchronizer;
import com.liferay.portal.search.elasticsearch7.internal.index.IndexSynchronizerImpl;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index.CreateIndexRequestExecutor;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index.CreateIndexRequestExecutorImpl;
import com.liferay.portal.search.elasticsearch7.internal.test.util.microcontainer.Microcontainer;
import com.liferay.portal.search.elasticsearch7.internal.test.util.microcontainer.MicrocontainerImpl;
import com.liferay.portal.search.elasticsearch7.spi.index.IndexRegistrar;
import com.liferay.portal.search.spi.index.IndexDefinition;

import java.io.IOException;

import java.util.Arrays;

import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class InstantIndexesTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			InstantIndexesTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Before
	public void setUp() throws Exception {
		IndexDefinitionsHolderImpl indexDefinitionsHolderImpl =
			new IndexDefinitionsHolderImpl();

		IndexSynchronizerImpl indexSynchronizerImpl = createIndexSynchronizer(
			_elasticsearchFixture, indexDefinitionsHolderImpl);

		IndexSynchronizationPortalInitializedListener
			indexSynchronizationPortalInitializedListener =
				createIndexSynchronizationPortalInitializedListener(
					indexSynchronizerImpl);

		Microcontainer microcontainer = new MicrocontainerImpl();

		microcontainer.wire(
			IndexDefinition.class,
			indexDefinitionsHolderImpl::addIndexDefinition,
			indexSynchronizationPortalInitializedListener::addIndexDefinition);

		microcontainer.wire(
			IndexRegistrar.class, indexSynchronizerImpl::addIndexRegistrar,
			indexSynchronizationPortalInitializedListener::addIndexRegistrar);

		_eventsIndexDefinition = new EventsIndexDefinition();
		_indexSynchronizationPortalInitializedListener =
			indexSynchronizationPortalInitializedListener;
		_instancesAndProcessesIndexRegistrar =
			new InstancesAndProcessesIndexRegistrar();
		_microcontainer = microcontainer;
		_tasksIndexDefinition = new TasksIndexDefinition();
	}

	@Test
	public void testAutomaticIndexCreation() throws Exception {
		deployComponents(
			_eventsIndexDefinition,
			_indexSynchronizationPortalInitializedListener,
			_instancesAndProcessesIndexRegistrar, _tasksIndexDefinition);

		startPortal();

		assertIndexesExist(
			EventsIndexDefinition.INDEX_NAME_WORKFLOW_EVENTS,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_INSTANCES,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_PROCESSES,
			TasksIndexDefinition.INDEX_NAME_WORKFLOW_TASKS);
	}

	@Test
	public void testRuntimeIndexCreation() throws Exception {
		deployComponents(
			_indexSynchronizationPortalInitializedListener,
			_instancesAndProcessesIndexRegistrar, _tasksIndexDefinition);

		startPortal();

		assertIndexesExist(
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_INSTANCES,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_PROCESSES,
			TasksIndexDefinition.INDEX_NAME_WORKFLOW_TASKS);

		deployComponents(_eventsIndexDefinition);

		assertIndexesExist(
			EventsIndexDefinition.INDEX_NAME_WORKFLOW_EVENTS,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_INSTANCES,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_PROCESSES,
			TasksIndexDefinition.INDEX_NAME_WORKFLOW_TASKS);
	}

	@Test
	public void testStartTwiceIndexCreation() throws Exception {
		deployComponents(
			_eventsIndexDefinition,
			_indexSynchronizationPortalInitializedListener,
			_instancesAndProcessesIndexRegistrar, _tasksIndexDefinition);

		startPortal();

		assertIndexesExist(
			EventsIndexDefinition.INDEX_NAME_WORKFLOW_EVENTS,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_INSTANCES,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_PROCESSES,
			TasksIndexDefinition.INDEX_NAME_WORKFLOW_TASKS);

		startPortal();

		assertIndexesExist(
			EventsIndexDefinition.INDEX_NAME_WORKFLOW_EVENTS,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_INSTANCES,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_PROCESSES,
			TasksIndexDefinition.INDEX_NAME_WORKFLOW_TASKS);
	}

	protected static CreateIndexRequestExecutor
		createCreateIndexRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new CreateIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static IndexSynchronizationPortalInitializedListener
		createIndexSynchronizationPortalInitializedListener(
			IndexSynchronizer indexSynchronizer) {

		return new IndexSynchronizationPortalInitializedListener() {
			{
				setIndexSynchronizer(indexSynchronizer);
			}
		};
	}

	protected static IndexSynchronizerImpl createIndexSynchronizer(
		ElasticsearchFixture elasticsearchFixture,
		IndexDefinitionsHolderImpl indexDefinitionsHolderImpl) {

		return new IndexSynchronizerImpl() {
			{
				setCreateIndexRequestExecutor(
					createCreateIndexRequestExecutor(elasticsearchFixture));
				setIndexDefinitionsHolder(indexDefinitionsHolderImpl);
			}
		};
	}

	protected void assertIndexesExist(String... expectedIndices) {
		GetIndexRequest getIndexRequest = new GetIndexRequest();

		getIndexRequest.indices(expectedIndices);

		GetIndexResponse getIndexResponse = getIndexResponse(getIndexRequest);

		String[] actualIndices = getIndexResponse.getIndices();

		Assert.assertEquals(
			Arrays.asList(expectedIndices), Arrays.asList(actualIndices));
	}

	protected void deployComponents(Object... components) {
		_microcontainer.deploy(components);
	}

	protected GetIndexResponse getIndexResponse(
		GetIndexRequest getIndexRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchFixture.getRestHighLevelClient();

		IndicesClient indicesClient = restHighLevelClient.indices();

		try {
			return indicesClient.get(getIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected void startPortal() {
		_microcontainer.start();
	}

	private static ElasticsearchFixture _elasticsearchFixture;

	private EventsIndexDefinition _eventsIndexDefinition;
	private IndexSynchronizationPortalInitializedListener
		_indexSynchronizationPortalInitializedListener;
	private InstancesAndProcessesIndexRegistrar
		_instancesAndProcessesIndexRegistrar;
	private Microcontainer _microcontainer;
	private TasksIndexDefinition _tasksIndexDefinition;

}