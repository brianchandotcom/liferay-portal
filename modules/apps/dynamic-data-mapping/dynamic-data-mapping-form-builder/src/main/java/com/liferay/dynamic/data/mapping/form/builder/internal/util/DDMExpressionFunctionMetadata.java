/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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