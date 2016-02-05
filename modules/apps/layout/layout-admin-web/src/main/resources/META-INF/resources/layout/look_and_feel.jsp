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
Group group = layoutsAdminDisplayContext.getGroup();
long groupId = layoutsAdminDisplayContext.getGroupId();
long liveGroupId = layoutsAdminDisplayContext.getLiveGroupId();
boolean privateLayout = layoutsAdminDisplayContext.isPrivateLayout();
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

String rootNodeName = layoutsAdminDisplayContext.getRootNodeName();

PortletURL redirectURL = layoutsAdminDisplayContext.getRedirectURL();

Theme selTheme = null;
ColorScheme selColorScheme = null;

LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, privateLayout);

if (selLayout != null) {
	selTheme = selLayout.getTheme();
	selColorScheme = selLayout.getColorScheme();
}
else {
	selTheme = layoutSet.getTheme();
	selColorScheme = layoutSet.getColorScheme();
}

String cssText = null;

if ((selLayout != null) && !selLayout.isInheritLookAndFeel()) {
	cssText = selLayout.getCssText();
}
else {
	cssText = layoutSet.getCss();
}
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="look-and-feel" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<aui:input name="devices" type="hidden" value="regular" />

<liferay-util:buffer var="rootNodeNameLink">
	<c:choose>
		<c:when test="<%= themeDisplay.isStateExclusive() %>">
			<%= HtmlUtil.escape(rootNodeName) %>
		</c:when>
		<c:otherwise>
			<aui:a href="<%= redirectURL.toString() %>"><%= HtmlUtil.escape(rootNodeName) %></aui:a>
		</c:otherwise>
	</c:choose>
</liferay-util:buffer>

<%
String taglibLabel = null;

if (group.isLayoutPrototype()) {
	taglibLabel = LanguageUtil.get(request, "use-the-same-look-and-feel-of-the-pages-in-which-this-template-is-used");
}
else {
	taglibLabel = LanguageUtil.format(request, "use-the-same-look-and-feel-of-the-x", rootNodeNameLink, false);
}
%>

<%@ include file="/layout/look_and_feel_regular_browser.jspf" %>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />regularInheritLookAndFeel', '<portlet:namespace />inheritThemeOptions', '<portlet:namespace />themeOptions');
	Liferay.Util.toggleRadio('<portlet:namespace />regularUniqueLookAndFeel', '<portlet:namespace />themeOptions', '<portlet:namespace />inheritThemeOptions');
</aui:script>