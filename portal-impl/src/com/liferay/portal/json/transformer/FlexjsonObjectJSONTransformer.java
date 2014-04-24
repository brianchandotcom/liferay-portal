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

package com.liferay.portal.json.transformer;

import com.liferay.portal.kernel.json.JSONIncludesManagerUtil;
import com.liferay.portal.kernel.json.JSONTransformer;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

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

		String path = getPath();

		addExcludesAndIncludes(type, pathExpressions, path);

		super.transform(object);
	}

	protected void addExcludesAndIncludes(
		Class<?> type, List<PathExpression> pathExpressions, String path) {

		String[] excludes = JSONIncludesManagerUtil.lookupExcludes(type);

		_exclude(pathExpressions, path, excludes);

		String[] includes = JSONIncludesManagerUtil.lookupIncludes(type);

		_include(pathExpressions, path, includes);
	}

	protected String getPath() {
		JSONContext jsonContext = getContext();

		Path path = jsonContext.getPath();

		List<String> paths = path.getPath();

		if (paths.isEmpty()) {
			return StringPool.BLANK;
		}

		String mergedPath = StringUtil.merge(paths, StringPool.PERIOD);

		mergedPath = mergedPath.concat(StringPool.PERIOD);

		return mergedPath;
	}

	private void _exclude(
		List<PathExpression> pathExpressions, String path, String... names) {

		for (String name : names) {
			PathExpression pathExpression = new PathExpression(
				path.concat(name), false);

			for (int i = 0; i < pathExpressions.size(); i++) {
				PathExpression curPathExpression = pathExpressions.get(i);

				if (pathExpression.equals(curPathExpression) &&
					curPathExpression.isIncluded()) {

					// Same path expression found, but it was included,
					// therefore, replace it with the excluded path expression

					pathExpressions.set(i, pathExpression);

					return;
				}
			}

			pathExpressions.add(pathExpression);
		}
	}

	private void _include(
		List<PathExpression> pathExpressions, String path, String... names) {

		for (String name : names) {
			PathExpression pathExpression = new PathExpression(
				path.concat(name), true);

			if (!pathExpressions.contains(pathExpression)) {
				pathExpressions.add(0, pathExpression);
			}
		}
	}

}