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

package com.liferay.layout.page.template.internal.upgrade.v3_3_1;

import com.liferay.layout.page.template.internal.validator.LayoutPageTemplateEntryValidator;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Mariano Álvaro Sáiz
 */
public class UpgradeLayoutPageTemplateEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeLayoutPageTemplateEntryNameAndKey();
	}

	private String _generateValidString(String value) {
		StringBundler sb = new StringBundler(value.length());

		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);

			if (LayoutPageTemplateEntryValidator.isBlacklistedChar(c)) {
				sb.append(CharPool.DASH);
			}
			else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	private void _upgradeLayoutPageTemplateEntryNameAndKey() throws Exception {
		try (Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(
				"select layoutPageTemplateEntryId, " +
					"layoutPageTemplateEntryKey, name from " +
						"LayoutPageTemplateEntry");
			PreparedStatement ps = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update LayoutPageTemplateEntry set " +
						"layoutPageTemplateEntryKey = ?, name = ? where " +
							"layoutPageTemplateEntryId = ?"))) {

			while (rs.next()) {
				String name = rs.getString("name");

				if (LayoutPageTemplateEntryValidator.isValidName(name)) {
					continue;
				}

				long layoutPageTemplateEntryId = rs.getLong(
					"layoutPageTemplateEntryId");

				String layoutPageTemplateEntryKey = rs.getString(
					"layoutPageTemplateEntryKey");

				ps.setString(
					1, _generateValidString(layoutPageTemplateEntryKey));

				ps.setString(2, _generateValidString(name));

				ps.setLong(3, layoutPageTemplateEntryId);

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

}