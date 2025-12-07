/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vendel Toreki
 * @author Petteri Karttunen
 */
public class BatchEnginePortletDataHandlerRegistryUtil {

	public static BatchEnginePortletDataHandler getByClassName(
		long companyId, String className) {

		return getByPortletId(companyId, _getPortletId(companyId, className));
	}

	public static BatchEnginePortletDataHandler getByPortletId(
		long companyId, String portletId) {

		Map<String, BatchEnginePortletDataHandler>
			batchEnginePortletDataHandlerMap =
				_batchEnginePortletDataHandlersMaps.get(companyId);

		if (batchEnginePortletDataHandlerMap == null) {
			return null;
		}

		return batchEnginePortletDataHandlerMap.get(portletId);
	}

	public static boolean hasByClassName(String className, long companyId) {
		Map<String, String> classNamesToPortletIdsMap =
			_classNamesToPortletIdsMaps.get(companyId);

		if (classNamesToPortletIdsMap == null) {
			return false;
		}

		return classNamesToPortletIdsMap.containsKey(className);
	}

	protected static void put(
		BatchEnginePortletDataHandler batchEnginePortletDataHandler,
		long companyId, String portletId) {

		Map<String, BatchEnginePortletDataHandler>
			batchEnginePortletDataHandlersMap =
				_batchEnginePortletDataHandlersMaps.computeIfAbsent(
					companyId, key -> new ConcurrentHashMap<>());

		batchEnginePortletDataHandlersMap.put(
			portletId, batchEnginePortletDataHandler);

		Map<String, String> classNamesToPortletIdsMap =
			_classNamesToPortletIdsMaps.computeIfAbsent(
				companyId, key -> new ConcurrentHashMap<>());

		for (String className : batchEnginePortletDataHandler.getClassNames()) {
			classNamesToPortletIdsMap.put(className, portletId);
		}
	}

	protected static void remove(long companyId, String portletId) {
		_batchEnginePortletDataHandlersMaps.computeIfPresent(
			companyId,
			(key, batchEnginePortletDataHandlersMap) -> {
				BatchEnginePortletDataHandler batchEnginePortletDataHandler =
					batchEnginePortletDataHandlersMap.remove(portletId);

				if (batchEnginePortletDataHandler != null) {
					_removeClassNameMappings(
						batchEnginePortletDataHandler, companyId, portletId);
				}

				return batchEnginePortletDataHandlersMap.isEmpty() ? null :
					batchEnginePortletDataHandlersMap;
			});
	}

	private static String _getPortletId(long companyId, String className) {
		Map<String, String> classNamesToPortletIdsMap =
			_classNamesToPortletIdsMaps.get(companyId);

		if (classNamesToPortletIdsMap == null) {
			return null;
		}

		return classNamesToPortletIdsMap.get(className);
	}

	private static void _removeClassNameMappings(
		BatchEnginePortletDataHandler batchEnginePortletDataHandler,
		long companyId, String portletId) {

		_classNamesToPortletIdsMaps.computeIfPresent(
			companyId,
			(key, classNamesToPortletIdsMap) -> {
				for (String className :
						batchEnginePortletDataHandler.getClassNames()) {

					classNamesToPortletIdsMap.remove(className, portletId);
				}

				return classNamesToPortletIdsMap.isEmpty() ? null :
					classNamesToPortletIdsMap;
			});
	}

	private static final Map<Long, Map<String, BatchEnginePortletDataHandler>>
		_batchEnginePortletDataHandlersMaps = new ConcurrentHashMap<>();
	private static final Map<Long, Map<String, String>>
		_classNamesToPortletIdsMaps = new ConcurrentHashMap<>();

}