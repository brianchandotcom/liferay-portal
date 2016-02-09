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

<div class="closed lfr-admin-panel lfr-product-menu-panel lfr-simulation-panel sidenav-fixed sidenav-menu-slider sidenav-right" id="simulationPanelId">
	<div class="product-menu sidebar sidebar-body sidebar-inverse">
		<h4 class="sidebar-header">
			<span><liferay-ui:message key="simulation" /></span>

			<aui:icon cssClass="close" id="closeSimulationPanel" image="times" markupView="lexicon" url="javascript:;" />
		</h4>

		<div class="loading-animation"></div>
	</div>
</div>

<aui:script use="liferay-store,io-request,parse-content">
	var simulationToggle = $('#simulationToggleId');

	simulationToggle.sideNavigation();

	var simulationPanel = $('#simulationPanelId');

	simulationPanel.on(
		'urlLoaded.lexicon.sidenav',
		function() {
			simulationPanel.find('.loading-animation').remove();
		}
	);

	A.one('#<portlet:namespace />closeSimulationPanel').on(
		'click',
		function(event) {
			simulationToggle.sideNavigation('hide');
		}
	);
</aui:script>