/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Vendel Toreki
 */
public class BatchEnginePortletDataHandlerRegistry {

	public static BatchEnginePortletDataHandler
		getBatchEnginePortletDataHandler(String deletionSystemEventClassName) {

		String portletId = deletionSystemEventClassNamePortletIds.get(
			deletionSystemEventClassName);

		if (portletId == null) {
			return null;
		}

		PortletDataHandler portletDataHandler = _serviceTrackerMap.getService(
			portletId);

		if (portletDataHandler instanceof
				BatchEnginePortletDataHandler batchEnginePortletDataHandler) {

			return batchEnginePortletDataHandler;
		}

		return null;
	}

	public static boolean hasBatchEnginePortletDataHandler(
		String deletionSystemEventClassName) {

		String portletId = deletionSystemEventClassNamePortletIds.get(
			deletionSystemEventClassName);

		if (portletId == null) {
			return false;
		}

		return _serviceTrackerMap.containsKey(portletId);
	}

	protected static final Map<String, String>
		deletionSystemEventClassNamePortletIds = new ConcurrentHashMap<>();

	private static final ServiceTrackerMap<String, PortletDataHandler>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			BatchEnginePortletDataHandlerRegistry.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PortletDataHandler.class, "jakarta.portlet.name");
	}

}