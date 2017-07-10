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

package com.liferay.portal.search.elasticsearch.connection;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
public class ElasticsearchConnectionManagerTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		resetMockConnections();

		_elasticsearchConnectionManager = new ElasticsearchConnectionManager();

		_elasticsearchConnectionManager.setEmbeddedElasticsearchConnection(
			_embeddedElasticsearchConnection);
		_elasticsearchConnectionManager.setRemoteElasticsearchConnection(
			_remoteElasticsearchConnection);
	}

	@Test
	public void testActivateMustNotOpenAnyConnection() {
		HashMap<String, Object> properties = new HashMap<>();

		properties.put("operationMode", OperationMode.EMBEDDED.name());

		_elasticsearchConnectionManager.activate(properties);

		verifyNeverCloseNeverConnect(_embeddedElasticsearchConnection);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection);
	}

	@Test
	public void testActivateThenConnect() {
		HashMap<String, Object> properties = new HashMap<>();

		properties.put("operationMode", OperationMode.EMBEDDED.name());

		_elasticsearchConnectionManager.activate(properties);

		_elasticsearchConnectionManager.connect();

		verifyConnectNeverClose(_embeddedElasticsearchConnection);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection);
	}

	@Test
	public void testGetClient() {
		modify(OperationMode.EMBEDDED);

		_elasticsearchConnectionManager.getClient();

		Mockito.verify(
			_embeddedElasticsearchConnection
		).getClient();

		modify(OperationMode.REMOTE);

		_elasticsearchConnectionManager.getClient();

		Mockito.verify(
			_remoteElasticsearchConnection
		).getClient();
	}

	@Test
	public void testGetClientWhenOperationModeNotSet() {
		try {
			_elasticsearchConnectionManager.getClient();

			Assert.fail();
		}
		catch (ElasticsearchConnectionNotInitializedException ecnie) {
		}
	}

	@Test
	public void testSetModifiedOperationModeResetsConnection() {
		HashMap<String, Object> properties = new HashMap<>();

		properties.put("operationMode", OperationMode.EMBEDDED.name());

		_elasticsearchConnectionManager.activate(properties);

		resetMockConnections();

		properties.put("operationMode", OperationMode.REMOTE.name());

		_elasticsearchConnectionManager.modified(properties);

		verifyCloseNeverConnect(_embeddedElasticsearchConnection);
		verifyConnectNeverClose(_remoteElasticsearchConnection);
	}

	@Test
	public void testSetOperationModeToUnavailable() {
		_elasticsearchConnectionManager.unsetElasticsearchConnection(
			_remoteElasticsearchConnection);

		verifyCloseNeverConnect(_remoteElasticsearchConnection);
		verifyNeverCloseNeverConnect(_embeddedElasticsearchConnection);

		resetMockConnections();

		try {
			modify(OperationMode.REMOTE);

			Assert.fail();
		}
		catch (MissingOperationModeException mome) {
			String message = mome.getMessage();

			Assert.assertTrue(
				message.contains(String.valueOf(OperationMode.REMOTE)));
		}

		verifyNeverCloseNeverConnect(_embeddedElasticsearchConnection);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection);
	}

	@Test
	public void testSetSameOperationModeMustNotResetConnection() {
		modify(OperationMode.REMOTE);

		resetMockConnections();

		modify(OperationMode.REMOTE);

		verifyNeverCloseNeverConnect(_embeddedElasticsearchConnection);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection);
	}

	@Test
	public void testToggleOperationMode() {
		modify(OperationMode.EMBEDDED);

		verifyConnectNeverClose(_embeddedElasticsearchConnection);
		verifyNeverCloseNeverConnect(_remoteElasticsearchConnection);

		resetMockConnections();

		modify(OperationMode.REMOTE);

		verifyCloseNeverConnect(_embeddedElasticsearchConnection);
		verifyConnectNeverClose(_remoteElasticsearchConnection);

		resetMockConnections();

		modify(OperationMode.EMBEDDED);

		verifyCloseNeverConnect(_remoteElasticsearchConnection);
		verifyConnectNeverClose(_embeddedElasticsearchConnection);
	}

	@Test
	public void testUnableToCloseOldConnectionUseNewConnectionAnyway() {
		modify(OperationMode.EMBEDDED);

		resetMockConnections();

		Mockito.doThrow(
			IllegalStateException.class
		).when(
			_embeddedElasticsearchConnection
		).close();

		modify(OperationMode.REMOTE);

		Assert.assertSame(
			_remoteElasticsearchConnection,
			_elasticsearchConnectionManager.getElasticsearchConnection());

		verifyCloseNeverConnect(_embeddedElasticsearchConnection);
		verifyConnectNeverClose(_remoteElasticsearchConnection);
	}

	@Test
	public void testUnableToOpenNewConnectionStayWithOldConnection() {
		modify(OperationMode.EMBEDDED);

		resetMockConnections();

		Mockito.doThrow(
			IllegalStateException.class
		).when(
			_remoteElasticsearchConnection
		).connect();

		try {
			modify(OperationMode.REMOTE);

			Assert.fail();
		}
		catch (IllegalStateException ise) {
		}

		Assert.assertSame(
			_embeddedElasticsearchConnection,
			_elasticsearchConnectionManager.getElasticsearchConnection());

		verifyConnectNeverClose(_remoteElasticsearchConnection);
		verifyNeverCloseNeverConnect(_embeddedElasticsearchConnection);
	}

	protected void modify(OperationMode operationMode) {
		_elasticsearchConnectionManager.modify(operationMode);
	}

	protected void resetMockConnections() {
		Mockito.reset(
			_embeddedElasticsearchConnection, _remoteElasticsearchConnection);

		Mockito.when(
			_embeddedElasticsearchConnection.getOperationMode()
		).thenReturn(
			OperationMode.EMBEDDED
		);
		Mockito.when(
			_remoteElasticsearchConnection.getOperationMode()
		).thenReturn(
			OperationMode.REMOTE
		);
	}

	protected void verifyCloseNeverConnect(
		ElasticsearchConnection elasticsearchConnection) {

		Mockito.verify(
			elasticsearchConnection
		).close();

		Mockito.verify(
			elasticsearchConnection, Mockito.never()
		).connect();
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

	private ElasticsearchConnectionManager _elasticsearchConnectionManager;

	@Mock
	private ElasticsearchConnection _embeddedElasticsearchConnection;

	@Mock
	private ElasticsearchConnection _remoteElasticsearchConnection;

}