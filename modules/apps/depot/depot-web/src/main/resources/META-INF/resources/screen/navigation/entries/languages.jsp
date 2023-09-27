<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DepotEntry depotEntry = (DepotEntry)request.getAttribute(DepotAdminWebKeys.DEPOT_ENTRY);

Group group = depotEntry.getGroup();

UnicodeProperties typeSettingsUnicodeProperties = group.getTypeSettingsProperties();

boolean inheritLocales = GetterUtil.getBoolean(typeSettingsUnicodeProperties.getProperty(GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES), true);
%>

<liferay-ui:error exception="<%= LocaleException.class %>">

	<%
	LocaleException le = (LocaleException)errorException;
	%>

	<c:choose>
		<c:when test="<%= le.getType() == LocaleException.TYPE_DEFAULT %>">
			<liferay-ui:message key="you-cannot-remove-a-language-that-is-the-current-default-language" />
		</c:when>
		<c:when test="<%= le.getType() == LocaleException.TYPE_DISPLAY_SETTINGS %>">
			<liferay-ui:message arguments='<%= "<em>" + StringUtil.merge(LocaleUtil.toDisplayNames(le.getSourceAvailableLocales(), locale), StringPool.COMMA_AND_SPACE) + "</em>" %>' key="please-select-the-available-languages-of-the-asset-library-among-the-available-languages-of-the-portal-x" translateArguments="<%= false %>" />
		</c:when>
	</c:choose>
</liferay-ui:error>

<liferay-ui:error exception="<%= DuplicateGroupException.class %>">
	<liferay-ui:message key="there-is-already-a-workspace-with-the-same-name-in-the-selected-default-language.-please-enter-a-unique-name" />
</liferay-ui:error>

<liferay-ui:error exception="<%= DepotEntryNameException.class %>">
	<liferay-ui:message key="asset-library-name-is-required-for-the-default-language" />
</liferay-ui:error>

<liferay-ui:error exception="<%= GroupKeyException.class %>">
	<liferay-ui:message key="please-enter-a-valid-name" />
</liferay-ui:error>

<div class="site-languages">

	<%
	User guestUser = company.getGuestUser();
	%>

	<react:component
		module="js/Languages.es"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"availableLocales", DepotLanguageUtil.getAvailableLocalesJSONArray(locale)
			).put(
				"defaultLocaleId", LocaleUtil.toLanguageId(guestUser.getLocale())
			).put(
				"inheritLocales", inheritLocales
			).put(
				"siteAvailableLocales", DepotLanguageUtil.getDepotAvailableLocalesJSONArray(group, locale)
			).put(
				"siteDefaultLocaleId", LocaleUtil.toLanguageId(PortalUtil.getSiteDefaultLocale(group.getGroupId()))
			).put(
				"translatedLanguages", group.getNameMap()
			).build()
		%>'
	/>
</div>