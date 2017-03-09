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
boolean useCustomTitle = GetterUtil.getBoolean(portletSetup.getValue("portletSetupUseCustomTitle", StringPool.BLANK));
%>

<aui:input name="useCustomTitle" type="toggle-switch" value="<%= useCustomTitle %>" />

<%
Portlet portlet = PortletLocalServiceUtil.getPortletById(portletResource);

Map<Locale, String> customTitleMap = new HashMap<>();

for (Locale curLocale : LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId())) {
	String languageId = LocaleUtil.toLanguageId(curLocale);

	String portletTitle = PortalUtil.getPortletTitle(portlet, application, curLocale);

	String portletSetupTitle = portletSetup.getValue("portletSetupTitle_" + languageId, portletTitle);

	customTitleMap.put(curLocale, portletSetupTitle);
}

String customTitleXml = LocalizationUtil.updateLocalization(customTitleMap, StringPool.BLANK, "customTitle", LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));
%>

<aui:field-wrapper cssClass="lfr-input-text-container">
	<liferay-ui:input-localized defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>" name="customTitle" xml="<%= customTitleXml %>" />
</aui:field-wrapper>

<%
String linkToLayoutUuid = portletSetup.getValue("portletSetupLinkToLayoutUuid", StringPool.BLANK);
%>

<aui:select label="link-portlet-urls-to-page" name="linkToLayoutUuid">
	<aui:option label="current-page" selected="<%= Objects.equals(StringPool.BLANK, linkToLayoutUuid) %>" value="" />

	<%
	Group group = layout.getGroup();

	List<LayoutDescription> layoutDescriptions = LayoutListUtil.getLayoutDescriptions(group.getGroupId(), layout.isPrivateLayout(), group.getGroupKey(), locale);

	for (LayoutDescription layoutDescription : layoutDescriptions) {
		Layout layoutDescriptionLayout = LayoutLocalServiceUtil.fetchLayout(layoutDescription.getPlid());

		if (layoutDescriptionLayout != null) {
	%>

			<aui:option label="<%= layoutDescription.getDisplayName() %>" selected="<%= Objects.equals(layoutDescriptionLayout.getUuid(), linkToLayoutUuid) %>" value="<%= layoutDescriptionLayout.getUuid() %>" />

	<%
		}
	}
	%>

</aui:select>

<%
String portletDecoratorId = portletSetup.getValue("portletSetupPortletDecoratorId", StringPool.BLANK);
%>

<aui:select label="portlet-decorators" name="portletDecoratorId">

	<%
	for (PortletDecorator portletDecorator : theme.getPortletDecorators()) {
	%>

		<aui:option label="<%= portletDecorator.getName() %>" selected="<%= Objects.equals(portletDecorator.getPortletDecoratorId(), portletDecoratorId) %>" value="<%= portletDecorator.getPortletDecoratorId() %>" />

	<%
	}
	%>

</aui:select>

<span class="alert alert-info form-hint hide" id="border-note">
	<liferay-ui:message key="this-change-will-only-be-shown-after-you-refresh-the-page" />
</span>