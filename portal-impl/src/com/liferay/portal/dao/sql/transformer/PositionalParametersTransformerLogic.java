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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.function.Function;

/**
 * @author Manuel de la Peña
 */
public class PositionalParametersTransformerLogic {

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

}