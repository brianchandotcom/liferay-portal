<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:fieldset>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<aui:input helpMessage="users-recovery-queries-enabled-help" label="users-recovery-queries-enabled" name='<%= "settings--" + PropsKeys.USERS_REMINDER_QUERIES_ENABLED + "--" %>' type="checkbox" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_ENABLED, PropsValues.USERS_REMINDER_QUERIES_ENABLED) %>" />

	<aui:input helpMessage="users-recovery-queries-custom-question-enabled-help" label="users-recovery-queries-custom-question-enabled" name='<%= "settings--" + PropsKeys.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED + "--" %>' type="checkbox" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED, PropsValues.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED) %>" />

	<aui:input helpMessage="users-recovery-queries-display-in-plain-text-help" label="users-recovery-queries-display-in-plain-text" name='<%= "settings--" + PropsKeys.USERS_REMINDER_QUERIES_DISPLAY_IN_PLAIN_TEXT + "--" %>' type="checkbox" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_DISPLAY_IN_PLAIN_TEXT, PropsValues.USERS_REMINDER_QUERIES_DISPLAY_IN_PLAIN_TEXT) %>" />

	<aui:input helpMessage="users-recovery-queries-required-help" label="users-recovery-queries-required" name='<%= "settings--" + PropsKeys.USERS_REMINDER_QUERIES_REQUIRED + "--" %>' type="checkbox" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_REQUIRED, PropsValues.USERS_REMINDER_QUERIES_REQUIRED) %>" />

	<aui:input helpMessage="users-recovery-queries-questions-help" label="users-recovery-queries-questions" name='<%= "settings--" + PropsKeys.USERS_REMINDER_QUERIES_QUESTIONS + "--" %>' type="textarea" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_QUESTIONS) %>" />
</aui:fieldset>