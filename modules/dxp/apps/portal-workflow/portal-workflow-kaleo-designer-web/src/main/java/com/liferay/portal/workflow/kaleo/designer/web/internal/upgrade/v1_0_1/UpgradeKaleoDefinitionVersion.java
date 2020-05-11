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

package com.liferay.portal.workflow.kaleo.designer.web.internal.upgrade.v1_0_1;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.List;

/**
 * @author Inácio Nery
 */
public class UpgradeKaleoDefinitionVersion extends UpgradeProcess {

	public UpgradeKaleoDefinitionVersion(
		CounterLocalService counterLocalService,
		KaleoDefinitionLocalService kaleoDefinitionLocalService,
		KaleoDefinitionVersionLocalService kaleoDefinitionVersionLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourceActions resourceActions, UserLocalService userLocalService) {

		_counterLocalService = counterLocalService;
		_kaleoDefinitionLocalService = kaleoDefinitionLocalService;
		_kaleoDefinitionVersionLocalService =
			kaleoDefinitionVersionLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourceActions = resourceActions;
		_userLocalService = userLocalService;
	}

	protected KaleoDefinition addKaleoDefinition(
			long groupId, long userId, Timestamp createDate,
			Timestamp modifiedDate, String name, String title, String content,
			int version)
		throws PortalException {

		long kaleoDefinitionId = _counterLocalService.increment();

		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.createKaleoDefinition(
				kaleoDefinitionId);

		kaleoDefinition.setGroupId(StagingUtil.getLiveGroupId(groupId));

		User user = _userLocalService.getUser(userId);

		kaleoDefinition.setCompanyId(user.getCompanyId());
		kaleoDefinition.setUserId(user.getUserId());
		kaleoDefinition.setUserName(user.getFullName());

		kaleoDefinition.setCreateDate(createDate);
		kaleoDefinition.setModifiedDate(modifiedDate);
		kaleoDefinition.setName(name);
		kaleoDefinition.setTitle(title);
		kaleoDefinition.setContent(content);
		kaleoDefinition.setVersion((version == 0) ? 1 : version);
		kaleoDefinition.setActive(false);

		return _kaleoDefinitionLocalService.addKaleoDefinition(kaleoDefinition);
	}

	protected void addKaleoDefinitionVersion(
			long groupId, long companyId, long userId, Timestamp createDate,
			Timestamp modifiedDate, String name, String title, String content,
			int version, int draftVersion)
		throws PortalException {

		removeDuplicatesKaleoDefinitionVersion(
			companyId, name, version, draftVersion);

		ServiceContext serviceContext = new ServiceContext();

		if ((version > 0) && (draftVersion == 1)) {
			serviceContext.setAttribute(
				"status", WorkflowConstants.STATUS_APPROVED);
		}
		else {
			serviceContext.setAttribute(
				"status", WorkflowConstants.STATUS_DRAFT);
		}

		serviceContext.setCreateDate(createDate);
		serviceContext.setModifiedDate(modifiedDate);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(_getValidUserId(companyId, userId));

		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.fetchKaleoDefinition(
				name, serviceContext);

		if (kaleoDefinition == null) {
			kaleoDefinition = addKaleoDefinition(
				groupId, userId, createDate, modifiedDate, name, title, content,
				version);
		}

		_kaleoDefinitionVersionLocalService.addKaleoDefinitionVersion(
			kaleoDefinition.getKaleoDefinitionId(), name, title,
			StringPool.BLANK, content, getVersion(version, draftVersion),
			serviceContext);
	}

	@Override
	protected void doUpgrade() throws Exception {
		initKaleoDesignerModelsResourceActions();

		if (hasTable("KaleoDraftDefinition")) {
			upgradeKaleoDefinitionVersion();
		}
	}

	protected String getVersion(int version, int draftVersion) {
		if (version == 0) {
			version = 1;
		}

		return version + StringPool.PERIOD + --draftVersion;
	}

	protected boolean hasApprovedKaleoDefinitionVersion(
		long companyId, String name, int version, int draftVersion) {

		KaleoDefinitionVersion kaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.fetchKaleoDefinitionVersion(
				companyId, name, getVersion(version, draftVersion));

		if (kaleoDefinitionVersion == null) {
			return false;
		}

		if (kaleoDefinitionVersion.getStatus() ==
				WorkflowConstants.STATUS_APPROVED) {

			return true;
		}

		return false;
	}

	protected void initKaleoDesignerModelsResourceActions() throws Exception {
		_resourceActions.read(
			null, UpgradeKaleoDefinitionVersion.class.getClassLoader(),
			"/META-INF/resource-actions/default.xml");

		List<String> modelNames = _resourceActions.getPortletModelResources(
			KaleoDesignerPortletKeys.KALEO_DESIGNER);

		for (String modelName : modelNames) {
			List<String> modelActions =
				_resourceActions.getModelResourceActions(modelName);

			_resourceActionLocalService.checkResourceActions(
				modelName, modelActions);
		}
	}

	protected void removeDuplicatesKaleoDefinitionVersion(
		long companyId, String name, int version, int draftVersion) {

		try {
			KaleoDefinitionVersion kaleoDefinitionVersion =
				_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
					companyId, name, getVersion(version, draftVersion));

			_kaleoDefinitionVersionLocalService.deleteKaleoDefinitionVersion(
				kaleoDefinitionVersion);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}
	}

	protected void upgradeKaleoDefinitionVersion()
		throws PortalException, SQLException {

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select * from KaleoDraftDefinition order by version, " +
					"draftVersion");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				String name = rs.getString("name");
				int version = rs.getInt("version");
				int draftVersion = rs.getInt("draftVersion");

				if (hasApprovedKaleoDefinitionVersion(
						companyId, name, version, draftVersion)) {

					continue;
				}

				long groupId = rs.getLong("groupId");
				long userId = rs.getLong("userId");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String title = rs.getString("title");
				String content = rs.getString("content");

				addKaleoDefinitionVersion(
					groupId, companyId, userId, createDate, modifiedDate, name,
					title, content, version, draftVersion);
			}
		}
	}

	private long _getValidUserId(long companyId, long userId)
		throws PortalException {

		if (_userLocalService.fetchUserById(userId) != null) {
			return userId;
		}

		return _userLocalService.getDefaultUserId(companyId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeKaleoDefinitionVersion.class);

	private final CounterLocalService _counterLocalService;
	private final KaleoDefinitionLocalService _kaleoDefinitionLocalService;
	private final KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceActions _resourceActions;
	private final UserLocalService _userLocalService;

}