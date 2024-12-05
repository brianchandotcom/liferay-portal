/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iván Zaera Avellón
 */
public class JSImportMapsCache {

	public static final long COMPANY_ID_ALL = 0;

	public JSImportMapsCache(
		CompanyLocalService companyLocalService, JSONFactory jsonFactory) {

		_companyLocalService = companyLocalService;
		_jsonFactory = jsonFactory;
	}

	public synchronized String getImportMaps(long companyId) {
		if (!_valid) {
			_rebuildImportMaps();

			_valid = true;
		}

		return _importMapsMap.get(companyId);
	}

	public synchronized void invalidate() {
		_valid = false;
	}

	public synchronized JSImportMapsRegistration register(
		long companyId, JSONObject jsonObject, String scope) {

		if (scope == null) {
			Map<Long, JSONObject> globalImportMapsJSONObjects =
				_getGlobalImportMapsJSONObjects(companyId);

			long globalId = _nextGlobalId++;

			globalImportMapsJSONObjects.put(globalId, jsonObject);

			invalidate();

			return () -> {
				synchronized (JSImportMapsCache.this) {
					globalImportMapsJSONObjects.remove(globalId);

					invalidate();
				}
			};
		}

		Map<String, JSONObject> scopedImportMapsJSONObjects =
			_getScopedImportMapsJSONObjects(companyId);

		scopedImportMapsJSONObjects.put(scope, jsonObject);

		invalidate();

		return () -> {
			synchronized (JSImportMapsCache.this) {
				scopedImportMapsJSONObjects.remove(scope);

				invalidate();
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

	private void _rebuildImportMaps() {
		_importMapsMap.clear();

		_companyLocalService.forEachCompanyId(
			companyId -> _importMapsMap.put(
				companyId,
				JSONUtil.put(
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
				).toString()));
	}

	private final CompanyLocalService _companyLocalService;
	private final Map<Long, Map<Long, JSONObject>>
		_globalImportMapsJSONObjectsMap = new HashMap<>();
	private final Map<Long, String> _importMapsMap = new HashMap<>();
	private final JSONFactory _jsonFactory;
	private volatile long _nextGlobalId;
	private final Map<Long, Map<String, JSONObject>>
		_scopedImportMapsJSONObjectsMap = new HashMap<>();
	private volatile boolean _valid = true;

}