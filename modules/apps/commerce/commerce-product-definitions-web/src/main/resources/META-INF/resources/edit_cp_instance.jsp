<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPInstanceDisplayContext cpInstanceDisplayContext = (CPInstanceDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPInstance cpInstance = cpInstanceDisplayContext.getCPInstance();
%>

<liferay-frontend:side-panel-content
	screenNavigatorKey="<%= CPInstanceScreenNavigationConstants.SCREEN_NAVIGATION_KEY_CP_INSTANCE_GENERAL %>"
	screenNavigatorModelBean="<%= cpInstance %>"
	screenNavigatorPortletURL="<%= currentURLObj %>"
	title='<%= (cpInstance == null) ? LanguageUtil.get(request, "add-sku") : cpInstance.getSku() %>'
/>