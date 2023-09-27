/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public Map<String, Object> apply(Map<String, Object> map) {
		Map<String, Object> filteredMap = new HashMap<>();

		for (String name : _includeNames) {
			filteredMap.put(name, map.get(name));
		}

		return filteredMap;
	}

	private final List<String> _includeNames;

}