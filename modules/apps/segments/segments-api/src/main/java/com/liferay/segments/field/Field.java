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

package com.liferay.segments.field;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;

/**
 * Represents a segments criteria field.
 *
 * @author Eduardo García
 */
public final class Field implements Comparable<Field>, Serializable {

	public Field() {
	}

	public Field(String name, String label, String type) {
		this(name, label, type, Collections.emptyList(), null);
	}

	public Field(
		String name, String label, String type, List<Option> options,
		SelectEntity selectEntity) {

		_name = name;
		_label = label;
		_type = type;
		_options = options;
		_selectEntity = selectEntity;
	}

	@Override
	public int compareTo(Field field) {
		return _name.compareTo(field._name);
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public List<Option> getOptions() {
		return _options;
	}

	public SelectEntity getSelectEntity() {
		return _selectEntity;
	}

	public String getType() {
		return _type;
	}

	public static final class Option implements Serializable {

		public Option() {
		}

		public Option(String label, String value) {
			_label = label;
			_value = value;
		}

		public String getLabel() {
			return _label;
		}

		public String getValue() {
			return _value;
		}

		private String _label;
		private String _value;

	}

	public static final class SelectEntity implements Serializable {

		public SelectEntity() {
		}

		public SelectEntity(
			String id, String title, String uri, boolean multiple) {

			_id = id;
			_title = title;
			_uri = uri;
			_multiple = multiple;
		}

		public String getId() {
			return _id;
		}

		public String getTitle() {
			return _title;
		}

		public String getUri() {
			return _uri;
		}

		public boolean isMultiple() {
			return _multiple;
		}

		private String _id;
		private boolean _multiple;
		private String _title;
		private String _uri;

	}

	private String _label;
	private String _name;
	private List<Option> _options;
	private SelectEntity _selectEntity;
	private String _type;

}