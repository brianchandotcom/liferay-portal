/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Shuyang Zhou
 */
public interface ParamSetter {

	public static final ParamSetter BIGINT = new ParamSetter() {

		@Override
		public void set(
				PreparedStatement preparedStatement, int index, Object param)
			throws SQLException {

			preparedStatement.setLong(index, (long)param);
		}

	};

	public void set(
			PreparedStatement preparedStatement, int index, Object param)
		throws SQLException;

}