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

<liferay-util:body-bottom outputKey="addContentMenu">

	<%
	String namespace = PortalUtil.getPortletNamespace(ProductNavigationControlMenuPortletKeys.PRODUCT_NAVIGATION_CONTROL_MENU);
	%>

	<div class="closed lfr-add-panel lfr-admin-panel lfr-product-menu-panel sidenav-fixed sidenav-menu-slider sidenav-right" id="<%= namespace %>addPanelId">
		<div class="product-menu sidebar sidebar-body sidebar-inverse">
			<h4 class="sidebar-header">
				<span><liferay-ui:message key="add" /></span>

				<aui:icon cssClass="close icon-monospaced" id='<%= namespace + "closePanelAdd" %>' image="times" markupView="lexicon" url="javascript:;" />
			</h4>

			<div class="loading-animation"></div>
		</div>
	</div>

	<aui:script use="liferay-store,io-request,parse-content">
		var addToggle = $('#<%= namespace %>addToggleId');

		addToggle.sideNavigation();

		Liferay.once(
			'screenLoad',
			function() {
				var sideNavigation = addToggle.data('lexicon.sidenav');

				if (sideNavigation) {
					sideNavigation.destroy();
				}
			}
		);

		var addPanel = $('#<%= namespace %>addPanelId');

		addPanel.on(
			'urlLoaded.lexicon.sidenav',
			function() {
				addPanel.find('.loading-animation').remove();
			}
		);

		A.one('#<%= namespace %>closePanelAdd').on(
			'click',
			function(event) {
				addToggle.sideNavigation('hide');
			}
		);
	</aui:script>
</liferay-util:body-bottom>