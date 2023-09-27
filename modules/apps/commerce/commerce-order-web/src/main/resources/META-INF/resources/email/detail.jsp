<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceNotificationQueueEntry commerceNotificationQueueEntry = commerceOrderEditDisplayContext.getCommerceNotificationQueueEntry();
%>

<liferay-frontend:side-panel-content
	title="<%= commerceNotificationQueueEntry.getSubject() %>"
>
	<div class="commerce-notification-queue-entry-body">
		<%= HtmlUtil.escape(commerceNotificationQueueEntry.getBody()) %>
	</div>
</liferay-frontend:side-panel-content>