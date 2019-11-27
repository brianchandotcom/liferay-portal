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
 MasterLayoutDisplayContext  MasterLayoutDisplayContext = new  MasterLayoutDisplayContext(request, renderRequest, renderResponse);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= layoutPageTemplatesAdminDisplayContext.getNavigationItems() %>"
/>

<%
 MasterLayoutManagementToolbarDisplayContext  MasterLayoutManagementToolbarDisplayContext = new  MasterLayoutManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse,  MasterLayoutDisplayContext);
%>

<clay:management-toolbar
	displayContext="<%=  MasterLayoutManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/layout_page_template/delete_master_layout" var="deleteMasterLayoutURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteMasterLayoutURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:error key="<%= PortalException.class.getName() %>" message="one-or-more-entries-could-not-be-deleted" />
	<liferay-ui:error key="<%= RequiredLayoutPageTemplateEntryException.class.getName() %>" message="you-cannot-delete-master-pages-that-are-used-by-a-page" />

	<liferay-ui:search-container
		id="<%=  MasterLayoutManagementToolbarDisplayContext.getSearchContainerId() %>"
		searchContainer="<%=  MasterLayoutDisplayContext.getMasterLayoutsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.page.template.model.LayoutPageTemplateEntry"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="layoutPrototypeId"
			modelVar="layoutPageTemplateEntry"
		>

			<%
			row.setCssClass("entry-card lfr-asset-item");

			Map<String, Object> rowData = new HashMap<>();

			rowData.put("actions",  MasterLayoutManagementToolbarDisplayContext.getAvailableActions(layoutPageTemplateEntry));

			row.setData(rowData);
			%>

			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					verticalCard="<%= new  MasterLayoutVerticalCard(layoutPageTemplateEntry, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="/layout_page_template/update_layout_page_template_entry_preview" var="masterPagePreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= masterPagePreviewURL %>" name="masterPagePreviewFm">
	<aui:input name="layoutPageTemplateEntryId" type="hidden" />
	<aui:input name="fileEntryId" type="hidden" />
</aui:form>

<liferay-frontend:component
	componentId="<%= LayoutPageTemplateAdminWebKeys.MASTER_LAYOUT_DROPDOWN_DEFAULT_EVENT_HANDLER%>"
	module="js/MasterLayoutDropdownDefaultEventHandler.es"
/>

<liferay-frontend:component
	componentId="<%=  MasterLayoutManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/MasterLayoutManagementToolbarDefaultEventHandler.es"
/>