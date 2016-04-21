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

package com.liferay.knowledge.base.service.permission;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.service.KBCommentLocalServiceUtil;
import com.liferay.knowledge.base.service.KBTemplateLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Shinn Lok
 */
public class KBCommentPermission {

	public static void check(
			PermissionChecker permissionChecker, KBComment kbComment,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, kbComment, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long kbCommentId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, kbCommentId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, KBComment kbComment,
			String actionId)
		throws PortalException {

		if (permissionChecker.getUserId() == kbComment.getUserId()) {
			return true;
		}

		if (actionId.equals(KBActionKeys.VIEW)) {
			return AdminPermission.contains(
				permissionChecker, kbComment.getGroupId(),
				KBActionKeys.VIEW_SUGGESTIONS);
		}

		if (!actionId.equals(KBActionKeys.DELETE) &&
			!actionId.equals(KBActionKeys.UPDATE)) {

			return false;
		}

		String className = kbComment.getClassName();

		if (className.equals(KBArticle.class.getName())) {
			KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
				kbComment.getClassPK(), WorkflowConstants.STATUS_ANY);

			return permissionChecker.hasPermission(
				kbArticle.getGroupId(), KBArticle.class.getName(),
				kbArticle.getResourcePrimKey(), KBActionKeys.UPDATE);
		}
		else if (className.equals(KBTemplate.class.getName())) {
			KBTemplate kbTemplate = KBTemplateLocalServiceUtil.getKBTemplate(
				kbComment.getClassPK());

			return permissionChecker.hasPermission(
				kbTemplate.getGroupId(), KBTemplate.class.getName(),
				kbTemplate.getPrimaryKey(), KBActionKeys.UPDATE);
		}

		return false;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long kbCommentId,
			String actionId)
		throws PortalException {

		KBComment kbComment = KBCommentLocalServiceUtil.getKBComment(
			kbCommentId);

		return contains(permissionChecker, kbComment, actionId);
	}

}