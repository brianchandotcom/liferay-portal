/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Vendel Toreki
 * @author Petteri Karttunen
 */
public class BatchEnginePortletDataHandlerRegistryUtil {

	public static BatchEnginePortletDataHandler getByClassName(
		long companyId, String className) {

		if (!_classNamePortletIdsMap.containsKey(className)) {
			return null;
		}

		String portletId = _classNamePortletIdsMap.get(className);

		if (!_isAllowed(companyId, portletId)) {
			return null;
		}

		return _portletIdBatchEnginePortletDataHandlersMap.get(portletId);
	}

	public static String getPortletId(long companyId, String key) {
		String portletId = null;

		if (_allCompaniesKeyPortletIdsMap.containsKey(key)) {
			portletId = _allCompaniesKeyPortletIdsMap.get(key);
		}
		else if (_companyIdKeyPortletIdsMap.containsKey(companyId)) {
			Map<String, String> keyPortletIdsMap =
				_companyIdKeyPortletIdsMap.get(companyId);

			if (keyPortletIdsMap.containsKey(key)) {
				portletId = keyPortletIdsMap.get(key);
			}
		}

		if (!_isAllowed(companyId, portletId)) {
			return null;
		}

		return portletId;
	}

	public static boolean hasByClassName(String className, long companyId) {
		if (!_classNamePortletIdsMap.containsKey(className)) {
			return false;
		}

		String portletId = _classNamePortletIdsMap.get(className);

		return _isAllowed(companyId, portletId);
	}

	protected static BatchEnginePortletDataHandler getByPortletId(
		long companyId, String portletId) {

		if (!_isAllowed(companyId, portletId)) {
			return null;
		}

		return _portletIdBatchEnginePortletDataHandlersMap.get(portletId);
	}

	protected static void registerBatchEnginePortletDataHandler(
		BatchEnginePortletDataHandler batchEnginePortletDataHandler,
		Long companyId, String portletId) {

		if (_isAllCompanies(companyId)) {
			_allCompaniesPortletIds.add(portletId);
		}
		else {
			_portletIdCompanyIdMap.put(portletId, companyId);
		}

		_portletIdBatchEnginePortletDataHandlersMap.put(
			portletId, batchEnginePortletDataHandler);

		for (String className : batchEnginePortletDataHandler.getClassNames()) {
			_classNamePortletIdsMap.put(className, portletId);
		}
	}

	protected static void registerKey(
		Long companyId, String key, String portletId) {

		if (_isAllCompanies(companyId)) {
			_allCompaniesKeyPortletIdsMap.put(key, portletId);
		}
		else {
			_companyIdKeyPortletIdsMap.computeIfAbsent(
				companyId, __ -> new ConcurrentHashMap<>()
			).put(
				key, portletId
			);
		}
	}

	protected static void unregister(Long companyId, String portletId) {
		if (_isAllCompanies(companyId)) {
			_allCompaniesPortletIds.remove(portletId);
		}
		else {
			_portletIdCompanyIdMap.remove(portletId, companyId);
		}

		_portletIdBatchEnginePortletDataHandlersMap.remove(portletId);

		_removeEntries(_classNamePortletIdsMap, portletId);
	}

	protected static void unregisterKey(
		Long companyId, String key, String portletId) {

		if (_isAllCompanies(companyId)) {
			_allCompaniesKeyPortletIdsMap.remove(key, portletId);
		}

		if (!_companyIdKeyPortletIdsMap.containsKey(companyId)) {
			return;
		}

		Map<String, String> keyPortletIdsMap = _companyIdKeyPortletIdsMap.get(
			companyId);

		keyPortletIdsMap.remove(key, portletId);

		if (keyPortletIdsMap.isEmpty()) {
			_companyIdKeyPortletIdsMap.remove(companyId);
		}
	}

	private static boolean _isAllCompanies(Long companyId) {
		if ((companyId == null) || (companyId == 0)) {
			return true;
		}

		return false;
	}

	private static boolean _isAllowed(long companyId, String portletId) {
		if (portletId == null) {
			return false;
		}

		if (_allCompaniesPortletIds.contains(portletId)) {
			return true;
		}

		return Objects.equals(companyId, _portletIdCompanyIdMap.get(portletId));
	}

	private static void _removeEntries(Map<String, String> map, String value) {
		map.entrySet(
		).removeIf(
			entry -> Objects.equals(entry.getValue(), value)
		);
	}

	private static final Map<String, String> _allCompaniesKeyPortletIdsMap =
		new ConcurrentHashMap<>();
	private static final Set<String> _allCompaniesPortletIds =
		new CopyOnWriteArraySet<>();
	private static final Map<String, String> _classNamePortletIdsMap =
		new ConcurrentHashMap<>();
	private static final Map<Long, Map<String, String>>
		_companyIdKeyPortletIdsMap = new ConcurrentHashMap<>();
	private static final Map<String, BatchEnginePortletDataHandler>
		_portletIdBatchEnginePortletDataHandlersMap = new ConcurrentHashMap<>();
	private static final Map<String, Long> _portletIdCompanyIdMap =
		new ConcurrentHashMap<>();

}