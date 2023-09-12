<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error exception="<%= MembershipPolicyException.class %>">

	<%
	MembershipPolicyException mpe = (MembershipPolicyException)errorException;

	List<User> users = mpe.getUsers();
	%>

	<c:choose>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.ORGANIZATION_MEMBERSHIP_NOT_ALLOWED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getOrganizations(), "name", StringPool.COMMA_AND_SPACE)} %>' key='<%= (users.size() == 1) ? "x-is-not-allowed-to-join-x" : "the-following-users-are-not-allowed-to-join-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.ORGANIZATION_MEMBERSHIP_REQUIRED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getOrganizations(), "name", StringPool.COMMA_AND_SPACE)} %>' key='<%= (users.size() == 1) ? "x-is-not-allowed-to-leave-x" : "the-following-users-are-not-allowed-to-leave-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.ROLE_MEMBERSHIP_NOT_ALLOWED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getRoles(), "title", StringPool.COMMA_AND_SPACE)} %>' key='<%= (users.size() == 1) ? "x-cannot-be-assigned-to-x" : "the-following-users-cannot-be-assigned-to-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.ROLE_MEMBERSHIP_REQUIRED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getRoles(), "title", StringPool.COMMA_AND_SPACE)} %>' key='<%= (users.size() == 1) ? "x-cannot-be-unassigned-from-x" : "the-following-users-cannot-be-unassigned-from-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.SITE_MEMBERSHIP_NOT_ALLOWED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getGroups(), "descriptiveName", StringPool.COMMA_AND_SPACE)} %>' key='<%= (users.size() == 1) ? "x-is-not-allowed-to-join-x" : "the-following-users-are-not-allowed-to-join-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.SITE_MEMBERSHIP_REQUIRED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getGroups(), "descriptiveName", StringPool.COMMA_AND_SPACE)} %>' key='<%= (users.size() == 1) ? "x-is-not-allowed-to-leave-x" : "the-following-users-are-not-allowed-to-leave-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.USER_GROUP_MEMBERSHIP_NOT_ALLOWED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getUserGroups(), "name", StringPool.COMMA_AND_SPACE)} %>' key='<%= (users.size() == 1) ? "x-are-not-allowed-to-join-x" : "the-following-users-are-not-allowed-to-join-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= mpe.getType() == MembershipPolicyException.USER_GROUP_MEMBERSHIP_REQUIRED %>">
			<liferay-ui:message arguments='<%= new Object[] {ListUtil.toString(users, "fullName", StringPool.COMMA_AND_SPACE), ListUtil.toString(mpe.getUserGroups(), "name", StringPool.COMMA_AND_SPACE)} %>' key='<%= (users.size() == 1) ? "x-are-not-allowed-to-leave-x" : "the-following-users-are-not-allowed-to-leave-x-x" %>' translateArguments="<%= false %>" />
		</c:when>
	</c:choose>
</liferay-ui:error>