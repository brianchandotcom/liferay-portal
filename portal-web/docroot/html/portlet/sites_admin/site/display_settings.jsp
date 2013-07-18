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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("site.liveGroup");

Locale[] locales = LanguageUtil.getAvailableLocales(liveGroup.getGroupId());
String[] languageIds = LocaleUtil.toLanguageIds(locales);

String languageId = LocaleUtil.toLanguageId(PortalUtil.getSiteDefaultLocale(liveGroup.getGroupId()));
String availableLocales = StringUtil.merge(languageIds);

UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

boolean inheritLocales = GetterUtil.getBoolean(groupTypeSettings.getProperty("inheritLocales"), true);
%>

<liferay-ui:error-marker key="errorSection" value="displaySettings" />

<h3><liferay-ui:message key="language" /></h3>

<aui:input checked="<%= inheritLocales %>" id="inheritLocales" label="use-the-default-language-options" name="TypeSettingsProperties--inheritLocales--" type="radio" value="<%= true %>" />

<aui:input checked="<%= !inheritLocales %>" id="customLocales" label="define-a-custom-default-language-and-additional-available-languages-for-this-site" name="TypeSettingsProperties--inheritLocales--" type="radio" value="<%= false %>" />

<aui:fieldset id="customLocalesFieldset">

	<%
	User user2 = company.getDefaultUser();
	Locale defaultLocale = user2.getLocale();
	%>

	<aui:fieldset cssClass="default-language" label="default-language">
		<%= defaultLocale.getDisplayName(locale) %>
	</aui:fieldset>

	<aui:fieldset cssClass="available-languages" label="available-languages">

		<%
		Locale[] defaultAvailableLocales = LanguageUtil.getAvailableLocales();

		for (Locale availableLocale : defaultAvailableLocales) {
		%>

			<%= availableLocale.getDisplayName(locale) %>,

		<%
		}
		%>

	</aui:fieldset>
</aui:fieldset>

<aui:fieldset id="inheritLocalesFieldset">
	<liferay-ui:error exception="<%= LocaleException.class %>" message="please-enter-a-valid-locale" />

	<aui:fieldset cssClass="default-language" label="default-language">
		<aui:select label="" name="TypeSettingsProperties--languageId--">

			<%
			Locale locale2 = LocaleUtil.fromLanguageId(languageId);

			for (int i = 0; i < locales.length; i++) {
			%>

				<aui:option label="<%= locales[i].getDisplayName(locale) %>" lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>" selected="<%= (locale2.getLanguage().equals(locales[i].getLanguage()) && locale2.getCountry().equals(locales[i].getCountry())) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

			<%
			}
			%>

		</aui:select>
	</aui:fieldset>

	<aui:fieldset cssClass="available-languages" label="available-languages">
		<aui:input name='<%= "TypeSettingsProperties--" + PropsKeys.LOCALES + "--" %>' type="hidden" value="<%= availableLocales %>" />

		<%
		Locale[] availableLanguageIdsSet = LanguageUtil.getAvailableLocales();

		// Left list

		List leftList = new ArrayList();

		for (String curLanguageId : languageIds) {
			leftList.add(new KeyValuePair(curLanguageId, LocaleUtil.fromLanguageId(curLanguageId).getDisplayName(locale)));
		}

		// Right list

		List rightList = new ArrayList();

		Arrays.sort(languageIds);

		for (Locale curLocale : availableLanguageIdsSet) {
			String curLanguageId = LocaleUtil.toLanguageId(curLocale);

			if (Arrays.binarySearch(languageIds, curLanguageId) < 0) {
				rightList.add(new KeyValuePair(curLanguageId, curLocale.getDisplayName(locale)));
			}
		}

		rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
		%>

		<liferay-ui:input-move-boxes
			leftBoxName="currentLanguageIds"
			leftList="<%= leftList %>"
			leftReorder="true"
			leftTitle="current"
			rightBoxName="availableLanguageIds"
			rightList="<%= rightList %>"
			rightTitle="available"
		/>
	</aui:fieldset>
</aui:fieldset>

<aui:script use="liferay-util-list-fields">
	Liferay.provide(
		window,
		'<portlet:namespace />saveLocales',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace /><%= PropsKeys.LOCALES %>.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentLanguageIds);
		},
		['liferay-util-list-fields']
	);
</aui:script>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />customLocales', '<portlet:namespace />inheritLocalesFieldset', '<portlet:namespace />customLocalesFieldset');
	Liferay.Util.toggleRadio('<portlet:namespace />inheritLocales', '<portlet:namespace />customLocalesFieldset', '<portlet:namespace />inheritLocalesFieldset');
</aui:script>