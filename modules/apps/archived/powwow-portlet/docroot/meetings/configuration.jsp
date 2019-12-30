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
String currentLanguageId = LanguageUtil.getLanguageId(request);

String emailSubjectParam = "emailSubject_" + currentLanguageId;
String emailBodyParam = "emailBody_" + currentLanguageId;

String defaultEmailSubject = ContentUtil.get(PowwowUtil.class.getClassLoader(), PortletPropsValues.POWWOW_INVITATION_EMAIL_SUBJECT);
String defaultEmailBody = ContentUtil.get(PowwowUtil.class.getClassLoader(), PortletPropsValues.POWWOW_INVITATION_EMAIL_BODY);

String emailSubject = PrefsParamUtil.getString(portletPreferences, request, emailSubjectParam, defaultEmailSubject);
String emailBody = PrefsParamUtil.getString(portletPreferences, request, emailBodyParam, defaultEmailBody);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<aui:fieldset>
		<aui:select label="language" name="languageId" onChange='<%= renderResponse.getNamespace() + "updateLanguage(this);" %>'>

			<%
			Set<Locale> locales = LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId());

			for (Locale currentLocale : locales) {
				String style = StringPool.BLANK;

				if (Validator.isNotNull(portletPreferences.getValue("emailSubject_" + LocaleUtil.toLanguageId(currentLocale), StringPool.BLANK)) || Validator.isNotNull(portletPreferences.getValue("emailBody_" + LocaleUtil.toLanguageId(currentLocale), StringPool.BLANK))) {
					style = "font-weight: bold;";
				}
			%>

				<aui:option label="<%= currentLocale.getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(currentLocale)) %>" style="<%= style %>" value="<%= LocaleUtil.toLanguageId(currentLocale) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "preferences--" + emailSubjectParam + "--" %>' value="<%= emailSubject %>" />

		<aui:field-wrapper label="body">
			<liferay-ui:input-editor
				editorImpl="ckeditor"
			/>

			<aui:input name='<%= "preferences--" + emailBodyParam + "--" %>' type="hidden" value="<%= emailBody %>" />
		</aui:field-wrapper>
	</aui:fieldset>

	<div class="definition-of-terms">
		<h4><liferay-ui:message key="definition-of-terms" /></h4>

		<dl>
			<dt>
				[$MEETING_DATE$]
			</dt>
			<dd>
				<liferay-ui:message key="the-start-date-of-the-meeting" />
			</dd>
			<dt>
				[$MEETING_DESCRIPTION$]
			</dt>
			<dd>
				<liferay-ui:message key="the-description-of-the-meeting" />
			</dd>
			<dt>
				[$MEETING_JOIN_BY_PHONE_ACCESS_CODE$]
			</dt>
			<dd>
				<liferay-ui:message key="the-join-by-phone-access-code-of-the-meeting" />
			</dd>
			<dt>
				[$MEETING_JOIN_BY_PHONE_ACCESS_CODE_LABEL$]
			</dt>
			<dd>
				<liferay-ui:message key="the-join-by-phone-access-code-label-of-the-provider-type-associated-with-the-meeting" />
			</dd>
			<dt>
				[$MEETING_NAME$]
			</dt>
			<dd>
				<liferay-ui:message key="the-name-of-the-meeting" />
			</dd>
			<dt>
				[$MEETING_PASSWORD$]
			</dt>
			<dd>
				<liferay-ui:message key="the-password-for-the-meeting" />
			</dd>
			<dt>
				[$MEETING_TIME$]
			</dt>
			<dd>
				<liferay-ui:message key="the-start-time-of-the-meeting" />
			</dd>
			<dt>
				[$MEETING_TIME_ZONE$]
			</dt>
			<dd>
				<liferay-ui:message key="the-time-zone-of-the-meeting" />
			</dd>
			<dt>
				[$MEETING_URL$]
			</dt>
			<dd>
				<liferay-ui:message key="the-meeting-url" />
			</dd>
			<dt>
				[$MEETING_USER_ADDRESS$]
			</dt>
			<dd>
				<liferay-ui:message key="the-email-address-of-the-user-who-added-the-meeting" />
			</dd>
			<dt>
				[$MEETING_USER_NAME$]
			</dt>
			<dd>
				<liferay-ui:message key="the-user-who-added-the-meeting" />
			</dd>
			<dt>
				[$COMPANY_ID$]
			</dt>
			<dd>
				<liferay-ui:message key="the-company-id-associated-with-the-meeting" />
			</dd>
			<dt>
				[$COMPANY_MX$]
			</dt>
			<dd>
				<liferay-ui:message key="the-company-mx-associated-with-the-meeting" />
			</dd>
			<dt>
				[$COMPANY_NAME$]
			</dt>
			<dd>
				<liferay-ui:message key="the-company-name-associated-with-the-meeting" />
			</dd>
			<dt>
				[$PORTAL_URL$]
			</dt>
			<dd>
				<%= company.getVirtualHostname() %>
			</dd>
			<dt>
				[$PORTLET_NAME$]
			</dt>
			<dd>
				<%= PortalUtil.getPortletTitle(renderResponse) %>
			</dd>
			<dt>
				[$SITE_NAME$]
			</dt>
			<dd>
				<liferay-ui:message key="the-site-name-associated-with-the-meeting" />
			</dd>
			<dt>
				[$TO_ADDRESS$]
			</dt>
			<dd>
				<liferay-ui:message key="the-address-of-the-email-recipient" />
			</dd>
			<dt>
				[$TO_NAME$]
			</dt>
			<dd>
				<liferay-ui:message key="the-name-of-the-email-recipient" />
			</dd>
		</dl>
	</div>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />initEditor() {
		return '<%= UnicodeFormatter.toString(emailBody) %>';
	}

	function <portlet:namespace />updateLanguage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '';

		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace /><%= emailBodyParam %>.value = window.<portlet:namespace />editor.getHTML();

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);
</aui:script>