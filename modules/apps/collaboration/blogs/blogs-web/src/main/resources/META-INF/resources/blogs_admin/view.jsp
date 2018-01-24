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

<%@ include file="/blogs_admin/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "entries");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/blogs/view");
portletURL.setParameter("navigation", navigation);

List<NavigationItem> navigationItems = new ArrayList<>();

NavigationItem entriesNavigationItem = new NavigationItem();

entriesNavigationItem.setActive(navigation.equals("entries"));

PortletURL viewEntriesURL = renderResponse.createRenderURL();

entriesNavigationItem.setHref(viewEntriesURL.toString());

entriesNavigationItem.setLabel(LanguageUtil.get(request, "entries"));

navigationItems.add(entriesNavigationItem);

NavigationItem imagesNavigationItem = new NavigationItem();

imagesNavigationItem.setActive(navigation.equals("images"));

PortletURL viewImagesURL = renderResponse.createRenderURL();

viewImagesURL.setParameter("navigation", "images");

imagesNavigationItem.setHref(viewImagesURL.toString());

imagesNavigationItem.setLabel(LanguageUtil.get(request, "images"));

navigationItems.add(imagesNavigationItem);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= navigationItems %>"
/>

<c:choose>
	<c:when test='<%= navigation.equals("entries") %>'>
		<liferay-util:include page="/blogs_admin/view_entries.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/blogs_admin/view_images.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>