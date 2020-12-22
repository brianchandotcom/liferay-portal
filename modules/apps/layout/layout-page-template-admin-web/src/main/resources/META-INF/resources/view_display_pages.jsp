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
DisplayPageDisplayContext displayPageDisplayContext = new DisplayPageDisplayContext(request, renderRequest, renderResponse);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= layoutPageTemplatesAdminDisplayContext.getNavigationItems() %>"
/>

<liferay-ui:success key="displayPagePublished" message="the-display-page-template-was-published-succesfully" />

<%
DisplayPageManagementToolbarDisplayContext displayPageManagementToolbarDisplayContext = new DisplayPageManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, displayPageDisplayContext);
%>

<clay:management-toolbar-v2
	displayContext="<%= displayPageManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/layout_page_template_admin/delete_layout_page_template_entry" var="deleteDisplayPageURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteDisplayPageURL %>" cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:error key="<%= RequiredLayoutPageTemplateEntryException.class.getName() %>" message="you-cannot-delete-display-page-templates-that-are-used-by-one-or-more-items.-please-view-the-usages-and-try-to-unassign-them" />

	<liferay-ui:success key="displayPageTemplateDeleted" message='<%= GetterUtil.getString(MultiSessionMessages.get(renderRequest, "displayPageTemplateDeleted")) %>' />

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
			row.setData(
				HashMapBuilder.<String, Object>put(
					"actions", displayPageManagementToolbarDisplayContext.getAvailableActions(layoutPageTemplateEntry)
				).build());
			%>

			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					propsTransformer="js/propsTransformers/DisplayPageDropdownPropsTransformer"
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

<portlet:actionURL name="/layout_page_template_admin/update_layout_page_template_entry_preview" var="updateLayoutPageTemplateEntryPreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= updateLayoutPageTemplateEntryPreviewURL %>" name="layoutPageTemplateEntryPreviewFm">
	<aui:input name="layoutPageTemplateEntryId" type="hidden" />
	<aui:input name="fileEntryId" type="hidden" />
</aui:form>

<liferay-frontend:component
	componentId="<%= displayPageManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/DisplayPageManagementToolbarDefaultEventHandler.es"
/>