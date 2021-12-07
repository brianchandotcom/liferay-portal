/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.federation.internal.ingest;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * @author André de Oliveira
 */
public class TagResolver {

	public TagResolver(
		String equivalences, String fieldName, String transformations) {

		_fieldName = fieldName;

		_equivalencesMap = _toEquivalencesMap(equivalences);
		_transformationsMap = _toTransformationsMap(transformations);
	}

	public Map<String, Set<String>> resolve(Collection<String> tags) {
		Map<String, Set<String>> map = new HashMap<>();

		for (String tag : tags) {
			_resolve(map, tag);
		}

		return map;
	}

	private Set<String> _add(String label, Set<String> set) {
		if (set == null) {
			return new HashSet(Collections.singleton(label));
		}

		set.add(label);

		return set;
	}

	private String _getKey(String line) {
		return StringUtils.substringBefore(line, StringPool.EQUAL);
	}

	private String _getValue(String line) {
		return StringUtils.substringAfter(line, StringPool.EQUAL);
	}

	private void _resolve(Map<String, Set<String>> fieldValuesMap, String tag) {
		MapUtil.isNotEmptyForEach(
			_transformationsMap.get(tag),
			(key, value) -> fieldValuesMap.compute(
				key, (fieldName, fieldValues) -> _add(value, fieldValues)));

		String equivalence = _equivalencesMap.get(tag);

		// do not isNull since "null" is a valid word

		if (equivalence != null) {
			_resolve(fieldValuesMap, equivalence);
		}
		else {
			fieldValuesMap.compute(
				_fieldName, (fieldName, fieldValues) -> _add(tag, fieldValues));
		}
	}

	private Map<String, String> _toEquivalencesMap(String string) {
		if (Validator.isNull(string)) {
			return Collections.emptyMap();
		}

		Map<String, String> map = new HashMap<>();

		ArrayUtil.isNotEmptyForEach(
			StringUtil.splitLines(string),
			line -> map.put(_getKey(line), _getValue(line)));

		return map;
	}

	private Map<String, String> _toMap(String string) {
		return MapUtil.toLinkedHashMap(
			StringUtil.split(string.substring(1, string.length() - 1)),
			StringPool.EQUAL);
	}

	private Map<String, Map<String, String>> _toTransformationsMap(
		String string) {

		if (Validator.isNull(string)) {
			return Collections.emptyMap();
		}

		Map<String, Map<String, String>> map = new HashMap<>();

		ArrayUtil.isNotEmptyForEach(
			StringUtil.splitLines(string),
			line -> map.put(_getKey(line), _toMap(_getValue(line))));

		return map;
	}

	private final Map<String, String> _equivalencesMap;
	private final String _fieldName;
	private final Map<String, Map<String, String>> _transformationsMap;

}