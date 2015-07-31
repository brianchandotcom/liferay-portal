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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaTerm {

	public static final int TYPE_CLASS_PRIVATE = 24;

	public static final int TYPE_CLASS_PRIVATE_STATIC = 23;

	public static final int TYPE_CLASS_PROTECTED = 16;

	public static final int TYPE_CLASS_PROTECTED_STATIC = 15;

	public static final int TYPE_CLASS_PUBLIC = 8;

	public static final int TYPE_CLASS_PUBLIC_STATIC = 7;

	public static final int TYPE_CONSTRUCTOR_PRIVATE = 18;

	public static final int TYPE_CONSTRUCTOR_PROTECTED = 10;

	public static final int TYPE_CONSTRUCTOR_PUBLIC = 4;

	public static final int TYPE_METHOD_PRIVATE = 19;

	public static final int TYPE_METHOD_PRIVATE_STATIC = 17;

	public static final int TYPE_METHOD_PROTECTED = 11;

	public static final int TYPE_METHOD_PROTECTED_STATIC = 9;

	public static final int TYPE_METHOD_PUBLIC = 5;

	public static final int TYPE_METHOD_PUBLIC_STATIC = 3;

	public static final int TYPE_STATIC_BLOCK = 21;

	public static final int TYPE_VARIABLE_PRIVATE = 22;

	public static final int TYPE_VARIABLE_PRIVATE_STATIC = 20;

	public static final int TYPE_VARIABLE_PROTECTED = 14;

	public static final int TYPE_VARIABLE_PROTECTED_STATIC = 12;

	public static final int TYPE_VARIABLE_PUBLIC = 6;

	public static final int TYPE_VARIABLE_PUBLIC_STATIC = 1;

	public JavaTerm(
		String name, int type, String content, int lineCount, String indent) {

		_name = name;
		_type = type;
		_content = content;
		_lineCount = lineCount;
		_indent = indent;
	}

	public String getContent() {
		return _content;
	}

	public int getLineCount() {
		return _lineCount;
	}

	public String getName() {
		return _name;
	}

	public List<String> getParameterNames() {
		if (_parameterNames == null) {
			readParameterNamesAndTypes();
		}

		return _parameterNames;
	}

	public List<String> getParameterTypes() {
		if (_parameterTypes == null) {
			readParameterNamesAndTypes();
		}

		return _parameterTypes;
	}

	public int getType() {
		return _type;
	}

	public boolean hasAnnotation(String annotation) {
		if (_content.contains(_indent + StringPool.AT + annotation + "\n") ||
			_content.contains(
				_indent + StringPool.AT + annotation + 
					StringPool.OPEN_PARENTHESIS)) {

			return true;
		}

		return false;
	}

	public boolean isClass() {
		if ((_type == TYPE_CLASS_PRIVATE) ||
			(_type == TYPE_CLASS_PRIVATE_STATIC) ||
			(_type == TYPE_CLASS_PROTECTED) ||
			(_type == TYPE_CLASS_PROTECTED_STATIC) ||
			(_type == TYPE_CLASS_PUBLIC) ||
			(_type == TYPE_CLASS_PUBLIC_STATIC)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isConstructor() {
		if ((_type == TYPE_CONSTRUCTOR_PRIVATE) ||
			(_type == TYPE_CONSTRUCTOR_PROTECTED) ||
			(_type == TYPE_CONSTRUCTOR_PUBLIC)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isMethod() {
		if ((_type == TYPE_METHOD_PRIVATE) ||
			(_type == TYPE_METHOD_PRIVATE_STATIC) ||
			(_type == TYPE_METHOD_PROTECTED) ||
			(_type == TYPE_METHOD_PROTECTED_STATIC) ||
			(_type == TYPE_METHOD_PUBLIC) ||
			(_type == TYPE_METHOD_PUBLIC_STATIC)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isPrivate() {
		if ((_type == TYPE_CLASS_PRIVATE) ||
			(_type == TYPE_CLASS_PRIVATE_STATIC) ||
			(_type == TYPE_CONSTRUCTOR_PRIVATE) ||
			(_type == TYPE_METHOD_PRIVATE) ||
			(_type == TYPE_METHOD_PRIVATE_STATIC) ||
			(_type == TYPE_VARIABLE_PRIVATE) ||
			(_type == TYPE_VARIABLE_PRIVATE_STATIC)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isProtected() {
		if ((_type == TYPE_CLASS_PROTECTED) ||
			(_type == TYPE_CLASS_PROTECTED_STATIC) ||
			(_type == TYPE_CONSTRUCTOR_PROTECTED) ||
			(_type == TYPE_METHOD_PROTECTED) ||
			(_type == TYPE_METHOD_PROTECTED_STATIC) ||
			(_type == TYPE_VARIABLE_PROTECTED) ||
			(_type == TYPE_VARIABLE_PROTECTED_STATIC)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isPublic() {
		if ((_type == TYPE_CLASS_PUBLIC) ||
			(_type == TYPE_CLASS_PUBLIC_STATIC) ||
			(_type == TYPE_CONSTRUCTOR_PUBLIC) ||
			(_type == TYPE_METHOD_PUBLIC) ||
			(_type == TYPE_METHOD_PUBLIC_STATIC) ||
			(_type == TYPE_VARIABLE_PUBLIC) ||
			(_type == TYPE_VARIABLE_PUBLIC_STATIC)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isStatic() {
		if ((_type == TYPE_CLASS_PRIVATE_STATIC) ||
			(_type == TYPE_CLASS_PROTECTED_STATIC) ||
			(_type == TYPE_CLASS_PUBLIC_STATIC) ||
			(_type == TYPE_METHOD_PRIVATE_STATIC) ||
			(_type == TYPE_METHOD_PROTECTED_STATIC) ||
			(_type == TYPE_METHOD_PUBLIC_STATIC) ||
			(_type == TYPE_VARIABLE_PRIVATE_STATIC) ||
			(_type == TYPE_VARIABLE_PROTECTED_STATIC) ||
			(_type == TYPE_VARIABLE_PUBLIC_STATIC)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isVariable() {
		if ((_type == TYPE_VARIABLE_PRIVATE) ||
			(_type == TYPE_VARIABLE_PRIVATE_STATIC) ||
			(_type == TYPE_VARIABLE_PROTECTED) ||
			(_type == TYPE_VARIABLE_PROTECTED_STATIC) ||
			(_type == TYPE_VARIABLE_PUBLIC) ||
			(_type == TYPE_VARIABLE_PUBLIC_STATIC)) {

			return true;
		}
		else {
			return false;
		}
	}

	public void setType(int type) {
		_type = type;
	}

	protected void readParameterNamesAndTypes() {
		_parameterNames = new ArrayList<>();
		_parameterTypes = new ArrayList<>();

		if (!isConstructor() && !isMethod()) {
			return;
		}

		int x = -1;

		if (isPrivate()) {
			x = _content.indexOf("\tprivate ");
		}
		else if (isProtected()) {
			x = _content.indexOf("\tprotected ");
		}
		else if (isPublic()) {
			x = _content.indexOf("\tpublic ");
		}

		if (x == -1) {
			return;
		}

		x = _content.indexOf(CharPool.OPEN_PARENTHESIS, x);

		int y = x;

		String parameters = StringPool.BLANK;

		while (true) {
			y = _content.indexOf(CharPool.CLOSE_PARENTHESIS, y + 1);

			if (y == -1) {
				return;
			}

			parameters = _content.substring(x + 1, y);

			int closeParenthesesCount = StringUtil.count(
				parameters, StringPool.CLOSE_PARENTHESIS);
			int openParenthesesCount = StringUtil.count(
				parameters, StringPool.OPEN_PARENTHESIS);

			if (closeParenthesesCount == openParenthesesCount) {
				break;
			}
		}

		parameters = StringUtil.replace(
			parameters, new String[] {StringPool.TAB, StringPool.NEW_LINE},
			new String[] {StringPool.BLANK, StringPool.SPACE});

		for (x = 0;;) {
			parameters = StringUtil.trim(parameters);

			if (parameters.startsWith(StringPool.AT)) {
				parameters = stripAnnotation(parameters);
			}

			if (parameters.startsWith("final ")) {
				parameters = parameters.substring(6);
			}

			x = parameters.indexOf(CharPool.SPACE, x + 1);

			if (x == -1) {
				return;
			}

			String parameterType = parameters.substring(0, x);

			int greaterThanCount = StringUtil.count(
				parameterType, StringPool.GREATER_THAN);
			int lessThanCount = StringUtil.count(
				parameterType, StringPool.LESS_THAN);

			if (greaterThanCount != lessThanCount) {
				continue;
			}

			_parameterTypes.add(parameterType);

			y = parameters.indexOf(CharPool.COMMA, x);

			if (y == -1) {
				_parameterNames.add(parameters.substring(x + 1));

				return;
			}

			_parameterNames.add(parameters.substring(x + 1, y));

			parameters = parameters.substring(y + 1);

			x = 0;
		}
	}

	protected String stripAnnotation(String parameters) {
		int pos = -1;

		while (true) {
			pos = parameters.indexOf(CharPool.SPACE, pos + 1);

			if (pos == -1) {
				return parameters;
			}

			String annotation = parameters.substring(0, pos);

			int closeParenthesesCount = StringUtil.count(
				annotation, StringPool.CLOSE_PARENTHESIS);
			int greaterThanCount = StringUtil.count(
				annotation, StringPool.GREATER_THAN);
			int lessThanCount = StringUtil.count(
				annotation, StringPool.LESS_THAN);
			int openParenthesesCount = StringUtil.count(
				annotation, StringPool.OPEN_PARENTHESIS);

			if ((closeParenthesesCount == openParenthesesCount) &&
				(greaterThanCount == lessThanCount)) {

				return parameters.substring(pos + 1);
			}
		}
	}

	private String _content;
	private String _indent;
	private int _lineCount;
	private String _name;
	private List<String> _parameterNames;
	private List<String> _parameterTypes;
	private int _type;

}