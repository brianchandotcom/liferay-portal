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

<liferay-portlet:renderURL portletName="<%= ProductNavigationSimulationPortletKeys.PRODUCT_NAVIGATION_SIMULATION %>" var="simulationPanelURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcPath" value="/portlet/view.jsp" />
</liferay-portlet:renderURL>

<%
Map<String, Object> data = new HashMap<String, Object>();

data.put("panelURL", simulationPanelURL);
data.put("qa-id", "simulation");
data.put("title", HtmlUtil.escape(LanguageUtil.get(request, "simulation")));
%>

<li>
	<aui:icon
		cssClass="control-menu-icon"
		data="<%= data %>"
		id="simulationPanel"
		image="simulation-menu-closed"
		label="simulation"
		markupView="lexicon"
		url="javascript:;"
	/>
</li>

<aui:script use="liferay-control-menu">
	var ControlMenu = Liferay.ControlMenu;

	ControlMenu.registerPanel(
		{
			css: 'lfr-has-simulation-panel',
			id: 'simulationPanel',
			layoutControl: '.page-preview-controls > a',
			node: null,
			showFn: A.bind('showPanel', ControlMenu),
			tpl: '<div class="lfr-admin-panel lfr-simulation-panel" id="{0}" />',
			trigger: A.one('#<portlet:namespace />simulationPanel')
		}
	);
</aui:script>