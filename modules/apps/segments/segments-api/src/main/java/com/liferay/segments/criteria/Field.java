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

package com.liferay.segments.criteria;

import java.io.Serializable;

import java.util.List;

/**
 * Represents a segments criteria field.
 *
 * @author Eduardo García
 * @review
 */
public final class Field implements Comparable<Field>, Serializable {

	public Field() {
	}

	public Field(String name, String label, String type, List<Option> options) {
		_name = name;
		_label = label;
		_type = type;
		_options = options;
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

	public String getType() {
		return _type;
	}

	public static final class Option implements Serializable {

		public Option() {
		}

		public Option(String name, String label) {
			_name = name;
			_label = label;
		}

		public String getLabel() {
			return _label;
		}

		public String getName() {
			return _name;
		}

		private String _label;
		private String _name;

	}

	private String _label;
	private String _name;
	private List<Option> _options;
	private String _type;

}