<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SearchContainer<?> searchContainer = (SearchContainer)request.getAttribute(WebKeys.SEARCH_CONTAINER);

String redirect = String.valueOf(searchContainer.getIteratorURL());

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
		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/dynamic_data_lists/export_record_set" var="exportRecordSetURL">
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</liferay-portlet:resourceURL>

		<%
		StringBundler sb = new StringBundler(5);

		sb.append("javascript:");
		sb.append(liferayPortletResponse.getNamespace());
		sb.append("exportRecordSet('");
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
		<liferay-portlet:actionURL name="/dynamic_data_lists/delete_record_set" portletName="<%= DDLPortletKeys.DYNAMIC_DATA_LISTS %>" var="deleteRecordSetURL">
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteRecordSetURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>