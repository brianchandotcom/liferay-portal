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

package com.liferay.commerce.internal.upgrade.v2_2_0;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.internal.upgrade.base.BaseCommerceServiceUpgradeProcess;
import com.liferay.commerce.model.impl.CommerceOrderModelImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Ethan Bustad
 */
public class CommerceOrderUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	public CommerceOrderUpgradeProcess(
		CommerceAccountLocalService commerceAccountLocalService,
		UserLocalService userLocalService) {

		_commerceAccountLocalService = commerceAccountLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CommerceOrderModelImpl.class, CommerceOrderModelImpl.TABLE_NAME,
			"commerceAccountId", "LONG");

		if (hasColumn(CommerceOrderModelImpl.TABLE_NAME, "siteGroupId")) {
			runSQL("update CommerceOrder set groupId = siteGroupId");

			dropColumn(CommerceOrderModelImpl.TABLE_NAME, "siteGroupId");
		}

		if (!hasColumn(
				CommerceOrderModelImpl.TABLE_NAME, "orderOrganizationId") ||
			!hasColumn(CommerceOrderModelImpl.TABLE_NAME, "orderUserId")) {

			return;
		}

		String updateCommerceOrderSQL1 =
			"update CommerceOrder set commerceAccountId = ? where " +
				"orderOrganizationId = ?";
		String updateCommerceOrderSQL2 =
			"update CommerceOrder set commerceAccountId = ? where " +
				"orderUserId = ?";

		try (PreparedStatement ps1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateCommerceOrderSQL1);
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateCommerceOrderSQL2);
			Statement s = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = s.executeQuery(
				"select distinct orderOrganizationId, orderUserId from " +
					"CommerceOrder")) {

			while (rs.next()) {
				long commerceAccountId = 0;

				long orderOrganizationId = rs.getLong("orderOrganizationId");
				long orderUserId = rs.getLong("orderUserId");

				if (orderOrganizationId > 0) {
					commerceAccountId = _getCommerceAccountId(
						orderOrganizationId);

					if (commerceAccountId == 0) {
						_log.error(
							"No CommerceAccount for orderOrganizationId " +
								orderOrganizationId);

						continue;
					}

					ps1.setLong(1, commerceAccountId);
					ps1.setLong(2, orderOrganizationId);

					ps1.execute();
				}
				else if (orderUserId > 0) {
					User user = _userLocalService.getUser(orderUserId);

					ServiceContext serviceContext = new ServiceContext();

					serviceContext.setCompanyId(user.getCompanyId());
					serviceContext.setUserId(user.getUserId());

					CommerceAccount commerceAccount =
						_commerceAccountLocalService.addPersonalCommerceAccount(
							user.getUserId(), StringPool.BLANK,
							StringPool.BLANK, serviceContext);

					commerceAccountId = commerceAccount.getCommerceAccountId();

					ps2.setLong(1, commerceAccountId);

					ps2.setLong(2, orderUserId);

					ps2.execute();
				}
			}

			ps1.executeBatch();
			ps2.executeBatch();
		}

		dropColumn(CommerceOrderModelImpl.TABLE_NAME, "orderOrganizationId");
		dropColumn(CommerceOrderModelImpl.TABLE_NAME, "orderUserId");
	}

	private long _getCommerceAccountId(long organizationId)
		throws SQLException {

		String sql =
			"select commerceAccountId from CommerceAccountOrganizationRel " +
				"where organizationId = " + organizationId;

		try (Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(sql)) {

			if (rs.next()) {
				return rs.getLong("commerceAccountId");
			}
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderUpgradeProcess.class);

	private final CommerceAccountLocalService _commerceAccountLocalService;
	private final UserLocalService _userLocalService;

}