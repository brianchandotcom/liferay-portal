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

<%@ include file="/html/taglib/ui/portlet_toolbar_menu/init.jsp" %>

<%
List<PortletToolbarMenuItem> portletToolbarMenuItems = (List<PortletToolbarMenuItem>)request.getAttribute("liferay-ui:portlet_toolbar_menu:portletToolbarMenuItems");
%>

<liferay-ui:icon-menu cssClass="portlet-options" direction="down" extended="<%= false %>" icon="../aui/plus-sign-2" message="add" showArrow="<%= true %>" showWhenSingleIcon="<%= true %>">

	<%
	for (PortletToolbarMenuItem portletToolbarMenuItem : portletToolbarMenuItems) {
		MenuItem menuItem = portletToolbarMenuItem.getMenuItem(renderRequest);
	%>

		<c:if test="<%= menuItem != null %>">
			<liferay-ui:menu-item menuItem="<%= menuItem %>" />
		</c:if>

	<%
	}
	%>

</liferay-ui:icon-menu>