/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal;

import com.liferay.portal.kernel.json.JSON;

/**
 * @author Igor Spasic
 */
public class BarData {

	@JSON
	public int[] getArray() {
		return _array;
	}

	@JSON(include = false)
	public String getSecret() {
		return _secret;
	}

	public String getValue() {
		return _value;
	}

	public void setArray(int[] array) {
		_array = array;
	}

	public void setSecret(String secret) {
		_secret = secret;
	}

	public void setValue(String value) {
		_value = value;
	}

	private int[] _array = {1, 2, 3};
	private String _secret = "secret";
	private String _value = "value";

}