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
		<a href="<%= PortalUtil.addPreservedParameters(themeDisplay, themeDisplay.getURLPortal(), false, true) %>">
			<span class="company-details">
				<img alt="" class="company-logo" src="<%= themeDisplay.getRealCompanyLogo() %>" />
				<span class="company-name"><%= company.getName() %></span>
			</span>

			<aui:icon cssClass="icon-monospaced sidenav-close visible-xs-block" image="times" markupView="lexicon" url="javascript:;" />
		</a>
	</div>

	<div class="sidebar-body">
		<c:if test='<%= Objects.equals(productMenuState, "open") %>'>
			<liferay-util:include page="/portlet/product_menu.jsp" servletContext="<%= application %>" />
		</c:if>
	</div>
</div>

<aui:script use="liferay-store,io-request,parse-content">
	var sidenavToggle = $('#<portlet:namespace />sidenavToggleId');

	sidenavToggle.sideNavigation();

	Liferay.once(
		'screenLoad',
		function() {
			var sideNavigation = sidenavToggle.data('lexicon.sidenav');

			if (sideNavigation) {
				sideNavigation.destroy();
			}
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
		sidenavToggle.sideNavigation('hide');
	}

	<c:if test="<%= productMenuDisplayContext.hasUserPanelCategory() %>">
		Liferay.on(
			'ProductMenu:openUserMenu',
			function(event) {
				var userCollapseSelector = '#<portlet:namespace /><%= AUIUtil.normalizeId(PanelCategoryKeys.USER) %>Collapse';

				var showUserCollapse = function() {
					var userCollapse = $(userCollapseSelector);

					userCollapse.collapse(
						{
							parent: '#<portlet:namespace />Accordion',
							show: true
						}
					);

					userCollapse.collapse('show');
				};

				if ($('body').hasClass('open')) {
					if ($(userCollapseSelector).hasClass('in')) {
						sidenavToggle.sideNavigation('hide');
					}
					else {
						showUserCollapse();
					}
				}
				else {
					sidenavToggle.sideNavigation('show');

					if (!sidenavToggle.attr('data-url')) {
						showUserCollapse();
					}
					else {
						var urlLoadedState = sidenavSlider.data('url-loaded') ? sidenavSlider.data('url-loaded').state() : '';

						if (urlLoadedState === 'resolved') {
							showUserCollapse();
						}
						else {
							sidenavSlider.on(
								'urlLoaded.lexicon.sidenav',
								function(event) {
									showUserCollapse();
								}
							);
						}
					}
				}
			}
		);
	</c:if>
</aui:script>