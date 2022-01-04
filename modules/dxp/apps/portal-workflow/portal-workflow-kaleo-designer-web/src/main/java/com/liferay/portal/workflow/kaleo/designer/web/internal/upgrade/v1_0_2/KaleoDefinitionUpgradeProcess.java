/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.designer.web.internal.upgrade.v1_0_2;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Rodrigo Paulino
 */
public class KaleoDefinitionUpgradeProcess extends UpgradeProcess {

	public KaleoDefinitionUpgradeProcess(
		CounterLocalService counterLocalService,
		KaleoDefinitionLocalService kaleoDefinitionLocalService,
		UserLocalService userLocalService) {

		_counterLocalService = counterLocalService;
		_kaleoDefinitionLocalService = kaleoDefinitionLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (hasTable("KaleoDefinitionVersion") &&
			hasTable("KaleoDraftDefinition")) {

			_addKaleoDefinitionsFromKaleoDefinitionVersion();
		}
	}

	private void _addKaleoDefinition(
			long groupId, long userId, Timestamp createDate,
			Timestamp modifiedDate, String name, String title, String content,
			int version)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		long liveGroupId = StagingUtil.getLiveGroupId(groupId);

		long kaleoDefinitionId = _counterLocalService.increment();

		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.createKaleoDefinition(
				kaleoDefinitionId);

		kaleoDefinition.setGroupId(liveGroupId);
		kaleoDefinition.setCompanyId(user.getCompanyId());
		kaleoDefinition.setUserId(user.getUserId());
		kaleoDefinition.setUserName(user.getFullName());
		kaleoDefinition.setCreateDate(createDate);
		kaleoDefinition.setModifiedDate(modifiedDate);
		kaleoDefinition.setName(name);
		kaleoDefinition.setTitle(title);
		kaleoDefinition.setContent(content);
		kaleoDefinition.setVersion(version);
		kaleoDefinition.setActive(false);

		_kaleoDefinitionLocalService.addKaleoDefinition(kaleoDefinition);
	}

	private void _addKaleoDefinitionsFromKaleoDefinitionVersion()
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select KaleoDefinitionVersion.* from ",
					"KaleoDefinitionVersion join (select name,  ",
					"max(kaleoDefinitionVersionId) as ",
					"kaleoDefinitionVersionId from KaleoDefinitionVersion ",
					"group by name) sub on sub.name = KaleoDefinitionVersion.",
					"name and sub.kaleoDefinitionVersionId = ",
					"KaleoDefinitionVersion.kaleoDefinitionVersionId left ",
					"join KaleoDefinition on KaleoDefinitionVersion.name = ",
					"KaleoDefinition.name where KaleoDefinition.",
					"kaleoDefinitionId is null"));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long groupId = resultSet.getLong("groupId");
				long userId = resultSet.getLong("userId");
				Timestamp createDate = resultSet.getTimestamp("createDate");
				Timestamp modifiedDate = resultSet.getTimestamp("modifiedDate");
				String name = resultSet.getString("name");
				String title = resultSet.getString("title");
				String content = resultSet.getString("content");
				String version = resultSet.getString("version");

				_addKaleoDefinition(
					groupId, userId, createDate, modifiedDate, name, title,
					content, _getVersion(version));
			}
		}
	}

	private int _getVersion(String version) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		return versionParts[0];
	}

	private final CounterLocalService _counterLocalService;
	private final KaleoDefinitionLocalService _kaleoDefinitionLocalService;
	private final UserLocalService _userLocalService;

}