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

<li class="<%= Validator.equals(productMenuState, "open") ? "active" : StringPool.BLANK %>">
	<a class="control-menu-icon sidenav-toggler" data-content="body" data-qa-id="productMenu" data-target="#sidenavSliderId,#wrapper" data-title="<%= HtmlUtil.escape(LanguageUtil.get(request, "menu")) %>" data-toggle="sidenav" data-type="fixed-push" data-type-mobile="fixed" href="#sidenavSliderId" id="sidenavToggleId">
		<div class="toast-animation">
			<div class="pm"></div>

			<div class="cn"></div>
		</div>
	</a>
</li>

<%
String controlMenuPortletId = PortletProviderUtil.getPortletId(PortalControlMenuApplicationType.ControlMenu.CLASS_NAME, PortletProvider.Action.VIEW);
%>

<c:if test="<%= Validator.isNotNull(controlMenuPortletId) %>">
	<aui:script>
		Liferay.on(
			'<%= controlMenuPortletId %>:portletRefreshed',
			function(event) {
				Liferay.Portlet.refresh('#p_p_id_<%= ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU %>_', {});
			}
		);
	</aui:script>
</c:if>