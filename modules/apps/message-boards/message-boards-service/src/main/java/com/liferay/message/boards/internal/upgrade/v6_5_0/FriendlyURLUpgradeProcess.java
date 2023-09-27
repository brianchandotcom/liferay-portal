/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
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
public class FriendlyURLUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (SafeCloseable safeCloseable = addTemporaryIndex(
				"MBCategory", false, "name")) {

			try (PreparedStatement preparedStatement1 =
					connection.prepareStatement(
						"select categoryId, name from MBCategory order by " +
							"name, categoryId asc");
				ResultSet resultSet = preparedStatement1.executeQuery();
				PreparedStatement preparedStatement2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection,
						"update MBCategory set friendlyURL = ? where " +
							"categoryId = ?")) {

				int count = 0;
				String currentFriendlyURL = null;
				String previousFriendlyURL = null;

				while (resultSet.next()) {
					long categoryId = resultSet.getLong(1);
					String name = resultSet.getString(2);

					currentFriendlyURL = _getFriendlyURL(categoryId, name);

					String suffix = null;

					if (StringUtil.equals(
							previousFriendlyURL, currentFriendlyURL)) {

						count++;
						suffix = StringPool.DASH + count;
					}
					else {
						count = 0;
						previousFriendlyURL = currentFriendlyURL;
						suffix = StringPool.BLANK;
					}

					preparedStatement2.setString(
						1, currentFriendlyURL + suffix);
					preparedStatement2.setLong(2, categoryId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"MBCategory", "friendlyURL VARCHAR(255)")
		};
	}

	private String _getFriendlyURL(long id, String name) {
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

}