/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.post.upgrade.fix.osgi.commands;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.sql.SQLException;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BasePostUpgradeFixOSGiCommands {

	public static final String SCOPE = "postUpgradeFix";

	protected abstract void doExecute() throws Exception;

	protected void execute() {
		if (log.isInfoEnabled()) {
			if (log.isInfoEnabled()) {
				log.info("Executing " + getCommand());
			}
		}

		try {
			doExecute();

			if (log.isInfoEnabled()) {
				log.info("Finished executing " + getCommand());
			}
		}
		catch (Exception exception) {
			log.error(
				"An exception was thrown while executing " + getCommand(),
				exception);
		}
	}

	protected String getCommand() {
		return SCOPE + ":" + getFunction();
	}

	protected abstract String getFunction();

	protected void runSQL(String template) throws IOException, SQLException {
		DB db = DBManagerUtil.getDB();

		db.runSQL(template);
	}

	protected final Log log = LogFactoryUtil.getLog(getClass());

}