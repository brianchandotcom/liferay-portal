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

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.InvalidFolderException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.exception.SourceFileNameException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLTrashService;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.trash.service.TrashEntryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		long[] fileEntryIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsFileEntry");

		for (long fileEntryId : fileEntryIds) {
			_dlAppService.cancelCheckOut(fileEntryId);
		}
	}

	protected void checkInEntries(ActionRequest actionRequest)
		throws Exception {

		long[] fileEntryIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsFileEntry");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		for (long fileEntryId : fileEntryIds) {
			_dlAppService.checkInFileEntry(
				fileEntryId, false, StringPool.BLANK, serviceContext);
		}
	}

	protected void checkOutEntries(ActionRequest actionRequest)
		throws Exception {

		long[] fileEntryIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsFileEntry");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		for (long fileEntryId : fileEntryIds) {
			_dlAppService.checkOutFileEntry(fileEntryId, serviceContext);
		}
	}

	protected void deleteEntries(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long[] deleteFolderIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsFolder");

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (int i = 0; i < deleteFolderIds.length; i++) {
			long deleteFolderId = deleteFolderIds[i];

			if (moveToTrash) {
				Folder folder = _dlTrashService.moveFolderToTrash(
					deleteFolderId);

				if (folder.getModel() instanceof TrashedModel) {
					trashedModels.add((TrashedModel)folder.getModel());
				}
			}
			else {
				_dlAppService.deleteFolder(deleteFolderId);
			}
		}

		// Delete file shortcuts before file entries. See LPS-21348.

		long[] deleteFileShortcutIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsDLFileShortcut");

		for (int i = 0; i < deleteFileShortcutIds.length; i++) {
			long deleteFileShortcutId = deleteFileShortcutIds[i];

			if (moveToTrash) {
				FileShortcut fileShortcut =
					_dlTrashService.moveFileShortcutToTrash(
						deleteFileShortcutId);

				if (fileShortcut.getModel() instanceof TrashedModel) {
					trashedModels.add((TrashedModel)fileShortcut.getModel());
				}
			}
			else {
				_dlAppService.deleteFileShortcut(deleteFileShortcutId);
			}
		}

		long[] deleteFileEntryIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsFileEntry");

		for (long deleteFileEntryId : deleteFileEntryIds) {
			if (moveToTrash) {
				FileEntry fileEntry = _dlTrashService.moveFileEntryToTrash(
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
			Map<String, Object> data = new HashMap<>();

			data.put("trashedModels", trashedModels);

			addDeleteSuccessData(actionRequest, data);
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
				String redirect = _portal.escapeRedirect(
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
				HttpServletResponse response = _portal.getHttpServletResponse(
					actionResponse);

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

		long[] folderIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsFolder");

		for (long folderId : folderIds) {
			_dlAppService.moveFolder(folderId, newFolderId, serviceContext);
		}

		long[] fileEntryIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsFileEntry");

		for (long fileEntryId : fileEntryIds) {
			_dlAppService.moveFileEntry(
				fileEntryId, newFolderId, serviceContext);
		}

		long[] fileShortcutIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsDLFileShortcut");

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
	protected void setDLTrashService(DLTrashService dlTrashService) {
		_dlTrashService = dlTrashService;
	}

	@Reference(unbind = "-")
	protected void setTrashEntryService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	private DLAppService _dlAppService;
	private DLTrashService _dlTrashService;

	@Reference
	private Portal _portal;

	private TrashEntryService _trashEntryService;

}