/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class FinderPath {

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #FinderPath(String, String, String[], String[], boolean)}
	 */
	@Deprecated
	public FinderPath(
		boolean entityCacheEnabled, boolean finderCacheEnabled,
		Class<?> resultClass, String cacheName, String methodName,
		String[] params) {

		this(
			cacheName, methodName, params, new String[0],
			BaseModel.class.isAssignableFrom(resultClass));
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #FinderPath(String, String, String[], String[], boolean)}
	 */
	@Deprecated
	public FinderPath(
		boolean entityCacheEnabled, boolean finderCacheEnabled,
		Class<?> resultClass, String cacheName, String methodName,
		String[] params, long columnBitmask) {

		this(
			cacheName, methodName, params, new String[0],
			BaseModel.class.isAssignableFrom(resultClass));
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #FinderPath(String, String, String[], String[], boolean)}
	 */
	@Deprecated
	public FinderPath(
		Class<?> resultClass, String cacheName, String methodName,
		String[] params) {

		this(
			cacheName, methodName, params, new String[0],
			BaseModel.class.isAssignableFrom(resultClass));
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #FinderPath(String, String, String[], String[], boolean)}
	 */
	@Deprecated
	public FinderPath(
		Class<?> resultClass, String cacheName, String methodName,
		String[] params, long columnBitmask) {

		this(
			cacheName, methodName, params, new String[0],
			BaseModel.class.isAssignableFrom(resultClass));
	}

	public FinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		_cacheName = cacheName;
		_columnNames = columnNames;
		_baseModelResult = baseModelResult;

		if (baseModelResult) {
			_cacheKeyGenerator = CacheKeyGeneratorUtil.getCacheKeyGenerator(
				_BASE_MODEL_CACHE_KEY_GENERATOR_NAME);
		}
		else {
			_cacheKeyGenerator = CacheKeyGeneratorUtil.getCacheKeyGenerator(
				FinderCache.class.getName());
		}

		_initCacheKeyPrefix(methodName, params);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #encodeCacheKey(Object[])}
	 */
	@Deprecated
	public String encodeArguments(Object[] arguments) {
		String[] keys = new String[arguments.length * 2];

		for (int i = 0; i < arguments.length; i++) {
			int index = i * 2;

			keys[index] = StringPool.PERIOD;
			keys[index + 1] = StringUtil.toHexString(arguments[i]);
		}

		return StringUtil.toHexString(_getCacheKey(keys));
	}

	public Serializable encodeCacheKey(Object[] arguments) {
		String[] keys = new String[arguments.length * 2];

		for (int i = 0; i < arguments.length; i++) {
			int index = i * 2;

			keys[index] = StringPool.PERIOD;
			keys[index + 1] = StringUtil.toHexString(arguments[i]);
		}

		return _cacheKeyGenerator.getCacheKey(
			new String[] {
				_cacheKeyPrefix,
				StringUtil.toHexString(_cacheKeyGenerator.getCacheKey(keys))
			});
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #encodeCacheKey(Object[])}
	 */
	@Deprecated
	public Serializable encodeCacheKey(String encodedArguments) {
		return _getCacheKey(new String[] {_cacheKeyPrefix, encodedArguments});
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public Serializable encodeLocalCacheKey(String encodedArguments) {
		return _getCacheKey(
			new String[] {
				StringBundler.concat(
					_cacheName, StringPool.PERIOD, _cacheKeyPrefix),
				encodedArguments
			});
	}

	public String getCacheName() {
		return _cacheName;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public long getColumnBitmask() {
		return 0;
	}

	public String[] getColumnNames() {
		return _columnNames;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public Class<?> getResultClass() {
		return null;
	}

	public boolean isBaseModelResult() {
		return _baseModelResult;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public boolean isEntityCacheEnabled() {
		return true;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public boolean isFinderCacheEnabled() {
		return true;
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

	private Serializable _getCacheKey(String[] keys) {
		return _cacheKeyGenerator.getCacheKey(keys);
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

	private static final String _BASE_MODEL_CACHE_KEY_GENERATOR_NAME =
		FinderCache.class.getName() + "#BaseModel";

	private static final String _PARAMS_SEPARATOR = "_P_";

	private static final Map<String, String> _encodedTypes = _getEncodedTypes();

	private final boolean _baseModelResult;
	private final CacheKeyGenerator _cacheKeyGenerator;
	private String _cacheKeyPrefix;
	private final String _cacheName;
	private final String[] _columnNames;

}