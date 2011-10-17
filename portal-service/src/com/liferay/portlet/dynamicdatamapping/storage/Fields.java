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

package com.liferay.portlet.dynamicdatamapping.storage;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class Fields implements Serializable {

	public boolean contains(String name) {
		return _fieldsMap.containsKey(name);
	}

	public Field get(String name) {
		return _fieldsMap.get(name);
	}

	public Set<String> getNames() {
		return _fieldsMap.keySet();
	}

	public Iterator<Field> iterator() {
		return iterator(null);
	}

	public Iterator<Field> iterator(Comparator<Field> comparator) {
		Collection<Field> fieldsCollection = _fieldsMap.values();

		List<Field> fieldsList = new ArrayList<Field>(fieldsCollection);

		if (comparator != null) {
			Collections.sort(fieldsList, comparator);
		}

		return fieldsList.iterator();
	}

	public void put(Field field) {
		_fieldsMap.put(field.getName(), field);
	}

	public Field remove(String name) {
		return _fieldsMap.remove(name);
	}

	private Map<String, Field> _fieldsMap = new HashMap<String, Field>();

}