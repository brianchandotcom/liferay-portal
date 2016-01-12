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
MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute(WebKeys.ASSET_RENDERER);

request.setAttribute("edit_message.jsp-category", message.getCategory());
request.setAttribute("edit_message.jsp-editable", Boolean.FALSE);
request.setAttribute("edit_message.jsp-message", message);
request.setAttribute("edit-message.jsp-showDeletedAttachmentsFileEntries", Boolean.FALSE);
request.setAttribute("edit-message.jsp-showPermanentLink", Boolean.FALSE);
request.setAttribute("edit-message.jsp-showRecentPosts", Boolean.FALSE);
request.setAttribute("edit_message.jsp-thread", message.getThread());
%>

<liferay-util:include page="/message_boards/view_thread_message.jsp" servletContext="<%= application %>" />

<c:if test="<%= assetRenderer != null %>">
	<div class="asset-more">
		<aui:a href="<%= assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, null) %>"><liferay-ui:message key="view-in-context" /> &raquo;</aui:a>
	</div>
</c:if>