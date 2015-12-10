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

<%@ include file="/bookmarks/init.jsp" %>

<%
BookmarksPortletToolbarContributor bookmarksPortletToolbarContributor = (BookmarksPortletToolbarContributor)request.getAttribute(BookmarksWebKeys.BOOKMARKS_PORTLET_TOOLBAR_CONTRIBUTOR);

List<Menu> menus = bookmarksPortletToolbarContributor.getPortletTitleMenus(renderRequest);
%>

<div id="addButtonContainer">

	<%
	for (Menu menu : menus) {
		List<URLMenuItem> urlMenuItems = (List<URLMenuItem>)(List<?>)menu.getMenuItems();

		List<AddMenuItem> addMenuItems = new ArrayList<AddMenuItem>();

		for (URLMenuItem urlMenuItem : urlMenuItems) {
			addMenuItems.add(new AddMenuItem(urlMenuItem.getLabel(), urlMenuItem.getURL()));

		}
	%>

		<liferay-frontend:add-menu addMenuItems="<%= addMenuItems %>" />

	<%
	}
	%>

</div>