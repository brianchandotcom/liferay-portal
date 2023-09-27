/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Peter Shin
 */
public class JavaMethodParameter {

	public JavaMethodParameter(String parameterName, String parameterType) {
		if (!Validator.isVariableName(parameterName)) {
			_parameterName = parameterName + "Value";
		}
		else {
			_parameterName = parameterName;
		}

		_parameterType = parameterType;
	}

	public String getParameterName() {
		return _parameterName;
	}

	public String getParameterType() {
		return _parameterType;
	}

	private final String _parameterName;
	private final String _parameterType;

}