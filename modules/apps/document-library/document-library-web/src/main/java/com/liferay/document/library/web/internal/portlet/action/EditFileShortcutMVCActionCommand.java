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
import com.liferay.document.library.kernel.exception.FileShortcutPermissionException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileShortcutException;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLTrashService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

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
 * @author Levente Hudák
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_file_shortcut"
	},
	service = MVCActionCommand.class
)
public class EditFileShortcutMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateFileShortcut(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteFileShortcut(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				_deleteFileShortcut(actionRequest, true);
			}
		}
		catch (NoSuchFileShortcutException | PrincipalException e) {
			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter(
				"mvcPath", "/document_library/error.jsp");
		}
		catch (FileShortcutPermissionException | NoSuchFileEntryException e) {
			SessionErrors.add(actionRequest, e.getClass());
		}
	}

	private void _deleteFileShortcut(
			ActionRequest actionRequest, boolean moveToTrash)
		throws PortalException {

		long fileShortcutId = ParamUtil.getLong(
			actionRequest, "fileShortcutId");

		if (moveToTrash) {
			FileShortcut fileShortcut = _dlTrashService.moveFileShortcutToTrash(
				fileShortcutId);

			if (!(fileShortcut.getModel() instanceof TrashedModel)) {
				hideDefaultSuccessMessage(actionRequest);

				return;
			}

			List<TrashedModel> trashedModels = new ArrayList<>();

			trashedModels.add((TrashedModel)fileShortcut.getModel());

			Map<String, Object> data = new HashMap<>();

			data.put("trashedModels", trashedModels);

			addDeleteSuccessData(actionRequest, data);
		}
		else {
			_dlAppService.deleteFileShortcut(fileShortcutId);
		}
	}

	private void _updateFileShortcut(ActionRequest actionRequest)
		throws PortalException {

		long fileShortcutId = ParamUtil.getLong(
			actionRequest, "fileShortcutId");

		long folderId = ParamUtil.getLong(actionRequest, "folderId");
		long toFileEntryId = ParamUtil.getLong(actionRequest, "toFileEntryId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileShortcutConstants.getClassName(), actionRequest);

		if (fileShortcutId <= 0) {

			// Add file shortcut

			long repositoryId = ParamUtil.getLong(
				actionRequest, "repositoryId");

			_dlAppService.addFileShortcut(
				repositoryId, folderId, toFileEntryId, serviceContext);
		}
		else {

			// Update file shortcut

			_dlAppService.updateFileShortcut(
				fileShortcutId, folderId, toFileEntryId, serviceContext);
		}
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLTrashService _dlTrashService;

}