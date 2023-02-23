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

package com.liferay.object.internal.upgrade.v4_0_1;

import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jorge García Jiménez
 */
public class CleanObjectEntriesWithNonExistentGroupIdUpgradeProcess
	extends UpgradeProcess {

	public CleanObjectEntriesWithNonExistentGroupIdUpgradeProcess(
		ObjectEntryLocalService objectEntryLocalService) {

		_objectEntryLocalService = objectEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"SELECT ObjectEntry.objectEntryId from ObjectEntry left join " +
					"Group_ on ObjectEntry.groupId = Group_.groupId where " +
						"ObjectEntry.groupId != 0 and Group_.groupId is null",
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long objectEntryId = resultSet.getLong("objectEntryId");

				try {
					_objectEntryLocalService.deleteObjectEntry(objectEntryId);
				}
				catch (PortalException portalException) {
					_log.error(
						"Unable to delete object entry " + objectEntryId,
						portalException);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CleanObjectEntriesWithNonExistentGroupIdUpgradeProcess.class);

	private final ObjectEntryLocalService _objectEntryLocalService;

}