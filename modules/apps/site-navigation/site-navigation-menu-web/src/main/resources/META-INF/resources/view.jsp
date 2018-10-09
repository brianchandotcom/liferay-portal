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

<c:choose>
	<c:when test="<%= siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuId() > 0 %>">

		<%
		SiteNavigationMenu siteNavigationMenu = SiteNavigationMenuLocalServiceUtil.fetchSiteNavigationMenu(siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuId());
		%>

		<c:choose>
			<c:when test="<%= siteNavigationMenu != null %>">
				<liferay-site-navigation:navigation-menu
					ddmTemplateGroupId="<%= siteNavigationMenuDisplayContext.getDisplayStyleGroupId() %>"
					ddmTemplateKey="<%= siteNavigationMenuDisplayContext.getDDMTemplateKey() %>"
					displayDepth="<%= siteNavigationMenuDisplayContext.getDisplayDepth() %>"
					expandedLevels="<%= siteNavigationMenuDisplayContext.getExpandedLevels() %>"
					preview="<%= siteNavigationMenuDisplayContext.isPreview() %>"
					rootItemId="<%= siteNavigationMenuDisplayContext.getRootMenuItemId() %>"
					rootItemLevel="<%= siteNavigationMenuDisplayContext.getRootMenuItemLevel() %>"
					rootItemType="<%= siteNavigationMenuDisplayContext.getRootMenuItemType() %>"
					siteNavigationMenuId="<%= siteNavigationMenu.getSiteNavigationMenuId() %>"
				/>
			</c:when>
			<c:otherwise>
				<div class="alert alert-warning">
					<liferay-ui:message key="the-selected-menu-does-not-exist" />
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="<%= (siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY) || (siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_PUBLIC_PAGES_HIERARCHY) %>">
		<liferay-site-navigation:navigation-menu
			ddmTemplateGroupId="<%= siteNavigationMenuDisplayContext.getDisplayStyleGroupId() %>"
			ddmTemplateKey="<%= siteNavigationMenuDisplayContext.getDDMTemplateKey() %>"
			displayDepth="<%= siteNavigationMenuDisplayContext.getDisplayDepth() %>"
			expandedLevels="<%= siteNavigationMenuDisplayContext.getExpandedLevels() %>"
			preview="<%= siteNavigationMenuDisplayContext.isPreview() %>"
			rootItemId="<%= siteNavigationMenuDisplayContext.getRootMenuItemId() %>"
			rootItemLevel="<%= siteNavigationMenuDisplayContext.getRootMenuItemLevel() %>"
			rootItemType="<%= siteNavigationMenuDisplayContext.getRootMenuItemType() %>"
			siteNavigationMenuId="<%= 0 %>"
		/>
	</c:when>
	<c:when test="<%= (siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_PRIMARY) || (siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_SECONDARY) || (siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_SOCIAL) %>">

		<%
		SiteNavigationMenu siteNavigationMenu = SiteNavigationMenuLocalServiceUtil.fetchSiteNavigationMenu(themeDisplay.getScopeGroupId(), siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType());
		%>

		<c:choose>
			<c:when test="<%= siteNavigationMenu != null %>">
				<liferay-site-navigation:navigation-menu
					ddmTemplateGroupId="<%= siteNavigationMenuDisplayContext.getDisplayStyleGroupId() %>"
					ddmTemplateKey="<%= siteNavigationMenuDisplayContext.getDDMTemplateKey() %>"
					displayDepth="<%= siteNavigationMenuDisplayContext.getDisplayDepth() %>"
					expandedLevels="<%= siteNavigationMenuDisplayContext.getExpandedLevels() %>"
					preview="<%= siteNavigationMenuDisplayContext.isPreview() %>"
					rootItemId="<%= siteNavigationMenuDisplayContext.getRootMenuItemId() %>"
					rootItemLevel="<%= siteNavigationMenuDisplayContext.getRootMenuItemLevel() %>"
					rootItemType="<%= siteNavigationMenuDisplayContext.getRootMenuItemType() %>"
					siteNavigationMenuId="<%= siteNavigationMenu.getSiteNavigationMenuId() %>"
				/>
			</c:when>
			<c:otherwise>
				<div class="alert alert-warning">
					<liferay-ui:message arguments="<%= siteNavigationMenuDisplayContext.getSiteNavigationMenuTypeLabel() %>" key="there-is-no-x-available-for-the-current-site" />
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info text-center">
			<aui:a href="javascript:;" onClick="<%= portletDisplay.getURLConfigurationJS() %>"><liferay-ui:message key="configure" /></aui:a>
		</div>
	</c:otherwise>
</c:choose>