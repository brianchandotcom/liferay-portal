<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
%>

<c:if test="<%= mbMessageIterator != null %>">

	<%
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
	%>

</c:if>

<%
List<MBMessage> messages = treeWalker.getMessages();
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

	<c:if test="<%= messages.size() <= (index + 1) %>">
		var moreMessagesLink = document.getElementById(
			'<portlet:namespace />moreMessages'
		);

		if (moreMessagesLink) {
			moreMessagesLink.classList.add('hide');
		}
	</c:if>
</script>