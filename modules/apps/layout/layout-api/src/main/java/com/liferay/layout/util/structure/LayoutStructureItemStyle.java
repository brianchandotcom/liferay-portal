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

package com.liferay.layout.util.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov
 */
public class LayoutStructureItemStyle {

	public LayoutStructureItemStyle(
		String defaultValue, String label, String name, boolean responsive) {

		_defaultValue = defaultValue;
		_label = label;
		_name = name;
		_responsive = responsive;

		_dependencies = new ArrayList<>();
		_validValues = new ArrayList<>();
	}

	public void addDependency(
		LayoutStructureItemStyleValue layoutStructureItemStyleValue) {

		_dependencies.add(layoutStructureItemStyleValue);
	}

	public void addValidValue(
		LayoutStructureItemStyleValue layoutStructureItemStyleValue) {

		_validValues.add(layoutStructureItemStyleValue);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof LayoutStructureItemStyle)) {
			return false;
		}

		LayoutStructureItemStyle layoutStructureItemStyle =
			(LayoutStructureItemStyle)object;

		return _name.equals(layoutStructureItemStyle.getName());
	}

	public String getDefaultValue() {
		return _defaultValue;
	}

	public List<LayoutStructureItemStyleValue> getDependencies() {
		return _dependencies;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public List<LayoutStructureItemStyleValue> getValidValues() {
		return _validValues;
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	public boolean isResponsive() {
		return _responsive;
	}

	private final String _defaultValue;
	private final List<LayoutStructureItemStyleValue> _dependencies;
	private final String _label;
	private final String _name;
	private final boolean _responsive;
	private final List<LayoutStructureItemStyleValue> _validValues;

}