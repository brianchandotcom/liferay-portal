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

package com.liferay.message.boards.web.portlet.action;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portlet.messageboards.exception.LockedThreadException;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBThreadService;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Deepak Gothe
 * @author Sergio González
 * @author Zsolt Berentey
 */
@Component(
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"mvc.command.name=/message_boards/delete_thread"
	},
	service = MVCActionCommand.class
)
public class DeleteThreadMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteThreads(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long[] deleteThreadIds = null;

		long threadId = ParamUtil.getLong(actionRequest, "threadId");

		if (threadId > 0) {
			deleteThreadIds = new long[] {threadId};
		}
		else {
			deleteThreadIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "threadIds"), 0L);
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long deleteThreadId : deleteThreadIds) {
			if (moveToTrash) {
				MBThread thread = _mbThreadService.moveThreadToTrash(
					deleteThreadId);

				trashedModels.add(thread);
			}
			else {
				_mbThreadService.deleteThread(deleteThreadId);
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
			if (cmd.equals(Constants.DELETE)) {
				deleteThreads(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteThreads(actionRequest, true);
			}
		}
		catch (LockedThreadException | PrincipalException e) {
			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter(
				"mvcPath", "/message_boards/error.jsp");
		}
	}

	@Reference(unbind = "-")
	protected void setMBThreadService(MBThreadService mbThreadService) {
		_mbThreadService = mbThreadService;
	}

	private MBThreadService _mbThreadService;

}