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

package com.liferay.portal.upgrade.v7_0_0.util.companyId;

import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdInTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdUtil;

/**
 * @author Cristina González
 */
public class RatingsStatsUpgradeCompanyId implements UpgradeCompanyIdInTable {

	@Override
	public String getTableName() {
		return "RatingsStats";
	}

	@Override
	public void upgradeProcess() throws Exception {
		StringBuilder select = new StringBuilder();

		// BookmarksEntry

		select.append(
			"(select be.companyId, r.statsId from BookmarksEntry be, " +
				"RatingsStats r where be.entryId=r.classPK)");

		select.append(" union ");

		// BookmarksFolder

		select.append(
			"(select bf.companyId, r.statsId from BookmarksFolder bf, " +
				"RatingsStats r where bf.folderId=r.classPK)");

		select.append(" union ");

		// BlogsEntry

		select.append(
			"(select be.companyId, r.statsId from BlogsEntry be, " +
				"RatingsStats r where be.entryId=r.classPK)");

		select.append(" union ");

		// CalendarBooking

		select.append(
			"(select cb.companyId, r.statsId from CalendarBooking cb, " +
				"RatingsStats r where cb.calendarBookingId=r.classPK)");

		select.append(" union ");

		// DDLRecord

		select.append(
			"(select ddlr.companyId, r.statsId from DDLRecord ddlr, " +
				"RatingsStats r where ddlr.recordId=r.classPK)");

		select.append(" union ");

		// DLFileEntry

		select.append(
			"(select dlfe.companyId, r.statsId from DLFileEntry dlfe, " +
				"RatingsStats r where dlfe.fileEntryId=r.classPK)");

		select.append(" union ");

		// DLFolder

		select.append(
			"(select dlf.companyId, r.statsId from DLFolder dlf, " +
				"RatingsStats r where dlf.folderId=r.classPK)");

		select.append(" union ");

		// JournalArticle

		select.append(
			"(select ja.companyId, r.statsId from JournalArticle ja, " +
				"RatingsStats r where ja.articleId=r.classPK)");

		select.append(" union ");

		// JournalFolder

		select.append(
			"(select jf.companyId, r.statsId from JournalFolder jf, " +
				"RatingsStats r where jf.folderId=r.classPK)");

		select.append(" union ");

		// MBDiscussion

		select.append(
			"(select mbd.companyId, r.statsId from MBDiscussion mbd, " +
				"RatingsStats r where mbd.discussionId=r.classPK)");

		select.append(" union ");

		// MBMessage

		select.append(
			"(select mbm.companyId, r.statsId from MBMessage mbm, " +
				"RatingsStats r where mbm.messageId=r.classPK)");

		select.append(" union ");

		// WikiPage

		select.append(
			"(select wp.companyId, r.statsId from WikiPage wp, " +
				"RatingsStats r where wp.pageId=r.classPK)");

		String update =
			"update RatingsStats set companyId = ? where statsId = ?";

		UpgradeCompanyIdUtil.updateCompanyColumnOnTable(
			"RatingsStats", select.toString(), update, "companyId", "statsId");
	}

}