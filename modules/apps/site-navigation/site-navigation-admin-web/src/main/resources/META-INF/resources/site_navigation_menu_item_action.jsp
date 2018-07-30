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

<%@ include file="/init.jsp" %>

<%
long siteNavigationMenuItemId = GetterUtil.getLong(request.getAttribute("edit_site_navigation_menu.jsp-siteNavigationMenuItemId"));
%>

<portlet:actionURL copyCurrentRenderParameters="<%= false %>" name="/navigation_menu/delete_site_navigation_menu_item" var="deleteURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="siteNavigationMenuItemId" value="<%= String.valueOf(siteNavigationMenuItemId) %>" />
</portlet:actionURL>

<liferay-ui:icon
	icon="times-circle"
	linkCssClass="icon-monospaced site-navigation-menu-item__remove-icon text-default"
	markupView="lexicon"
	url="<%= deleteURL %>"
/>