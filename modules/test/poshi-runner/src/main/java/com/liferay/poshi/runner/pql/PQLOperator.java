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
		new ArrayList<>();

	static {
		_prioritizedOperatorList.add(Arrays.asList(new String[] {"<", ">"}));
		_prioritizedOperatorList.add(Arrays.asList(new String[] {"<=", ">="}));
		_prioritizedOperatorList.add(Arrays.asList(new String[] {"~", "=="}));
		_prioritizedOperatorList.add(Arrays.asList(new String[] {"!~", "!="}));
		_prioritizedOperatorList.add(Arrays.asList(new String[] {"OR"}));
		_prioritizedOperatorList.add(Arrays.asList(new String[] {"AND"}));

		for (List<String> operators : _prioritizedOperatorList) {
			_availableOperators.addAll(operators);
		}
	}

	private final String _operator;

}