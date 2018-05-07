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
LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext = new LayoutPageTemplateDisplayContext(renderRequest, renderResponse, request);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= layoutsAdminDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	actionItems="<%= layoutPageTemplateDisplayContext.geLayoutPageTemplateCollectionsActionDropdownItems() %>"
	clearResultsURL="<%= layoutPageTemplateDisplayContext.getClearResultsURL() %>"
	componentId="layoutPageTemplateCollectionsManagementToolbar"
	creationMenu="<%= layoutPageTemplateDisplayContext.isShowAddButton(LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_COLLECTION) ? layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionsCreationMenu() : null %>"
	disabled="<%= layoutPageTemplateDisplayContext.isDisabledLayoutPageTemplateCollectionsManagementBar() %>"
	filterItems="<%= layoutPageTemplateDisplayContext.getFilterDropdownItems() %>"
	searchActionURL="<%= layoutPageTemplateDisplayContext.getSearchActionURL() %>"
	searchContainerId="layoutPageTemplateCollections"
	searchFormName="searchFm"
	showSearch="<%= layoutPageTemplateDisplayContext.isShowLayoutPageTemplateCollectionsSearch() %>"
	sortingOrder="<%= layoutPageTemplateDisplayContext.getOrderByType() %>"
	sortingURL="<%= layoutPageTemplateDisplayContext.getSortingURL() %>"
	totalItems="<%= layoutPageTemplateDisplayContext.getTotalItems() %>"
	viewTypes="<%= layoutPageTemplateDisplayContext.getViewTypeItems() %>"
/>

<portlet:actionURL name="/layout/delete_layout_page_template_collection" var="deleteLayoutPageTemplateCollectionURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutPageTemplateCollectionURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="layoutPageTemplateCollections"
		searchContainer="<%= layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.page.template.model.LayoutPageTemplateCollection"
			keyProperty="layoutPageTemplateCollectionId"
			modelVar="layoutPageTemplateCollection"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/view_layout_page_template_entries.jsp" />
				<portlet:param name="tabs1" value="page-templates" />
				<portlet:param name="layoutPageTemplateCollectionId" value="<%= String.valueOf(layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()) %>" />
			</portlet:renderURL>

			<%
			row.setCssClass("entry-card lfr-asset-folder");
			%>

			<liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text
					colspan="<%= 2 %>"
				>
					<liferay-frontend:horizontal-card
						actionJsp="/layout_page_template_collection_action.jsp"
						actionJspServletContext="<%= application %>"
						resultRow="<%= row %>"
						rowChecker="<%= searchContainer.getRowChecker() %>"
						text="<%= HtmlUtil.escape(layoutPageTemplateCollection.getName()) %>"
						url="<%= rowURL.toString() %>"
					>
						<liferay-frontend:horizontal-card-col>
							<liferay-frontend:horizontal-card-icon
								icon="folder"
							/>
						</liferay-frontend:horizontal-card-col>
					</liferay-frontend:horizontal-card>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= layoutPageTemplateDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script sandbox="<%= true %>">
	window.deleteLayoutPageTemplateCollections = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm($(document.<portlet:namespace />fm));
		}
	}
</aui:script>