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

package com.liferay.message.boards.parser.bbcode.internal;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Iliyan Peychev
 */
public class BBCodeToken {

	public BBCodeToken(String endTag) {
		this(null, null, endTag, 0, 0);
	}

	public BBCodeToken(
		String startTag, String attribute, String endTag, int start, int end) {

		_startTag = StringUtil.lowerCase(startTag);
		_attribute = attribute;
		_endTag = StringUtil.lowerCase(endTag);
		_start = start;
		_end = end;
	}

	public String getAttribute() {
		return _attribute;
	}

	public int getEnd() {
		return _end;
	}

	public String getEndTag() {
		return _endTag;
	}

	public int getStart() {
		return _start;
	}

	public String getStartTag() {
		return _startTag;
	}

	public void setAttribute(String attribute) {
		_attribute = attribute;
	}

	private String _attribute;
	private final int _end;
	private final String _endTag;
	private final int _start;
	private final String _startTag;

}