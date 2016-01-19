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

<%@ include file="/portlet/init.jsp" %>

<%
Map<String, Object> data = new HashMap<String, Object>();

PortletURL simulationPanelURL = PortletURLFactoryUtil.create(request, ProductNavigationSimulationPortletKeys.PRODUCT_NAVIGATION_SIMULATION, plid, PortletRequest.RENDER_PHASE);

simulationPanelURL.setWindowState(LiferayWindowState.EXCLUSIVE);

data.put("panelURL", simulationPanelURL);

data.put("qa-id", "simulation");
%>

<div class="toolbar-group-content">
	<liferay-ui:icon
		cssClass="hidden-xs"
		data="<%= data %>"
		icon="simulation-menu-closed"
		id="simulationPanel"
		label="<%= false %>"
		linkCssClass="control-menu-icon"
		markupView="lexicon"
		message="simulation"
		url="javascript:;"
	/>
</div>

<aui:script position="auto" use="liferay-control-menu">
	var ControlMenu = Liferay.ControlMenu;

	ControlMenu.registerPanel(
		{
			css: 'lfr-has-simulation-panel',
			id: 'simulationPanel',
			layoutControl: '.page-preview-controls > a',
			node: null,
			showFn: A.bind('showPanel', ControlMenu),
			tpl: '<div class="lfr-admin-panel lfr-simulation-panel product-menu sidebar-inverse" id="{0}" />',
			trigger: A.one('#<portlet:namespace />simulationPanel')
		}
	);
</aui:script>