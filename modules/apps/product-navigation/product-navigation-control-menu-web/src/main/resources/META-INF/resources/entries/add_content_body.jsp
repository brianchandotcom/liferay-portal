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

<liferay-util:body-bottom
	outputKey="addContentMenu"
>

	<%
	String portletNamespace = PortalUtil.getPortletNamespace(ProductNavigationControlMenuPortletKeys.PRODUCT_NAVIGATION_CONTROL_MENU);
	%>

	<div class="cadmin closed d-print-none lfr-add-panel lfr-admin-panel sidenav-fixed sidenav-menu-slider sidenav-right" id="<%= portletNamespace %>addPanelId">
		<div class="sidebar sidebar-inverse sidebar-light sidenav-menu">
			<div class="d-flex justify-content-between p-3 sidebar-header">
				<h1 class="sr-only"><liferay-ui:message key="widget-selection-panel" /></h1>

				<span class="font-weight-bold"><liferay-ui:message key="add" /></span>

				<a aria-label="<%= LanguageUtil.get(request, "close") %>" class="sidenav-close text-secondary" href="javascript:;">
					<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
				</a>
			</div>

			<div class="sidebar-body"></div>
		</div>
	</div>

	<aui:script>
		var addToggle = document.getElementById('<%= portletNamespace %>addToggleId');

		Liferay.SideNavigation.initialize(addToggle);

		Liferay.once('screenLoad', () => {
			Liferay.SideNavigation.destroy(addToggle);
		});
	</aui:script>
</liferay-util:body-bottom>