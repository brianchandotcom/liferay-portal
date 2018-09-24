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
OrganizationScreenNavigationDisplayContext organizationScreenNavigationDisplayContext = (OrganizationScreenNavigationDisplayContext)request.getAttribute(UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT);

Organization organization = organizationScreenNavigationDisplayContext.getOrganization();

String reminderQueries = ParamUtil.getString(request, "reminderQueries");

String currentLanguageId = LanguageUtil.getLanguageId(request);
Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

Set<Locale> locales = LanguageUtil.getAvailableLocales();

if ((organization != null) && Validator.isNull(reminderQueries)) {
	reminderQueries = StringUtil.merge(organization.getReminderQueryQuestions(defaultLocale), StringPool.NEW_LINE);
}

Map<Locale, String> reminderQueriesMap = LocalizationUtil.getLocalizationMap(renderRequest, "reminderQueries");
%>

<div class="sheet-text">
	<liferay-ui:message key="specify-custom-security-questions-for-the-users-of-this-organization" />
</div>

<aui:fieldset>
	<aui:input id="reminderQueries" label="security-questions" localized="<%= true %>" name="reminderQueries" type="textarea" />
</aui:fieldset>

<aui:script sandbox="<%= true %>">
	var lastLanguageId = '<%= currentLanguageId %>';
	var reminderQueriesChanged = false;
	var reminderQueriesTemp = $('#<portlet:namespace />reminderQueries_temp');

	function updateReminderQueriesLanguage() {
		var selLanguageId = $(document.<portlet:namespace />fm).fm('reminderQueryLanguageId').val();

		if (reminderQueriesChanged && (lastLanguageId != '<%= defaultLanguageId %>')) {
			$('#<portlet:namespace />reminderQueries_' + lastLanguageId).val(reminderQueriesTemp.val());

			reminderQueriesChanged = false;
		}

		if (selLanguageId) {
			updateReminderQueriesLanguageTemps(selLanguageId);
		}

		reminderQueriesTemp.toggleClass('hide', !selLanguageId);

		lastLanguageId = selLanguageId;
	}

	function updateReminderQueriesLanguageTemps(lang) {
		if (lang != '<%= defaultLanguageId %>') {
			var reminderQueriesValue = $('#<portlet:namespace />reminderQueries_' + lang).val();

			if (!reminderQueriesValue) {
				reminderQueriesValue = $('#<portlet:namespace />reminderQueries_<%= defaultLanguageId %>').val();
			}

			reminderQueriesTemp.val(reminderQueriesValue);
		}
	}

	var reminderQueriesHandle = Liferay.on('submitForm', updateReminderQueriesLanguage);

	function clearReminderQueriesHandle(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			reminderQueriesHandle.detach();

			Liferay.detach('destroyPortlet', clearReminderQueriesHandle);
		}
	}

	updateReminderQueriesLanguageTemps(lastLanguageId);

	Liferay.on('destroyPortlet', clearReminderQueriesHandle);

	$('#<portlet:namespace />reminderQueryLanguageId').on('change', updateReminderQueriesLanguage);

	reminderQueriesTemp.on(
		'change',
		function() {
			reminderQueriesChanged = true;
		}
	);
</aui:script>