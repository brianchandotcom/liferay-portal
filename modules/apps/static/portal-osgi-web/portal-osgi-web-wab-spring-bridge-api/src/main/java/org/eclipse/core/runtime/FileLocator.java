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

package org.eclipse.core.runtime;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.io.IOException;

import java.net.URL;

import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.osgi.service.urlconversion.URLConverter;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * This is a dummy copy of what's needed to make spring annotation processing
 * work in OSGi.
 *
 * @author Raymond Augé
 */
@Component(immediate = true)
public class FileLocator {

	public static URL resolve(URL url) throws IOException {
		String protocol = url.getProtocol();

		_lock.lock();

		try {
			if (_serviceTrackerMap == null) {
				Bundle bundle = FrameworkUtil.getBundle(FileLocator.class);

				_serviceTrackerMap =
					ServiceTrackerMapFactory.openSingleValueMap(
						bundle.getBundleContext(), URLConverter.class,
						"protocol");
			}

			URLConverter converter = _serviceTrackerMap.getService(protocol);

			if (converter == null) {
				return url;
			}

			return converter.resolve(url);
		}
		finally {
			_lock.unlock();
		}
	}

	@Deactivate
	protected void deactivate() {
		_lock.lock();

		try {
			if (_serviceTrackerMap != null) {
				_serviceTrackerMap.close();

				_serviceTrackerMap = null;
			}
		}
		finally {
			_lock.unlock();
		}
	}

	private static final ReentrantLock _lock = new ReentrantLock(true);
	private static volatile ServiceTrackerMap<String, URLConverter>
		_serviceTrackerMap;

}