/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.comment.internal.security.permission.resource;

import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.portal.kernel.comment.DiscussionPermission;
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
	property = "model.class.name=com.liferay.message.boards.model.MBDiscussion",
	service = ModelResourcePermission.class
)
public class MBDiscussionModelResourcePermission
	implements ModelResourcePermission<MBDiscussion> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long discussionId,
			String actionId)
		throws PortalException {

		check(
			permissionChecker,
			_mbDiscussionLocalService.getMBDiscussion(discussionId), actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, MBDiscussion mbDiscussion,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, mbDiscussion, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MBDiscussion.class.getName(),
				mbDiscussion.getDiscussionId(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long discussionId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_mbDiscussionLocalService.getMBDiscussion(discussionId), actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, MBDiscussion mbDiscussion,
		String actionId) {

		return _discussionPermission.hasPermission(
			permissionChecker, mbDiscussion.getCompanyId(),
			mbDiscussion.getGroupId(), mbDiscussion.getClassName(),
			mbDiscussion.getClassPK(), actionId);
	}

	@Override
	public String getModelName() {
		return MBDiscussion.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private DiscussionPermission _discussionPermission;

	@Reference
	private MBDiscussionLocalService _mbDiscussionLocalService;

}