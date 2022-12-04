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