/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service.permission;

import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Charles May
 * @author Roberto Díaz
 * @author Sergio González
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBDiscussion",
	service = BaseModelPermissionChecker.class
)
public class MBDiscussionPermission implements BaseModelPermissionChecker {

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		MBDiscussion mbDiscussion = _mbDiscussionLocalService.getMBDiscussion(
			primaryKey);

		if (!_discussionPermission.hasPermission(
				permissionChecker, mbDiscussion.getCompanyId(), groupId,
				mbDiscussion.getClassName(), mbDiscussion.getClassPK(),
				actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, mbDiscussion.getClassName(),
				mbDiscussion.getClassPK(), actionId);
		}
	}

	@Reference(unbind = "-")
	protected void setMBDiscussionLocalService(
		MBDiscussionLocalService mbDiscussionLocalService) {

		_mbDiscussionLocalService = mbDiscussionLocalService;
	}

	private static MBDiscussionLocalService _mbDiscussionLocalService;

	@Reference
	private DiscussionPermission _discussionPermission;

}