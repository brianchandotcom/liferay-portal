<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/portal/init.jsp" %>

<%
String currentURL = PortalUtil.getCurrentURL(request);

String referer = ParamUtil.getString(request, WebKeys.REFERER, currentURL);

if (referer.equals(themeDisplay.getPathMain() + "/portal/update_reminder_query")) {
	referer = themeDisplay.getPathMain() + "?doAsUserId=" + themeDisplay.getDoAsUserId();
}
%>

<div class="mt-4 sheet sheet-lg">
	<div class="sheet-header">
		<div class="autofit-padded-no-gutters-x autofit-row">
			<div class="autofit-col autofit-col-expand">
				<h2 class="sheet-title">
					<liferay-ui:message key="password-recovery-question-and-answer" />
				</h2>
			</div>

			<div class="autofit-col">
				<%@ include file="/html/portal/select_language.jspf" %>
			</div>
		</div>
	</div>

	<div class="sheet-text">
		<aui:form action='<%= themeDisplay.getPathMain() + "/portal/update_reminder_query" %>' autocomplete='<%= PropsValues.COMPANY_SECURITY_PASSWORD_REMINDER_QUERY_FORM_AUTOCOMPLETE ? "on" : "off" %>' cssClass="update-reminder-query" method="post" name="fm">
			<aui:input name="p_auth" type="hidden" value="<%= AuthTokenUtil.getToken(request) %>" />
			<aui:input name="doAsUserId" type="hidden" value="<%= themeDisplay.getDoAsUserId() %>" />
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= referer %>" />

			<c:if test="<%= SessionErrors.contains(request, UserReminderQueryException.class.getName()) %>">
				<div class="alert alert-danger">
					<liferay-ui:message key="reminder-query-and-answer-cannot-be-empty" />
				</div>
			</c:if>

			<aui:fieldset>
				<aui:select cssClass="reminder-query-question" label="question" name="reminderQueryQuestion">

					<%
					for (String question : user.getReminderQueryQuestions()) {
					%>

						<aui:option label="<%= question %>" />

					<%
					}
					%>

					<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED, PropsValues.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED) %>">
						<aui:option label="<%= UsersAdmin.CUSTOM_QUESTION %>" />
					</c:if>
				</aui:select>

				<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED, PropsValues.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED) %>">
					<div class="hide" id="customQuestionContainer">
						<aui:input bean="<%= user %>" cssClass="reminder-query-custom" fieldParam="reminderQueryCustomQuestion" label="" model="<%= User.class %>" name="reminderQueryQuestion" />
					</div>
				</c:if>

				<%
				String answer = user.getReminderQueryAnswer();

				if (!PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_DISPLAY_IN_PLAIN_TEXT, PropsValues.USERS_REMINDER_QUERIES_DISPLAY_IN_PLAIN_TEXT) && Validator.isNotNull(answer)) {
					answer = Portal.TEMP_OBFUSCATION_VALUE;
				}
				%>

				<aui:input autocomplete="off" cssClass="reminder-query-answer" label="answer[noun]" maxlength="<%= ModelHintsConstants.TEXT_MAX_LENGTH %>" name="reminderQueryAnswer" required="<%= true %>" showRequiredLabel="<%= false %>" size="50" type='<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_DISPLAY_IN_PLAIN_TEXT, PropsValues.USERS_REMINDER_QUERIES_DISPLAY_IN_PLAIN_TEXT) ? "text" : "password" %>' value="<%= answer %>" />
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:form>
	</div>
</div>

<script>
	(function() {
		var customQuestionContainer = document.getElementById('customQuestionContainer');
		var reminderQueryQuestion = document.getElementById('reminderQueryQuestion');

		if (customQuestionContainer && reminderQueryQuestion) {
			if (reminderQueryQuestion.value === '<%= UsersAdmin.CUSTOM_QUESTION %>') {
				customQuestionContainer.classList.remove('hide');
			}
			else {
				customQuestionContainer.classList.add('hide');
			}

			reminderQueryQuestion.addEventListener(
				'change',
				function(event) {
					if (reminderQueryQuestion.value === '<%= UsersAdmin.CUSTOM_QUESTION %>') {
						<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED, PropsValues.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED) %>">
							customQuestionContainer.classList.remove('hide');

							Liferay.Util.focusFormField('#reminderQueryCustomQuestion');
						</c:if>
					}
					else {
						customQuestionContainer.classList.add('hide');

						Liferay.Util.focusFormField('#reminderQueryAnswer');
					}
				}
			);
		}
	})();
</script>