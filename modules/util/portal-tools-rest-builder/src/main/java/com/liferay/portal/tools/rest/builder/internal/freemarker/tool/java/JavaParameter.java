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

package com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Peter Shin
 */
public class JavaParameter {

	public JavaParameter(
		List<String> parameterAnnotations, String parameterName,
		String parameterType) {

		_parameterName = parameterName;
		_parameterType = parameterType;

		if (parameterAnnotations != null) {
			_parameterAnnotations.addAll(parameterAnnotations);
		}
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

	private final Set<String> _parameterAnnotations = new TreeSet<>();
	private final String _parameterName;
	private final String _parameterType;

}