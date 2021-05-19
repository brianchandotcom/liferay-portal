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

package com.liferay.dynamic.data.mapping.form.builder.internal.util;

import com.liferay.petra.lang.HashUtil;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class DDMExpressionFunctionMetadata {

	public DDMExpressionFunctionMetadata(
		String name, String label, String returnClassName,
		String[] parameterClassNames) {

		_name = name;
		_label = label;
		_returnClassName = returnClassName;
		_parameterClassNames = parameterClassNames;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMExpressionFunctionMetadata)) {
			return false;
		}

		DDMExpressionFunctionMetadata ddmExpressionFunctionMetadata =
			(DDMExpressionFunctionMetadata)object;

		if (Objects.equals(_label, ddmExpressionFunctionMetadata._label) &&
			Objects.equals(_name, ddmExpressionFunctionMetadata._name) &&
			Arrays.equals(
				_parameterClassNames,
				ddmExpressionFunctionMetadata._parameterClassNames) &&
			Objects.equals(
				_returnClassName,
				ddmExpressionFunctionMetadata._returnClassName)) {

			return true;
		}

		return false;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public String[] getParameterClassNames() {
		return _parameterClassNames;
	}

	public String getReturnClassName() {
		return _returnClassName;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _label);

		hash = HashUtil.hash(hash, _name);

		for (String parameterClassName : _parameterClassNames) {
			hash = HashUtil.hash(hash, parameterClassName);
		}

		return HashUtil.hash(hash, _returnClassName);
	}

	private final String _label;
	private final String _name;
	private final String[] _parameterClassNames;
	private final String _returnClassName;

}