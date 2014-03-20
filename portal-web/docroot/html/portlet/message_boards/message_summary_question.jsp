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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

MBMessage message = (MBMessage)objArray[0];
%>

<liferay-portlet:renderURL varImpl="viewMessage">
	<portlet:param name="struts_action" value="/message_boards/view_message" />
	<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
</liferay-portlet:renderURL>

<div class="question-subject">
	<a class="question-subject" href="<%= viewMessage.toString() %>"><%= HtmlUtil.escape(message.getSubject()) %></a>
</div>

<div class="summary">

	<%
	String msgBody = StringUtil.shorten(message.getBody(), 250);

	if (message.isFormatBBCode()) {
		msgBody = BBCodeTranslatorUtil.getHTML(msgBody);
		msgBody = StringUtil.replace(msgBody, "@theme_images_path@/emoticons", themeDisplay.getPathThemeImages() + "/emoticons");
	}
	%>

	<%= msgBody %>
</div>

<div class="tags">
	<liferay-ui:asset-tags-summary
		className="<%= MBMessage.class.getName() %>"
		classPK="<%= message.getMessageId() %>"
	/>
</div>