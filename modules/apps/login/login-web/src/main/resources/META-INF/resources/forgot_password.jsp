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
User user2 = (User)request.getAttribute(WebKeys.FORGOT_PASSWORD_REMINDER_USER);

if (Validator.isNull(authType)) {
	authType = company.getAuthType();
}

Integer reminderAttempts = (Integer)portletSession.getAttribute(WebKeys.FORGOT_PASSWORD_REMINDER_ATTEMPTS);

if (reminderAttempts == null) {
	reminderAttempts = 0;
}

renderResponse.setTitle(LanguageUtil.get(request, "forgot-password"));
%>

<portlet:actionURL name="/login/forgot_password" var="forgotPasswordURL">
	<portlet:param name="mvcRenderCommandName" value="/login/forgot_password" />
</portlet:actionURL>

<div class="login-container">
	<aui:form action="<%= forgotPasswordURL %>" method="post" name="fm">
		<aui:input name="saveLastPath" type="hidden" value="<%= false %>" />

		<liferay-ui:error exception="<%= CaptchaConfigurationException.class %>" message="a-captcha-error-occurred-please-contact-an-administrator" />
		<liferay-ui:error exception="<%= CaptchaException.class %>" message="captcha-verification-failed" />
		<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
		<liferay-ui:error exception="<%= NoSuchUserException.class %>" message='<%= "the-" + TextFormatter.format(HtmlUtil.escape(authType), TextFormatter.K) + "-you-requested-is-not-registered-in-our-database" %>' />
		<liferay-ui:error exception="<%= RequiredReminderQueryException.class %>" message="you-have-not-configured-a-reminder-query" />
		<liferay-ui:error exception="<%= SendPasswordException.MustBeEnabled.class %>" message="password-recovery-is-disabled" />
		<liferay-ui:error exception="<%= UserActiveException.class %>" message="your-account-is-not-active" />
		<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeNull.class %>" message="please-enter-an-email-address" />
		<liferay-ui:error exception="<%= UserEmailAddressException.MustValidate.class %>" message="please-enter-a-valid-email-address" />
		<liferay-ui:error exception="<%= UserLockoutException.LDAPLockout.class %>" message="this-account-is-locked" />

		<liferay-ui:error exception="<%= UserLockoutException.PasswordPolicyLockout.class %>">

			<%
			UserLockoutException.PasswordPolicyLockout ule = (UserLockoutException.PasswordPolicyLockout)errorException;
			%>

			<c:choose>
				<c:when test="<%= ule.passwordPolicy.isRequireUnlock() %>">
					<liferay-ui:message key="this-account-is-locked" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message arguments="<%= ule.user.getUnlockDate() %>" key="this-account-is-locked-until-x" translateArguments="<%= false %>" />
				</c:otherwise>
			</c:choose>
		</liferay-ui:error>

		<liferay-ui:error exception="<%= UserReminderQueryException.class %>" message="your-answer-does-not-match-what-is-in-our-database" />

		<aui:fieldset>
			<c:choose>
				<c:when test="<%= user2 == null %>">

					<%
					String loginParameter = null;
					String loginLabel = null;

					if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
						loginParameter = "emailAddress";
						loginLabel = "email-address";
					}
					else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
						loginParameter = "screenName";
						loginLabel = "screen-name";
					}
					else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
						loginParameter = "userId";
						loginLabel = "id";
					}

					String loginValue = ParamUtil.getString(request, loginParameter);
					%>

					<aui:input name="step" type="hidden" value="1" />

					<c:if test="<%= !PropsValues.USERS_REMINDER_QUERIES_ENABLED %>">
						<portlet:renderURL var="redirectURL">
							<portlet:param name="mvcRenderCommandName" value="/login/login" />
						</portlet:renderURL>

						<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
					</c:if>

					<aui:input label="<%= loginLabel %>" name="<%= loginParameter %>" size="30" type="text" value="<%= loginValue %>">
						<aui:validator name="required" />
					</aui:input>

					<c:if test="<%= captchaConfiguration.sendPasswordCaptchaEnabled() %>">
						<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/login/captcha" var="captchaURL" />

						<liferay-captcha:captcha
							url="<%= captchaURL %>"
						/>
					</c:if>

					<aui:button-row>
						<aui:button type="submit" value='<%= PropsValues.USERS_REMINDER_QUERIES_ENABLED ? "next" : "send-new-password" %>' />
					</aui:button-row>
				</c:when>
				<c:when test="<%= (user2 != null) && Validator.isNotNull(user2.getEmailAddress()) %>">
					<aui:input name="step" type="hidden" value="2" />

					<portlet:renderURL var="redirectURL">
						<portlet:param name="mvcRenderCommandName" value="/login/login" />
					</portlet:renderURL>

					<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />

					<c:if test="<%= Validator.isNotNull(user2.getReminderQueryQuestion()) && Validator.isNotNull(user2.getReminderQueryAnswer()) %>">

						<%
						String login = null;

						if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
							login = user2.getEmailAddress();
						}
						else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
							login = user2.getScreenName();
						}
						else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
							login = String.valueOf(user2.getUserId());
						}
						%>

						<div class="alert alert-info">
							<liferay-ui:message arguments="<%= HtmlUtil.escape(login) %>" key="an-email-will-be-sent-to-x-if-you-can-correctly-answer-the-following-question" translateArguments="<%= false %>" />
						</div>

						<aui:input autoFocus="<%= true %>" label="<%= HtmlUtil.escape(LanguageUtil.get(request, user2.getReminderQueryQuestion())) %>" name="answer" type="text" />
					</c:if>

					<c:choose>
						<c:when test="<%= PropsValues.USERS_REMINDER_QUERIES_REQUIRED && !user2.hasReminderQuery() %>">
							<div class="alert alert-info">
								<liferay-ui:message key="the-password-cannot-be-reset-because-you-have-not-configured-a-reminder-query" />
							</div>
						</c:when>
						<c:otherwise>
							<c:if test="<%= reminderAttempts >= 3 %>">
								<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/login/captcha" var="captchaURL" />

								<liferay-captcha:captcha
									url="<%= captchaURL %>"
								/>
							</c:if>

							<aui:button-row>
								<aui:button type="submit" value='<%= company.isSendPasswordResetLink() ? "send-password-reset-link" : "send-new-password" %>' />
							</aui:button-row>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<div class="alert alert-warning">
						<liferay-ui:message key="the-system-cannot-send-you-a-new-password-because-you-have-not-provided-an-email-address" />
					</div>
				</c:otherwise>
			</c:choose>
		</aui:fieldset>
	</aui:form>

	<%@ include file="/navigation.jspf" %>
</div>