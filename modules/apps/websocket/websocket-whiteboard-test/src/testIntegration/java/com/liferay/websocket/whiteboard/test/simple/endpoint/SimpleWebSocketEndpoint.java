/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					catch (IOException ioException) {
						throw new RuntimeException(ioException);
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
					catch (IOException ioException) {
						throw new RuntimeException(ioException);
					}
				}

			});
	}

}