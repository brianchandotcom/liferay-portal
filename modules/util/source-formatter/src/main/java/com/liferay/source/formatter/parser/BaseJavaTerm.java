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

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Hugo Huijser
 */
public abstract class BaseJavaTerm implements JavaTerm {

	public BaseJavaTerm(
		String name, String content, String accessModifier, boolean isStatic) {

		_name = name;
		_content = content;
		_accessModifier = accessModifier;
		_isStatic = isStatic;
	}

	@Override
	public String getAccessModifier() {
		return _accessModifier;
	}

	@Override
	public String getContent() {
		return _content;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public JavaClass getParentJavaClass() {
		return _parentJavaClass;
	}

	@Override
	public JavaSignature getSignature() {
		return null;
	}

	@Override
	public boolean hasAnnotation(String annotation) {
		if (_content.contains("\t@" + annotation + "\n") ||
			_content.contains(
				"\t@" + annotation + StringPool.OPEN_PARENTHESIS)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isStatic() {
		return _isStatic;
	}

	@Override
	public void setParentJavaClass(JavaClass javaClass) {
		_parentJavaClass = javaClass;
	}

	private final String _accessModifier;
	private final String _content;
	private final boolean _isStatic;
	private final String _name;
	private JavaClass _parentJavaClass;

}