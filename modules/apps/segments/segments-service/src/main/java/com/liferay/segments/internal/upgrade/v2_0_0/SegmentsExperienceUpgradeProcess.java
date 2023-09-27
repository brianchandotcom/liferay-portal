/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.upgrade.v2_0_0;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author David Arques
 */
public class SegmentsExperienceUpgradeProcess extends UpgradeProcess {

	public SegmentsExperienceUpgradeProcess(
		CounterLocalService counterLocalService) {

		_counterLocalService = counterLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateSegmentsExperiences();
	}

	private void _updateSegmentsExperience(
		long segmentsExperienceId, String segmentsExperienceKey) {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update SegmentsExperience set segmentsExperienceKey = ? " +
					"where segmentsExperienceId = ?")) {

			preparedStatement.setString(1, segmentsExperienceKey);
			preparedStatement.setLong(2, segmentsExperienceId);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private void _updateSegmentsExperiences() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select segmentsExperienceId from SegmentsExperience")) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long segmentsExperienceId = resultSet.getLong(
						"segmentsExperienceId");

					_updateSegmentsExperience(
						segmentsExperienceId,
						String.valueOf(_counterLocalService.increment()));
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperienceUpgradeProcess.class);

	private final CounterLocalService _counterLocalService;

}