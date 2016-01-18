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

PortletURL portletURL = PortletURLFactoryUtil.create(request, ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU, plid, RenderRequest.RENDER_PHASE);

portletURL.setParameter("mvcPath", "/portlet/product_menu.jsp");
portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
%>

<div class="toolbar-group-content <%= Validator.equals(productMenuState, "open") ? "active" : StringPool.BLANK %>">
	<a class="control-menu-icon product-menu-toggle sidenav-toggler" data-content="body" data-panelurl="<%= portletURL.toString() %>" data-qa-id="productMenu" data-target="#sidenavSliderId,#wrapper" data-title="<%= HtmlUtil.escape(LanguageUtil.get(request, "menu")) %>" data-toggle="sidenav" data-type="fixed-push" data-type-mobile="fixed" href="javascript:;" id="sidenavToggleId" onmouseover="Liferay.Portal.ToolTip.show(this, '<%= HtmlUtil.escapeJS(LanguageUtil.get(request, "menu")) %>')">
		<div class="toast-animation">
			<div class="pm"></div>

			<div class="cn"></div>
		</div>
	</a>
</div>