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
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

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

	protected void deleteExpiredTemporaryFileEntries(
			ActionRequest actionRequest)
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

	protected void deleteFolders(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

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
				Folder folder = _dlTrashService.moveFolderToTrash(
					deleteFolderId);

				if (folder.getModel() instanceof DLFolder) {
					trashedModels.add((DLFolder)folder.getModel());
				}
			}
			else {
				_dlAppService.deleteFolder(deleteFolderId);
			}
		}

		if (moveToTrash && (deleteFolderIds.length > 0)) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateFolder(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteFolders(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteFolders(actionRequest, true);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeFolder(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeFolder(actionRequest);
			}
			else if (cmd.equals("deleteExpiredTemporaryFileEntries")) {
				deleteExpiredTemporaryFileEntries(actionRequest);
			}
			else if (cmd.equals("updateWorkflowDefinitions")) {
				updateWorkflowDefinitions(actionRequest);
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
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Reference(unbind = "-")
	protected void setDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	@Reference(unbind = "-")
	protected void setDLTrashService(DLTrashService dlTrashService) {
		_dlTrashService = dlTrashService;
	}

	protected void subscribeFolder(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		_dlAppService.subscribeFolder(themeDisplay.getScopeGroupId(), folderId);
	}

	protected void unsubscribeFolder(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		_dlAppService.unsubscribeFolder(
			themeDisplay.getScopeGroupId(), folderId);
	}

	protected void updateFolder(ActionRequest actionRequest) throws Exception {
		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");
		long parentFolderId = ParamUtil.getLong(
			actionRequest, "parentFolderId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFolder.class.getName(), actionRequest);

		if (folderId <= 0) {

			// Add folder

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

	protected void updateWorkflowDefinitions(ActionRequest actionRequest)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		_dlAppService.updateFolder(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, null, null,
			serviceContext);
	}

	private DLAppService _dlAppService;
	private DLTrashService _dlTrashService;

}