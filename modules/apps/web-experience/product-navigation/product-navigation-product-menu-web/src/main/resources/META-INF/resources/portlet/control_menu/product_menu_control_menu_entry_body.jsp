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

<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %>
<%@ page import="com.liferay.portal.kernel.util.SessionClicks" %>
<%@ page import="com.liferay.product.navigation.product.menu.web.constants.ProductNavigationProductMenuPortletKeys" %>
<%@ page import="com.liferay.product.navigation.product.menu.web.constants.ProductNavigationProductMenuWebKeys" %>

<liferay-theme:defineObjects />

<%
String portletNamespace = PortalUtil.getPortletNamespace(ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU);

String productMenuState = SessionClicks.get(request, ProductNavigationProductMenuWebKeys.PRODUCT_NAVIGATION_PRODUCT_MENU_STATE, "closed");
%>

<liferay-util:body-bottom outputKey="productMenu">
	<div class="<%= productMenuState %> hidden-print lfr-product-menu-panel sidenav-fixed sidenav-menu-slider" id="<%= portletNamespace %>sidenavSliderId">
		<div class="product-menu sidebar sidenav-menu">
			<liferay-portlet:runtime portletName="<%= ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU %>" />
		</div>
	</div>
</liferay-util:body-bottom>