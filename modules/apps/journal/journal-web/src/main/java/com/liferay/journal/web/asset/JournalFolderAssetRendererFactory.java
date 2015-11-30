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

package com.liferay.journal.web.asset;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.service.permission.JournalFolderPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + JournalPortletKeys.JOURNAL},
	service = AssetRendererFactory.class
)
public class JournalFolderAssetRendererFactory
	extends BaseAssetRendererFactory<JournalFolder> {

	public static final String TYPE = "content_folder";

	public JournalFolderAssetRendererFactory() {
		setCategorizable(false);
		setClassName(JournalFolder.class.getName());
		setPortletId(JournalPortletKeys.JOURNAL);
		setSearchable(true);
	}

	@Override
	public AssetRenderer<JournalFolder> getAssetRenderer(long classPK, int type)
		throws PortalException {

		JournalFolder folder = _journalFolderLocalService.getFolder(classPK);

		JournalFolderAssetRenderer journalFolderAssetRenderer =
			new JournalFolderAssetRenderer(folder);

		journalFolderAssetRenderer.setAssetRendererType(type);
		journalFolderAssetRenderer.setServletContext(_servletContext);

		return journalFolderAssetRenderer;
	}

	@Override
	public String getClassName() {
		return JournalFolder.class.getName();
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
				JournalPortletKeys.JOURNAL, PortletRequest.RENDER_PHASE);

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

		JournalFolder folder = _journalFolderLocalService.getFolder(classPK);

		return JournalFolderPermission.contains(
			permissionChecker, folder, actionId);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/folder.png";
	}

	@Reference(unbind = "-")
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	private volatile JournalFolderLocalService _journalFolderLocalService;
	private volatile ServletContext _servletContext;

}