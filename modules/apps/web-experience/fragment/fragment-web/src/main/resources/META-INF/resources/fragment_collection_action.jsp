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

FragmentCollection fragmentCollection = (FragmentCollection)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= FragmentCollectionPermission.contains(permissionChecker, fragmentCollection, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editFragmentCollectionURL">
			<portlet:param name="mvcRenderCommandName" value="/fragment/edit_fragment_collection" />
			<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentCollection.getFragmentCollectionId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editFragmentCollectionURL %>"
		/>
	</c:if>

	<c:if test="<%= FragmentCollectionPermission.contains(permissionChecker, fragmentCollection, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= FragmentCollection.class.getName() %>"
			modelResourceDescription="<%= fragmentCollection.getName() %>"
			resourcePrimKey="<%= String.valueOf(fragmentCollection.getFragmentCollectionId()) %>"
			var="fragmentCollectionPermissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= fragmentCollectionPermissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<portlet:resourceURL id="/fragment/export_fragment_collections" var="exportFragmentCollectionsURL">
		<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentCollection.getFragmentCollectionId()) %>" />
	</portlet:resourceURL>

	<liferay-ui:icon
		message="export"
		url="<%= exportFragmentCollectionsURL %>"
	/>

	<c:if test="<%= FragmentCollectionPermission.contains(permissionChecker, fragmentCollection, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/fragment/delete_fragment_collection" var="deleteFragmentCollectionURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentCollection.getFragmentCollectionId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteFragmentCollectionURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>