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

import com.liferay.websocket.whiteboard.test.client.TestWebSocketClient;

import java.net.URI;
import java.net.URL;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class TestWebSocketEndpointTest {

	@RunAsClient
	@Test
	public void testSendMessageAndReceiveTheSame() throws Exception {
		WebSocketContainer webSocketContainer =
			ContainerProvider.getWebSocketContainer();

		SynchronousQueue<String> textQueue = new SynchronousQueue<>();

		TestWebSocketClient testWebSocketClient = new TestWebSocketClient(
			textQueue);

		StringBuilder sb = new StringBuilder();

		sb.append("ws://");
		sb.append(_url.getHost());
		sb.append(":");
		sb.append(_url.getPort());
		sb.append("/o/websocket/test");

		URI uri = new URI(sb.toString());

		webSocketContainer.connectToServer(testWebSocketClient, uri);

		testWebSocketClient.sendText("echo");

		Assert.assertEquals("echo", textQueue.poll(1, TimeUnit.HOURS));
	}

	@ArquillianResource
	private URL _url;

}