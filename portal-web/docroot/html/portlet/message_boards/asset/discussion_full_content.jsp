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
String randomNamespace = PortalUtil.generateRandomKey(request, "discussion_full_content") + StringPool.UNDERLINE;

MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

MBThread thread = message.getThread();

MBMessage rootMessage = MBMessageLocalServiceUtil.getMessage(thread.getRootMessageId());
MBMessage parentMessage = MBMessageLocalServiceUtil.getMessage(message.getParentMessageId());
%>

<table class="lfr-grid lfr-table">
<tr>
	<td colspan="2" id="<%= randomNamespace %>messageScroll<%= message.getMessageId() %>">
		<a name="<%= randomNamespace %>message_<%= message.getMessageId() %>"></a>
	</td>
</tr>
<tr>
	<td class="lfr-center lfr-top">
		<liferay-ui:user-display
			displayStyle="<%= 2 %>"
			userId="<%= message.getUserId() %>"
			userName="<%= HtmlUtil.escape(message.getUserName()) %>"
		/>
	</td>
	<td class="lfr-top stretch">
		<c:if test="<%= (message != null) && !message.isApproved() %>">
			<aui:model-context bean="<%= message %>" model="<%= MBMessage.class %>" />

			<div>
				<aui:workflow-status status="<%= message.getStatus() %>" />
			</div>
		</c:if>

		<div>
			<%= message.getBody() %>
		</div>

		<br />

		<div>
			<c:if test="<%= message.getParentMessageId() == rootMessage.getMessageId() %>">
				<%= LanguageUtil.format(pageContext, "posted-on-x", dateFormatDateTime.format(message.getModifiedDate()), false) %>
			</c:if>
		</div>
	</td>
</tr>
</table>

<c:if test="<%= (parentMessage != null) && !parentMessage.isRoot() %>">
	<h3><liferay-ui:message key="replying-to" />:</h3>

	<%
	request.setAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE, parentMessage);
	%>

	<liferay-util:include page="/html/portlet/message_boards/asset/discussion_full_content.jsp" />
</c:if>