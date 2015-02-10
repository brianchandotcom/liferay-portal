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

<%@ include file="/html/taglib/ui/discussion/init.jsp" %>

<%
String className = (String)request.getAttribute("liferay-ui:discussion:className");
long classPK = GetterUtil.getLong((String) request.getAttribute("liferay-ui:discussion:classPK"));
int index = GetterUtil.getInteger(request.getAttribute("liferay-ui:discussion:index"));
int initialIndex = GetterUtil.getInteger(request.getAttribute("liferay-ui:discussion:index"));
int rootIndexPage = GetterUtil.getInteger(request.getAttribute("liferay-ui:discussion:rootIndexPage"));
long userId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:discussion:userId"));

MBMessageDisplay messageDisplay = MBMessageLocalServiceUtil.getDiscussionMessageDisplay(userId, themeDisplay.getScopeGroupId(), className, classPK, WorkflowConstants.STATUS_ANY);

MBTreeWalker treeWalker = messageDisplay.getTreeWalker();
MBMessage rootMessage = treeWalker.getRoot();
List<MBMessage> messages = treeWalker.getMessages();

messages = ListUtil.copy(messages);

messages.remove(0);

List<Long> classPKs = new ArrayList<Long>();

for (MBMessage curMessage : messages) {
	classPKs.add(curMessage.getMessageId());
}

List<RatingsEntry> ratingsEntries = RatingsEntryLocalServiceUtil.getEntries(themeDisplay.getUserId(), MBDiscussion.class.getName(), classPKs);
List<RatingsStats> ratingsStatsList = RatingsStatsLocalServiceUtil.getStats(MBDiscussion.class.getName(), classPKs);

int[] range = treeWalker.getChildrenRange(rootMessage);

for (;rootIndexPage < range[1] - 1; rootIndexPage++) {
	if (index >= (initialIndex + PropsValues.DISCUSSION_COMMENTS_DELTA_VALUE)) {
		break;
	}

	MBMessage message = (MBMessage)messages.get(rootIndexPage);

	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, message);

	request.setAttribute("liferay-ui:discussion:ratingsEntries", ratingsEntries);
	request.setAttribute("liferay-ui:discussion:ratingsStatsList", ratingsStatsList);
	request.setAttribute("liferay-ui:discussion:rootMessage", rootMessage);
%>

	<liferay-util:include page="/html/taglib/ui/discussion/view_message_thread.jsp" />

<%
	index = GetterUtil.getInteger(request.getAttribute("liferay-ui:discussion:index"));
}
%>

<aui:script sandbox="<%= true %>">
	var rootIndexPage = $('#<%= namespace %>rootIndexPage');
	var index = $('#<%= namespace %>index');

	rootIndexPage.val('<%= String.valueOf(rootIndexPage) %>');
	index.val('<%= String.valueOf(index) %>');

	<c:if test="<%= messages.size() <= (index) %>">
		var moreCommentsLink = $('#<%= namespace %>moreComments');

		moreCommentsLink.hide();
	</c:if>
</aui:script>