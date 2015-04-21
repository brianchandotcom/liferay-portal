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

AssetTag tag = (AssetTag)row.getObject();
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
	<c:if test="<%= AssetTagPermission.contains(permissionChecker, tag, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_tag.jsp" />
			<portlet:param name="tagId" value="<%= String.valueOf(tag.getTagId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			label="<%= true %>"
			message="edit"
			url="<%= editURL %>"
		/>

		<portlet:renderURL var="mergeURL">
			<portlet:param name="mvcPath" value="/merge_tag.jsp" />
			<portlet:param name="mergeTagIds" value="<%= String.valueOf(tag.getTagId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-random"
			label="<%= true %>"
			message="merge"
			url="<%= mergeURL %>"
		/>
	</c:if>

	<c:if test="<%= AssetTagPermission.contains(permissionChecker, tag, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteTag" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="tagId" value="<%= String.valueOf(tag.getTagId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>