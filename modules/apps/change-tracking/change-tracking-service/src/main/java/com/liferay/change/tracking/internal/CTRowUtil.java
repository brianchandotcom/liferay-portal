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

package com.liferay.change.tracking.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.registry.CTModelRegistration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Map;

/**
 * @author Preston Crary
 */
public class CTRowUtil {

	public static int copyCTRows(
			CTModelRegistration ctModelRegistration, Connection connection,
			String selectSQL)
		throws SQLException {

		Map<String, Integer> tableColumnsMap =
			ctModelRegistration.getTableColumnsMap();

		StringBundler sb = new StringBundler(2 * tableColumnsMap.size() + 4);

		sb.append("insert into ");
		sb.append(ctModelRegistration.getTableName());
		sb.append(" (");

		for (String name : tableColumnsMap.keySet()) {
			sb.append(name);
			sb.append(", ");
		}

		sb.setStringAt(") ", sb.index() - 1);

		sb.append(selectSQL);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			return preparedStatement.executeUpdate();
		}
	}

	private CTRowUtil() {
	}

}