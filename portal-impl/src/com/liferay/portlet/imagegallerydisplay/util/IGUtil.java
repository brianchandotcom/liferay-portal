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

package com.liferay.portlet.imagegallerydisplay.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class IGUtil {

	public static void addPortletBreadcrumbEntries(
			Folder folder, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		String strutsAction = ParamUtil.getString(request, "struts_action");

		PortletURL portletURL = renderResponse.createRenderURL();

		if (strutsAction.equals("/image_gallery_display/select_folder")) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			portletURL.setParameter("struts_action", strutsAction);
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			PortalUtil.addPortletBreadcrumbEntry(
				request, themeDisplay.translate("home"), portletURL.toString());
		}
		else {
			portletURL.setParameter(
				"struts_action", "/image_gallery_display/view");
		}

		List<Long> ancestorFolderIds = folder.getAncestorFolderIds();

		Collections.reverse(ancestorFolderIds);

		String portletId = PortalUtil.getPortletId(request);

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletPreferences(
				request, portletId);

		long rootFolderId = PrefsParamUtil.getLong(
			preferences, request, "rootFolderId",
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		List<Long> filteredFolderAncestorIds = removeRootFolderAncestors(
			rootFolderId, ancestorFolderIds);

		for (Long ancestorFolderId : filteredFolderAncestorIds) {
			portletURL.setParameter(
				"folderId", String.valueOf(ancestorFolderId));

			Folder ancestorFolder = DLAppLocalServiceUtil.getFolder(
				ancestorFolderId);

			PortalUtil.addPortletBreadcrumbEntry(
				request, ancestorFolder.getName(), portletURL.toString());
		}

		portletURL.setParameter(
			"folderId", String.valueOf(folder.getFolderId()));

		if (strutsAction.equals("/journal/select_image_gallery")) {
			portletURL.setParameter(
				"groupId", String.valueOf(folder.getGroupId()));
		}

		PortalUtil.addPortletBreadcrumbEntry(
			request, folder.getName(), portletURL.toString());
	}

	public static void addPortletBreadcrumbEntries(
			long folderId, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

		addPortletBreadcrumbEntries(folder, request, renderResponse);
	}

	protected static List<Long> removeRootFolderAncestors(
			Long rootFolderId, List<Long> ancestorFolderIds)
		throws PortalException, SystemException {

		List<Long> avoidBreadcrumbFolderIds = new UniqueList<Long>();

		if (Validator.isNotNull(rootFolderId) &&
			(rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) ) {

			Folder rootFolder = DLAppLocalServiceUtil.getFolder(rootFolderId);

			avoidBreadcrumbFolderIds = rootFolder.getAncestorFolderIds();

			avoidBreadcrumbFolderIds.add(rootFolder.getFolderId());
		}

		return ListUtil.remove(ancestorFolderIds, avoidBreadcrumbFolderIds);
	}

}