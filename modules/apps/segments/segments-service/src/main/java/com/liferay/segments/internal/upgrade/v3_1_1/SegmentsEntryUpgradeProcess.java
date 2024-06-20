/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.upgrade.v3_1_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Marcellus Tavares
 */
public class SegmentsEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select ctCollectionId, groupId, segmentsEntryId, ",
					"criteria from SegmentsEntry where criteria like ",
					"'%deviceBrand%' or criteria like '%deviceModel%' or ",
					"criteria like '%deviceScreenResolutionHeight%' or ",
					"criteria like '%deviceScreenResolutionWidth%'"))) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					_inactivateSegmentsEntry(
						resultSet.getLong("ctCollectionId"),
						resultSet.getLong("groupId"),
						resultSet.getLong("segmentsEntryId"),
						resultSet.getString("criteria"));
				}
			}
		}
	}

	private void _inactivateSegmentsEntry(
		long ctCollectionId, long groupId, long segmentsEntryId,
		String criteria) {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update SegmentsEntry set active_ = ? where ctCollectionId = " +
					"? and segmentsEntryId = ?")) {

			preparedStatement.setBoolean(1, false);
			preparedStatement.setLong(2, ctCollectionId);
			preparedStatement.setLong(3, segmentsEntryId);

			preparedStatement.executeUpdate();

			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Successfully inactivated segments entry {groupId: " +
							"%s, segmentsEntryId: %s, criteria: %s} because " +
								"it contains device-related constraints",
						groupId, segmentsEntryId, criteria));
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to inactivate segments entry ID " + segmentsEntryId,
				exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryUpgradeProcess.class);

}