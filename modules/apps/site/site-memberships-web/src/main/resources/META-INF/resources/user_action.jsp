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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

User user2 = (User)row.getObject();

boolean organizationUser = GetterUtil.getBoolean(row.getParameter("organizationUser"));
boolean userGroupUser = GetterUtil.getBoolean(row.getParameter("userGroupUser"));
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, siteMembershipsDisplayContext.getGroupId(), ActionKeys.ASSIGN_USER_ROLES) %>">
		<portlet:renderURL var="assignURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/users_roles.jsp" />
			<portlet:param name="p_u_i_d" value="<%= String.valueOf(user2.getUserId()) %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
		</portlet:renderURL>

		<%
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("href", assignURL.toString());
		%>

		<liferay-ui:icon
			cssClass="assign-site-roles"
			data="<%= data %>"
			message="assign-site-roles"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, siteMembershipsDisplayContext.getGroupId(), ActionKeys.ASSIGN_MEMBERS) && !(organizationUser || userGroupUser) && !SiteMembershipPolicyUtil.isMembershipProtected(permissionChecker, user2.getUserId(), siteMembershipsDisplayContext.getGroupId()) && !SiteMembershipPolicyUtil.isMembershipRequired(user2.getUserId(), siteMembershipsDisplayContext.getGroupId()) %>">
		<portlet:actionURL name="deleteGroupUsers" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
			<portlet:param name="removeUserId" value="<%= String.valueOf(user2.getUserId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="remove-membership"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>