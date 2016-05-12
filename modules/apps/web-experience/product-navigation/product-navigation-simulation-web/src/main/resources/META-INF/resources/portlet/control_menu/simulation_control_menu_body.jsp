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

<liferay-util:body-bottom outputKey="simulationMenu">

	<%
	String portletNamespace = PortalUtil.getPortletNamespace(ProductNavigationSimulationPortletKeys.PRODUCT_NAVIGATION_SIMULATION);
	%>

	<div class="closed lfr-admin-panel lfr-product-menu-panel lfr-simulation-panel sidenav-fixed sidenav-menu-slider sidenav-right" id="<%= portletNamespace %>simulationPanelId">
		<div class="product-menu sidebar sidebar-inverse sidenav-menu">
			<h4 class="sidebar-header">
				<span><liferay-ui:message key="simulation" /></span>

				<aui:icon cssClass="close icon-monospaced" id='<%= portletNamespace + "closeSimulationPanel" %>' image="times" markupView="lexicon" url="javascript:;" />
			</h4>

			<div class="sidebar-body"></div>
		</div>
	</div>

	<aui:script use="liferay-store,io-request,parse-content">
		var simulationToggle = $('#<%= portletNamespace %>simulationToggleId');

		simulationToggle.sideNavigation();

		A.one('#<%= portletNamespace %>closeSimulationPanel').on(
			'click',
			function(event) {
				simulationToggle.sideNavigation('hide');
			}
		);

		var simulationPanel = $('#<%= portletNamespace %>simulationPanelId');

		simulationPanel.on(
			'open.lexicon.sidenav',
			function(event) {
				Liferay.fire('SimulationMenu:openSimulationPanel');
			}
		);

		simulationPanel.on(
			'closed.lexicon.sidenav',
			function(event) {
				Liferay.fire('SimulationMenu:closeSimulationPanel');
			}
		);

		Liferay.once(
			'screenLoad',
			function() {
				var sideNavigation = simulationToggle.data('lexicon.sidenav');

				if (sideNavigation) {
					sideNavigation.destroy();
				}
			}
		);
	</aui:script>
</liferay-util:body-bottom>