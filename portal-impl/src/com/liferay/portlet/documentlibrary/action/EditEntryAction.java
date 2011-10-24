/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
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
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

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
 */
public class EditEntryAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteEntries(actionRequest);
			}
			else if (cmd.equals(Constants.CANCEL_CHECKOUT)) {
				cancelCheckedOutEntries(actionRequest);
			}
			else if (cmd.equals(Constants.CHECKIN)) {
				checkInEntries(actionRequest);
			}
			else if (cmd.equals(Constants.CHECKOUT)) {
				checkOutEntries(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE)) {
				moveEntries(actionRequest);
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
						actionRequest, dle.getClass().getName(), dle.getLock());
				}
				else {
					SessionErrors.add(actionRequest, e.getClass().getName());
				}

				setForward(actionRequest, "portlet.document_library.error");
			}
			else if (e instanceof DuplicateFileException ||
					 e instanceof DuplicateFolderNameException ||
					 e instanceof NoSuchFolderException ||
					 e instanceof SourceFileNameException) {

				if (e instanceof DuplicateFileException) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(actionResponse);

					response.setStatus(
						ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION);
				}

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException) {

				SessionErrors.add(actionRequest, e.getClass().getName(), e);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getFolders(renderRequest);
			ActionUtil.getFileEntries(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.document_library.error");
			}
			else {
				throw e;
			}
		}

		String forward = "portlet.document_library.edit_entry";

		return mapping.findForward(getForward(renderRequest, forward));
	}

	protected void cancelCheckedOutEntries(ActionRequest actionRequest)
		throws Exception {

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");

		long[] folderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		for (int i = 0; i < folderIds.length; i++) {
			DLAppServiceUtil.lockFolder(repositoryId, folderIds[i]);
		}

		long[] fileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		for (int i = 0; i < fileEntryIds.length; i++) {
			DLAppServiceUtil.cancelCheckOut(fileEntryIds[i]);
		}
	}

	protected void checkInEntries(ActionRequest actionRequest)
		throws Exception {

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");

		long[] folderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		for (int i = 0; i < folderIds.length; i++) {
			DLAppServiceUtil.unlockFolder(repositoryId, folderIds[i], null);
		}

		long[] fileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		for (int i = 0; i < fileEntryIds.length; i++) {
			DLAppServiceUtil.checkInFileEntry(
				fileEntryIds[i], false, StringPool.BLANK, serviceContext);
		}
	}

	protected void checkOutEntries(ActionRequest actionRequest)
		throws Exception {

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");

		long[] folderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		for (int i = 0; i < folderIds.length; i++) {
			DLAppServiceUtil.lockFolder(repositoryId, folderIds[i]);
		}

		long[] fileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		for (int i = 0; i < fileEntryIds.length; i++) {
			DLAppServiceUtil.checkOutFileEntry(fileEntryIds[i]);
		}
	}

	protected void deleteEntries(ActionRequest actionRequest)
		throws Exception {

		long[] deleteFolderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		for (int i = 0; i < deleteFolderIds.length; i++) {
			DLAppServiceUtil.deleteFolder(deleteFolderIds[i]);
		}

		long[] fileShortcutIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileShortcutIds"), 0L);

		for (int i = 0; i < fileShortcutIds.length; i++) {
			DLAppServiceUtil.deleteFileShortcut(fileShortcutIds[i]);
		}

		long[] deleteFileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		for (int i = 0; i < deleteFileEntryIds.length; i++) {
			DLAppServiceUtil.deleteFileEntry(deleteFileEntryIds[i]);
		}
	}

	protected void moveEntries(ActionRequest actionRequest)
		throws Exception {

		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		long[] folderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		for (int i = 0; i < folderIds.length; i++) {
			DLAppServiceUtil.moveFolder(
				folderIds[i], newFolderId, serviceContext);
		}

		long[] fileEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

		for (int i = 0; i < fileEntryIds.length; i++) {
			DLAppServiceUtil.moveFileEntry(
				fileEntryIds[i], newFolderId, serviceContext);
		}
	}

}