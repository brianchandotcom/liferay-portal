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

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(siteNavigationAdminDisplayContext.getSiteNavigationMenuName());

Group scopeGroup = themeDisplay.getScopeGroup();
%>

<c:if test="<%= siteNavigationAdminDisplayContext.hasUpdatePermission() %>">
	<nav class="management-bar management-bar-light navbar navbar-expand-md site-navigation-management-bar">
		<div class="container">
			<ul class="navbar-nav"></ul>

			<ul class="navbar-nav">
				<c:if test="<%= !scopeGroup.isCompany() %>">
					<li class="nav-item">
						<button class="btn btn-unstyled nav-link nav-link-monospaced" id="<portlet:namespace />showSiteNavigationMenuSettings" type="button">
							<aui:icon cssClass="icon-monospaced" image="cog" markupView="lexicon" />
						</button>
					</li>
				</c:if>

				<li class="nav-item">
					<div class="dropdown">
						<button class="btn btn-primary dropdown-toggle nav-btn nav-btn-monospaced" type="button">
							<clay:icon
								symbol="plus"
							/>
						</button>

						<%
						Map<String, Object> data = new HashMap<>();

						data.put("dropdownItems", siteNavigationAdminDisplayContext.getAddSiteNavigationMenuItemDropdownItems());
						%>

						<react:component
							data="<%= data %>"
							module="js/add_menu/index"
						/>
					</div>
				</li>
			</ul>
		</div>
	</nav>
</c:if>

<div class="container-fluid-1280 contextual-sidebar-content site-navigation-content">
	<div class="lfr-search-container-wrapper site-navigation-menu-container">
		<liferay-ui:error embed="<%= false %>" key="<%= InvalidSiteNavigationMenuItemOrderException.class.getName() %>" message="the-order-of-site-navigation-menu-items-is-invalid" />

		<liferay-ui:error embed="<%= false %>" exception="<%= SiteNavigationMenuItemNameException.class %>">
			<liferay-ui:message arguments='<%= ModelHintsUtil.getMaxLength(SiteNavigationMenuItem.class.getName(), "name") %>' key="please-enter-a-name-with-fewer-than-x-characters" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error embed="<%= false %>" exception="<%= NoSuchLayoutException.class %>" message="the-page-could-not-be-found" />

		<%
		List<SiteNavigationMenuItem> siteNavigationMenuItems = SiteNavigationMenuItemLocalServiceUtil.getSiteNavigationMenuItems(siteNavigationAdminDisplayContext.getSiteNavigationMenuId(), 0);
		%>

		<c:choose>
			<c:when test="<%= siteNavigationMenuItems.size() > 0 %>">
				<div class="hide" data-site-navigation-menu-item-id="0"></div>

				<%
				for (SiteNavigationMenuItem siteNavigationMenuItem : siteNavigationMenuItems) {
				%>

					<liferay-util:include page="/view_site_navigation_menu_item.jsp" servletContext="<%= application %>">
						<liferay-util:param name="siteNavigationMenuItemId" value="<%= String.valueOf(siteNavigationMenuItem.getSiteNavigationMenuItemId()) %>" />
					</liferay-util:include>

				<%
				}
				%>

			</c:when>
			<c:otherwise>
				<liferay-frontend:empty-result-message
					actionDropdownItems="<%= siteNavigationAdminDisplayContext.getAddSiteNavigationMenuItemDropdownItems() %>"
					defaultEventHandler='<%= renderResponse.getNamespace() + "AddMenuDefaultEventHandler" %>'
					description='<%= LanguageUtil.get(request, "fortunately-it-is-very-easy-to-add-new-ones") %>'
				/>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<c:if test="<%= siteNavigationAdminDisplayContext.hasUpdatePermission() %>">
	<div>
		<portlet:actionURL name="/navigation_menu/edit_site_navigation_menu_item_parent" var="editSiteNavigationMenuItemParentURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:actionURL>

		<portlet:renderURL var="editSiteNavigationMenuItemURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
			<portlet:param name="mvcPath" value="/edit_site_navigation_menu_item.jsp" />
		</portlet:renderURL>

		<portlet:renderURL var="editSiteNavigationMenuSettingsURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
			<portlet:param name="mvcPath" value="/site_navigation_menu_settings.jsp" />
		</portlet:renderURL>

		<%
		Map<String, Object> data = new HashMap<>();

		data.put("editSiteNavigationMenuItemParentURL", editSiteNavigationMenuItemParentURL.toString());
		data.put("editSiteNavigationMenuItemURL", editSiteNavigationMenuItemURL.toString());
		data.put("editSiteNavigationMenuSettingsURL", editSiteNavigationMenuSettingsURL.toString());
		data.put("id", renderResponse.getNamespace() + "sidebar");
		data.put("redirect", currentURL);
		data.put("siteNavigationMenuId", siteNavigationAdminDisplayContext.getSiteNavigationMenuId());
		data.put("siteNavigationMenuName", siteNavigationAdminDisplayContext.getSiteNavigationMenuName());
		%>

		<react:component
			componentId="contextualSidebar"
			data="<%= data %>"
			module="js/ContextualSidebar"
		/>
	</div>
</c:if>