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

package com.liferay.user.associated.data.web.internal.display;

import com.liferay.portal.kernel.util.KeyValuePair;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author William Newbury
 */
public class UADEntity<T> {

	public UADEntity(
		T entity, Serializable primaryKey, String editURL, boolean inTrash,
		Class<?> typeClass, boolean userOwned, String viewURL) {

		_entity = entity;
		_primaryKey = primaryKey;
		_editURL = editURL;
		_inTrash = inTrash;
		_typeClass = typeClass;
		_userOwned = userOwned;
		_viewURL = viewURL;
	}

	public void addColumnEntry(String key, Object value) {
		_columnEntries.add(new KeyValuePair(key, String.valueOf(value)));
	}

	public List<KeyValuePair> getColumnEntries() {
		if (_columnEntries.isEmpty()) {
			_columnEntries.add(
				new KeyValuePair("primaryKey", String.valueOf(_primaryKey)));
			_columnEntries.add(new KeyValuePair("editURL", _editURL));
		}

		return _columnEntries;
	}

	public Object getColumnEntry(String key) {
		for (KeyValuePair columnEntry : _columnEntries) {
			if (key.equals(columnEntry.getKey())) {
				return columnEntry.getValue();
			}
		}

		return null;
	}

	public String getEditURL() {
		return _editURL;
	}

	public T getEntity() {
		return _entity;
	}

	public Serializable getPrimaryKey() {
		return _primaryKey;
	}

	public Class<?> getTypeClass() {
		return _typeClass;
	}

	public String getViewURL() {
		return _viewURL;
	}

	public boolean isInTrash() {
		return _inTrash;
	}

	public boolean isUserOwned() {
		return _userOwned;
	}

	private final List<KeyValuePair> _columnEntries = new ArrayList<>();
	private final String _editURL;
	private final T _entity;
	private final boolean _inTrash;
	private final Serializable _primaryKey;
	private final Class<?> _typeClass;
	private final boolean _userOwned;
	private final String _viewURL;

}