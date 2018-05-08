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

package com.liferay.petra.json.web.service.client.internal;

import java.util.concurrent.TimeUnit;

import org.apache.http.nio.conn.NHttpClientConnectionManager;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class IdleConnectionMonitorThread extends Thread {

	public IdleConnectionMonitorThread(
		NHttpClientConnectionManager nHttpClientConnectionManager) {

		_nHttpClientConnectionManager = nHttpClientConnectionManager;
	}

	@Override
	public void run() {
		try {
			while (!_shutdown) {
				synchronized (this) {
					wait(5000);

					_nHttpClientConnectionManager.closeExpiredConnections();

					_nHttpClientConnectionManager.closeIdleConnections(
						30, TimeUnit.SECONDS);
				}
			}
		}
		catch (InterruptedException ie) {
		}
	}

	public void shutdown() {
		_shutdown = true;

		synchronized (this) {
			notifyAll();
		}
	}

	private final NHttpClientConnectionManager _nHttpClientConnectionManager;
	private volatile boolean _shutdown;

}