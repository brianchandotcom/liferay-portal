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

package com.liferay.portlet.documentlibrary.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Sergio González
 */
public class DLFileEntryAssetRenderer extends BaseAssetRenderer {

	public DLFileEntryAssetRenderer(
		FileEntry fileEntry, FileVersion fileVersion) {

		_fileEntry = fileEntry;
		_fileVersion = fileVersion;
	}

	public long getClassPK() {
		if (!_fileVersion.isApproved() &&
			(!_fileVersion.getVersion().equals(
				DLFileEntryConstants.VERSION_DEFAULT))) {

			return _fileVersion.getFileVersionId();
		}
		else {
			return _fileEntry.getFileEntryId();
		}
	}

	@Override
	public String getDiscussionPath() {
		if (PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED) {
			return "edit_file_entry_discussion";
		}
		else {
			return null;
		}
	}

	public long getGroupId() {
		return _fileEntry.getGroupId();
	}

	public String getSummary(Locale locale) {
		return HtmlUtil.stripHtml(_fileEntry.getDescription());
	}

	public String getTitle(Locale locale) {
		return _fileVersion.getTitle();
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest),
			PortletKeys.DOCUMENT_LIBRARY, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"struts_action", "/document_library/edit_file_entry");
		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		return portletURL;
	}

	@Override
	public PortletURL getURLExport(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter("struts_action", "/asset_publisher/get_file");
		portletURL.setParameter(
			"groupId", String.valueOf(_fileEntry.getRepositoryId()));
		portletURL.setParameter(
			"folderId", String.valueOf(_fileEntry.getFolderId()));
		portletURL.setParameter("title", String.valueOf(_fileEntry.getTitle()));

		return portletURL;
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathMain() +
			"/document_library/find_file_entry?p_l_id=" +
				themeDisplay.getPlid() + "&fileEntryId=" +
					_fileEntry.getFileEntryId();
	}

	public long getUserId() {
		return _fileEntry.getUserId();
	}

	public String getUuid() {
		return _fileEntry.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return DLFileEntryPermission.contains(
			permissionChecker, _fileEntry.getFileEntryId(), ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return DLFileEntryPermission.contains(
			permissionChecker, _fileEntry.getFileEntryId(), ActionKeys.VIEW);
	}

	@Override
	public boolean isConvertible() {
		return true;
	}

	@Override
	public boolean isPrintable() {
		return false;
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY, _fileEntry);
			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, _fileVersion);

			return "/html/portlet/document_library/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/file_system/small/" +
			_fileEntry.getIcon() + ".png";
	}

	private FileEntry _fileEntry;
	private FileVersion _fileVersion;

}