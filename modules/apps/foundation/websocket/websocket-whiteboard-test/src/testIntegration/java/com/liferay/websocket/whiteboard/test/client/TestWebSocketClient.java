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

package com.liferay.websocket.whiteboard.test.client;

import java.io.IOException;

import java.util.concurrent.BlockingQueue;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

/**
 * @author Cristina González
 */
@ClientEndpoint
public class TestWebSocketClient {

	public TestWebSocketClient(BlockingQueue<String> textQueue) {
		_textQueue = textQueue;
	}

	@OnOpen
	public void onOpen(Session session) {
		_session = session;
	}

	@OnMessage
	public void onText(String text, Session session)
		throws InterruptedException {

		_textQueue.put(text);
	}

	public void sendText(String text) throws IOException {
		Basic basic = _session.getBasicRemote();

		basic.sendText(text);
	}

	private Session _session;
	private final BlockingQueue<String> _textQueue;

}