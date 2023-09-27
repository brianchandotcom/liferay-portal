/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.sql.transformer;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;

/**
 * @author Manuel de la Peña
 */
public class HQLToJPQLTransformerLogic {

	public static Function<String, String> getCompositeIdMarkerFunction() {
		return (String sql) -> StringUtil.replace(
			sql, _HQL_COMPOSITE_ID_MARKER, _JPQL_DOT_SEPARTOR);
	}

	public static Function<String, String> getNotEqualsFunction() {
		return (String sql) -> StringUtil.replace(
			sql, _HQL_NOT_EQUALS, _JPQL_NOT_EQUALS);
	}

	public static Function<String, String> getPositionalParameterFunction() {
		return (String sql) -> {
			if (sql.indexOf(CharPool.QUESTION) == -1) {
				return sql;
			}

			StringBundler sb = new StringBundler();

			int i = 1;
			int from = 0;
			int to = 0;

			while ((to = sql.indexOf(CharPool.QUESTION, from)) != -1) {
				sb.append(sql.substring(from, to));
				sb.append(StringPool.QUESTION);
				sb.append(i++);

				from = to + 1;
			}

			sb.append(sql.substring(from));

			return sb.toString();
		};
	}

	private static final String _HQL_COMPOSITE_ID_MARKER = "\\.id\\.";

	private static final String _HQL_NOT_EQUALS = "!=";

	private static final String _JPQL_DOT_SEPARTOR = ".";

	private static final String _JPQL_NOT_EQUALS = "<>";

}