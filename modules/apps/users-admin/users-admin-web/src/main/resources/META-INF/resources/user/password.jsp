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
PasswordPolicy passwordPolicy = userDisplayContext.getPasswordPolicy();
User selUser = userDisplayContext.getSelectedUser();

boolean ldapPasswordPolicyEnabled = LDAPSettingsUtil.isPasswordPolicyEnabled(company.getCompanyId());
boolean passwordReset = false;
boolean passwordResetDisabled = false;

if (((selUser == null) || (selUser.getLastLoginDate() == null)) && (((passwordPolicy == null) && !ldapPasswordPolicyEnabled) || ((passwordPolicy != null) && passwordPolicy.isChangeable() && passwordPolicy.isChangeRequired()))) {
	passwordReset = true;
	passwordResetDisabled = true;
}
else {
	passwordReset = BeanParamUtil.getBoolean(selUser, request, "passwordReset");

	if ((passwordPolicy != null) && !passwordPolicy.isChangeable()) {
		passwordResetDisabled = true;
	}
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="password"
/>

<aui:model-context bean="<%= selUser %>" model="<%= User.class %>" />

<liferay-ui:error exception="<%= UserPasswordException.MustBeLonger.class %>">

	<%
	UserPasswordException.MustBeLonger upe = (UserPasswordException.MustBeLonger)errorException;
	%>

	<liferay-ui:message arguments="<%= String.valueOf(upe.minLength) %>" key="that-password-is-too-short" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= UserPasswordException.MustComplyWithModelListeners.class %>" message="that-password-is-invalid-please-enter-a-different-password" />

<liferay-ui:error exception="<%= UserPasswordException.MustComplyWithRegex.class %>">

	<%
	UserPasswordException.MustComplyWithRegex upe = (UserPasswordException.MustComplyWithRegex)errorException;
	%>

	<liferay-ui:message arguments="<%= upe.regex %>" key="that-password-does-not-comply-with-the-regular-expression" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= UserPasswordException.MustHaveMoreAlphanumeric.class %>">

	<%
	UserPasswordException.MustHaveMoreAlphanumeric upe = (UserPasswordException.MustHaveMoreAlphanumeric)errorException;
	%>

	<liferay-ui:message arguments="<%= String.valueOf(upe.minAlphanumeric) %>" key="that-password-must-contain-at-least-x-alphanumeric-characters" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= UserPasswordException.MustHaveMoreLowercase.class %>">

	<%
	UserPasswordException.MustHaveMoreLowercase upe = (UserPasswordException.MustHaveMoreLowercase)errorException;
	%>

	<liferay-ui:message arguments="<%= String.valueOf(upe.minLowercase) %>" key="that-password-must-contain-at-least-x-lowercase-characters" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= UserPasswordException.MustHaveMoreNumbers.class %>">

	<%
	UserPasswordException.MustHaveMoreNumbers upe = (UserPasswordException.MustHaveMoreNumbers)errorException;
	%>

	<liferay-ui:message arguments="<%= String.valueOf(upe.minNumbers) %>" key="that-password-must-contain-at-least-x-numbers" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= UserPasswordException.MustHaveMoreSymbols.class %>">

	<%
	UserPasswordException.MustHaveMoreSymbols upe = (UserPasswordException.MustHaveMoreSymbols)errorException;
	%>

	<liferay-ui:message arguments="<%= String.valueOf(upe.minSymbols) %>" key="that-password-must-contain-at-least-x-symbols" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= UserPasswordException.MustHaveMoreUppercase.class %>">

	<%
	UserPasswordException.MustHaveMoreUppercase upe = (UserPasswordException.MustHaveMoreUppercase)errorException;
	%>

	<liferay-ui:message arguments="<%= String.valueOf(upe.minUppercase) %>" key="that-password-must-contain-at-least-x-uppercase-characters" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= UserPasswordException.MustMatch.class %>" message="the-passwords-you-entered-do-not-match" />
<liferay-ui:error exception="<%= UserPasswordException.MustMatchCurrentPassword.class %>" message="the-password-you-entered-for-the-current-password-does-not-match-your-current-password" />
<liferay-ui:error exception="<%= UserPasswordException.MustNotBeChanged.class %>" message="passwords-may-not-be-changed-under-the-current-password-policy" />

<liferay-ui:error exception="<%= UserPasswordException.MustNotBeChangedYet.class %>">

	<%
	UserPasswordException.MustNotBeChangedYet upe = (UserPasswordException.MustNotBeChangedYet)errorException;
	%>

	<liferay-ui:message arguments="<%= String.valueOf(upe.changeableDate) %>" key="you-cannot-change-your-password-yet" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= UserPasswordException.MustNotBeEqualToCurrent.class %>" message="your-new-password-cannot-be-the-same-as-your-old-password-please-enter-a-different-password" />
<liferay-ui:error exception="<%= UserPasswordException.MustNotBeNull.class %>" message="the-password-cannot-be-blank" />
<liferay-ui:error exception="<%= UserPasswordException.MustNotBeRecentlyUsed.class %>" message="that-password-has-already-been-used-please-enter-a-different-password" />
<liferay-ui:error exception="<%= UserPasswordException.MustNotBeTrivial.class %>" message="that-password-uses-common-words-please-enter-a-password-that-is-harder-to-guess-i-e-contains-a-mix-of-numbers-and-letters" />
<liferay-ui:error exception="<%= UserPasswordException.MustNotContainDictionaryWords.class %>" message="that-password-uses-common-dictionary-words" />

<div class="sheet-section">
	<h3 class="sheet-subtitle"><liferay-ui:message key="password" /></h3>

	<!-- Begin LPS-38289 and LPS-55993 and LPS-61876 -->

	<input class="hide" type="password" />
	<input class="hide" type="password" />

	<!-- End LPS-38289 and LPS-55993 and LPS-61876 -->

	<c:if test="<%= portletName.equals(myAccountPortletId) %>">
		<aui:input autocomplete="off" label="current-password" name="password0" required="<%= true %>" size="30" type="password" />
	</c:if>

	<aui:input autocomplete="off" label="new-password" name="password1" required="<%= true %>" size="30" type="password" />

	<aui:input autocomplete="off" label="enter-again" name="password2" required="<%= true %>" size="30" type="password">
		<aui:validator name="equalTo">
			'#<portlet:namespace />password1'
		</aui:validator>
	</aui:input>

	<c:if test="<%= (selUser == null) || (user.getUserId() != selUser.getUserId()) %>">
		<aui:input disabled="<%= passwordResetDisabled %>" label="require-password-reset" name="passwordReset" type="checkbox" value="<%= passwordReset %>" />
	</c:if>
</div>

<c:if test="<%= PropsValues.USERS_REMINDER_QUERIES_ENABLED && portletName.equals(myAccountPortletId) %>">
	<div class="sheet-section">
		<h3 class="sheet-subtitle"><liferay-ui:message key="reminder" /></h3>

		<%
		boolean hasCustomQuestion = true;
		%>

		<%@ include file="/user/password_reminder_query_questions.jspf" %>

		<c:if test="<%= PropsValues.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED %>">
			<div class="<%= hasCustomQuestion ? "" : "hide" %>" id="<portlet:namespace />customQuestionDiv">
				<aui:input autocomplete='<%= PropsValues.COMPANY_SECURITY_PASSWORD_REMINDER_QUERY_FORM_AUTOCOMPLETE ? "on" : "off" %>' fieldParam="reminderQueryCustomQuestion" label="custom-question" name="reminderQueryQuestion" />
			</div>
		</c:if>

		<aui:input autocomplete='<%= PropsValues.COMPANY_SECURITY_PASSWORD_REMINDER_QUERY_FORM_AUTOCOMPLETE ? "on" : "off" %>' label="answer" maxlength="<%= ModelHintsConstants.TEXT_MAX_LENGTH %>" name="reminderQueryAnswer" size="50" value="<%= selUser.getReminderQueryAnswer() %>" />
	</div>

	<aui:script sandbox="<%= true %>">
		var reminderQueryQuestionSelect = document.getElementById(
			'<portlet:namespace />reminderQueryQuestion'
		);

		if (reminderQueryQuestionSelect) {
			reminderQueryQuestionSelect.addEventListener('change', function(event) {
				var customQuestion =
					event.currentTarget.value === '<%= UsersAdmin.CUSTOM_QUESTION %>';

				var focusInput;

				if (customQuestion) {
					var reminderQueryCustomQuestionInput = document.getElementById(
						'<portlet:namespace />reminderQueryCustomQuestion'
					);

					if (reminderQueryCustomQuestionInput) {

						<%
						for (String question : PropsValues.USERS_REMINDER_QUERIES_QUESTIONS) {
						%>

							if (
								reminderQueryCustomQuestionInput.value ===
								'<%= UnicodeFormatter.toString(question) %>'
							) {
								reminderQueryCustomQuestionInput.value = '';
							}

						<%
						}
						%>

						focusInput = reminderQueryCustomQuestionInput;
					}
				}
				else {
					focusInput = '#<portlet:namespace />reminderQueryAnswer';
				}

				var customQuestionDiv = document.getElementById(
					'<portlet:namespace />customQuestionDiv'
				);

				if (customQuestionDiv) {
					if (!customQuestion) {
						customQuestionDiv.classList.add('hide');
					}
					else {
						customQuestionDiv.classList.remove('hide');
					}
				}

				Liferay.Util.focusFormField(focusInput);
			});
		}
	</aui:script>
</c:if>