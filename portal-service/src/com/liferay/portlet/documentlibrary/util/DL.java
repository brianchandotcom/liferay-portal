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

package com.liferay.portlet.documentlibrary.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
@ProviderType
public interface DL {

	public static final String MANUAL_CHECK_IN_REQUIRED =
		"manualCheckInRequired";

	public static final String MANUAL_CHECK_IN_REQUIRED_PATH =
		StringPool.SLASH + MANUAL_CHECK_IN_REQUIRED;

	public static final String OFFICE_EXTENSION = "officeExtension";

	public static final String OFFICE_EXTENSION_PATH =
		StringPool.SLASH + OFFICE_EXTENSION;

	public static final String WEBDAV_CHECK_IN_MODE = "webDAVCheckInMode";

	public int compareVersions(String version1, String version2);

	public String getAbsolutePath(PortletRequest portletRequest, long folderId)
		throws PortalException;

	public Set<String> getAllMediaGalleryMimeTypes();

	public String getDDMStructureKey(DLFileEntryType dlFileEntryType);

	public String getDDMStructureKey(String fileEntryTypeUuid);

	public String getDeprecatedDDMStructureKey(DLFileEntryType dlFileEntryType);

	public String getDeprecatedDDMStructureKey(long fileEntryTypeId);

	public String getDividedPath(long id);

	public String getDLFileEntryControlPanelLink(
			PortletRequest portletRequest, long fileEntryId)
		throws PortalException;

	public String getDLFolderControlPanelLink(
			PortletRequest portletRequest, long folderId)
		throws PortalException;

	public String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString);

	public String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL);

	public Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName);

	public Map<String, String> getEmailFromDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName);

	public List<FileEntry> getFileEntries(Hits hits);

	public String getFileEntryImage(
		FileEntry fileEntry, ThemeDisplay themeDisplay);

	public Set<Long> getFileEntryTypeSubscriptionClassPKs(long userId);

	public String getFileIcon(String extension);

	public String getFileIconCssClass(String extension);

	public String getGenericName(String extension);

	public String getImagePreviewURL(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay)
		throws Exception;

	public String getImagePreviewURL(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception;

	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString);

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getPreviewURL(FileEntry,
	 *             FileVersion, ThemeDisplay, String, boolean, boolean)}
	 */
	@Deprecated
	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendToken);

	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL);

	public <T> OrderByComparator<T> getRepositoryModelOrderByComparator(
		String orderByCol, String orderByType);

	public <T> OrderByComparator<T> getRepositoryModelOrderByComparator(
		String orderByCol, String orderByType, boolean orderByModel);

	public String getSanitizedFileName(String title, String extension);

	public String getTempFileId(long id, String version);

	public String getTempFileId(long id, String version, String languageId);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getThumbnailSrc(FileEntry,
	 *             ThemeDisplay)}
	 */
	@Deprecated
	public String getThumbnailSrc(
			FileEntry fileEntry, DLFileShortcut dlFileShortcut,
			ThemeDisplay themeDisplay)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getThumbnailSrc(FileEntry,
	 *             FileVersion, ThemeDisplay)}
	 */
	@Deprecated
	public String getThumbnailSrc(
			FileEntry fileEntry, FileVersion fileVersion,
			DLFileShortcut dlFileShortcut, ThemeDisplay themeDisplay)
		throws Exception;

	public String getThumbnailSrc(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay)
		throws Exception;

	public String getThumbnailSrc(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception;

	public String getThumbnailStyle();

	public String getThumbnailStyle(boolean max, int margin);

	public String getThumbnailStyle(
		boolean max, int margin, int height, int width);

	public String getTitleWithExtension(FileEntry fileEntry);

	public String getTitleWithExtension(String title, String extension);

	public String getUniqueFileName(
		long groupId, long folderId, String fileName);

	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry)
		throws PortalException;

	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired)
		throws PortalException;

	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired, boolean officeExtensionRequired)
		throws PortalException;

	public boolean hasWorkflowDefinitionLink(
			long companyId, long groupId, long folderId, long fileEntryTypeId)
		throws Exception;

	public boolean isAutoGeneratedDLFileEntryTypeDDMStructureKey(
		String ddmStructureKey);

	public abstract boolean isOfficeExtension(String extension);

	public boolean isSubscribedToFileEntryType(
		long companyId, long groupId, long userId, long fileEntryTypeId);

	public boolean isSubscribedToFolder(
			long companyId, long groupId, long userId, long folderId)
		throws PortalException;

	public boolean isSubscribedToFolder(
			long companyId, long groupId, long userId, long folderId,
			boolean recursive)
		throws PortalException;

	public boolean isValidVersion(String version);

	public void startWorkflowInstance(
			long userId, DLFileVersion dlFileVersion, String syncEventType,
			ServiceContext serviceContext)
		throws PortalException;

}