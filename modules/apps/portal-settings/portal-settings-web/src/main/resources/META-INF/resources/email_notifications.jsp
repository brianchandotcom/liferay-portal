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

<h3><liferay-ui:message key="email-notifications" /></h3>

<%
String adminEmailFromName = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
String adminEmailFromAddress = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId(), true);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="email_notifications"
/>

<liferay-ui:tabs
	names="password-reset-notification"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<liferay-ui:error key="emailPasswordResetSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailPasswordResetBody" message="please-enter-a-valid-body" />

		<liferay-frontend:email-notification-settings
			emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(companyPortletPreferences, renderRequest, "adminEmailPasswordResetBody", "settings", ContentUtil.get(PortalClassLoaderUtil.getClassLoader(), PropsValues.ADMIN_EMAIL_PASSWORD_RESET_BODY)) %>'
			emailParam="adminEmailPasswordReset"
			emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(companyPortletPreferences, renderRequest, "adminEmailPasswordResetSubject", "settings", ContentUtil.get(PortalClassLoaderUtil.getClassLoader(), PropsValues.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT)) %>'
			fieldPrefix="settings"
			showEmailEnabled="<%= false %>"
		/>

		<aui:fieldset cssClass="definition-of-terms email-verification terms" label="definition-of-terms">
			<%@ include file="/definition_of_terms.jspf" %>
		</aui:fieldset>
	</liferay-ui:section>
</liferay-ui:tabs>