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

<aui:fieldset>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<aui:input helpMessage="users-reminder-queries-enabled-help" label="users-reminder-queries-enabled" name='<%= "settings--" + PropsKeys.USERS_REMINDER_QUERIES_ENABLED + "--" %>' type="checkbox" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_ENABLED, PropsValues.USERS_REMINDER_QUERIES_ENABLED) %>" />

	<aui:input helpMessage="users-reminder-queries-custom-question-enabled-help" label="users-reminder-queries-custom-question-enabled" name='<%= "settings--" + PropsKeys.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED + "--" %>' type="checkbox" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED, PropsValues.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED) %>" />

	<aui:input helpMessage="users-reminder-queries-display-in-plain-text-help" label="users-reminder-queries-display-in-plain-text" name='<%= "settings--" + PropsKeys.USERS_REMINDER_QUERIES_DISPLAY_IN_PLAIN_TEXT + "--" %>' type="checkbox" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_DISPLAY_IN_PLAIN_TEXT, PropsValues.USERS_REMINDER_QUERIES_DISPLAY_IN_PLAIN_TEXT) %>" />

	<aui:input helpMessage="users-reminder-queries-required-help" label="users-reminder-queries-required" name='<%= "settings--" + PropsKeys.USERS_REMINDER_QUERIES_REQUIRED + "--" %>' type="checkbox" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_REQUIRED, PropsValues.USERS_REMINDER_QUERIES_REQUIRED) %>" />

	<aui:input helpMessage="users-reminder-queries-questions-help" label="users-reminder-queries-questions" name='<%= "settings--" + PropsKeys.USERS_REMINDER_QUERIES_QUESTIONS + "--" %>' type="textarea" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.USERS_REMINDER_QUERIES_QUESTIONS) %>" />
</aui:fieldset>