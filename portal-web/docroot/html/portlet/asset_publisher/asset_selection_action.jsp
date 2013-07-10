<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "backURL");

redirect = ParamUtil.getString(request, "redirect");

SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

int assetEntryOrder = searchContainer.getStart() + row.getPos();

boolean last = (assetEntryOrder == (searchContainer.getTotal() - 1));
%>

<c:choose>
	<c:when test="<%= (assetEntryOrder == 0) && last %>">
	</c:when>
	<c:when test="<%= (assetEntryOrder > 0) && !last %>">

		<%
		String taglibDownURL = "javascript:" + renderResponse.getNamespace() + "moveSelectionDown('" + assetEntryOrder + "')";
		%>

		<liferay-ui:icon
			message="down"
			src='<%= themeDisplay.getPathThemeImages() + "/arrows/02_down.png" %>'
			url="<%= taglibDownURL %>"
		/>

		<%
		String taglibUpURL = "javascript:" + renderResponse.getNamespace() + "moveSelectionUp('" + assetEntryOrder + "')";
		%>

		<liferay-ui:icon
			message="up"
			src='<%= themeDisplay.getPathThemeImages() + "/arrows/02_up.png" %>'
			url="<%= taglibUpURL %>"
		/>
	</c:when>
	<c:when test="<%= assetEntryOrder == 0 %>">

		<%
		String taglibDownURL = "javascript:" + renderResponse.getNamespace() + "moveSelectionDown('" + assetEntryOrder + "')";
		%>

		<liferay-ui:icon
			message="down"
			src='<%= themeDisplay.getPathThemeImages() + "/arrows/02_down.png" %>'
			url="<%= taglibDownURL %>"
		/>
	</c:when>
	<c:when test="<%= last %>">

		<%
		String taglibUpURL = "javascript:" + renderResponse.getNamespace() + "moveSelectionUp('" + assetEntryOrder + "')";
		%>

		<liferay-ui:icon
			message="up"
			src='<%= themeDisplay.getPathThemeImages() + "/arrows/02_up.png" %>'
			url="<%= taglibUpURL %>"
		/>
	</c:when>
</c:choose>

<liferay-portlet:actionURL portletConfiguration="true" var="deleteURL">
	<portlet:param name="<%= Constants.CMD %>" value="remove-selection" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="assetEntryOrder" value="<%= String.valueOf(assetEntryOrder) %>" />
</liferay-portlet:actionURL>

<liferay-ui:icon-delete
	url="<%= deleteURL %>"
/>