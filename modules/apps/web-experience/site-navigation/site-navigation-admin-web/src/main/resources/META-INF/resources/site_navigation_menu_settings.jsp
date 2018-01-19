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

<portlet:actionURL name="/navigation_menu/edit_primary_site_navigation_menu" var="editPrimarySiteNavigationMenuURL" />

<aui:form action="<%= editPrimarySiteNavigationMenuURL %>">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="siteNavigationMenuId" type="hidden" value="<%= siteNavigationMenu.getSiteNavigationMenuId() %>" />

	<aui:fieldset helpMessage="function-help" label="function">

		<%
		String taglibOnClickPrimary = renderResponse.getNamespace() + "toggleCheckboxes('primary', 'secondary', 'social');";
		String taglibOnClickSecondary = renderResponse.getNamespace() + "toggleCheckboxes('secondary', 'primary', 'social');";
		String taglibOnClickSocial = renderResponse.getNamespace() + "toggleCheckboxes('social', 'primary', 'secondary');";
		%>

		<aui:input
			checked="<%= siteNavigationMenu.isPrimary() %>"
			disabled="<%= siteNavigationMenu.isSecondary() || siteNavigationMenu.isSocial() %>"
			label="main-menu"
			name="primary"
			onClick="<%= taglibOnClickPrimary %>"
			type="checkbox"
			wrapperCssClass="mb-1"
		/>

		<%
		SiteNavigationMenu primarySiteNavigationMenu = siteNavigationAdminDisplayContext.getPrimarySiteNavigationMenu();
		%>

		<c:if test="<%= siteNavigationAdminDisplayContext.isNotPrimarySiteNavigationMenu() %>">
			<div class="text-muted" id="<portlet:namespace/>currentPrimaryMenu">
				<liferay-ui:message arguments="<%= primarySiteNavigationMenu.getName() %>" key="current-main-menu-x" />
			</div>
		</c:if>

		<aui:input
			checked="<%= siteNavigationMenu.isSecondary() %>"
			disabled="<%= siteNavigationMenu.isPrimary() || siteNavigationMenu.isSocial() %>"
			label="subsidiary-menu"
			name="secondary"
			onClick="<%= taglibOnClickSecondary %>"
			type="checkbox"
			wrapperCssClass="mt-4"
		/>

		<aui:input
			checked="<%= siteNavigationMenu.isSocial() %>"
			disabled="<%= siteNavigationMenu.isPrimary() || siteNavigationMenu.isSecondary() %>"
			label="social-menu"
			name="social"
			onClick="<%= taglibOnClickSocial %>"
			type="checkbox"
		/>
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-block" type="submit" value="save" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	<portlet:namespace/>toggleCheckboxes = function(current, switchBox1, switchBox2) {
		var checked = A.one('#<portlet:namespace/>' + current).get('checked');
		var switchBox1 = A.one('#<portlet:namespace/>' + switchBox1)
		var switchBox2 = A.one('#<portlet:namespace/>' + switchBox2)

		Liferay.Util.toggleDisabled(switchBox1, checked);
		Liferay.Util.toggleDisabled(switchBox2, checked);

		var primaryMenu = A.one('#<portlet:namespace/>currentPrimaryMenu');

		if ((current === 'primary') && checked && primaryMenu) {
			primaryMenu.addClass('hide');
		}
		else if ((current === 'primary') && !checked && primaryMenu) {
			primaryMenu.removeClass('hide');
		}
	};
</aui:script>