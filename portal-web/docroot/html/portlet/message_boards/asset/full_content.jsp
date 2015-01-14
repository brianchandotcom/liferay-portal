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
MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

request.setAttribute("edit_message.jsp-category", message.getCategory());
request.setAttribute("edit_message.jsp-className", message.getClassName());
request.setAttribute("edit_message.jsp-depth", 0);
request.setAttribute("edit_message.jsp-editable", Boolean.FALSE);
request.setAttribute("edit_message.jsp-message", message);
request.setAttribute("edit-message.jsp-showDeletedAttachmentsFileEntries", Boolean.FALSE);
request.setAttribute("edit-message.jsp-showPermanentLink", Boolean.FALSE);
request.setAttribute("edit-message.jsp-showRecentPosts", Boolean.FALSE);
request.setAttribute("edit_message.jsp-thread", message.getThread());
%>

<liferay-util:include page="/html/portlet/message_boards/view_thread_message.jsp" />

<c:if test="<%= portletName.equals(PortletKeys.TRASH) %>">

	<%
	MBThread thread = message.getThread();

	PortletURL viewContentURL = renderResponse.createRenderURL();

	viewContentURL.setParameter("struts_action", "/trash/view_content");
	viewContentURL.setParameter("redirect", currentURL);
	viewContentURL.setParameter("className", MBThread.class.getName());
	viewContentURL.setParameter("classPK", String.valueOf(thread.getPrimaryKey()));
	viewContentURL.setParameter("showActions", Boolean.FALSE.toString());
	viewContentURL.setParameter("showAssetMetadata", Boolean.TRUE.toString());
	viewContentURL.setParameter("showEditURL", Boolean.FALSE.toString());
	%>

	<div class="asset-more">
		<a href="<%= viewContentURL.toString() %>"><liferay-ui:message key="view-in-context" /> &raquo;</a>
	</div>
</c:if>