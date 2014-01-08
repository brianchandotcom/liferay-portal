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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Group group = (Group)request.getAttribute("edit_pages.jsp-group");
long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

String rootNodeName = (String)request.getAttribute("edit_pages.jsp-rootNodeName");

PortletURL redirectURL = (PortletURL)request.getAttribute("edit_pages.jsp-redirectURL");

Theme selTheme = null;
ColorScheme selColorScheme = null;

Theme selWapTheme = null;
ColorScheme selWapColorScheme = null;

LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, privateLayout);

if (selLayout != null) {
	selTheme = selLayout.getTheme();
	selColorScheme = selLayout.getColorScheme();

	selWapTheme = selLayout.getWapTheme();
	selWapColorScheme = selLayout.getWapColorScheme();
}
else {
	selTheme = layoutSet.getTheme();
	selColorScheme = layoutSet.getColorScheme();

	selWapTheme = layoutSet.getWapTheme();
	selWapColorScheme = layoutSet.getWapColorScheme();
}

String cssText = null;

if ((selLayout != null) && !selLayout.isInheritLookAndFeel()) {
	cssText = selLayout.getCssText();
}
else {
	cssText = layoutSet.getCss();
}
%>

<liferay-ui:error-marker key="errorSection" value="look-and-feel" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<h3><liferay-ui:message key="look-and-feel" /></h3>

<aui:fieldset>
	<aui:input name="devices" type="hidden" value='<%= PropsValues.MOBILE_DEVICE_STYLING_WAP_ENABLED ? "regular,wap" : "regular" %>' />

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
		taglibLabel = LanguageUtil.get(pageContext, "use-the-same-look-and-feel-of-the-pages-in-which-this-template-is-used");
	}
	else {
		taglibLabel = LanguageUtil.format(pageContext, "use-the-same-look-and-feel-of-the-x", rootNodeNameLink);
	}
	%>

	<c:choose>
		<c:when test="<%= PropsValues.MOBILE_DEVICE_STYLING_WAP_ENABLED %>">
			<liferay-ui:tabs
				names="regular-browsers,mobile-devices"
				refresh="<%= false %>"
			>
				<liferay-ui:section>
					<%@ include file="/html/portlet/layouts_admin/layout/look_and_feel_regular_browser.jspf" %>
				</liferay-ui:section>

				<liferay-ui:section>
					<%@ include file="/html/portlet/layouts_admin/layout/look_and_feel_wap_browser.jspf" %>
				</liferay-ui:section>
			</liferay-ui:tabs>
		</c:when>
		<c:otherwise>
			<%@ include file="/html/portlet/layouts_admin/layout/look_and_feel_regular_browser.jspf" %>
		</c:otherwise>
	</c:choose>
</aui:fieldset>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />regularInheritLookAndFeel', '<portlet:namespace />inheritThemeOptions', '<portlet:namespace />themeOptions');
	Liferay.Util.toggleRadio('<portlet:namespace />regularUniqueLookAndFeel', '<portlet:namespace />themeOptions', '<portlet:namespace />inheritThemeOptions');
	Liferay.Util.toggleRadio('<portlet:namespace />wapInheritLookAndFeel', '<portlet:namespace />inheritWapThemeOptions', '<portlet:namespace />wapThemeOptions');
	Liferay.Util.toggleRadio('<portlet:namespace />wapUniqueLookAndFeel', '<portlet:namespace />wapThemeOptions', '<portlet:namespace />inheritWapThemeOptions');
</aui:script>