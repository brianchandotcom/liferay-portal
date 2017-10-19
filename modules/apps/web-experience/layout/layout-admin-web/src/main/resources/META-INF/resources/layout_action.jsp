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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Layout curLayout = (Layout)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, curLayout, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editLayoutURL">
			<portlet:param name="mvcPath" value="/edit_layout.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(curLayout.getGroupId()) %>" />
			<portlet:param name="selPlid" value="<%= String.valueOf(curLayout.getPlid()) %>" />
			<portlet:param name="privateLayout" value="<%= String.valueOf(curLayout.isPrivateLayout()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="configure"
			url="<%= editLayoutURL %>"
		/>
	</c:if>

	<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, curLayout, ActionKeys.ADD_LAYOUT) %>">
		<portlet:renderURL var="addChildPageURL">
			<portlet:param name="mvcPath" value="/add_layout.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(curLayout.getGroupId()) %>" />
			<portlet:param name="selPlid" value="<%= String.valueOf(curLayout.getPlid()) %>" />
			<portlet:param name="privateLayout" value="<%= String.valueOf(curLayout.isPrivateLayout()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="add-child-page"
			url="<%= addChildPageURL %>"
		/>
	</c:if>

	<liferay-ui:icon
		message="view-page"
		target="_blank"
		url="<%= PortalUtil.getLayoutFullURL(curLayout, themeDisplay) %>"
	/>

	<%
	boolean isDeletable = true;

	if (!LayoutPermissionUtil.contains(permissionChecker, curLayout, ActionKeys.DELETE)) {
		isDeletable = false;
	}

	Group group = curLayout.getGroup();

	int layoutsCount = LayoutLocalServiceUtil.getLayoutsCount(group, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

	if (group.isGuest() && !curLayout.isPrivateLayout() && curLayout.isRootLayout() && (layoutsCount == 1)) {
		isDeletable = false;
	}
	%>

	<c:if test="<%= isDeletable %>">
		<portlet:actionURL name="/layout/delete_layout" var="deleteLayoutURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="plid" value="<%= String.valueOf(curLayout.getPlid()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteLayoutURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>