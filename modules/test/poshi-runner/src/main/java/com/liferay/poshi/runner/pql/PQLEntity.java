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
public abstract class PQLEntity {

	public static String fixPQL(String pql) {
		while (true) {
			pql = pql.trim();

			if (!pql.startsWith("(") || !pql.endsWith(")")) {
				break;
			}

			String subPQL = pql.substring(1, pql.length() - 1);

			int parenthesisCount = 0;

			for (int i = 0; i < subPQL.length(); i++) {
				char c = subPQL.charAt(i);

				if (c == '(') {
					parenthesisCount++;
				}

				if (c == ')') {
					if (parenthesisCount < 1) {
						return pql.trim();
					}

					parenthesisCount--;
				}
			}

			if (parenthesisCount > 0) {
				return pql.trim();
			}

			pql = subPQL;
		}

		return pql.trim();
	}

	public PQLEntity(String pql) throws Exception {
		if (pql != null) {
			pql = fixPQL(pql);
		}

		_pql = pql;
	}

	public abstract Object getValue(Properties properties) throws Exception;

	protected String getPQL() {
		return _pql;
	}

	private final String _pql;

}