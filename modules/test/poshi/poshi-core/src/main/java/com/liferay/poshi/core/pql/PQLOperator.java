/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.pql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public abstract class PQLOperator {

	public static Set<String> getAvailableOperators() {
		return _availableOperators;
	}

	public static List<List<String>> getPrioritizedOperatorList() {
		return _prioritizedOperatorList;
	}

	public static void validateOperator(String operator) throws Exception {
		if ((operator == null) || !_availableOperators.contains(operator)) {
			throw new Exception("Invalid operator: " + operator);
		}
	}

	public PQLOperator(String operator) throws Exception {
		validateOperator(operator);

		_operator = operator;
	}

	public String getOperator() {
		return _operator;
	}

	public abstract Object getPQLResult(
			PQLEntity pqlEntity1, PQLEntity pqlEntity2, Properties properties)
		throws Exception;

	private static final Set<String> _availableOperators = new HashSet<>();
	private static final List<List<String>> _prioritizedOperatorList =
		new ArrayList<List<String>>() {
			{
				add(Arrays.asList("<", ">"));
				add(Arrays.asList("<=", ">="));
				add(Arrays.asList("~", "=="));
				add(Arrays.asList("!~", "!="));
				add(Arrays.asList("OR"));
				add(Arrays.asList("AND"));
			}
		};

	static {
		for (List<String> operators : _prioritizedOperatorList) {
			_availableOperators.addAll(operators);
		}
	}

	private final String _operator;

}