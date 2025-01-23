/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
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
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Iván Zaera Avellón
 */
public class JSImportMapsCache {

	public static final long COMPANY_ID_ALL = 0;

	public JSImportMapsRegistration register(
		long companyId, JSONObject jsonObject, String scope) {

		if (scope == null) {
			Map<Long, String> globalImportMapsValues =
				_getGlobalImportMapsValues(companyId);

			long globalId = _nextGlobalId.getAndIncrement();

			String value = jsonObject.toString();

			value = value.substring(1, value.length() - 1);

			globalImportMapsValues.put(globalId, value);

			return () -> globalImportMapsValues.remove(globalId);
		}

		Map<String, String> scopedImportMapsValues = _getScopedImportMapsValues(
			companyId);

		String value = scopedImportMapsValues.putIfAbsent(
			scope, jsonObject.toString());

		if (value != null) {
			_log.error(
				StringBundler.concat(
					"Import map ", jsonObject, " for scope ", scope, " will ",
					"be ignored because there is already an import map ",
					"registered under that scope"));

			return () -> {
			};
		}

		return () -> scopedImportMapsValues.remove(scope);
	}

	public void writeImportMaps(long companyId, Writer writer)
		throws IOException {

		if (companyId == COMPANY_ID_ALL) {
			throw new IllegalArgumentException(
				"Company ID cannot be " + COMPANY_ID_ALL);
		}

		writer.write("{\"imports\": {");

		Map<Long, String> globalImportMapsValues1 = _getGlobalImportMapsValues(
			COMPANY_ID_ALL);

		_writeImports(globalImportMapsValues1, writer);

		Map<Long, String> globalImportMapsValues2 = _getGlobalImportMapsValues(
			companyId);

		if (!globalImportMapsValues1.isEmpty() &&
			!globalImportMapsValues2.isEmpty()) {

			writer.write(StringPool.COMMA);
		}

		_writeImports(globalImportMapsValues2, writer);

		writer.write("}, \"scopes\": {");

		Map<String, String> scopedImportMapsValues1 =
			_getScopedImportMapsValues(COMPANY_ID_ALL);

		_writeScopes(scopedImportMapsValues1, writer);

		Map<String, String> scopedImportMapsValues2 =
			_getScopedImportMapsValues(companyId);

		if (!scopedImportMapsValues1.isEmpty() &&
			!scopedImportMapsValues2.isEmpty()) {

			writer.write(StringPool.COMMA);
		}

		_writeScopes(scopedImportMapsValues2, writer);

		writer.write("}}");
	}

	private Map<Long, String> _getGlobalImportMapsValues(Long companyId) {
		Map<Long, String> globalImportMapsValues1 =
			_globalImportMapsValuesMap.get(companyId);

		if (globalImportMapsValues1 != null) {
			return globalImportMapsValues1;
		}

		_globalImportMapsValuesMap.putIfAbsent(
			companyId, new ConcurrentHashMap<>());

		return _globalImportMapsValuesMap.get(companyId);
	}

	private Map<String, String> _getScopedImportMapsValues(Long companyId) {
		Map<String, String> scopedImportMapsValues1 =
			_scopedImportMapsValuesMap.get(companyId);

		if (scopedImportMapsValues1 != null) {
			return scopedImportMapsValues1;
		}

		_scopedImportMapsValuesMap.putIfAbsent(
			companyId, new ConcurrentHashMap<>());

		return _scopedImportMapsValuesMap.get(companyId);
	}

	private void _writeImports(
			Map<Long, String> globalImportMapsValues, Writer writer)
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
			Map<String, String> scopedImportMapsValues, Writer writer)
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
			writer.write("\": ");
			writer.write(entry.getValue());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSImportMapsCache.class);

	private final Map<Long, Map<Long, String>> _globalImportMapsValuesMap =
		new ConcurrentHashMap<>();
	private final AtomicLong _nextGlobalId = new AtomicLong();
	private final Map<Long, Map<String, String>> _scopedImportMapsValuesMap =
		new ConcurrentHashMap<>();

}