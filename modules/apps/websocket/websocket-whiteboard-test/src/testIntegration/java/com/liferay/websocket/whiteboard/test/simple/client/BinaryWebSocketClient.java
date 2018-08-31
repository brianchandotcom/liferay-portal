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

package com.liferay.websocket.whiteboard.test.simple.client;

import java.io.IOException;

import java.nio.ByteBuffer;

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
public class BinaryWebSocketClient {

	public BinaryWebSocketClient(BlockingQueue<ByteBuffer> blockingQueue) {
		_blockingQueue = blockingQueue;
	}

	@OnMessage
	public void onMessage(ByteBuffer byteBuffer, Session session)
		throws InterruptedException {

		_blockingQueue.put(byteBuffer);
	}

	@OnOpen
	public void onOpen(Session session) {
		_session = session;
	}

	public void sendMessage(ByteBuffer byteBuffer) throws IOException {
		RemoteEndpoint.Basic basic = _session.getBasicRemote();

		basic.sendBinary(byteBuffer);
	}

	private final BlockingQueue<ByteBuffer> _blockingQueue;
	private Session _session;

}