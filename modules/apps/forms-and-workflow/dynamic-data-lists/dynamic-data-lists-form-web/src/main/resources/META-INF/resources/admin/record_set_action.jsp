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

<%@ include file="/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDLRecordSet recordSet = (DDLRecordSet)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= ddlFormAdminDisplayContext.isShowViewEntriesRecordSetIcon(recordSet) %>">
		<portlet:renderURL var="viewEntriesURL">
			<portlet:param name="mvcPath" value="/admin/view_records.jsp" />
			<portlet:param name="redirect" value="<%= PortalUtil.getCurrentURL(request) %>" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="view-entries"
			url="<%= viewEntriesURL %>"
		/>
	</c:if>

	<c:if test="<%= ddlFormAdminDisplayContext.isShowEditRecordSetIcon(recordSet) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
			<portlet:param name="redirect" value="<%= PortalUtil.getCurrentURL(request) %>" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= ddlFormAdminDisplayContext.isShowCopyRecordSetButton() %>">
		<liferay-portlet:actionURL name="copyRecordSet" var="copyRecordSetURL">
			<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon
			message="duplicate"
			url="<%= copyRecordSetURL %>"
		/>
	</c:if>

	<c:if test="<%= ddlFormAdminDisplayContext.isShowExportRecordSetIcon(recordSet) %>">
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

	<c:if test="<%= ddlFormAdminDisplayContext.isShowPermissionsIcon(recordSet) %>">
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

	<c:if test="<%= ddlFormAdminDisplayContext.isShowDeleteRecordSetIcon(recordSet) %>">
		<portlet:actionURL name="deleteRecordSet" var="deleteURL">
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>