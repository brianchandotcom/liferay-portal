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
String controlMenuPortletId = ControlMenuPortletKeys.CONTROL_MENU;
String portletId = ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU;
String productMenuState = SessionClicks.get(request, "com.liferay.control.menu.web_productMenuState", "closed");
%>

<li class="<%= Validator.equals(productMenuState, "open") ? "active" : StringPool.BLANK %>">
	<a class="control-menu-icon sidenav-toggler" data-content="body" data-toggle="sidenav" data-type="fixed-push" data-type-mobile="fixed" href="#sidenavSliderId" id="sidenavToggleId">
		<span class="icon-align-justify icon-monospaced"></span>
	</a>
</li>

<aui:script>
	Liferay.on(
		'<%= controlMenuPortletId %>:portletRefreshed',
		function(event) {
			Liferay.Portlet.refresh('#p_p_id_<%= portletId %>_', {});
		}
	);
</aui:script>