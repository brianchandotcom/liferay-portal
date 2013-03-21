/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.trash.util.TrashUtil;

/**
 * @author Ryan Park
 * @author Zsolt Berentey
 */
public class DLActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getBody(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if (!activity.isClassName(DLFileEntry.class.getName())) {
			return StringPool.BLANK;
		}

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			activity.getClassPK());

		if (TrashUtil.isInTrash(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId())) {

			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(3);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				DLFileEntry.class.getName());

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			fileEntry.getFileEntryId());

		String fileEntryLink = assetRenderer.getURLDownload(themeDisplay);

		sb.append(wrapLink(fileEntryLink, "download-file", serviceContext));

		sb.append(StringPool.SPACE);

		String folderLink = getFolderLink(fileEntry, themeDisplay);

		sb.append(wrapLink(folderLink, "go-to-folder", serviceContext));

		return sb.toString();
	}

	@Override
	protected String getEntryTitle(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		if (activity.isClassName(DLFileEntry.class.getName())) {
			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
				activity.getClassPK());

			if (fileEntry.getModel() instanceof DLFileEntry) {
				DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

				DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

				if (dlFileVersion.isInTrash()) {
					return TrashUtil.getOriginalTitle(fileEntry.getTitle());
				}
			}

			return fileEntry.getTitle();
		}
		else if (activity.isClassName(DLFolder.class.getName())) {
			Folder folder = DLAppLocalServiceUtil.getFolder(
				activity.getClassPK());

			if (folder.getModel() instanceof DLFolder) {
				DLFolder dlFolder = (DLFolder)folder.getModel();

				if (dlFolder.isInTrash()) {
					return TrashUtil.getOriginalTitle(folder.getName());
				}
			}

			return folder.getName();
		}

		return StringPool.BLANK;
	}

	protected String getFolderLink(
		FileEntry fileEntry, ThemeDisplay themeDisplay) {

		StringBundler sb = new StringBundler(6);

		sb.append(themeDisplay.getPortalURL());
		sb.append(themeDisplay.getPathMain());
		sb.append("/document_library/find_folder?groupId=");
		sb.append(fileEntry.getRepositoryId());
		sb.append("&folderId=");
		sb.append(fileEntry.getFolderId());

		return sb.toString();
	}

	@Override
	protected String getPath(SocialActivity activity) {
		if (activity.isClassName(DLFileEntry.class.getName())) {
			return "/document_library/find_file_entry?fileEntryId=";
		}
		else if (activity.isClassName(DLFolder.class.getName())) {
			return "/document_library/find_folder?folderId=";
		}

		return StringPool.BLANK;
	}

	@Override
	protected String getTitlePattern(
		String groupName, SocialActivity activity) {

		int activityType = activity.getType();

		if (activityType == DLActivityKeys.ADD_FILE_ENTRY) {
			if (Validator.isNull(groupName)) {
				return "activity-document-library-add-file";
			}
			else {
				return "activity-document-library-add-file-in";
			}
		}
		else if (activityType == DLActivityKeys.UPDATE_FILE_ENTRY) {
			if (Validator.isNull(groupName)) {
				return "activity-document-library-update-file";
			}
			else {
				return "activity-document-library-update-file-in";
			}
		}
		else if (activityType == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			if (activity.isClassName(DLFileEntry.class.getName())) {
				if (Validator.isNull(groupName)) {
					return "activity-document-library-file-move-to-trash";
				}
				else {
					return "activity-document-library-file-move-to-trash-in";
				}
			}
			else if (activity.isClassName(DLFolder.class.getName())) {
				if (Validator.isNull(groupName)) {
					return "activity-document-library-folder-move-to-trash";
				}
				else {
					return "activity-document-library-folder-move-to-trash-in";
				}
			}
		}
		else if (activityType ==
					SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {

			if (activity.isClassName(DLFileEntry.class.getName())) {
				if (Validator.isNull(groupName)) {
					return "activity-document-library-file-restore-from-trash";
				}
				else {
					return
						"activity-document-library-file-restore-from-trash-in";
				}
			}
			else if (activity.isClassName(DLFolder.class.getName())) {
				if (Validator.isNull(groupName)) {
					return
						"activity-document-library-folder-restore-from-trash";
				}
				else {
					return
						"activity-document-library-folder-restore-from-trash-" +
							"in";
				}
			}
		}

		return null;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		if (activity.isClassName(DLFileEntry.class.getName())) {
			return DLFileEntryPermission.contains(
				permissionChecker, activity.getClassPK(), actionId);
		}
		else if (activity.isClassName(DLFolder.class.getName())) {
			return DLFolderPermission.contains(
				permissionChecker, activity.getGroupId(), activity.getClassPK(),
				actionId);
		}

		return false;
	}

	private static final String[] _CLASS_NAMES =
		{DLFileEntry.class.getName(), DLFolder.class.getName()};

}