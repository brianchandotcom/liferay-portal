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

PortletURL viewCategoryURL = renderResponse.createRenderURL();

viewCategoryURL.setParameter("mvcRenderCommandName", "/view_category");
viewCategoryURL.setParameter("configurationCategory", configurationScreen.getCategoryKey());

String categoryDisplayName = LanguageUtil.get(request, "category." + configurationScreen.getCategoryKey());

PortalUtil.addPortletBreadcrumbEntry(request, categoryDisplayName, viewCategoryURL.toString());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(portletURL.toString());

renderResponse.setTitle(categoryDisplayName);
%>

<div class="container-fluid container-fluid-max-xl">
	<div class="col-12">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>
	</div>
</div>

<div class="container-fluid container-fluid-max-xl">
	<div class="row">
		<div class="col-md-3">
			<liferay-util:include page="/configuration_category_menu.jsp" servletContext="<%= application %>" />
		</div>

		<div class="col-md-9">

			<%
			configurationScreen.render(request, response);
			%>

		</div>
	</div>
</div>