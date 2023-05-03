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

import java.util.Objects;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JavaParameter {

	public JavaParameter(
		String parameterName, JavaClassType parameterType,
		Set<String> parameterAnnotations, boolean isFinal) {

		_parameterName = parameterName;
		_parameterType = parameterType;
		_parameterAnnotations = parameterAnnotations;
		_isFinal = isFinal;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof JavaParameter)) {
			return false;
		}

		JavaParameter otherJavaParameter = (JavaParameter)object;

		if ((_isFinal == otherJavaParameter._isFinal) &&
			Objects.equals(
				_parameterAnnotations,
				otherJavaParameter._parameterAnnotations) &&
			Objects.equals(_parameterName, otherJavaParameter._parameterName) &&
			Objects.equals(
				getParameterType(true),
				otherJavaParameter.getParameterType(true))) {

			return true;
		}

		return false;
	}

	public Set<String> getParameterAnnotations() {
		return _parameterAnnotations;
	}

	public String getParameterName() {
		return _parameterName;
	}

	public String getParameterType() {
		return getParameterType(false);
	}

	public String getParameterType(boolean fullyQualifiedName) {
		return _parameterType.toString(fullyQualifiedName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			_isFinal, _parameterAnnotations, _parameterName,
			getParameterType(true));
	}

	public boolean isFinal() {
		return _isFinal;
	}

	private final boolean _isFinal;
	private final Set<String> _parameterAnnotations;
	private final String _parameterName;
	private final JavaClassType _parameterType;

}