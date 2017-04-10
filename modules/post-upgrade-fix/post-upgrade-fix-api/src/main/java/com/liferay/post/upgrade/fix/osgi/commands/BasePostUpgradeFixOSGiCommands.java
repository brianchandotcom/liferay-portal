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
		catch (Exception e) {
			log.error(
				"An exception was thrown while executing " + getCommand(), e);
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