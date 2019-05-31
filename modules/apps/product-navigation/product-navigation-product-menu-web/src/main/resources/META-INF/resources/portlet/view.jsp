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
String productMenuState = SessionClicks.get(request, ProductNavigationProductMenuWebKeys.PRODUCT_NAVIGATION_PRODUCT_MENU_STATE, "closed");
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

<aui:script use="liferay-store,io-request,parse-content">
	var sidenavToggle = document.getElementById('<portlet:namespace />sidenavToggleId');

	SideNavigation.initialize(sidenavToggle);

	Liferay.once(
		'screenLoad',
		function() {
			SideNavigation.destroy(sidenavToggle);
		}
	);

	var sidenavSlider = $('#<portlet:namespace />sidenavSliderId');

	sidenavSlider.on(
		'closed.lexicon.sidenav',
		function(event) {
			Liferay.Store('<%= ProductNavigationProductMenuWebKeys.PRODUCT_NAVIGATION_PRODUCT_MENU_STATE %>', 'closed');
		}
	);

	sidenavSlider.on(
		'open.lexicon.sidenav',
		function(event) {
			Liferay.Store('<%= ProductNavigationProductMenuWebKeys.PRODUCT_NAVIGATION_PRODUCT_MENU_STATE %>', 'open');
		}
	);

	if (Liferay.Util.isPhone() && ($('body').hasClass('open'))) {
		SideNavigation.hide(sidenavToggle);
	}
</aui:script>