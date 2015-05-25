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

DDLRecord record = (DDLRecord)row.getObject();

long formDDMTemplateId = GetterUtil.getLong((String)row.getParameter("formDDMTemplateId"));

boolean hasDeletePermission = GetterUtil.getBoolean((String)row.getParameter("hasDeletePermission"));
boolean hasUpdatePermission = GetterUtil.getBoolean((String)row.getParameter("hasUpdatePermission"));

DDLRecordVersion recordVersion = record.getRecordVersion();

if (hasUpdatePermission) {
	recordVersion = record.getLatestRecordVersion();
}
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
	<c:if test="<%= DDLRecordSetPermission.contains(permissionChecker, record.getRecordSet(), ActionKeys.VIEW) %>">
		<portlet:renderURL var="viewRecordURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
			<portlet:param name="struts_action" value="/dynamic_data_lists/view_record" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="recordId" value="<%= String.valueOf(record.getRecordId()) %>" />
			<portlet:param name="version" value="<%= recordVersion.getVersion() %>" />
			<portlet:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-search"
			message="view[action]"
			url="<%= viewRecordURL %>"
		/>
	</c:if>

	<c:if test="<%= hasUpdatePermission %>">
		<portlet:renderURL var="editRecordURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
			<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="recordId" value="<%= String.valueOf(record.getRecordId()) %>" />
			<portlet:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editRecordURL %>"
		/>
	</c:if>

	<c:if test="<%= hasDeletePermission %>">
		<portlet:actionURL var="deleteRecordURL">
			<portlet:param name="struts_action" value="/dynamic_data_mapping_list/edit_record" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="recordId" value="<%= String.valueOf(record.getRecordId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteRecordURL %>" />
	</c:if>
</liferay-ui:icon-menu>