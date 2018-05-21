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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
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
		KaleoDefinitionVersionLocalService kaleoDefinitionVersionLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourceActions resourceActions) {

		_kaleoDefinitionVersionLocalService =
			kaleoDefinitionVersionLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourceActions = resourceActions;
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
		serviceContext.setUserId(userId);

		_kaleoDefinitionVersionLocalService.addKaleoDefinitionVersion(
			name, title, StringPool.BLANK, content,
			getVersion(version, draftVersion), serviceContext);
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
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe);
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
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String name = rs.getString("name");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int version = rs.getInt("version");
				int draftVersion = rs.getInt("draftVersion");

				addKaleoDefinitionVersion(
					groupId, companyId, userId, createDate, modifiedDate, name,
					title, content, version, draftVersion);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeKaleoDefinitionVersion.class);

	private final KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceActions _resourceActions;

}