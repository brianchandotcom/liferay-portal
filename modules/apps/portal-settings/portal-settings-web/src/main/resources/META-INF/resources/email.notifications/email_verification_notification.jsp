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

String sectionName = "email-verification-notification";
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<liferay-ui:error key="emailVerificationSubject" message="please-enter-a-valid-subject" />
<liferay-ui:error key="emailVerificationBody" message="please-enter-a-valid-body" />

<liferay-frontend:email-notification-settings
	emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(companyPortletPreferences, renderRequest, "adminEmailVerificationBody", "settings", StringUtil.read(PortalClassLoaderUtil.getClassLoader(), PropsValues.ADMIN_EMAIL_VERIFICATION_BODY)) %>'
	emailParam="adminEmailVerification"
	emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(companyPortletPreferences, renderRequest, "adminEmailVerificationSubject", "settings", StringUtil.read(PortalClassLoaderUtil.getClassLoader(), PropsValues.ADMIN_EMAIL_VERIFICATION_SUBJECT)) %>'
	fieldPrefix="settings"
	showEmailEnabled="<%= false %>"
/>

<aui:fieldset cssClass="definition-of-terms email-verification terms" label="definition-of-terms">
	<%@ include file="/email.notifications/definition_of_terms.jspf" %>
</aui:fieldset>