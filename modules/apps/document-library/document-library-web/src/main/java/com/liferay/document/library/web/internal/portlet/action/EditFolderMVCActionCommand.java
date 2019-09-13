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
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFileException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.FolderNameException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.exception.RequiredFileEntryTypeException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLTrashService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Sergio González
 * @author Levente Hudák
 * @author Roberto Díaz
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_folder"
	},
	service = MVCActionCommand.class
)
public class EditFolderMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateFolder(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteFolders(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				_deleteFolders(actionRequest, true);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				_subscribeFolder(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				_unsubscribeFolder(actionRequest);
			}
			else if (cmd.equals("deleteExpiredTemporaryFileEntries")) {
				_deleteExpiredTemporaryFileEntries(actionRequest);
			}
			else if (cmd.equals("updateWorkflowDefinitions")) {
				_updateWorkflowDefinitions(actionRequest);
			}
		}
		catch (NoSuchFolderException | PrincipalException e) {
			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter(
				"mvcPath", "/document_library/error.jsp");
		}
		catch (DuplicateFileEntryException | DuplicateFileException |
			   DuplicateFolderNameException | FolderNameException |
			   RequiredFileEntryTypeException e) {

			SessionErrors.add(actionRequest, e.getClass());
		}
	}

	private void _deleteExpiredTemporaryFileEntries(ActionRequest actionRequest)
		throws PortalException {

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(repositoryId);

		if (localRepository.isCapabilityProvided(
				TemporaryFileEntriesCapability.class)) {

			TemporaryFileEntriesCapability temporaryFileEntriesCapability =
				localRepository.getCapability(
					TemporaryFileEntriesCapability.class);

			temporaryFileEntriesCapability.deleteExpiredTemporaryFileEntries();
		}
	}

	private void _deleteFolders(
			ActionRequest actionRequest, boolean moveToTrash)
		throws PortalException {

		long[] deleteFolderIds = null;

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		if (folderId > 0) {
			deleteFolderIds = new long[] {folderId};
		}
		else {
			deleteFolderIds = ParamUtil.getLongValues(
				actionRequest, "rowIdsFolder");
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long deleteFolderId : deleteFolderIds) {
			if (moveToTrash) {
				Folder folder = _dlAppService.getFolder(deleteFolderId);

				if (folder.isRepositoryCapabilityProvided(
						TrashCapability.class)) {

					_dlTrashService.moveFolderToTrash(deleteFolderId);

					trashedModels.add((DLFolder)folder.getModel());
				}
			}
			else {
				_dlAppService.deleteFolder(deleteFolderId);
			}
		}

		if (moveToTrash && (deleteFolderIds.length > 0)) {
			Map<String, Object> data = new HashMap<>();

			data.put("trashedModels", trashedModels);

			addDeleteSuccessData(actionRequest, data);
		}
	}

	private void _subscribeFolder(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		_dlAppService.subscribeFolder(themeDisplay.getScopeGroupId(), folderId);
	}

	private void _unsubscribeFolder(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		_dlAppService.unsubscribeFolder(
			themeDisplay.getScopeGroupId(), folderId);
	}

	private void _updateFolder(ActionRequest actionRequest)
		throws PortalException {

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFolder.class.getName(), actionRequest);

		if (folderId <= 0) {

			// Add folder

			long repositoryId = ParamUtil.getLong(
				actionRequest, "repositoryId");
			long parentFolderId = ParamUtil.getLong(
				actionRequest, "parentFolderId");

			_dlAppService.addFolder(
				repositoryId, parentFolderId, name, description,
				serviceContext);
		}
		else {

			// Update folder

			_dlAppService.updateFolder(
				folderId, name, description, serviceContext);
		}
	}

	private void _updateWorkflowDefinitions(ActionRequest actionRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		_dlAppService.updateFolder(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, null, null,
			serviceContext);
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLTrashService _dlTrashService;

}