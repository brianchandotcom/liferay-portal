/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jodd.mutable.MutableInteger;

import jodd.util.KeyValue;

/**
 * @author Igor Spasic
 */
public class DeepNamedValueScanner {

	public DeepNamedValueScanner(String value) {
		this._value = value.toLowerCase();
	}

	public DeepNamedValueScanner(String value, boolean visitFlag) {
		this(value);

		_visitArrays = visitFlag;
		_visitCollections = visitFlag;
		_visitLists = visitFlag;
		_visitSets = visitFlag;
		_visitMaps = visitFlag;
		_visitStaticFields = visitFlag;
	}

	public long getElapsed() {
		return _elapsed;
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

	public boolean isFounded() {
		return _scanning == false;
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

	public void printStats(int topCount) {
		if (!_trackUsageCount) {
			return;
		}

		System.out.println("\nType usage statistics");
		System.out.println("---------------------");

		_printStats(_typesStats.values(), topCount);

		System.out.println("\nNames usage statistics");
		System.out.println("----------------------");

		_printStats(_namesStats.values(), topCount);
	}

	public boolean scan(Object target) throws Exception {
		_elapsed = System.currentTimeMillis();

		_visitedIds = new HashSet<String>();

		_scanning = true;

		_scan(target);

		_visitedIds = null;

		_elapsed = System.currentTimeMillis() - _elapsed;

		return isFounded();
	}

	public void setExcludedClassNames(String... excludedClassNames) {
		_excludedClassNames = excludedClassNames;

		if (excludedClassNames != null) {
			for (int i = 0; i < excludedClassNames.length; i++) {
				excludedClassNames[i] = excludedClassNames[i].toLowerCase();
			}
		}
	}

	public void setExcludedNames(String... excludedNames) {
		_excludedNames = excludedNames;

		if (excludedNames != null) {
			for (int i = 0; i < excludedNames.length; i++) {
				excludedNames[i] = excludedNames[i].toLowerCase();
			}
		}
	}

	public void setIncludedClassNames(String... includedClassNames) {
		_includedClassNames = includedClassNames;

		if (includedClassNames != null) {
			for (int i = 0; i < includedClassNames.length; i++) {
				includedClassNames[i] = includedClassNames[i].toLowerCase();
			}
		}
	}

	public void setSkipFirstCount(int skipFirstCount) {
		this._skipFirstCount = skipFirstCount;
	}

	public void setTrackUsageCount(boolean trackUsageCount) {
		_trackUsageCount = trackUsageCount;

		if (trackUsageCount) {
			_typesStats = new HashMap<String, KeyValue>();

			_namesStats = new HashMap<String, KeyValue>();
		}
	}

	public void setVisitArrays(boolean visitArrays) {
		this._visitArrays = visitArrays;
	}

	public void setVisitCollections(boolean visitCollections) {
		this._visitCollections = visitCollections;
	}

	public void setVisitLists(boolean visitLists) {
		_visitLists = visitLists;
	}

	public void setVisitMaps(boolean visitMaps) {
		this._visitMaps = visitMaps;
	}

	public void setVisitSets(boolean visitSets) {
		_visitSets = visitSets;
	}

	public void setVisitStaticFields(boolean visitStaticFields) {
		_visitStaticFields = visitStaticFields;
	}

	private boolean _acceptClass(Class targetClass) {

		String targetClassName = targetClass.getName();

		targetClassName = targetClassName.toLowerCase();

		if (targetClassName.startsWith("java.")) {
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
			_incrementUsageCount(_typesStats, targetClass.getName());
		}

		return true;
	}

	private boolean _acceptName(String name) {
		if (name == null) {
			return true;
		}

		name = name.toLowerCase();

		if (_excludedNames != null) {
			for (String excludedNames : _excludedNames) {
				if (name.contains(excludedNames)) {

					return false;
				}
			}
		}

		if (_trackUsageCount) {
			_incrementUsageCount(_namesStats, name);
		}

		return true;
	}

	private void _incrementUsageCount(
		HashMap<String, KeyValue> statMap, String name) {
		KeyValue<String, MutableInteger> keyValue = statMap.get(name);

		if (keyValue == null) {
			keyValue = new KeyValue<String, MutableInteger>();

			keyValue.setKey(name);
			keyValue.setValue(new MutableInteger());

			statMap.put(name, keyValue);
		}

		keyValue.getValue().value++;
	}

	private void _matchField(Object target, Field field, String name)
		throws IllegalAccessException {

		if (name == null) {
			return;
		}

		_matchingCount++;

		if (name.toLowerCase().contains(_value)) {
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

		if (name.toLowerCase().contains(_value)) {
			if (_skipFirstCount > 0) {
				_skipFirstCount--;
				return;
			}

			_matchedValue = value;

			_scanning = false;
		}
	}

	private void _printStats(Collection<KeyValue> values, int topCount) {

		List<KeyValue<String, MutableInteger>> list =
			new ArrayList<KeyValue<String, MutableInteger>>();

		for (KeyValue value : values) {
			list.add(value);
		}

		Collections.sort(
			list, new Comparator<KeyValue<String, MutableInteger>>() {

			public int compare(
				KeyValue<String, MutableInteger> keyValue1,
				KeyValue<String, MutableInteger> keyValue2) {

				return keyValue2.getValue().value - keyValue1.getValue().value;
			}
		});

		for (KeyValue<String, MutableInteger> keyValue : list) {
			System.out.println(keyValue.getValue() + " " + keyValue.getKey());

			topCount--;

			if (topCount == 0) {
				break;
			}
		}

	}

	private Object _resolveJavaProxy(Object target)
		throws IllegalAccessException, NoSuchFieldException {

		Class targetClass = target.getClass();
		Class targetSuperClass = targetClass.getSuperclass();

		if ((targetSuperClass != null) &&
			targetSuperClass.equals(Proxy.class)) {

			Field hField = targetSuperClass.getDeclaredField("h");

			hField.setAccessible(true);

			target = hField.get(target);
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

		String id;

		try {
			id = String.valueOf(System.identityHashCode(target));

			if (_visitedIds.contains(id)) {
				return;
			}
		}
		catch (Exception e) {
			return;
		}

		_visitedIds.add(id);

		Class targetClass = target.getClass();

		if (targetClass.isArray()) {
			if (!_visitArrays) {
				return;
			}

			Class componentType = targetClass.getComponentType();

			if (componentType.isPrimitive() == false) {
				Object[] array = (Object[])target;

				for (Object element : array) {
					_scan(element);
				}
			}

		}
		else if (_visitMaps && target instanceof Map) {
			_scanMap((Map)target);
		}
		else if (_visitLists && target instanceof List) {
			_scanCollection((List)target);
		}
		else if (_visitSets && target instanceof Set) {
			_scanCollection((Set)target);
		}
		else if (_visitCollections && target instanceof Collection) {
			_scanCollection((Collection)target);
		}
		else {
			_scanObject(target);
		}
	}

	private void _scanCollection(Collection collection) throws Exception {
		for (Object element : collection) {
			if (!_scanning) {
				break;
			}

			_scan(element);
		}
	}

	private void _scanMap(Map map) throws Exception {
		Set<Map.Entry> entrySet = map.entrySet();

		for (Map.Entry entry : entrySet) {
			if (!_scanning) {
				break;
			}

			Object key = entry.getKey();
			Object value = entry.getValue();

			String keyName = null;

			if (key != null) {
				keyName = key.toString();
			}

			if (_acceptName(keyName)) {
				_matchName(value, keyName);
				_scan(value);
			}
		}
	}

	private void _scanObject(Object target) throws Exception {

		Class targetClass = target.getClass();

		if (!_acceptClass(targetClass)) {
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

				if (_acceptName(fieldName)) {

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

	private long _elapsed;
	private String[] _excludedClassNames;
	private String[] _excludedNames;
	private String[] _includedClassNames;
	private Object _matchedValue;
	private int _matchingCount;
	private HashMap<String, KeyValue> _namesStats;
	private boolean _scanning;
	private int _skipFirstCount;
	private boolean _trackUsageCount;
	private HashMap<String, KeyValue> _typesStats;
	private final String _value;
	private boolean _visitArrays;
	private boolean _visitCollections;
	private boolean _visitLists;
	private boolean _visitMaps;
	private boolean _visitSets;
	private boolean _visitStaticFields;
	private Set<String> _visitedIds;

}