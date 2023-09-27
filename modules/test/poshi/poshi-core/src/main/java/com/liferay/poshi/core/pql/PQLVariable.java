/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.pql;

import com.liferay.poshi.core.PoshiContext;

import java.util.List;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class PQLVariable extends PQLValue {

	public static boolean isVariable(String variable) {
		if (variable == null) {
			return false;
		}

		List<String> poshiPropertyNames = PoshiContext.getPoshiPropertyNames();

		if (poshiPropertyNames.contains(variable)) {
			return true;
		}

		return false;
	}

	public PQLVariable(String variable) throws Exception {
		super(variable);

		_validateVariable(getPQL());
	}

	@Override
	public Object getPQLResult(Properties properties) throws Exception {
		String pql = getPQL();

		if (!properties.containsKey(pql)) {
			return null;
		}

		String value = properties.getProperty(pql);

		if (!(value.startsWith("'") && value.endsWith("'")) &&
			!(value.startsWith("\"") && value.endsWith("\"")) &&
			value.contains(" ")) {

			value = "'" + value + "'";
		}

		return getObjectValue(value);
	}

	private void _validateVariable(String variable) throws Exception {
		if (variable == null) {
			throw new Exception("Invalid variable: " + variable);
		}

		variable.trim();

		if (!isVariable(variable)) {
			throw new Exception("Invalid testcase property: " + variable);
		}
	}

}