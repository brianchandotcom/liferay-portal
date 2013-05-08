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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);

String reminderQueries = ParamUtil.getString(request, "reminderQueries");

String currentLanguageId = LanguageUtil.getLanguageId(request);
Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

Locale[] locales = LanguageUtil.getAvailableLocales();

if ((organization != null) && Validator.isNull(reminderQueries)) {
	reminderQueries = StringUtil.merge(organization.getReminderQueryQuestions(defaultLocale), StringPool.NEW_LINE);
}

Map<Locale, String> reminderQueriesMap = LocalizationUtil.getLocalizedParameter(renderRequest, "reminderQueries");
%>

<h3><liferay-ui:message key="reminder-queries" /></h3>

<div class="alert alert-info">
	<liferay-ui:message key="specify-custom-reminder-queries-for-the-users-of-this-organization" />
</div>

<aui:fieldset cssClass="reminder">
	<aui:input label='<%= LanguageUtil.get(pageContext, "default-language") + StringPool.COLON + StringPool.SPACE + defaultLocale.getDisplayName(defaultLocale) %>' name="reminderQueries" type="textarea" value="<%= reminderQueries %>" />

	<aui:select cssClass="localized-language-selector" label='<%= LanguageUtil.get(pageContext, "localized-language") + StringPool.COLON %>' name="reminderQueryLanguageId" onChange='<%= renderResponse.getNamespace() + "updateReminderQueriesLanguage();" %>'>
		<aui:option value="" />

		<%
		for (int i = 0; i < locales.length; i++) {
			if (locales[i].equals(defaultLocale)) {
				continue;
			}

			String curReminderQueries = reminderQueriesMap.get(locales[i]);

			if ((organization != null) && Validator.isNull(curReminderQueries)) {
				curReminderQueries = StringUtil.merge(organization.getReminderQueryQuestions(locales[i]), StringPool.NEW_LINE);
			}

			String style = StringPool.BLANK;

			if (Validator.isNotNull(curReminderQueries)) {
				style = "font-weight: bold;";
			}
		%>

			<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= (currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i]))) %>" style="<%= style %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

		<%
		}
		%>

	</aui:select>

	<%
	for (int i = 0; i < locales.length; i++) {
		if (locales[i].equals(defaultLocale)) {
			continue;
		}

		String curReminderQueries = reminderQueriesMap.get(locales[i]);

		if ((organization != null) && Validator.isNull(curReminderQueries)) {
			curReminderQueries = StringUtil.merge(organization.getReminderQueryQuestions(locales[i]), StringPool.NEW_LINE);
		}
	%>

		<aui:input name='<%= "reminderQueries_" + LocaleUtil.toLanguageId(locales[i]) %>' type="hidden" value="<%= curReminderQueries %>" />

	<%
	}
	%>

	<aui:input label="" name="reminderQueries_temp" onChange='<%= renderResponse.getNamespace() + "onReminderQueriesChanged();" %>' type="textarea" />
</aui:fieldset>

<aui:script>
	var reminderQueriesChanged = false;
	var lastLanguageId = "<%= currentLanguageId %>";

	function <portlet:namespace />onReminderQueriesChanged() {
		reminderQueriesChanged = true;
	}

	Liferay.provide(
		window,
		'<portlet:namespace />updateReminderQueriesLanguage',
		function() {
			var A = AUI();

			if (lastLanguageId != "<%= defaultLanguageId %>") {
				if (reminderQueriesChanged) {
					var reminderQueriesValue = A.one("#<portlet:namespace />reminderQueries_temp").attr("value");

					if (reminderQueriesValue == null) {
						reminderQueriesValue = "";
					}

					A.one("#<portlet:namespace />reminderQueries_" + lastLanguageId).attr("value", reminderQueriesValue);

					reminderQueriesChanged = false;
				}
			}

			var selLanguageId = "";

			for (var i = 0; i < document.<portlet:namespace />fm.<portlet:namespace />reminderQueryLanguageId.length; i++) {
				if (document.<portlet:namespace />fm.<portlet:namespace />reminderQueryLanguageId.options[i].selected) {
					selLanguageId = document.<portlet:namespace />fm.<portlet:namespace />reminderQueryLanguageId.options[i].value;

					break;
				}
			}

			if (selLanguageId != "") {
				<portlet:namespace />updateReminderQueriesLanguageTemps(selLanguageId);

				A.one("#<portlet:namespace />reminderQueries_temp").show();
			}
			else {
				A.one("#<portlet:namespace />reminderQueries_temp").hide();
			}

			lastLanguageId = selLanguageId;
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateReminderQueriesLanguageTemps',
		function(lang) {
			var A = AUI();

			if (lang != "<%= defaultLanguageId %>") {
				var reminderQueriesLang = A.one("#<portlet:namespace />reminderQueries_" + lang);

				if (reminderQueriesLang) {
					var reminderQueriesValue = reminderQueriesLang.attr("value");
				}

				var defaultReminderQueriesLang = A.one("#<portlet:namespace />reminderQueries_<%= defaultLanguageId %>");

				if (defaultReminderQueriesLang) {
					var defaultReminderQueriesValue = defaultReminderQueriesLan.attr("value");
				}

				if (defaultReminderQueriesValue == null) {
					defaultReminderQueriesValue = "";
				}

				if ((reminderQueriesValue == null) || (reminderQueriesValue == "")) {
					A.one("#<portlet:namespace />reminderQueries_temp").attr("value", defaultReminderQueriesValue);
				}
				else {
					A.one("#<portlet:namespace />reminderQueries_temp").attr("value", reminderQueriesValue);
				}
			}
		},
		['aui-base']
	);

	<portlet:namespace />updateReminderQueriesLanguageTemps(lastLanguageId);

	Liferay.on(
		'submitForm',
		function(event, data) {
			<portlet:namespace />updateReminderQueriesLanguage();
		}
	);
</aui:script>