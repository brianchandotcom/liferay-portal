<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDLRecord record = (DDLRecord)row.getObject();

boolean editable = GetterUtil.getBoolean((String)row.getParameter("editable"));
long formDDMTemplateId = GetterUtil.getLong((String)row.getParameter("formDDMTemplateId"));

boolean hasDeletePermission = GetterUtil.getBoolean((String)row.getParameter("hasDeletePermission"));
boolean hasUpdatePermission = GetterUtil.getBoolean((String)row.getParameter("hasUpdatePermission"));

DDLRecordVersion recordVersion = record.getRecordVersion();

if (hasUpdatePermission) {
	recordVersion = record.getLatestRecordVersion();
}
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= DDLRecordSetPermission.contains(permissionChecker, record.getRecordSet(), ActionKeys.VIEW) %>">
		<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="viewRecordURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
			<portlet:param name="mvcPath" value="/view_record.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="recordId" value="<%= String.valueOf(record.getRecordId()) %>" />
			<portlet:param name="version" value="<%= recordVersion.getVersion() %>" />
			<portlet:param name="editable" value="<%= String.valueOf(editable) %>" />
			<portlet:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="view[action]"
			url="<%= viewRecordURL %>"
		/>
	</c:if>

	<c:if test="<%= hasUpdatePermission %>">
		<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="editRecordURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
			<portlet:param name="mvcPath" value="/edit_record.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="recordId" value="<%= String.valueOf(record.getRecordId()) %>" />
			<portlet:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editRecordURL %>"
		/>
	</c:if>

	<c:if test="<%= hasDeletePermission %>">
		<portlet:actionURL copyCurrentRenderParameters="<%= false %>" name="/dynamic_data_lists/delete_record" var="deleteRecordURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="recordIds" value="<%= String.valueOf(record.getRecordId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteRecordURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>