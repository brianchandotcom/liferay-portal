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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.DuplicateRepositoryNameException;
import com.liferay.document.library.kernel.exception.FolderNameException;
import com.liferay.document.library.kernel.exception.RepositoryNameException;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.exception.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.NoSuchRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.RepositoryService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

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
		throws PortalException {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateRepository(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_unmountRepository(actionRequest);
			}
		}
		catch (NoSuchRepositoryException | PrincipalException e) {
			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter(
				"mvcPath", "/document_library/error.jsp");
		}
		catch (InvalidRepositoryException ire) {
			_log.error(ire, ire);

			SessionErrors.add(actionRequest, ire.getClass());
		}
		catch (DuplicateFolderNameException | DuplicateRepositoryNameException |
			   FolderNameException | RepositoryNameException e) {

			SessionErrors.add(actionRequest, e.getClass());
		}
	}

	private void _unmountRepository(ActionRequest actionRequest)
		throws PortalException {

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");

		_repositoryService.deleteRepository(repositoryId);
	}

	private void _updateRepository(ActionRequest actionRequest)
		throws PortalException {

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		if (repositoryId <= 0) {

			// Add repository

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			String className = ParamUtil.getString(actionRequest, "className");

			long classNameId = _portal.getClassNameId(className);

			long folderId = ParamUtil.getLong(actionRequest, "folderId");

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
			UnicodeProperties typeSettingsProperties =
				PropertiesParamUtil.getProperties(actionRequest, "settings--");
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				DLFolder.class.getName(), actionRequest);

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

	private static final Log _log = LogFactoryUtil.getLog(
		EditRepositoryMVCActionCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private RepositoryService _repositoryService;

}