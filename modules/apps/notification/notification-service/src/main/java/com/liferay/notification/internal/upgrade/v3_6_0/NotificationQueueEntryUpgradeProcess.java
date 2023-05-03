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

package com.liferay.notification.internal.upgrade.v3_6_0;

import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Leite
 */
public class NotificationQueueEntryUpgradeProcess extends UpgradeProcess {

	public NotificationQueueEntryUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		ResourceLocalService resourceLocalService) {

		_classNameLocalService = classNameLocalService;
		_resourceLocalService = resourceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select notificationQueueEntryId, companyId, userId, ",
					"classNameId, classPK from NotificationQueueEntry where ",
					"notificationQueueEntryId not in (select primKeyId from ",
					"ResourcePermission where name = ?)"));
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select objectDefinitionId from ObjectEntry where " +
					"objectEntryId = ?");
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update NotificationQueueEntry set classNameId = ? where " +
						"notificationQueueEntryId = ?")) {

			preparedStatement1.setString(
				1, NotificationQueueEntry.class.getName());

			try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
				while (resultSet1.next()) {
					_resourceLocalService.addResources(
						resultSet1.getLong("companyId"), 0,
						resultSet1.getLong("userId"),
						NotificationQueueEntry.class.getName(),
						resultSet1.getLong("notificationQueueEntryId"), false,
						true, true);

					long objectDefinitionId = 0;

					preparedStatement2.setLong(
						1, resultSet1.getLong("classPK"));

					try (ResultSet resultSet2 =
							preparedStatement2.executeQuery()) {

						if (resultSet2.next()) {
							objectDefinitionId = resultSet2.getLong(
								"objectDefinitionId");
						}
					}

					if (objectDefinitionId == 0) {
						continue;
					}

					ClassName className = _classNameLocalService.fetchClassName(
						"com.liferay.object.model.ObjectDefinition#" +
							objectDefinitionId);

					if (className == null) {
						continue;
					}

					long classNameId = className.getClassNameId();

					if ((classNameId == 0) ||
						(classNameId == resultSet1.getLong("classNameId"))) {

						continue;
					}

					preparedStatement3.setLong(1, classNameId);

					preparedStatement3.setLong(
						2, resultSet1.getLong("notificationQueueEntryId"));

					preparedStatement3.addBatch();
				}
			}

			preparedStatement3.executeBatch();
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final ResourceLocalService _resourceLocalService;

}