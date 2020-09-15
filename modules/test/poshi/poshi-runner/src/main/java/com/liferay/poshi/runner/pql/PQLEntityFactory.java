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

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author Michael Hashimoto
 */
public class PQLEntityFactory {

	public static PQLEntity newPQLEntity(String pql) throws Exception {
		if (pql != null) {
			validateExpressionBalance(pql);
		}

		if (PQLQuery.isQuery(pql)) {
			return new PQLQuery(pql);
		}
		else if (PQLVariable.isVariable(pql)) {
			return new PQLVariable(pql);
		}

		return new PQLValue(pql);
	}

	public static void validateExpressionBalance(String pql) {
		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < pql.length(); i++) {
			char c = pql.charAt(i);

			if (!stack.isEmpty()) {
				int topIndex = stack.peek();

				Character topCodeBoundary = pql.charAt(topIndex);

				if (c == _codeBoundariesMap.get(topCodeBoundary)) {
					stack.pop();

					continue;
				}

				if (topCodeBoundary == '\"') {
					continue;
				}
			}

			if (_codeBoundariesMap.containsKey(c)) {
				stack.push(i);

				continue;
			}

			if (_codeBoundariesMap.containsValue(c)) {
				throw new RuntimeException(
					"Invalid PQL: Unexpected closing boundary '" +
						pql.charAt(i) + "'\n" + pql);
			}
		}

		if (!stack.isEmpty()) {
			throw new RuntimeException(
				"Invalid PQL: Unmatched opening boundary '" +
					pql.charAt(stack.peek()) + "'\n" + pql);
		}
	}

	private static final Map<Character, Character> _codeBoundariesMap =
		new HashMap<Character, Character>() {
			{
				put('(', ')');
				put('\"', '\"');
			}
		};

}