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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SiteNavigationMenu siteNavigationMenu = (SiteNavigationMenu)row.getObject();

PortletURL portletURL = renderResponse.createRenderURL();
%>

<liferay-ui:icon-menu
	cssClass="dropdown-menu-indicator-end"
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= SiteNavigationMenuPermission.contains(permissionChecker, siteNavigationMenu, ActionKeys.UPDATE) && siteNavigationAdminDisplayContext.hasEditPermission() %>">
		<portlet:renderURL var="editSiteNavigationMenuURL">
			<portlet:param name="mvcPath" value="/edit_site_navigation_menu.jsp" />
			<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
			<portlet:param name="siteNavigationMenuId" value="<%= String.valueOf(siteNavigationMenu.getSiteNavigationMenuId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editSiteNavigationMenuURL %>"
		/>
	</c:if>

	<c:if test="<%= SiteNavigationMenuPermission.contains(permissionChecker, siteNavigationMenu, ActionKeys.UPDATE) && siteNavigationAdminDisplayContext.hasEditPermission() %>">
		<portlet:actionURL name="/navigation_menu/update_site_navigation_menu" var="updateSiteNavigationMenuURL">
			<portlet:param name="siteNavigationMenuId" value="<%= String.valueOf(siteNavigationMenu.getSiteNavigationMenuId()) %>" />
		</portlet:actionURL>

		<%
		Map<String, Object> updateSiteNavigationMenuData = new HashMap<String, Object>();

		updateSiteNavigationMenuData.put("form-submit-url", updateSiteNavigationMenuURL.toString());
		updateSiteNavigationMenuData.put("id-field-value", siteNavigationMenu.getSiteNavigationMenuId());
		updateSiteNavigationMenuData.put("main-field-value", siteNavigationMenu.getName());
		%>

		<liferay-ui:icon
			cssClass='<%= renderResponse.getNamespace() + "update-site-navigation-menu-action-option" %>'
			data="<%= updateSiteNavigationMenuData %>"
			message="rename"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= SiteNavigationMenuPermission.contains(permissionChecker, siteNavigationMenu, ActionKeys.PERMISSIONS) && siteNavigationAdminDisplayContext.hasEditPermission() %>">
		<liferay-security:permissionsURL
			modelResource="<%= SiteNavigationMenu.class.getName() %>"
			modelResourceDescription="<%= siteNavigationMenu.getName() %>"
			resourcePrimKey="<%= String.valueOf(siteNavigationMenu.getSiteNavigationMenuId()) %>"
			var="permissionsMenuURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsMenuURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= SiteNavigationMenuPermission.contains(permissionChecker, siteNavigationMenu, ActionKeys.UPDATE) && siteNavigationAdminDisplayContext.hasEditPermission() %>">
		<div class="border-top dropdown-subheader">
			<liferay-ui:message key="mark-as" />
		</div>

		<liferay-portlet:actionURL name="/navigation_menu/edit_site_navigation_menu_settings" var="markAsPrimaryURL">
			<liferay-portlet:param name="redirect" value="<%= portletURL.toString() %>" />
			<liferay-portlet:param name="siteNavigationMenuId" value="<%= String.valueOf(siteNavigationMenu.getSiteNavigationMenuId()) %>" />
			<liferay-portlet:param name="auto" value="<%= String.valueOf(siteNavigationMenu.isAuto()) %>" />
			<liferay-portlet:param name="type" value="<%= String.valueOf(SiteNavigationConstants.TYPE_PRIMARY) %>" />
		</liferay-portlet:actionURL>

		<%
		String taglibOnClickPrimary = "submitForm(document.hrefFm, '" + markAsPrimaryURL + "');";

		SiteNavigationMenu primarySiteNavigationMenu = siteNavigationAdminDisplayContext.getPrimarySiteNavigationMenu();

		if ((siteNavigationMenu.getType() != SiteNavigationConstants.TYPE_PRIMARY) && (primarySiteNavigationMenu != null)) {
			taglibOnClickPrimary = "if (confirm('" + UnicodeLanguageUtil.format(request, "do-you-want-to-replace-x-for-x-as-the-primary-navigation?-this-action-will-affect-all-the-pages-using-primary-navigation", new String[] {siteNavigationMenu.getName(), primarySiteNavigationMenu.getName()}) + "')) { submitForm(document.hrefFm, '" + markAsPrimaryURL + "'); } ";
		}
		%>

		<liferay-ui:icon
			icon='<%= (siteNavigationMenu.getType() == SiteNavigationConstants.TYPE_PRIMARY) ? "check" : StringPool.BLANK %>'
			iconCssClass="pull-right"
			markupView="lexicon"
			message="primary-navigation"
			onClick="<%= taglibOnClickPrimary %>"
			url="javascript:;"
		/>

		<liferay-portlet:actionURL name="/navigation_menu/edit_site_navigation_menu_settings" var="markAsSecondaryURL">
			<liferay-portlet:param name="redirect" value="<%= portletURL.toString() %>" />
			<liferay-portlet:param name="siteNavigationMenuId" value="<%= String.valueOf(siteNavigationMenu.getSiteNavigationMenuId()) %>" />
			<liferay-portlet:param name="auto" value="<%= String.valueOf(siteNavigationMenu.isAuto()) %>" />
			<liferay-portlet:param name="type" value="<%= String.valueOf(SiteNavigationConstants.TYPE_SECONDARY) %>" />
		</liferay-portlet:actionURL>

		<%
		String taglibOnClickSecondary = "submitForm(document.hrefFm, '" + markAsSecondaryURL + "');";
		%>

		<liferay-ui:icon
			icon='<%= (siteNavigationMenu.getType() == SiteNavigationConstants.TYPE_SECONDARY) ? "check" : StringPool.BLANK %>'
			iconCssClass="pull-right"
			markupView="lexicon"
			message="secondary-navigation"
			onClick="<%= taglibOnClickSecondary %>"
			url="javascript:;"
		/>

		<liferay-portlet:actionURL name="/navigation_menu/edit_site_navigation_menu_settings" var="markAsSocialURL">
			<liferay-portlet:param name="redirect" value="<%= portletURL.toString() %>" />
			<liferay-portlet:param name="siteNavigationMenuId" value="<%= String.valueOf(siteNavigationMenu.getSiteNavigationMenuId()) %>" />
			<liferay-portlet:param name="auto" value="<%= String.valueOf(siteNavigationMenu.isAuto()) %>" />
			<liferay-portlet:param name="type" value="<%= String.valueOf(SiteNavigationConstants.TYPE_SOCIAL) %>" />
		</liferay-portlet:actionURL>

		<%
		String taglibOnClickSocial = "submitForm(document.hrefFm, '" + markAsSocialURL + "');";
		%>

		<liferay-ui:icon
			icon='<%= (siteNavigationMenu.getType() == SiteNavigationConstants.TYPE_SOCIAL) ? "check" : StringPool.BLANK %>'
			iconCssClass="pull-right"
			markupView="lexicon"
			message="social-navigation"
			onClick="<%= taglibOnClickSocial %>"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= SiteNavigationMenuPermission.contains(permissionChecker, siteNavigationMenu, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/navigation_menu/delete_site_navigation_menu" var="deleteSiteNavigationMenuURL">
			<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
			<portlet:param name="siteNavigationMenuId" value="<%= String.valueOf(siteNavigationMenu.getSiteNavigationMenuId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			trash="<%= false %>"
			url="<%= deleteSiteNavigationMenuURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>