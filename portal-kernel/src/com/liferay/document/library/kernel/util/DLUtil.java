/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
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
		return getDL().compareVersions(version1, version2);
	}

	public static String getAbsolutePath(
			PortletRequest portletRequest, long rootFolderId, long folderId)
		throws PortalException {

		return getDL().getAbsolutePath(portletRequest, rootFolderId, folderId);
	}

	public static Set<String> getAllMediaGalleryMimeTypes() {
		return getDL().getAllMediaGalleryMimeTypes();
	}

	public static String getDDMStructureKey(DLFileEntryType dlFileEntryType) {
		return getDL().getDDMStructureKey(dlFileEntryType);
	}

	public static String getDDMStructureKey(String fileEntryTypeUuid) {
		return getDL().getDDMStructureKey(fileEntryTypeUuid);
	}

	public static String getDeprecatedDDMStructureKey(
		DLFileEntryType dlFileEntryType) {

		return getDL().getDeprecatedDDMStructureKey(dlFileEntryType);
	}

	public static String getDeprecatedDDMStructureKey(long fileEntryTypeId) {
		return getDL().getDeprecatedDDMStructureKey(fileEntryTypeId);
	}

	public static String getDividedPath(long id) {
		return getDL().getDividedPath(id);
	}

	public static DL getDL() {
		return _dlSnapshot.get();
	}

	public static Map<String, String> getEmailDefinitionTerms(
		RenderRequest renderRequest, String emailFromAddress,
		String emailFromName) {

		return getDL().getEmailDefinitionTerms(
			renderRequest, emailFromAddress, emailFromName);
	}

	public static Map<String, String> getEmailFromDefinitionTerms(
		RenderRequest renderRequest, String emailFromAddress,
		String emailFromName) {

		return getDL().getEmailFromDefinitionTerms(
			renderRequest, emailFromAddress, emailFromName);
	}

	public static List<FileEntry> getFileEntries(Hits hits) {
		return getDL().getFileEntries(hits);
	}

	public static String getFileEntryImage(
		FileEntry fileEntry, ThemeDisplay themeDisplay) {

		return getDL().getFileEntryImage(fileEntry, themeDisplay);
	}

	public static String getFileIcon(String extension) {
		return getDL().getFileIcon(extension);
	}

	public static String getFileIconCssClass(String extension) {
		return getDL().getFileIconCssClass(extension);
	}

	public static String getGenericName(String extension) {
		return getDL().getGenericName(extension);
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

		return getDL().getPreviewURL(
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

		return getDL().getPreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);
	}

	public static <T> OrderByComparator<T> getRepositoryModelOrderByComparator(
		String orderByCol, String orderByType) {

		return getDL().getRepositoryModelOrderByComparator(
			orderByCol, orderByType);
	}

	public static <T> OrderByComparator<T> getRepositoryModelOrderByComparator(
		String orderByCol, String orderByType, boolean orderByModel) {

		return getDL().getRepositoryModelOrderByComparator(
			orderByCol, orderByType, orderByModel);
	}

	public static String getSanitizedFileName(String title, String extension) {
		return getDL().getSanitizedFileName(title, extension);
	}

	public static String getTempFileId(long id, String version) {
		return getDL().getTempFileId(id, version);
	}

	public static String getTempFileId(
		long id, String version, String languageId) {

		return getDL().getTempFileId(id, version, languageId);
	}

	public static String getThumbnailStyle() {
		return getDL().getThumbnailStyle();
	}

	public static String getThumbnailStyle(boolean max, int margin) {
		return getDL().getThumbnailStyle(max, margin);
	}

	public static String getThumbnailStyle(
		boolean max, int margin, int height, int width) {

		return getDL().getThumbnailStyle(max, margin, height, width);
	}

	public static String getTitleWithExtension(FileEntry fileEntry) {
		return getDL().getTitleWithExtension(fileEntry);
	}

	public static String getTitleWithExtension(String title, String extension) {
		return getDL().getTitleWithExtension(title, extension);
	}

	public static String getUniqueFileName(
		long groupId, long folderId, String fileName,
		boolean ignoreDuplicateTitle) {

		return getDL().getUniqueFileName(
			groupId, folderId, fileName, ignoreDuplicateTitle);
	}

	public static String getUniqueTitle(
		long groupId, long folderId, String title) {

		return getDL().getUniqueTitle(groupId, folderId, title);
	}

	public static boolean hasWorkflowDefinitionLink(
		long companyId, long groupId, long folderId, long fileEntryTypeId) {

		return getDL().hasWorkflowDefinitionLink(
			companyId, groupId, folderId, fileEntryTypeId);
	}

	public static boolean isAutoGeneratedDLFileEntryTypeDDMStructureKey(
		String ddmStructureKey) {

		return getDL().isAutoGeneratedDLFileEntryTypeDDMStructureKey(
			ddmStructureKey);
	}

	public static boolean isOfficeExtension(String extension) {
		return getDL().isOfficeExtension(extension);
	}

	public static boolean isValidVersion(String version) {
		return getDL().isValidVersion(version);
	}

	public static void startWorkflowInstance(
			long userId, DLFileVersion dlFileVersion, String syncEventType,
			ServiceContext serviceContext)
		throws PortalException {

		getDL().startWorkflowInstance(
			userId, dlFileVersion, syncEventType, serviceContext);
	}

	private static final Snapshot<DL> _dlSnapshot = new Snapshot<>(
		DLUtil.class, DL.class);

}