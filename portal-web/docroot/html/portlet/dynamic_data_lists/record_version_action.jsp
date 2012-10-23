<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDLRecordVersion recordVersion = (DDLRecordVersion)row.getObject();

long formDDMTemplateId = GetterUtil.getLong((String)row.getParameter("formDDMTemplateId"));
%>

<liferay-ui:icon-menu>
	<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_LISTS %>" var="viewRecordVersionURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
		<portlet:param name="struts_action" value="/dynamic_data_lists/view_record" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="recordId" value="<%= String.valueOf(recordVersion.getRecordId()) %>" />
		<portlet:param name="version" value="<%= recordVersion.getVersion() %>" />
		<portlet:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		image="view"
		url="<%= viewRecordVersionURL %>"
	/>

	<portlet:actionURL var="revertURL">
		<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.REVERT %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="recordId" value="<%= String.valueOf(recordVersion.getRecordId()) %>" />
		<portlet:param name="version" value="<%= String.valueOf(recordVersion.getVersion()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		image="undo"
		message="revert"
		url="<%= revertURL %>"
	/>
</liferay-ui:icon-menu>