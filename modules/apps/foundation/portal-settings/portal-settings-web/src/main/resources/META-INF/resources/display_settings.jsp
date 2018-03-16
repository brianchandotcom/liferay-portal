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

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="displaySettings" />

<h3><liferay-ui:message key="display-settings" /></h3>

<h4><liferay-ui:message key="language-and-time-zone" /></h4>

<aui:fieldset>
	<liferay-ui:error exception="<%= LocaleException.class %>">

		<%
		LocaleException le = (LocaleException)errorException;
		%>

		<c:if test="<%= le.getType() == LocaleException.TYPE_DISPLAY_SETTINGS %>">
			<liferay-ui:message key="please-enter-a-valid-locale" />
		</c:if>
	</liferay-ui:error>

	<aui:select label="default-language" name="languageId">

		<%
		User defaultUser = company.getDefaultUser();

		String languageId = ParamUtil.getString(request, "languageId", defaultUser.getLanguageId());

		Locale companyLocale = LocaleUtil.fromLanguageId(languageId);

		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
		%>

			<aui:option label="<%= availableLocale.getDisplayName(locale) %>" lang="<%= LocaleUtil.toW3cLanguageId(availableLocale) %>" selected="<%= Objects.equals(companyLocale.getLanguage(), availableLocale.getLanguage()) && Objects.equals(companyLocale.getCountry(), availableLocale.getCountry()) %>" value="<%= LocaleUtil.toLanguageId(availableLocale) %>" />

		<%
		}
		%>

	</aui:select>

	<div id="<portlet:namespace />languageWarning"></div>

	<aui:fieldset cssClass="available-languages" label="available-languages">

		<%
		String[] availableLanguageIds = LocaleUtil.toLanguageIds(LanguageUtil.getAvailableLocales());
		%>

		<aui:input name='<%= "settings--" + PropsKeys.LOCALES + "--" %>' type="hidden" value="<%= StringUtil.merge(availableLanguageIds) %>" />

		<%

		// Left list

		List leftList = new ArrayList();

		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			leftList.add(new KeyValuePair(LocaleUtil.toLanguageId(availableLocale), availableLocale.getDisplayName(locale)));
		}

		leftList = ListUtil.sort(leftList, new KeyValuePairComparator(false, true));

		// Right list

		List rightList = new ArrayList();

		for (String propsValuesLanguageId : SetUtil.fromArray(PropsValues.LOCALES)) {
			if (!ArrayUtil.contains(availableLanguageIds, propsValuesLanguageId)) {
				Locale propsValuesLocale = LocaleUtil.fromLanguageId(propsValuesLanguageId);

				rightList.add(new KeyValuePair(propsValuesLanguageId, propsValuesLocale.getDisplayName(locale)));
			}
		}

		rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
		%>

		<liferay-ui:input-move-boxes
			leftBoxName="currentLanguageIds"
			leftList="<%= leftList %>"
			leftTitle="current"
			rightBoxName="availableLanguageIds"
			rightList="<%= rightList %>"
			rightTitle="available"
		/>
	</aui:fieldset>

	<%
	User defaultUser = company.getDefaultUser();

	String timeZoneId = ParamUtil.getString(request, "timeZoneId", defaultUser.getTimeZoneId());
	%>

	<aui:input label="time-zone" name="timeZoneId" type="timeZone" value="<%= timeZoneId %>" />
</aui:fieldset>

<h4><liferay-ui:message key="logo" /></h4>

<aui:fieldset>
	<aui:input label="allow-site-administrators-to-use-their-own-logo" name='<%= "settings--" + PropsKeys.COMPANY_SECURITY_SITE_LOGO + "--" %>' type="checkbox" value="<%= company.isSiteLogo() %>" />

	<liferay-ui:logo-selector
		currentLogoURL='<%= themeDisplay.getPathImage() + "/company_logo?img_id=" + company.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(company.getLogoId()) %>'
		defaultLogo="<%= company.getLogoId() == 0 %>"
		defaultLogoURL='<%= themeDisplay.getPathImage() + "/company_logo?img_id=0" %>'
		logoDisplaySelector=".company-logo"
		tempImageFileName="<%= String.valueOf(themeDisplay.getCompanyId()) %>"
	/>
</aui:fieldset>

<h4><liferay-ui:message key="look-and-feel" /></h4>

<aui:fieldset>
	<aui:select label="default-theme" name='<%= "settings--" + PropsKeys.DEFAULT_REGULAR_THEME_ID + "--" %>'>

		<%
		String defaultRegularThemeId = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.DEFAULT_REGULAR_THEME_ID, PropsValues.DEFAULT_REGULAR_THEME_ID);

		boolean deployed = false;

		List<Theme> themes = ThemeLocalServiceUtil.getPageThemes(company.getCompanyId(), 0, user.getUserId());

		for (Theme curTheme : themes) {
			if (Objects.equals(defaultRegularThemeId, curTheme.getThemeId())) {
				deployed = true;
			}
		%>

			<aui:option label="<%= curTheme.getName() %>" selected="<%= Objects.equals(defaultRegularThemeId, curTheme.getThemeId()) %>" value="<%= curTheme.getThemeId() %>" />

		<%
		}
		%>

		<c:if test="<%= !deployed %>">
			<aui:option label='<%= defaultRegularThemeId + "(" + LanguageUtil.get(request, "undeployed") + ")" %>' selected="<%= true %>" value="<%= defaultRegularThemeId %>" />
		</c:if>
	</aui:select>

	<aui:select label="default-control-panel-theme" name='<%= "settings--" + PropsKeys.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID + "--" %>'>

		<%
		String defaultControlPanelThemeId = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID, PropsValues.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID);

		boolean deployed = false;

		List<Theme> themes = ThemeLocalServiceUtil.getControlPanelThemes(company.getCompanyId(), user.getUserId());

		for (Theme curTheme : themes) {
			if (Objects.equals(defaultControlPanelThemeId, curTheme.getThemeId())) {
				deployed = true;
			}
		%>

			<aui:option label="<%= curTheme.getName() %>" selected="<%= Objects.equals(defaultControlPanelThemeId, curTheme.getThemeId()) %>" value="<%= curTheme.getThemeId() %>" />

		<%
		}
		%>

		<c:if test="<%= !deployed %>">
			<aui:option label='<%= defaultControlPanelThemeId + "(" + LanguageUtil.get(request, "undeployed") + ")" %>' selected="<%= true %>" value="<%= defaultControlPanelThemeId %>" />
		</c:if>
	</aui:select>
</aui:fieldset>

<aui:script use="aui-base,aui-alert">
	var languageSelectInput = A.one('#<portlet:namespace />languageId');

	if (languageSelectInput) {
		languageSelectInput.on(
			'change',
			function() {
				new A.Alert(
					{
						bodyContent: '<liferay-ui:message key="this-change-will-only-affect-the-newly-created-localized-content" />',
						boundingBox: '#<portlet:namespace />languageWarning',
						closeable: true,
						cssClass: 'alert-warning',
						destroyOnHide: false,
						render: true
					}
				);
			}
		);
	}
</aui:script>