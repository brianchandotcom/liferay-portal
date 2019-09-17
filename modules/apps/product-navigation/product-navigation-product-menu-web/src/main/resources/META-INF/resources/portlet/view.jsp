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
%>

<div class="lfr-product-menu-sidebar" id="productMenuSidebar">
	<div class="sidebar-header">
		<h1 class="sr-only"><liferay-ui:message key="product-admin-menu" /></h1>

		<div class="autofit-row">
			<div class="autofit-col autofit-col-expand">
				<a href="<%= PortalUtil.addPreservedParameters(themeDisplay, themeDisplay.getURLPortal(), false, true) %>">
					<span class="company-details truncate-text">
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
		<c:if test='<%= Objects.equals(productMenuState, "open") %>'>
			<liferay-util:include page="/portlet/product_menu.jsp" servletContext="<%= application %>" />
		</c:if>
	</div>
</div>

<aui:script use="aui-base">
	var sidenavToggle = document.getElementById('<portlet:namespace />sidenavToggleId');

	var sidenavInstance = Liferay.SideNavigation.initialize(sidenavToggle);

	Liferay.once(
		'screenLoad',
		function() {
			Liferay.SideNavigation.destroy(sidenavToggle);
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

	if (Liferay.Util.isPhone() && (document.body.classList.contains('open'))) {
		Liferay.SideNavigation.hide(sidenavToggle);
	}
</aui:script>