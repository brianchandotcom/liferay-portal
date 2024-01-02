/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.internal.upgrade.v2_0_0;

import com.liferay.commerce.product.type.virtual.order.internal.upgrade.v2_0_0.util.CommerceVirtualOrderItemFileEntryTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Andrea Sbarra
 */
public class CommerceVirtualOrderItemFileEntryUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("CVirtualOrderItemFileEntry")) {
			return;
		}

		String insertCPDVirtualFileEntriesSQL = StringBundler.concat(
			"insert into CVirtualOrderItemFileEntry (",
			"cVirtualOrderItemFileEntryId, uuid_, groupId, companyId, userId, ",
			"userName, createDate, modifiedDate, commerceVirtualOrderItemId, ",
			"fileEntryId, url, usages) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
			"?, ?)");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select * from CommerceVirtualOrderItem");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, insertCPDVirtualFileEntriesSQL);
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long cVirtualOrderItemFileEntryId = increment();
				String uuid_ = PortalUUIDUtil.generate();
				long groupId = resultSet.getLong("groupId");
				long companyId = resultSet.getLong("companyId");
				long userId = resultSet.getLong("userId");
				String userName = resultSet.getString("userName");
				Date createDate = resultSet.getDate("createDate");
				Date modifiedDate = resultSet.getDate("modifiedDate");
				long commerceVirtualOrderItemId = resultSet.getLong(
					"commerceVirtualOrderItemId");
				long fileEntryId = resultSet.getLong("fileEntryId");
				String url = resultSet.getString("url");
				String usages = resultSet.getString("usages");

				preparedStatement2.setLong(1, cVirtualOrderItemFileEntryId);
				preparedStatement2.setString(2, uuid_);
				preparedStatement2.setLong(3, groupId);
				preparedStatement2.setLong(4, companyId);
				preparedStatement2.setLong(5, userId);
				preparedStatement2.setString(6, userName);
				preparedStatement2.setDate(7, createDate);
				preparedStatement2.setDate(8, modifiedDate);
				preparedStatement2.setLong(9, commerceVirtualOrderItemId);
				preparedStatement2.setLong(10, fileEntryId);
				preparedStatement2.setString(11, url);
				preparedStatement2.setString(12, usages);

				preparedStatement2.execute();
			}
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns(
				"CommerceVirtualOrderItem", "fileEntryId", "url", "usages")
		};
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			CommerceVirtualOrderItemFileEntryTable.create()
		};
	}

}