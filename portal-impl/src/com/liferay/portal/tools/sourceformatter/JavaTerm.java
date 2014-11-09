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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public JavaTerm(String name, int type, String content, int lineCount) {
		_name = name;
		_type = type;
		_content = content;
		_lineCount = lineCount;
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

	public void setContent(String content) {
		_content = content;
	}

	public void setLineCount(int lineCount) {
		_lineCount = lineCount;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setParameterTypes(List<String> parameterTypes) {
		_parameterTypes = parameterTypes;
	}

	public void setType(int type) {
		_type = type;
	}

	protected void readParameterNamesAndTypes() {
		_parameterNames = new ArrayList<String>();
		_parameterTypes = new ArrayList<String>();

		if (!isConstructor() && !isMethod()) {
			return;
		}

		Matcher matcher = _parameterTypesPattern.matcher(_content);

		if (!matcher.find()) {
			return;
		}

		String parameters = matcher.group(3);

		if (Validator.isNull(parameters)) {
			return;
		}

		parameters = StringUtil.replace(
			parameters, new String[] {StringPool.TAB, StringPool.NEW_LINE},
			new String[] {StringPool.BLANK, StringPool.SPACE});

		for (int x = 0;;) {
			parameters = StringUtil.trim(parameters);

			if (parameters.startsWith("final ")) {
				parameters = parameters.substring(6);
			}

			x = parameters.indexOf(StringPool.SPACE, x + 1);

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

			int y = parameters.indexOf(StringPool.COMMA, x);

			if (y == -1) {
				_parameterNames.add(parameters.substring(x + 1));

				return;
			}

			_parameterNames.add(parameters.substring(x + 1, y));

			parameters = parameters.substring(y + 1);

			x = 0;
		}
	}

	private String _content;
	private int _lineCount;
	private String _name;
	private List<String> _parameterNames;
	private List<String> _parameterTypes;
	private final Pattern _parameterTypesPattern = Pattern.compile(
		"\t(private |protected |public )([\\s\\S]*?)\\(([\\s\\S]*?)\\)");
	private int _type;

}