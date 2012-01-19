<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
User user2 = company.getDefaultUser();

Locale[] locales = LanguageUtil.getAvailableLocales();
String[] languageIds = LocaleUtil.toLanguageIds(locales);

String timeZoneId = ParamUtil.getString(request, "timeZoneId", user2.getTimeZoneId());
String languageId = ParamUtil.getString(request, "languageId", user2.getLanguageId());
String availableLocales = StringUtil.merge(languageIds);

boolean companySecurityCommunityLogo = company.isSiteLogo();
boolean deleteLogo = ParamUtil.getBoolean(request, "deleteLogo");

String defaultRegularThemeId = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.DEFAULT_REGULAR_THEME_ID, PropsValues.DEFAULT_REGULAR_THEME_ID);
String defaultWapThemeId = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.DEFAULT_WAP_THEME_ID, PropsValues.DEFAULT_WAP_THEME_ID);
String defaultControlPanelThemeId = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID, PropsValues.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID);
%>

<liferay-ui:error-marker key="errorSection" value="displaySettings" />

<h3><liferay-ui:message key="language-and-time-zone" /></h3>

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

	<aui:input cssClass="lfr-input-text-container" label="available-languages" name='<%= "settings--" + PropsKeys.LOCALES + "--" %>' type="text" value="<%= availableLocales %>" />

	<aui:input label="time-zone" name="timeZoneId" type="timeZone" value="<%= timeZoneId %>" />
</aui:fieldset>

<h3><liferay-ui:message key="logo" /></h3>

<aui:fieldset>
	<aui:input inlineLabel="right" label="allow-site-administrators-to-use-their-own-logo" name='<%= "settings--" + PropsKeys.COMPANY_SECURITY_SITE_LOGO + "--" %>' type="checkbox" value="<%= companySecurityCommunityLogo %>" />

	<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="editCompanyLogoURL">
		<portlet:param name="struts_action" value="/portal_settings/edit_company_logo" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-ui:logo-selector
		defaultLogoURL='<%= themeDisplay.getPathImage() + "/company_logo?img_id=0" %>'
		editLogoURL="<%= editCompanyLogoURL %>"
		imageId="<%= company.getLogoId() %>"
		logoDisplaySelector=".company-logo"
	/>
</aui:fieldset>

<h3><liferay-ui:message key="look-and-feel" /></h3>

<%
List<Theme> themes = null;
boolean deployed = false;
%>

<aui:fieldset>
	<aui:select label="default-regular-theme" name='<%= "settings--" + PropsKeys.DEFAULT_REGULAR_THEME_ID + "--" %>'>

		<%
		themes = ThemeLocalServiceUtil.getThemes(company.getCompanyId(), 0, user.getUserId(), false);
		deployed = false;

		for (Theme curTheme: themes) {
			if (Validator.equals(defaultRegularThemeId, curTheme.getThemeId())) {
				deployed = true;
			}
		%>

			<aui:option label="<%= curTheme.getName() %>" selected="<%= (Validator.equals(defaultRegularThemeId, curTheme.getThemeId())) %>" value="<%= curTheme.getThemeId() %>" />

		<%
		}
		%>

		<c:if test="<%= !deployed %>">
			<aui:option label='<%= defaultRegularThemeId + "(" + LanguageUtil.get(pageContext, "undeployed") + ")" %>' selected="<%= true %>" value="<%= defaultRegularThemeId %>" />
		</c:if>
	</aui:select>

	<aui:select label="default-mobile-theme" name='<%= "settings--" + PropsKeys.DEFAULT_REGULAR_THEME_ID + "--" %>'>

		<%
		themes = ThemeLocalServiceUtil.getThemes(company.getCompanyId(), 0, user.getUserId(), true);
		deployed = false;

		for (Theme curTheme: themes) {
			if (Validator.equals(defaultWapThemeId, curTheme.getThemeId())) {
				deployed = true;
			}
		%>

			<aui:option label="<%= curTheme.getName() %>" selected="<%= (Validator.equals(defaultWapThemeId, curTheme.getThemeId())) %>" value="<%= curTheme.getThemeId() %>" />

		<%
		}
		%>

		<c:if test="<%= !deployed %>">
			<aui:option label='<%= defaultWapThemeId + "(" + LanguageUtil.get(pageContext, "undeployed") + ")" %>' selected="<%= true %>" value="<%= defaultWapThemeId %>" />
		</c:if>
	</aui:select>

	<aui:select label="default-control-panel-theme" name='<%= "settings--" + PropsKeys.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID + "--" %>'>

		<%
		themes = ThemeLocalServiceUtil.getThemes(company.getCompanyId(), 0, user.getUserId(), false);
		deployed = false;

		Theme controlPanelTheme = ThemeLocalServiceUtil.getTheme(company.getCompanyId(), "controlpanel", false);

		if (controlPanelTheme != null) {
			themes.add(controlPanelTheme);
		}

		for (Theme curTheme: themes) {
			if (Validator.equals(defaultControlPanelThemeId, curTheme.getThemeId())) {
				deployed = true;
			}
		%>

			<aui:option label="<%= curTheme.getName() %>" selected="<%= (Validator.equals(defaultControlPanelThemeId, curTheme.getThemeId())) %>" value="<%= curTheme.getThemeId() %>" />

		<%
		}
		%>

		<c:if test="<%= !deployed %>">
			<aui:option label='<%= defaultControlPanelThemeId + "(" + LanguageUtil.get(pageContext, "undeployed") + ")" %>' selected="<%= true %>" value="<%= defaultControlPanelThemeId %>" />
		</c:if>
	</aui:select>
</aui:fieldset>