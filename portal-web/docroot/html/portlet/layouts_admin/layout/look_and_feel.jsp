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
	<aui:input name="devices" type="hidden" value="regular,wap" />

	<%
	String taglibLabel = null;

	if (group.isLayoutPrototype()) {
		taglibLabel = LanguageUtil.get(pageContext, "use-the-same-look-and-feel-of-the-pages-in-which-this-template-is-used");
	}
	else {
		taglibLabel = LanguageUtil.format(pageContext, "use-the-same-look-and-feel-of-the-x-x", new String[] {HtmlUtil.escape(rootNodeName), redirectURL.toString()});
	}
	%>

	<liferay-ui:tabs
		names="regular-browsers,mobile-devices"
		refresh="<%= false %>"
	>
		<liferay-ui:section>
			<aui:input checked="<%= selLayout.isInheritLookAndFeel() %>" id="regularInheritLookAndFeel" label="<%= taglibLabel %>" name="regularInheritLookAndFeel" type="radio" value="<%= true %>" />

			<aui:input checked="<%= !selLayout.isInheritLookAndFeel() %>" id="regularUniqueLookAndFeel" label="define-a-specific-look-and-feel-for-this-page" name="regularInheritLookAndFeel" type="radio" value="<%= false %>" />

			<%
			List<Theme> themes = ThemeLocalServiceUtil.getThemes(company.getCompanyId(), liveGroupId, user.getUserId(), false);
			List<ColorScheme> colorSchemes = selTheme.getColorSchemes();

			request.setAttribute("edit_pages.jsp-themes", themes);
			request.setAttribute("edit_pages.jsp-colorSchemes", colorSchemes);
			request.setAttribute("edit_pages.jsp-selTheme", selTheme);
			request.setAttribute("edit_pages.jsp-selColorScheme", selColorScheme);
			request.setAttribute("edit_pages.jsp-device", "regular");
			request.setAttribute("edit_pages.jsp-editable", false);
			%>

			<div id="<portlet:namespace />inheritThemeOptions">
				<c:if test="<%= !group.isLayoutPrototype() %>">
					<liferay-util:include page="/html/portlet/layouts_admin/look_and_feel_themes.jsp" />
				</c:if>
			</div>

			<div id="<portlet:namespace />themeOptions">

				<%
				request.setAttribute("edit_pages.jsp-editable", true);
				%>

				<liferay-util:include page="/html/portlet/layouts_admin/look_and_feel_themes.jsp" />

				<legend><liferay-ui:message key="css" /></legend>

				<aui:input cssClass="lfr-textarea-container" label="insert-custom-css-that-will-be-loaded-after-the-theme" name="regularCss" type="textarea" value="<%= cssText %>" />
			</div>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:input checked="<%= selLayout.isInheritWapLookAndFeel() %>" id="wapInheritLookAndFeel" label="<%= taglibLabel %>" name="wapInheritLookAndFeel" type="radio" value="<%= true %>" />

			<aui:input checked="<%= !selLayout.isInheritWapLookAndFeel() %>" id="wapUniqueLookAndFeel" label="define-a-specific-look-and-feel-for-this-page" name="wapInheritLookAndFeel" type="radio" value="<%= false %>" />

			<%
			List<Theme> themes = ThemeLocalServiceUtil.getThemes(company.getCompanyId(), liveGroupId, user.getUserId(), true);
			List<ColorScheme> colorSchemes = selWapTheme.getColorSchemes();

			request.setAttribute("edit_pages.jsp-themes", themes);
			request.setAttribute("edit_pages.jsp-colorSchemes", colorSchemes);
			request.setAttribute("edit_pages.jsp-selTheme", selWapTheme);
			request.setAttribute("edit_pages.jsp-selColorScheme", selWapColorScheme);
			request.setAttribute("edit_pages.jsp-device", "wap");
			request.setAttribute("edit_pages.jsp-editable", false);
			%>

			<div id="<portlet:namespace />inheritWapThemeOptions">
				<c:if test="<%= !group.isLayoutPrototype() %>">
					<liferay-util:include page="/html/portlet/layouts_admin/look_and_feel_themes.jsp" />
				</c:if>
			</div>

			<div id="<portlet:namespace />wapThemeOptions">

				<%
				request.setAttribute("edit_pages.jsp-editable", true);
				%>

				<liferay-util:include page="/html/portlet/layouts_admin/look_and_feel_themes.jsp" />
			</div>
		</liferay-ui:section>
	</liferay-ui:tabs>
</aui:fieldset>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />regularInheritLookAndFeel', '<portlet:namespace />inheritThemeOptions', '<portlet:namespace />themeOptions');
	Liferay.Util.toggleRadio('<portlet:namespace />regularUniqueLookAndFeel', '<portlet:namespace />themeOptions', '<portlet:namespace />inheritThemeOptions');
	Liferay.Util.toggleRadio('<portlet:namespace />wapInheritLookAndFeel', '<portlet:namespace />inheritWapThemeOptions', '<portlet:namespace />wapThemeOptions');
	Liferay.Util.toggleRadio('<portlet:namespace />wapUniqueLookAndFeel', '<portlet:namespace />wapThemeOptions', '<portlet:namespace />inheritWapThemeOptions');
</aui:script>