<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error-header />

<liferay-ui:error exception="<%= DataLimitExceededException.class %>" message="unable-to-create-role-because-the-maximum-number-of-roles-has-been-reached" />
<liferay-ui:error exception="<%= NoSuchRoleException.class %>" message="the-role-could-not-be-found" />
<liferay-ui:error exception="<%= RequiredRoleException.MustNotRemoveLastAdministator.class %>" message="at-least-one-administrator-is-required" />
<liferay-ui:error exception="<%= RequiredRoleException.MustNotRemoveUserRole.class %>" message="user-role-could-not-be-removed" />
<liferay-ui:error exception="<%= RoleAssignmentException.class %>" message="you-cannot-assign-groups-or-users-to-this-role" />
<liferay-ui:error exception="<%= RolePermissionsException.class %>" message="you-cannot-edit-the-permissions-of-this-role" />

<liferay-ui:error-principal />