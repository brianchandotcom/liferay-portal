/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Iván Zaera Avellón
 */
public class JSImportMapsCache {

	public static final long COMPANY_ID_ALL = 0;

	public String getImportMaps(long companyId) {
		if (companyId == COMPANY_ID_ALL) {
			throw new IllegalArgumentException(
				"Do not pass COMPANY_ID_ALL as companyId");
		}

		StringBundler sb = new StringBundler();

		sb.append("{\"imports\":{");

		Map<Long, String> globalImportMapsValues1 = _getGlobalImportMapsValues(
			COMPANY_ID_ALL);

		_putImports(sb, globalImportMapsValues1);

		Map<Long, String> globalImportMapsValues2 = _getGlobalImportMapsValues(
			companyId);

		if (!globalImportMapsValues1.isEmpty() &&
			!globalImportMapsValues2.isEmpty()) {

			sb.append(StringPool.COMMA);
		}

		_putImports(sb, globalImportMapsValues2);

		sb.append("},\"scopes\":{");

		Map<String, String> scopedImportMapsValues1 =
			_getScopedImportMapsValues(COMPANY_ID_ALL);

		_putScopes(sb, scopedImportMapsValues1);

		Map<String, String> scopedImportMapsValues2 =
			_getScopedImportMapsValues(companyId);

		if (!scopedImportMapsValues1.isEmpty() &&
			!scopedImportMapsValues2.isEmpty()) {

			sb.append(StringPool.COMMA);
		}

		_putScopes(sb, scopedImportMapsValues2);

		sb.append("}}");

		return sb.toString();
	}

	public JSImportMapsRegistration register(
		long companyId, JSONObject jsonObject, String scope) {

		if (scope == null) {
			ConcurrentMap<Long, String> globalImportMapsValues =
				_getGlobalImportMapsValues(companyId);

			long globalId = _nextGlobalId.getAndIncrement();

			String value = jsonObject.toString();

			value = value.substring(1, value.length() - 1);

			globalImportMapsValues.put(globalId, value);

			return () -> globalImportMapsValues.remove(globalId);
		}

		ConcurrentMap<String, String> scopedImportMapsValues =
			_getScopedImportMapsValues(companyId);

		String value = scopedImportMapsValues.putIfAbsent(
			scope, jsonObject.toString());

		if (value != null) {
			_log.error(
				StringBundler.concat(
					"Import map ", jsonObject, " for scope ", scope, " will ",
					"be ignored because there is already an import map ",
					"registered under that scope."));

			return () -> {
			};
		}

		return () -> scopedImportMapsValues.remove(scope);
	}

	private ConcurrentMap<Long, String> _getGlobalImportMapsValues(
		Long companyId) {

		ConcurrentMap<Long, String> globalImportMapsValues1 =
			_globalImportMapsValuesMap.get(companyId);

		if (globalImportMapsValues1 != null) {
			return globalImportMapsValues1;
		}

		_globalImportMapsValuesMap.putIfAbsent(
			companyId, new ConcurrentHashMap<>());

		return _globalImportMapsValuesMap.get(companyId);
	}

	private ConcurrentMap<String, String> _getScopedImportMapsValues(
		Long companyId) {

		ConcurrentMap<String, String> scopedImportMapsValues1 =
			_scopedImportMapsValuesMap.get(companyId);

		if (scopedImportMapsValues1 != null) {
			return scopedImportMapsValues1;
		}

		_scopedImportMapsValuesMap.putIfAbsent(
			companyId, new ConcurrentHashMap<>());

		return _scopedImportMapsValuesMap.get(companyId);
	}

	private void _putImports(
		StringBundler sb, Map<Long, String> globalImportMapsValues) {

		boolean first = true;

		for (String value : globalImportMapsValues.values()) {
			if (!first) {
				sb.append(StringPool.COMMA);
			}
			else {
				first = false;
			}

			sb.append(value);
		}
	}

	private void _putScopes(
		StringBundler sb, Map<String, String> scopedImportMapsValues) {

		boolean first = true;

		for (Map.Entry<String, String> entry :
				scopedImportMapsValues.entrySet()) {

			if (!first) {
				sb.append(StringPool.COMMA);
			}
			else {
				first = false;
			}

			sb.append(StringPool.QUOTE);
			sb.append(entry.getKey());
			sb.append("\":");
			sb.append(entry.getValue());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSImportMapsCache.class);

	private final ConcurrentMap<Long, ConcurrentMap<Long, String>>
		_globalImportMapsValuesMap = new ConcurrentHashMap<>();
	private final AtomicLong _nextGlobalId = new AtomicLong();
	private final ConcurrentMap<Long, ConcurrentMap<String, String>>
		_scopedImportMapsValuesMap = new ConcurrentHashMap<>();

}