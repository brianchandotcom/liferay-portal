<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
renderResponse.setTitle(LanguageUtil.get(request, "anonymous-account"));
%>

<portlet:actionURL name="/login/create_anonymous_account" var="createAnonymousAccountURL">
	<portlet:param name="mvcRenderCommandName" value="/login/create_anonymous_account" />
</portlet:actionURL>

<div class="login-container">
	<aui:form action="<%= createAnonymousAccountURL %>" method="post" name="fm">
		<aui:input name="saveLastPath" type="hidden" value="<%= false %>" />
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />

		<liferay-ui:error exception="<%= CaptchaConfigurationException.class %>" message="a-captcha-error-occurred-please-contact-an-administrator" />
		<liferay-ui:error exception="<%= CaptchaException.class %>" message="captcha-verification-failed" />
		<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
		<liferay-ui:error exception="<%= CompanyMaxUsersException.class %>" message="unable-to-create-user-account-because-the-maximum-number-of-users-has-been-reached" />
		<liferay-ui:error exception="<%= ContactNameException.MustHaveFirstName.class %>" message="please-enter-a-valid-first-name" />
		<liferay-ui:error exception="<%= ContactNameException.MustHaveLastName.class %>" message="please-enter-a-valid-last-name" />
		<liferay-ui:error exception="<%= ContactNameException.MustHaveValidFullName.class %>" message="please-enter-a-valid-first-middle-and-last-name" />
		<liferay-ui:error exception="<%= EmailAddressException.class %>" message="please-enter-a-valid-email-address" />

		<liferay-ui:error exception="<%= GroupFriendlyURLException.class %>">

			<%
			GroupFriendlyURLException gfurle = (GroupFriendlyURLException)errorException;
			%>

			<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.POSSIBLE_DUPLICATE %>">
				<liferay-ui:message key="the-friendly-url-generated-by-the-email-address-you-requested-may-conflict-with-an-existing-friendly-url" />
			</c:if>
		</liferay-ui:error>

		<liferay-ui:error exception="<%= RequiredFieldException.class %>" message="please-fill-out-all-required-fields" />
		<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeDuplicate.class %>" message="the-email-address-you-requested-is-already-taken" />
		<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeNull.class %>" message="please-enter-an-email-address" />
		<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBePOP3User.class %>" message="the-email-address-you-requested-is-reserved" />
		<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeReserved.class %>" message="the-email-address-you-requested-is-reserved" />
		<liferay-ui:error exception="<%= UserEmailAddressException.MustNotUseCompanyMx.class %>" message="the-email-address-you-requested-is-not-valid-because-its-domain-is-reserved" />
		<liferay-ui:error exception="<%= UserEmailAddressException.MustValidate.class %>" message="please-enter-a-valid-email-address" />

		<aui:fieldset column="<%= true %>">
			<clay:col
				md="6"
			>
				<aui:input model="<%= User.class %>" name="firstName" />

				<%
				FullNameDefinition fullNameDefinition = FullNameDefinitionFactory.getInstance(locale);
				%>

				<c:if test='<%= fullNameDefinition.isFieldRequired("last-name") %>'>
					<aui:input model="<%= User.class %>" name="lastName" required="<%= true %>" />
				</c:if>

				<aui:input model="<%= User.class %>" name="emailAddress" required="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_EMAIL_ADDRESS_REQUIRED, PropsValues.USERS_EMAIL_ADDRESS_REQUIRED) %>" />
			</clay:col>

			<clay:col
				md="6"
			>
				<c:if test="<%= captchaConfiguration.createAccountCaptchaEnabled() %>">
					<liferay-captcha:captcha />
				</c:if>
			</clay:col>
		</aui:fieldset>

		<aui:button-row>
			<aui:button type="submit" />
		</aui:button-row>
	</aui:form>

	<%@ include file="/navigation.jspf" %>
</div>