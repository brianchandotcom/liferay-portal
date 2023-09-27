<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewNotificationTemplatesDisplayContext viewNotificationTemplatesDisplayContext = (ViewNotificationTemplatesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<frontend-data-set:headless-display
	apiURL="<%= viewNotificationTemplatesDisplayContext.getAPIURL() %>"
	creationMenu="<%= viewNotificationTemplatesDisplayContext.getCreationMenu() %>"
	fdsActionDropdownItems="<%= viewNotificationTemplatesDisplayContext.getFDSActionDropdownItems() %>"
	formName="fm"
	id="<%= NotificationFDSNames.NOTIFICATION_TEMPLATES %>"
	style="fluid"
/>