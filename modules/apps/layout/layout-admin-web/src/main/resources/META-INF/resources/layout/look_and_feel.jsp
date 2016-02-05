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

List<Theme> themes = ThemeLocalServiceUtil.getPageThemes(company.getCompanyId(), liveGroupId, user.getUserId());
List<ColorScheme> colorSchemes = selTheme.getColorSchemes();

request.setAttribute("edit_pages.jsp-themes", themes);
request.setAttribute("edit_pages.jsp-colorSchemes", colorSchemes);
request.setAttribute("edit_pages.jsp-selTheme", selTheme);
request.setAttribute("edit_pages.jsp-selColorScheme", selColorScheme);
request.setAttribute("edit_pages.jsp-device", "regular");
request.setAttribute("edit_pages.jsp-editable", Boolean.FALSE);
%>

<aui:input checked="<%= selLayout.isInheritLookAndFeel() %>" id="regularInheritLookAndFeel" label="<%= taglibLabel %>" name="regularInheritLookAndFeel" type="radio" value="<%= true %>" />

<aui:input checked="<%= !selLayout.isInheritLookAndFeel() %>" id="regularUniqueLookAndFeel" label="define-a-specific-look-and-feel-for-this-page" name="regularInheritLookAndFeel" type="radio" value="<%= false %>" />

<div class="lfr-inherit-theme-options" id="<portlet:namespace />inheritThemeOptions">
	<c:if test="<%= !group.isLayoutPrototype() %>">
		<liferay-util:include page="/look_and_feel_themes.jsp" servletContext="<%= application %>" />
	</c:if>
</div>

<div class="lfr-theme-options" id="<portlet:namespace />themeOptions">

	<%
	request.setAttribute("edit_pages.jsp-editable", Boolean.TRUE);
	%>

	<liferay-util:include page="/look_and_feel_themes.jsp" servletContext="<%= application %>" />

	<legend><liferay-ui:message key="css" /></legend>

	<aui:input cssClass="lfr-textarea-container" label="insert-custom-css-that-is-loaded-after-the-theme" name="regularCss" type="textarea" value="<%= cssText %>" />
</div>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />regularInheritLookAndFeel', '<portlet:namespace />inheritThemeOptions', '<portlet:namespace />themeOptions');
	Liferay.Util.toggleRadio('<portlet:namespace />regularUniqueLookAndFeel', '<portlet:namespace />themeOptions', '<portlet:namespace />inheritThemeOptions');
</aui:script>