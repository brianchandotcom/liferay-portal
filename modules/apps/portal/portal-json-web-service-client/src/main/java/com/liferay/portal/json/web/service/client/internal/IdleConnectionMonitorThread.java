/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.web.service.client.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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
		catch (InterruptedException interruptedException) {
			if (_log.isDebugEnabled()) {
				_log.debug(interruptedException);
			}
		}
	}

	public void shutdown() {
		_shutdown = true;

		synchronized (this) {
			notifyAll();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IdleConnectionMonitorThread.class);

	private final NHttpClientConnectionManager _nHttpClientConnectionManager;
	private volatile boolean _shutdown;

}