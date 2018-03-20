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
SiteNavigationMenuItemSelectorViewDisplayContext siteNavigationMenuItemSelectorViewDisplayContext = (SiteNavigationMenuItemSelectorViewDisplayContext)request.getAttribute(SiteNavigationItemSelectorWebKeys.SITE_NAVIGATION_MENU_ITEM_SELECTOR_DISPLAY_CONTEXT);

String displayStyle = siteNavigationMenuItemSelectorViewDisplayContext.getDisplayStyle();
%>

<liferay-frontend:management-bar
	searchContainerId="siteNavigationMenus"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews="<%= siteNavigationMenuItemSelectorViewDisplayContext.getDisplayViews() %>"
			portletURL="<%= siteNavigationMenuItemSelectorViewDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			label="all"
		>

			<%
			PortletURL portletURL = siteNavigationMenuItemSelectorViewDisplayContext.getPortletURL();
			%>

			<liferay-frontend:management-bar-filter-item
				active="<%= true %>"
				label="all"
				url="<%= portletURL.toString() %>"
			/>
		</liferay-frontend:management-bar-navigation>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= siteNavigationMenuItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= siteNavigationMenuItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns="<%= siteNavigationMenuItemSelectorViewDisplayContext.getOrderColumns() %>"
			portletURL="<%= siteNavigationMenuItemSelectorViewDisplayContext.getPortletURL() %>"
		/>

		<li>
			<liferay-item-selector:search />
		</li>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<aui:form action="<%= siteNavigationMenuItemSelectorViewDisplayContext.getPortletURL() %>" cssClass="container-fluid-1280" name="selectSiteNavigationMenuFm">
	<liferay-ui:search-container
		id="siteNavigationMenus"
		searchContainer="<%= siteNavigationMenuItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.site.navigation.model.SiteNavigationMenu"
			keyProperty="siteNavigationMenuId"
			modelVar="siteNavigationMenu"
		>

			<%
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("id", siteNavigationMenu.getSiteNavigationMenuId());
			data.put("name", siteNavigationMenu.getName());
			%>

			<c:choose>
				<c:when test='<%= displayStyle.equals("descriptive") %>'>
					<liferay-ui:search-container-column-user
						showDetails="<%= false %>"
						userId="<%= siteNavigationMenu.getUserId() %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h4>
							<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
								<%= HtmlUtil.escape(siteNavigationMenu.getName()) %>
							</aui:a>
						</h4>

						<%
						Date createDate = siteNavigationMenu.getCreateDate();

						String createDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
						%>

						<h5 class="text-default">
							<liferay-ui:message arguments="<%= new String[] {siteNavigationMenu.getUserName(), createDateDescription} %>" key="x-created-x-ago" />
						</h5>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							icon="list"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
						>
							<liferay-frontend:vertical-card-header>
								<div class="row">
									<h5 class="col text-truncate">
										<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
											<%= HtmlUtil.escape(siteNavigationMenu.getName()) %>
										</aui:a>
									</h5>
								</div>
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-sticker-bottom>
								<liferay-ui:user-portrait
									cssClass="sticker sticker-bottom"
									userId="<%= siteNavigationMenu.getUserId() %>"
								/>
							</liferay-frontend:vertical-card-sticker-bottom>
						</liferay-frontend:icon-vertical-card>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="title"
					>
						<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
							<%= HtmlUtil.escape(siteNavigationMenu.getName()) %>
						</aui:a>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="author"
						property="userName"
					/>

					<liferay-ui:search-container-column-date
						name="create-date"
						property="createDate"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectSiteNavigationMenuFm', '<%= HtmlUtil.escapeJS(siteNavigationMenuItemSelectorViewDisplayContext.getItemSelectedEventName()) %>');
</aui:script>