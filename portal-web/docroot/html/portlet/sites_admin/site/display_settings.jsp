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
User user2 = company.getDefaultUser();

Locale[] locales = LanguageUtil.getAvailableLocales();
String[] languageIds = LocaleUtil.toLanguageIds(locales);

String languageId = ParamUtil.getString(request, "languageId", user2.getLanguageId());
String availableLocales = StringUtil.merge(languageIds);
%>

<liferay-ui:error-marker key="errorSection" value="displaySettings" />

<h3><liferay-ui:message key="language" /></h3>

<aui:fieldset>
	<liferay-ui:error exception="<%= LocaleException.class %>" message="please-enter-a-valid-locale" />

	<aui:select label="default-language" name="languageId">

		<%
		Locale locale2 = LocaleUtil.fromLanguageId(languageId);

		for (int i = 0; i < locales.length; i++) {
		%>

			<aui:option label="<%= locales[i].getDisplayName(locale) %>" lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>" selected="<%= (locale2.getLanguage().equals(locales[i].getLanguage()) && locale2.getCountry().equals(locales[i].getCountry())) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

		<%
		}
		%>

	</aui:select>

	<aui:fieldset cssClass="available-languages" label="available-languages">
		<aui:input name='<%= "settings--" + PropsKeys.LOCALES + "--" %>' type="hidden" value="<%= availableLocales %>" />

		<%
		Set<String> availableLanguageIdsSet = SetUtil.fromArray(PropsValues.LOCALES);

		// Left list

		List leftList = new ArrayList();

		for (String curLanguageId : languageIds) {
			leftList.add(new KeyValuePair(curLanguageId, LocaleUtil.fromLanguageId(curLanguageId).getDisplayName(locale)));
		}

		// Right list

		List rightList = new ArrayList();

		Arrays.sort(languageIds);

		for (String curLanguageId : availableLanguageIdsSet) {
			if (Arrays.binarySearch(languageIds, curLanguageId) < 0) {
				rightList.add(new KeyValuePair(curLanguageId, LocaleUtil.fromLanguageId(curLanguageId).getDisplayName(locale)));
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