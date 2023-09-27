/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.security.permission.resource;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jiaxu Wei
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBThread",
	service = ModelResourcePermission.class
)
public class MBThreadModelResourcePermission
	implements ModelResourcePermission<MBThread> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long threadId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, threadId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MBThread.class.getName(), threadId,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, MBThread mbThread,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, mbThread, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MBThread.class.getName(),
				mbThread.getRootMessageId(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long threadId, String actionId)
		throws PortalException {

		return _messageModelResourcePermission.contains(
			permissionChecker, threadId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, MBThread mbThread,
			String actionId)
		throws PortalException {

		return contains(permissionChecker, mbThread.getThreadId(), actionId);
	}

	@Override
	public String getModelName() {
		return MBThread.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _messageModelResourcePermission.getPortletResourcePermission();
	}

	@Reference(
		target = "(model.class.name=com.liferay.message.boards.model.MBMessage)"
	)
	private ModelResourcePermission<MBMessage> _messageModelResourcePermission;

}