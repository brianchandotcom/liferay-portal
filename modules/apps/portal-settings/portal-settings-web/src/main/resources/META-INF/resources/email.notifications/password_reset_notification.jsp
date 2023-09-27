<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String adminEmailFromName = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
String adminEmailFromAddress = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId());

String sectionName = "password-reset-notification";
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<liferay-ui:error key="emailPasswordResetSubject" message="please-enter-a-valid-subject" />
<liferay-ui:error key="emailPasswordResetBody" message="please-enter-a-valid-body" />

<liferay-frontend:email-notification-settings
	emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(companyPortletPreferences, renderRequest, "adminEmailPasswordResetBody", "settings", StringUtil.read(PortalClassLoaderUtil.getClassLoader(), PropsValues.ADMIN_EMAIL_PASSWORD_RESET_BODY)) %>'
	emailParam="adminEmailPasswordReset"
	emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(companyPortletPreferences, renderRequest, "adminEmailPasswordResetSubject", "settings", StringUtil.read(PortalClassLoaderUtil.getClassLoader(), PropsValues.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT)) %>'
	fieldPrefix="settings"
	showEmailEnabled="<%= false %>"
/>

<aui:fieldset cssClass="definition-of-terms email-verification terms" label="definition-of-terms">
	<%@ include file="/email.notifications/definition_of_terms.jspf" %>
</aui:fieldset>