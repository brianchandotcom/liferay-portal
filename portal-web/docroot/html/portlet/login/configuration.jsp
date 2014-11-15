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

<%@ include file="/html/portlet/login/init.jsp" %>

<%
String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", LoginUtil.getEmailFromName(portletPreferences, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", LoginUtil.getEmailFromAddress(portletPreferences, company.getCompanyId()));
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
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
				<liferay-ui:email-notification-settings
					emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailPasswordSentBody", "preferences", ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_SENT_BODY)) %>'
					emailDefinitionTerms="<%= LoginUtil.getEmailDefinitionTerms(renderRequest, emailFromAddress, emailFromName, false) %>"
					emailParam="emailPasswordSent"
					emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailPasswordSentSubject", "preferences", ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT)) %>'
					showEmailEnabled="<%= false %>"
				/>
			</aui:fieldset>
		</liferay-ui:section>

		<liferay-ui:section>
			<div class="alert alert-info">
				<liferay-ui:message key="enter-custom-values-or-leave-it-blank-to-use-the-default-portal-settings" />
			</div>

			<aui:fieldset>
				<liferay-ui:email-notification-settings
					emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailPasswordResetBody", "preferences", ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_RESET_BODY)) %>'
					emailDefinitionTerms="<%= LoginUtil.getEmailDefinitionTerms(renderRequest, emailFromAddress, emailFromName, true) %>"
					emailParam="emailPasswordReset"
					emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailPasswordResetSubject", "preferences", ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT)) %>'
					showEmailEnabled="<%= false %>"
				/>
			</aui:fieldset>
		</liferay-ui:section>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>