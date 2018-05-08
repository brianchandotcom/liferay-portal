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

package com.liferay.websocket.whiteboard.test.encode.endpoint;

import com.liferay.websocket.whiteboard.test.encode.data.Example;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

/**
 * @author Cristina González
 */
public class EncodeWebSocketEndpoint extends Endpoint {

	@Override
	public void onOpen(final Session session, EndpointConfig endpointConfig) {
		session.addMessageHandler(
			new MessageHandler.Whole<Example>() {

				@Override
				public void onMessage(Example data) {
					try {
						RemoteEndpoint.Basic basic = session.getBasicRemote();

						basic.sendObject(data);
					}
					catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}
					catch (EncodeException ee) {
						throw new RuntimeException(ee);
					}
				}

			});
	}

}