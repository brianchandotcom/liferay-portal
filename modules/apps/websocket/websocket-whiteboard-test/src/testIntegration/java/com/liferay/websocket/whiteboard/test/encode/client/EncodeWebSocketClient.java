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