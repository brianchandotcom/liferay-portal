/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.sourceformatter;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaTerm {

	public JavaTerm(
		String name, int type, List<String> parameterTypes, String content,
		int lineCount) {

		_name = name;
		_type = type;
		_parameterTypes = parameterTypes;
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
		return _parameterTypes;
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
	private int _type;

}