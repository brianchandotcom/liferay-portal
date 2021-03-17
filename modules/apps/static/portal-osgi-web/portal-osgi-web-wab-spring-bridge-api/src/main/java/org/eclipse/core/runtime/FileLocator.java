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

import org.eclipse.osgi.service.urlconversion.URLConverter;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * This is a dummy copy of what's needed to make spring annotation processing
 * work in OSGi.
 *
 * @author Raymond Augé
 */
public class FileLocator {

	public static URL resolve(URL url) throws IOException {
		URLConverter urlConverter = _serviceTrackerMap.getService(
			url.getProtocol());

		if (urlConverter == null) {
			return url;
		}

		return urlConverter.resolve(url);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	protected void deactivate() {
	}

	private static final ServiceTrackerMap<String, URLConverter>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(FileLocator.class);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundle.getBundleContext(), URLConverter.class, "protocol");
	}

}