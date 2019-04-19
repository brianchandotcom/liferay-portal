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

package com.liferay.websocket.whiteboard.test.simple.endpoint;

import java.io.IOException;

import java.nio.ByteBuffer;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

/**
 * @author Cristina González
 */
public class SimpleWebSocketEndpoint extends Endpoint {

	@Override
	public void onOpen(final Session session, EndpointConfig endpointConfig) {
		session.addMessageHandler(
			new MessageHandler.Whole<ByteBuffer>() {

				@Override
				public void onMessage(ByteBuffer byteBuffer) {
					try {
						RemoteEndpoint.Basic basic = session.getBasicRemote();

						basic.sendBinary(byteBuffer);
					}
					catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}
				}

			});
		session.addMessageHandler(
			new MessageHandler.Whole<String>() {

				@Override
				public void onMessage(String text) {
					try {
						RemoteEndpoint.Basic basic = session.getBasicRemote();

						basic.sendText(text);
					}
					catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}
				}

			});
	}

}