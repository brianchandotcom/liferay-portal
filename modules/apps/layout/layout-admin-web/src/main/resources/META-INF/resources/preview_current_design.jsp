<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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