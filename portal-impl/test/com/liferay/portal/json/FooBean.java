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

package com.liferay.portal.json;

import java.util.Collection;
import java.util.HashSet;
public class FooBean {

	public FooBean() {
		_collection = new HashSet();
		_collection.add("element");
	}

	public Collection getCollection() {
		return _collection;
	}

	public String getName() {
		return _name;
	}

	public int getValue() {
		return _value;
	}

	public void setCollection(Collection collection) {
		this._collection = collection;
	}

	public void setName(String name) {
		this._name = name;
	}

	public void setValue(int value) {
		this._value = value;
	}

	private Collection _collection;
	private String _name = "bar";
	private int _value = 173;

}