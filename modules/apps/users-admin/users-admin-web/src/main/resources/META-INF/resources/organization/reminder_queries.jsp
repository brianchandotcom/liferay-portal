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
long organizationId = ParamUtil.getLong(request, "organizationId");

Organization organization = OrganizationServiceUtil.fetchOrganization(organizationId);

String reminderQueries = ParamUtil.getString(request, "reminderQueries");

String currentLanguageId = LanguageUtil.getLanguageId(request);
Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

Set<Locale> locales = LanguageUtil.getAvailableLocales();

if ((organization != null) && Validator.isNull(reminderQueries)) {
	reminderQueries = StringUtil.merge(organization.getReminderQueryQuestions(defaultLocale), StringPool.NEW_LINE);
}

Map<Locale, String> reminderQueriesMap = LocalizationUtil.getLocalizedParameter(renderRequest, "reminderQueries");
%>

<div class="alert alert-info">
	<liferay-ui:message key="specify-custom-reminder-queries-for-the-users-of-this-organization" />
</div>

<aui:fieldset cssClass="reminder">
	<aui:input label='<%= LanguageUtil.get(request, "default-language") + StringPool.COLON + StringPool.SPACE + defaultLocale.getDisplayName(defaultLocale) %>' name='<%= "reminderQueries_" + defaultLocale %>' type="textarea" value="<%= reminderQueries %>" />

	<aui:select cssClass="localized-language-selector" label='<%= LanguageUtil.get(request, "localized-language") + StringPool.COLON %>' name="reminderQueryLanguageId">
		<aui:option value="" />

		<%
		for (Locale curLocale : locales) {
			if (curLocale.equals(defaultLocale)) {
				continue;
			}

			String curReminderQueries = reminderQueriesMap.get(curLocale);

			if ((organization != null) && Validator.isNull(curReminderQueries)) {
				curReminderQueries = StringUtil.merge(organization.getReminderQueryQuestions(curLocale), StringPool.NEW_LINE);
			}

			String style = StringPool.BLANK;

			if (Validator.isNotNull(curReminderQueries)) {
				style = "font-weight: bold;";
			}
		%>

			<aui:option label="<%= curLocale.getDisplayName(locale) %>" selected="<%= (currentLanguageId.equals(LocaleUtil.toLanguageId(curLocale))) %>" style="<%= style %>" value="<%= LocaleUtil.toLanguageId(curLocale) %>" />

		<%
		}
		%>

	</aui:select>

	<%
	for (Locale curLocale : locales) {
		if (curLocale.equals(defaultLocale)) {
			continue;
		}

		String curReminderQueries = reminderQueriesMap.get(curLocale);

		if ((organization != null) && Validator.isNull(curReminderQueries)) {
			curReminderQueries = StringUtil.merge(organization.getReminderQueryQuestions(curLocale), StringPool.NEW_LINE);
		}
	%>

		<aui:input name='<%= "reminderQueries_" + LocaleUtil.toLanguageId(curLocale) %>' type="hidden" value="<%= curReminderQueries %>" />

	<%
	}
	%>

	<aui:input cssClass="hide" label="" name="reminderQueries_temp" title="reminder-queries" type="textarea" />
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