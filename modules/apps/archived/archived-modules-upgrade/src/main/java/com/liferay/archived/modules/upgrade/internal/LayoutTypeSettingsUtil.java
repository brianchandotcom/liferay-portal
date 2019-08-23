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

package com.liferay.archived.modules.upgrade.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

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

	public static void removePortletId(Connection connection, String portletId)
		throws Exception {

		try (PreparedStatement selectPS = connection.prepareStatement(
				StringBundler.concat(
					"select plid, typeSettings from Layout where typeSettings ",
					"like '%", portletId, "%'"));
			PreparedStatement updatePS =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update Layout set typeSettings = ? where plid = ?"));
			ResultSet rs = selectPS.executeQuery()) {

			while (rs.next()) {
				long plid = rs.getLong(1);
				String typeSettings = rs.getString(2);

				UnicodeProperties unicodeProperties = new UnicodeProperties(
					true);

				unicodeProperties.fastLoad(typeSettings);

				Set<Map.Entry<String, String>> entrySet =
					unicodeProperties.entrySet();

				Iterator<Map.Entry<String, String>> iterator =
					entrySet.iterator();

				while (iterator.hasNext()) {
					Map.Entry<String, String> entry = iterator.next();

					String value = entry.getValue();

					if (!value.contains(portletId)) {
						continue;
					}

					List<String> parts = StringUtil.split(
						value, CharPool.COMMA);

					if (parts.size() <= 1) {
						iterator.remove();

						continue;
					}

					StringBundler sb = new StringBundler(2 * parts.size() - 2);

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

				updatePS.setString(1, unicodeProperties.toString());

				updatePS.setLong(2, plid);

				updatePS.addBatch();
			}

			updatePS.executeBatch();
		}
	}

}