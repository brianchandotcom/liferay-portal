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

import flexjson.BeanAnalyzer;
import flexjson.BeanProperty;
import flexjson.JSONContext;
import flexjson.Path;
import flexjson.PathExpression;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class FlexjsonBeanAnalyzerTransformer
	extends FlexjsonObjectJSONTransformer {

	public FlexjsonBeanAnalyzerTransformer(
		List<PathExpression> pathExpressions) {

		_pathExpressions = pathExpressions;
	}

	public Map<String, Map<String, String>> getPropertiesMap() {
		return _propertiesMap;
	}

	@Override
	public void transform(Object object) {
		Class<?> type = null;

		if (object instanceof Class) {
			type = (Class<?>)object;
		}
		else {
			type = object.getClass();
		}

		addExcludesAndIncludes(type, _pathExpressions, getPath());

		JSONContext jsonContext = getContext();

		Path path = jsonContext.getPath();

		BeanAnalyzer beanAnalyzer = BeanAnalyzer.analyze(type);

		for (BeanProperty beanProperty : beanAnalyzer.getProperties()) {
			String name = beanProperty.getName();

			if (name.equals("class")) {
				continue;
			}

			path.enqueue(name);

			if (jsonContext.isIncluded(beanProperty) &&
				beanProperty.isReadable()) {

				Map<String, String> properties =
					new LinkedHashMap<String, String>();

				properties.put(
					"type", getTypeName(beanProperty.getPropertyType()));

				_propertiesMap.put(name, properties);
			}

			path.pop();
		}
	}

	protected String getTypeName(Class<?> type) {
		return type.getName();
	}

	private final List<PathExpression> _pathExpressions;
	private final Map<String, Map<String, String>> _propertiesMap =
		new LinkedHashMap<String, Map<String, String>>();

}