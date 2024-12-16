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

import java.io.IOException;
import java.io.Writer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Iván Zaera Avellón
 */
public class JSImportMapsCache {

	public static final long COMPANY_ID_ALL = 0;

	public synchronized JSImportMapsRegistration register(
		long companyId, JSONObject jsonObject, String scope) {

		if (scope == null) {
			ConcurrentMap<Long, String> globalImportMapsValues =
				_getGlobalImportMapsValues(companyId);

			long globalId = _nextGlobalId.getAndIncrement();

			String value = jsonObject.toString();

			value = value.substring(1, value.length() - 1);

			globalImportMapsValues.put(globalId, value);

			return () -> {
				synchronized (this) {
					globalImportMapsValues.remove(globalId);

					if (globalImportMapsValues.isEmpty()) {
						_globalImportMapsValuesMap.remove(companyId);
					}
				}
			};
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

		return () -> {
			synchronized (this) {
				scopedImportMapsValues.remove(scope);

				if (scopedImportMapsValues.isEmpty()) {
					_scopedImportMapsValuesMap.remove(companyId);
				}
			}
		};
	}

	public void writeImportMaps(long companyId, Writer writer)
		throws IOException {

		if (companyId == COMPANY_ID_ALL) {
			throw new IllegalArgumentException(
				"Do not pass COMPANY_ID_ALL as companyId");
		}

		writer.write("{\"imports\":{");

		Map<Long, String> globalImportMapsValues1 = _getGlobalImportMapsValues(
			COMPANY_ID_ALL);

		_writeImports(writer, globalImportMapsValues1);

		Map<Long, String> globalImportMapsValues2 = _getGlobalImportMapsValues(
			companyId);

		if (!globalImportMapsValues1.isEmpty() &&
			!globalImportMapsValues2.isEmpty()) {

			writer.write(StringPool.COMMA);
		}

		_writeImports(writer, globalImportMapsValues2);

		writer.write("},\"scopes\":{");

		Map<String, String> scopedImportMapsValues1 =
			_getScopedImportMapsValues(COMPANY_ID_ALL);

		_writeScopes(writer, scopedImportMapsValues1);

		Map<String, String> scopedImportMapsValues2 =
			_getScopedImportMapsValues(companyId);

		if (!scopedImportMapsValues1.isEmpty() &&
			!scopedImportMapsValues2.isEmpty()) {

			writer.write(StringPool.COMMA);
		}

		_writeScopes(writer, scopedImportMapsValues2);

		writer.write("}}");
	}

	private ConcurrentMap<Long, String> _getGlobalImportMapsValues(
		Long companyId) {

		ConcurrentMap<Long, String> globalImportMapsValues1 =
			_globalImportMapsValuesMap.get(companyId);

		if (globalImportMapsValues1 != null) {
			return globalImportMapsValues1;
		}

		synchronized (this) {
			_globalImportMapsValuesMap.putIfAbsent(
				companyId, new ConcurrentHashMap<>());
		}

		return _globalImportMapsValuesMap.get(companyId);
	}

	private ConcurrentMap<String, String> _getScopedImportMapsValues(
		Long companyId) {

		ConcurrentMap<String, String> scopedImportMapsValues1 =
			_scopedImportMapsValuesMap.get(companyId);

		if (scopedImportMapsValues1 != null) {
			return scopedImportMapsValues1;
		}

		synchronized (this) {
			_scopedImportMapsValuesMap.putIfAbsent(
				companyId, new ConcurrentHashMap<>());
		}

		return _scopedImportMapsValuesMap.get(companyId);
	}

	private void _writeImports(
			Writer writer, Map<Long, String> globalImportMapsValues)
		throws IOException {

		boolean first = true;

		for (String value : globalImportMapsValues.values()) {
			if (!first) {
				writer.write(StringPool.COMMA);
			}
			else {
				first = false;
			}

			writer.write(value);
		}
	}

	private void _writeScopes(
			Writer writer, Map<String, String> scopedImportMapsValues)
		throws IOException {

		boolean first = true;

		for (Map.Entry<String, String> entry :
				scopedImportMapsValues.entrySet()) {

			if (!first) {
				writer.write(StringPool.COMMA);
			}
			else {
				first = false;
			}

			writer.write(StringPool.QUOTE);
			writer.write(entry.getKey());
			writer.write("\":");
			writer.write(entry.getValue());
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