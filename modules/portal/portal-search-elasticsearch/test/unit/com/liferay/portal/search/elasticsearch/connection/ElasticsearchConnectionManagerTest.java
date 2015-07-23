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

		properties.put("operationMode", "EMBEDDED");

		_elasticsearchConnectionManager.activate(properties);

		verifyNeitherCloseNorConnect(_embeddedElasticsearchConnection);
		verifyNeitherCloseNorConnect(_remoteElasticsearchConnection);
	}

	@Test
	public void testActivateThenConnect() {
		HashMap<String, Object> properties = new HashMap<>();

		properties.put("operationMode", "EMBEDDED");

		_elasticsearchConnectionManager.activate(properties);
		_elasticsearchConnectionManager.connect();

		verifyConnectNotClose(_embeddedElasticsearchConnection);
		verifyNeitherCloseNorConnect(_remoteElasticsearchConnection);
	}

	@Test
	public void testGetClient() {
		modify(OperationMode.EMBEDDED);

		_elasticsearchConnectionManager.getClient();

		Mockito.verify(_embeddedElasticsearchConnection).getClient();

		modify(OperationMode.REMOTE);

		_elasticsearchConnectionManager.getClient();

		Mockito.verify(_remoteElasticsearchConnection).getClient();
	}

	@Test
	public void testGetClientWhenOperationModeNotSetYet() {
		try {
			_elasticsearchConnectionManager.getClient();

			Assert.fail("Client available when operation mode not set yet");
		}
		catch (ElasticsearchConnectionNotInitializedException ecnie) {
		}
	}

	@Test
	public void testModifiedClosesOldConnectionAndOpensNewConnection() {
		HashMap<String, Object> properties = new HashMap<>();

		properties.put("operationMode", "EMBEDDED");

		_elasticsearchConnectionManager.activate(properties);

		resetMockConnections();

		properties.put("operationMode", "REMOTE");

		_elasticsearchConnectionManager.modified(properties);

		verifyCloseNotConnect(_embeddedElasticsearchConnection);
		verifyConnectNotClose(_remoteElasticsearchConnection);
	}

	@Test
	public void testSetOperationModeToSameMustNotTouchAnyConnection() {
		modify(OperationMode.REMOTE);

		resetMockConnections();

		modify(OperationMode.REMOTE);

		verifyNeitherCloseNorConnect(_embeddedElasticsearchConnection);
		verifyNeitherCloseNorConnect(_remoteElasticsearchConnection);
	}

	@Test
	public void testSetOperationModeToUnavailable() {
		_elasticsearchConnectionManager.unsetElasticsearchConnection(
			_remoteElasticsearchConnection);

		verifyCloseNotConnect(_remoteElasticsearchConnection);
		verifyNeitherCloseNorConnect(_embeddedElasticsearchConnection);

		resetMockConnections();

		try {
			modify(OperationMode.REMOTE);

			Assert.fail("Allowed setting an unavailable operation mode");
		}
		catch (MissingOperationModeException mome) {
			String message = mome.getMessage();

			Assert.assertTrue(
				message.contains(String.valueOf(OperationMode.REMOTE)));
		}

		verifyNeitherCloseNorConnect(_embeddedElasticsearchConnection);
		verifyNeitherCloseNorConnect(_remoteElasticsearchConnection);
	}

	@Test
	public void testToggleOperationMode() {
		modify(OperationMode.EMBEDDED);

		verifyConnectNotClose(_embeddedElasticsearchConnection);
		verifyNeitherCloseNorConnect(_remoteElasticsearchConnection);

		resetMockConnections();

		modify(OperationMode.REMOTE);

		verifyCloseNotConnect(_embeddedElasticsearchConnection);
		verifyConnectNotClose(_remoteElasticsearchConnection);

		resetMockConnections();

		modify(OperationMode.EMBEDDED);

		verifyCloseNotConnect(_remoteElasticsearchConnection);
		verifyConnectNotClose(_embeddedElasticsearchConnection);
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

		verifyCloseNotConnect(_embeddedElasticsearchConnection);
		verifyConnectNotClose(_remoteElasticsearchConnection);
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

			Assert.fail("Changed operation mode although connect failed");
		}
		catch (IllegalStateException ise) {
		}

		Assert.assertSame(
			_embeddedElasticsearchConnection,
			_elasticsearchConnectionManager.getElasticsearchConnection());

		verifyConnectNotClose(_remoteElasticsearchConnection);
		verifyNeitherCloseNorConnect(_embeddedElasticsearchConnection);
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

	protected void verifyCloseNotConnect(
		ElasticsearchConnection elasticsearchConnection) {

		Mockito.verify(
			elasticsearchConnection
		).close();
		Mockito.verify(
			elasticsearchConnection, Mockito.never()
		).connect();
	}

	protected void verifyConnectNotClose(
		ElasticsearchConnection elasticsearchConnection) {

		Mockito.verify(
			elasticsearchConnection, Mockito.never()
		).close();
		Mockito.verify(
			elasticsearchConnection
		).connect();
	}

	protected void verifyNeitherCloseNorConnect(
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