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

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.index.IndexDefinitionsRegistryImpl;
import com.liferay.portal.search.elasticsearch7.internal.index.IndexSynchronizationPortalInitializedListener;
import com.liferay.portal.search.elasticsearch7.internal.index.IndexSynchronizer;
import com.liferay.portal.search.elasticsearch7.internal.index.IndexSynchronizerImpl;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index.CreateIndexRequestExecutor;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index.CreateIndexRequestExecutorImpl;
import com.liferay.portal.search.elasticsearch7.internal.test.util.microcontainer.Microcontainer;
import com.liferay.portal.search.elasticsearch7.internal.test.util.microcontainer.MicrocontainerImpl;
import com.liferay.portal.search.elasticsearch7.spi.index.IndexRegistrar;
import com.liferay.portal.search.spi.index.IndexDefinition;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.Arrays;

import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author André de Oliveira
 */
public class InstantIndexesTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			InstantIndexesTest.class.getSimpleName());

		_elasticsearchFixture.setUp();

		_bundleContext = SystemBundleUtil.getBundleContext();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Before
	public void setUp() throws Exception {
		IndexDefinitionsRegistryImpl indexDefinitionsRegistryImpl =
			new IndexDefinitionsRegistryImpl();

		_indexSynchronizerImpl = _createIndexSynchronizer(
			_elasticsearchFixture, indexDefinitionsRegistryImpl);

		IndexSynchronizationPortalInitializedListener
			indexSynchronizationPortalInitializedListener =
				_createIndexSynchronizationPortalInitializedListener(
					_indexSynchronizerImpl);

		Microcontainer microcontainer = new MicrocontainerImpl();

		microcontainer.wire(
			IndexDefinition.class,
			indexDefinitionsRegistryImpl::addIndexDefinition,
			indexSynchronizationPortalInitializedListener::addIndexDefinition);

		microcontainer.wire(
			IndexRegistrar.class,
			indexSynchronizationPortalInitializedListener::addIndexRegistrar);

		_eventsIndexDefinition = new EventsIndexDefinition();
		_indexSynchronizationPortalInitializedListener =
			indexSynchronizationPortalInitializedListener;
		_instancesAndProcessesIndexRegistrar =
			new InstancesAndProcessesIndexRegistrar();
		_microcontainer = microcontainer;
		_tasksIndexDefinition = new TasksIndexDefinition();

		_serviceRegistration = _bundleContext.registerService(
			IndexRegistrar.class, _instancesAndProcessesIndexRegistrar, null);
	}

	@After
	public void tearDown() throws Exception {
		ReflectionTestUtil.invoke(
			_indexSynchronizerImpl, "deactivate", new Class<?>[0]);

		_serviceRegistration.unregister();
	}

	@Test
	public void testAutomaticIndexCreation() throws Exception {
		_deployComponents(
			_eventsIndexDefinition,
			_indexSynchronizationPortalInitializedListener,
			_instancesAndProcessesIndexRegistrar, _tasksIndexDefinition);

		_startPortal();

		_assertIndexesExist(
			EventsIndexDefinition.INDEX_NAME_WORKFLOW_EVENTS,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_INSTANCES,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_PROCESSES,
			TasksIndexDefinition.INDEX_NAME_WORKFLOW_TASKS);
	}

	@Test
	public void testRuntimeIndexCreation() throws Exception {
		_deployComponents(
			_indexSynchronizationPortalInitializedListener,
			_instancesAndProcessesIndexRegistrar, _tasksIndexDefinition);

		_startPortal();

		_assertIndexesExist(
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_INSTANCES,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_PROCESSES,
			TasksIndexDefinition.INDEX_NAME_WORKFLOW_TASKS);

		_deployComponents(_eventsIndexDefinition);

		_assertIndexesExist(
			EventsIndexDefinition.INDEX_NAME_WORKFLOW_EVENTS,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_INSTANCES,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_PROCESSES,
			TasksIndexDefinition.INDEX_NAME_WORKFLOW_TASKS);
	}

	@Test
	public void testStartTwiceIndexCreation() throws Exception {
		_deployComponents(
			_eventsIndexDefinition,
			_indexSynchronizationPortalInitializedListener,
			_instancesAndProcessesIndexRegistrar, _tasksIndexDefinition);

		_startPortal();

		_assertIndexesExist(
			EventsIndexDefinition.INDEX_NAME_WORKFLOW_EVENTS,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_INSTANCES,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_PROCESSES,
			TasksIndexDefinition.INDEX_NAME_WORKFLOW_TASKS);

		_startPortal();

		_assertIndexesExist(
			EventsIndexDefinition.INDEX_NAME_WORKFLOW_EVENTS,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_INSTANCES,
			InstancesAndProcessesIndexRegistrar.INDEX_NAME_WORKFLOW_PROCESSES,
			TasksIndexDefinition.INDEX_NAME_WORKFLOW_TASKS);
	}

	private void _assertIndexesExist(String... expectedIndices) {
		GetIndexRequest getIndexRequest = new GetIndexRequest();

		getIndexRequest.indices(expectedIndices);

		GetIndexResponse getIndexResponse = _getIndexResponse(getIndexRequest);

		String[] actualIndices = getIndexResponse.getIndices();

		Assert.assertEquals(
			Arrays.asList(expectedIndices), Arrays.asList(actualIndices));
	}

	private CreateIndexRequestExecutor _createCreateIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		CreateIndexRequestExecutor createIndexRequestExecutor =
			new CreateIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			createIndexRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return createIndexRequestExecutor;
	}

	private IndexSynchronizationPortalInitializedListener
		_createIndexSynchronizationPortalInitializedListener(
			IndexSynchronizer indexSynchronizer) {

		return new IndexSynchronizationPortalInitializedListener() {
			{
				setIndexSynchronizer(indexSynchronizer);
			}
		};
	}

	private IndexSynchronizerImpl _createIndexSynchronizer(
		ElasticsearchFixture elasticsearchFixture,
		IndexDefinitionsRegistryImpl indexDefinitionsRegistryImpl) {

		IndexSynchronizerImpl indexSynchronizerImpl =
			new IndexSynchronizerImpl();

		ReflectionTestUtil.setFieldValue(
			indexSynchronizerImpl, "_createIndexRequestExecutor",
			_createCreateIndexRequestExecutor(elasticsearchFixture));
		ReflectionTestUtil.setFieldValue(
			indexSynchronizerImpl, "_indexDefinitionsRegistry",
			indexDefinitionsRegistryImpl);

		ReflectionTestUtil.invoke(
			indexSynchronizerImpl, "activate",
			new Class<?>[] {BundleContext.class}, _bundleContext);

		return indexSynchronizerImpl;
	}

	private void _deployComponents(Object... components) {
		_microcontainer.deploy(components);
	}

	private GetIndexResponse _getIndexResponse(
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

	private void _startPortal() {
		_microcontainer.start();
	}

	private static BundleContext _bundleContext;
	private static ElasticsearchFixture _elasticsearchFixture;

	private EventsIndexDefinition _eventsIndexDefinition;
	private IndexSynchronizationPortalInitializedListener
		_indexSynchronizationPortalInitializedListener;
	private IndexSynchronizerImpl _indexSynchronizerImpl;
	private InstancesAndProcessesIndexRegistrar
		_instancesAndProcessesIndexRegistrar;
	private Microcontainer _microcontainer;
	private ServiceRegistration<IndexRegistrar> _serviceRegistration;
	private TasksIndexDefinition _tasksIndexDefinition;

}