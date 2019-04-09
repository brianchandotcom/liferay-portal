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
String adminDefaultGroupNames = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_GROUP_NAMES);
String adminDefaultOrganizationGroupNames = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_ORGANIZATION_GROUP_NAMES);
String adminDefaultRoleNames = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_ROLE_NAMES);
String adminDefaultUserGroupNames = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES);
boolean adminSyncDefaultAssociations = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.ADMIN_SYNC_DEFAULT_ASSOCIATIONS, PropsValues.ADMIN_SYNC_DEFAULT_ASSOCIATIONS);
%>

<aui:fieldset>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<aui:input helpMessage="check-to-apply-the-changes-to-existing-users" label="apply-to-existing-users" name='<%= "settings--" + PropsKeys.ADMIN_SYNC_DEFAULT_ASSOCIATIONS + "--" %>' type="checkbox" value="<%= adminSyncDefaultAssociations %>" />

	<aui:input helpMessage="enter-the-default-site-names-per-line-that-are-associated-with-newly-created-users" label="sites" name='<%= "settings--" + PropsKeys.ADMIN_DEFAULT_GROUP_NAMES + "--" %>' type="textarea" value="<%= adminDefaultGroupNames %>" />

	<aui:input helpMessage="enter-the-default-organization-site-names-per-line-that-are-associated-with-newly-created-users" label="organization-sites" name='<%= "settings--" + PropsKeys.ADMIN_DEFAULT_ORGANIZATION_GROUP_NAMES + "--" %>' type="textarea" value="<%= adminDefaultOrganizationGroupNames %>" />

	<aui:input helpMessage="enter-the-default-regular-role-names-per-line-that-are-associated-with-newly-created-users" label="regular-roles" name='<%= "settings--" + PropsKeys.ADMIN_DEFAULT_ROLE_NAMES + "--" %>' type="textarea" value="<%= adminDefaultRoleNames %>" />

	<aui:input helpMessage="enter-the-default-user-group-names-per-line-that-are-associated-with-newly-created-users" label="user-groups" name='<%= "settings--" + PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES + "--" %>' type="textarea" value="<%= adminDefaultUserGroupNames %>" />
</aui:fieldset>