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

package com.liferay.portal.server;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Igor Spasic
 */
public class DeepNamedValueScanner {

	public DeepNamedValueScanner(String value) {
		_value = StringUtil.toLowerCase(value);
	}

	public DeepNamedValueScanner(String value, boolean visit) {
		this(value);

		_visitArrays = visit;
		_visitCollections = visit;
		_visitLists = visit;
		_visitSets = visit;
		_visitMaps = visit;
		_visitStaticFields = visit;
	}

	public long getElapsedTime() {
		return _elapsedTime;
	}

	public String[] getExcludedClassNames() {
		return _excludedClassNames;
	}

	public String[] getExcludedNames() {
		return _excludedNames;
	}

	public String[] getIncludedClassNames() {
		return _includedClassNames;
	}

	public Object getMatchedValue() {
		return _matchedValue;
	}

	public int getMatchingCount() {
		return _matchingCount;
	}

	public int getSkipFirstCount() {
		return _skipFirstCount;
	}

	public boolean isScanning() {
		return _scanning;
	}

	public boolean isTrackUsageCount() {
		return _trackUsageCount;
	}

	public boolean isVisitArrays() {
		return _visitArrays;
	}

	public boolean isVisitCollectionss() {
		return _visitCollections;
	}

	public boolean isVisitLists() {
		return _visitLists;
	}

	public boolean isVisitMaps() {
		return _visitMaps;
	}

	public boolean isVisitSets() {
		return _visitSets;
	}

	public boolean isVisitStaticFields() {
		return _visitStaticFields;
	}

	public void printStatistics(int topCount) {
		if (!_trackUsageCount) {
			return;
		}

		System.out.println("-- names statistics --");

		_printStatistics(_nameDatasets.values(), topCount);

		System.out.println("-- types statistics --");

		_printStatistics(_typeDatasets.values(), topCount);
	}

	public boolean scan(Object target) throws Exception {
		_elapsedTime = System.currentTimeMillis();

		_visitedIds = new HashSet<>();

		_scanning = true;

		_scan(target);

		_visitedIds = null;

		_elapsedTime = System.currentTimeMillis() - _elapsedTime;

		if (_log.isDebugEnabled()) {
			if (!_scanning) {
				StringBundler sb = new StringBundler(5);

				sb.append("Deep named value scanner found ");
				sb.append(_matchingCount);
				sb.append(" matches in ");
				sb.append(_elapsedTime);
				sb.append(" ms");

				_log.debug(sb.toString());
			}
			else {
				_log.debug("Deep named value scanner did not finish scanning");
			}
		}

		return !_scanning;
	}

	public void setExcludedClassNames(String... excludedClassNames) {
		_excludedClassNames = excludedClassNames;

		StringUtil.lowerCase(excludedClassNames);
	}

	public void setExcludedNames(String... excludedNames) {
		_excludedNames = excludedNames;

		StringUtil.lowerCase(excludedNames);
	}

	public void setIncludedClassNames(String... includedClassNames) {
		_includedClassNames = includedClassNames;

		StringUtil.lowerCase(includedClassNames);
	}

	public void setSkipFirstCount(int skipFirstCount) {
		_skipFirstCount = skipFirstCount;
	}

	public void setTrackUsageCount(boolean trackUsageCount) {
		_trackUsageCount = trackUsageCount;

		if (trackUsageCount) {
			_nameDatasets = new HashMap<>();
			_typeDatasets = new HashMap<>();
		}
	}

	public void setVisitArrays(boolean visitArrays) {
		_visitArrays = visitArrays;
	}

	public void setVisitCollections(boolean visitCollections) {
		_visitCollections = visitCollections;
	}

	public void setVisitLists(boolean visitLists) {
		_visitLists = visitLists;
	}

	public void setVisitMaps(boolean visitMaps) {
		_visitMaps = visitMaps;
	}

	public void setVisitSets(boolean visitSets) {
		_visitSets = visitSets;
	}

	public void setVisitStaticFields(boolean visitStaticFields) {
		_visitStaticFields = visitStaticFields;
	}

	private void _incrementUsageCount(
		Map<String, Dataset> datasets, String name) {

		Dataset dataset = datasets.get(name);

		if (dataset == null) {
			dataset = new Dataset();

			dataset.setKey(name);
			dataset.setValue(new IntegerWrapper());

			datasets.put(name, dataset);
		}

		IntegerWrapper integerWrapper = dataset.getValue();

		integerWrapper.increment();
	}

	private boolean _isAcceptClass(Class<?> targetClass) {
		String targetClassName = targetClass.getName();

		targetClassName = StringUtil.toLowerCase(targetClassName);

		if (targetClassName.startsWith("java.")) {
			return false;
		}

		if (targetClassName.startsWith("org.eclipse.osgi.")) {
			return false;
		}

		if (targetClassName.startsWith("sun.misc.")) {
			return false;
		}

		if (targetClassName.contains("log")) {
			return false;
		}

		if (_excludedClassNames != null) {
			for (String excludedClassName : _excludedClassNames) {
				if (targetClassName.contains(excludedClassName)) {
					return false;
				}
			}
		}

		if (_includedClassNames != null) {
			boolean accept = false;

			for (String includedClassName : _includedClassNames) {
				if (targetClassName.contains(includedClassName)) {
					accept = true;

					break;
				}
			}

			if (!accept) {
				return false;
			}
		}

		if (_trackUsageCount) {
			_incrementUsageCount(_typeDatasets, targetClass.getName());
		}

		return true;
	}

