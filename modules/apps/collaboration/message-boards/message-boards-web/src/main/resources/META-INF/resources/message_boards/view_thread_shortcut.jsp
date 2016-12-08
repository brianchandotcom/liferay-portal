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
boolean editable = true;

MBTreeWalker treeWalker = (MBTreeWalker)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER);
MBMessage selMessage = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE);
MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE);
MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY);
MBThread thread = (MBThread)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD);
MBThreadFlag threadFlag = (MBThreadFlag)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD_FLAG);
boolean lastNode = ((Boolean)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE)).booleanValue();
int depth = ((Integer)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH)).intValue();

long threadFlagModifiedTime = 0;

if (threadFlag != null) {
	Date threadFlagModifiedDate = threadFlag.getModifiedDate();

	threadFlagModifiedTime = threadFlagModifiedDate.getTime();
}
%>

<c:if test="<%= (message.getMessageId() != selMessage.getMessageId()) || MBUtil.isViewableMessage(themeDisplay, message) %>">
	<tr>
		<td class="table-cell" style="padding-left: <%= depth > 0 ? depth * 10 : 5 %>px; width: 90%;" valign="middle">
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
			String messageURL = null;

			if (portletName.equals(MBPortletKeys.MESSAGE_BOARDS_ADMIN)) {
				PortletURL selMessageURL = renderResponse.createRenderURL();

				selMessageURL.setParameter("mvcRenderCommandName", "/message_boards/view_message");
				selMessageURL.setParameter("messageId", String.valueOf(selMessage.getMessageId()));

				messageURL = selMessageURL.toString();
			}
			else {
				String layoutFullURL = PortalUtil.getLayoutFullURL(themeDisplay);

				messageURL = layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "message_boards/view_message/" + selMessage.getMessageId();

				if (windowState.equals(WindowState.MAXIMIZED)) {
					messageURL += "/maximized";
				}
			}

			String rowHREF = "#" + renderResponse.getNamespace() + "message_" + message.getMessageId();

			rowHREF = messageURL + rowHREF;

			boolean readThread = true;

			if (themeDisplay.isSignedIn()) {
				Date messageModifiedDate = message.getModifiedDate();

				if (threadFlagModifiedTime < messageModifiedDate.getTime()) {
					readThread = false;
				}
			}
			%>

			<a href="<%= rowHREF %>">
				<c:if test="<%= !readThread %>">
					<strong>
				</c:if>

				<%= HtmlUtil.escape(message.getSubject()) %>

				<c:if test="<%= !readThread %>">
					</strong>
				</c:if>
			</a>
		</td>
		<td class="table-cell" style="white-space: nowrap;">
			<a href="<%= rowHREF %>">
				<c:if test="<%= !readThread %>">
					<strong>
				</c:if>

				<c:choose>
					<c:when test="<%= message.isAnonymous() %>">
						<liferay-ui:message key="anonymous" />
					</c:when>
					<c:otherwise>
						<%= HtmlUtil.escape(PortalUtil.getUserName(message)) %>
					</c:otherwise>
				</c:choose>

				<c:if test="<%= !readThread %>">
					</strong>
				</c:if>
			</a>
		</td>
		<td class="table-cell" style="white-space: nowrap;">
			<a href="<%= rowHREF %>"><%= dateFormatDateTime.format(message.getModifiedDate()) %></a>
		</td>
	</tr>
</c:if>

<%
List messages = treeWalker.getMessages();
int[] range = treeWalker.getChildrenRange(message);

depth++;

for (int i = range[0]; i < range[1]; i++) {
	MBMessage curMessage = (MBMessage)messages.get(i);

	if (!MBUtil.isViewableMessage(themeDisplay, curMessage, message)) {
		continue;
	}

	boolean lastChildNode = false;

	if ((i + 1) == range[1]) {
		lastChildNode = true;
	}

	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, curMessage);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, Integer.valueOf(depth));
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, Boolean.valueOf(lastChildNode));
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, selMessage);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD, thread);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD_FLAG, threadFlag);
%>

	<liferay-util:include page="/message_boards/view_thread_shortcut.jsp" servletContext="<%= application %>" />

<%
}
%>