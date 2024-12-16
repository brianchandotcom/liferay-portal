/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iván Zaera Avellón
 */
public class JSImportMapsCache {

	public static final long COMPANY_ID_ALL = 0;

	public synchronized String getImportMaps(long companyId) {
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

	public synchronized JSImportMapsRegistration register(
		long companyId, JSONObject jsonObject, String scope) {

		if (scope == null) {
			Map<Long, JSONObject> globalImportMapsJSONObjects =
				_getGlobalImportMapsJSONObjects(companyId);

			long globalId = _nextGlobalId++;

			globalImportMapsJSONObjects.put(globalId, jsonObject);

			return () -> {
				synchronized (JSImportMapsCache.this) {
					globalImportMapsJSONObjects.remove(globalId);
				}
			};
		}

		Map<String, JSONObject> scopedImportMapsJSONObjects =
			_getScopedImportMapsJSONObjects(companyId);

		scopedImportMapsJSONObjects.put(scope, jsonObject);

		return () -> {
			synchronized (JSImportMapsCache.this) {
				scopedImportMapsJSONObjects.remove(scope);
			}
		};
	}

	private Map<Long, JSONObject> _getGlobalImportMapsJSONObjects(
		Long companyId) {

		Map<Long, JSONObject> globalImportMapsJSONObjects1 =
			_globalImportMapsJSONObjectsMap.get(companyId);

		if (globalImportMapsJSONObjects1 != null) {
			return globalImportMapsJSONObjects1;
		}

		Map<Long, JSONObject> globalImportMapsJSONObjects2 = new HashMap<>();

		globalImportMapsJSONObjects1 =
			_globalImportMapsJSONObjectsMap.putIfAbsent(
				companyId, globalImportMapsJSONObjects2);

		if (globalImportMapsJSONObjects1 != null) {
			return globalImportMapsJSONObjects1;
		}

		return globalImportMapsJSONObjects2;
	}

	private Map<String, JSONObject> _getScopedImportMapsJSONObjects(
		Long companyId) {

		Map<String, JSONObject> scopedImportMapsJSONObjects1 =
			_scopedImportMapsJSONObjectsMap.get(companyId);

		if (scopedImportMapsJSONObjects1 != null) {
			return scopedImportMapsJSONObjects1;
		}

		Map<String, JSONObject> scopedImportMapsJSONObjects2 = new HashMap<>();

		scopedImportMapsJSONObjects1 =
			_scopedImportMapsJSONObjectsMap.putIfAbsent(
				companyId, scopedImportMapsJSONObjects2);

		if (scopedImportMapsJSONObjects1 != null) {
			return scopedImportMapsJSONObjects1;
		}

		return scopedImportMapsJSONObjects2;
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

	private final Map<Long, Map<Long, JSONObject>>
		_globalImportMapsJSONObjectsMap = new HashMap<>();
	private volatile long _nextGlobalId;
	private final Map<Long, Map<String, JSONObject>>
		_scopedImportMapsJSONObjectsMap = new HashMap<>();

}