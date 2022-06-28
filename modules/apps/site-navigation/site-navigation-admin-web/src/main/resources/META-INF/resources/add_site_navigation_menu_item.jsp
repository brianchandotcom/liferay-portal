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

long siteNavigationMenuId = ParamUtil.getLong(request, "siteNavigationMenuId");

String type = ParamUtil.getString(request, "type");

SiteNavigationMenuItemType siteNavigationMenuItemType = siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(type);

PortletURL addURL = siteNavigationMenuItemType.getAddURL(renderRequest, renderResponse);

if (addURL == null) {
	addURL = PortletURLBuilder.createActionURL(
		renderResponse
	).setActionName(
		"/site_navigation_admin/add_site_navigation_menu_item"
	).buildPortletURL();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(siteNavigationMenuItemType.getAddTitle(locale));
%>

<liferay-ui:error exception="<%= SiteNavigationMenuItemNameException.class %>">
	<liferay-ui:message arguments='<%= ModelHintsUtil.getMaxLength(SiteNavigationMenuItem.class.getName(), "name") %>' key="please-enter-a-name-with-fewer-than-x-characters" translateArguments="<%= false %>" />
</liferay-ui:error>

<aui:form action="<%= addURL %>" cssClass="add-site-navigation-menu-item container-fluid container-fluid-max-xl" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="siteNavigationMenuId" type="hidden" value="<%= siteNavigationMenuId %>" />
	<aui:input name="type" type="hidden" value="<%= type %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>

			<%
			siteNavigationMenuItemType.renderAddPage(request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
			%>

		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row cssClass="modal-footer position-fixed">
		<aui:button name="addButton" type="submit" value='<%= type.equals("layout") ? "select" : "add" %>' />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<liferay-frontend:component
	module="js/AddSiteNavigationMenuItem"
/>