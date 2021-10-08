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

<div class="container-fluid container-fluid-max-xl p-4" id="<portlet:namespace />siteNavigationMenuLevelSelector">
	<div class="alert alert-info">
		<liferay-ui:message key="select-the-page-level-of-the-navigation-menu-to-be-displayed" />
	</div>

	<div class="align-items-center d-flex justify-content-between">
		<liferay-site-navigation:breadcrumb
			breadcrumbEntries="<%= selectSiteNavigationMenuDisplayContext.getBreadcrumbEntries() %>"
		/>

		<clay:button
			cssClass="site-navigation-menu-selector"
			data-parent-site-navigation-menu-item-id="<%= selectSiteNavigationMenuDisplayContext.getParentSiteNavigationMenuItemId() %>"
			data-private-layout="<%= selectSiteNavigationMenuDisplayContext.isPrivateLayout() %>"
			data-site-navigation-menu-id="<%= selectSiteNavigationMenuDisplayContext.getSiteNavigationMenuId() %>"
			data-title="<%= selectSiteNavigationMenuDisplayContext.getCurrentLevelTitle() %>"
			displayType="primary"
			label='<%= LanguageUtil.get(resourceBundle, "select-this-level") %>'
			small="<%= true %>"
		/>
	</div>

	<liferay-ui:search-container
		cssClass="table-hover"
		searchContainer="<%= selectSiteNavigationMenuDisplayContext.getSiteNavigationMenuItemSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.site.navigation.item.selector.web.internal.display.context.SiteNavigationMenuEntry"
			modelVar="siteNavigationMenuEntry"
		>
			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
				cssClass="table-title"
				name="name"
			>
				<clay:sticker
					cssClass="bg-light mr-3"
					displayType="dark"
					icon="page"
				/>

				<a href="<%= siteNavigationMenuEntry.getURL() %>">
					<%= siteNavigationMenuEntry.getName() %>
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

<liferay-frontend:component
	componentId="SelectEntityHandler"
	context="<%= selectSiteNavigationMenuDisplayContext.getContext(liferayPortletResponse) %>"
	module="js/SelectEntityHandler"
/>