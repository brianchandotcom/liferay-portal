/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.entity;

import java.io.Serializable;

/**
 * @author Vendel Toreki
 */
public class TestEntity implements Serializable {

	public int getIntValue() {
		return _intValue;
	}

	public String getTextValue() {
		return _textValue;
	}

	public void setIntValue(int intValue) {
		_intValue = intValue;
	}

	public void setTextValue(String textValue) {
		_textValue = textValue;
	}

	private int _intValue;
	private String _textValue;

}