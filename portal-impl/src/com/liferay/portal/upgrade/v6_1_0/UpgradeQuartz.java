/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;

import java.sql.Types;

/**
 * @author Miguel Pastor
 */
public class UpgradeQuartz extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeQuartzTables();
	}

	protected void upgradeQuartzTables() throws Exception {

		if (DB.TYPE_POSTGRESQL.equals(DBFactoryUtil.getDB().getType())) {

			upgradeTableQuartzJobDetails();

			upgradeTableQuartzTriggers();

			upgradeTableQuartzBlobTriggers();

			upgradeTableQuartzCalendars();
		}
	}

	protected void upgradeTable(
			String tableName, Object[][] columns, String createSql)
		throws Exception {

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			tableName, columns);

		upgradeTable.setCreateSQL(createSql);

		upgradeTable.updateTable();
	}

	protected void upgradeTableQuartzBlobTriggers() throws Exception {
		upgradeTable(QUARTZ_BLOB_TRIGGERS_TABLE_NAME,
			QUARTZ_BLOB_TRIGGERS_TABLE_COLUMNS,
			QUARTZ_BLOB_TRIGGERS_TABLE_SQL_CREATE);
	}

	protected void upgradeTableQuartzCalendars() throws Exception {
		upgradeTable(QUARTZ_CALENDARS_TABLE_NAME,
			QUARTZ_CALENDARS_TABLE_COLUMNS,
			QUARTZ_CALENDARS_TABLE_SQL_CREATE);
	}

	protected void upgradeTableQuartzJobDetails() throws Exception {
		upgradeTable(QUARTZ_JOB_DETAILS_TABLE_NAME,
			QUARTZ_JOB_DETAILS_TABLE_COLUMNS,
			QUARTZ_JOB_DETAILS_TABLE_SQL_CREATE);
	}

	protected void upgradeTableQuartzTriggers() throws Exception {
		upgradeTable(QUARTZ_TRIGGERS_TABLE_NAME,
			QUARTZ_TRIGGERS_TABLE_COLUMNS,
			QUARTZ_TRIGGERS_TABLE_SQL_CREATE);
	}

	protected static final Object[][] QUARTZ_BLOB_TRIGGERS_TABLE_COLUMNS = {
		{"TRIGGER_NAME", Types.VARCHAR},
		{"TRIGGER_GROUP", Types.VARCHAR},
		{"BLOB_DATA", Types.BLOB}
	};

	protected static final String QUARTZ_BLOB_TRIGGERS_TABLE_NAME =
		"QUARTZ_BLOB_TRIGGERS";

	protected static final String QUARTZ_BLOB_TRIGGERS_TABLE_SQL_CREATE =
			"create table " + QUARTZ_BLOB_TRIGGERS_TABLE_NAME +
				" (TRIGGER_NAME VARCHAR(80) not null, " +
					"TRIGGER_GROUP VARCHAR(80) not null, " +
						"BLOB_DATA SBLOB null, " +
							"primary key (TRIGGER_NAME, TRIGGER_GROUP))";

	protected static final Object[][] QUARTZ_CALENDARS_TABLE_COLUMNS = {
		{"CALENDAR_NAME", Types.VARCHAR},
		{"CALENDAR", Types.BLOB}
	};

	protected static final String QUARTZ_CALENDARS_TABLE_NAME =
		"QUARTZ_CALENDARS";

	protected static final String QUARTZ_CALENDARS_TABLE_SQL_CREATE =
			"create table " + QUARTZ_CALENDARS_TABLE_NAME +
				" (CALENDAR_NAME VARCHAR(80) not null primary key, " +
					"CALENDAR SBLOB not null)";

	protected static final Object[][] QUARTZ_JOB_DETAILS_TABLE_COLUMNS = {
		{"JOB_NAME", Types.VARCHAR},
		{"JOB_GROUP", Types.VARCHAR},
		{"DESCRIPTION", Types.VARCHAR},
		{"JOB_CLASS_NAME", Types.VARCHAR},
		{"IS_DURABLE", Types.BOOLEAN},
		{"IS_VOLATILE", Types.BOOLEAN},
		{"IS_STATEFUL", Types.BOOLEAN},
		{"REQUESTS_RECOVERY", Types.BOOLEAN},
		{"JOB_DATA", Types.BLOB}
	};

	protected static final String QUARTZ_JOB_DETAILS_TABLE_NAME =
		"QUARTZ_JOB_DETAILS";

	protected static final String QUARTZ_JOB_DETAILS_TABLE_SQL_CREATE =
			"create table " + QUARTZ_JOB_DETAILS_TABLE_NAME + " (JOB_NAME " +
				"VARCHAR(80) not null, JOB_GROUP VARCHAR(80) not null, " +
					"DESCRIPTION VARCHAR(120) null, JOB_CLASS_NAME " +
						"VARCHAR(128) not null, IS_DURABLE BOOLEAN not null," +
							" IS_VOLATILE BOOLEAN not null, IS_STATEFUL " +
								"BOOLEAN not null, REQUESTS_RECOVERY BOOLEAN" +
									" not null, JOB_DATA SBLOB null," +
										"primary key (JOB_NAME, JOB_GROUP))";

	protected static final Object[][] QUARTZ_TRIGGERS_TABLE_COLUMNS = {
		{"TRIGGER_NAME", Types.VARCHAR},
		{"TRIGGER_GROUP", Types.VARCHAR},
		{"JOB_NAME", Types.VARCHAR},
		{"JOB_GROUP", Types.VARCHAR},
		{"IS_VOLATILE", Types.BOOLEAN},
		{"IS_VOLATILE", Types.BOOLEAN},
		{"DESCRIPTION", Types.VARCHAR},
		{"REQUESTS_RECOVERY", Types.BOOLEAN},
		{"NEXT_FIRE_TIME", Types.BIGINT},
		{"PREV_FIRE_TIME", Types.BIGINT},
		{"TRIGGER_STATE", Types.VARCHAR},
		{"TRIGGER_TYPE", Types.VARCHAR},
		{"START_TIME", Types.BIGINT},
		{"END_TIME", Types.BIGINT},
		{"CALENDAR_NAME", Types.VARCHAR},
		{"MISFIRE_INSTR", Types.INTEGER},
		{"JOB_DATA", Types.BLOB},
	};

	protected static final String QUARTZ_TRIGGERS_TABLE_NAME =
		"QUARTZ_TRIGGERS";

	protected static final String QUARTZ_TRIGGERS_TABLE_SQL_CREATE =
		"create table " + QUARTZ_TRIGGERS_TABLE_NAME + " (TRIGGER_NAME VARCHAR(80) not null, TRIGGER_GROUP VARCHAR(80) not null, JOB_NAME VARCHAR(80) not null, JOB_GROUP VARCHAR(80) not null, IS_VOLATILE BOOLEAN not null, DESCRIPTION VARCHAR(120) null, NEXT_FIRE_TIME LONG null, PREV_FIRE_TIME LONG null, PRIORITY INTEGER null, TRIGGER_STATE VARCHAR(16) not null, TRIGGER_TYPE VARCHAR(8) not null, START_TIME LONG not null, END_TIME LONG null, CALENDAR_NAME VARCHAR(80) null, MISFIRE_INSTR INTEGER null, JOB_DATA SBLOB null, primary key (TRIGGER_NAME, TRIGGER_GROUP))";

}