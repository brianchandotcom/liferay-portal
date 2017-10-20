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

renderResponse.setTitle(LanguageUtil.get(request, "page-templates"));
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="page-templates" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= layoutPageTemplateDisplayContext.isShowLayoutPageTemplateCollectionsSearch() %>">
		<portlet:renderURL var="portletURL">
			<portlet:param name="mvcPath" value="/view_layout_page_template_collections.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="displayStyle" value="<%= layoutPageTemplateDisplayContext.getDisplayStyle() %>" />
		</portlet:renderURL>

		<aui:nav-bar-search>
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= layoutPageTemplateDisplayContext.isDisabledLayoutPageTemplateCollectionsManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="layoutPageTemplateCollections"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon"} %>'
			portletURL="<%= currentURLObj %>"
			selectedDisplayStyle="<%= layoutPageTemplateDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= currentURLObj %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= layoutPageTemplateDisplayContext.getOrderByCol() %>"
			orderByType="<%= layoutPageTemplateDisplayContext.getOrderByType() %>"
			orderColumns="<%= layoutPageTemplateDisplayContext.getOrderColumns() %>"
			portletURL="<%= currentURLObj %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedLayoutPageTemplateCollections" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

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
				<portlet:param name="layoutPageTemplateCollectionId" value="<%= String.valueOf(layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()) %>" />
			</portlet:renderURL>

			<%
			row.setCssClass("entry-card lfr-asset-folder");
			%>

			<liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text colspan="<%= 2 %>">
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

		<liferay-ui:search-iterator displayStyle="<%= layoutPageTemplateDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= layoutPageTemplateDisplayContext.isShowAddButton(LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_COLLECTION) %>">
	<portlet:renderURL var="addLayoutPageTemplateCollectionURL">
		<portlet:param name="mvcPath" value="/edit_layout_page_template_collection.jsp" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-collection") %>' url="<%= addLayoutPageTemplateCollectionURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteSelectedLayoutPageTemplateCollections').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>