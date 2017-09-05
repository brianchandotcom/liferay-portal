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

MSBFragmentEntry msbFragmentEntry = (MSBFragmentEntry)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= MSBFragmentEntryPermission.contains(permissionChecker, msbFragmentEntry, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editMSBFragmentEntryURL">
			<portlet:param name="mvcPath" value="/edit_msb_fragment_entry.jsp" />
			<portlet:param name="msbFragmentCollectionId" value="<%= String.valueOf(msbFragmentEntry.getMsbFragmentCollectionId()) %>" />
			<portlet:param name="msbFragmentEntryId" value="<%= String.valueOf(msbFragmentEntry.getMsbFragmentEntryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editMSBFragmentEntryURL %>"
		/>
	</c:if>

	<c:if test="<%= MSBFragmentEntryPermission.contains(permissionChecker, msbFragmentEntry, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= MSBFragmentEntry.class.getName() %>"
			modelResourceDescription="<%= msbFragmentEntry.getName() %>"
			resourcePrimKey="<%= String.valueOf(msbFragmentEntry.getMsbFragmentEntryId()) %>"
			var="msbFragmentEntryPermissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= msbFragmentEntryPermissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= MSBFragmentEntryPermission.contains(permissionChecker, msbFragmentEntry, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteMSBFragmentEntries" var="deleteMSBFragmentEntryURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="msbFragmentEntryId" value="<%= String.valueOf(msbFragmentEntry.getMsbFragmentEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteMSBFragmentEntryURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>