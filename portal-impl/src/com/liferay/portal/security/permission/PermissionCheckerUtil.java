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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.permission.BookmarksEntryPermission;
import com.liferay.portlet.bookmarks.service.permission.BookmarksPermission;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.service.permission.MBPermission;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionCheckerUtil {

	public static Boolean containsResourcePermission(
			PermissionChecker permissionChecker, String className, long classPK,
			String actionId)
		throws PortalException {

		if (className.equals(BlogsEntry.class.getName())) {
			return BlogsPermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(BookmarksEntry.class.getName())) {
			return BookmarksEntryPermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(BookmarksFolder.class.getName())) {
			return BookmarksPermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(DLFileEntry.class.getName())) {
			return DLFileEntryPermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(Folder.class.getName())) {
			return DLPermission.contains(permissionChecker, classPK, actionId);
		}
		else if (className.equals(JournalArticle.class.getName())) {
			return JournalArticlePermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(JournalFolder.class.getName())) {
			return JournalPermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(MBCategory.class.getName())) {
			return MBPermission.contains(permissionChecker, classPK, actionId);
		}
		else if (className.equals(MBMessage.class.getName())) {
			return MBMessagePermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(MBThread.class.getName())) {
			return MBMessagePermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(WikiNode.class.getName())) {
			return WikiNodePermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(WikiPage.class.getName())) {
			return WikiPagePermission.contains(
				permissionChecker, classPK, actionId);
		}

		return null;
	}

	public static void setThreadValues(User user) {
		if (user == null) {
			PrincipalThreadLocal.setName(null);
			PermissionThreadLocal.setPermissionChecker(null);

			return;
		}

		long userId = user.getUserId();

		String name = String.valueOf(userId);

		PrincipalThreadLocal.setName(name);

		try {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker == null) {
				Class<?> clazz = Class.forName(PropsValues.PERMISSIONS_CHECKER);

				permissionChecker = (PermissionChecker)clazz.newInstance();
			}

			permissionChecker.init(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PermissionCheckerUtil.class);

}