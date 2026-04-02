/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.internal.message.listener;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tancredi Covioli
 */
@Component(
	property = {
		"destination.name=" + DestinationNames.BACKGROUND_TASK_STATUS,
		"service.ranking:Integer=100"
	},
	service = MessageListener.class
)
public class BackgroundTaskStatusMessageListener implements MessageListener {

	@Override
	public void receive(Message message) throws MessageListenerException {
		long companyId = message.getLong("companyId");

		if ((companyId == CompanyConstants.SYSTEM) ||
			!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-66359") ||
			(message.getInteger("status") !=
				BackgroundTaskConstants.STATUS_SUCCESSFUL) ||
			!Objects.equals(
				message.getString("taskExecutorClassName"),
				"com.liferay.exportimport.internal.background.task." +
					"LayoutSetPrototypeImportBackgroundTaskExecutor")) {

			return;
		}

		BackgroundTask backgroundTask =
			_backgroundTaskManager.fetchBackgroundTask(
				message.getLong("backgroundTaskId"));

		if (backgroundTask == null) {
			return;
		}

		Group group = _groupLocalService.fetchGroup(
			backgroundTask.getGroupId());

		if ((group == null) ||
			Objects.equals(group.getClassName(), Group.class.getName())) {

			return;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DSR_ROOM", companyId);

		if ((objectDefinition == null) ||
			!Objects.equals(
				objectDefinition.getClassName(), group.getClassName()) ||
			(group.getDefaultPublicPlid() == 0)) {

			return;
		}

		Role role = _roleLocalService.fetchRoleByExternalReferenceCode(
			"L_DSR_SELLER", companyId);

		if (role == null) {
			return;
		}

		try {
			_resourcePermissionLocalService.removeResourcePermission(
				companyId, Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(group.getDefaultPublicPlid()), role.getRoleId(),
				ActionKeys.VIEW);
		}
		catch (PortalException portalException) {
			throw new MessageListenerException(portalException);
		}
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}