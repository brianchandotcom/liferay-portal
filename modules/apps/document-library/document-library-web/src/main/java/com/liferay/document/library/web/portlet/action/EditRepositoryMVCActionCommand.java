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

package com.liferay.document.library.web.portlet.action;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.NoSuchRepositoryException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.RepositoryService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.DuplicateRepositoryNameException;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.RepositoryNameException;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_repository"
	},
	service = MVCActionCommand.class
)
public class EditRepositoryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateRepository(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				unmountRepository(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchRepositoryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcPath", "/document_library/error.jsp");
			}
			else if (e instanceof DuplicateFolderNameException ||
					 e instanceof DuplicateRepositoryNameException ||
					 e instanceof FolderNameException ||
					 e instanceof InvalidRepositoryException ||
					 e instanceof RepositoryNameException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setRepositoryService(RepositoryService repositoryService) {
		_repositoryService = repositoryService;
	}

	protected void unmountRepository(ActionRequest actionRequest)
		throws Exception {

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");

		_repositoryService.deleteRepository(repositoryId);
	}

	protected void updateRepository(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");

		String className = ParamUtil.getString(actionRequest, "className");

		long classNameId = PortalUtil.getClassNameId(className);

		long folderId = ParamUtil.getLong(actionRequest, "folderId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		UnicodeProperties typeSettingsProperties =
			PropertiesParamUtil.getProperties(actionRequest, "settings--");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFolder.class.getName(), actionRequest);

		if (repositoryId <= 0) {

			// Add repository

			_repositoryService.addRepository(
				themeDisplay.getScopeGroupId(), classNameId, folderId, name,
				description, portletDisplay.getId(), typeSettingsProperties,
				serviceContext);
		}
		else {

			// Update repository

			_repositoryService.updateRepository(
				repositoryId, name, description);
		}
	}

	private volatile RepositoryService _repositoryService;

}