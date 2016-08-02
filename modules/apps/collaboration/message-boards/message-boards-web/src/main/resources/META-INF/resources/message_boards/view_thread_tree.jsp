<%--
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
--%>

<%@ include file="/message_boards/init.jsp" %>

<%
MBTreeWalker treeWalker = (MBTreeWalker)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER);
MBMessage selMessage = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE);
MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE);
MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY);
MBThread thread = (MBThread)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD);
int depth = ((Integer)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH)).intValue();

int index = GetterUtil.getInteger(request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_INDEX));

index++;

request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_INDEX, Integer.valueOf(index));

if (message.getMessageId() == selMessage.getMessageId()) {
	request.setAttribute("view_thread_tree.jsp-messageFound", true);
}

MBMessageDisplay messageDisplay = (MBMessageDisplay)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE_DISPLAY);
%>

<c:if test="<%= (message.getMessageId() != selMessage.getMessageId()) || MBUtil.isViewableMessage(themeDisplay, message) %>">

	<%
	request.setAttribute("edit-message.jsp-showDeletedAttachmentsFileEntries", Boolean.TRUE);
	request.setAttribute("edit-message.jsp-showPermanentLink", Boolean.TRUE);
	request.setAttribute("edit-message.jsp-showRecentPosts", Boolean.TRUE);
	request.setAttribute("edit_message.jsp-category", category);
	request.setAttribute("edit_message.jsp-editable", Boolean.TRUE);
	request.setAttribute("edit_message.jsp-message", message);
	request.setAttribute("edit_message.jsp-thread", thread);
	%>

	<liferay-util:include page="/message_boards/view_thread_message.jsp" servletContext="<%= application %>" />

	<%
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_VIEWABLE_THREAD, Boolean.TRUE.toString());
	%>

</c:if>

<c:if test="<%= message.getMessageId() != treeWalker.getRoot().getMessageId() %>">

	<%
	List messages = treeWalker.getMessages();
	int[] range = treeWalker.getChildrenRange(message);

	depth++;

	MBMessageIterator mbMessageIterator = new MBMessageIteratorImpl(messages, range[0], range[1]);

	while (mbMessageIterator.hasNext()) {
		MBMessage curMessage = mbMessageIterator.next();

		if (!MBUtil.isViewableMessage(themeDisplay, curMessage, message)) {
			continue;
		}

		boolean lastChildNode = false;

		if (!mbMessageIterator.hasNext()) {
			lastChildNode = true;
		}

		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, curMessage);
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, Integer.valueOf(depth));
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, Boolean.valueOf(lastChildNode));
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, selMessage);
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD, thread);
	%>

		<div class="card-tab message-container">
			<liferay-util:include page="/message_boards/view_thread_tree.jsp" servletContext="<%= application %>" />
		</div>

	<%
	}
	%>

	<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, message.getCategoryId(), ActionKeys.REPLY_TO_MESSAGE) && !thread.isLocked() %>">

		<%
		MBMessage curMessage = message;
		long replyToMessageId = message.getMessageId();
		%>

		<%@ include file="/message_boards/edit_message_quick.jspf" %>
	</c:if>
</c:if>