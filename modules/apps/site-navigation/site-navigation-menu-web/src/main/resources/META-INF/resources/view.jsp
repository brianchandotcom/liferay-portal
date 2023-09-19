<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				<clay:alert
					displayType="warning"
					message="the-selected-menu-does-not-exist"
				/>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="<%= (siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY) || (siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_PUBLIC_PAGES_HIERARCHY) %>">
		<c:if test="<%= siteNavigationMenuDisplayContext.showAlert() %>">

			<%
			String alertKey = null;

			if (siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType() == SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY) {
				alertKey = "the-navigation-being-displayed-here-is-the-private-pages-hierarchy.-if-a-public-page-inherits-this-page-template-the-public-pages-hierarchy-will-be-displayed-there-instead";
			}
			else {
				alertKey = "the-navigation-being-displayed-here-is-the-public-pages-hierarchy.-if-a-private-page-inherits-this-page-template-the-private-pages-hierarchy-will-be-displayed-there-instead";
			}
			%>

			<clay:alert
				displayType="info"
				message="<%= LanguageUtil.format(request, alertKey, siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuTypeLabel()) %>"
			/>
		</c:if>

		<liferay-site-navigation:navigation-menu
			ddmTemplateGroupId="<%= siteNavigationMenuDisplayContext.getDisplayStyleGroupId() %>"
			ddmTemplateKey="<%= siteNavigationMenuDisplayContext.getDDMTemplateKey() %>"
			displayDepth="<%= siteNavigationMenuDisplayContext.getDisplayDepth() %>"
			expandedLevels="<%= siteNavigationMenuDisplayContext.getExpandedLevels() %>"
			navigationMenuMode="<%= siteNavigationMenuDisplayContext.getNavigationMenuMode() %>"
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
				<clay:alert
					displayType="warning"
					message='<%= LanguageUtil.format(request, "there-is-no-x-available-for-the-current-site", siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuTypeLabel()) %>'
				/>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<clay:alert
			cssClass="text-center"
			message='<%= LanguageUtil.format(request, "there-is-no-x-available-for-the-current-site", siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuTypeLabel()) %>'
		>
			<clay:button
				displayType="link"
				label="configure"
				onClick="<%= portletDisplay.getURLConfigurationJS() %>"
				small="<%= true %>"
			/>
		</clay:alert>
	</c:otherwise>
</c:choose>