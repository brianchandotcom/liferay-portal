<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/membership_policy_error/init.jsp" %>

<liferay-ui:error exception="<%= MembershipPolicyException.class %>">

	<%
	MembershipPolicyException mpe = (MembershipPolicyException)errorException;

	List<User> users = mpe.getUsers();
	%>

	<c:choose>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.ORGANIZATION_MEMBERSHIP_NOT_ALLOWED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getOrganizations(), "name", StringPool.COMMA_AND_SPACE)} %>' key='<%= users.size() == 1 ? "x-is-not-allowed-to-join-x" : "the-following-users-are-not-allowed-to-join-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.ORGANIZATION_MEMBERSHIP_REQUIRED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getOrganizations(), "name", StringPool.COMMA_AND_SPACE)} %>' key='<%= users.size() == 1 ? "x-is-not-allowed-to-leave-x" : "the-following-users-are-not-allowed-to-leave-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.ROLE_MEMBERSHIP_NOT_ALLOWED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getRoles(), "title", StringPool.COMMA_AND_SPACE)} %>' key='<%= users.size() == 1 ? "x-cannot-be-assigned-to-x" : "the-following-users-cannot-be-assigned-to-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.ROLE_MEMBERSHIP_REQUIRED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getRoles(), "title", StringPool.COMMA_AND_SPACE)} %>' key='<%= users.size() == 1 ? "x-cannot-be-unassigned-from-x" : "the-following-users-cannot-be-unassigned-from-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.SITE_MEMBERSHIP_NOT_ALLOWED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getGroups(), "descriptiveName", StringPool.COMMA_AND_SPACE)} %>' key='<%= users.size() == 1 ? "x-is-not-allowed-to-join-x" : "the-following-users-are-not-allowed-to-join-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.SITE_MEMBERSHIP_REQUIRED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getGroups(), "descriptiveName", StringPool.COMMA_AND_SPACE)} %>' key='<%= users.size() == 1 ? "x-is-not-allowed-to-leave-x" : "the-following-users-are-not-allowed-to-leave-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.USER_GROUP_MEMBERSHIP_NOT_ALLOWED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getUserGroups(), "name", StringPool.COMMA_AND_SPACE)} %>' key='<%= users.size() == 1 ? "x-are-not-allowed-to-join-x" : "the-following-users-are-not-allowed-to-join-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.USER_GROUP_MEMBERSHIP_REQUIRED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getUserGroups(), "name", StringPool.COMMA_AND_SPACE)} %>' key='<%= users.size() == 1 ? "x-are-not-allowed-to-leave-x" : "the-following-users-are-not-allowed-to-leave-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
	</c:choose>
</liferay-ui:error>