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
int index = GetterUtil.getInteger(request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_INDEX));
int initialIndex = GetterUtil.getInteger(request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_INDEX));
int rootIndexPage = ParamUtil.getInteger(request, "rootIndexPage");

MBMessageDisplay messageDisplay = (MBMessageDisplay)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE_DISPLAY);

MBMessage message = messageDisplay.getMessage();

MBCategory category = messageDisplay.getCategory();

MBThread thread = messageDisplay.getThread();

MBTreeWalker treeWalker = messageDisplay.getTreeWalker();

int[] range = treeWalker.getChildrenRange(treeWalker.getRoot());

MBMessageIterator mbMessageIterator = new MBMessageIterator(treeWalker.getMessages(), rootIndexPage, range[1]);

if (mbMessageIterator != null) {
	while (mbMessageIterator.hasNext()) {
		rootIndexPage = mbMessageIterator.getIndexPage();

		if (index >= (initialIndex + PropsValues.DISCUSSION_COMMENTS_DELTA_VALUE)) {
			break;
		}

		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, mbMessageIterator.next());
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, Integer.valueOf(0));
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, Boolean.valueOf(false));
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, message);
		request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD, thread);
%>

		<div class="card-tab message-container">
			<liferay-util:include page="/message_boards/view_thread_tree.jsp" servletContext="<%= application %>" />
		</div>

<%
		index = GetterUtil.getInteger(request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_INDEX));
	}
}
%>

<script>
	var index = document.getElementById('<portlet:namespace />index');

	if (index) {
		index.value = '<%= String.valueOf(index) %>';
	}

	var rootIndexPage = document.getElementById(
		'<portlet:namespace />rootIndexPage'
	);

	if (rootIndexPage) {
		rootIndexPage.value = '<%= String.valueOf(rootIndexPage) %>';
	}

	<c:if test="<%= treeWalker.getMessages().size() <= (index + 1) %>">
		var moreMessagesLink = document.getElementById(
			'<portlet:namespace />moreMessages'
		);

		if (moreMessagesLink) {
			moreMessagesLink.classList.add('hide');
		}
	</c:if>
</script>