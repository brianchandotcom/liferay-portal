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

package com.liferay.portal.xml.xpath;

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
 * @author Eduardo Lundgren
 */
public class LiferayNamespaceContextMap implements Serializable {

	public boolean contains(String prefix) {
		return _namespaceMap.containsKey(prefix);
	}

	public String get(String prefix) {
		return _namespaceMap.get(prefix);
	}

	public Collection<String> getNamespaces() {
		return _namespaceMap.values();
	}

	public Set<String> getPrefixes() {
		return _namespaceMap.keySet();
	}

	public Iterator<String> iterator() {
		return iterator(null);
	}

	public Iterator<String> iterator(Comparator<String> comparator) {
		Collection<String> namespaceCollection = _namespaceMap.values();

		List<String> namespaceList = new ArrayList<String>(namespaceCollection);

		if (comparator != null) {
			Collections.sort(namespaceList, comparator);
		}

		return namespaceList.iterator();
	}

	public void put(String prefix, String namespace) {
		_namespaceMap.put(prefix, namespace);
	}

	public String remove(String prefix) {
		return _namespaceMap.remove(prefix);
	}

	private Map<String, String> _namespaceMap = new HashMap<String, String>();

}