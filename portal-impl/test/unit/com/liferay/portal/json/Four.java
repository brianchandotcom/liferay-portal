/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSON;

/**
 * @author Igor Spasic
 */
@JSON(strict = true)
public class Four {

	@JSON(name = "nuMber")
	public int getNumber() {
		return _number;
	}

	public long getPrivate() {
		return _private;
	}

	public String getValue() {
		return _value;
	}

	public void setNumber(int number) {
		_number = number;
	}

	public void setPrivate(long aPrivate) {
		_private = aPrivate;
	}

	public void setValue(String value) {
		_value = value;
	}

	private int _number = 173;
	private long _private = 0xCAFEBABE;

	@JSON(name = "vaLue")
	private String _value = "something";

}