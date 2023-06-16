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
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

if (Validator.isNull(backURL)) {
	backURL = PortalUtil.getLayoutFullURL(layoutsAdminDisplayContext.getSelLayout(), themeDisplay);
}

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

renderResponse.setTitle(layoutsAdminDisplayContext.getConfigurationTitle(selLayout, locale));
%>

<clay:container-fluid>
	<liferay-frontend:form-navigator
		formModelBean="<%= selLayout %>"
		id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_LAYOUT_DESIGN %>"
		showButtons="<%= false %>"
		type="<%= FormNavigatorConstants.FormNavigatorType.SHEET_SECTIONS %>"
	/>
</clay:container-fluid>