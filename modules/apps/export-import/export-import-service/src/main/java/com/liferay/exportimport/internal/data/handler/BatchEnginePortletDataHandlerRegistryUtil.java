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

	public static BatchEnginePortletDataHandler getByKey(
		long companyId, String key) {

		Map<String, BatchEnginePortletDataHandler>
			batchEnginePortletDataHandlers =
				_keyBatchEnginePortletDataHandlersMap.get(companyId);

		if (batchEnginePortletDataHandlers == null) {
			return null;
		}

		return batchEnginePortletDataHandlers.get(key);
	}

	public static BatchEnginePortletDataHandler getByPortletId(
		long companyId, String portletId) {

		Map<String, BatchEnginePortletDataHandler>
			batchEnginePortletDataHandlers =
				_portletIdBatchEnginePortletDataHandlersMap.get(companyId);

		if (batchEnginePortletDataHandlers == null) {
			return null;
		}

		return batchEnginePortletDataHandlers.get(portletId);
	}

	public static boolean hasByClassName(String className, long companyId) {
		Map<String, String> portletIds = _portletIdsMap.get(companyId);

		if (portletIds == null) {
			return false;
		}

		return portletIds.containsKey(className);
	}

	protected static void put(
		BatchEnginePortletDataHandler batchEnginePortletDataHandler,
		long companyId, String key, String portletId) {

		Map<String, BatchEnginePortletDataHandler>
			keyBatchEnginePortletDataHandlers =
				_keyBatchEnginePortletDataHandlersMap.computeIfAbsent(
					companyId, k -> new ConcurrentHashMap<>());

		keyBatchEnginePortletDataHandlers.put(
			key, batchEnginePortletDataHandler);

		Map<String, BatchEnginePortletDataHandler>
			portletIdBatchEnginePortletDataHandlers =
				_portletIdBatchEnginePortletDataHandlersMap.computeIfAbsent(
					companyId, k -> new ConcurrentHashMap<>());

		portletIdBatchEnginePortletDataHandlers.put(
			portletId, batchEnginePortletDataHandler);

		Map<String, String> portletIds = _portletIdsMap.computeIfAbsent(
			companyId, k -> new ConcurrentHashMap<>());

		for (String className : batchEnginePortletDataHandler.getClassNames()) {
			portletIds.put(className, portletId);
		}
	}

	protected static void remove(long companyId, String key, String portletId) {
		_keyBatchEnginePortletDataHandlersMap.computeIfPresent(
			companyId,
			(k, batchEnginePortletDataHandlers) -> {
				batchEnginePortletDataHandlers.remove(key);

				return batchEnginePortletDataHandlers.isEmpty() ? null :
					batchEnginePortletDataHandlers;
			});

		_portletIdBatchEnginePortletDataHandlersMap.computeIfPresent(
			companyId,
			(k, batchEnginePortletDataHandlers) -> {
				BatchEnginePortletDataHandler batchEnginePortletDataHandler =
					batchEnginePortletDataHandlers.remove(portletId);

				if (batchEnginePortletDataHandler != null) {
					_removePortletIds(
						batchEnginePortletDataHandler, companyId, portletId);
				}

				return batchEnginePortletDataHandlers.isEmpty() ? null :
					batchEnginePortletDataHandlers;
			});
	}

	private static String _getPortletId(long companyId, String className) {
		Map<String, String> portletIds = _portletIdsMap.get(companyId);

		if (portletIds == null) {
			return null;
		}

		return portletIds.get(className);
	}

	private static void _removePortletIds(
		BatchEnginePortletDataHandler batchEnginePortletDataHandler,
		long companyId, String portletId) {

		_portletIdsMap.computeIfPresent(
			companyId,
			(key, portletIds) -> {
				for (String className :
						batchEnginePortletDataHandler.getClassNames()) {

					portletIds.remove(className, portletId);
				}

				return portletIds.isEmpty() ? null : portletIds;
			});
	}

	private static final Map<Long, Map<String, BatchEnginePortletDataHandler>>
		_keyBatchEnginePortletDataHandlersMap = new ConcurrentHashMap<>();
	private static final Map<Long, Map<String, BatchEnginePortletDataHandler>>
		_portletIdBatchEnginePortletDataHandlersMap = new ConcurrentHashMap<>();
	private static final Map<Long, Map<String, String>> _portletIdsMap =
		new ConcurrentHashMap<>();

}