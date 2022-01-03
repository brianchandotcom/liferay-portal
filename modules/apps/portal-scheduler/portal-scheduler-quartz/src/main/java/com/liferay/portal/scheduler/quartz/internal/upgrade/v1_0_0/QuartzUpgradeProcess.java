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

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Akos Thurzo
 */
public class QuartzUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateJobDetails();
	}

	private void _updateJobDetails() {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"update QUARTZ_JOB_DETAILS set job_class_name = ? where " +
					"job_class_name = ?")) {

			preparedStatement.setString(
				1,
				"com.liferay.portal.scheduler.quartz.internal.job." +
					"MessageSenderJob");
			preparedStatement.setString(
				2, "com.liferay.portal.scheduler.job.MessageSenderJob");

			preparedStatement.executeUpdate();
		}
		catch (SQLException sqlException) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqlException, sqlException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		QuartzUpgradeProcess.class);

}