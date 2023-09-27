/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.websocket.whiteboard.test.simple.client;

import java.io.IOException;

import java.util.concurrent.BlockingQueue;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

/**
 * @author Cristina González
 */
@ClientEndpoint
public class TextWebSocketClient {

	public TextWebSocketClient(BlockingQueue<String> blockingQueue) {
		_blockingQueue = blockingQueue;
	}

	@OnMessage
	public void onMessage(String text, Session session)
		throws InterruptedException {

		_blockingQueue.put(text);
	}

	@OnOpen
	public void onOpen(Session session) {
		_session = session;
	}

	public void sendMessage(String text) throws IOException {
		RemoteEndpoint.Basic basic = _session.getBasicRemote();

		basic.sendText(text);
	}

	private final BlockingQueue<String> _blockingQueue;
	private Session _session;

}