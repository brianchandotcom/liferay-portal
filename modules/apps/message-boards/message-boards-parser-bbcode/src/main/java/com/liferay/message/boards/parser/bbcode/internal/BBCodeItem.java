/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.parser.bbcode.internal;

/**
 * @author Iliyan Peychev
 */
public class BBCodeItem {

	public BBCodeItem(int type, String attribute, String value) {
		_type = type;
		_attribute = attribute;
		_value = value;
	}

	public String getAttribute() {
		return _attribute;
	}

	public int getType() {
		return _type;
	}

	public String getValue() {
		return _value;
	}

	private final String _attribute;
	private final int _type;
	private final String _value;

}