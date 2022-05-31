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

package com.liferay.segments.internal.upgrade.v2_6_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Eudaldo Alonso
 */
public class SegmentsExperienceUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateSegmentsExperiencePriorities();
	}

	private void _updateSegmentsExperience(
		long segmentsExperienceId, int priority) {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update SegmentsExperience set priority = ? where " +
					"segmentsExperienceId = ?")) {

			preparedStatement.setInt(1, priority + 1);
			preparedStatement.setLong(2, segmentsExperienceId);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private void _updateSegmentsExperiencePriorities() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select segmentsExperienceId, priority from " +
					"SegmentsExperience where priority >= 0 and " +
						"segmentsExperienceKey != ? order by priority desc")) {

			preparedStatement.setString(
				1, SegmentsExperienceConstants.KEY_DEFAULT);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long segmentsExperienceId = resultSet.getLong(
						"segmentsExperienceId");
					int priority = resultSet.getInt("priority");

					_updateSegmentsExperience(segmentsExperienceId, priority);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperienceUpgradeProcess.class);

}