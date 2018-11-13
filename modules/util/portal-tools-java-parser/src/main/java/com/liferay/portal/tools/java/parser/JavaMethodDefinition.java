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

package com.liferay.portal.tools.java.parser;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaMethodDefinition extends BaseJavaTerm {

	public boolean hasBody() {
		return _hasBody;
	}

	public void setHasBody(boolean hasBody) {
		_hasBody = hasBody;
	}

	public void setJavaAnnotations(List<JavaAnnotation> javaAnnotations) {
		_javaAnnotations = javaAnnotations;
	}

	public void setJavaSignature(JavaSignature javaSignature) {
		_javaSignature = javaSignature;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		if (_hasBody) {
			return _javaSignature.toString(indent, prefix, " {", maxLineLength);
		}

		return _javaSignature.toString(indent, prefix, ";", maxLineLength);
	}

	private boolean _hasBody;
	private List<JavaAnnotation> _javaAnnotations;
	private JavaSignature _javaSignature;

}