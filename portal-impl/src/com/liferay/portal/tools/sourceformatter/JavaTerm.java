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

	public List<String> getParameterTypes() {
		if (_parameterTypes != null) {
			return _parameterTypes;
		}

		_parameterTypes = new ArrayList<String>();

		if (!JavaClass.isInJavaTermTypeGroup(
				_type, JavaClass.TYPE_CONSTRUCTOR) &&
			!JavaClass.isInJavaTermTypeGroup(_type, JavaClass.TYPE_METHOD)) {

			return _parameterTypes;
		}

		Matcher matcher = _parameterTypesPattern.matcher(_content);

		if (!matcher.find()) {
			return _parameterTypes;
		}

		String parameters = matcher.group(3);

		if (Validator.isNull(parameters)) {
			return _parameterTypes;
		}

		parameters = StringUtil.replace(
			parameters, new String[] {StringPool.TAB, StringPool.NEW_LINE},
			new String[] {StringPool.BLANK, StringPool.SPACE});

		for (int x = 0;;) {
			parameters = StringUtil.trim(parameters);

			x = parameters.indexOf(StringPool.SPACE);

			if (x == -1) {
				return _parameterTypes;
			}

			String parameterType = parameters.substring(0, x);

			if (parameterType.equals("final")) {
				int y = parameters.indexOf(StringPool.SPACE, x + 1);

				if (y == -1) {
					return _parameterTypes;
				}

				parameterType = parameters.substring(x + 1, y);
			}

			_parameterTypes.add(parameterType);

			int y = parameters.indexOf(StringPool.COMMA);

			if (y == -1) {
				return _parameterTypes;
			}

			parameters = parameters.substring(y + 1);
		}
	}

	public int getType() {
		return _type;
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

	private String _content;
	private int _lineCount;
	private String _name;
	private List<String> _parameterTypes;
	private Pattern _parameterTypesPattern = Pattern.compile(
		"\t(private |protected |public )([\\s\\S]*?)\\(([\\s\\S]*?)\\)");
	private int _type;

}