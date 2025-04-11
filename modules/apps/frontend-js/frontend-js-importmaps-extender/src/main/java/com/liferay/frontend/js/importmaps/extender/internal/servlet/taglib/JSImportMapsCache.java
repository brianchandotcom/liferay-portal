/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib;

import com.liferay.frontend.js.importmaps.extender.DynamicJSImportMapsContributor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class JSImportMapsCache {

	public static final long COMPANY_ID_ALL = 0;

	public JSImportMapsRegistration register(
		long companyId,
		DynamicJSImportMapsContributor dynamicJSImportMapsContributor) {

		Map<Long, DynamicJSImportMapsContributor>
			dynamicJSImportMapsContributors =
				_getDynamicJSImportMapsContributors(companyId);

		long id = _nextId.getAndIncrement();

		dynamicJSImportMapsContributors.put(id, dynamicJSImportMapsContributor);

		return () -> dynamicJSImportMapsContributors.remove(id);
	}

	public JSImportMapsRegistration register(
		long companyId, JSONObject jsonObject, String scope) {

		if (scope == null) {
			Map<Long, String> globalImportMapsValues =
				_getGlobalImportMapsValues(companyId);

			long id = _nextId.getAndIncrement();

			String value = jsonObject.toString();

			value = value.substring(1, value.length() - 1);

			globalImportMapsValues.put(id, value);

			return () -> globalImportMapsValues.remove(id);
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

	public void writeImportMaps(
			long companyId, HttpServletRequest httpServletRequest,
			Writer writer)
		throws IOException {

		if (companyId == COMPANY_ID_ALL) {
			throw new IllegalArgumentException(
				"Company ID cannot be " + COMPANY_ID_ALL);
		}

		StringBuilder importsSB = new StringBuilder();

		_writeStaticImports(
			_getGlobalImportMapsValues(COMPANY_ID_ALL), importsSB);

		_writeStaticImports(_getGlobalImportMapsValues(companyId), importsSB);

		Map<Long, DynamicJSImportMapsContributor>
			dynamicJSImportMapsContributors1 =
				_getDynamicJSImportMapsContributors(COMPANY_ID_ALL);

		_writeDynamicImports(
			dynamicJSImportMapsContributors1, httpServletRequest, importsSB);

		Map<Long, DynamicJSImportMapsContributor>
			dynamicJSImportMapsContributors2 =
				_getDynamicJSImportMapsContributors(companyId);

		_writeDynamicImports(
			dynamicJSImportMapsContributors2, httpServletRequest, importsSB);

		StringBuilder scopesSB = new StringBuilder();

		_writeStaticScopes(
			_getScopedImportMapsValues(COMPANY_ID_ALL), scopesSB);

		_writeStaticScopes(_getScopedImportMapsValues(companyId), scopesSB);

		_writeDynamicScopes(
			dynamicJSImportMapsContributors1, httpServletRequest, scopesSB);

		_writeDynamicScopes(
			dynamicJSImportMapsContributors2, httpServletRequest, scopesSB);

		writer.write("{\"imports\": {");

		writer.write(importsSB.toString());

		writer.write("}, \"scopes\": {");

		writer.write(scopesSB.toString());

		writer.write("}}");
	}

	private Map<Long, DynamicJSImportMapsContributor>
		_getDynamicJSImportMapsContributors(Long companyId) {

		Map<Long, DynamicJSImportMapsContributor>
			dynamicJSImportMapsContributors =
				_dynamicJSImportMapsContributorsMap.get(companyId);

		if (dynamicJSImportMapsContributors != null) {
			return dynamicJSImportMapsContributors;
		}

		_dynamicJSImportMapsContributorsMap.putIfAbsent(
			companyId, new ConcurrentHashMap<>());

		return _dynamicJSImportMapsContributorsMap.get(companyId);
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

	private void _writeDynamicImports(
			Map<Long, DynamicJSImportMapsContributor>
				dynamicJSImportMapsContributors,
			HttpServletRequest httpServletRequest, StringBuilder sb)
		throws IOException {

		boolean first = true;

		for (DynamicJSImportMapsContributor dynamicJSImportMapsContributor :
				dynamicJSImportMapsContributors.values()) {

			CharArrayWriter charArrayWriter = new CharArrayWriter();

			dynamicJSImportMapsContributor.writeGlobalImports(
				httpServletRequest, charArrayWriter);

			if (charArrayWriter.size() == 0) {
				continue;
			}

			if (first) {
				first = false;

				if (!sb.isEmpty()) {
					sb.append(StringPool.COMMA);
				}
			}
			else {
				sb.append(StringPool.COMMA);
			}

			sb.append(charArrayWriter);
		}
	}

	private void _writeDynamicScopes(
			Map<Long, DynamicJSImportMapsContributor>
				dynamicJSImportMapsContributors,
			HttpServletRequest httpServletRequest, StringBuilder sb)
		throws IOException {

		boolean first = true;

		for (DynamicJSImportMapsContributor dynamicJSImportMapsContributor :
				dynamicJSImportMapsContributors.values()) {

			CharArrayWriter charArrayWriter = new CharArrayWriter();

			dynamicJSImportMapsContributor.writeScopedImports(
				httpServletRequest, charArrayWriter);

			if (charArrayWriter.size() == 0) {
				continue;
			}

			if (first) {
				first = false;

				if (!sb.isEmpty()) {
					sb.append(StringPool.COMMA);
				}
			}
			else {
				sb.append(StringPool.COMMA);
			}

			sb.append(charArrayWriter);
		}
	}

	private void _writeStaticImports(
		Map<Long, String> globalImportMapsValues, StringBuilder sb) {

		boolean first = true;

		for (String value : globalImportMapsValues.values()) {
			if (first) {
				first = false;

				if (!sb.isEmpty()) {
					sb.append(StringPool.COMMA);
				}
			}
			else {
				sb.append(StringPool.COMMA);
			}

			sb.append(value);
		}
	}

	private void _writeStaticScopes(
		Map<String, String> scopedImportMapsValues, StringBuilder sb) {

		boolean first = true;

		for (Map.Entry<String, String> entry :
				scopedImportMapsValues.entrySet()) {

			if (first) {
				first = false;

				if (!sb.isEmpty()) {
					sb.append(StringPool.COMMA);
				}
			}
			else {
				sb.append(StringPool.COMMA);
			}

			sb.append(StringPool.QUOTE);
			sb.append(entry.getKey());
			sb.append("\": ");
			sb.append(entry.getValue());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSImportMapsCache.class);

	private final Map<Long, Map<Long, DynamicJSImportMapsContributor>>
		_dynamicJSImportMapsContributorsMap = new ConcurrentHashMap<>();
	private final Map<Long, Map<Long, String>> _globalImportMapsValuesMap =
		new ConcurrentHashMap<>();
	private final AtomicLong _nextId = new AtomicLong();
	private final Map<Long, Map<String, String>> _scopedImportMapsValuesMap =
		new ConcurrentHashMap<>();

}