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

<c:choose>
	<c:when test="<%= selectSiteNavigationMenuDisplayContext.getSiteNavigationMenuId() >= 0 %>">
		<liferay-util:include page="/select_site_navigation_menu_level.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<div class="container-fluid container-fluid-max-xl p-4">
			<div class="alert alert-info">
				<liferay-ui:message key="select-the-page-level-of-the-navigation-menu-to-be-displayed" />
			</div>

			<div class="align-items-center d-flex justify-content-between">
				<liferay-site-navigation:breadcrumb
					breadcrumbEntries="<%= selectSiteNavigationMenuDisplayContext.getBreadcrumbEntries() %>"
				/>

				<clay:button
					cssClass="site-navigation-menu-selector"
					disabled="<%= true %>"
					displayType="primary"
					label='<%= LanguageUtil.get(resourceBundle, "select-this-level") %>'
					small="<%= true %>"
				/>
			</div>

			<liferay-ui:search-container
				cssClass="table-hover"
				searchContainer="<%= selectSiteNavigationMenuDisplayContext.getSiteNavigationMenuSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.site.navigation.model.SiteNavigationMenu"
					keyProperty="siteNavigationMenuId"
					modelVar="siteNavigationMenu"
				>

					<%
					String name = siteNavigationMenu.getName();

					if (siteNavigationMenu.getGroupId() != scopeGroupId) {
						Group group = GroupLocalServiceUtil.getGroup(siteNavigationMenu.getGroupId());

						name = StringUtil.appendParentheticalSuffix(name, group.getDescriptiveName(locale));
					}
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="name"
					>
						<clay:sticker
							cssClass="bg-light mr-3"
							displayType="dark"
							icon="sites"
						/>

						<a href="<%= selectSiteNavigationMenuDisplayContext.getSelectSiteNavigationMenuLevelURL(siteNavigationMenu.getSiteNavigationMenuId(), siteNavigationMenu.getType()) %>">
							<b><%= HtmlUtil.escape(name) %></b>
						</a>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-minw-300"
						name="marked-as"
					>
						<liferay-ui:message key="<%= siteNavigationMenu.getTypeKey() %>" />
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
					searchResultCssClass="table table-autofit"
				/>
			</liferay-ui:search-container>
		</div>
	</c:otherwise>
</c:choose>