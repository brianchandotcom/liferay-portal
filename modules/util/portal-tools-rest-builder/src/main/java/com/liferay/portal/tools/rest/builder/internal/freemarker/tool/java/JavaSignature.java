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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Peter Shin
 */
public class JavaSignature {

	public JavaSignature(
		List<JavaParameter> javaParameters, List<String> methodAnnotations,
		String methodName, String returnType) {

		_methodName = methodName;
		_returnType = returnType;

		if (javaParameters != null) {
			_javaParameters.addAll(javaParameters);
		}

		if (methodAnnotations != null) {
			_methodAnnotations.addAll(methodAnnotations);
		}
	}

	public List<JavaParameter> getJavaParameters() {
		return _javaParameters;
	}

	public Set<String> getMethodAnnotations() {
		return _methodAnnotations;
	}

	public String getMethodName() {
		return _methodName;
	}

	public String getReturnType() {
		return _returnType;
	}

	private final List<JavaParameter> _javaParameters = new ArrayList<>();
	private final Set<String> _methodAnnotations = new TreeSet<>();
	private final String _methodName;
	private final String _returnType;

}