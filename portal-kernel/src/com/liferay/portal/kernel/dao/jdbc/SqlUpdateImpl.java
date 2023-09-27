/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.jdbc;

import com.liferay.petra.string.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SqlUpdateImpl implements SqlUpdate {

	public SqlUpdateImpl(
		DataSource dataSource, String sql, ParamSetter... paramSetters) {

		_dataSource = dataSource;
		_sql = sql;
		_paramSetters = paramSetters;
	}

	@Override
	public int update(Object... params) throws SQLException {
		if (_paramSetters.length != params.length) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Expected ", _paramSetters.length,
					" parameters instead of ", params.length, " parameters"));
		}

		try (Connection connection = ConnectionUtil.getConnection(_dataSource);
			PreparedStatement preparedStatement = connection.prepareStatement(
				_sql)) {

			for (int i = 0; i < _paramSetters.length; i++) {
				ParamSetter paramSetter = _paramSetters[i];

				paramSetter.set(preparedStatement, i + 1, params[i]);
			}

			return preparedStatement.executeUpdate();
		}
	}

	private final DataSource _dataSource;
	private final ParamSetter[] _paramSetters;
	private final String _sql;

}