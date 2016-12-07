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
	String portletNamespace = PortalUtil.getPortletNamespace(ProductNavigationControlMenuPortletKeys.PRODUCT_NAVIGATION_CONTROL_MENU);
	%>

	<div class="closed hidden-print lfr-add-panel lfr-admin-panel sidenav-fixed sidenav-menu-slider sidenav-right" id="<%= portletNamespace %>addPanelId">
		<div class="product-menu sidebar sidebar-inverse sidenav-menu">
			<div class="sidebar-header">
				<span><liferay-ui:message key="add" /></span>

				<aui:icon cssClass="icon-monospaced sidenav-close" image="times" markupView="lexicon" url="javascript:;" />
			</div>

			<div class="sidebar-body"></div>
		</div>
	</div>

	<aui:script use="liferay-store,io-request,parse-content">
		var addToggle = $('#<%= portletNamespace %>addToggleId');

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
	</aui:script>
</liferay-util:body-bottom>