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
MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

String body = StringPool.BLANK;

if (message.isFormatBBCode()) {
	body = BBCodeTranslatorUtil.getHTML(message.getBody());
	body = StringUtil.replace(body, "@theme_images_path@/emoticons", themeDisplay.getPathThemeImages() + "/emoticons");
}
else{
	body = message.getBody();
}
%>

<%= body %>

<liferay-ui:custom-attributes-available className="<%= MBMessage.class.getName() %>">
	<liferay-ui:custom-attribute-list
		className="<%= MBMessage.class.getName() %>"
		classPK="<%= (message != null) ? message.getMessageId() : 0 %>"
		editable="<%= false %>"
		label="<%= true %>"
	/>
</liferay-ui:custom-attributes-available>