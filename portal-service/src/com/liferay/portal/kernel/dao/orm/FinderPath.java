/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class FinderPath {

	public FinderPath(
		boolean entityCacheEnabled, boolean finderCacheEnabled,
		Class<?> modelClass, String className, String methodName,
		String[] params) {

		_entityCacheEnabled = entityCacheEnabled;
		_finderCacheEnabled = finderCacheEnabled;
		_modelClass = modelClass;
		_className = className;
		_methodName = methodName;
		_params = params;

		_initCacheKeyPrefix();
		_initLocalCacheKeyPrefix();
	}

	public String encodeCacheKey(Object[] args) {
		StringBundler sb = new StringBundler(args.length * 2 + 3);

		sb.append(ShardUtil.getCurrentShardName());
		sb.append(StringPool.PERIOD);
		sb.append(_cacheKeyPrefix);

		for (Object arg : args) {
			sb.append(StringPool.PERIOD);
			sb.append(StringUtil.toHexString(arg));
		}

		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(
				FinderCache.class.getName());

		return cacheKeyGenerator.getCacheKey(sb);
	}

	public String encodeLocalCacheKey(Object[] args) {
		StringBundler sb = new StringBundler(args.length * 2 + 3);

		sb.append(ShardUtil.getCurrentShardName());
		sb.append(StringPool.PERIOD);
		sb.append(_localCacheKeyPrefix);

		for (Object arg : args) {
			sb.append(StringPool.PERIOD);
			sb.append(StringUtil.toHexString(arg));
		}

		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(
				FinderCache.class.getName());

		return cacheKeyGenerator.getCacheKey(sb);
	}

	public String getClassName() {
		return _className;
	}

	public String getMethodName() {
		return _methodName;
	}

	public Class<?> getModelClass() {
		return _modelClass;
	}

	public String[] getParams() {
		return _params;
	}

	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	private void _initCacheKeyPrefix() {
		StringBundler sb = new StringBundler(_params.length * 2 + 3);

		sb.append(_methodName);
		sb.append(_PARAMS_SEPARATOR);

		for (String param : _params) {
			sb.append(StringPool.PERIOD);
			sb.append(param);
		}

		sb.append(_ARGS_SEPARATOR);

		_cacheKeyPrefix = sb.toString();
	}

	private void _initLocalCacheKeyPrefix() {
		_localCacheKeyPrefix = _className.concat(StringPool.PERIOD).concat(
			_cacheKeyPrefix);
	}

	private static final String _ARGS_SEPARATOR = "_A_";

	private static final String _PARAMS_SEPARATOR = "_P_";

	private String _cacheKeyPrefix;
	private String _className;
	private boolean _entityCacheEnabled;
	private boolean _finderCacheEnabled;
	private String _localCacheKeyPrefix;
	private String _methodName;
	private Class<?> _modelClass;
	private String[] _params;

}