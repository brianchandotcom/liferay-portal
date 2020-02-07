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

long siteNavigationMenuItemId = ParamUtil.getLong(request, "siteNavigationMenuItemId");

SiteNavigationMenuItem siteNavigationMenuItem = SiteNavigationMenuItemLocalServiceUtil.getSiteNavigationMenuItem(siteNavigationMenuItemId);

SiteNavigationMenuItemType siteNavigationMenuItemType = siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(siteNavigationMenuItem.getType());
%>

<portlet:actionURL name="/navigation_menu/edit_site_navigation_menu_item" var="editSiteNavigationMenuItemURL" />

<aui:form action="<%= editSiteNavigationMenuItemURL %>">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="siteNavigationMenuId" type="hidden" value="<%= siteNavigationMenuItem.getSiteNavigationMenuId() %>" />
	<aui:input name="siteNavigationMenuItemId" type="hidden" value="<%= siteNavigationMenuItem.getSiteNavigationMenuItemId() %>" />
	<aui:input name="parentSiteNavigationMenuItemId" type="hidden" value="<%= siteNavigationMenuItem.getParentSiteNavigationMenuItemId() %>" />

	<%
	siteNavigationMenuItemType.renderEditPage(request, PipingServletResponse.createPipingServletResponse(pageContext), siteNavigationMenuItem);
	%>

	<c:if test="<%= CustomAttributesUtil.hasCustomAttributes(company.getCompanyId(), SiteNavigationMenuItem.class.getName(), siteNavigationMenuItemId, null) %>">
		<liferay-expando:custom-attribute-list
			className="<%= SiteNavigationMenuItem.class.getName() %>"
			classPK="<%= siteNavigationMenuItemId %>"
			editable="<%= true %>"
			label="<%= true %>"
		/>
	</c:if>

	<aui:button-row>
		<aui:button cssClass="btn-block" type="submit" />
	</aui:button-row>
</aui:form>