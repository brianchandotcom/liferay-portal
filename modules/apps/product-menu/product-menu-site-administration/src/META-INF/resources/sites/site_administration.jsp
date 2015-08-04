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
PanelCategory panelCategory = (PanelCategory)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY);
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

boolean containsPortlet = false;

if (Validator.isNotNull(themeDisplay.getPpid())) {
	PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(panelAppRegistry, panelCategoryRegistry);

	PanelCategory siteAdministrationPanelCategory = panelCategoryRegistry.getPanelCategory(PanelCategoryKeys.SITE_ADMINISTRATION);

	containsPortlet = panelCategoryHelper.containsPortlet(themeDisplay.getPpid(), siteAdministrationPanelCategory);
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

	<div class="toolbar">
		<div class="toolbar-group-field">
			<a class="icon-angle-left icon-monospaced" href="<%= selectSiteURL.toString() %>"></a>
		</div>
		<div class="toolbar-group-content">
			<%= themeDisplay.getScopeGroupName() %>
		</div>
	</div>
</c:if>

<liferay-application-list:panel panelCategory="<%= panelCategory %>" />