<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SiteNavigationMenuItem siteNavigationMenuItem = (SiteNavigationMenuItem)request.getAttribute(SiteNavigationWebKeys.SITE_NAVIGATION_MENU_ITEM);

String url = StringPool.BLANK;
boolean useNewTab = false;

if (siteNavigationMenuItem != null) {
	UnicodeProperties typeSettingsUnicodeProperties = UnicodePropertiesBuilder.fastLoad(
		siteNavigationMenuItem.getTypeSettings()
	).build();

	url = typeSettingsUnicodeProperties.getProperty("url");
	useNewTab = GetterUtil.getBoolean(typeSettingsUnicodeProperties.getProperty("useNewTab", Boolean.FALSE.toString()));
}
%>

<aui:input defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>" label="name" localized="<%= true %>" maxlength='<%= ModelHintsUtil.getMaxLength(SiteNavigationMenuItem.class.getName(), "name") %>' name="name" placeholder="name" required="<%= true %>" type="text" value='<%= SiteNavigationMenuItemUtil.getSiteNavigationMenuItemXML(siteNavigationMenuItem, "name") %>' />

<aui:input label="url" name="TypeSettingsProperties--url--" placeholder="http://" required="<%= true %>" value="<%= url %>">
	<aui:validator name="urlAllowRelative" />
</aui:input>

<aui:input checked="<%= useNewTab %>" label="open-in-a-new-tab" name="TypeSettingsProperties--useNewTab--" type="checkbox" />