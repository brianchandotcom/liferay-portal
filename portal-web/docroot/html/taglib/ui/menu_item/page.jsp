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

<%@ include file="/html/taglib/ui/menu_item/init.jsp" %>

<%
MenuItem menuItem = (MenuItem)request.getAttribute("liferay-ui:menu_item:menuItem");
%>

<c:choose>
	<c:when test="<%= menuItem instanceof DeleteMenuItem %>">

		<%
		DeleteMenuItem deleteMenuItem = (DeleteMenuItem)menuItem;
		%>

		<liferay-ui:icon-delete
			trash="<%= deleteMenuItem.isTrash() %>"
			url="<%= deleteMenuItem.getURL() %>"
		/>
	</c:when>
	<c:when test="<%= menuItem instanceof JavascriptMenuItem %>">

		<%
		JavascriptMenuItem javascriptMenuItem = (JavascriptMenuItem)menuItem;
		%>

		<liferay-ui:icon
			iconCssClass="<%= javascriptMenuItem.getIcon() %>"
			message="<%= javascriptMenuItem.getLabel() %>"
			onClick="<%= javascriptMenuItem.getOnClick() %>"
		/>

		<aui:script>
			<%= javascriptMenuItem.getJavascript() %>
		</aui:script>
	</c:when>
	<c:when test="<%= menuItem instanceof URLMenuItem %>">

		<%
		URLMenuItem urlMenuItem = (URLMenuItem)menuItem;
		%>

		<liferay-ui:icon
			iconCssClass="<%= urlMenuItem.getIcon() %>"
			message="<%= urlMenuItem.getLabel() %>"
			method="<%= urlMenuItem.getMethod() %>"
			target="<%= urlMenuItem.getTarget() %>"
			url="<%= urlMenuItem.getURL() %>"
			useDialog="<%= urlMenuItem.isUseDialog() %>"
		/>
	</c:when>
</c:choose>