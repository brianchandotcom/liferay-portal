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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.service.permission.MBPermission;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;

/**
 * @author Mate Thurzo
 */
public class SubscriptionPermissionImpl implements SubscriptionPermission {

	public void check(
			PermissionChecker permissionChecker, String className, long classPK)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, className, classPK)) {
			throw new PrincipalException();
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, String className, long classPK)
		throws PortalException, SystemException {

		if (className == null) {
			return false;
		}

		if (className.equals(BlogsEntry.class.getName())) {
			return BlogsPermission.contains(
				permissionChecker, classPK, ActionKeys.SUBSCRIBE);
		}
		else if (className.equals(JournalArticle.class.getName())) {
			return JournalPermission.contains(
				permissionChecker, classPK, ActionKeys.SUBSCRIBE);
		}
		else if (className.equals(MBCategory.class.getName())) {
			Group group = GroupLocalServiceUtil.fetchGroup(classPK);

			if (group == null) {
				return MBCategoryPermission.contains(
					permissionChecker, classPK, ActionKeys.SUBSCRIBE);
			}
			else {
				return MBPermission.contains(
					permissionChecker, classPK, ActionKeys.SUBSCRIBE);
			}
		}
		else if (className.equals(MBThread.class.getName())) {
			MBThread thread = MBThreadLocalServiceUtil.fetchThread(classPK);

			return MBMessagePermission.contains(
				permissionChecker, thread.getRootMessageId(),
				ActionKeys.SUBSCRIBE);
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

}