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

/**
 * @author Pavel Savinov
 */
public class LayoutStructureItemStyleValue {

	public LayoutStructureItemStyleValue(
		String label, String styleName, String value) {

		_label = label;
		_styleName = styleName;
		_value = value;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof LayoutStructureItemStyleValue)) {
			return false;
		}

		LayoutStructureItemStyleValue layoutStructureItemStyleValue =
			(LayoutStructureItemStyleValue)object;

		return _value.equals(layoutStructureItemStyleValue.getValue());
	}

	public String getLabel() {
		return _label;
	}

	public String getStyleName() {
		return _styleName;
	}

	public String getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		return _value.hashCode();
	}

	private final String _label;
	private final String _styleName;
	private final String _value;

}