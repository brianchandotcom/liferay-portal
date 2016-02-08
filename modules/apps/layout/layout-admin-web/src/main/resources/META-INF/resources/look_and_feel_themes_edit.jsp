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
ColorScheme selColorScheme = null;

if (selLayout != null) {
	selTheme = selLayout.getTheme();
	selColorScheme = selLayout.getColorScheme();
}
else {
	selTheme = selLayoutSet.getTheme();
	selColorScheme = selLayoutSet.getColorScheme();
}
%>

<aui:input name="regularThemeId" type="hidden" value="<%= selTheme.getThemeId() %>" />
<aui:input name="regularColorSchemeId" type="hidden" value="<%= selColorScheme.getColorSchemeId() %>" />

<h5 class="text-default"><liferay-ui:message key="current-theme" /></h5>

<div class="card-horizontal main-content-card">
	<div class="card-row card-row-padded">
		<liferay-util:include page="/look_and_feel_theme_details.jsp" servletContext="<%= application %>" />
	</div>
</div>