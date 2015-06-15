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

package com.liferay.polls.upgrade.v1_0_0;

import com.liferay.portal.upgrade.v7_0_0.util.PollsChoiceTable;
import com.liferay.portal.upgrade.v7_0_0.util.PollsQuestionTable;
import com.liferay.portal.upgrade.v7_0_0.util.PollsVoteTable;

import java.sql.SQLException;

/**
 * @author Levente Hudák
 */
public class UpgradeLastPublishDate
	extends com.liferay.portal.kernel.upgrade.UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradePollsChoice();
		upgradePollsQuestion();
		upgradePollsVote();
	}

	protected void upgradePollsChoice() throws Exception {
		try {
			runSQL("alter table PollsChoice add lastPublishDate DATE null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				PollsChoiceTable.TABLE_NAME, PollsChoiceTable.TABLE_COLUMNS,
				PollsChoiceTable.TABLE_SQL_CREATE,
				PollsChoiceTable.TABLE_SQL_ADD_INDEXES);
		}
	}

	protected void upgradePollsQuestion() throws Exception {
		try {
			runSQL("alter table PollsQuestion add lastPublishDate DATE null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				PollsQuestionTable.TABLE_NAME, PollsQuestionTable.TABLE_COLUMNS,
				PollsQuestionTable.TABLE_SQL_CREATE,
				PollsQuestionTable.TABLE_SQL_ADD_INDEXES);
		}
	}

	protected void upgradePollsVote() throws Exception {
		try {
			runSQL("alter table PollsVote add lastPublishDate DATE null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				PollsVoteTable.TABLE_NAME, PollsVoteTable.TABLE_COLUMNS,
				PollsVoteTable.TABLE_SQL_CREATE,
				PollsVoteTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}