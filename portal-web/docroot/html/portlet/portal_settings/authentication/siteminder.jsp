<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
boolean siteminderAuthEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SITEMINDER_AUTH_ENABLED, PropsValues.SITEMINDER_AUTH_ENABLED);
boolean siteminderImportFromLdap = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SITEMINDER_IMPORT_FROM_LDAP, PropsValues.SITEMINDER_IMPORT_FROM_LDAP);
String siteminderUserHeader = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.SITEMINDER_USER_HEADER, PropsValues.SITEMINDER_USER_HEADER);
%>

<aui:fieldset>
	<aui:input label="enabled" name='<%= "settings--" + PropsKeys.SITEMINDER_AUTH_ENABLED + "--" %>' type="checkbox" value="<%= siteminderAuthEnabled %>" />

	<aui:input helpMessage="import-siteminder-users-from-ldap-help" label="import-siteminder-users-from-ldap" name='<%= "settings--" + PropsKeys.SITEMINDER_IMPORT_FROM_LDAP + "--" %>' type="checkbox" value="<%= siteminderImportFromLdap %>" />

	<aui:input cssClass="lfr-input-text-container" label="user-header" name='<%= "settings--" + PropsKeys.SITEMINDER_USER_HEADER + "--" %>' type="text" value="<%= siteminderUserHeader %>" />
</aui:fieldset>