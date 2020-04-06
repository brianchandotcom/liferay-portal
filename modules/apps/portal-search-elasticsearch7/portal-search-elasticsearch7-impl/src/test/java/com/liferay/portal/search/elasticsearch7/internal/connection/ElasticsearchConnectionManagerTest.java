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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;

import org.elasticsearch.client.RestHighLevelClient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
@Ignore
public class ElasticsearchConnectionManagerTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		resetMockConnections();

		_elasticsearchConnectionManager = createElasticsearchConnectionManager(
			_embeddedElasticsearchConnection, _remoteElasticsearchConnection1,
			_remoteElasticsearchConnection2, _remoteElasticsearchConnection3);
	}

	@Test
	public void testActivateMustNotChangeAnyConnection() {
		Mockito.reset(
			_embeddedElasticsearchConnection, _remoteElasticsearchConnection1,
			_remoteElasticsearchConnection2, _remoteElasticsearchConnection3);

		HashMap<String, Object> properties = HashMapBuilder.<String, Object>put(
			"operationMode", OperationMode.EMBEDDED.name()
		).build();

		_elasticsearchConnectionManager.activate(properties);

		verifyNeverCloseNeverConnect(_embeddedElasticsearchConnection);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection1);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection2);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection3);

		properties = HashMapBuilder.<String, Object>put(
			"operationMode", OperationMode.REMOTE.name()
		).build();

		_elasticsearchConnectionManager.activate(properties);

		verifyNeverCloseNeverConnect(_embeddedElasticsearchConnection);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection1);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection2);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection3);
	}

	@Test
	public void testGetElasticsearchConnectionWhenOperationModeNotSet() {
		Assert.assertEquals(
			null, _elasticsearchConnectionManager.getElasticsearchConnection());
	}

	@Test
	public void testGetEmbeddedElasticsearchConnection() {
		_elasticsearchConnectionManager.setOperationMode(
			OperationMode.EMBEDDED);

		Assert.assertEquals(
			_embeddedElasticsearchConnection,
			_elasticsearchConnectionManager.getElasticsearchConnection());
	}

	@Test
	public void testGetEmbeddedModeRestHighLevelClient() {
		_elasticsearchConnectionManager.setOperationMode(
			OperationMode.EMBEDDED);

		Assert.assertEquals(
			_embeddedElasticsearchConnection.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient());

		Assert.assertEquals(
			_remoteElasticsearchConnection1.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient(
				_REMOTE_1_CONNECTION_ID));
	}

	@Test
	public void testGetRemoteElasticsearchConnection() {
		HashMap<String, Object> properties = HashMapBuilder.<String, Object>put(
			"operationMode", OperationMode.REMOTE.name()
		).put(
			"remoteClusterConnectionId", _REMOTE_1_CONNECTION_ID
		).build();

		_elasticsearchConnectionManager.setConfiguration(properties);

		Assert.assertEquals(
			_remoteElasticsearchConnection1,
			_elasticsearchConnectionManager.getElasticsearchConnection());

		properties = HashMapBuilder.<String, Object>put(
			"operationMode", OperationMode.REMOTE.name()
		).put(
			"remoteClusterConnectionId", _REMOTE_3_CONNECTION_ID
		).build();

		_elasticsearchConnectionManager.setConfiguration(properties);

		Assert.assertEquals(
			_remoteElasticsearchConnection3,
			_elasticsearchConnectionManager.getElasticsearchConnection());
	}

	@Test
	public void testGetRemoteElasticsearchConnectionWithConnectionId() {
		HashMap<String, Object> properties = HashMapBuilder.<String, Object>put(
			"operationMode", OperationMode.REMOTE.name()
		).put(
			"remoteClusterConnectionId", _REMOTE_2_CONNECTION_ID
		).build();

		_elasticsearchConnectionManager.setConfiguration(properties);

		Assert.assertEquals(
			_remoteElasticsearchConnection1,
			_elasticsearchConnectionManager.getElasticsearchConnection(
				_REMOTE_1_CONNECTION_ID));
	}

	@Test
	public void testGetRemoteRestHighLevelClient() {
		HashMap<String, Object> properties = HashMapBuilder.<String, Object>put(
			"operationMode", OperationMode.REMOTE.name()
		).put(
			"remoteClusterConnectionId", _REMOTE_1_CONNECTION_ID
		).build();

		_elasticsearchConnectionManager.setConfiguration(properties);

		Assert.assertEquals(
			_remoteElasticsearchConnection1.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient());

		Assert.assertEquals(
			_remoteElasticsearchConnection2.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient(
				_REMOTE_2_CONNECTION_ID));
	}

	@Test
	public void testGetRestHighLevelClientWhenConnectionNull() {
		_elasticsearchConnectionManager.setOperationMode(OperationMode.REMOTE);

		try {
			_elasticsearchConnectionManager.getRestHighLevelClient("none");

			Assert.fail();
		}
		catch (ElasticsearchConnectionNotInitializedException
					elasticsearchConnectionNotInitializedException) {

			String message =
				elasticsearchConnectionNotInitializedException.getMessage();

			Assert.assertTrue(
				message.contains("Elasticsearch connection not found"));
		}
	}

	@Test
	public void testGetRestHighLevelClientWhenOperationModeNotSet() {
		try {
			_elasticsearchConnectionManager.getRestHighLevelClient();

			Assert.fail();
		}
		catch (ElasticsearchConnectionNotInitializedException
					elasticsearchConnectionNotInitializedException) {

			String message =
				elasticsearchConnectionNotInitializedException.getMessage();

			Assert.assertTrue(
				message.contains("Elasticsearch connection not found"));
		}
	}

	@Test
	public void testGetRestHighLevelClientWhenRestClientNull() {
		_elasticsearchConnectionManager.setOperationMode(OperationMode.REMOTE);

		try {
			_elasticsearchConnectionManager.getRestHighLevelClient(
				_REMOTE_3_CONNECTION_ID);

			Assert.fail();
		}
		catch (ElasticsearchConnectionNotInitializedException
					elasticsearchConnectionNotInitializedException) {

			String message =
				elasticsearchConnectionNotInitializedException.getMessage();

			Assert.assertTrue(
				message.contains("REST high level client not found"));
		}
	}

	@Test
	public void testModifiedMustNotChangeAnyConnection() {
		Mockito.reset(
			_embeddedElasticsearchConnection, _remoteElasticsearchConnection1,
			_remoteElasticsearchConnection2, _remoteElasticsearchConnection3);

		HashMap<String, Object> properties = HashMapBuilder.<String, Object>put(
			"operationMode", OperationMode.EMBEDDED.name()
		).build();

		_elasticsearchConnectionManager.modified(properties);

		verifyNeverCloseNeverConnect(_embeddedElasticsearchConnection);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection1);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection2);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection3);

		properties = HashMapBuilder.<String, Object>put(
			"operationMode", OperationMode.REMOTE.name()
		).build();

		_elasticsearchConnectionManager.modified(properties);

		verifyNeverCloseNeverConnect(_embeddedElasticsearchConnection);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection1);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection2);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection3);
	}

	@Test
	public void testSetElasticsearchConnection() {
		verifyNeverCloseNeverConnect(_embeddedElasticsearchConnection);
		verifyConnectNeverClose(_remoteElasticsearchConnection1);
		verifyConnectNeverClose(_remoteElasticsearchConnection2);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection3);
	}

	protected ElasticsearchConnectionManager
		createElasticsearchConnectionManager(
			ElasticsearchConnection embeddedElasticsearchConnection,
			ElasticsearchConnection remoteElasticsearchConnection1,
			ElasticsearchConnection remoteElasticsearchConnection2,
			ElasticsearchConnection remoteElasticsearchConnection3) {

		ElasticsearchConnectionManager elasticsearchConnectionManager =
			new ElasticsearchConnectionManager();

		elasticsearchConnectionManager.setEmbeddedElasticsearchConnection(
			embeddedElasticsearchConnection);
		elasticsearchConnectionManager.setRemoteElasticsearchConnection(
			remoteElasticsearchConnection1);
		elasticsearchConnectionManager.setRemoteElasticsearchConnection(
			remoteElasticsearchConnection2);
		elasticsearchConnectionManager.setRemoteElasticsearchConnection(
			remoteElasticsearchConnection3);

		return elasticsearchConnectionManager;
	}

	protected void resetMockConnections() {
		Mockito.reset(
			_embeddedElasticsearchConnection, _remoteElasticsearchConnection1,
			_remoteElasticsearchConnection2, _remoteElasticsearchConnection3);

		setUpEmbeddedConnection();
		setUpRemoteConnection1();
		setUpRemoteConnection2();
		setUpRemoteConnection3();
	}

	protected void setUpEmbeddedConnection() {
		Mockito.when(
			_embeddedElasticsearchConnection.getConnectionId()
		).thenReturn(
			String.valueOf(OperationMode.EMBEDDED)
		);
		Mockito.when(
			_embeddedElasticsearchConnection.getOperationMode()
		).thenReturn(
			OperationMode.EMBEDDED
		);
		Mockito.when(
			_embeddedElasticsearchConnection.getRestHighLevelClient()
		).thenReturn(
			Mockito.mock(RestHighLevelClient.class)
		);
		Mockito.when(
			_embeddedElasticsearchConnection.isActive()
		).thenReturn(
			true
		);
	}

	protected void setUpRemoteConnection1() {
		Mockito.when(
			_remoteElasticsearchConnection1.getConnectionId()
		).thenReturn(
			_REMOTE_1_CONNECTION_ID
		);
		Mockito.when(
			_remoteElasticsearchConnection1.getOperationMode()
		).thenReturn(
			OperationMode.REMOTE
		);
		Mockito.when(
			_remoteElasticsearchConnection1.getRestHighLevelClient()
		).thenReturn(
			Mockito.mock(RestHighLevelClient.class)
		);
		Mockito.when(
			_remoteElasticsearchConnection1.isActive()
		).thenReturn(
			true
		);
	}

	protected void setUpRemoteConnection2() {
		Mockito.when(
			_remoteElasticsearchConnection2.getConnectionId()
		).thenReturn(
			_REMOTE_2_CONNECTION_ID
		);
		Mockito.when(
			_remoteElasticsearchConnection2.getOperationMode()
		).thenReturn(
			OperationMode.REMOTE
		);
		Mockito.when(
			_remoteElasticsearchConnection2.getRestHighLevelClient()
		).thenReturn(
			Mockito.mock(RestHighLevelClient.class)
		);
		Mockito.when(
			_remoteElasticsearchConnection2.isActive()
		).thenReturn(
			true
		);
	}

	protected void setUpRemoteConnection3() {
		Mockito.when(
			_remoteElasticsearchConnection3.getConnectionId()
		).thenReturn(
			_REMOTE_3_CONNECTION_ID
		);
		Mockito.when(
			_remoteElasticsearchConnection3.getOperationMode()
		).thenReturn(
			OperationMode.REMOTE
		);
		Mockito.when(
			_remoteElasticsearchConnection3.getRestHighLevelClient()
		).thenReturn(
			null
		);
		Mockito.when(
			_remoteElasticsearchConnection3.isActive()
		).thenReturn(
			false
		);
	}

	protected void verifyConnectNeverClose(
		ElasticsearchConnection elasticsearchConnection) {

		Mockito.verify(
			elasticsearchConnection, Mockito.never()
		).close();

		Mockito.verify(
			elasticsearchConnection
		).connect();
	}

	protected void verifyNeverCloseNeverConnect(
		ElasticsearchConnection elasticsearchConnection) {

		Mockito.verify(
			elasticsearchConnection, Mockito.never()
		).close();

		Mockito.verify(
			elasticsearchConnection, Mockito.never()
		).connect();
	}

	private static final String _REMOTE_1_CONNECTION_ID = "remote 1";

	private static final String _REMOTE_2_CONNECTION_ID = "remote 2";

	private static final String _REMOTE_3_CONNECTION_ID = "remote 3";

	private ElasticsearchConnectionManager _elasticsearchConnectionManager;

	@Mock
	private ElasticsearchConnection _embeddedElasticsearchConnection;

	@Mock
	private ElasticsearchConnection _remoteElasticsearchConnection1;

	@Mock
	private ElasticsearchConnection _remoteElasticsearchConnection2;

	@Mock
	private ElasticsearchConnection _remoteElasticsearchConnection3;

}