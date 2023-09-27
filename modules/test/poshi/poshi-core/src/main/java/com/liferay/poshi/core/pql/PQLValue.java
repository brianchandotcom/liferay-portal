/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.pql;

import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class PQLValue extends PQLEntity {

	public PQLValue(String value) throws Exception {
		super(value);

		_validateValue(value);
	}

	@Override
	public Object getPQLResult(Properties properties) throws Exception {
		return getObjectValue(getPQL());
	}

	protected Object getObjectValue(String value) throws Exception {
		_validateValue(value);

		if (value == null) {
			return null;
		}

		if ((value.startsWith("'") && value.endsWith("'")) ||
			(value.startsWith("\"") && value.endsWith("\""))) {

			value = value.substring(1, value.length() - 1);
		}
		else if (value.contains(" ")) {
			throw new Exception("Invalid value: " + value);
		}

		Object objectValue;

		if (value.equals("null")) {
			return null;
		}
		else if (value.equals("true") || value.equals("false")) {
			objectValue = Boolean.valueOf(value);
		}
		else if (value.matches("\\d+\\.\\d+")) {
			objectValue = Double.valueOf(value);
		}
		else if (value.matches("\\d+")) {
			objectValue = Integer.valueOf(value);
		}
		else {
			objectValue = value;
		}

		PQLModifier pqlModifier = getPQLModifier();

		if (pqlModifier != null) {
			objectValue = pqlModifier.getPQLResult(objectValue);
		}

		return objectValue;
	}

	private void _validateValue(String value) throws Exception {
		if (value == null) {
			return;
		}

		value = removeModifierFromPQL(value);

		if ((value.startsWith("'") && value.endsWith("'")) ||
			(value.startsWith("\"") && value.endsWith("\""))) {

			return;
		}

		if (value.contains(" ")) {
			throw new Exception("Invalid value: " + value);
		}
	}

}