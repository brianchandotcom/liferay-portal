<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
boolean editable = true;

MBTreeWalker treeWalker = (MBTreeWalker)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER);
MBMessage selMessage = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE);
MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE);
MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY);
MBThread thread = (MBThread)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD);
MBThreadFlag threadFlag = (MBThreadFlag)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD_FLAG);
boolean lastNode = ((Boolean)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE)).booleanValue();
int depth = ((Integer)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH)).intValue();

long lastReadTime = 0;

if (threadFlag != null) {
	lastReadTime = threadFlag.getModifiedDate().getTime();
}

String className = "portlet-section-alternate results-row alt";
String classHoverName = "portlet-section-alternate-hover results-row alt hover";

if (treeWalker.isOdd()) {
	className = "portlet-section-body results-row";
	classHoverName = "portlet-section-body-hover results-row hover";
}
%>

<tr class="<%= className %>" onMouseEnter="this.className = '<%= classHoverName %>';" onMouseLeave="this.className = '<%= className %>';">
	<td style="padding-left: <%= depth > 0 ? depth * 10 : 5 %>px; width: 90%;" valign="middle">
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
		String layoutFullURL = PortalUtil.getLayoutFullURL(themeDisplay);

		String messageURL = layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "message_boards/view_message/" + selMessage.getMessageId();

		if (windowState.equals(WindowState.MAXIMIZED)) {
			messageURL += "/maximized";
		}

		String rowHREF = "#" + renderResponse.getNamespace() + "message_" + message.getMessageId();

		if (!themeDisplay.isFacebook()) {
			rowHREF = messageURL + rowHREF;
		}

		boolean readFlag = true;

		if (themeDisplay.isSignedIn() && (lastReadTime < message.getModifiedDate().getTime())) {
			readFlag = false;
		}
		%>

		<a href="<%= rowHREF %>">
			<c:if test="<%= !readFlag %>">
				<strong>
			</c:if>

			<%= HtmlUtil.escape(message.getSubject()) %>

			<c:if test="<%= !readFlag %>">
				</strong>
			</c:if>
		</a>
	</td>
	<td style="white-space: nowrap;">
		<a href="<%= rowHREF %>">
			<c:if test="<%= !readFlag %>">
				<strong>
			</c:if>

			<c:choose>
				<c:when test="<%= message.isAnonymous() %>">
					<liferay-ui:message key="anonymous" />
				</c:when>
				<c:otherwise>
					<%= HtmlUtil.escape(PortalUtil.getUserName(message.getUserId(), message.getUserName())) %>
				</c:otherwise>
			</c:choose>

			<c:if test="<%= !readFlag %>">
				</strong>
			</c:if>
		</a>
	</td>
	<td style="white-space: nowrap;">
		<a href="<%= rowHREF %>"><%= dateFormatDateTime.format(message.getModifiedDate()) %></a>
	</td>
</tr>

<%
List messages = treeWalker.getMessages();
int[] range = treeWalker.getChildrenRange(message);

depth++;

for (int i = range[0]; i < range[1]; i++) {
	MBMessage curMessage = (MBMessage)messages.get(i);

	if ((!curMessage.isApproved() && (curMessage.getUserId() != user.getUserId()) && !permissionChecker.isGroupAdmin(scopeGroupId)) || !MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW)) {
		continue;
	}

	boolean lastChildNode = false;

	if ((i + 1) == range[1]) {
		lastChildNode = true;
	}

	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, selMessage);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, curMessage);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD, thread);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD_FLAG, threadFlag);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, Boolean.valueOf(lastChildNode));
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, new Integer(depth));
%>

	<liferay-util:include page="/html/portlet/message_boards/view_thread_shortcut.jsp" />

<%
}
%>