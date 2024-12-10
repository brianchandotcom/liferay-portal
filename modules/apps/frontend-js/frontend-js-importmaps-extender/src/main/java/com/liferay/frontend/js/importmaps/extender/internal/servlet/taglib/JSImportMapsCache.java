/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iván Zaera Avellón
 */
public class JSImportMapsCache {

	public static final long COMPANY_ID_ALL = 0;

	public JSImportMapsCache(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	public synchronized String getImportMaps(long companyId) {
		if (companyId == COMPANY_ID_ALL) {
			throw new IllegalArgumentException(
				"Do not pass COMPANY_ID_ALL as companyId");
		}

		String importMaps = _importMapsMap.get(companyId);

		if (importMaps == null) {
			importMaps = JSONUtil.put(
				"imports",
				() -> {
					JSONObject importsJSONObject =
						_jsonFactory.createJSONObject();

					_putImports(
						importsJSONObject,
						_getGlobalImportMapsJSONObjects(COMPANY_ID_ALL));
					_putImports(
						importsJSONObject,
						_getGlobalImportMapsJSONObjects(companyId));

					return importsJSONObject;
				}
			).put(
				"scopes",
				() -> {
					JSONObject scopesJSONObject =
						_jsonFactory.createJSONObject();

					_putScopes(
						scopesJSONObject,
						_getScopedImportMapsJSONObjects(COMPANY_ID_ALL));
					_putScopes(
						scopesJSONObject,
						_getScopedImportMapsJSONObjects(companyId));

					return scopesJSONObject;
				}
			).toString();

			_importMapsMap.put(companyId, importMaps);
		}

		return importMaps;
	}

	public synchronized void invalidate(long companyId) {
		if (companyId == COMPANY_ID_ALL) {
			_importMapsMap.clear();
		}
		else {
			_importMapsMap.remove(companyId);
		}
	}

	public synchronized JSImportMapsRegistration register(
		long companyId, JSONObject jsonObject, String scope) {

		if (scope == null) {
			Map<Long, JSONObject> globalImportMapsJSONObjects =
				_getGlobalImportMapsJSONObjects(companyId);

			long globalId = _nextGlobalId++;

			globalImportMapsJSONObjects.put(globalId, jsonObject);

			invalidate(companyId);

			return () -> {
				synchronized (JSImportMapsCache.this) {
					globalImportMapsJSONObjects.remove(globalId);

					invalidate(companyId);
				}
			};
		}

		Map<String, JSONObject> scopedImportMapsJSONObjects =
			_getScopedImportMapsJSONObjects(companyId);

		scopedImportMapsJSONObjects.put(scope, jsonObject);

		invalidate(companyId);

		return () -> {
			synchronized (JSImportMapsCache.this) {
				scopedImportMapsJSONObjects.remove(scope);

				invalidate(companyId);
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
		JSONObject importsJSONObject,
		Map<Long, JSONObject> globalImportMapsJSONObjects) {

		for (JSONObject jsonObject : globalImportMapsJSONObjects.values()) {
			for (String key : jsonObject.keySet()) {
				importsJSONObject.put(key, jsonObject.getString(key));
			}
		}
	}

	private void _putScopes(
		JSONObject scopesJSONObject,
		Map<String, JSONObject> scopedImportMapsJSONObjects) {

		for (Map.Entry<String, JSONObject> entry :
				scopedImportMapsJSONObjects.entrySet()) {

			scopesJSONObject.put(entry.getKey(), entry.getValue());
		}
	}

	private final Map<Long, Map<Long, JSONObject>>
		_globalImportMapsJSONObjectsMap = new HashMap<>();
	private final Map<Long, String> _importMapsMap = new HashMap<>();
	private final JSONFactory _jsonFactory;
	private volatile long _nextGlobalId;
	private final Map<Long, Map<String, JSONObject>>
		_scopedImportMapsJSONObjectsMap = new HashMap<>();

}