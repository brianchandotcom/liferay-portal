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

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public abstract class BaseJavaTerm implements JavaTerm {

	public BaseJavaTerm(
		String name, String content, String accessModifier, int lineNumber,
		boolean isAbstract, boolean isStatic) {

		_name = name;
		_content = content;
		_accessModifier = accessModifier;
		_lineNumber = lineNumber;
		_isAbstract = isAbstract;
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
	public int getLineNumber() {
		return _lineNumber;
	}

	@Override
	public int getLineNumber(int pos) {
		return _lineNumber + SourceUtil.getLineNumber(_content, pos) - 1;
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
	public boolean hasAnnotation(String... annotations) {
		for (String annotation : annotations) {
			Pattern pattern = Pattern.compile(
				StringBundler.concat(
					"(\\A|\n)", SourceUtil.getIndent(_content), "@", annotation,
					"(\\(|\n)"));

			Matcher matcher = pattern.matcher(_content);

			if (matcher.find()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isAbstract() {
		return _isAbstract;
	}

	@Override
	public boolean isJavaClass() {
		if (this instanceof JavaClass) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isJavaConstructor() {
		if (this instanceof JavaConstructor) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isJavaMethod() {
		if (this instanceof JavaMethod) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isJavaStaticBlock() {
		if (this instanceof JavaStaticBlock) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isJavaVariable() {
		if (this instanceof JavaVariable) {
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
	private final boolean _isAbstract;
	private final boolean _isStatic;
	private final int _lineNumber;
	private final String _name;
	private JavaClass _parentJavaClass;

}