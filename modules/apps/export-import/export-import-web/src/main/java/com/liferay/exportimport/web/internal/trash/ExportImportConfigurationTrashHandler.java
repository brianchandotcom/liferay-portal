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

package com.liferay.exportimport.web.internal.trash;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Levente Hudák
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.exportimport.kernel.model.ExportImportConfiguration",
	service = TrashHandler.class
)
public class ExportImportConfigurationTrashHandler extends BaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK) throws PortalException {
		_exportImportConfigurationLocalService.deleteExportImportConfiguration(
			classPK);
	}

	@Override
	public String getClassName() {
		return ExportImportConfiguration.class.getName();
	}

	@Override
	public String getRestoreMessage(
		PortletRequest portletRequest, long classPK) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.translate("export-import-template");
	}

	@Override
	public TrashedModel getTrashedModel(long classPK) {
		return _exportImportConfigurationLocalService.
			fetchExportImportConfiguration(classPK);
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK) throws PortalException {
		ExportImportConfigurationTrashRenderer
			exportImportConfigurationTrashRenderer =
				new ExportImportConfigurationTrashRenderer(
					_exportImportConfigurationLocalService.
						getExportImportConfiguration(classPK));

		exportImportConfigurationTrashRenderer.setServletContext(
			servletContext);

		return exportImportConfigurationTrashRenderer;
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		_exportImportConfigurationLocalService.
			restoreExportImportConfigurationFromTrash(userId, classPK);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.exportimport.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.getExportImportConfiguration(
				classPK);

		Group group = _groupLocalService.getGroup(
			exportImportConfiguration.getGroupId());

		return GroupPermissionUtil.contains(permissionChecker, group, actionId);
	}

	@Reference(unbind = "-")
	protected void setExportImportConfigurationLocalService(
		ExportImportConfigurationLocalService
			exportImportConfigurationLocalService) {

		_exportImportConfigurationLocalService =
			exportImportConfigurationLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	protected ServletContext servletContext;

	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;
	private GroupLocalService _groupLocalService;

}