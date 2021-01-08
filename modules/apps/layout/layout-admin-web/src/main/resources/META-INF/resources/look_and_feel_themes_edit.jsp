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
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

Theme selTheme = null;

if (selLayout != null) {
	selTheme = selLayout.getTheme();
}
else {
	selTheme = selLayoutSet.getTheme();
}
%>

<h1 class="h4 text-default"><liferay-ui:message key="current-theme" /></h1>

<div class="card-horizontal main-content-card">
	<div class="card-body">
		<div id="<portlet:namespace />currentThemeContainer">
			<liferay-util:include page="/look_and_feel_theme_details.jsp" servletContext="<%= application %>" />
		</div>

		<aui:input label="insert-custom-css-that-is-loaded-after-the-theme" name="regularCss" placeholder="css" type="textarea" value="<%= (selLayout != null) ? selLayout.getCssText() : selLayoutSet.getCss() %>" />
	</div>
</div>

<aui:button cssClass="btn btn-secondary" id="changeTheme" value="change-current-theme" />

<portlet:renderURL var="selectThemeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/select_theme.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<portlet:renderURL var="lookAndFeelDetailURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcPath" value="/look_and_feel_theme_details.jsp" />
</portlet:renderURL>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"changeThemeButtonId", liferayPortletResponse.getNamespace() + "changeTheme"
		).put(
			"initialSelectedThemeId", selTheme.getThemeId()
		).put(
			"lookAndFeelDetailURL", lookAndFeelDetailURL
		).put(
			"selectThemeURL", selectThemeURL
		).put(
			"themeContainerId", liferayPortletResponse.getNamespace() + "currentThemeContainer"
		).build()
	%>'
	module="js/LookAndFeelThemeEdit"
/>