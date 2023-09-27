/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class FinderPath {

	public static String[] decodeDSLQueryCacheName(String cacheName) {
		return StringUtil.split(cacheName, _TABLE_SEPARATOR);
	}

	public static String encodeDSLQueryCacheName(String[] tableNames) {
		StringBundler sb = new StringBundler((tableNames.length * 2) - 1);

		for (int i = 0; i < tableNames.length; i++) {
			sb.append(tableNames[i]);

			if ((i + 1) < tableNames.length) {
				sb.append(_TABLE_SEPARATOR);
			}
		}

		return sb.toString();
	}

	public FinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		_cacheName = cacheName;
		_columnNames = columnNames;
		_baseModelResult = baseModelResult;

		_initCacheKeyPrefix(methodName, params);
	}

	public String getCacheKeyPrefix() {
		return _cacheKeyPrefix;
	}

	public String getCacheName() {
		return _cacheName;
	}

	public String[] getColumnNames() {
		return _columnNames;
	}

	public boolean isBaseModelResult() {
		return _baseModelResult;
	}

	private static Map<String, String> _getEncodedTypes() {
		return HashMapBuilder.put(
			Boolean.class.getName(), Boolean.class.getSimpleName()
		).put(
			Byte.class.getName(), Byte.class.getSimpleName()
		).put(
			Character.class.getName(), Character.class.getSimpleName()
		).put(
			Double.class.getName(), Double.class.getSimpleName()
		).put(
			Float.class.getName(), Float.class.getSimpleName()
		).put(
			Integer.class.getName(), Integer.class.getSimpleName()
		).put(
			Long.class.getName(), Long.class.getSimpleName()
		).put(
			Short.class.getName(), Short.class.getSimpleName()
		).put(
			String.class.getName(), String.class.getSimpleName()
		).build();
	}

	private void _initCacheKeyPrefix(String methodName, String[] params) {
		StringBundler sb = new StringBundler((params.length * 2) + 3);

		sb.append(methodName);
		sb.append(_PARAMS_SEPARATOR);

		for (String param : params) {
			sb.append(StringPool.PERIOD);
			sb.append(_encodedTypes.getOrDefault(param, param));
		}

		sb.append(_ARGS_SEPARATOR);

		_cacheKeyPrefix = sb.toString();
	}

	private static final String _ARGS_SEPARATOR = "_A_";

	private static final String _PARAMS_SEPARATOR = "_P_";

	private static final String _TABLE_SEPARATOR = "_T_";

	private static final Map<String, String> _encodedTypes = _getEncodedTypes();

	private final boolean _baseModelResult;
	private String _cacheKeyPrefix;
	private final String _cacheName;
	private final String[] _columnNames;

}