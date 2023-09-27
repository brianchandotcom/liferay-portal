<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(siteNavigationAdminDisplayContext.getSiteNavigationMenuName());
%>

<liferay-ui:success key="siteNavigationMenuItemsAdded" message='<%= GetterUtil.getString(SessionMessages.get(renderRequest, "siteNavigationMenuItemsAdded")) %>' />

<c:if test="<%= siteNavigationAdminDisplayContext.hasUpdatePermission() %>">
	<react:component
		componentId="siteNavigationMenuEditor"
		module="site_navigation_menu_editor/index"
		props="<%= siteNavigationAdminDisplayContext.getSiteNavigationContext() %>"
	/>
</c:if>