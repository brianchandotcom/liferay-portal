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
String adminEmailFromName = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
String adminEmailFromAddress = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId(), true);

String sectionName = StringPool.BLANK;
%>

<aui:fieldset>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<aui:input label="enabled" name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED + "--" %>' type="checkbox" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED) %>" />

	<liferay-ui:error key="emailUserAddedSubject" message="please-enter-a-valid-subject" />

	<aui:field-wrapper label="subject">
		<liferay-ui:input-localized
			fieldPrefix="settings"
			fieldPrefixSeparator="--"
			name="adminEmailUserAddedSubject"
			xml='<%= LocalizationUtil.getLocalizationXmlFromPreferences(companyPortletPreferences, renderRequest, "adminEmailUserAddedSubject", "settings", ContentUtil.get(PortalClassLoaderUtil.getClassLoader(), PropsValues.ADMIN_EMAIL_USER_ADDED_SUBJECT)) %>'
		/>
	</aui:field-wrapper>

	<liferay-ui:error key="emailUserAddedBody" message="please-enter-a-valid-body" />

	<liferay-frontend:email-notification-settings
		bodyLabel="body-with-password"
		emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(companyPortletPreferences, renderRequest, "adminEmailUserAddedBody", "settings", ContentUtil.get(PortalClassLoaderUtil.getClassLoader(), PropsValues.ADMIN_EMAIL_USER_ADDED_BODY)) %>'
		emailParam="adminEmailUserAdded"
		fieldPrefix="settings"
		helpMessage="account-created-notification-body-with-password-help"
		showEmailEnabled="<%= false %>"
		showSubject="<%= false %>"
	/>

	<liferay-ui:error key="emailUserAddedNoPasswordBody" message="please-enter-a-valid-body" />

	<liferay-frontend:email-notification-settings
		bodyLabel="body-without-password"
		emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(companyPortletPreferences, renderRequest, "adminEmailUserAddedNoPasswordBody", "settings", ContentUtil.get(PortalClassLoaderUtil.getClassLoader(), PropsValues.ADMIN_EMAIL_USER_ADDED_NO_PASSWORD_BODY)) %>'
		emailParam="adminEmailUserAddedNoPassword"
		fieldPrefix="settings"
		helpMessage="account-created-notification-body-without-password-help"
		showEmailEnabled="<%= false %>"
		showSubject="<%= false %>"
	/>

	<aui:fieldset cssClass="definition-of-terms email-user-add terms" label="definition-of-terms">
		<%@ include file="/definition_of_terms.jspf" %>
	</aui:fieldset>
</aui:fieldset>