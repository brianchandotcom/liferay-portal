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

package com.liferay.fragment.internal.upgrade.v2_5_0;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.fragment.internal.upgrade.v2_5_0.util.FragmentEntryVersionTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * @author Rubén Pulido
 */
public class UpgradeFragmentEntryVersion extends UpgradeProcess {

	public UpgradeFragmentEntryVersion(
		CounterLocalService counterLocalService) {

		_counterLocalService = counterLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable(FragmentEntryVersionTable.TABLE_NAME)) {
			runSQL(FragmentEntryVersionTable.TABLE_SQL_CREATE);
		}

		insertIntoFragmentEntryVersion();
	}

	protected void insertIntoFragmentEntryVersion() throws Exception {
		StringBundler sb1 = new StringBundler(11);

		sb1.append("select uuid_, fragmentEntryId, groupId, companyId, ");
		sb1.append("userId, userName, createDate, modifiedDate, ");
		sb1.append("fragmentCollectionId, fragmentEntryKey, name, css, html, ");
		sb1.append("js, cacheable, configuration, previewFileEntryId, ");
		sb1.append("readOnly, type_, lastPublishDate, status, ");
		sb1.append("statusByUserId, statusByUserName, statusDate from ");
		sb1.append("FragmentEntry where status = ");
		sb1.append(WorkflowConstants.STATUS_APPROVED);
		sb1.append(" and FragmentEntry.fragmentEntryId not in (select ");
		sb1.append("FragmentEntryVersion.fragmentEntryId from ");
		sb1.append("FragmentEntryVersion)");

		StringBundler sb2 = new StringBundler(9);

		sb2.append("insert into FragmentEntryVersion(fragmentEntryVersionId, ");
		sb2.append("version, uuid_, fragmentEntryId, groupId, companyId, ");
		sb2.append("userId, userName, createDate, modifiedDate, ");
		sb2.append("fragmentCollectionId, fragmentEntryKey, name, css, html, ");
		sb2.append("js, cacheable, configuration, previewFileEntryId, ");
		sb2.append("readOnly, type_, lastPublishDate, status, ");
		sb2.append("statusByUserId, statusByUserName, statusDate) values(?, ");
		sb2.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		sb2.append("?, ?, ?, ?, ?, ?)");

		try (Statement s = connection.createStatement();
			PreparedStatement ps =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString())) {

			try (ResultSet rs = s.executeQuery(sb1.toString())) {
				while (rs.next()) {
					String uuid_ = rs.getString(1);
					long fragmentEntryId = rs.getLong(2);
					long groupId = rs.getLong(3);
					long companyId = rs.getLong(4);
					long userId = rs.getLong(5);
					String userName = rs.getString(6);
					Timestamp createDate = rs.getTimestamp(7);
					Timestamp modifiedDate = rs.getTimestamp(8);
					long fragmentCollectionId = rs.getLong(9);
					String fragmentEntryKey = rs.getString(10);
					String name = rs.getString(11);
					String css = rs.getString(12);
					String html = rs.getString(13);
					String js = rs.getString(14);
					boolean cacheable = rs.getBoolean(15);
					String configuration = rs.getString(16);
					long previewFileEntryId = rs.getLong(17);
					boolean readOnly = rs.getBoolean(18);
					int type_ = rs.getInt(19);
					Timestamp lastPublishDate = rs.getTimestamp(20);
					int status = rs.getInt(21);
					long statusByUserId = rs.getLong(22);
					String statusByUserName = rs.getString(23);
					Timestamp statusDate = rs.getTimestamp(24);

					ps.setLong(1, _counterLocalService.increment());
					ps.setLong(2, 1);
					ps.setString(3, uuid_);
					ps.setLong(4, fragmentEntryId);
					ps.setLong(5, groupId);
					ps.setLong(6, companyId);
					ps.setLong(7, userId);
					ps.setString(8, userName);
					ps.setTimestamp(9, createDate);
					ps.setTimestamp(10, modifiedDate);
					ps.setLong(11, fragmentCollectionId);
					ps.setString(12, fragmentEntryKey);
					ps.setString(13, name);
					ps.setString(14, css);
					ps.setString(15, html);
					ps.setString(16, js);
					ps.setBoolean(17, cacheable);
					ps.setString(18, configuration);
					ps.setLong(19, previewFileEntryId);
					ps.setBoolean(20, readOnly);
					ps.setInt(21, type_);
					ps.setTimestamp(22, lastPublishDate);
					ps.setInt(23, status);
					ps.setLong(24, statusByUserId);
					ps.setString(25, statusByUserName);
					ps.setTimestamp(26, statusDate);

					ps.addBatch();
				}

				ps.executeBatch();
			}
		}
	}

	private final CounterLocalService _counterLocalService;

}