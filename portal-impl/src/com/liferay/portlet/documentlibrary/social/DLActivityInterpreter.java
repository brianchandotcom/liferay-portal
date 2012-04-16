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

package com.liferay.portlet.documentlibrary.social;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;

/**
 * @author Ryan Park
 */
public class DLActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			activity.getClassPK());

		if (!DLFileEntryPermission.contains(
				permissionChecker, fileEntry.getFileEntryId(),
				ActionKeys.VIEW)) {

			return null;
		}

		String groupName = StringPool.BLANK;

		if (activity.getGroupId() != themeDisplay.getScopeGroupId()) {
			groupName = getGroupName(activity.getGroupId(), themeDisplay);
		}

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);

		int activityType = activity.getType();

		// Link

		String link =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/document_library/get_file?groupId=" +
					fileEntry.getRepositoryId() + "&folderId=" +
						fileEntry.getFolderId() + "&title=" +
							HttpUtil.encodeURL(fileEntry.getTitle());

		// Title

		String titlePattern = null;

		if (activityType == DLActivityKeys.ADD_FILE_ENTRY) {
			if (Validator.isNull(groupName)) {
				titlePattern = "activity-document-library-add-file";
			}
			else {
				titlePattern = "activity-document-library-add-file-in";
			}
		}
		else if (activityType == DLActivityKeys.UPDATE_FILE_ENTRY) {
			if (Validator.isNull(groupName)) {
				titlePattern = "activity-document-library-update-file";
			}
			else {
				titlePattern = "activity-document-library-update-file-in";
			}
		}

		String fileTitle = wrapLink(
			link, HtmlUtil.escape(cleanContent(fileEntry.getTitle())));

		Object[] titleArguments = new Object[] {
			groupName, creatorUserName, fileTitle
		};

		String title = themeDisplay.translate(titlePattern, titleArguments);

		// Body

		StringBundler sb = new StringBundler(3);

		String fileEntryLink =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/document_library/find_file_entry?fileEntryId=" +
					fileEntry.getFileEntryId();

		sb.append(wrapLink(fileEntryLink, "view-document", themeDisplay));
		sb.append(StringPool.SPACE);

		String folderLink =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/document_library/find_folder?groupId=" +
					fileEntry.getRepositoryId() + "&folderId=" +
						fileEntry.getFolderId();

		sb.append(wrapLink(folderLink, "go-to-folder", themeDisplay));

		String body = sb.toString();

		return new SocialActivityFeedEntry(link, title, body);
	}

	private static final String[] _CLASS_NAMES = new String[] {
		DLFileEntry.class.getName()
	};

}