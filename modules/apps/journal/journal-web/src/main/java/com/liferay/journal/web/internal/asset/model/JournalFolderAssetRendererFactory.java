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

package com.liferay.journal.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.trash.TrashHelper;

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
	property = "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
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

		JournalFolderAssetRenderer journalFolderAssetRenderer =
			new JournalFolderAssetRenderer(
				_journalFolderLocalService.getFolder(classPK), _trashHelper);

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
		return "folder";
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

		return _journalFolderModelResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference(unbind = "-")
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	private JournalFolderLocalService _journalFolderLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalFolder)"
	)
	private ModelResourcePermission<JournalFolder>
		_journalFolderModelResourcePermission;

	private ServletContext _servletContext;

	@Reference
	private TrashHelper _trashHelper;

}