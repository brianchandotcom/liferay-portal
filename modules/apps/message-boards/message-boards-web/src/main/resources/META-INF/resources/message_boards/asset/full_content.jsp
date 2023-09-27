<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/message_boards/init.jsp" %>

<%
MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

request.setAttribute("edit-message.jsp-showPermanentLink", Boolean.FALSE);
request.setAttribute("edit-message.jsp-showRecentPosts", Boolean.FALSE);
request.setAttribute("edit_message.jsp-category", message.getCategory());
request.setAttribute("edit_message.jsp-editable", Boolean.FALSE);
request.setAttribute("edit_message.jsp-message", message);
request.setAttribute("edit_message.jsp-thread", message.getThread());
%>

<liferay-util:include page="/message_boards/view_thread_message.jsp" servletContext="<%= application %>" />