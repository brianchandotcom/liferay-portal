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
int abstractLength = (Integer)request.getAttribute(WebKeys.ASSET_PUBLISHER_ABSTRACT_LENGTH);

MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

String summary = message.getBody();

if (message.isFormatBBCode()) {
	summary = BBCodeTranslatorUtil.getHTML(summary);
	summary = StringUtil.replace(summary, "@theme_images_path@/emoticons", themeDisplay.getPathThemeImages() + "/emoticons");
}
%>

<%= StringUtil.shorten(HtmlUtil.stripHtml(summary), abstractLength) %>