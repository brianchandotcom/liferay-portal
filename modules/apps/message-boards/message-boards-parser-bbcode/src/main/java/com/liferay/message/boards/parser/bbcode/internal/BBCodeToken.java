/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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