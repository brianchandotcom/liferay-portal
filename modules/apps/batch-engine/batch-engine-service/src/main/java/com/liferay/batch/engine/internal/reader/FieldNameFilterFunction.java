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

package com.liferay.batch.engine.internal.reader;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Igor Beslic
 */
public class FieldNameFilterFunction
	implements Function<Map<String, Object>, Map<String, Object>> {

	public FieldNameFilterFunction(List<String> includeNames) {
		_includeNames = Collections.unmodifiableList(includeNames);
	}

	@Override
	public Map<String, Object> apply(Map<String, Object> stringObjectMap) {
		Map<String, Object> filteredMap = new HashMap<>();

		for (String name : _includeNames) {
			filteredMap.put(name, stringObjectMap.get(name));
		}

		return filteredMap;
	}

	private final List<String> _includeNames;

}