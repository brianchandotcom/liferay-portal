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

<div class="<%= Validator.equals(productMenuState, "open") ? "content-loaded" : StringPool.BLANK %>" id="productMenuSidebar">
	<c:choose>
		<c:when test='<%= Validator.equals(productMenuState, "open") %>'>
			<liferay-util:include page="/portlet/product_menu.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
			<div class="loading-animation"></div>
		</c:otherwise>
	</c:choose>
</div>

<aui:script use="liferay-store,io-request,parse-content">
	var sidenavToggle = $('#sidenavToggleId');

	sidenavToggle.sideNavigation();

	var loadPanelContent = function(container, uri) {
		if (uri) {
			A.io.request(
				uri,
				{
					after: {
						success: function(event, id, obj) {
							var response = this.get('responseData');

							var productMenuSidebar = A.one('#productMenuSidebar');

							container.plug(A.Plugin.ParseContent);

							container.setContent(response);
							container.addClass('content-loaded');

							Liferay.fire('ProductMenu:contentLoaded');
						}
					}
				}
			);
		}
	};

	var sidenavSlider = $('#sidenavSliderId');

	sidenavSlider.off('closed.lexicon.sidenav');
	sidenavSlider.off('open.lexicon.sidenav');

	sidenavSlider.on(
		'closed.lexicon.sidenav',
		function(event) {
			Liferay.Store('com.liferay.control.menu.web_productMenuState', 'closed');
		}
	);

	var productMenuSidebar = A.one('#productMenuSidebar');

	sidenavSlider.on(
		'open.lexicon.sidenav',
		function(event) {
	 		if (productMenuSidebar && !productMenuSidebar.hasClass('content-loaded')) {
				loadPanelContent(productMenuSidebar, sidenavToggle.attr('data-panelurl'));
	 		}

			Liferay.Store('com.liferay.control.menu.web_productMenuState', 'open');
		}
	);

	Liferay.on(
		'ProductMenu:openUserMenu',
		function(event) {
			var userCollapse = $('#<portlet:namespace /><%= AUIUtil.normalizeId(PanelCategoryKeys.USER) %>Collapse');

			if ($('body').hasClass('open')) {
				if (userCollapse.hasClass('in')) {
					userCollapse.collapse('hide');

					sidenavToggle.sideNavigation('hide');
				}
				else {
					userCollapse.collapse('show');
				}
			}
			else {
				sidenavToggle.sideNavigation('show');

				if (productMenuSidebar.hasClass('content-loaded')) {
					userCollapse.collapse('show');
				}
				else {
					Liferay.on(
						'ProductMenu:contentLoaded',
						function(event) {
							userCollapse = $('#<portlet:namespace /><%= AUIUtil.normalizeId(PanelCategoryKeys.USER) %>Collapse');

							userCollapse.collapse('show');
						}
					);
				}
			}
		}
	);
</aui:script>