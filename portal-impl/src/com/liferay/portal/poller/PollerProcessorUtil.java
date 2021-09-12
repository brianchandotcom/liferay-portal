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

package com.liferay.portal.poller;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.poller.PollerProcessor;

/**
 * @author Brian Wing Shun Chan
 */
public class PollerProcessorUtil {

	public static PollerProcessor getPollerProcessor(String portletId) {
		return _pollerProcessors.getService(portletId);
	}

	private PollerProcessorUtil() {
	}

	private static final ServiceTrackerMap<String, PollerProcessor>
		_pollerProcessors = ServiceTrackerMapFactory.openSingleValueMap(
			SystemBundleUtil.getBundleContext(), PollerProcessor.class,
			"javax.portlet.name");

}