/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vendel Toreki
 */
public class BatchEnginePortletDataHandlerRegistryUtil {

	public static BatchEnginePortletDataHandler getByModelClassName(
		String modelClassName) {

		String portletId = _findPortletIdByClassName(modelClassName);

		if (portletId == null) {
			return null;
		}

		return _batchEnginePortletDataHandlers.get(portletId);
	}

	public static BatchEnginePortletDataHandler getByPortletId(
		String portletId) {

		return _batchEnginePortletDataHandlers.get(portletId);
	}

	public static boolean hasByModelClassName(String modelClassName) {
		if (_findPortletIdByClassName(modelClassName) != null) {
			return true;
		}

		return false;
	}

	protected static void put(
		String portletId, BatchEnginePortletDataHandler handler) {

		_batchEnginePortletDataHandlers.put(portletId, handler);
	}

	protected static void remove(String portletId) {
		_batchEnginePortletDataHandlers.remove(portletId);
	}

	private static String _findPortletIdByClassName(String modelClassName) {
		for (Map.Entry<String, BatchEnginePortletDataHandler> entry :
				_batchEnginePortletDataHandlers.entrySet()) {

			BatchEnginePortletDataHandler batchEnginePortletDataHandler =
				entry.getValue();

			String[] classNames = batchEnginePortletDataHandler.getClassNames();

			for (String className : classNames) {
				if (className.equals(modelClassName)) {
					return entry.getKey();
				}
			}
		}

		return null;
	}

	private static final Map<String, BatchEnginePortletDataHandler>
		_batchEnginePortletDataHandlers = new ConcurrentHashMap<>();

}