/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.websocket.whiteboard.test.encode.client;

import com.liferay.websocket.whiteboard.test.encode.data.Example;
import com.liferay.websocket.whiteboard.test.encode.data.ExampleDecoder;
import com.liferay.websocket.whiteboard.test.encode.data.ExampleEncoder;

import java.io.IOException;

import java.util.concurrent.BlockingQueue;

import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

/**
 * @author Cristina González
 */
@ClientEndpoint(
	decoders = ExampleDecoder.class, encoders = ExampleEncoder.class
)
public class EncodeWebSocketClient {

	public EncodeWebSocketClient(BlockingQueue<Example> blockingQueue) {
		_blockingQueue = blockingQueue;
	}

	@OnMessage
	public void onMessage(Example example, Session session)
		throws InterruptedException {

		_blockingQueue.put(example);
	}

	@OnOpen
	public void onOpen(Session session) {
		_session = session;
	}

	public void sendMessage(Example example)
		throws EncodeException, IOException {

		RemoteEndpoint.Basic basic = _session.getBasicRemote();

		basic.sendObject(example);
	}

	private final BlockingQueue<Example> _blockingQueue;
	private Session _session;

}