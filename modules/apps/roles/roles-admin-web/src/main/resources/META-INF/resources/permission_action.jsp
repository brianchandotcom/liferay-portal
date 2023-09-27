<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

Permission permission = (Permission)objArray[0];
Role role = (Role)objArray[1];
String[] primKeys = (String[])objArray[2];
%>

<liferay-ui:icon-menu
	icon="<%= StringPool.BLANK %>"
	message="<%= StringPool.BLANK %>"
>
	<portlet:actionURL name="deletePermission" var="deletePermissionURL">
		<portlet:param name="mvcPath" value="/edit_role_permissions.jsp" />
		<portlet:param name="tabs1" value="define-permissions" />
		<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
		<portlet:param name="name" value="<%= permission.getName() %>" />
		<portlet:param name="scope" value="<%= String.valueOf(permission.getScope()) %>" />
		<portlet:param name="primKeys" value="<%= StringUtil.merge(primKeys, StringPool.COMMA) %>" />
		<portlet:param name="actionId" value="<%= String.valueOf(permission.getActionId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		confirmation="are-you-sure-you-want-to-remove-this-permission"
		message="remove"
		url='<%= "submitForm(document." + liferayPortletResponse.getNamespace() + "fm, '" + HtmlUtil.escapeJS(deletePermissionURL) + "');" %>'
	/>
</liferay-ui:icon-menu>