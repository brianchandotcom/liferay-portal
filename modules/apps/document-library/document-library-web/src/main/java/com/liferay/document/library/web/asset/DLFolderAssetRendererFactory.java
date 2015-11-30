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

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY},
	service = AssetRendererFactory.class
)
public class DLFolderAssetRendererFactory
	extends BaseAssetRendererFactory<Folder> {

	public static final String TYPE = "document_folder";

	public DLFolderAssetRendererFactory() {
		setCategorizable(false);
		setPortletId(DLPortletKeys.DOCUMENT_LIBRARY);
		setSearchable(true);
	}

	@Override
	public AssetRenderer<Folder> getAssetRenderer(long classPK, int type)
		throws PortalException {

		Folder folder = _dlAppLocalService.getFolder(classPK);

		DLFolderAssetRenderer dlFolderAssetRenderer = new DLFolderAssetRenderer(
			folder);

		dlFolderAssetRenderer.setAssetRendererType(type);

		return dlFolderAssetRenderer;
	}

	@Override
	public String getClassName() {
		return DLFolder.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "icon-folder-close";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLView(
		LiferayPortletResponse liferayPortletResponse,
		WindowState windowState) {

		LiferayPortletURL liferayPortletURL =
			liferayPortletResponse.createLiferayPortletURL(
				DLPortletKeys.DOCUMENT_LIBRARY, PortletRequest.RENDER_PHASE);

		try {
			liferayPortletURL.setWindowState(windowState);
		}
		catch (WindowStateException wse) {
		}

		return liferayPortletURL;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		Folder folder = _dlAppLocalService.getFolder(classPK);

		return DLFolderPermission.contains(permissionChecker, folder, actionId);
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/folder.png";
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	private volatile DLAppLocalService _dlAppLocalService;

}