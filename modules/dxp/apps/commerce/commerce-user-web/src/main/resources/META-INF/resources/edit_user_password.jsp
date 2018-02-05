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
CommerceUserPasswordDisplayContext commerceUserPasswordDisplayContext = (CommerceUserPasswordDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

User selUser = commerceUserPasswordDisplayContext.getSelectedUser();

PasswordPolicy passwordPolicy = selUser.getPasswordPolicy();

boolean passwordReset = false;
boolean passwordResetDisabled = false;

if ((selUser.getLastLoginDate() == null) && ((passwordPolicy == null) || (passwordPolicy.isChangeable() && passwordPolicy.isChangeRequired()))) {
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

<portlet:actionURL name="editCommerceUser" var="editCommerceUserActionURL" />

<aui:form action="<%= editCommerceUserActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updatePassword" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="userId" type="hidden" value="<%= selUser.getUserId() %>" />

	<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="password" />

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

	<aui:fieldset>
		<aui:input autocomplete="off" label="new-password" name="password1" size="30" type="password" />

		<aui:input autocomplete="off" label="enter-again" name="password2" size="30" type="password">
			<aui:validator name="equalTo">
				'#<portlet:namespace />password1'
			</aui:validator>
		</aui:input>

		<c:if test="<%= (user.getUserId() != selUser.getUserId()) %>">
			<aui:input disabled="<%= passwordResetDisabled %>" label="require-password-reset" name="passwordReset" type="checkbox" value="<%= passwordReset %>" />
		</c:if>
	</aui:fieldset>

	<c:if test="<%= PropsValues.USERS_REMINDER_QUERIES_ENABLED %>">
		<h3><liferay-ui:message key="reminder" /></h3>

		<%
		boolean hasCustomQuestion = true;
		%>

		<aui:fieldset>
			<%@ include file="/password_reminder_query_questions.jspf" %>

			<c:if test="<%= PropsValues.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED %>">
				<div class="<%= hasCustomQuestion ? "" : "hide" %>" id="<portlet:namespace />customQuestionDiv">
					<aui:input autocomplete='<%= PropsValues.COMPANY_SECURITY_PASSWORD_REMINDER_QUERY_FORM_AUTOCOMPLETE ? "on" : "off" %>' fieldParam="reminderQueryCustomQuestion" label="custom-question" name="reminderQueryQuestion" />
				</div>
			</c:if>

			<aui:input autocomplete='<%= PropsValues.COMPANY_SECURITY_PASSWORD_REMINDER_QUERY_FORM_AUTOCOMPLETE ? "on" : "off" %>' label="answer" maxlength="<%= ModelHintsConstants.TEXT_MAX_LENGTH %>" name="reminderQueryAnswer" size="50" value="<%= selUser.getReminderQueryAnswer() %>" />
		</aui:fieldset>

		<aui:script sandbox="<%= true %>">
			var customQuestionDiv = $('#<portlet:namespace />customQuestionDiv');

			$('#<portlet:namespace />reminderQueryQuestion').on(
				'change',
				function(event) {
					var customQuestion = $(event.currentTarget).val() == '<%= UsersAdmin.CUSTOM_QUESTION %>';

					var focusInput;

					if (customQuestion) {
						var reminderQueryCustomQuestion = $('#<portlet:namespace />reminderQueryCustomQuestion');

						<%
						for (String question : PropsValues.USERS_REMINDER_QUERIES_QUESTIONS) {
						%>

							if (reminderQueryCustomQuestion.val() == '<%= UnicodeFormatter.toString(question) %>') {
								reminderQueryCustomQuestion.val('');
							}

						<%
						}
						%>

						focusInput = reminderQueryCustomQuestion;
					}
					else {
						focusInput = '#<portlet:namespace />reminderQueryAnswer';
					}

					customQuestionDiv.toggleClass('hide', !customQuestion);

					Liferay.Util.focusFormField(focusInput);
				}
			);
		</aui:script>
	</c:if>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>