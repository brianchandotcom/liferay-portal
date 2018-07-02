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
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author     Adolfo Pérez
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class SuggestionPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId,
			KBArticle kbArticle, String actionId)
		throws PortalException {

		if (!actionId.equals(KBActionKeys.VIEW_SUGGESTIONS)) {
			throw new IllegalArgumentException(
				"Suggestions only support the " +
					KBActionKeys.VIEW_SUGGESTIONS + " permission");
		}

		if (AdminPermission.contains(
				permissionChecker, groupId, KBActionKeys.VIEW_SUGGESTIONS) ||
			KBArticlePermission.contains(
				permissionChecker, kbArticle, KBActionKeys.UPDATE)) {

			return true;
		}

		return false;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String className,
			long classPK, String actionId)
		throws PortalException {

		if (!className.equals(KBArticleConstants.getClassName())) {
			throw new IllegalArgumentException(
				"Only KB articles support suggestions");
		}

		KBArticle kbArticle = KBArticleLocalServiceUtil.fetchKBArticle(classPK);

		if (kbArticle != null) {
			kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
				kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_ANY);
		}
		else {
			kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
				classPK, WorkflowConstants.STATUS_ANY);
		}

		return contains(permissionChecker, groupId, kbArticle, actionId);
	}

}