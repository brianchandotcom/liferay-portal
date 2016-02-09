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

<div class="closed lfr-add-panel lfr-admin-panel lfr-product-menu-panel sidenav-fixed sidenav-menu-slider sidenav-right" id="addPanelId">
	<div class="product-menu sidebar sidebar-body sidebar-inverse">
		<h4 class="sidebar-header">
			<span><liferay-ui:message key="add" /></span>

			<aui:icon cssClass="close" id="closePanelAdd" image="times" markupView="lexicon" url="javascript:;" />
		</h4>

		<div class="loading-animation"></div>
	</div>
</div>

<aui:script use="liferay-store,io-request,parse-content">
	var addToggle = $('#addToggleId');

	addToggle.sideNavigation();

	var addPanel = $('#addPanelId');

	addPanel.on(
		'urlLoaded.lexicon.sidenav',
		function() {
			addPanel.find('.loading-animation').remove();
		}
	);

	A.one('#<portlet:namespace />closePanelAdd').on(
		'click',
		function(event) {
			addToggle.sideNavigation('hide');
		}
	);
</aui:script>