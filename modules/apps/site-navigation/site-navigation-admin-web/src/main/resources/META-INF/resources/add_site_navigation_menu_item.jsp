<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
%>

<liferay-ui:error exception="<%= SiteNavigationMenuItemNameException.class %>">
	<liferay-ui:message arguments='<%= ModelHintsUtil.getMaxLength(SiteNavigationMenuItem.class.getName(), "name") %>' key="please-enter-a-name-with-fewer-than-x-characters" translateArguments="<%= false %>" />
</liferay-ui:error>

<aui:form action="<%= addURL %>" cssClass="add-site-navigation-menu-item container-fluid container-fluid-max-xl" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="siteNavigationMenuId" type="hidden" value="<%= siteNavigationMenuId %>" />
	<aui:input name="type" type="hidden" value="<%= type %>" />

	<aui:fieldset>

		<%
		siteNavigationMenuItemType.renderAddPage(request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
		%>

	</aui:fieldset>

	<aui:button-row cssClass="modal-footer position-fixed">
		<clay:button
			id='<%= liferayPortletResponse.getNamespace() + "addButton" %>'
			label='<%= type.equals("layout") ? "select" : "add" %>'
			type="submit"
		/>

		<clay:button
			displayType="btn-secondary cancel"
			label="cancel"
			onClick='<%= "Liferay.Util.navigation('" + redirect + "')" %>'
			type="button"
		/>
	</aui:button-row>
</aui:form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"order", ParamUtil.getLong(request, "order", -1)
		).put(
			"parentSiteNavigationMenuItemId", ParamUtil.getLong(request, "parentSiteNavigationMenuItemId")
		).build()
	%>'
	module="js/AddSiteNavigationMenuItem"
/>