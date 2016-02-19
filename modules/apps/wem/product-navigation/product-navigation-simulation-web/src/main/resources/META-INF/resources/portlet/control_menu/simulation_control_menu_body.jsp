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
	String namespace = PortalUtil.getPortletNamespace(ProductNavigationSimulationPortletKeys.PRODUCT_NAVIGATION_SIMULATION);
	%>

	<div class="closed lfr-admin-panel lfr-product-menu-panel lfr-simulation-panel sidenav-fixed sidenav-menu-slider sidenav-right" id="<%= namespace %>simulationPanelId">
		<div class="product-menu sidebar sidebar-body sidebar-inverse">
			<h4 class="sidebar-header">
				<span><liferay-ui:message key="simulation" /></span>

				<aui:icon cssClass="close icon-monospaced" id='<%= namespace + "closeSimulationPanel" %>' image="times" markupView="lexicon" url="javascript:;" />
			</h4>

			<div class="loading-animation"></div>
		</div>
	</div>

	<aui:script use="liferay-store,io-request,parse-content">
		var simulationToggle = $('#<%= namespace %>simulationToggleId');

		simulationToggle.sideNavigation();

		var simulationPanel = $('#<%= namespace %>simulationPanelId');

		simulationPanel.on(
			'urlLoaded.lexicon.sidenav',
			function() {
				simulationPanel.find('.loading-animation').remove();
			}
		);

		A.one('#<%= namespace %>closeSimulationPanel').on(
			'click',
			function(event) {
				simulationToggle.sideNavigation('hide');
			}
		);
	</aui:script>
</liferay-util:body-bottom>