/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_4_2;

import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.UUID;

/**
 * @author Jonathan McCann
 */
public class LayoutUpgradeProcess extends UpgradeProcess {

	public LayoutUpgradeProcess(LayoutLocalService layoutLocalService) {
		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select groupId, plid, friendlyURL from Layout where ",
					"privateLayout = ? and (plid IN (select plid from ",
					"LayoutPageTemplateEntry where type_ = ?) OR (classPK IN (",
					"select plid from LayoutPageTemplateEntry where type_ = ?",
					") and classNameId = ?))"));
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select plid from Layout where privateLayout = ? and groupId " +
					"= ? and friendlyURL = ? and plid != ?");
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update Layout set uuid_ = ?, friendlyURL = ? where plid " +
						"= ?");
			PreparedStatement preparedStatement4 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update LayoutFriendlyURL set friendlyURL = ? where plid " +
						"= ?");
			PreparedStatement preparedStatement5 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update Layout set privateLayout = ?, layoutId = ? where " +
						"plid = ?");
			PreparedStatement preparedStatement6 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update LayoutFriendlyURL set privateLayout = ? where " +
						"plid = ?")) {

			preparedStatement1.setBoolean(1, false);
			preparedStatement1.setLong(
				2, LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT);
			preparedStatement1.setLong(
				3, LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT);
			preparedStatement1.setLong(
				4, PortalUtil.getClassNameId(Layout.class.getName()));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long groupId = resultSet.getLong("groupId");
					long plid = resultSet.getLong("plid");
					String friendlyUrl = resultSet.getString("friendlyUrl");

					preparedStatement2.setBoolean(1, true);
					preparedStatement2.setLong(2, groupId);
					preparedStatement2.setString(3, friendlyUrl);
					preparedStatement2.setLong(4, plid);

					try (ResultSet resultSet1 =
							preparedStatement2.executeQuery()) {

						while (resultSet1.next()) {
							long duplicationPlid = resultSet1.getLong("plid");
							String newFriendlyUrl = _generateFriendlyURLUUID();

							preparedStatement3.setString(1, _generateUUID());
							preparedStatement3.setString(2, newFriendlyUrl);
							preparedStatement3.setLong(3, duplicationPlid);

							preparedStatement3.addBatch();

							preparedStatement4.setString(1, newFriendlyUrl);
							preparedStatement4.setLong(2, duplicationPlid);

							preparedStatement4.addBatch();
						}
					}

					preparedStatement5.setBoolean(1, true);
					preparedStatement5.setLong(
						2, _layoutLocalService.getNextLayoutId(groupId, true));
					preparedStatement5.setLong(3, plid);

					preparedStatement5.addBatch();

					preparedStatement6.setBoolean(1, true);
					preparedStatement6.setLong(2, plid);

					preparedStatement6.addBatch();
				}
			}

			preparedStatement3.executeBatch();

			preparedStatement4.executeBatch();

			preparedStatement5.executeBatch();

			preparedStatement6.executeBatch();
		}
	}

	private String _generateFriendlyURLUUID() {
		return StringPool.SLASH + _generateUUID();
	}

	private String _generateUUID() {
		UUID uuid = new UUID(
			SecureRandomUtil.nextLong(), SecureRandomUtil.nextLong());

		return uuid.toString();
	}

	private final LayoutLocalService _layoutLocalService;

}