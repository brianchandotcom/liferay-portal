<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewNotificationQueueEntriesDisplayContext viewNotificationQueueEntriesDisplayContext = (ViewNotificationQueueEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<frontend-data-set:headless-display
	apiURL="<%= viewNotificationQueueEntriesDisplayContext.getAPIURL() %>"
	fdsActionDropdownItems="<%= viewNotificationQueueEntriesDisplayContext.getFDSActionDropdownItems() %>"
	formName="fm"
	id="<%= NotificationFDSNames.NOTIFICATION_QUEUE_ENTRIES %>"
	propsTransformer="js/components/FDSPropsTransformer/NotificationQueueEntryFDSPropsTransformer"
	style="fluid"
/>