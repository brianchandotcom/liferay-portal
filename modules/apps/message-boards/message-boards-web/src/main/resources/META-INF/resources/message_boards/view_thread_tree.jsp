<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/message_boards/init.jsp" %>

<%
MBTreeWalker treeWalker = (MBTreeWalker)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER);
MBMessage selMessage = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE);
MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE);
MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY);
MBThread thread = (MBThread)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD);
int depth = (Integer)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH);

int index = GetterUtil.getInteger(request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_INDEX));

index++;

request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_INDEX, Integer.valueOf(index));

if (message.getMessageId() == selMessage.getMessageId()) {
	request.setAttribute("view_thread_tree.jsp-messageFound", true);
}

List<MBMessage> messages = treeWalker.getMessages();
int[] range = treeWalker.getChildrenRange(message);

MBMessageIterator mbMessageIterator = new MBMessageIterator(messages, range[0], range[1]);

MBMessage rootMessage = treeWalker.getRoot();
%>

<c:choose>
	<c:when test="<%= !MBUtil.isViewableMessage(themeDisplay, message) %>">
		<c:if test="<%= (message.getParentMessageId() == MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) || mbMessageIterator.hasNext() %>">
			<div class="alert alert-danger">
				<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource" />
			</div>
		</c:if>
	</c:when>
	<c:otherwise>

		<%
		request.setAttribute("edit-message.jsp-showPermanentLink", Boolean.TRUE);
		request.setAttribute("edit-message.jsp-showRecentPosts", Boolean.TRUE);
		request.setAttribute("edit_message.jsp-category", category);
		request.setAttribute("edit_message.jsp-editable", !thread.isInTrash());
		request.setAttribute("edit_message.jsp-message", message);
		request.setAttribute("edit_message.jsp-thread", thread);
		%>

		<liferay-util:include page="/message_boards/view_thread_message.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>

<c:if test="<%= message.getMessageId() != rootMessage.getMessageId() %>">

	<%
	depth++;

	while (mbMessageIterator.hasNext()) {
		MBMessage curMessage = mbMessageIterator.next();

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

	<c:if test="<%= !thread.isLocked() && !message.isDraft() && MBCategoryPermission.contains(permissionChecker, scopeGroupId, message.getCategoryId(), ActionKeys.REPLY_TO_MESSAGE) %>">

		<%
		long replyToMessageId = message.getMessageId();
		%>

		<%@ include file="/message_boards/edit_message_quick.jspf" %>
	</c:if>
</c:if>