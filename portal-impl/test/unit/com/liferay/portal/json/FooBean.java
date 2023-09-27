/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Igor Spasic
 */
public class FooBean {

	public FooBean() {
		_collection.add("element");
	}

	public Collection<Object> getCollection() {
		return _collection;
	}

	public String getName() {
		return _name;
	}

	public int getValue() {
		return _value;
	}

	public void setCollection(Collection<Object> collection) {
		_collection = collection;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValue(int value) {
		_value = value;
	}

	private Collection<Object> _collection = new HashSet<>();
	private String _name = "bar";
	private int _value = 173;

}