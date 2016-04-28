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

package com.liferay.document.library.web.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Chow
 */
public class DLFolderAssetRenderer
	extends BaseJSPAssetRenderer<Folder> implements TrashRenderer {

	public static final String TYPE = "folder";

	public DLFolderAssetRenderer(Folder folder) {
		_folder = folder;
	}

	@Override
	public Folder getAssetObject() {
		return _folder;
	}

	@Override
	public String getClassName() {
		return DLFolder.class.getName();
	}

	@Override
	public long getClassPK() {
		return _folder.getPrimaryKey();
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public Date getDisplayDate() {
		return _folder.getModifiedDate();
	}

	@Override
	public long getGroupId() {
		return _folder.getGroupId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/document_library/asset/folder_" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory<Folder> assetRendererFactory =
			getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return _folder.getDescription();
	}

	@Override
	public String getTitle(Locale locale) {
		return TrashUtil.getOriginalTitle(_folder.getName());
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(_folder.getGroupId());

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, group, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			0, 0, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_folder");
		portletURL.setParameter(
			"folderId", String.valueOf(_folder.getFolderId()));

		return portletURL;
	}

	@Override
	public String getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		AssetRendererFactory<Folder> assetRendererFactory =
			getAssetRendererFactory();

		PortletURL portletURL = assetRendererFactory.getURLView(
			liferayPortletResponse, windowState);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/view_folder");
		portletURL.setParameter(
			"folderId", String.valueOf(_folder.getFolderId()));
		portletURL.setWindowState(windowState);

		return portletURL.toString();
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		return getURLViewInContext(
			liferayPortletRequest, noSuchEntryRedirect,
			"/document_library/find_folder", "folderId", _folder.getFolderId());
	}

	@Override
	public long getUserId() {
		return _folder.getUserId();
	}

	@Override
	public String getUserName() {
		return _folder.getUserName();
	}

	@Override
	public String getUuid() {
		return _folder.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return DLFolderPermission.contains(
			permissionChecker, _folder, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return DLFolderPermission.contains(
			permissionChecker, _folder, ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER, _folder);

		return super.include(request, response, template);
	}

	@Override
	public boolean isDisplayable() {
		if (_folder.isMountPoint()) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFolderAssetRenderer.class);

	private final Folder _folder;

}