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

		Map<Long, JSONObject> globalImportMapsJSONObjects1 =
			_getGlobalImportMapsJSONObjects(COMPANY_ID_ALL);

		_putImports(sb, globalImportMapsJSONObjects1);

		Map<Long, JSONObject> globalImportMapsJSONObjects2 =
			_getGlobalImportMapsJSONObjects(companyId);

		if (!globalImportMapsJSONObjects1.isEmpty() &&
			!globalImportMapsJSONObjects2.isEmpty()) {

			sb.append(StringPool.COMMA);
		}

		_putImports(sb, globalImportMapsJSONObjects2);

		sb.append("},\"scopes\":{");

		Map<String, JSONObject> scopedImportMapsJSONObjects1 =
			_getScopedImportMapsJSONObjects(COMPANY_ID_ALL);

		_putScopes(sb, scopedImportMapsJSONObjects1);

		Map<String, JSONObject> scopedImportMapsJSONObjects2 =
			_getScopedImportMapsJSONObjects(companyId);

		if (!scopedImportMapsJSONObjects1.isEmpty() &&
			!scopedImportMapsJSONObjects2.isEmpty()) {

			sb.append(StringPool.COMMA);
		}

		_putScopes(sb, scopedImportMapsJSONObjects2);

		sb.append("}}");

		return sb.toString();
	}

	public JSImportMapsRegistration register(
		long companyId, JSONObject jsonObject, String scope) {

		if (scope == null) {
			ConcurrentMap<Long, JSONObject> globalImportMapsJSONObjects =
				_getGlobalImportMapsJSONObjects(companyId);

			long globalId = _nextGlobalId.getAndIncrement();

			globalImportMapsJSONObjects.put(globalId, jsonObject);

			return () -> globalImportMapsJSONObjects.remove(globalId);
		}

		ConcurrentMap<String, JSONObject> scopedImportMapsJSONObjects =
			_getScopedImportMapsJSONObjects(companyId);

		if (scopedImportMapsJSONObjects.putIfAbsent(scope, jsonObject) !=
				null) {

			_log.error(
				StringBundler.concat(
					"Import map ", jsonObject, " for scope ", scope,
					" will be ignored because there is already an import map ",
					"registered under that scope."));

			return () -> {
			};
		}

		return () -> scopedImportMapsJSONObjects.remove(scope);
	}

	private ConcurrentMap<Long, JSONObject> _getGlobalImportMapsJSONObjects(
		Long companyId) {

		ConcurrentMap<Long, JSONObject> globalImportMapsJSONObjects1 =
			_globalImportMapsJSONObjectsMap.get(companyId);

		if (globalImportMapsJSONObjects1 != null) {
			return globalImportMapsJSONObjects1;
		}

		_globalImportMapsJSONObjectsMap.putIfAbsent(
			companyId, new ConcurrentHashMap<>());

		return _globalImportMapsJSONObjectsMap.get(companyId);
	}

	private ConcurrentMap<String, JSONObject> _getScopedImportMapsJSONObjects(
		Long companyId) {

		ConcurrentMap<String, JSONObject> scopedImportMapsJSONObjects1 =
			_scopedImportMapsJSONObjectsMap.get(companyId);

		if (scopedImportMapsJSONObjects1 != null) {
			return scopedImportMapsJSONObjects1;
		}

		_scopedImportMapsJSONObjectsMap.putIfAbsent(
			companyId, new ConcurrentHashMap<>());

		return _scopedImportMapsJSONObjectsMap.get(companyId);
	}

	private void _putImports(
		StringBundler sb, Map<Long, JSONObject> globalImportMapsJSONObjects) {

		boolean first = true;

		for (JSONObject jsonObject : globalImportMapsJSONObjects.values()) {
			for (String key : jsonObject.keySet()) {
				if (!first) {
					sb.append(StringPool.COMMA);
				}
				else {
					first = false;
				}

				sb.append(StringPool.QUOTE);
				sb.append(key);
				sb.append("\":\"");
				sb.append(jsonObject.getString(key));
				sb.append(StringPool.QUOTE);
			}
		}
	}

	private void _putScopes(
		StringBundler sb, Map<String, JSONObject> scopedImportMapsJSONObjects) {

		boolean first = true;

		for (Map.Entry<String, JSONObject> entry :
				scopedImportMapsJSONObjects.entrySet()) {

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

	private final ConcurrentMap<Long, ConcurrentMap<Long, JSONObject>>
		_globalImportMapsJSONObjectsMap = new ConcurrentHashMap<>();
	private final AtomicLong _nextGlobalId = new AtomicLong();
	private final ConcurrentMap<Long, ConcurrentMap<String, JSONObject>>
		_scopedImportMapsJSONObjectsMap = new ConcurrentHashMap<>();

}