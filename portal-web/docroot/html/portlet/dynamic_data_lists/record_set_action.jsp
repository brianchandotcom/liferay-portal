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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDLRecordSet recordSet = (DDLRecordSet)row.getObject();

DDLRecordSet selRecordSet = (DDLRecordSet)request.getAttribute("record_set_action.jsp-selRecordSet");

boolean hasViewPermission = portletName.equals(PortletKeys.DYNAMIC_DATA_LISTS) && DDLRecordSetPermission.contains(permissionChecker, recordSet, ActionKeys.VIEW);
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
	<c:if test="<%= hasViewPermission %>">
		<portlet:renderURL var="viewRecordSetURL">
			<portlet:param name="struts_action" value="/dynamic_data_lists/view_record_set" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VIEW %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-search"
			message="view[action]"
			url="<%= viewRecordSetURL %>"
		/>

		<portlet:renderURL var="viewRecordSetURL">
			<portlet:param name="struts_action" value="/dynamic_data_lists/view_record_set" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VIEW %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
			<portlet:param name="spreadsheet" value="true" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-table"
			message="spreadsheet-view"
			url="<%= viewRecordSetURL %>"
		/>
	</c:if>

	<c:if test="<%= DDLRecordSetPermission.contains(permissionChecker, recordSet, ActionKeys.UPDATE) %>">
		<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_LISTS %>" var="editRecordSetURL">
			<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record_set" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editRecordSetURL %>"
		/>
	</c:if>

	<c:if test="<%= hasViewPermission %>">
		<portlet:resourceURL var="exportRecordSetURL">
			<portlet:param name="struts_action" value="/dynamic_data_lists/export" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</portlet:resourceURL>

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
			iconCssClass="icon-arrow-down"
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
			iconCssClass="icon-lock"
			message="permissions"
			method="get"
			url="<%= permissionsRecordSetURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= ((selRecordSet == null) || (selRecordSet.getRecordSetId() != recordSet.getRecordSetId())) && DDLRecordSetPermission.contains(permissionChecker, recordSet, ActionKeys.DELETE) %>">
		<liferay-portlet:actionURL portletName="<%= PortletKeys.DYNAMIC_DATA_LISTS %>" var="deleteRecordSetURL">
			<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record_set" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteRecordSetURL %>" />
	</c:if>
</liferay-ui:icon-menu>