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

package com.liferay.poshi.runner.pql;

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

	private static void _validateValue(String value) throws Exception {
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