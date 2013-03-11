/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.social;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.permission.BookmarksEntryPermission;
import com.liferay.portlet.bookmarks.service.permission.BookmarksFolderPermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;

/**
 * @author Juan Fernández
 * @author Zsolt Berentey
 */
public class BookmarksActivityInterpreter
	extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getPath(SocialActivity activity) throws Exception {
		if (activity.isClassName(BookmarksEntry.class.getName())) {
			return "/bookmarks/find_entry?entryId=";
		}
		else if (activity.isClassName(BookmarksFolder.class.getName())) {
			return "/bookmarks/find_folder?folderId=";
		}

		return StringPool.BLANK;
	}

	@Override
	protected String getTitle(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		if (activity.isClassName(BookmarksEntry.class.getName())) {
			BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(
				activity.getClassPK());

			return entry.getName();
		}
		else if (activity.isClassName(BookmarksFolder.class.getName())) {
			BookmarksFolder folder = BookmarksFolderLocalServiceUtil.getFolder(
				activity.getClassPK());

			return folder.getName();
		}

		return StringPool.BLANK;
	}

	@Override
	protected String getTitlePattern(String groupName, SocialActivity activity)
		throws Exception {

		int activityType = activity.getType();

		if (activityType == BookmarksActivityKeys.ADD_ENTRY) {
			if (Validator.isNull(groupName)) {
				return "activity-bookmarks-add-entry";
			}
			else {
				return "activity-bookmarks-add-entry-in";
			}
		}
		else if (activityType == BookmarksActivityKeys.UPDATE_ENTRY) {
			if (Validator.isNull(groupName)) {
				return "activity-bookmarks-update-entry";
			}
			else {
				return "activity-bookmarks-update-entry-in";
			}
		}
		else if (activityType == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			if (activity.isClassName(BookmarksEntry.class.getName())) {
				if (Validator.isNull(groupName)) {
					return "activity-bookmarks-entry-move-to-trash";
				}
				else {
					return "activity-bookmarks-entry-move-to-trash-in";
				}
			}
			else if (activity.isClassName(BookmarksFolder.class.getName())) {
				if (Validator.isNull(groupName)) {
					return "activity-bookmarks-folder-move-to-trash";
				}
				else {
					return "activity-bookmarks-folder-move-to-trash-in";
				}
			}
		}
		else if (activityType ==
					SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {

			if (activity.isClassName(BookmarksEntry.class.getName())) {
				if (Validator.isNull(groupName)) {
					return "activity-bookmarks-entry-restore-from-trash";
				}
				else {
					return "activity-bookmarks-entry-restore-from-trash-in";
				}
			}
			else if (activity.isClassName(BookmarksFolder.class.getName())) {
				if (Validator.isNull(groupName)) {
					return "activity-bookmarks-folder-restore-from-trash";
				}
				else {
					return "activity-bookmarks-folder-restore-from-trash-in";
				}
			}
		}

		return null;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ThemeDisplay themeDisplay)
		throws Exception {

		if (activity.isClassName(BookmarksEntry.class.getName())) {
			return BookmarksEntryPermission.contains(
				permissionChecker, activity.getClassPK(), actionId);
		}
		else if (activity.isClassName(BookmarksFolder.class.getName())) {
			return BookmarksFolderPermission.contains(
				permissionChecker, activity.getGroupId(), activity.getClassPK(),
				actionId);
		}

		return false;
	}

	private static final String[] _CLASS_NAMES = new String[] {
		BookmarksEntry.class.getName(), BookmarksFolder.class.getName()
	};

}