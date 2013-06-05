/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

/**
 * @author Igor Spasic
 */
public class FooBean {

	public FooBean() {
		_collection = new HashSet<Object>();
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

	private Collection<Object> _collection;
	private String _name = "bar";
	private int _value = 173;

}