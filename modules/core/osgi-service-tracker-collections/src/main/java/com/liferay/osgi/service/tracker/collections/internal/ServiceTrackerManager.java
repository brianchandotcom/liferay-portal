/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections.internal;

import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Shuyang Zhou
 */
public class ServiceTrackerManager {

	public ServiceTrackerManager(
		ServiceTracker<?, ?> serviceTracker, boolean trackAllServices) {

		_serviceTracker = serviceTracker;
		_trackAllServices = trackAllServices;
	}

	public void close() {
		boolean opened = _opened;

		if (!opened) {
			return;
		}

		boolean closed = _closed;

		if (closed) {
			return;
		}

		synchronized (this) {
			if (!_opened || _closed) {
				return;
			}

			_serviceTracker.close();

			_closed = true;
		}
	}

	public void open() {
		boolean opened = _opened;

		if (opened) {
			return;
		}

		synchronized (this) {
			if (_opened) {
				return;
			}

			_serviceTracker.open(_trackAllServices);

			_opened = true;
		}
	}

	private volatile boolean _closed;
	private volatile boolean _opened;
	private final ServiceTracker<?, ?> _serviceTracker;
	private final boolean _trackAllServices;

}