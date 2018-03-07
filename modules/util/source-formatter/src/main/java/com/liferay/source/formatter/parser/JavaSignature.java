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

package com.liferay.source.formatter.parser;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JavaSignature {

	public void addParameter(
		String parameterName, String parameterType,
		Set<String> parameterAnnotations, boolean isFinal) {

		_parameters.add(
			new JavaParameter(
				parameterName, parameterType, parameterAnnotations, isFinal));
	}

	public List<JavaParameter> getParameters() {
		return _parameters;
	}

	public String getReturnType() {
		return _returnType;
	}

	public void setReturnType(String returnType) {
		_returnType = returnType;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(_parameters.size() * 2 + 1);

		sb.append(CharPool.OPEN_PARENTHESIS);

		for (JavaParameter parameter : _parameters) {
			sb.append(parameter.getParameterType());
			sb.append(CharPool.COMMA);
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append(CharPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private final List<JavaParameter> _parameters = new ArrayList<>();
	private String _returnType;

}