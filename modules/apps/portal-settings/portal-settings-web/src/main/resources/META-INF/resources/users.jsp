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
boolean usersScreenNameAlwaysAutogenerate = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE, PropsValues.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE);
boolean fieldEnableMale = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_CONTACT_MALE, PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_CONTACT_MALE);
boolean fieldEnableBirthday = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_CONTACT_BIRTHDAY, PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_CONTACT_BIRTHDAY);

String adminReservedScreenNames = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_RESERVED_SCREEN_NAMES);
String adminReservedEmailAddresses = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES);

String adminDefaultGroupNames = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_GROUP_NAMES);
String adminDefaultOrganizationGroupNames = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_ORGANIZATION_GROUP_NAMES);
String adminDefaultRoleNames = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_ROLE_NAMES);
String adminDefaultUserGroupNames = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES);
boolean adminSyncDefaultAssociations = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.ADMIN_SYNC_DEFAULT_ASSOCIATIONS, PropsValues.ADMIN_SYNC_DEFAULT_ASSOCIATIONS);
%>

<h3><liferay-ui:message key="users" /></h3>

<liferay-ui:tabs
	names="fields,reserved-credentials,default-user-associations"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<aui:fieldset>
			<aui:input label="autogenerate-user-screen-names" name='<%= "settings--" + PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE + "--" %>' type="checkbox" value="<%= usersScreenNameAlwaysAutogenerate %>" />

			<aui:input label="enable-birthday" name='<%= "settings--" + PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_CONTACT_BIRTHDAY + "--" %>' type="checkbox" value="<%= fieldEnableBirthday %>" />

			<aui:input label="enable-gender" name='<%= "settings--" + PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_CONTACT_MALE + "--" %>' type="checkbox" value="<%= fieldEnableMale %>" />
		</aui:fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<aui:fieldset>
			<aui:input helpMessage="enter-one-screen-name-per-line-to-reserve-the-screen-name" label="screen-names" name='<%= "settings--" + PropsKeys.ADMIN_RESERVED_SCREEN_NAMES + "--" %>' type="textarea" value="<%= adminReservedScreenNames %>" />

			<aui:input helpMessage="enter-one-user-email-address-per-line-to-reserve-the-user-email-address" label="email-addresses" name='<%= "settings--" + PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES + "--" %>' type="textarea" value="<%= adminReservedEmailAddresses %>" />
		</aui:fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<aui:fieldset>
			<aui:input helpMessage="check-to-apply-the-changes-to-existing-users" label="apply-to-existing-users" name='<%= "settings--" + PropsKeys.ADMIN_SYNC_DEFAULT_ASSOCIATIONS + "--" %>' type="checkbox" value="<%= adminSyncDefaultAssociations %>" />

			<aui:input helpMessage="enter-the-default-site-names-per-line-that-are-associated-with-newly-created-users" label="sites" name='<%= "settings--" + PropsKeys.ADMIN_DEFAULT_GROUP_NAMES + "--" %>' type="textarea" value="<%= adminDefaultGroupNames %>" />

			<aui:input helpMessage="enter-the-default-organization-site-names-per-line-that-are-associated-with-newly-created-users" label="organization-sites" name='<%= "settings--" + PropsKeys.ADMIN_DEFAULT_ORGANIZATION_GROUP_NAMES + "--" %>' type="textarea" value="<%= adminDefaultOrganizationGroupNames %>" />

			<aui:input helpMessage="enter-the-default-regular-role-names-per-line-that-are-associated-with-newly-created-users" label="regular-roles" name='<%= "settings--" + PropsKeys.ADMIN_DEFAULT_ROLE_NAMES + "--" %>' type="textarea" value="<%= adminDefaultRoleNames %>" />

			<aui:input helpMessage="enter-the-default-user-group-names-per-line-that-are-associated-with-newly-created-users" label="user-groups" name='<%= "settings--" + PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES + "--" %>' type="textarea" value="<%= adminDefaultUserGroupNames %>" />
		</aui:fieldset>
	</liferay-ui:section>
</liferay-ui:tabs>