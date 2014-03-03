<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/login/init.jsp" %>

<%
String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", LoginUtil.getEmailFromName(portletPreferences, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", LoginUtil.getEmailFromAddress(portletPreferences, company.getCompanyId()));

String currentLanguageId = LanguageUtil.getLanguageId(request);
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="languageId" type="hidden" value="<%= currentLanguageId %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:tabs
		names="general,email-from,password-changed-notification,password-reset-notification"
		refresh="<%= false %>"
	>

		<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
		<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />

		<liferay-ui:section>
			<aui:fieldset>
				<aui:select label="authentication-type" name="preferences--authType--" value="<%= authType %>">
					<aui:option label="default" value="" />
					<aui:option label="by-email-address" value="<%= CompanyConstants.AUTH_TYPE_EA %>" />
					<aui:option label="by-screen-name" value="<%= CompanyConstants.AUTH_TYPE_SN %>" />
					<aui:option label="by-user-id" value="<%= CompanyConstants.AUTH_TYPE_ID %>" />
				</aui:select>
			</aui:fieldset>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= emailFromName %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= emailFromAddress %>" />
			</aui:fieldset>
		</liferay-ui:section>

		<liferay-ui:section>
			<div class="alert alert-info">
				<liferay-ui:message key="enter-custom-values-or-leave-it-blank-to-use-the-default-portal-settings" />
			</div>

			<aui:fieldset>
				<aui:select label="language" name="emailPasswordSentlanguageId" onChange='<%= renderResponse.getNamespace() + "updateLanguage(this.value);" %>'>

					<%
					Locale[] locales = LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId());

					for (int i = 0; i < locales.length; i++) {
						String style = StringPool.BLANK;

						if (Validator.isNotNull(portletPreferences.getValue("emailPasswordSentSubject_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK)) ||
							Validator.isNotNull(portletPreferences.getValue("emailPasswordSentBody_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK))) {

							style = "font-weight: bold;";
						}
					%>

						<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" style="<%= style %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

					<%
					}
					%>

				</aui:select>

				<liferay-ui:email-notifications-settings
					emailBody='<%= PrefsParamUtil.getString(portletPreferences, request, "emailPasswordSentBody_" + currentLanguageId, ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_SENT_BODY)) %>'
					emailDefinitionTerms="<%= LoginUtil.getEmailDefinitionTerms(renderRequest, emailFromAddress, emailFromName, false) %>"
					emailParam="emailPasswordSent"
					emailSubject='<%= PrefsParamUtil.getString(portletPreferences, request, "emailPasswordSentSubject_" + currentLanguageId, ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT)) %>'
					languageId="<%= currentLanguageId %>"
					showEmailEnabled="<%= false %>"
				/>
			</aui:fieldset>
		</liferay-ui:section>

		<liferay-ui:section>
			<div class="alert alert-info">
				<liferay-ui:message key="enter-custom-values-or-leave-it-blank-to-use-the-default-portal-settings" />
			</div>

			<aui:fieldset>
				<aui:select label="language" name="emailPasswordResetlanguageId" onChange='<%= renderResponse.getNamespace() + "updateLanguage(this.value);" %>'>

					<%
					Locale[] locales = LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId());

					for (int i = 0; i < locales.length; i++) {
						String style = StringPool.BLANK;

						if (Validator.isNotNull(portletPreferences.getValue("emailPasswordResetSubject_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK)) ||
							Validator.isNotNull(portletPreferences.getValue("emailPasswordResetBody_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK))) {

							style = "font-weight: bold;";
						}
					%>

						<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" style="<%= style %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

					<%
					}
					%>

				</aui:select>

				<liferay-ui:email-notifications-settings
					emailBody='<%= PrefsParamUtil.getString(portletPreferences, request, "emailPasswordResetBody_" + currentLanguageId, ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_RESET_BODY)) %>'
					emailDefinitionTerms="<%= LoginUtil.getEmailDefinitionTerms(renderRequest, emailFromAddress, emailFromName, true) %>"
					emailParam="emailPasswordReset"
					emailSubject='<%= PrefsParamUtil.getString(portletPreferences, request, "emailPasswordResetSubject_" + currentLanguageId, ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT)) %>'
					languageId="<%= currentLanguageId %>"
					showEmailEnabled="<%= false %>"
				/>
			</aui:fieldset>
		</liferay-ui:section>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />updateLanguage(languageId) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '';
		document.<portlet:namespace />fm.<portlet:namespace />languageId.value = languageId;

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveConfiguration() {
		<portlet:namespace />saveEmails();

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveEmails() {
		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailPasswordSentBody_<%= currentLanguageId %>--'].value = window['<portlet:namespace />emailPasswordSent'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailPasswordResetBody_<%= currentLanguageId %>--'].value = window['<portlet:namespace />emailPasswordReset'].getHTML();
		}
		catch (e) {
		}
	}
</aui:script>