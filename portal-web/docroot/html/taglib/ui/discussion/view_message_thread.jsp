<%--
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
--%>

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
MBTreeWalker treeWalker = (MBTreeWalker)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER);
MBMessage selMessage = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE);
MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE);
MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY);
MBThread thread = (MBThread)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD);
boolean lastNode = ((Boolean)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE)).booleanValue();
int depth = ((Integer)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH)).intValue();
%>

<tr>
	<td class="table-cell" style="padding-left: <%= depth * 10 %>px; width: 90%">
		<c:if test="<%= !message.isRoot() %>">
			<c:choose>
				<c:when test="<%= !lastNode %>">
					<img alt="" src="<%= themeDisplay.getPathThemeImages() %>/message_boards/t.png" />
				</c:when>
				<c:otherwise>
					<img alt="" src="<%= themeDisplay.getPathThemeImages() %>/message_boards/l.png" />
				</c:otherwise>
			</c:choose>
		</c:if>

		<%
		String rowHREF = "#" + renderResponse.getNamespace() + "message_" + message.getMessageId();
		%>

		<a href="<%= rowHREF %>"><%= HtmlUtil.escape(StringUtil.shorten(message.getBody(), 50, StringPool.TRIPLE_PERIOD)) %></a>
	</td>
	<td class="table-cell"></td>
	<td class="table-cell" nowrap="nowrap">
		<a href="<%= rowHREF %>">

		<c:choose>
			<c:when test="<%= message.isAnonymous() %>">
				<c:choose>
					<c:when test="<%= Validator.isNull(message.getUserName()) %>">
						<liferay-ui:message key="anonymous" />
					</c:when>
					<c:otherwise>
						<%= HtmlUtil.escape(message.getUserName()) %>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<%= HtmlUtil.escape(PortalUtil.getUserName(message)) %>
			</c:otherwise>
		</c:choose>

		</a>
	</td>
	<td class="table-cell"></td>
	<td class="table-cell" nowrap="nowrap">
		<a href="<%= rowHREF %>">
		<%= dateFormatDateTime.format(message.getModifiedDate()) %>
		</a>
	</td>
</tr>

<%
List messages = treeWalker.getMessages();
int[] range = treeWalker.getChildrenRange(message);

depth++;

for (int i = range[0]; i < range[1]; i++) {
	MBMessage curMessage = (MBMessage)messages.get(i);

	boolean lastChildNode = false;

	if ((i + 1) == range[1]) {
		lastChildNode = true;
	}

	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, curMessage);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, new Integer(depth));
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, Boolean.valueOf(lastChildNode));
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, selMessage);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD, thread);
%>

	<liferay-util:include page="/html/taglib/ui/discussion/view_message_thread.jsp" />

<%
}
%>