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
PasswordPolicySearch searchContainer = (PasswordPolicySearch)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

PasswordPolicy passwordPolicy = (PasswordPolicy)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= PasswordPolicyPermissionUtil.contains(permissionChecker, passwordPolicy.getPasswordPolicyId(), ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_password_policy.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="passwordPolicyId" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= PasswordPolicyPermissionUtil.contains(permissionChecker, passwordPolicy.getPasswordPolicyId(), ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= PasswordPolicy.class.getName() %>"
			modelResourceDescription="<%= HtmlUtil.escape(passwordPolicy.getName()) %>"
			resourcePrimKey="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>"
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

	<c:if test="<%= PasswordPolicyPermissionUtil.contains(permissionChecker, passwordPolicy.getPasswordPolicyId(), ActionKeys.ASSIGN_MEMBERS) %>">
		<portlet:renderURL var="assignMembersURL">
			<portlet:param name="mvcPath" value="/edit_password_policy_assignments.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="passwordPolicyId" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="assign-members"
			url="<%= assignMembersURL %>"
		/>
	</c:if>

	<c:if test="<%= !passwordPolicy.getDefaultPolicy() && PasswordPolicyPermissionUtil.contains(permissionChecker, passwordPolicy.getPasswordPolicyId(), ActionKeys.DELETE) %>">
		<portlet:actionURL name="deletePasswordPolicy" var="deletePasswordPolicyURL">
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="passwordPolicyId" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deletePasswordPolicyURL %>" />
	</c:if>
</liferay-ui:icon-menu>