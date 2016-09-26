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

package com.liferay.websocket.whiteboard;

import java.io.IOException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.osgi.framework.ServiceObjects;
import org.osgi.service.log.LogService;

/**
 * @author Cristina González Castellano
 */
class EndpointWrapper extends Endpoint {

	public EndpointWrapper(
		ServiceObjects<Endpoint> serviceObjects, LogService log) {

		_serviceObjects = serviceObjects;
		_endpoint = serviceObjects.getService();
		_log = log;
	}

	protected void close() {
		_closed = true;

		Iterator<Session> iterator = _sessions.iterator();

		while (iterator.hasNext()) {
			Session session = iterator.next();
			iterator.remove();

			try {
				CloseReason closeReason = new CloseReason(
					CloseReason.CloseCodes.GOING_AWAY, "Service has gone away");

				session.close(closeReason);
				_endpoint.onClose(session, closeReason);

				_serviceObjects.ungetService(_endpoint);
			}
			catch (IOException ioe) {
				_log.log(
					LogService.LOG_ERROR,
					"It is not possible close the session", ioe);
			}
		}
	}

	@Override
	public void onClose(Session session, CloseReason closeReason) {
		if (_closed) {
			return;
		}

		_endpoint.onClose(session, closeReason);
		_sessions.remove(session);
		_serviceObjects.ungetService(_endpoint);
	}

	@Override
	public void onError(Session session, Throwable throwable) {
		if (_closed) {
			return;
		}

		_endpoint.onError(session, throwable);
	}

	@Override
	public void onOpen(Session session, EndpointConfig endpointConfig) {
		if (_closed) {
			return;
		}

		_endpoint.onOpen(session, endpointConfig);
		_sessions.add(session);
	}

	private final LogService _log;
	private volatile boolean _closed = false;
	private final Endpoint _endpoint;
	private final ServiceObjects<Endpoint> _serviceObjects;
	private final Set<Session> _sessions = new HashSet<>();

}