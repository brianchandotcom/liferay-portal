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

<aui:input name="useCustomTitle" type="toggle-switch" />

<span class="field-row">
	<aui:input inlineField="<%= true %>" label="" name="customTitle" />

	<aui:select inlineField="<%= true %>" label="" name="lfr-portlet-language" title="language">

		<%
		for (Locale curLocale : LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId())) {
		%>

			<aui:option label="<%= curLocale.getDisplayName(locale) %>" value="<%= LocaleUtil.toLanguageId(curLocale) %>" />

		<%
		}
		%>

	</aui:select>
</span>

<aui:select label="link-portlet-urls-to-page" name="linkToLayoutUuid">
	<aui:option label="current-page" value="" />

	<%
	String linkToLayoutUuid = StringPool.BLANK;

	Group group = layout.getGroup();

	List<LayoutDescription> layoutDescriptions = LayoutListUtil.getLayoutDescriptions(layout.getGroup().getGroupId(), layout.isPrivateLayout(), group.getGroupKey(), locale);

	for (LayoutDescription layoutDescription : layoutDescriptions) {
		Layout layoutDescriptionLayout = LayoutLocalServiceUtil.fetchLayout(layoutDescription.getPlid());

		if (layoutDescriptionLayout != null) {
	%>

			<aui:option label="<%= layoutDescription.getDisplayName() %>" selected="<%= layoutDescriptionLayout.getUuid().equals(linkToLayoutUuid) %>" value="<%= layoutDescriptionLayout.getUuid() %>" />

	<%
		}
	}
	%>

</aui:select>

<aui:select label="portlet-decorators" name="portletDecoratorId">

	<%
	List<PortletDecorator> portletDecorators = theme.getPortletDecorators();

	for (PortletDecorator portletDecorator : portletDecorators) {
	%>

		<aui:option label="<%= portletDecorator.getName() %>" selected="<%= portletDecorator.isDefaultPortletDecorator() %>" value="<%= portletDecorator.getPortletDecoratorId() %>" />

	<%
	}
	%>

</aui:select>

<span class="alert alert-info form-hint hide" id="border-note">
	<liferay-ui:message key="this-change-will-only-be-shown-after-you-refresh-the-page" />
</span>