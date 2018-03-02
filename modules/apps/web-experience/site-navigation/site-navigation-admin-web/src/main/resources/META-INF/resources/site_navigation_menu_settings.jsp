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

<portlet:actionURL name="/navigation_menu/edit_site_navigation_menu_settings" var="editSiteNavigationMenuSettingsURL" />

<aui:form action="<%= editSiteNavigationMenuSettingsURL %>">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="siteNavigationMenuId" type="hidden" value="<%= siteNavigationMenu.getSiteNavigationMenuId() %>" />

	<aui:fieldset helpMessage="function-help" label="function">
		<aui:input checked="<%= siteNavigationMenu.getType() == SiteNavigationConstants.TYPE_PRIMARY %>" label="primary-navigation" name="type" type="radio" value="<%= SiteNavigationConstants.TYPE_PRIMARY %>" wrapperCssClass="mb-1" />

		<c:if test="<%= siteNavigationAdminDisplayContext.isShowPrimarySiteNavigationMenuMessage() %>">

			<%
			SiteNavigationMenu primarySiteNavigationMenu = siteNavigationAdminDisplayContext.getPrimarySiteNavigationMenu();
			%>

			<div class="text-muted">
				<liferay-ui:message arguments="<%= primarySiteNavigationMenu.getName() %>" key="current-main-menu-x" />
			</div>
		</c:if>

		<aui:input checked="<%= siteNavigationMenu.getType() == SiteNavigationConstants.TYPE_SECONDARY %>" label="secondary-navigation" name="type" type="radio" value="<%= SiteNavigationConstants.TYPE_SECONDARY %>" wrapperCssClass="mt-4" />

		<aui:input checked="<%= siteNavigationMenu.getType() == SiteNavigationConstants.TYPE_SOCIAL %>" label="social-navigation" name="type" type="radio" value="<%= SiteNavigationConstants.TYPE_SOCIAL %>" />

		<aui:input checked="<%= siteNavigationMenu.getType() == SiteNavigationConstants.TYPE_DEFAULT %>" label="default" name="type" type="radio" value="<%= SiteNavigationConstants.TYPE_DEFAULT %>" />
	</aui:fieldset>

	<aui:fieldset>
		<aui:input checked="<%= siteNavigationMenu.isAuto() %>" label="add-new-pages-to-this-menu" name="auto" type="checkbox" />

		<c:if test="<%= siteNavigationAdminDisplayContext.isShowAutoSiteNavigationMenuMessage() %>">

			<%
			SiteNavigationMenu autoSiteNavigationMenu = siteNavigationAdminDisplayContext.getAutoSiteNavigationMenu();
			%>

			<div class="text-muted">
				<liferay-ui:message arguments="<%= autoSiteNavigationMenu.getName() %>" key="current-selected-menu-x" />
			</div>
		</c:if>
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-block" type="submit" value="save" />
	</aui:button-row>
</aui:form>