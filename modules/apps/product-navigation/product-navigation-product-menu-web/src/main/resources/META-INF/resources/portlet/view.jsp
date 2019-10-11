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
String productMenuState = SessionClicks.get(request, "com.liferay.product.navigation.product.menu.web_productMenuState", "closed");
String pagesTreeState = SessionClicks.get(request, "com.liferay.product.navigation.product.menu.web_pagesTreeState", "closed");

String panelName = GetterUtil.getString(request.getAttribute(ProductNavigationProductMenuWebKeys.PANEL_NAME), ProductNavigationProductMenuWebKeys.PRODUCT_MENU);
%>

<div class="lfr-product-menu-sidebar" id="productMenuSidebar">
	<div class="sidebar-header">
		<h1 class="sr-only"><liferay-ui:message key="product-admin-menu" /></h1>

		<div class="autofit-row">
			<div class="autofit-col autofit-col-expand">
				<a href="<%= PortalUtil.addPreservedParameters(themeDisplay, themeDisplay.getURLPortal(), false, true) %>">
					<span class="company-details text-truncate">
						<img alt="" class="company-logo" src="<%= themeDisplay.getPathImage() + "/company_logo?img_id=" + company.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(company.getLogoId()) %>" />

						<span class="company-name"><%= HtmlUtil.escape(company.getName()) %></span>
					</span>
				</a>
			</div>

			<div class="autofit-col">
				<aui:icon cssClass="d-inline-block d-md-none icon-monospaced sidenav-close" image="times" markupView="lexicon" url="javascript:;" />
			</div>
		</div>
	</div>

	<div class="sidebar-body">
		<c:choose>
			<c:when test='<%= Objects.equals(productMenuState, "open") && Objects.equals(panelName, ProductNavigationProductMenuWebKeys.PRODUCT_MENU) %>'>
				<liferay-util:include page="/portlet/product_menu.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= Objects.equals(pagesTreeState, "open") && Objects.equals(panelName, ProductNavigationProductMenuWebKeys.PAGES_TREE) %>'>
				<liferay-util:include page="/portlet/pages_tree.jsp" servletContext="<%= application %>" />
			</c:when>
		</c:choose>
	</div>
</div>

<aui:script use="aui-base">
	var sidenavToggle = document.getElementById('<portlet:namespace />sidenavToggleId');
	var pagesTreeSidenavToggle = document.getElementById('<portlet:namespace />pagesTreeSidenavToggleId');

	if (sidenavToggle) {
		var sidenavInstance = Liferay.SideNavigation.initialize(sidenavToggle);

		Liferay.once(
			'screenLoad',
			function() {
				Liferay.SideNavigation.destroy(sidenavToggle);
				Liferay.SideNavigation.destroy(pagesTreeSidenavToggle);
			}
		);

		sidenavInstance.on(
			'closed.lexicon.sidenav',
			function(event) {
				Liferay.Util.Session.set('com.liferay.product.navigation.product.menu.web_productMenuState', 'closed');
			}
		);

		sidenavInstance.on(
			'open.lexicon.sidenav',
			function(event) {
				Liferay.Util.Session.set('com.liferay.product.navigation.product.menu.web_productMenuState', 'open');
			}
		);
	}

	if (pagesTreeSidenavToggle) {
		var pagesTreeSidenavInstance = Liferay.SideNavigation.initialize(pagesTreeSidenavToggle);

		if (sidenavInstance) {
			sidenavInstance.on(
				'open.lexicon.sidenav',
				function(event) {
					if (Liferay.Util.Session.set('com.liferay.product.navigation.product.menu.web_pagesTreeState') === 'open') {
						pagesTreeSidenavInstance.hideSimpleSidenav();
						Liferay.Util.Session.set('com.liferay.product.navigation.product.menu.web_pagesTreeState', 'closed');
					}
				}
			);
		}

		pagesTreeSidenavInstance.on(
			'closed.lexicon.sidenav',
			function(event) {
				Liferay.Util.Session.set('com.liferay.product.navigation.product.menu.web_pagesTreeState', 'closed');
			}
		);

		pagesTreeSidenavInstance.on(
			'open.lexicon.sidenav',
			function(event) {
				Liferay.Util.Session.set('com.liferay.product.navigation.product.menu.web_pagesTreeState', 'open');

				if (Liferay.Util.Session.set('com.liferay.product.navigation.product.menu.web_productMenuState') === 'open') {
					sidenavInstance.hideSimpleSidenav();
					Liferay.Util.Session.set('com.liferay.product.navigation.product.menu.web_productMenuState', 'closed');
				}
			}
		);
	}

	if (Liferay.Util.isPhone() && (document.body.classList.contains('open'))) {
		Liferay.SideNavigation.hide(sidenavToggle);
		Liferay.SideNavigation.hide(pagesTreeSidenavToggle);
	}
</aui:script>