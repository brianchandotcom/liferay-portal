/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.DuplicateLockException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 * @author Manuel de la Peña
 * @author Levente Hudák
 */
public class EditEntryAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
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
				deleteEntries(
					(LiferayPortletConfig)portletConfig, actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE)) {
				moveEntries(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteEntries(
					(LiferayPortletConfig)portletConfig, actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreEntries(actionRequest);
			}

			WindowState windowState = actionRequest.getWindowState();

			if (!windowState.equals(LiferayWindowState.POP_UP)) {
				sendRedirect(actionRequest, actionResponse);
			}
			else {
				String redirect = PortalUtil.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					actionResponse.sendRedirect(redirect);
				}
			}
		}
		catch (Exception e) {
			if (e instanceof DuplicateLockException ||
				e instanceof NoSuchFileEntryException ||
				e instanceof NoSuchFolderException ||
				e instanceof PrincipalException) {

				if (e instanceof DuplicateLockException) {
					DuplicateLockException dle = (DuplicateLockException)e;

					SessionErrors.add(
						actionRequest, dle.getClass(), dle.getLock());
				}
				else {
					SessionErrors.add(actionRequest, e.getClass());
				}

				setForward(actionRequest, "portlet.document_library.error");
			}
			else if (e instanceof DuplicateFileException ||
					 e instanceof DuplicateFolderNameException ||
					 e instanceof SourceFileNameException) {

				if (e instanceof DuplicateFileException) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(actionResponse);

					response.setStatus(
						ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION);
				}

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getFileEntries(renderRequest);
			ActionUtil.getFileShortcuts(renderRequest);
			ActionUtil.getFolders(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward(
					"portlet.document_library.error");
			}
			else {
				throw e;
			}
		}

		String forward = "portlet.document_library.edit_entry";

		return actionMapping.findForward(getForward(renderRequest, forward));
	}

	protected void cancelCheckedOutEntries(ActionRequest actionRequest)
		throws Exception {

		long[] fileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		for (long fileEntryId : fileEntryIds) {
			DLAppServiceUtil.cancelCheckOut(fileEntryId);
		}

		long[] fileShortcutIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileShortcutIds"), 0L);

		for (long fileShortcutId : fileShortcutIds) {
			DLFileShortcut fileShortcut = DLAppLocalServiceUtil.getFileShortcut(
				fileShortcutId);

			DLAppServiceUtil.cancelCheckOut(fileShortcut.getToFileEntryId());
		}
	}

	protected void checkInEntries(ActionRequest actionRequest)
		throws Exception {

		long[] fileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		for (long fileEntryId : fileEntryIds) {
			DLAppServiceUtil.checkInFileEntry(
				fileEntryId, false, StringPool.BLANK, serviceContext);
		}

		long[] fileShortcutIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileShortcutIds"), 0L);

		for (long fileShortcutId : fileShortcutIds) {
			DLFileShortcut fileShortcut = DLAppLocalServiceUtil.getFileShortcut(
				fileShortcutId);

			DLAppServiceUtil.checkInFileEntry(
				fileShortcut.getToFileEntryId(), false, StringPool.BLANK,
				serviceContext);
		}
	}

	protected void checkOutEntries(ActionRequest actionRequest)
		throws Exception {

		long[] fileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		for (long fileEntryId : fileEntryIds) {
			DLAppServiceUtil.checkOutFileEntry(fileEntryId, serviceContext);
		}

		long[] fileShortcutIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileShortcutIds"), 0L);

		for (long fileShortcutId : fileShortcutIds) {
			DLFileShortcut fileShortcut = DLAppLocalServiceUtil.getFileShortcut(
				fileShortcutId);

			DLAppServiceUtil.checkOutFileEntry(
				fileShortcut.getToFileEntryId(), serviceContext);
		}
	}

	protected void deleteEntries(
			LiferayPortletConfig liferayPortletConfig,
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long[] deleteFolderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		for (long deleteFolderId : deleteFolderIds) {
			if (moveToTrash) {
				DLAppServiceUtil.moveFolderToTrash(deleteFolderId);
			}
			else {
				DLAppServiceUtil.deleteFolder(deleteFolderId);
			}
		}

		// Delete file shortcuts before file entries. See LPS-21348.

		long[] deleteFileShortcutIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileShortcutIds"), 0L);

		for (long deleteFileShortcutId : deleteFileShortcutIds) {
			if (moveToTrash) {
				DLAppServiceUtil.moveFileShortcutToTrash(deleteFileShortcutId);
			}
			else {
				DLAppServiceUtil.deleteFileShortcut(deleteFileShortcutId);
			}
		}

		long[] deleteFileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		for (long deleteFileEntryId : deleteFileEntryIds) {
			if (moveToTrash) {
				DLAppServiceUtil.moveFileEntryToTrash(deleteFileEntryId);
			}
			else {
				DLAppServiceUtil.deleteFileEntry(deleteFileEntryId);
			}
		}

		if (moveToTrash &&
			((deleteFileEntryIds.length > 0) ||
			 (deleteFileShortcutIds.length > 0) ||
			 (deleteFolderIds.length > 0))) {

			Map<String, String[]> data = new HashMap<String, String[]>();

			data.put(
				"restoreFileEntryIds",
				ArrayUtil.toStringArray(deleteFileEntryIds));
			data.put(
				"restoreFileShortcutIds",
				ArrayUtil.toStringArray(deleteFileShortcutIds));
			data.put(
				"restoreFolderIds", ArrayUtil.toStringArray(deleteFolderIds));

			SessionMessages.add(
				actionRequest,
				liferayPortletConfig.getPortletId() +
					SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA, data);

			SessionMessages.add(
				actionRequest,
				liferayPortletConfig.getPortletId() +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
		}
	}

	protected void moveEntries(ActionRequest actionRequest) throws Exception {
		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		long[] folderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		for (long folderId : folderIds) {
			DLAppServiceUtil.moveFolder(folderId, newFolderId, serviceContext);
		}

		long[] fileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		for (long fileEntryId : fileEntryIds) {
			DLAppServiceUtil.moveFileEntry(
				fileEntryId, newFolderId, serviceContext);
		}

		long[] fileShortcutIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileShortcutIds"), 0L);

		for (long fileShortcutId : fileShortcutIds) {
			if (fileShortcutId == 0) {
				continue;
			}

			DLFileShortcut fileShortcut = DLAppServiceUtil.getFileShortcut(
				fileShortcutId);

			DLAppServiceUtil.updateFileShortcut(
				fileShortcutId, newFolderId, fileShortcut.getToFileEntryId(),
				serviceContext);
		}
	}

	protected void restoreEntries(ActionRequest actionRequest)
		throws PortalException, SystemException {

		long[] restoreFolderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreFolderIds"), 0L);

		for (long restoreFolderId : restoreFolderIds) {
			DLAppServiceUtil.restoreFolderFromTrash(restoreFolderId);
		}

		long[] restoreFileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreFileEntryIds"), 0L);

		for (long restoreFileEntryId : restoreFileEntryIds) {
			DLAppServiceUtil.restoreFileEntryFromTrash(restoreFileEntryId);
		}

		long[] restoreFileShortcutIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreFileShortcutIds"), 0L);

		for (long restoreFileShortcutId : restoreFileShortcutIds) {
			DLAppServiceUtil.restoreFileShortcutFromTrash(
				restoreFileShortcutId);
		}
	}

}