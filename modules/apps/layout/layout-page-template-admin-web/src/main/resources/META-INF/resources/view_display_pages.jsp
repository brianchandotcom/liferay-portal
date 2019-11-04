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
DisplayPageDisplayContext displayPageDisplayContext = new DisplayPageDisplayContext(renderRequest, renderResponse, request);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= layoutPageTemplatesAdminDisplayContext.getNavigationItems() %>"
/>

<liferay-ui:success key="displayPagePublished" message="the-display-page-template-was-published-succesfully" />

<%
DisplayPageManagementToolbarDisplayContext displayPageManagementToolbarDisplayContext = new DisplayPageManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, displayPageDisplayContext);
%>

<clay:management-toolbar
	displayContext="<%= displayPageManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/layout_page_template/delete_layout_page_template_entry" var="deleteDisplayPageURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteDisplayPageURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:error key="<%= PortalException.class.getName() %>" message="one-or-more-entries-could-not-be-deleted" />
	<liferay-ui:error key="<%= RequiredLayoutPageTemplateEntryException.class.getName() %>" message="you-cannot-delete-asset-display-page-templates-that-are-used-by-one-or-more-assets" />

	<liferay-ui:search-container
		id="displayPages"
		searchContainer="<%= displayPageDisplayContext.getDisplayPagesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.page.template.model.LayoutPageTemplateEntry"
			keyProperty="layoutPageTemplateEntryId"
			modelVar="layoutPageTemplateEntry"
		>

			<%
			row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());

			Map<String, Object> rowData = new HashMap<>();

			rowData.put("actions", displayPageManagementToolbarDisplayContext.getAvailableActions(layoutPageTemplateEntry));

			row.setData(rowData);
			%>

			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					verticalCard="<%= new DisplayPageVerticalCard(layoutPageTemplateEntry, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<liferay-frontend:component
	componentId="<%= LayoutPageTemplateAdminWebKeys.DISPLAY_PAGE_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
	module="js/DisplayPageDropdownDefaultEventHandler.es"
/>

<liferay-frontend:component
	componentId="<%= displayPageManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/DisplayPageManagementToolbarDefaultEventHandler.es"
/>