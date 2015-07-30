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

<%@ include file="/sites/init.jsp" %>

<%
PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);
PanelCategory panelCategory = (PanelCategory)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY);

String portletId = themeDisplay.getPpid();

boolean containsPortlet = false;

if (Validator.isNotNull(portletId)) {
	PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(panelAppRegistry, panelCategoryRegistry);

	PanelCategory siteAdministrationPanelCategory = panelCategoryRegistry.getPanelCategory(PanelCategoryKeys.SITE_ADMINISTRATION);

	containsPortlet = panelCategoryHelper.containsPortlet(portletId, siteAdministrationPanelCategory);
}

Group group = layout.getGroup();

if (layout instanceof VirtualLayout) {
	VirtualLayout virtualLayout = (VirtualLayout)layout;

	Layout sourceLayout = virtualLayout.getSourceLayout();

	group = sourceLayout.getGroup();
}

boolean showSiteSelector = ParamUtil.getBoolean(request, "showSiteSelector", group.isControlPanel() && !containsPortlet);

if (showSiteSelector) {
	panelCategory = panelCategoryRegistry.getPanelCategory(PanelCategoryKeys.SITES);
}
%>

<c:if test="<%= !showSiteSelector %>">

	<%
	String selectSiteURL = HttpUtil.addParameter(currentURL, liferayPortletResponse.getNamespace() + "showSiteSelector", true);
	%>

	<aui:icon image="arrow-left" label="<%= themeDisplay.getScopeGroupName() %>" url="<%= selectSiteURL.toString() %>" />
</c:if>

<liferay-application-list:panel panelCategory="<%= panelCategory %>" />