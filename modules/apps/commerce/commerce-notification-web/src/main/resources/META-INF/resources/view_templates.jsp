<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceNotificationQueueEntriesDisplayContext commerceNotificationQueueEntriesDisplayContext = (CommerceNotificationQueueEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<frontend-data-set:classic-display
	contextParams='<%=
		HashMapBuilder.<String, String>put(
			"commerceChannelId", String.valueOf(commerceNotificationQueueEntriesDisplayContext.getCommerceChannelId())
		).build()
	%>'
	creationMenu="<%= commerceNotificationQueueEntriesDisplayContext.getNotificationTemplateCreationMenu() %>"
	dataProviderKey="<%= CommerceNotificationFDSNames.NOTIFICATION_TEMPLATES %>"
	id="<%= CommerceNotificationFDSNames.NOTIFICATION_TEMPLATES %>"
	itemsPerPage="<%= 10 %>"
	showSearch="<%= false %>"
/>