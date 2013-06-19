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

import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.documentlibrary.FileShortcutPermissionException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Levente Hudák
 */
public class EditFileShortcutAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateFileShortcut(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteFileShortcut(
					(LiferayPortletConfig)portletConfig, actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE)) {
				moveFileShortcut(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_FROM_TRASH)) {
				moveFileShortcut(actionRequest, true);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteFileShortcut(
					(LiferayPortletConfig)portletConfig, actionRequest, true);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileShortcutException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.document_library.error");
			}
			else if (e instanceof FileShortcutPermissionException ||
					 e instanceof NoSuchFileEntryException) {

				SessionErrors.add(actionRequest, e.getClass());
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
			ActionUtil.getFileShortcut(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileShortcutException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward(
					"portlet.document_library.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(
				renderRequest, "portlet.document_library.edit_file_shortcut"));
	}

	protected void deleteFileShortcut(
			LiferayPortletConfig liferayPortletConfig,
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long fileShortcutId = ParamUtil.getLong(
			actionRequest, "fileShortcutId");

		if (moveToTrash) {
			DLAppServiceUtil.moveFileShortcutToTrash(fileShortcutId);

			Map<String, String[]> data = new HashMap<String, String[]>();

			data.put(
				"restoreFileShortcutIds",
				new String[] {String.valueOf(fileShortcutId)});

			SessionMessages.add(
				actionRequest,
				liferayPortletConfig.getPortletId() +
					SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA, data);

			SessionMessages.add(
				actionRequest,
				liferayPortletConfig.getPortletId() +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
		}
		else {
			DLAppServiceUtil.deleteFileShortcut(fileShortcutId);
		}
	}

	protected void moveFileShortcut(
			ActionRequest actionRequest, boolean moveFromTrash)
		throws Exception {

		long fileShortcutId = ParamUtil.getLong(
			actionRequest, "fileShortcutId");

		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		DLFileShortcut fileShortcut = DLAppServiceUtil.getFileShortcut(
			fileShortcutId);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileShortcut.class.getName(), actionRequest);

		if (moveFromTrash) {
			DLAppServiceUtil.moveFileShortcutFromTrash(
				fileShortcutId, newFolderId, serviceContext);
		}
		else {
			DLAppServiceUtil.updateFileShortcut(
				fileShortcutId, newFolderId, fileShortcut.getToFileEntryId(),
				serviceContext);
		}
	}

	protected void updateFileShortcut(ActionRequest actionRequest)
		throws Exception {

		long fileShortcutId = ParamUtil.getLong(
			actionRequest, "fileShortcutId");

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");
		long folderId = ParamUtil.getLong(actionRequest, "folderId");
		long toFileEntryId = ParamUtil.getLong(actionRequest, "toFileEntryId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileShortcut.class.getName(), actionRequest);

		if (fileShortcutId <= 0) {

			// Add file shortcut

			DLFileShortcut fileShortcut = DLAppServiceUtil.addFileShortcut(
				repositoryId, folderId, toFileEntryId, serviceContext);

			AssetPublisherUtil.addAndStoreSelection(
				actionRequest, DLFileShortcut.class.getName(),
				fileShortcut.getFileShortcutId(), -1);
		}
		else {

			// Update file shortcut

			DLAppServiceUtil.updateFileShortcut(
				fileShortcutId, folderId, toFileEntryId, serviceContext);

			AssetPublisherUtil.addRecentFolderId(
				actionRequest, DLFileShortcut.class.getName(), folderId);
		}
	}

}