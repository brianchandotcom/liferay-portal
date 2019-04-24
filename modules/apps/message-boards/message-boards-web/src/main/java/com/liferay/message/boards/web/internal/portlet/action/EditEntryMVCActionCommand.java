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

package com.liferay.message.boards.web.internal.portlet.action;

import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.exception.LockedThreadException;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryService;
import com.liferay.message.boards.service.MBThreadService;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
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
 * @author Sergio González
 */
@Component(
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"mvc.command.name=/message_boards/edit_entry"
	},
	service = MVCActionCommand.class
)
public class EditEntryMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteEntries(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] threadIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsMBThread");

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long threadId : threadIds) {
			if (moveToTrash) {
				MBThread thread = _mbThreadService.moveThreadToTrash(threadId);

				trashedModels.add(thread);
			}
			else {
				_mbThreadService.deleteThread(threadId);
			}
		}

		long[] categoryIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsMBCategory");

		for (long categoryId : categoryIds) {
			if (moveToTrash) {
				MBCategory category = _mbCategoryService.moveCategoryToTrash(
					categoryId);

				trashedModels.add(category);
			}
			else {
				_mbCategoryService.deleteCategory(
					themeDisplay.getScopeGroupId(), categoryId);
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
			if (cmd.equals(Constants.DELETE)) {
				deleteEntries(actionRequest, false);
			}
			else if (cmd.equals(Constants.LOCK)) {
				lockThreads(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteEntries(actionRequest, true);
			}
			else if (cmd.equals(Constants.UNLOCK)) {
				unlockThreads(actionRequest);
			}
		}
		catch (LockedThreadException | PrincipalException e) {
			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter(
				"mvcPath", "/message_boards/error.jsp");
		}
	}

	protected void lockThreads(ActionRequest actionRequest) throws Exception {
		long[] threadIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsMBThread");

		for (long threadId : threadIds) {
			_mbThreadService.lockThread(threadId);
		}
	}

	protected void unlockThreads(ActionRequest actionRequest) throws Exception {
		long[] threadIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsMBThread");

		for (long threadId : threadIds) {
			_mbThreadService.unlockThread(threadId);
		}
	}

	@Reference
	private MBCategoryService _mbCategoryService;

	@Reference
	private MBThreadService _mbThreadService;

}