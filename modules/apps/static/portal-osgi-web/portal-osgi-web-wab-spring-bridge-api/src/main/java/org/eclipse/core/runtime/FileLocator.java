/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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