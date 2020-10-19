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
SelectSiteNavigationMenuDisplayContext selectSiteNavigationMenuDisplayContext = (SelectSiteNavigationMenuDisplayContext)request.getAttribute(SiteNavigationItemSelectorWebKeys.SELECT_SITE_NAVIGATION_ITEM_SELECTOR_DISPLAY_CONTEXT);
%>

<div class="container-fluid-1280 p-4">
	<liferay-ui:search-container
		cssClass="table-hover"
		searchContainer="<%= selectSiteNavigationMenuDisplayContext.getSiteNavigationMenuItemSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.site.navigation.model.SiteNavigationMenuItem"
			keyProperty="siteNavigationMenuItemId"
			modelVar="siteNavigationMenuItem"
		>
			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
				cssClass="table-title"
				name="name"
			>
				<clay:icon
					cssClass="mr-2"
					symbol="page"
				/>

				<a href="<%= selectSiteNavigationMenuDisplayContext.getSelectSiteNavigationMenuLevelURL(siteNavigationMenuItem.getSiteNavigationMenuId(), siteNavigationMenuItem.getSiteNavigationMenuItemId()) %>">
					<%= selectSiteNavigationMenuDisplayContext.getSiteNavigationMenuItemName(siteNavigationMenuItem) %>
				</a>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
			searchResultCssClass="table table-autofit"
		/>
	</liferay-ui:search-container>
</div>