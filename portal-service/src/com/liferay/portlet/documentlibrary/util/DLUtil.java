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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelCreateDateComparator;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelModifiedDateComparator;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelNameComparator;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelReadCountComparator;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelSizeComparator;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class DLUtil {

	public static void addPortletBreadcrumbEntries(
			DLFileShortcut dlFileShortcut, HttpServletRequest request,
			RenderResponse renderResponse, boolean showGlobally)
		throws Exception {

		Folder folder = dlFileShortcut.getFolder();

		if (folder.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			addPortletBreadcrumbEntries(
				folder, request, renderResponse, showGlobally);
		}

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter(
			"struts_action", "/document_library/view_file_entry");
		portletURL.setParameter(
			"fileEntryId",
			String.valueOf(dlFileShortcut.getToFileEntryId()));

		PortalUtil.addPortletBreadcrumbEntry(
			request, dlFileShortcut.getToTitle(), portletURL.toString());
	}

	public static void addPortletBreadcrumbEntries(
			FileEntry fileEntry, HttpServletRequest request,
			RenderResponse renderResponse, boolean showGlobally)
		throws Exception {

		Folder folder = fileEntry.getFolder();

		if (folder.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			addPortletBreadcrumbEntries(
				folder, request, renderResponse, showGlobally);
		}

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter(
			"struts_action", "/document_library/view_file_entry");
		portletURL.setParameter(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

		PortalUtil.addPortletBreadcrumbEntry(
			request, fileEntry.getTitle(), portletURL.toString());
	}

	public static void addPortletBreadcrumbEntries(
			Folder folder, HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse, boolean showGlobally)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL =
			(PortletURL)liferayPortletResponse.createResourceURL();

		portletURL.setParameter("struts_action", "/document_library/view");
		portletURL.setParameter("showSiblings", Boolean.TRUE.toString());
		portletURL.setParameter("viewAddButton", Boolean.TRUE.toString());
		portletURL.setParameter("viewBreadcrumb", Boolean.TRUE.toString());
		portletURL.setParameter(
			"viewDisplayStyleButttons", Boolean.TRUE.toString());
		portletURL.setParameter("viewEntries", Boolean.TRUE.toString());
		portletURL.setParameter(
			"viewFileEntrySearch", Boolean.TRUE.toString());
		portletURL.setParameter("viewFolders", Boolean.TRUE.toString());
		portletURL.setParameter("viewSortButton", Boolean.TRUE.toString());

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("folder-id", _getDefaultFolderId(request));
		data.put("refresh-folders", Boolean.TRUE.toString());

		PortalUtil.addPortletBreadcrumbEntry(
			request, themeDisplay.translate("documents-home"),
			portletURL.toString(), data);

		addPortletBreadcrumbEntries(folder, request, portletURL, showGlobally);
	}

	public static void addPortletBreadcrumbEntries(
			Folder folder, HttpServletRequest request,
			PortletURL portletURL, boolean showGlobally)
		throws Exception {

		long defaultFolderId = _getDefaultFolderId(request);

		List<Folder> ancestorFolders = Collections.emptyList();

		if ((folder != null) && (folder.getFolderId() != defaultFolderId)) {
			ancestorFolders = folder.getAncestors();

			int indexOfRootFolder = -1;

			for (int i = 0; i < ancestorFolders.size(); i++) {
				Folder ancestorFolder = ancestorFolders.get(i);

				if (defaultFolderId == ancestorFolder.getFolderId()) {
					indexOfRootFolder = i;
				}
			}

			if (indexOfRootFolder > -1) {
				ancestorFolders = ancestorFolders.subList(0, indexOfRootFolder);
			}
		}

		Collections.reverse(ancestorFolders);

		for (Folder ancestorFolder : ancestorFolders) {
			portletURL.setParameter(
				"folderId", String.valueOf(ancestorFolder.getFolderId()));

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("folder-id", ancestorFolder.getFolderId());
			data.put("refresh-folders", Boolean.TRUE.toString());
			data.put("show-globally", String.valueOf(showGlobally));

			PortalUtil.addPortletBreadcrumbEntry(
				request, ancestorFolder.getName(), portletURL.toString(), data);
		}

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (folder != null) {
			folderId = folder.getFolderId();
		}

		portletURL.setParameter("folderId", String.valueOf(folderId));

		if ((folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId != defaultFolderId)) {

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("folder-id", folderId);
			data.put("refresh-folders", Boolean.TRUE.toString());
			data.put("show-globally", String.valueOf(showGlobally));

			PortalUtil.addPortletBreadcrumbEntry(
				request, folder.getName(), portletURL.toString(), data);
		}
	}

	public static void addPortletBreadcrumbEntries(
			Folder folder, HttpServletRequest request,
			RenderResponse renderResponse, boolean showGlobally)
		throws Exception {

		String strutsAction = ParamUtil.getString(
			request, "struts_action");

		long groupId = ParamUtil.getLong(request, "groupId");

		PortletURL portletURL = renderResponse.createRenderURL();

		if (strutsAction.equals("/journal/select_document_library") ||
			strutsAction.equals("/document_library/select_file_entry") ||
			strutsAction.equals("/document_library/select_folder") ||
			strutsAction.equals("/document_library_display/select_folder") ||
			strutsAction.equals("/image_gallery_display/select_folder")) {

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			portletURL.setWindowState(LiferayWindowState.POP_UP);

			portletURL.setParameter("struts_action", strutsAction);
			portletURL.setParameter("groupId", String.valueOf(groupId));

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("folder-id", _getDefaultFolderId(request));
			data.put("refresh-folders", Boolean.TRUE.toString());

			if (strutsAction.equals("/image_gallery_display/select_folder")) {
				PortalUtil.addPortletBreadcrumbEntry(
					request, themeDisplay.translate("images-home"),
					portletURL.toString(), data);
			}
			else {
				PortalUtil.addPortletBreadcrumbEntry(
					request, themeDisplay.translate("documents-home"),
					portletURL.toString(), data);
			}
		}
		else {
			portletURL.setParameter("struts_action", "/document_library/view");
		}

		addPortletBreadcrumbEntries(folder, request, portletURL, showGlobally);
	}

	public static void addPortletBreadcrumbEntries(
			long folderId, HttpServletRequest request,
			RenderResponse renderResponse, boolean showGlobally)
		throws Exception {

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

			if (folder.getFolderId() !=
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				addPortletBreadcrumbEntries(
					folder, request, renderResponse, showGlobally);
			}
		}
	}

	public static int compareVersions(String version1, String version2) {
		int[] splitVersion1 = StringUtil.split(version1, StringPool.PERIOD, 0);
		int[] splitVersion2 = StringUtil.split(version2, StringPool.PERIOD, 0);

		if ((splitVersion1.length != 2) && (splitVersion2.length != 2)) {
			return 0;
		}
		else if ((splitVersion1.length != 2)) {
			return -1;
		}
		else if ((splitVersion2.length != 2)) {
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

	public static String getDividedPath(long id) {
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

	public static String getFileIcon(String extension) {
		return _instance._getFileIcon(extension);
	}

	public static String getGenericName(String extension) {
		return _instance._getGenericName(extension);
	}

	public static long[] getGroupIds(long groupId)
		throws PortalException, SystemException {

		Group scopeGroup = GroupLocalServiceUtil.getGroup(groupId);

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			scopeGroup.getCompanyId());

		if (scopeGroup.isLayout()) {
			return new long[] {
				scopeGroup.getParentGroupId(), companyGroup.getGroupId()
			};
		}
		else {
			return new long[] {groupId, companyGroup.getGroupId()};
		}
	}

	public static long[] getGroupIds(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getGroupIds(themeDisplay.getScopeGroupId());
	}

	public static OrderByComparator getRepositoryModelOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = true;

		if (orderByType.equals("desc")) {
			orderByAsc = false;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("creationDate")) {
			orderByComparator = new RepositoryModelCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("downloads")) {
			orderByComparator = new RepositoryModelReadCountComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("modifiedDate")) {
			orderByComparator = new RepositoryModelModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("size")) {
			orderByComparator = new RepositoryModelSizeComparator(orderByAsc);
		}
		else {
			orderByComparator = new RepositoryModelNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static String getTempFileId(long id, String version) {
		return getTempFileId(id, version, null);
	}

	public static String getTempFileId(
		long id, String version, String languageId) {

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

	private static long _getDefaultFolderId(HttpServletRequest request)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getPortletPreferences(
				request, PortalUtil.getPortletId(request));

		return GetterUtil.getLong(
			portletPreferences.getValue(
				"rootFolderId",
				String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)));
	}

	private DLUtil() {
		String[] fileIcons = null;

		try {
			fileIcons = PrefsPropsUtil.getStringArray(
				PropsKeys.DL_FILE_ICONS, StringPool.COMMA);
		}
		catch (Exception e) {
			_log.error(e, e);

			fileIcons = new String[] {StringPool.BLANK};
		}

		for (int i = 0; i < fileIcons.length; i++) {

			// Only process non wildcard extensions

			if (!StringPool.STAR.equals(fileIcons[i])) {

				// Strip starting period

				String extension = fileIcons[i];
				extension = extension.substring(1, extension.length());

				_fileIcons.add(extension);
			}
		}

		String[] genericNames = PropsUtil.getArray(
			PropsKeys.DL_FILE_GENERIC_NAMES);

		for (String genericName : genericNames) {
			_populateGenericNamesMap(genericName);
		}
	}

	private String _getFileIcon(String extension) {
		if (!_fileIcons.contains(extension)) {
			extension = _DEFAULT_FILE_ICON;
		}

		return extension;
	}

	private String _getGenericName(String extension) {
		String genericName = _genericNames.get(extension);

		if (genericName == null) {
			genericName = _DEFAULT_GENERIC_NAME;
		}

		return genericName;
	}

	private void _populateGenericNamesMap(String genericName) {
		String[] extensions = PropsUtil.getArray(
			PropsKeys.DL_FILE_GENERIC_EXTENSIONS, new Filter(genericName));

		for (String extension : extensions) {
			_genericNames.put(extension, genericName);
		}
	}

	private static final String _DEFAULT_FILE_ICON = "page";

	private static final String _DEFAULT_GENERIC_NAME = "default";

	private static final long _DIVISOR = 256;

	private static Log _log = LogFactoryUtil.getLog(DLUtil.class);

	private static DLUtil _instance = new DLUtil();

	private Set<String> _fileIcons = new HashSet<String>();
	private Map<String, String> _genericNames = new HashMap<String, String>();

}