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

package com.liferay.portlet.messageboards.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.BaseTrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadConstants;
import com.liferay.portlet.messageboards.model.MBTreeWalker;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Zsolt Berentey
 */
public class MBThreadTrashRenderer extends BaseTrashRenderer {

	public static final String TYPE = "message_thread";

	public MBThreadTrashRenderer(MBThread thread) throws PortalException {
		_thread = thread;

		_rootMessage = MBMessageLocalServiceUtil.getMBMessage(
			thread.getRootMessageId());
	}

	@Override
	public String getClassName() {
		return MBThread.class.getName();
	}

	@Override
	public long getClassPK() {
		return _thread.getPrimaryKey();
	}

	@Override
	public String getIconCssClass() {
		return "icon-comments";
	}

	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/conversation.png";
	}

	@Override
	public String getPortletId() {
		return PortletKeys.MESSAGE_BOARDS;
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return null;
	}

	@Override
	public String getTitle(Locale locale) {
		return HtmlUtil.stripHtml(_rootMessage.getSubject());
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String render(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String template)
		throws Exception {

		MBMessageDisplay messageDisplay =
			MBMessageServiceUtil.getMessageDisplay(
				_rootMessage.getMessageId(), WorkflowConstants.STATUS_ANY,
				MBThreadConstants.THREAD_VIEW_TREE, false);

		portletRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_MESSAGE, messageDisplay);

		MBTreeWalker treeWalker = messageDisplay.getTreeWalker();

		portletRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
		portletRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY,
			messageDisplay.getCategory());
		portletRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE,
			treeWalker.getRoot());
		portletRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, Integer.valueOf(0));
		portletRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, Boolean.FALSE);
		portletRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, _rootMessage);
		portletRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD,
			messageDisplay.getThread());

		return "/html/portlet/message_boards/view_thread_tree.jsp";
	}

	private final MBMessage _rootMessage;
	private final MBThread _thread;

}