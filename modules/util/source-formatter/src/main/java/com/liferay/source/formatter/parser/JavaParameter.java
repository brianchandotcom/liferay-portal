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

import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JavaParameter {

	public JavaParameter(
		String parameterName, String parameterType,
		Set<String> parameterAnnotations, boolean isFinal) {

		_parameterName = parameterName;
		_parameterType = parameterType;
		_parameterAnnotations = parameterAnnotations;
		_isFinal = isFinal;
	}

	public Set<String> getParameterAnnotations() {
		return _parameterAnnotations;
	}

	public String getParameterName() {
		return _parameterName;
	}

	public String getParameterType() {
		return _parameterType;
	}

	public boolean isFinal() {
		return _isFinal;
	}

	private final boolean _isFinal;
	private final Set<String> _parameterAnnotations;
	private final String _parameterName;
	private final String _parameterType;

}