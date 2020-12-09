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

ConfigurationScreen configurationScreen = (ConfigurationScreen)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_SCREEN);

PortletURL portletURL = renderResponse.createRenderURL();

if (Validator.isNull(redirect)) {
	redirect = portletURL.toString();
}

PortalUtil.addPortletBreadcrumbEntry(request, portletDisplay.getPortletDisplayName(), String.valueOf(renderResponse.createRenderURL()));

ConfigurationCategoryMenuDisplay configurationCategoryMenuDisplay = (ConfigurationCategoryMenuDisplay)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_CATEGORY_MENU_DISPLAY);

ConfigurationCategoryDisplay configurationCategoryDisplay = configurationCategoryMenuDisplay.getConfigurationCategoryDisplay();

String categoryDisplayName = configurationCategoryDisplay.getCategoryLabel(locale);

String viewCategoryHREF = ConfigurationCategoryUtil.getHREF(configurationCategoryMenuDisplay, liferayPortletResponse, renderRequest, renderResponse);

PortalUtil.addPortletBreadcrumbEntry(request, categoryDisplayName, viewCategoryHREF);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(portletURL.toString());

renderResponse.setTitle(categoryDisplayName);
%>

<clay:container-fluid>
	<clay:col
		size="12"
	>
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>
	</clay:col>
</clay:container-fluid>

<clay:container-fluid>
	<clay:row>
		<clay:col
			md="3"
		>
			<liferay-util:include page="/configuration_category_menu.jsp" servletContext="<%= application %>" />
		</clay:col>

		<clay:col
			md="9"
		>

			<%
			configurationScreen.render(request, PipingServletResponse.createPipingServletResponse(pageContext));
			%>

		</clay:col>
	</clay:row>
</clay:container-fluid>