	private boolean _isAcceptName(String name) {
		if (name == null) {
			return true;
		}

		name = StringUtil.toLowerCase(name);

		if (_excludedNames != null) {
			for (String excludedNames : _excludedNames) {
				if (name.contains(excludedNames)) {
					return false;
				}
			}
		}

		if (_trackUsageCount) {
			_incrementUsageCount(_nameDatasets, name);
		}

		return true;
	}

	private void _matchField(Object target, Field field, String name)
		throws IllegalAccessException {

		if (name == null) {
			return;
		}

		_matchingCount++;

		name = StringUtil.toLowerCase(name);

		if (name.contains(_value)) {
			if (_skipFirstCount > 0) {
				_skipFirstCount--;

				return;
			}

			field.setAccessible(true);

			_matchedValue = field.get(target);

			_scanning = false;
		}
	}

	private void _matchName(Object value, String name) {
		if (name == null) {
			return;
		}

		_matchingCount++;

		name = StringUtil.toLowerCase(name);

		if (name.contains(_value)) {
			if (_skipFirstCount > 0) {
				_skipFirstCount--;

				return;
			}

			_matchedValue = value;

			_scanning = false;
		}
	}

	private void _printStatistics(Collection<Dataset> datasets, int topCount) {
		List<Dataset> datasetsList = new ArrayList<>();

		for (Dataset dataset : datasets) {
			datasetsList.add(dataset);
		}

		Collections.sort(datasetsList);

		for (Dataset dataset : datasetsList) {
			System.out.println(dataset.getValue() + " " + dataset.getKey());

			topCount--;

			if (topCount == 0) {
				break;
			}
		}
	}

	private Object _resolveJavaProxy(Object target)
		throws IllegalAccessException, NoSuchFieldException {

		Class<?> targetClass = target.getClass();
		Class<?> targetSuperClass = targetClass.getSuperclass();

		if ((targetSuperClass != null) &&
			targetSuperClass.equals(Proxy.class)) {

			Field field = targetSuperClass.getDeclaredField("h");

			field.setAccessible(true);

			target = field.get(target);
		}

		return target;
	}

	private void _scan(Object target) throws Exception {
		if (target == null) {
			return;
		}

		if (!_scanning) {
			return;
		}

		target = _resolveJavaProxy(target);

		String visitedId = null;

		try {
			visitedId = String.valueOf(System.identityHashCode(target));

			if (_visitedIds.contains(visitedId)) {
				return;
			}
		}
		catch (Exception e) {
			return;
		}

		_visitedIds.add(visitedId);

		Class<?> targetClass = target.getClass();

		if (targetClass.isArray()) {
			if (!_visitArrays) {
				return;
			}

			Class<?> componentTypeClass = targetClass.getComponentType();

			if (componentTypeClass.isPrimitive() == false) {
				Object[] array = (Object[])target;

				for (Object element : array) {
					_scan(element);
				}
			}
		}
		else if (_visitLists && (target instanceof List)) {
			_scanCollection((List<Object>)target);
		}
		else if (_visitMaps && (target instanceof Map)) {
			_scanMap((Map<Object, Object>)target);
		}
		else if (_visitSets && (target instanceof Set)) {
			_scanCollection((Set<Object>)target);
		}
		else if (_visitCollections && (target instanceof Collection)) {
			_scanCollection((Collection<Object>)target);
		}
		else {
			_scanObject(target);
		}
	}

	private void _scanCollection(Collection<Object> collection)
		throws Exception {

		for (Object element : collection) {
			if (!_scanning) {
				break;
			}

			_scan(element);
		}
	}

	private void _scanMap(Map<Object, Object> map) throws Exception {
		Set<Map.Entry<Object, Object>> entrySet = map.entrySet();

		for (Map.Entry<Object, Object> entry : entrySet) {
			if (!_scanning) {
				break;
			}

			Object key = entry.getKey();
			Object value = entry.getValue();

			String name = null;

			if (key != null) {
				name = key.toString();
			}

			if (_isAcceptName(name)) {
				_matchName(value, name);
				_scan(value);
			}
		}
	}

	private void _scanObject(Object target) throws Exception {
		Class<?> targetClass = target.getClass();

		if (!_isAcceptClass(targetClass)) {
			return;
		}

		while (targetClass != null) {
			Field[] fields = targetClass.getDeclaredFields();

			for (Field field : fields) {
				if (!_scanning) {
					break;
				}

				if (!_visitStaticFields) {
					if ((field.getModifiers() & Modifier.STATIC) != 0) {
						continue;
					}
				}

				String fieldName = field.getName();

				if (_isAcceptName(fieldName)) {
					_matchField(target, field, fieldName);

					field.setAccessible(true);

					Object fieldValue = field.get(target);

					if (fieldValue != null) {
						_scan(fieldValue);
					}
				}
			}

			targetClass = targetClass.getSuperclass();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeepNamedValueScanner.class);

	private long _elapsedTime;
	private String[] _excludedClassNames;
	private String[] _excludedNames;
	private String[] _includedClassNames;
	private Object _matchedValue;
	private int _matchingCount;
	private Map<String, Dataset> _nameDatasets;
	private boolean _scanning;
	private int _skipFirstCount;
	private boolean _trackUsageCount;
	private Map<String, Dataset> _typeDatasets;
	private final String _value;
	private boolean _visitArrays;
	private boolean _visitCollections;
	private Set<String> _visitedIds;
	private boolean _visitLists;
	private boolean _visitMaps;
	private boolean _visitSets;
	private boolean _visitStaticFields;

	private static class Dataset
		extends ObjectValuePair<String, IntegerWrapper>
		implements Comparable<Dataset> {

		@Override
		public int compareTo(Dataset dataset) {
			IntegerWrapper integerWrapper = dataset.getValue();

			return integerWrapper.compareTo(getValue());
		}

	}

}