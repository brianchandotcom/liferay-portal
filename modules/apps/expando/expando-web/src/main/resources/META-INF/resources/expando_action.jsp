<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SearchContainer<?> searchContainer = (SearchContainer<?>)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = String.valueOf(searchContainer.getIteratorURL());

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

ExpandoColumn expandoColumn = (ExpandoColumn)row.getParameter("expandoColumn");
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= ExpandoColumnPermissionUtil.contains(permissionChecker, expandoColumn, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit/expando.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="columnId" value="<%= String.valueOf(expandoColumn.getColumnId()) %>" />
			<portlet:param name="modelResource" value='<%= (String)row.getParameter("modelResource") %>' />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= ExpandoColumnPermissionUtil.contains(permissionChecker, expandoColumn, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= ExpandoColumn.class.getName() %>"
			modelResourceDescription="<%= HtmlUtil.escape(expandoColumn.getName()) %>"
			resourcePrimKey="<%= String.valueOf(expandoColumn.getColumnId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= ExpandoColumnPermissionUtil.contains(permissionChecker, expandoColumn, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteExpando" var="deleteExpandoURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="columnId" value="<%= String.valueOf(expandoColumn.getColumnId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteExpandoURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>