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

SiteNavigationMenu siteNavigationMenu = siteNavigationAdminDisplayContext.getSiteNavigationMenu();
%>

<c:if test="<%= siteNavigationAdminDisplayContext.isNotPrimarySiteNavigationMenu() %>">
	<div class="alert alert-warning">
		<liferay-ui:message key="the-primary-menu-for-this-site-is-already-defined" />
	</div>
</c:if>

<portlet:actionURL name="/navigation_menu/edit_primary_site_navigation_menu" var="editPrimarySiteNavigationMenuURL" />

<aui:form action="<%= editPrimarySiteNavigationMenuURL %>">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="siteNavigationMenuId" type="hidden" value="<%= siteNavigationMenu.getSiteNavigationMenuId() %>" />

	<aui:fieldset label="function">
		<aui:input checked="<%= siteNavigationMenu.isPrimary() %>" disabled="<%= siteNavigationAdminDisplayContext.isNotPrimarySiteNavigationMenu() %>" label="primary" name="primary" type="radio" value="<%= true %>" />

		<aui:input checked="<%= !siteNavigationMenu.isPrimary() %>" disabled="<%= siteNavigationAdminDisplayContext.isNotPrimarySiteNavigationMenu() %>" label="secondary" name="primary" type="radio" value="<%= false %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-block btn-lg" type="submit" value="save" />
	</aui:button-row>
</aui:form>