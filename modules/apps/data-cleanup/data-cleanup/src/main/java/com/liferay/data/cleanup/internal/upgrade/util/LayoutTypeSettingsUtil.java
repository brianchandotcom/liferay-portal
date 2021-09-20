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

package com.liferay.data.cleanup.internal.upgrade.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Preston Crary
 */
public class LayoutTypeSettingsUtil {

	public static void removePortletIds(
			Connection connection, String... portletIds)
		throws Exception {

		if (portletIds == null) {
			return;
		}

		try (PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update Layout set typeSettings = ? where plid = ?"))) {

			for (String portletId : portletIds) {
				try (PreparedStatement selectPreparedStatement =
						connection.prepareStatement(
							StringBundler.concat(
								"select plid, typeSettings from Layout where ",
								"typeSettings like '%", portletId, "%'"));
					ResultSet resultSet =
						selectPreparedStatement.executeQuery()) {

					while (resultSet.next()) {
						_removePortletId(
							portletId, resultSet, updatePreparedStatement);
					}
				}
			}

			updatePreparedStatement.executeBatch();
		}
	}

	private static void _removePortletId(
			String portletId, ResultSet resultSet,
			PreparedStatement updatePreparedStatement)
		throws Exception {

		String typeSettings = resultSet.getString(2);

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.create(
			true
		).fastLoad(
			typeSettings
		).build();

		Set<Map.Entry<String, String>> set = unicodeProperties.entrySet();

		Iterator<Map.Entry<String, String>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();

			String value = entry.getValue();

			if (!value.contains(portletId)) {
				continue;
			}

			List<String> parts = StringUtil.split(value, CharPool.COMMA);

			if (parts.size() <= 1) {
				iterator.remove();

				continue;
			}

			StringBundler sb = new StringBundler((2 * parts.size()) - 2);

			for (String part : parts) {
				if (!part.startsWith(portletId)) {
					sb.append(part);
					sb.append(StringPool.COMMA);
				}
			}

			if (sb.index() == 0) {
				iterator.remove();

				continue;
			}

			sb.setIndex(sb.index() - 1);

			entry.setValue(sb.toString());
		}

		updatePreparedStatement.setString(1, unicodeProperties.toString());

		long plid = resultSet.getLong(1);

		updatePreparedStatement.setLong(2, plid);

		updatePreparedStatement.addBatch();
	}

}