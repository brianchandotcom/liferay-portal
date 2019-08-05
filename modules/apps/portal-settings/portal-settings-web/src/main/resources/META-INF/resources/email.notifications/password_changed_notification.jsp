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

String sectionName = "password-changed-notification";
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<liferay-ui:error key="emailPasswordChangedSubject" message="please-enter-a-valid-subject" />
<liferay-ui:error key="emailPasswordChangedBody" message="please-enter-a-valid-body" />

<aui:field-wrapper label="email-without-password">
	<liferay-frontend:email-notification-settings
		emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(companyPortletPreferences, renderRequest, "adminEmailPasswordChangedBody", "settings", ContentUtil.get(PortalClassLoaderUtil.getClassLoader(), PropsValues.ADMIN_EMAIL_PASSWORD_CHANGED_BODY)) %>'
		emailParam="adminEmailPasswordChanged"
		emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(companyPortletPreferences, renderRequest, "adminEmailPasswordChangedSubject", "settings", ContentUtil.get(PortalClassLoaderUtil.getClassLoader(), PropsValues.ADMIN_EMAIL_PASSWORD_CHANGED_SUBJECT)) %>'
		fieldPrefix="settings"
		showEmailEnabled="<%= false %>"
	/>
</aui:field-wrapper>

<aui:fieldset cssClass="definition-of-terms email-verification terms" label="definition-of-terms">
	<%@ include file="/email.notifications/definition_of_terms.jspf" %>
</aui:fieldset>

<%
String adminEmailPasswordSentSubject = LocalizationUtil.getLocalizationXmlFromPreferences(companyPortletPreferences, renderRequest, "adminEmailPasswordSentSubject", "preferences", StringPool.BLANK);
String adminEmailPasswordSentBody = LocalizationUtil.getLocalizationXmlFromPreferences(companyPortletPreferences, renderRequest, "adminEmailPasswordSentBody", "preferences", StringPool.BLANK);
%>

<c:if test="<%= Validator.isNotNull(adminEmailPasswordSentSubject) || Validator.isNotNull(adminEmailPasswordSentBody) %>">
	<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="legacy-template-no-longer-used" markupView="lexicon">
		<aui:input checked="<%= false %>" label="discard" name="discardLegacyKey" type="checkbox" value="adminEmailPasswordSentSubject,adminEmailPasswordSentBody" />

		<div class="alert alert-info">
			<liferay-ui:message key="sending-of-passwords-by-email-is-no-longer-supported-the-template-below-is-not-used-and-can-be-discarded" />
		</div>

		<c:if test="<%= Validator.isNotNull(adminEmailPasswordSentSubject) %>">
			<aui:field-wrapper label="subject">
				<liferay-ui:input-localized
					fieldPrefix="settings"
					fieldPrefixSeparator="--"
					name="adminEmailPasswordSentSubject"
					readonly="<%= true %>"
					xml="<%= adminEmailPasswordSentSubject %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<c:if test="<%= Validator.isNotNull(adminEmailPasswordSentBody) %>">
			<aui:field-wrapper label="body">
				<liferay-ui:input-localized
					fieldPrefix="settings"
					fieldPrefixSeparator="--"
					name="adminEmailPasswordSentBody"
					readonly="<%= true %>"
					type="textarea"
					xml="<%= adminEmailPasswordSentBody %>"
				/>
			</aui:field-wrapper>
		</c:if>
	</aui:fieldset>
</c:if>