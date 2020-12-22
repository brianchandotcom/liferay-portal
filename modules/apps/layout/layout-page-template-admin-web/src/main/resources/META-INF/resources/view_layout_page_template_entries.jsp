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
LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext = new LayoutPageTemplateDisplayContext(request, renderRequest, renderResponse);

LayoutPageTemplateManagementToolbarDisplayContext layoutPageTemplateManagementToolbarDisplayContext = new LayoutPageTemplateManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, layoutPageTemplateDisplayContext);
%>

<clay:management-toolbar-v2
	displayContext="<%= layoutPageTemplateManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/layout_page_template_admin/delete_layout_page_template_entry" var="deleteLayoutPageTemplateEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutPageTemplateEntryURL %>" name="fm">
	<liferay-ui:error key="<%= PortalException.class.getName() %>" message="you-cannot-delete-page-templates-that-are-used-by-a-page" />

	<liferay-ui:search-container
		id="layoutPageTemplateEntries"
		searchContainer="<%= layoutPageTemplateDisplayContext.getLayoutPageTemplateEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.page.template.model.LayoutPageTemplateEntry"
			keyProperty="layoutPageTemplateEntryId"
			modelVar="layoutPageTemplateEntry"
		>

			<%
			row.setData(
				HashMapBuilder.<String, Object>put(
					"actions", layoutPageTemplateManagementToolbarDisplayContext.getAvailableActions(layoutPageTemplateEntry)
				).build());
			%>

			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					propsTransformer="js/propsTransformers/LayoutPageTemplateEntryPropsTransformer"
					verticalCard="<%= new LayoutPageTemplateEntryVerticalCard(layoutPageTemplateEntry, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
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
	componentId="<%= layoutPageTemplateManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/LayoutPageTemplateEntryManagementToolbarDefaultEventHandler.es"
/>