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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import java.util.Objects;

/**
 * @author Marcela Cunha
 */
public class DataFieldOption {

	public DataFieldOption() {
	}

	public DataFieldOption(String label, String value) {
		_label = label;
		_value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DataFieldOption)) {
			return false;
		}

		DataFieldOption dataFieldOption = (DataFieldOption)obj;

		if (Objects.equals(_value, dataFieldOption._value)) {
			return true;
		}

		return false;
	}

	public String getLabel() {
		return _label;
	}

	public String getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		if (_value != null) {
			return _value.hashCode();
		}

		return 0;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setValue(String value) {
		_value = value;
	}

	private String _label;
	private String _value;

}