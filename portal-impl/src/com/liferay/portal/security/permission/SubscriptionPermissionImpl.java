/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;

/**
 * @author Mate Thurzo
 */
public class SubscriptionPermissionImpl implements SubscriptionPermission {

	public void check(User user, String className, long classPK)
		throws PortalException, SystemException {

		if (!contains(user, className, classPK)) {
			throw new PrincipalException();
		}
	}

	public boolean contains(User user, String className, long classPK)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			try {
				permissionChecker = PermissionCheckerFactoryUtil.create(
					user, true);

				PermissionThreadLocal.setPermissionChecker(permissionChecker);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Subscription permission checking failed for user: " +
							user.getUserId());
				}
				return false;
			}
		}

		if (className == null) {
			return false;
		}
		else if (className.equals(BlogsEntry.class.getName())) {
			return BlogsPermission.contains(
				permissionChecker, classPK, ActionKeys.SUBSCRIBE);
		}
		else if (className.equals(JournalArticle.class.getName())) {
			return JournalPermission.contains(
				permissionChecker, classPK, ActionKeys.SUBSCRIBE);
		}
		else if (className.equals(MBCategory.class.getName())) {
			return MBCategoryPermission.contains(
				permissionChecker, classPK, ActionKeys.SUBSCRIBE);
		}
		else if (className.equals(MBThread.class.getName())) {
			return MBMessagePermission.contains(
				permissionChecker, classPK, ActionKeys.SUBSCRIBE);
		}
		else if (className.equals(WikiNode.class.getName())) {
			return WikiNodePermission.contains(
				permissionChecker, classPK, ActionKeys.SUBSCRIBE);
		}
		else if (className.equals(WikiPage.class.getName())) {
			return WikiPagePermission.contains(
				permissionChecker, classPK, ActionKeys.SUBSCRIBE);
		}

		return true;
	}

	private static final Log _log =
		LogFactoryUtil.getLog(SubscriptionPermissionImpl.class);

}