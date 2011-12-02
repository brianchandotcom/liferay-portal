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

package com.liferay.portal.json.transformer;

import com.liferay.portal.json.JSONIncludesManager;
import com.liferay.portal.kernel.json.JSONTransformer;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import flexjson.JSONContext;
import flexjson.Path;
import flexjson.PathExpression;

import flexjson.transformer.ObjectTransformer;

import java.util.List;

import jodd.bean.BeanUtil;

/**
 * @author Igor Spasic
 */
public class FlexjsonObjectJSONTransformer
	extends ObjectTransformer implements JSONTransformer {

	@Override
	public void transform(Object object) {
		Class<?> type = resolveClass(object);

		List<PathExpression> pathExpressions =
			(List<PathExpression>)BeanUtil.getDeclaredProperty(
				getContext(), "pathExpressions");

		String path = _getPath();

		String[] excludes = _jsonIncludesManager.lookupExcludes(type);

		_exclude(pathExpressions, path, excludes);

		String[] includes = _jsonIncludesManager.lookupIncludes(type);

		_include(pathExpressions, path, includes);

		super.transform(object);
	}

	private void _exclude(
		List<PathExpression> pathExpressions, String path, String... names) {

		for (String name : names) {
			PathExpression pathExpression =
				new PathExpression(path + name, false);

			if (!pathExpressions.contains(pathExpression)) {
				pathExpressions.add(pathExpression);
			}
		}
	}

	private String _getPath() {
		JSONContext jsonContext = getContext();

		Path path = jsonContext.getPath();

		List<String> paths = path.getPath();

		if (paths.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(paths.size() * 2);

		for (String curPath : paths) {
			sb.append(curPath);
			sb.append(CharPool.PERIOD);
		}

		return sb.toString();
	}

	private void _include(
		List<PathExpression> pathExpressions, String path, String... names) {

		for (String name : names) {
			PathExpression pathExpression = new PathExpression(
				path + name, true);

			if (!pathExpressions.contains(pathExpression)) {
				pathExpressions.add(0, pathExpression);
			}
		}
	}

	private static JSONIncludesManager _jsonIncludesManager =
		new JSONIncludesManager();

}