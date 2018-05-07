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

<liferay-ui:success key='<%= portletDisplay.getPortletName() + "layoutAdded" %>' message='<%= LanguageUtil.get(resourceBundle, "the-page-was-created-succesfully") %>' />
<liferay-ui:success key='<%= portletDisplay.getPortletName() + "layoutUpdated" %>' message='<%= LanguageUtil.get(resourceBundle, "the-page-was-updated-succesfully") %>' />

<liferay-ui:error exception="<%= GroupInheritContentException.class %>" message="this-page-cannot-be-deleted-and-cannot-have-child-pages-because-it-is-associated-to-a-site-template" />

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= layoutsAdminDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	actionItems="<%= layoutsAdminDisplayContext.getActionDropdownItems() %>"
	componentId="pagesManagementToolbar"
	creationMenu="<%= layoutsAdminDisplayContext.isShowAddRootLayoutButton() ? layoutsAdminDisplayContext.getCreationMenu() : null %>"
	filterItems="<%= layoutsAdminDisplayContext.getFilterDropdownItems() %>"
	searchContainerId="pages"
	searchFormName="searchFm"
	showSearch="<%= false %>"
	sortingOrder="<%= layoutsAdminDisplayContext.getOrderByType() %>"
	sortingURL="<%= layoutsAdminDisplayContext.getSortingURL() %>"
	totalItems="<%= layoutsAdminDisplayContext.getTotalItems() %>"
	viewTypes="<%= layoutsAdminDisplayContext.getViewTypeItems() %>"
/>

<liferay-ui:error exception="<%= LayoutTypeException.class %>">

	<%
	LayoutTypeException lte = (LayoutTypeException)errorException;
	%>

	<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT %>">
		<liferay-ui:message arguments='<%= "layout.types." + lte.getLayoutType() %>' key="the-first-page-cannot-be-of-type-x" />
	</c:if>
</liferay-ui:error>

<portlet:actionURL name="/layout/delete_layout" var="deleteLayoutURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutURL %>" cssClass="container-fluid-1280" name="fm">
	<c:choose>
		<c:when test="<%= layoutsAdminDisplayContext.isMillerColumnsEnabled() %>">

			<%
			Map<String, Object> context = new HashMap<>();

			context.put("breadcrumbEntries", layoutsAdminDisplayContext.getBreadcrumbEntriesJSONArray());
			context.put("layoutColumns", layoutsAdminDisplayContext.getLayoutColumnsJSONArray());
			context.put("pathThemeImages", themeDisplay.getPathThemeImages());
			context.put("portletNamespace", renderResponse.getNamespace());
			context.put("searchContainerId", "pages");
			context.put("siteNavigationMenuNames", layoutsAdminDisplayContext.getAutoSiteNavigationMenuNames());
			%>

			<soy:template-renderer
				context="<%= context %>"
				module="layout-admin-web/js/miller_columns/Layout.es"
				templateNamespace="com.liferay.layout.admin.web.Layout.render"
			/>
		</c:when>
		<c:otherwise>
			<liferay-ui:search-container
				id="pages"
				searchContainer="<%= layoutsAdminDisplayContext.getLayoutsSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.Layout"
					keyProperty="plid"
					modelVar="curLayout"
				>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="page"
						value="<%= HtmlUtil.escape(curLayout.getName(locale)) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="path"
					>
						<%= HtmlUtil.escape(layoutsAdminDisplayContext.getPath(curLayout, locale)) %> <strong><%= HtmlUtil.escape(curLayout.getName(locale)) %></strong>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-date
						name="create-date"
						property="createDate"
					/>

					<liferay-ui:search-container-column-jsp
						path="/layout_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="<%= layoutsAdminDisplayContext.getDisplayStyle() %>"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</c:otherwise>
	</c:choose>
</aui:form>

<aui:script sandbox="<%= true %>">
	window.<portlet:namespace />deleteSelectedPages = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm($(document.<portlet:namespace />fm));
		}
	}
</aui:script>