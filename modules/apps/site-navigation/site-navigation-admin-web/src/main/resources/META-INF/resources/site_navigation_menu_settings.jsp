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
	<aui:input name="type" type="hidden" value="<%= siteNavigationMenu.getType() %>" />

	<%
	String typeKey = siteNavigationMenu.getTypeKey();
	%>

	<c:if test="<%= Validator.isNotNull(typeKey) %>">
		<div class="mb-4">
			<h5>
				<strong><liferay-ui:message key="this-menu-will-act-as-the" /></strong>
			</h5>

			<aui:icon image="check-circle-full" markupView="lexicon" /> <liferay-ui:message key="<%= typeKey %>" />
		</div>
	</c:if>

	<aui:fieldset>
		<aui:input checked="<%= siteNavigationMenu.isAuto() %>" label="when-creating-a-new-page,-the-page-will-be-automatically-added-to-this-menu-unless-the-user-deselects-it" name="auto" type="checkbox" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-block" type="submit" value="save" />
	</aui:button-row>
</aui:form>