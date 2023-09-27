/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.parser;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JavaSignature {

	public void addParameter(
		String parameterName, String parameterType,
		Set<String> parameterAnnotations, boolean isFinal, String packageName,
		List<String> importNames) {

		_parameters.add(
			new JavaParameter(
				parameterName,
				new JavaClassType(parameterType, packageName, importNames),
				parameterAnnotations, isFinal));
	}

	public List<JavaParameter> getParameters() {
		return _parameters;
	}

	public String getReturnType() {
		return getReturnType(false);
	}

	public String getReturnType(boolean fullyQualifiedName) {
		if (_returnType != null) {
			return _returnType.toString(fullyQualifiedName);
		}

		return StringPool.BLANK;
	}

	public void setReturnType(
		String returnTypeString, String packageName, List<String> importNames) {

		if (Validator.isNotNull(returnTypeString)) {
			_returnType = new JavaClassType(
				returnTypeString, packageName, importNames);
		}
	}

	@Override
	public String toString() {
		return toString(false);
	}

	public String toString(boolean fullyQualifiedName) {
		StringBundler sb = new StringBundler((_parameters.size() * 2) + 1);

		sb.append(CharPool.OPEN_PARENTHESIS);

		for (JavaParameter parameter : _parameters) {
			sb.append(parameter.getParameterType(fullyQualifiedName));
			sb.append(CharPool.COMMA);
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append(CharPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private final List<JavaParameter> _parameters = new ArrayList<>();
	private JavaClassType _returnType;

}