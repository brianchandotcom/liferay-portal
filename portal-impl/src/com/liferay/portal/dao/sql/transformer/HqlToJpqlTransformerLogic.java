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

package com.liferay.portal.dao.sql.transformer;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;

/**
 * @author Manuel de la Peña
 */
public class HqlToJpqlTransformerLogic {

	public static final Function<String, String>
		getCompositeIdMarkerFunction() {

		return (String sql) ->
			StringUtil.replace(
				sql, _HQL_COMPOSITE_ID_MARKER, _JPQL_DOT_SEPARTOR);
	}

	public static final Function<String, String> getNotEqualsFunction() {
		return (String sql) ->
			StringUtil.replace(sql, _HQL_NOT_EQUALS, _JPQL_NOT_EQUALS);
	}

	private static final String _HQL_COMPOSITE_ID_MARKER = "\\.id\\.";

	private static final String _HQL_NOT_EQUALS = "!=";

	private static final String _JPQL_DOT_SEPARTOR = ".";

	private static final String _JPQL_NOT_EQUALS = "<>";

}