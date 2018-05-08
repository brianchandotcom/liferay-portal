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

import com.liferay.websocket.whiteboard.test.simple.client.BinaryWebSocketClient;
import com.liferay.websocket.whiteboard.test.simple.client.TextWebSocketClient;

import java.net.URI;
import java.net.URL;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

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
public class SimpleWebSocketEndpointTest {

	@RunAsClient
	@Test
	public void testSendBinaryMessageAndReceiveTheSame() throws Exception {
		WebSocketContainer webSocketContainer =
			ContainerProvider.getWebSocketContainer();

		SynchronousQueue<ByteBuffer> synchronousQueue =
			new SynchronousQueue<>();

		BinaryWebSocketClient binaryWebSocketClient = new BinaryWebSocketClient(
			synchronousQueue);

		StringBuilder sb = new StringBuilder();

		sb.append("ws://");
		sb.append(_url.getHost());
		sb.append(":");
		sb.append(_url.getPort());
		sb.append("/o/websocket/test");

		URI uri = new URI(sb.toString());

		webSocketContainer.connectToServer(binaryWebSocketClient, uri);

		binaryWebSocketClient.sendMessage(ByteBuffer.wrap("echo".getBytes()));

		ByteBuffer byteBuffer = synchronousQueue.poll(1, TimeUnit.HOURS);

		Assert.assertEquals(
			"echo", new String(byteBuffer.array(), Charset.forName("UTF-8")));
	}

	@RunAsClient
	@Test
	public void testSendMessageAndReceiveTheSame() throws Exception {
		WebSocketContainer webSocketContainer =
			ContainerProvider.getWebSocketContainer();

		SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

		TextWebSocketClient textWebSocketClient = new TextWebSocketClient(
			synchronousQueue);

		StringBuilder sb = new StringBuilder();

		sb.append("ws://");
		sb.append(_url.getHost());
		sb.append(":");
		sb.append(_url.getPort());
		sb.append("/o/websocket/test");

		URI uri = new URI(sb.toString());

		webSocketContainer.connectToServer(textWebSocketClient, uri);

		textWebSocketClient.sendMessage("echo");

		Assert.assertEquals("echo", synchronousQueue.poll(1, TimeUnit.HOURS));
	}

	@ArquillianResource
	private URL _url;

}