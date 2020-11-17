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
	addURL = renderResponse.createActionURL();

	addURL.setParameter(ActionRequest.ACTION_NAME, "/site_navigation_admin/add_site_navigation_menu_item");
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.format(request, "add-x", siteNavigationMenuItemType.getLabel(locale)));
%>

<liferay-ui:error exception="<%= SiteNavigationMenuItemNameException.class %>">
	<liferay-ui:message arguments='<%= ModelHintsUtil.getMaxLength(SiteNavigationMenuItem.class.getName(), "name") %>' key="please-enter-a-name-with-fewer-than-x-characters" translateArguments="<%= false %>" />
</liferay-ui:error>

<aui:form action="<%= addURL.toString() %>" cssClass="container-fluid-1280" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="siteNavigationMenuId" type="hidden" value="<%= siteNavigationMenuId %>" />
	<aui:input name="type" type="hidden" value="<%= type %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>

			<%
			siteNavigationMenuItemType.renderAddPage(request, PipingServletResponse.createPipingServletResponse(pageContext));
			%>

		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button name="addButton" type="submit" value="add" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<%
Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());
%>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"selPortletId", HtmlUtil.escapeJS(selPortlet.getPortletId())
		).put(
			"selPortletIsAjaxable", selPortlet.isAjaxable()
		).build()
	%>'
	module="js/AddSiteNavigationMenuItem"
/>