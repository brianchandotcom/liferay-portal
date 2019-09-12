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

LayoutSet layoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

Theme rootTheme = layoutSet.getTheme();

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

String rootNodeName = layoutsAdminDisplayContext.getRootNodeName();

PortletURL redirectURL = layoutsAdminDisplayContext.getRedirectURL();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="look-and-feel"
/>

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<aui:input name="devices" type="hidden" value="regular" />

<liferay-util:buffer
	var="rootNodeNameLink"
>
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

<aui:input checked="<%= selLayout.isInheritLookAndFeel() %>" id="regularInheritLookAndFeel" label="<%= taglibLabel %>" name="regularInheritLookAndFeel" type="radio" value="<%= true %>" />

<aui:input checked="<%= !selLayout.isInheritLookAndFeel() %>" id="regularUniqueLookAndFeel" label="define-a-specific-look-and-feel-for-this-page" name="regularInheritLookAndFeel" type="radio" value="<%= false %>" />

<c:if test="<%= !group.isLayoutPrototype() %>">
	<div class="lfr-inherit-theme-options" id="<portlet:namespace />inheritThemeOptions">
		<liferay-util:include page="/look_and_feel_themes.jsp" servletContext="<%= application %>">
			<liferay-util:param name="companyId" value="<%= String.valueOf(group.getCompanyId()) %>" />
			<liferay-util:param name="editable" value="<%= Boolean.FALSE.toString() %>" />
			<liferay-util:param name="themeId" value="<%= rootTheme.getThemeId() %>" />
		</liferay-util:include>
	</div>
</c:if>

<div class="lfr-theme-options" id="<portlet:namespace />themeOptions">
	<liferay-util:include page="/look_and_feel_themes.jsp" servletContext="<%= application %>" />
</div>

<aui:script>
	Liferay.Util.toggleRadio(
		'<portlet:namespace />regularInheritLookAndFeel',
		'<portlet:namespace />inheritThemeOptions',
		'<portlet:namespace />themeOptions'
	);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />regularUniqueLookAndFeel',
		'<portlet:namespace />themeOptions',
		'<portlet:namespace />inheritThemeOptions'
	);
</aui:script>