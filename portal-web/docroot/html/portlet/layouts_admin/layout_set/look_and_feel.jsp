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
long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();
LayoutSet layoutSet = ((LayoutSet)request.getAttribute("edit_pages.jsp-selLayoutSet"));

Theme selTheme = layoutSet.getTheme();
ColorScheme selColorScheme = layoutSet.getColorScheme();

Theme selWapTheme = layoutSet.getWapTheme();
ColorScheme selWapColorScheme = layoutSet.getWapColorScheme();
%>

<liferay-ui:error-marker key="errorSection" value="look-and-feel" />

<aui:model-context bean="<%= layoutSet %>" model="<%= Layout.class %>" />

<h3><liferay-ui:message key="look-and-feel" /></h3>

<aui:fieldset>
	<aui:input name="devices" type="hidden" value="regular,wap" />

	<liferay-ui:tabs
		names="regular-browsers,mobile-devices"
		refresh="<%= false %>"
	>
		<liferay-ui:section>

			<%
			List<Theme> themes = ThemeLocalServiceUtil.getThemes(company.getCompanyId(), liveGroupId, user.getUserId(), false);
			List<ColorScheme> colorSchemes = selTheme.getColorSchemes();

			request.setAttribute("edit_pages.jsp-themes", themes);
			request.setAttribute("edit_pages.jsp-colorSchemes", colorSchemes);
			request.setAttribute("edit_pages.jsp-selTheme", selTheme);
			request.setAttribute("edit_pages.jsp-selColorScheme", selColorScheme);
			request.setAttribute("edit_pages.jsp-device", "regular");
			request.setAttribute("edit_pages.jsp-editable", true);
			%>

			<liferay-util:include page="/html/portlet/layouts_admin/look_and_feel_themes.jsp" />

			<legend><liferay-ui:message key="css" /></legend>

			<aui:input label="insert-custom-css-that-will-be-loaded-after-the-theme" name="regularCss" type="textarea" value="<%= layoutSet.getCss() %>" />
		</liferay-ui:section>

		<liferay-ui:section>

			<%
			List<Theme> themes = ThemeLocalServiceUtil.getThemes(company.getCompanyId(), liveGroupId, user.getUserId(), true);
			List<ColorScheme> colorSchemes = selWapTheme.getColorSchemes();

			request.setAttribute("edit_pages.jsp-themes", themes);
			request.setAttribute("edit_pages.jsp-colorSchemes", colorSchemes);
			request.setAttribute("edit_pages.jsp-selTheme", selWapTheme);
			request.setAttribute("edit_pages.jsp-selColorScheme", selWapColorScheme);
			request.setAttribute("edit_pages.jsp-device", "wap");
			request.setAttribute("edit_pages.jsp-editable", true);
			%>

			<liferay-util:include page="/html/portlet/layouts_admin/look_and_feel_themes.jsp" />
		</liferay-ui:section>
	</liferay-ui:tabs>
</aui:fieldset>