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

<%@ include file="/html/portlet/password_policies_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

PasswordPolicy passwordPolicy = (PasswordPolicy)request.getAttribute(WebKeys.PASSWORD_POLICY);

long passwordPolicyId = BeanParamUtil.getLong(passwordPolicy, request, "passwordPolicyId");

boolean defaultPolicy = BeanParamUtil.getBoolean(passwordPolicy, request, "defaultPolicy");
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= (passwordPolicy == null) %>"
	title='<%= (passwordPolicy == null) ? "new-password-policy" : passwordPolicy.getName() %>'
/>

<portlet:actionURL var="editPasswordPolicyURL">
	<portlet:param name="struts_action" value="/password_policies_admin/edit_password_policy" />
</portlet:actionURL>

<aui:form action="<%= editPasswordPolicyURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (passwordPolicy == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="passwordPolicyId" type="hidden" value="<%= passwordPolicyId %>" />

	<liferay-ui:error exception="<%= DuplicatePasswordPolicyException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= PasswordPolicyNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= passwordPolicy %>" model="<%= PasswordPolicy.class %>" />

	<liferay-ui:panel-container extended="<%= true %>" id="passwordPoliciesAdminPasswordPolicyPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="passwordPoliciesAdminPasswordPolicyGeneralPanel" persistState="<%= true %>" title="general">
			<aui:fieldset>
				<aui:input autoFocus="<%= (!defaultPolicy && windowState.equals(WindowState.MAXIMIZED)) %>" disabled="<%= defaultPolicy %>" name="name" />

				<aui:input autoFocus="<%= (defaultPolicy && windowState.equals(WindowState.MAXIMIZED)) %>" name="description" />

				<aui:input helpMessage="changeable-help" name="changeable" />

				<div class="password-policy-options" id="<portlet:namespace />changeableSettings">
					<aui:input helpMessage="change-required-help" name="changeRequired" />

					<aui:select helpMessage="minimum-age-help" label="minimum-age" name="minAge">
						<aui:option label="none" value="0" />

						<%
						for (int i = 0; i < 15; i++) {
						%>

							<aui:option label="<%= LanguageUtil.getTimeDescription(pageContext, _DURATIONS[i] * 1000) %>" value="<%= _DURATIONS[i] %>" />

						<%
						}
						%>

					</aui:select>
				</div>

				<aui:select helpMessage="reset-ticket-max-age-help" name="resetTicketMaxAge">
					<aui:option label="eternal" value="0" />

					<%
					for (int i = 0; i < 15; i++) {
					%>

						<aui:option label="<%= LanguageUtil.getTimeDescription(pageContext, _DURATIONS[i] * 1000) %>" value="<%= _DURATIONS[i] %>" />

					<%
					}
					%>

				</aui:select>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="passwordPoliciesAdminPasswordPolicySyntaxPanel" persistState="<%= true %>" title="password-syntax-checking">
			<aui:fieldset>

				<aui:input helpMessage="syntax-checking-enabled-help" label="syntax-checking-enabled" name="checkSyntax" />

				<div class="password-policy-options" id="<portlet:namespace />syntaxSettings">
					<aui:input helpMessage="allow-dictionary-words-help" name="allowDictionaryWords" />

					<aui:input helpMessage="minimum-alpha-numeric-help" label="minimum-alpha-numeric" name="minAlphanumeric" />

					<aui:input helpMessage="minimum-length-help" label="minimum-length" name="minLength" />

					<aui:input helpMessage="minimum-lower-case-help" label="minimum-lower-case" name="minLowerCase" />

					<aui:input helpMessage="minimum-numbers-help" label="minimum-numbers" name="minNumbers" />

					<aui:input helpMessage="minimum-symbols-help" label="minimum-symbols" name="minSymbols" />

					<aui:input helpMessage="minimum-upper-case-help" label="minimum-upper-case" name="minUpperCase" />

					<%
					String taglinbHelpMessage = LanguageUtil.format(pageContext, "regular-expression-help", new Object[] {"<a href=\"http://docs.oracle.com/javase/tutorial/essential/regex\" target=\"_blank\">", "</a>"}, false);
					%>

					<aui:input helpMessage="<%= taglinbHelpMessage %>" label="regular-expression" name="regex" />
				</div>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="passwordPoliciesAdminPasswordPolicyHistoryPanel" persistState="<%= true %>" title="password-history">
			<aui:fieldset>

				<aui:input helpMessage="history-enabled-help" label="history-enabled" name="history" />

				<div class="password-policy-options" id="<portlet:namespace />historySettings">
					<aui:select helpMessage="history-count-help" name="historyCount">

						<%
						for (int i = 2; i < 25; i++) {
						%>

							<aui:option label="<%= i %>" />

						<%
						}
						%>

					</aui:select>
				</div>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="passwordPoliciesAdminPasswordPolicyExpirationPanel" persistState="<%= true %>" title="password-expiration">
			<aui:fieldset>

				<aui:input helpMessage="expiration-enabled-help" label="expiration-enabled" name="expireable" />

				<div class="password-policy-options" id="<portlet:namespace />expirationSettings">
					<aui:select helpMessage="maximum-age-help" label="maximum-age" name="maxAge">

						<%
						for (int i = 15; i < _DURATIONS.length; i++) {
						%>

							<aui:option label="<%= LanguageUtil.getTimeDescription(pageContext, _DURATIONS[i] * 1000) %>" value="<%= _DURATIONS[i] %>" />

						<%
						}
						%>

					</aui:select>

					<aui:select helpMessage="warning-time-help" name="warningTime">

						<%
						for (int i = 7; i < 16; i++) {
						%>

							<aui:option label="<%= LanguageUtil.getTimeDescription(pageContext, _DURATIONS[i] * 1000) %>" value="<%= _DURATIONS[i] %>" />

						<%
						}
						%>

						<aui:option label="do-not-warn" value="<%= 0 %>" />
					</aui:select>

					<aui:input helpMessage="grace-limit-help" name="graceLimit" />
				</div>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="passwordPoliciesAdminPasswordPolicyLockoutPanel" persistState="<%= true %>" title="lockout">
			<aui:fieldset>
				<aui:input helpMessage="lockout-enabled-help" label="lockout-enabled" name="lockout" />

				<div class="password-policy-options" id="<portlet:namespace />lockoutSettings">
					<aui:input helpMessage="maximum-failure-help" label="maximum-failure" name="maxFailure" />

					<aui:select helpMessage="reset-failure-count-help" name="resetFailureCount">

						<%
						for (int i = 0; i < 15; i++) {
						%>

							<aui:option label="<%= LanguageUtil.getTimeDescription(pageContext, _DURATIONS[i] * 1000) %>" value="<%= _DURATIONS[i] %>" />

						<%
						}
						%>

					</aui:select>

					<aui:select helpMessage="lockout-duration-help" name="lockoutDuration">
						<aui:option label="until-unlocked-by-an-administrator" value="0" />

						<%
						for (int i = 0; i < 15; i++) {
						%>

							<aui:option label="<%= LanguageUtil.getTimeDescription(pageContext, _DURATIONS[i] * 1000) %>" value="<%= _DURATIONS[i] %>" />

						<%
						}
						%>

					</aui:select>
				</div>
			</aui:fieldset>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace />changeableCheckbox', '<portlet:namespace />changeableSettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />checkSyntaxCheckbox', '<portlet:namespace />syntaxSettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />historyCheckbox', '<portlet:namespace />historySettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />expireableCheckbox', '<portlet:namespace />expirationSettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />lockoutCheckbox', '<portlet:namespace />lockoutSettings');
</aui:script>

<%
if (passwordPolicy != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, passwordPolicy.getName(), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-user"), currentURL);
}
%>

<%!
private static final long[] _DURATIONS = {300, 600, 1800, 3600, 7200, 10800, 21600, 43200, 86400, 172800, 259200, 345600, 432000, 518400, 604800, 1209600, 1814400, 2419200, 4838400, 7862400, 15724800, 31449600};
%>