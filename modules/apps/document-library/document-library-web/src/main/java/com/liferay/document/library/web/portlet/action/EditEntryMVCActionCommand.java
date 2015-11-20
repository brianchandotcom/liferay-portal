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
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.documentlibrary.DuplicateFileEntryException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.InvalidFolderException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.trash.service.TrashEntryService;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 * @author Manuel de la Peña
 * @author Levente Hudák
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_entry",
		"mvc.command.name=/document_library/move_entry"
	},
	service = MVCActionCommand.class
)
public class EditEntryMVCActionCommand extends BaseMVCActionCommand {

	protected void cancelCheckedOutEntries(ActionRequest actionRequest)
		throws Exception {

		long[] fileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		for (long fileEntryId : fileEntryIds) {
			_dlAppService.cancelCheckOut(fileEntryId);
		}
	}

	protected void checkInEntries(ActionRequest actionRequest)
		throws Exception {

		long[] fileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		for (long fileEntryId : fileEntryIds) {
			_dlAppService.checkInFileEntry(
				fileEntryId, false, StringPool.BLANK, serviceContext);
		}
	}

	protected void checkOutEntries(ActionRequest actionRequest)
		throws Exception {

		long[] fileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		for (long fileEntryId : fileEntryIds) {
			_dlAppService.checkOutFileEntry(fileEntryId, serviceContext);
		}
	}

	protected void deleteEntries(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long[] deleteFolderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (int i = 0; i < deleteFolderIds.length; i++) {
			long deleteFolderId = deleteFolderIds[i];

			if (moveToTrash) {
				Folder folder = _dlAppService.moveFolderToTrash(deleteFolderId);

				if (folder.getModel() instanceof TrashedModel) {
					trashedModels.add((TrashedModel)folder.getModel());
				}
			}
			else {
				_dlAppService.deleteFolder(deleteFolderId);
			}
		}

		// Delete file shortcuts before file entries. See LPS-21348.

		long[] deleteFileShortcutIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileShortcutIds"), 0L);

		for (int i = 0; i < deleteFileShortcutIds.length; i++) {
			long deleteFileShortcutId = deleteFileShortcutIds[i];

			if (moveToTrash) {
				FileShortcut fileShortcut =
					_dlAppService.moveFileShortcutToTrash(deleteFileShortcutId);

				if (fileShortcut.getModel() instanceof TrashedModel) {
					trashedModels.add((TrashedModel)fileShortcut.getModel());
				}
			}
			else {
				_dlAppService.deleteFileShortcut(deleteFileShortcutId);
			}
		}

		long[] deleteFileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		for (long deleteFileEntryId : deleteFileEntryIds) {
			if (moveToTrash) {
				FileEntry fileEntry = _dlAppService.moveFileEntryToTrash(
					deleteFileEntryId);

				if (fileEntry.getModel() instanceof TrashedModel) {
					trashedModels.add((TrashedModel)fileEntry.getModel());
				}
			}
			else {
				_dlAppService.deleteFileEntry(deleteFileEntryId);
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
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
			if (cmd.equals(Constants.CANCEL_CHECKOUT)) {
				cancelCheckedOutEntries(actionRequest);
			}
			else if (cmd.equals(Constants.CHECKIN)) {
				checkInEntries(actionRequest);
			}
			else if (cmd.equals(Constants.CHECKOUT)) {
				checkOutEntries(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEntries(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE)) {
				moveEntries(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteEntries(actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreTrashEntries(actionRequest);
			}

			WindowState windowState = actionRequest.getWindowState();

			if (windowState.equals(LiferayWindowState.POP_UP)) {
				String redirect = PortalUtil.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					sendRedirect(actionRequest, actionResponse, redirect);
				}
			}
		}
		catch (DuplicateLockException | NoSuchFileEntryException |
			   NoSuchFolderException | PrincipalException e) {

			if (e instanceof DuplicateLockException) {
				DuplicateLockException dle = (DuplicateLockException)e;

				SessionErrors.add(actionRequest, dle.getClass(), dle.getLock());
			}
			else {
				SessionErrors.add(actionRequest, e.getClass());
			}

			actionResponse.setRenderParameter(
				"mvcPath", "/document_library/error.jsp");
		}
		catch (DuplicateFileEntryException | DuplicateFolderNameException |
			   SourceFileNameException e) {

			if (e instanceof DuplicateFileEntryException) {
				HttpServletResponse response =
					PortalUtil.getHttpServletResponse(actionResponse);

				response.setStatus(
					ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION);
			}

			SessionErrors.add(actionRequest, e.getClass());
		}
		catch (AssetCategoryException | AssetTagException |
			   InvalidFolderException e) {

			SessionErrors.add(actionRequest, e.getClass(), e);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected void moveEntries(ActionRequest actionRequest) throws Exception {
		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		long[] folderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		for (long folderId : folderIds) {
			_dlAppService.moveFolder(folderId, newFolderId, serviceContext);
		}

		long[] fileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		for (long fileEntryId : fileEntryIds) {
			_dlAppService.moveFileEntry(
				fileEntryId, newFolderId, serviceContext);
		}

		long[] fileShortcutIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileShortcutIds"), 0L);

		for (long fileShortcutId : fileShortcutIds) {
			if (fileShortcutId == 0) {
				continue;
			}

			FileShortcut fileShortcut = _dlAppService.getFileShortcut(
				fileShortcutId);

			_dlAppService.updateFileShortcut(
				fileShortcutId, newFolderId, fileShortcut.getToFileEntryId(),
				serviceContext);
		}
	}

	protected void restoreTrashEntries(ActionRequest actionRequest)
		throws Exception {

		long[] restoreTrashEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreTrashEntryId : restoreTrashEntryIds) {
			_trashEntryService.restoreEntry(restoreTrashEntryId);
		}
	}

	@Reference(unbind = "-")
	protected void setDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	@Reference(unbind = "-")
	protected void setTrashEntryService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	private volatile DLAppService _dlAppService;
	private volatile TrashEntryService _trashEntryService;

}