/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.upgrade.v6_5_0;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Nilton Vieira
 */
public class UrlCategoryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (SafeCloseable safeCloseable = addTemporaryIndex(
				"MBCategory", false, "name")) {

			_populateURLCategory();
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"MBCategory", "urlCategory VARCHAR(255)")
		};
	}

	private String _getURLCategory(long id, String name) {
		if (name == null) {
			return String.valueOf(id);
		}

		name = StringUtil.toLowerCase(name.trim());

		if (Validator.isNull(name) || Validator.isNumber(name) ||
			name.equals("rss")) {

			name = String.valueOf(id);
		}
		else {
			name = FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(
				name);
		}

		return name.substring(0, Math.min(name.length(), 254));
	}

	private void _populateURLCategory() throws Exception {
		String sql =
			"update MBCategory set urlCategory = ? where categoryId = ?";

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select categoryId, name from MBCategory order by name, " +
					"categoryId asc");
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(connection, sql)) {

			int count = 0;
			String curURLCategory = null;
			String previousURLCategory = null;

			while (resultSet.next()) {
				long categoryId = resultSet.getLong(1);
				String name = resultSet.getString(2);

				curURLCategory = _getURLCategory(categoryId, name);

				String suffix = null;

				if (StringUtil.equals(previousURLCategory, curURLCategory)) {
					count++;
					suffix = StringPool.DASH + count;
				}
				else {
					count = 0;
					previousURLCategory = curURLCategory;
					suffix = StringPool.BLANK;
				}

				preparedStatement2.setString(1, curURLCategory + suffix);
				preparedStatement2.setLong(2, categoryId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}