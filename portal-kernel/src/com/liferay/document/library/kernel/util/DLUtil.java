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

package com.liferay.document.library.kernel.util;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class DLUtil {

	public static int compareVersions(String version1, String version2) {
		return _dl.compareVersions(version1, version2);
	}

	public static String getAbsolutePath(
			PortletRequest portletRequest, long folderId)
		throws PortalException {

		return _dl.getAbsolutePath(portletRequest, folderId);
	}

	public static Set<String> getAllMediaGalleryMimeTypes() {
		return _dl.getAllMediaGalleryMimeTypes();
	}

	public static String getDDMStructureKey(DLFileEntryType dlFileEntryType) {
		return _dl.getDDMStructureKey(dlFileEntryType);
	}

	public static String getDDMStructureKey(String fileEntryTypeUuid) {
		return _dl.getDDMStructureKey(fileEntryTypeUuid);
	}

	public static String getDeprecatedDDMStructureKey(
		DLFileEntryType dlFileEntryType) {

		return _dl.getDeprecatedDDMStructureKey(dlFileEntryType);
	}

	public static String getDeprecatedDDMStructureKey(long fileEntryTypeId) {
		return _dl.getDeprecatedDDMStructureKey(fileEntryTypeId);
	}

	public static String getDividedPath(long id) {
		return _dl.getDividedPath(id);
	}

	public static DL getDL() {
		return _dl;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             com.liferay.document.library.util.DLURLHelper#getDownloadURL(
	 *             FileEntry, FileVersion, ThemeDisplay, String)}
	 */
	@Deprecated
	public static String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString) {

		return _dl.getDownloadURL(
			fileEntry, fileVersion, themeDisplay, queryString);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             com.liferay.document.library.util.DLURLHelper#getDownloadURL(
	 *             FileEntry, FileVersion, ThemeDisplay, String, boolean,
	 *             boolean)}
	 */
	@Deprecated
	public static String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		return _dl.getDownloadURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);
	}

	public static Map<String, String> getEmailDefinitionTerms(
		RenderRequest renderRequest, String emailFromAddress,
		String emailFromName) {

		return _dl.getEmailDefinitionTerms(
			renderRequest, emailFromAddress, emailFromName);
	}

	public static Map<String, String> getEmailFromDefinitionTerms(
		RenderRequest renderRequest, String emailFromAddress,
		String emailFromName) {

		return _dl.getEmailFromDefinitionTerms(
			renderRequest, emailFromAddress, emailFromName);
	}

	public static List<FileEntry> getFileEntries(Hits hits) {
		return _dl.getFileEntries(hits);
	}

	public static String getFileEntryImage(
		FileEntry fileEntry, ThemeDisplay themeDisplay) {

		return _dl.getFileEntryImage(fileEntry, themeDisplay);
	}

	public static String getFileIcon(String extension) {
		return _dl.getFileIcon(extension);
	}

	public static String getFileIconCssClass(String extension) {
		return _dl.getFileIconCssClass(extension);
	}

	public static String getGenericName(String extension) {
		return _dl.getGenericName(extension);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             com.liferay.document.library.util.DLURLHelper#getImagePreviewURL(
	 *             FileEntry, FileVersion, ThemeDisplay)}
	 */
	@Deprecated
	public static String getImagePreviewURL(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay)
		throws Exception {

		return _dl.getImagePreviewURL(fileEntry, fileVersion, themeDisplay);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             com.liferay.document.library.util.DLURLHelper#getImagePreviewURL(
	 *             FileEntry, FileVersion, ThemeDisplay, String, boolean,
	 *             boolean)}
	 */
	@Deprecated
	public static String getImagePreviewURL(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay, String queryString,
			boolean appendVersion, boolean absoluteURL)
		throws PortalException {

		return _dl.getImagePreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             com.liferay.document.library.util.DLURLHelper#getImagePreviewURL(
	 *             FileEntry, FileVersion, ThemeDisplay)}
	 */
	@Deprecated
	public static String getImagePreviewURL(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return _dl.getImagePreviewURL(fileEntry, themeDisplay);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             com.liferay.document.library.util.DLURLHelper#getPreviewURL(
	 *             FileEntry, FileVersion, ThemeDisplay, String)}
	 */
	@Deprecated
	public static String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString) {

		return _dl.getPreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             com.liferay.document.library.util.DLURLHelper#getPreviewURL(
	 *             FileEntry, FileVersion, ThemeDisplay, String, boolean,
	 *             boolean)}
	 */
	@Deprecated
	public static String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		return _dl.getPreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);
	}

	public static <T> OrderByComparator<T> getRepositoryModelOrderByComparator(
		String orderByCol, String orderByType) {

		return _dl.getRepositoryModelOrderByComparator(orderByCol, orderByType);
	}

	public static <T> OrderByComparator<T> getRepositoryModelOrderByComparator(
		String orderByCol, String orderByType, boolean orderByModel) {

		return _dl.getRepositoryModelOrderByComparator(
			orderByCol, orderByType, orderByModel);
	}

	public static String getSanitizedFileName(String title, String extension) {
		return _dl.getSanitizedFileName(title, extension);
	}

	public static String getTempFileId(long id, String version) {
		return _dl.getTempFileId(id, version);
	}

	public static String getTempFileId(
		long id, String version, String languageId) {

		return _dl.getTempFileId(id, version, languageId);
	}

	public static String getThumbnailStyle() {
		return _dl.getThumbnailStyle();
	}

	public static String getThumbnailStyle(boolean max, int margin) {
		return _dl.getThumbnailStyle(max, margin);
	}

	public static String getThumbnailStyle(
		boolean max, int margin, int height, int width) {

		return _dl.getThumbnailStyle(max, margin, height, width);
	}

	public static String getTitleWithExtension(FileEntry fileEntry) {
		return _dl.getTitleWithExtension(fileEntry);
	}

	public static String getTitleWithExtension(String title, String extension) {
		return _dl.getTitleWithExtension(title, extension);
	}

	public static String getUniqueFileName(
		long groupId, long folderId, String fileName,
		boolean ignoreDuplicateTitle) {

		return _dl.getUniqueFileName(
			groupId, folderId, fileName, ignoreDuplicateTitle);
	}

	public static String getUniqueTitle(
		long groupId, long folderId, String title) {

		return _dl.getUniqueTitle(groupId, folderId, title);
	}

	public static boolean hasWorkflowDefinitionLink(
		long companyId, long groupId, long folderId, long fileEntryTypeId) {

		return _dl.hasWorkflowDefinitionLink(
			companyId, groupId, folderId, fileEntryTypeId);
	}

	public static boolean isAutoGeneratedDLFileEntryTypeDDMStructureKey(
		String ddmStructureKey) {

		return _dl.isAutoGeneratedDLFileEntryTypeDDMStructureKey(
			ddmStructureKey);
	}

	public static boolean isOfficeExtension(String extension) {
		return _dl.isOfficeExtension(extension);
	}

	public static boolean isValidVersion(String version) {
		return _dl.isValidVersion(version);
	}

	public static void startWorkflowInstance(
			long userId, DLFileVersion dlFileVersion, String syncEventType,
			ServiceContext serviceContext)
		throws PortalException {

		_dl.startWorkflowInstance(
			userId, dlFileVersion, syncEventType, serviceContext);
	}

	public void setDL(DL dl) {
		_dl = dl;
	}

	private static DL _dl;

}