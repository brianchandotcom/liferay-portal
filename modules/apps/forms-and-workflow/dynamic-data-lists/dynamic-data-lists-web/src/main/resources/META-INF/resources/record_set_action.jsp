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
SearchContainer searchContainer = (SearchContainer)request.getAttribute(WebKeys.SEARCH_CONTAINER);

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDLRecordSet recordSet = (DDLRecordSet)row.getObject();

DDLRecordSet selRecordSet = (DDLRecordSet)request.getAttribute("record_set_action.jsp-selRecordSet");

boolean hasViewPermission = ddlDisplayContext.isAdminPortlet() && DDLRecordSetPermission.contains(permissionChecker, recordSet, ActionKeys.VIEW);
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= hasViewPermission %>">
		<portlet:renderURL var="viewRecordSetURL">
			<portlet:param name="mvcPath" value="/view_record_set.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="view[action]"
			url="<%= viewRecordSetURL %>"
		/>

		<portlet:renderURL var="viewRecordSetURL">
			<portlet:param name="mvcPath" value="/view_record_set.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
			<portlet:param name="spreadsheet" value="<%= Boolean.TRUE.toString() %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="spreadsheet-view"
			url="<%= viewRecordSetURL %>"
		/>
	</c:if>

	<c:if test="<%= DDLRecordSetPermission.contains(permissionChecker, recordSet, ActionKeys.UPDATE) %>">
		<liferay-portlet:renderURL portletName="<%= DDLPortletKeys.DYNAMIC_DATA_LISTS %>" var="editRecordSetURL">
			<portlet:param name="mvcPath" value="/edit_record_set.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editRecordSetURL %>"
		/>
	</c:if>

	<c:if test="<%= hasViewPermission %>">
		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="exportRecordSet" var="exportRecordSetURL">
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</liferay-portlet:resourceURL>

		<%
		StringBundler sb = new StringBundler(6);

		sb.append("javascript:");
		sb.append(renderResponse.getNamespace());
		sb.append("exportRecordSet");
		sb.append("('");
		sb.append(exportRecordSetURL);
		sb.append("');");
		%>

		<liferay-ui:icon
			message="export"
			url="<%= sb.toString() %>"
		/>
	</c:if>

	<c:if test="<%= DDLRecordSetPermission.contains(permissionChecker, recordSet, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DDLRecordSet.class.getName() %>"
			modelResourceDescription="<%= recordSet.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(recordSet.getRecordSetId()) %>"
			var="permissionsRecordSetURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsRecordSetURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= ((selRecordSet == null) || (selRecordSet.getRecordSetId() != recordSet.getRecordSetId())) && DDLRecordSetPermission.contains(permissionChecker, recordSet, ActionKeys.DELETE) %>">
		<liferay-portlet:actionURL name="deleteRecordSet" portletName="<%= DDLPortletKeys.DYNAMIC_DATA_LISTS %>" var="deleteRecordSetURL">
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteRecordSetURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>