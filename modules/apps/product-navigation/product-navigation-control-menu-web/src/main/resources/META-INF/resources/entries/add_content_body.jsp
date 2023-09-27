<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

				<clay:button
					aria-label='<%= LanguageUtil.get(request, "close") %>'
					cssClass="sidenav-close text-secondary"
					displayType="unstyled"
					icon="times"
					small="<%= true %>"
				/>
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