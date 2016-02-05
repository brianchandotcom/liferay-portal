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
String productMenuState = SessionClicks.get(request, "com.liferay.control.menu.web_productMenuState", "closed");
%>

<div id="productMenuSidebar">
	<h4 class="sidebar-header">
		<a href="<%= themeDisplay.getURLPortal() %>">
			<span class="company-details">
				<img alt="" class="company-logo" src="<%= themeDisplay.getRealCompanyLogo() %>" />
				<span class="company-name"><%= company.getName() %></span>
			</span>

			<aui:icon cssClass="icon-monospaced sidenav-close visible-xs-block" image="times" markupView="lexicon" url="javascript:;" />
		</a>
	</h4>

	<div class="sidebar-body">
		<c:if test='<%= Validator.equals(productMenuState, "open") %>'>
			<liferay-util:include page="/portlet/product_menu.jsp" servletContext="<%= application %>" />
		</c:if>
	</div>
</div>

<aui:script use="liferay-store,io-request,parse-content">
	var sidenavToggle = $('#sidenavToggleId');

	sidenavToggle.sideNavigation();

	var sidenavSlider = $('#sidenavSliderId');

	sidenavSlider.off('closed.lexicon.sidenav');
	sidenavSlider.off('open.lexicon.sidenav');

	sidenavSlider.on(
		'closed.lexicon.sidenav',
		function(event) {
			Liferay.Store('com.liferay.control.menu.web_productMenuState', 'closed');
		}
	);

	sidenavSlider.on(
		'open.lexicon.sidenav',
		function(event) {
			Liferay.Store('com.liferay.control.menu.web_productMenuState', 'open');
		}
	);

	<c:if test="<%= productMenuDisplayContext.hasUserPanelCategory() %>">
		Liferay.on(
			'ProductMenu:openUserMenu',
			function(event) {
				var userCollapseSelector = '#<portlet:namespace /><%= AUIUtil.normalizeId(PanelCategoryKeys.USER) %>Collapse';

				var showUserCollapse = function() {
					var userCollapse = $(userCollapseSelector);

					userCollapse.collapse({
						show: true,
						parent: '#<portlet:namespace />Accordion'
					});

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
					var urlLoadedState = sidenavToggle.data('url-loaded') ? sidenavToggle.data('url-loaded').state() : '';

					sidenavToggle.sideNavigation('show');

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
		);
	</c:if>
</aui:script>