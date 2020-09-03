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

package com.liferay.commerce.product.internal.upgrade.v2_5_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Danny Situ
 */
public class FriendlyURLEntryUpgradeProcess extends UpgradeProcess {

	public FriendlyURLEntryUpgradeProcess(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Override
	public void doUpgrade() throws Exception {
		if (!hasTable("CPFriendlyURLEntry")) {
			return;
		}

		String insertFriendlyUREntrySQL = StringBundler.concat(
			"insert into FriendlyURLEntry (mvccVersion, uuid_, ",
			"defaultLanguageId, friendlyURLEntryId, groupId, companyId, ",
			"createDate, modifiedDate, classNameId, classPK) values (?, ?, ?, ",
			"?, ?, ?, ?, ?, ?, ?)");

		String insertFriendlyUREntryMappingSQL = StringBundler.concat(
			"insert into FriendlyURLEntryMapping (mvccVersion, ",
			"friendlyURLEntryMappingId, classNameId, classPK, ",
			"friendlyURLEntryId) values (?, ?, ?, ?, ?)");

		String insertFriendlyUREntryLocalizationSQL = StringBundler.concat(
			"insert into FriendlyURLEntryLocalization (mvccVersion, ",
			"friendlyURLEntryLocalizationId, companyId, friendlyURLEntryId, ",
			"languageId, urlTitle, groupId, classNameId, classPK)",
			"values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

		String selectCPFriendlyURLEntrySQL =
			"select * from CPFriendlyURLEntry order by main desc";

		try (PreparedStatement ps1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, insertFriendlyUREntrySQL);
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, insertFriendlyUREntryMappingSQL);
			PreparedStatement ps3 = connection.prepareStatement(
				insertFriendlyUREntryLocalizationSQL);
			Statement s1 = connection.createStatement();
			ResultSet rs = s1.executeQuery(selectCPFriendlyURLEntrySQL)) {

			while (rs.next()) {
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");
				long companyId = rs.getLong("companyId");
				Date date = new Date(System.currentTimeMillis());
				Group group = _groupLocalService.getCompanyGroup(
					PortalUtil.getDefaultCompanyId());
				String languageId = rs.getString("languageId");
				boolean main = rs.getBoolean("main");
				String uuid = rs.getString("uuid_");

				long friendlyURLEntryId = _getFriendlyURLEntryId(
					classNameId, classPK);

				if (friendlyURLEntryId <= 0) {
					friendlyURLEntryId = increment();

					ps1.setLong(1, 0);
					ps1.setString(2, uuid);
					ps1.setString(3, languageId);
					ps1.setLong(4, friendlyURLEntryId);
					ps1.setLong(5, group.getGroupId());
					ps1.setLong(6, companyId);
					ps1.setDate(7, date);
					ps1.setDate(8, date);
					ps1.setLong(9, classNameId);
					ps1.setLong(10, classPK);

					ps1.execute();
				}

				long friendlyURLEntryMappingId = _getFriendlyURLEntryMappingId(
					classNameId, classPK);

				if (main && (friendlyURLEntryMappingId <= 0)) {
					friendlyURLEntryMappingId = increment();

					ps2.setLong(1, 0);
					ps2.setLong(2, friendlyURLEntryMappingId);
					ps2.setLong(3, classNameId);
					ps2.setLong(4, classPK);
					ps2.setLong(5, friendlyURLEntryId);

					ps2.execute();
				}

				long friendlyURLEntryLocalizationId =
					_getFriendlyURLEntryLocalizationId(
						languageId, classNameId, classPK);

				if (friendlyURLEntryLocalizationId <= 0) {
					friendlyURLEntryLocalizationId = increment();

					ps3.setLong(1, 0);
					ps3.setLong(2, friendlyURLEntryLocalizationId);
					ps3.setLong(3, companyId);
					ps3.setLong(4, friendlyURLEntryId);
					ps3.setString(5, languageId);

					String urlTitle = rs.getString("urlTitle");

					urlTitle = _getUniqueURLTitle(
						group.getGroupId(), classNameId, urlTitle);

					ps3.setString(6, urlTitle);

					ps3.setLong(7, group.getGroupId());
					ps3.setLong(8, classNameId);
					ps3.setLong(9, classPK);

					ps3.execute();
				}
			}
		}
	}

	private long _getFriendlyURLEntryId(long classNameId, long classPK)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from FriendlyURLEntry where classNameId = ? and " +
					"classPK = ?")) {

			ps.setLong(1, classNameId);
			ps.setLong(2, classPK);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					return rs.getLong("friendlyURLEntryId");
				}

				return 0;
			}
		}
	}

	private long _getFriendlyURLEntryLocalizationId(
			String languageId, long classNameId, long classPK)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from FriendlyURLEntryLocalization where languageId " +
					"= ? and classNameId = ? and classPK = ?")) {

			ps.setString(1, languageId);
			ps.setLong(2, classNameId);
			ps.setLong(3, classPK);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					return rs.getLong("friendlyURLEntryLocalizationId");
				}

				return 0;
			}
		}
	}

	private long _getFriendlyURLEntryMappingId(long classNameId, long classPK)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from FriendlyURLEntryMapping where classNameId = ? " +
					"and classPK = ?")) {

			ps.setLong(1, classNameId);
			ps.setLong(2, classPK);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					return rs.getLong("friendlyURLEntryMappingId");
				}

				return 0;
			}
		}
	}

	private String _getUniqueURLTitle(
			long groupId, long classNameId, String urlTitle)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from FriendlyURLEntryLocalization where groupId = " +
					"? and classNameId = ? and urlTitle = ?")) {

			ps.setLong(1, groupId);
			ps.setLong(2, classNameId);
			ps.setString(3, urlTitle);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					return urlTitle + StringPool.DASH +
						PortalUUIDUtil.generate();
				}

				return urlTitle;
			}
		}
	}

	private final GroupLocalService _groupLocalService;

}