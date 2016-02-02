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

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletLayoutFinder;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelCreateDateComparator;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelModifiedDateComparator;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelReadCountComparator;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelSizeComparator;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelTitleComparator;
import com.liferay.portlet.documentlibrary.webdav.DLWebDAVUtil;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class DLImpl implements DL {

	@Override
	public int compareVersions(String version1, String version2) {
		int[] splitVersion1 = StringUtil.split(version1, StringPool.PERIOD, 0);
		int[] splitVersion2 = StringUtil.split(version2, StringPool.PERIOD, 0);

		if ((splitVersion1.length != 2) && (splitVersion2.length != 2)) {
			return 0;
		}
		else if (splitVersion1.length != 2) {
			return -1;
		}
		else if (splitVersion2.length != 2) {
			return 1;
		}

		if (splitVersion1[0] > splitVersion2[0]) {
			return 1;
		}
		else if (splitVersion1[0] < splitVersion2[0]) {
			return -1;
		}
		else if (splitVersion1[1] > splitVersion2[1]) {
			return 1;
		}
		else if (splitVersion1[1] < splitVersion2[1]) {
			return -1;
		}

		return 0;
	}

	@Override
	public String getAbsolutePath(PortletRequest portletRequest, long folderId)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return themeDisplay.translate("home");
		}

		Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

		List<Folder> folders = folder.getAncestors();

		Collections.reverse(folders);

		StringBundler sb = new StringBundler((folders.size() * 3) + 5);

		sb.append(themeDisplay.translate("home"));
		sb.append(StringPool.SPACE);

		for (Folder curFolder : folders) {
			sb.append(StringPool.RAQUO_CHAR);
			sb.append(StringPool.SPACE);
			sb.append(curFolder.getName());
		}

		sb.append(StringPool.RAQUO_CHAR);
		sb.append(StringPool.SPACE);
		sb.append(folder.getName());

		return sb.toString();
	}

	@Override
	public Set<String> getAllMediaGalleryMimeTypes() {
		return _allMediaGalleryMimeTypes;
	}

	@Override
	public String getDDMStructureKey(DLFileEntryType dlFileEntryType) {
		return getDDMStructureKey(dlFileEntryType.getUuid());
	}

	@Override
	public String getDDMStructureKey(String fileEntryTypeUuid) {
		return _STRUCTURE_KEY_PREFIX +
			StringUtil.toUpperCase(fileEntryTypeUuid);
	}

	@Override
	public String getDeprecatedDDMStructureKey(
		DLFileEntryType dlFileEntryType) {

		return getDeprecatedDDMStructureKey(
			dlFileEntryType.getFileEntryTypeId());
	}

	@Override
	public String getDeprecatedDDMStructureKey(long fileEntryTypeId) {
		return _STRUCTURE_KEY_PREFIX + fileEntryTypeId;
	}

	@Override
	public String getDividedPath(long id) {
		StringBundler sb = new StringBundler(16);

		long dividend = id;

		while ((dividend / _DIVISOR) != 0) {
			sb.append(StringPool.SLASH);
			sb.append(dividend % _DIVISOR);

			dividend = dividend / _DIVISOR;
		}

		sb.append(StringPool.SLASH);
		sb.append(id);

		return sb.toString();
	}

	@Override
	public String getDLFileEntryControlPanelLink(
		PortletRequest portletRequest, long fileEntryId) {

		String portletId = PortletProviderUtil.getPortletId(
			FileEntry.class.getName(), PortletProvider.Action.MANAGE);

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, portletId, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/view_file_entry");
		portletURL.setParameter("fileEntryId", String.valueOf(fileEntryId));

		return portletURL.toString();
	}

	@Override
	public String getDLFolderControlPanelLink(
		PortletRequest portletRequest, long folderId) {

		String portletId = PortletProviderUtil.getPortletId(
			Folder.class.getName(), PortletProvider.Action.MANAGE);

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, portletId, PortletRequest.RENDER_PHASE);

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/view");
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/view_folder");
		}

		portletURL.setParameter("folderId", String.valueOf(folderId));

		return portletURL.toString();
	}

	@Override
	public String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString) {

		return getDownloadURL(
			fileEntry, fileVersion, themeDisplay, queryString, true, true);
	}

	@Override
	public String getDownloadURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		String previewURL = getPreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString, appendVersion,
			absoluteURL);

		return HttpUtil.addParameter(previewURL, "download", true);
	}

	@Override
	public Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-document"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-document"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-document"));
		definitionTerms.put(
			"[$DOCUMENT_TITLE$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-document-title"));
		definitionTerms.put(
			"[$DOCUMENT_TYPE$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-document-type"));
		definitionTerms.put(
			"[$DOCUMENT_URL$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-document-url"));
		definitionTerms.put(
			"[$DOCUMENT_USER_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-email-address-of-the-user-who-added-the-document"));
		definitionTerms.put(
			"[$DOCUMENT_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-user-who-added-the-document"));
		definitionTerms.put(
			"[$FOLDER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-folder-in-which-the-document-has-been-added"));
		definitionTerms.put(
			"[$FROM_ADDRESS$]", HtmlUtil.escape(emailFromAddress));
		definitionTerms.put("[$FROM_NAME$]", HtmlUtil.escape(emailFromName));

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		definitionTerms.put(
			"[$PORTLET_NAME$]", HtmlUtil.escape(portletDisplay.getTitle()));

		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-document"));
		definitionTerms.put(
			"[$TO_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-address-of-the-email-recipient"));
		definitionTerms.put(
			"[$TO_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-name-of-the-email-recipient"));

		return definitionTerms;
	}

	@Override
	public Map<String, String> getEmailFromDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-document"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-document"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-document"));
		definitionTerms.put(
			"[$DOCUMENT_STATUS_BY_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-user-who-updated-the-document"));
		definitionTerms.put(
			"[$DOCUMENT_USER_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-email-address-of-the-user-who-added-the-document"));
		definitionTerms.put(
			"[$DOCUMENT_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-user-who-added-the-document"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		definitionTerms.put(
			"[$PORTLET_NAME$]", HtmlUtil.escape(portletDisplay.getTitle()));

		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-document"));

		return definitionTerms;
	}

	@Override
	public List<FileEntry> getFileEntries(Hits hits) {
		List<FileEntry> entries = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			long fileEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			try {
				FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
					fileEntryId);

				entries.add(fileEntry);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Documents and Media search index is stale and " +
							"contains file entry " + fileEntryId);
				}
			}
		}

		return entries;
	}

	@Override
	public String getFileEntryImage(
		FileEntry fileEntry, ThemeDisplay themeDisplay) {

		StringBundler sb = new StringBundler(5);

		sb.append("<img src=\"");
		sb.append(themeDisplay.getPathThemeImages());
		sb.append("/file_system/small/");
		sb.append(fileEntry.getIcon());
		sb.append(".png\" style=\"border-width: 0; text-align: left;\">");

		return sb.toString();
	}

	@Override
	public Set<Long> getFileEntryTypeSubscriptionClassPKs(long userId) {
		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getUserSubscriptions(
				userId, DLFileEntryType.class.getName());

		Set<Long> classPKs = new HashSet<>(subscriptions.size());

		for (Subscription subscription : subscriptions) {
			classPKs.add(subscription.getClassPK());
		}

		return classPKs;
	}

	@Override
	public String getFileIcon(String extension) {
		if (!_fileIcons.contains(extension)) {
			extension = _DEFAULT_FILE_ICON;
		}

		return extension;
	}

	@Override
	public String getFileIconCssClass(String extension) {
		return "documents-and-media";
	}

	@Override
	public String getGenericName(String extension) {
		String genericName = _genericNames.get(extension);

		if (genericName == null) {
			genericName = _DEFAULT_GENERIC_NAME;
		}

		return genericName;
	}

	@Override
	public String getImagePreviewURL(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay)
		throws Exception {

		String previewQueryString = null;

		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {
			if (ImageProcessorUtil.hasImages(fileVersion)) {
				previewQueryString = "&imagePreview=1";
			}
			else if (PDFProcessorUtil.hasImages(fileVersion)) {
				previewQueryString = "&previewFileIndex=1";
			}
			else if (VideoProcessorUtil.hasVideo(fileVersion)) {
				previewQueryString = "&videoThumbnail=1";
			}
		}

		return getImageSrc(
			fileEntry, fileVersion, themeDisplay, previewQueryString);
	}

	@Override
	public String getImagePreviewURL(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return getImagePreviewURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay);
	}

	@Override
	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString) {

		return getPreviewURL(
			fileEntry, fileVersion, themeDisplay, queryString, true, true);
	}

	@Override
	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL) {

		StringBundler sb = new StringBundler(15);

		if ((themeDisplay != null) && absoluteURL) {
			sb.append(themeDisplay.getPortalURL());
		}

		sb.append(PortalUtil.getPathContext());
		sb.append("/documents/");
		sb.append(fileEntry.getRepositoryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getFolderId());
		sb.append(StringPool.SLASH);

		String fileName = fileEntry.getFileName();

		if (fileEntry.isInTrash()) {
			fileName = TrashUtil.getOriginalTitle(fileEntry.getFileName());
		}

		sb.append(HttpUtil.encodeURL(HtmlUtil.unescape(fileName)));

		sb.append(StringPool.SLASH);
		sb.append(HttpUtil.encodeURL(fileEntry.getUuid()));

		if (appendVersion) {
			sb.append("?version=");
			sb.append(fileVersion.getVersion());
		}

		if (ImageProcessorUtil.isImageSupported(fileVersion)) {
			if (appendVersion) {
				sb.append("&t=");
			}
			else {
				sb.append("?t=");
			}

			Date modifiedDate = fileVersion.getModifiedDate();

			sb.append(modifiedDate.getTime());
		}

		sb.append(queryString);

		String previewURL = sb.toString();

		if ((themeDisplay != null) && themeDisplay.isAddSessionIdToURL()) {
			return PortalUtil.getURLWithSessionId(
				previewURL, themeDisplay.getSessionId());
		}

		return previewURL;
	}

	@Override
	public <T> OrderByComparator<T> getRepositoryModelOrderByComparator(
		String orderByCol, String orderByType) {

		return getRepositoryModelOrderByComparator(
			orderByCol, orderByType, false);
	}

	@Override
	public <T> OrderByComparator<T> getRepositoryModelOrderByComparator(
		String orderByCol, String orderByType, boolean orderByModel) {

		boolean orderByAsc = true;

		if (orderByType.equals("desc")) {
			orderByAsc = false;
		}

		OrderByComparator<T> orderByComparator = null;

		if (orderByCol.equals("creationDate")) {
			orderByComparator = new RepositoryModelCreateDateComparator<>(
				orderByAsc, orderByModel);
		}
		else if (orderByCol.equals("downloads")) {
			orderByComparator = new RepositoryModelReadCountComparator<>(
				orderByAsc, orderByModel);
		}
		else if (orderByCol.equals("modifiedDate")) {
			orderByComparator = new RepositoryModelModifiedDateComparator<>(
				orderByAsc, orderByModel);
		}
		else if (orderByCol.equals("size")) {
			orderByComparator = new RepositoryModelSizeComparator<>(
				orderByAsc, orderByModel);
		}
		else {
			orderByComparator = new RepositoryModelTitleComparator<>(
				orderByAsc, orderByModel);
		}

		return orderByComparator;
	}

	@Override
	public String getSanitizedFileName(String title, String extension) {
		String fileName = StringUtil.replace(
			title, StringPool.SLASH, StringPool.UNDERLINE);

		if (Validator.isNotNull(extension) &&
			!StringUtil.endsWith(fileName, StringPool.PERIOD + extension)) {

			fileName += StringPool.PERIOD + extension;
		}

		if (fileName.length() > 255) {
			int x = fileName.length() - 1;

			if (Validator.isNotNull(extension)) {
				x = fileName.lastIndexOf(StringPool.PERIOD);
			}

			int y = x - (fileName.length() - 255);

			fileName = fileName.substring(0, y) + fileName.substring(x);
		}

		return fileName;
	}

	@Override
	public String getTempFileId(long id, String version) {
		return getTempFileId(id, version, null);
	}

	@Override
	public String getTempFileId(long id, String version, String languageId) {
		if (Validator.isNull(languageId)) {
			return String.valueOf(id).concat(StringPool.PERIOD).concat(version);
		}

		StringBundler sb = new StringBundler(5);

		sb.append(id);
		sb.append(StringPool.PERIOD);
		sb.append(version);
		sb.append(StringPool.PERIOD);
		sb.append(languageId);

		return sb.toString();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getThumbnailSrc(FileEntry,
	 *             ThemeDisplay)}
	 */
	@Deprecated
	@Override
	public String getThumbnailSrc(
			FileEntry fileEntry, DLFileShortcut dlFileShortcut,
			ThemeDisplay themeDisplay)
		throws Exception {

		return getThumbnailSrc(
			fileEntry, fileEntry.getFileVersion(), themeDisplay);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getThumbnailSrc(FileEntry,
	 *             FileVersion, ThemeDisplay)}
	 */
	@Deprecated
	@Override
	public String getThumbnailSrc(
			FileEntry fileEntry, FileVersion fileVersion,
			DLFileShortcut dlFileShortcut, ThemeDisplay themeDisplay)
		throws Exception {

		return getThumbnailSrc(fileEntry, fileVersion, themeDisplay);
	}

	@Override
	public String getThumbnailSrc(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay)
		throws Exception {

		String thumbnailQueryString = null;

		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {
			if (ImageProcessorUtil.hasImages(fileVersion)) {
				thumbnailQueryString = "&imageThumbnail=1";
			}
			else if (PDFProcessorUtil.hasImages(fileVersion)) {
				thumbnailQueryString = "&documentThumbnail=1";
			}
			else if (VideoProcessorUtil.hasVideo(fileVersion)) {
				thumbnailQueryString = "&videoThumbnail=1";
			}
		}

		return getImageSrc(
			fileEntry, fileVersion, themeDisplay, thumbnailQueryString);
	}

	@Override
	public String getThumbnailSrc(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return getThumbnailSrc(
			fileEntry, fileEntry.getFileVersion(), themeDisplay);
	}

	@Override
	public String getThumbnailStyle() {
		return getThumbnailStyle(true, 0);
	}

	@Override
	public String getThumbnailStyle(boolean max, int margin) {
		return getThumbnailStyle(
			max, margin,
			PrefsPropsUtil.getInteger(
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT),
			PrefsPropsUtil.getInteger(
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH));
	}

	@Override
	public String getThumbnailStyle(
		boolean max, int margin, int height, int width) {

		StringBundler sb = new StringBundler(5);

		if (max) {
			sb.append("max-height: ");
		}
		else {
			sb.append("height: ");
		}

		height = height + (2 * margin);

		sb.append(height);

		if (max) {
			sb.append("px; max-width: ");
		}
		else {
			sb.append("px; width: ");
		}

		width = width + (2 * margin);

		sb.append(width);

		sb.append("px;");

		return sb.toString();
	}

	@Override
	public String getTitleWithExtension(FileEntry fileEntry) {
		String title = fileEntry.getTitle();
		String extension = fileEntry.getExtension();

		return getTitleWithExtension(title, extension);
	}

	@Override
	public String getTitleWithExtension(String title, String extension) {
		if (Validator.isNotNull(extension)) {
			String periodAndExtension = StringPool.PERIOD.concat(extension);

			if (!title.endsWith(periodAndExtension)) {
				title += periodAndExtension;
			}
		}

		return title;
	}

	@Override
	public String getUniqueFileName(
		long groupId, long folderId, String fileName) {

		String uniqueFileName = fileName;

		for (int i = 1;; i++) {
			try {
				DLAppLocalServiceUtil.getFileEntry(
					groupId, folderId, uniqueFileName);

				uniqueFileName = FileUtil.appendParentheticalSuffix(
					fileName, String.valueOf(i));
			}
			catch (Exception e) {
				break;
			}
		}

		return uniqueFileName;
	}

	@Override
	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry)
		throws PortalException {

		return getWebDavURL(themeDisplay, folder, fileEntry, false);
	}

	@Override
	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired)
		throws PortalException {

		return getWebDavURL(
			themeDisplay, folder, fileEntry, manualCheckInRequired, false);
	}

	@Override
	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired, boolean openDocumentUrl)
		throws PortalException {

		StringBundler webDavURL = new StringBundler(7);

		boolean secure = false;

		if (themeDisplay.isSecure() ||
			PropsValues.WEBDAV_SERVLET_HTTPS_REQUIRED) {

			secure = true;
		}

		String portalURL = PortalUtil.getPortalURL(
			themeDisplay.getServerName(), themeDisplay.getServerPort(), secure);

		webDavURL.append(portalURL);

		webDavURL.append(themeDisplay.getPathContext());
		webDavURL.append("/webdav");

		if (manualCheckInRequired) {
			webDavURL.append(MANUAL_CHECK_IN_REQUIRED_PATH);
		}

		Group group = null;

		if (fileEntry != null) {
			group = GroupLocalServiceUtil.getGroup(fileEntry.getGroupId());
		}
		else {
			group = themeDisplay.getScopeGroup();
		}

		webDavURL.append(group.getFriendlyURL());
		webDavURL.append("/document_library");

		StringBuilder sb = new StringBuilder();

		if ((folder != null) &&
			(folder.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			Folder curFolder = folder;

			while (true) {
				sb.insert(0, HttpUtil.encodeURL(curFolder.getName(), true));
				sb.insert(0, StringPool.SLASH);

				if (curFolder.getParentFolderId() ==
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

					break;
				}

				curFolder = DLAppLocalServiceUtil.getFolder(
					curFolder.getParentFolderId());
			}
		}

		if (fileEntry != null) {
			sb.append(StringPool.SLASH);
			sb.append(DLWebDAVUtil.escapeURLTitle(fileEntry.getTitle()));
		}

		webDavURL.append(sb.toString());

		return webDavURL.toString();
	}

	@Override
	public boolean hasWorkflowDefinitionLink(
			long companyId, long groupId, long folderId, long fileEntryTypeId)
		throws Exception {

		while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = DLFolderLocalServiceUtil.fetchDLFolder(
				folderId);

			if (dlFolder == null) {
				return false;
			}

			if (dlFolder.getRestrictionType() !=
					DLFolderConstants.RESTRICTION_TYPE_INHERIT) {

				break;
			}

			folderId = dlFolder.getParentFolderId();
		}

		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				companyId, groupId, DLFolderConstants.getClassName(), folderId,
				fileEntryTypeId) ||
			WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				companyId, groupId, DLFolderConstants.getClassName(), folderId,
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isAutoGeneratedDLFileEntryTypeDDMStructureKey(
		String ddmStructureKey) {

		if (ddmStructureKey.startsWith(_STRUCTURE_KEY_PREFIX)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isOfficeExtension(String extension) {
		return ArrayUtil.contains(_MICROSOFT_OFFICE_EXTENSIONS, extension);
	}

	@Override
	public boolean isSubscribedToFileEntryType(
		long companyId, long groupId, long userId, long fileEntryTypeId) {

		if (fileEntryTypeId ==
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

			fileEntryTypeId = groupId;
		}

		return SubscriptionLocalServiceUtil.isSubscribed(
			companyId, userId, DLFileEntryType.class.getName(),
			fileEntryTypeId);
	}

	@Override
	public boolean isSubscribedToFolder(
			long companyId, long groupId, long userId, long folderId)
		throws PortalException {

		return isSubscribedToFolder(companyId, groupId, userId, folderId, true);
	}

	@Override
	public boolean isSubscribedToFolder(
			long companyId, long groupId, long userId, long folderId,
			boolean recursive)
		throws PortalException {

		List<Long> ancestorFolderIds = new ArrayList<>();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

			ancestorFolderIds.add(folderId);

			if (recursive) {
				ancestorFolderIds.addAll(folder.getAncestorFolderIds());

				ancestorFolderIds.add(groupId);
			}
		}
		else {
			ancestorFolderIds.add(groupId);
		}

		long[] folderIdsArray = ArrayUtil.toLongArray(ancestorFolderIds);

		return SubscriptionLocalServiceUtil.isSubscribed(
			companyId, userId, DLFolder.class.getName(), folderIdsArray);
	}

	@Override
	public boolean isValidVersion(String version) {
		if (version.equals(DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {
			return true;
		}

		String[] versionParts = StringUtil.split(version, StringPool.PERIOD);

		if (versionParts.length != 2) {
			return false;
		}

		if (Validator.isNumber(versionParts[0]) &&
			Validator.isNumber(versionParts[1])) {

			return true;
		}

		return false;
	}

	@Override
	public void startWorkflowInstance(
			long userId, DLFileVersion dlFileVersion, String syncEventType,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		workflowContext.put(
			WorkflowConstants.CONTEXT_URL,
			getEntryURL(dlFileVersion, serviceContext));
		workflowContext.put("event", syncEventType);

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			dlFileVersion.getCompanyId(), dlFileVersion.getGroupId(), userId,
			DLFileEntryConstants.getClassName(),
			dlFileVersion.getFileVersionId(), dlFileVersion, serviceContext,
			workflowContext);
	}

	protected String getEntryURL(
			DLFileVersion dlFileVersion, ServiceContext serviceContext)
		throws PortalException {

		if (Validator.equals(
				serviceContext.getCommand(), Constants.ADD_WEBDAV) ||
			Validator.equals(
				serviceContext.getCommand(), Constants.UPDATE_WEBDAV)) {

			return serviceContext.getPortalURL() +
				serviceContext.getCurrentURL();
		}

		String entryURL = GetterUtil.getString(
			serviceContext.getAttribute("entryURL"));

		if (Validator.isNotNull(entryURL)) {
			return entryURL;
		}

		HttpServletRequest request = serviceContext.getRequest();
		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if ((request == null) || (themeDisplay == null)) {
			return StringPool.BLANK;
		}

		PortletURL portletURL = null;

		long plid = serviceContext.getPlid();
		long controlPanelPlid = PortalUtil.getControlPanelPlid(
			serviceContext.getCompanyId());
		String portletId = PortletProviderUtil.getPortletId(
			FileEntry.class.getName(), PortletProvider.Action.VIEW);

		for (PortletLayoutFinder portletLayoutFinder : _serviceTrackerList) {
			try {
				PortletLayoutFinder.Result result = portletLayoutFinder.find(
					themeDisplay, themeDisplay.getSiteGroupId());

				portletId = result.getPortletId();
				plid = result.getPlid();
			}
			catch (PortalException pe) {
			}
		}

		if ((plid == controlPanelPlid) ||
			(plid == LayoutConstants.DEFAULT_PLID)) {

			portletURL = PortalUtil.getControlPanelPortletURL(
				request, portletId, PortletRequest.RENDER_PHASE);
		}
		else {
			portletURL = PortletURLFactoryUtil.create(
				request, portletId, plid, PortletRequest.RENDER_PHASE);
		}

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/view_file_entry");
		portletURL.setParameter(
			"fileEntryId", String.valueOf(dlFileVersion.getFileEntryId()));

		return portletURL.toString();
	}

	protected String getImageSrc(
			FileEntry fileEntry, FileVersion fileVersion,
			ThemeDisplay themeDisplay, String queryString)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append(themeDisplay.getPathThemeImages());
		sb.append("/file_system/large/");
		sb.append(getGenericName(fileEntry.getExtension()));
		sb.append(".png");

		String thumbnailSrc = sb.toString();

		if (Validator.isNotNull(queryString)) {
			thumbnailSrc = getPreviewURL(
				fileEntry, fileVersion, themeDisplay, queryString, true, true);
		}

		return thumbnailSrc;
	}

	private static void _populateGenericNamesMap(String genericName) {
		String[] extensions = PropsUtil.getArray(
			PropsKeys.DL_FILE_GENERIC_EXTENSIONS, new Filter(genericName));

		for (String extension : extensions) {
			_genericNames.put(extension, genericName);
		}
	}

	private static final String _DEFAULT_FILE_ICON = "page";

	private static final String _DEFAULT_GENERIC_NAME = "default";

	private static final long _DIVISOR = 256;

	private static final String[] _MICROSOFT_OFFICE_EXTENSIONS = {
		"accda", "accdb", "accdc", "accde", "accdp", "accdr", "accdt", "accdu",
		"acl", "ade", "adp", "asd", "cnv", "crtx", "doc", "docm", "docx", "dot",
		"dotm", "dotx", "grv", "iaf", "laccdb", "maf", "mam", "maq", "mar",
		"mat", "mda", "mdb", "mde", "mdt", "mdw", "mpd", "mpp", "mpt", "oab",
		"obi", "oft", "olm", "one", "onepkg", "ops", "ost", "pa", "pip", "pot",
		"potm", "potx", "ppa", "ppam", "pps", "ppsm", "ppsx", "ppt", "pptm",
		"pptx", "prf", "pst", "pub", "puz", "rpmsg", "sldm", "sldx", "slk",
		"snp", "svd", "thmx", "vdx", "vrge08message", "vsd", "vss", "vst",
		"vsx", "vtx", "wbk", "wll", "xar", "xl", "xla", "xlam", "xlb", "xlc",
		"xll", "xlm", "xls", "xlsb", "xlsm", "xlsx", "xlt", "xltm", "xltx",
		"xlw", "xsf", "xsn"
	};

	private static final String _STRUCTURE_KEY_PREFIX = "AUTO_";

	private static final Log _log = LogFactoryUtil.getLog(DLImpl.class);

	private static final Set<String> _allMediaGalleryMimeTypes =
		new TreeSet<>();
	private static final Set<String> _fileIcons = new HashSet<>();
	private static final Map<String, String> _genericNames = new HashMap<>();
	private static final ServiceTrackerList<PortletLayoutFinder>
		_serviceTrackerList = ServiceTrackerCollections.list(
			PortletLayoutFinder.class,
			"(model.class.name=" + FileEntry.class.getName() + ")");

	static {
		_allMediaGalleryMimeTypes.addAll(
			SetUtil.fromArray(
				PropsUtil.getArray(
					PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES)));
		_allMediaGalleryMimeTypes.addAll(
			SetUtil.fromArray(
				PropsUtil.getArray(
					PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES)));
		_allMediaGalleryMimeTypes.addAll(
			SetUtil.fromArray(
				PropsUtil.getArray(
					PropsKeys.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES)));

		String[] fileIcons = null;

		try {
			fileIcons = PropsUtil.getArray(PropsKeys.DL_FILE_ICONS);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			fileIcons = new String[] {StringPool.BLANK};
		}

		for (int i = 0; i < fileIcons.length; i++) {

			// Only process non wildcard extensions

			if (!StringPool.STAR.equals(fileIcons[i])) {

				// Strip starting period

				String extension = fileIcons[i];

				if (extension.length() > 0) {
					extension = extension.substring(1);
				}

				_fileIcons.add(extension);
			}
		}

		String[] genericNames = PropsUtil.getArray(
			PropsKeys.DL_FILE_GENERIC_NAMES);

		for (String genericName : genericNames) {
			_populateGenericNamesMap(genericName);
		}
	}

}