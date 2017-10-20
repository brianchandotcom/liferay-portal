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
String displayStyle = siteNavigationAdminDisplayContext.getDisplayStyle();
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="menus" selected="<%= true %>" />
	</aui:nav>

	<aui:nav-bar-search>

		<%
		PortletURL portletURL = liferayPortletResponse.createRenderURL();
		%>

		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar searchContainerId="siteNavigationMenus">
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews="<%= siteNavigationAdminDisplayContext.getDisplayViews() %>"
			portletURL="<%= siteNavigationAdminDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= siteNavigationAdminDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation label="all">
			<liferay-frontend:management-bar-filter-item active="<%= true %>" label="all" url="<%= siteNavigationAdminDisplayContext.getPortletURL().toString() %>" />
		</liferay-frontend:management-bar-navigation>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= siteNavigationAdminDisplayContext.getOrderByCol() %>"
			orderByType="<%= siteNavigationAdminDisplayContext.getOrderByType() %>"
			orderColumns="<%= siteNavigationAdminDisplayContext.getOrderColumns() %>"
			portletURL="<%= siteNavigationAdminDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedSiteNavigationMenus" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="/navigation_menu/delete_site_navigation_menu" var="deleteSitaNavigationMenuURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteSitaNavigationMenuURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="siteNavigationMenus"
		searchContainer="<%= siteNavigationAdminDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.site.navigation.model.SiteNavigationMenu"
			keyProperty="siteNavigationMenuId"
			modelVar="siteNavigationMenu"
		>
			<portlet:renderURL var="editSiteNavigationMenuURL">
				<portlet:param name="mvcPath" value="/edit_site_navigation_menu.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="siteNavigationMenuId" value="<%= String.valueOf(siteNavigationMenu.getSiteNavigationMenuId()) %>" />
			</portlet:renderURL>

			<c:choose>
				<c:when test='<%= displayStyle.equals("descriptive") %>'>
					<liferay-ui:search-container-column-user
						cssClass="user-icon-lg"
						showDetails="<%= false %>"
						userId="<%= siteNavigationMenu.getUserId() %>"
					/>

					<liferay-ui:search-container-column-text colspan="<%= 2 %>" href="<%= editSiteNavigationMenuURL %>">

						<%
						Date createDate = siteNavigationMenu.getCreateDate();

						String createDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
						%>

						<h4>
							<%= siteNavigationMenu.getName() %>
						</h4>

						<h5 class="text-default">
							<liferay-ui:message arguments="<%= new String[] {siteNavigationMenu.getUserName(), createDateDescription} %>" key="x-created-x-ago" />
						</h5>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/site_navigation_menu_action.jsp"
					/>
				</c:when>
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/site_navigation_menu_action.jsp"
							actionJspServletContext="<%= application %>"
							icon="list"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= siteNavigationMenu.getName() %>"
							url="<%= editSiteNavigationMenuURL %>"
						>
							<liferay-frontend:vertical-card-sticker-bottom>
								<liferay-ui:user-portrait
									cssClass="sticker sticker-bottom user-icon-lg"
									userId="<%= siteNavigationMenu.getUserId() %>"
								/>
							</liferay-frontend:vertical-card-sticker-bottom>

							<liferay-frontend:vertical-card-header>
								<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - siteNavigationMenu.getModifiedDate().getTime(), true), HtmlUtil.escape(siteNavigationMenu.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= true %>" />
							</liferay-frontend:vertical-card-header>
						</liferay-frontend:icon-vertical-card>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						href="<%= editSiteNavigationMenuURL %>"
						name="title"
						orderable="<%= false %>"
						value="<%= siteNavigationMenu.getName() %>"
					/>

					<liferay-ui:search-container-column-text
						name="author"
						orderable="<%= false %>"
						property="userName"
					/>

					<liferay-ui:search-container-column-date
						name="create-date"
						orderable="<%= false %>"
						property="createDate"
					/>

					<liferay-ui:search-container-column-jsp
						path="/site_navigation_menu_action.jsp"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= siteNavigationAdminDisplayContext.isShowAddButton() %>">
	<portlet:renderURL var="addSiteNavigationMenuURL">
		<portlet:param name="mvcPath" value="/add_site_navigation_menu.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-menu") %>' url="<%= addSiteNavigationMenuURL %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteSelectedSiteNavigationMenus').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>