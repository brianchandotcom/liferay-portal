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

<%@ include file="/init.jsp" %>

<%
PortletURL addPanelURL = PortletURLFactoryUtil.create(request, ControlMenuPortletKeys.CONTROL_MENU, plid, PortletRequest.RENDER_PHASE);

addPanelURL.setParameter("mvcPath", "/add_panel.jsp");
addPanelURL.setParameter("stateMaximized", String.valueOf(themeDisplay.isStateMaximized()));
addPanelURL.setWindowState(LiferayWindowState.EXCLUSIVE);
%>

<li class="control-menu-nav-item">
	<a class="control-menu-icon product-menu-toggle sidenav-toggler" data-content="body" data-open-class="open-admin-panel" data-qa-id="add" data-target="#addPanelId,#wrapper" data-title="<%= HtmlUtil.escape(LanguageUtil.get(request, "add")) %>" data-toggle="sidenav" data-type="fixed-push" data-type-mobile="fixed" data-url="<%= addPanelURL.toString() %>" href="javascript:;" id="addToggleId" onmouseover="Liferay.Portal.ToolTip.show(this, '<%= HtmlUtil.escapeJS(LanguageUtil.get(request, "add")) %>')">
		<aui:icon image="plus" markupView="lexicon" />
	</a>
</li>

<div class="closed lfr-add-panel lfr-admin-panel lfr-product-menu-panel sidenav-fixed sidenav-menu-slider sidenav-right" id="addPanelId">
	<div class="product-menu sidebar sidebar-body sidebar-inverse"></div>
</div>

<aui:script use="liferay-store,io-request,parse-content">
	var addToggle = $('#addToggleId');

	addToggle.sideNavigation();
</aui:script>