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

package com.liferay.portal.search.elasticsearch6.internal.index.instant;

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexDefinitionsHolderImpl;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexSynchronizationPortalInitializedListener;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexSynchronizer;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexSynchronizerImpl;
import com.liferay.portal.search.elasticsearch6.internal.test.util.microcontainer.Microcontainer;
import com.liferay.portal.search.elasticsearch6.internal.test.util.microcontainer.MicrocontainerImpl;
import com.liferay.portal.search.elasticsearch6.spi.index.IndexRegistrar;
import com.liferay.portal.search.spi.index.IndexDefinition;

import java.util.Arrays;

import org.elasticsearch.action.admin.indices.get.GetIndexAction;
import org.elasticsearch.action.admin.indices.get.GetIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class InstantIndexesTest {

	@Before
	public void setUp() throws Exception {
		ElasticsearchFixture elasticsearchFixture = new ElasticsearchFixture(
			getClass());

		IndexDefinitionsHolderImpl indexDefinitionsHolderImpl =
			new IndexDefinitionsHolderImpl();

		IndexSynchronizerImpl indexSynchronizerImpl = createIndexSynchronizer(
			elasticsearchFixture, indexDefinitionsHolderImpl);

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

		_elasticsearchFixture = elasticsearchFixture;
		_eventsIndexDefinition = new EventsIndexDefinition();
		_indexSynchronizationPortalInitializedListener =
			indexSynchronizationPortalInitializedListener;
		_instancesAndProcessesIndexRegistrar =
			new InstancesAndProcessesIndexRegistrar();
		_microcontainer = microcontainer;
		_tasksIndexDefinition = new TasksIndexDefinition();

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
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
				setElasticsearchClientResolver(elasticsearchFixture);
				setIndexDefinitionsHolder(indexDefinitionsHolderImpl);
			}
		};
	}

	protected void assertIndexesExist(String... expectedIndices) {
		GetIndexRequestBuilder getIndexRequestBuilder =
			GetIndexAction.INSTANCE.newRequestBuilder(
				_elasticsearchFixture.getClient());

		GetIndexResponse getIndexResponse = getIndexRequestBuilder.addIndices(
			expectedIndices
		).get();

		String[] actualIndices = getIndexResponse.getIndices();

		Assert.assertEquals(
			Arrays.asList(expectedIndices), Arrays.asList(actualIndices));
	}

	protected void deployComponents(Object... components) {
		_microcontainer.deploy(components);
	}

	protected void startPortal() {
		_microcontainer.start();
	}

	private ElasticsearchFixture _elasticsearchFixture;
	private EventsIndexDefinition _eventsIndexDefinition;
	private IndexSynchronizationPortalInitializedListener
		_indexSynchronizationPortalInitializedListener;
	private InstancesAndProcessesIndexRegistrar
		_instancesAndProcessesIndexRegistrar;
	private Microcontainer _microcontainer;
	private TasksIndexDefinition _tasksIndexDefinition;

}