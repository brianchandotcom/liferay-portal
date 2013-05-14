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

package com.liferay.portlet.messageboards.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
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

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Zsolt Berentey
 */
public class MBThreadTrashRenderer extends BaseTrashRenderer {

	public static final String TYPE = "message_thread";

	public MBThreadTrashRenderer(MBThread thread)
		throws PortalException, SystemException {

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
	public String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/conversation.png";
	}

	@Override
	public String getPortletId() {
		return PortletKeys.MESSAGE_BOARDS;
	}

	@Override
	public String getSummary(Locale locale) {
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
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		MBMessageDisplay messageDisplay =
			MBMessageServiceUtil.getMessageDisplay(
				_rootMessage.getMessageId(), WorkflowConstants.STATUS_ANY,
				MBThreadConstants.THREAD_VIEW_TREE, false);

		renderRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_MESSAGE, messageDisplay);

		MBTreeWalker treeWalker = messageDisplay.getTreeWalker();

		renderRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
		renderRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY,
			messageDisplay.getCategory());
		renderRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE,
			treeWalker.getRoot());
		renderRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, new Integer(0));
		renderRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, Boolean.FALSE);
		renderRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, _rootMessage);
		renderRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD,
			messageDisplay.getThread());

		return "/html/portlet/message_boards/view_thread_tree.jsp";
	}

	private MBMessage _rootMessage;
	private MBThread _thread;

}