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

package com.liferay.websocket.whiteboard.test;

import com.liferay.websocket.whiteboard.test.encode.client.EncodeWebSocketClient;
import com.liferay.websocket.whiteboard.test.encode.data.Example;

import java.net.URI;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class EncodeWebsocketEndpointTest {

	@RunAsClient
	@Test
	public void testSendObjectAndReceiveTheSame() throws Exception {
		WebSocketContainer webSocketContainer =
			ContainerProvider.getWebSocketContainer();

		SynchronousQueue<Example> synchronousQueue = new SynchronousQueue<>();

		EncodeWebSocketClient encodeWebSocketClient = new EncodeWebSocketClient(
			synchronousQueue);

		URI uri = new URI("ws://localhost:8080/o/websocket/decoder");

		webSocketContainer.connectToServer(encodeWebSocketClient, uri);

		encodeWebSocketClient.sendMessage(new Example(2, "echo"));

		Example example = synchronousQueue.poll(1, TimeUnit.HOURS);

		Assert.assertEquals("echo", example.getData());
		Assert.assertEquals(2, example.getNumber());
	}

}