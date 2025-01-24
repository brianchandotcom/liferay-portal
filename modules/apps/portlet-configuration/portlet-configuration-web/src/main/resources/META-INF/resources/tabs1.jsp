<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
PortletConfigurationDisplayContext portletConfigurationDisplayContext = new PortletConfigurationDisplayContext(request, renderResponse);

PortalUtil.addPortletBreadcrumbEntry(request, PortalUtil.getPortletTitle(renderResponse), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "configuration"), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, portletConfigurationDisplayContext.getTabs1()), currentURL);
%>

<div class="cadmin">
	<clay:navigation-bar
		navigationItems="<%= portletConfigurationDisplayContext.getNavigationItems() %>"
	/>
</div>