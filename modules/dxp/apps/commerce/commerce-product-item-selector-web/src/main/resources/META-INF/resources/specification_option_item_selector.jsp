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
CPSpecificationOptionItemSelectorViewDisplayContext cpSpecificationOptionItemSelectorViewDisplayContext = (CPSpecificationOptionItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer cpSpecificationOptionSearchContainer = cpSpecificationOptionItemSelectorViewDisplayContext.getSearchContainer();

String displayStyle = cpSpecificationOptionItemSelectorViewDisplayContext.getDisplayStyle();

String itemSelectedEventName = cpSpecificationOptionItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = cpSpecificationOptionItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpSpecificationOptions"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpSpecificationOptionItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpSpecificationOptionItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"title"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />cpSpecificationOptionSelectorWrapper">
	<liferay-ui:search-container
		id="cpSpecificationOptions"
		searchContainer="<%= cpSpecificationOptionSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CPSpecificationOption"
			cssClass="commerce-product-option-row"
			keyProperty="CPSpecificationOptionId"
			modelVar="cpSpecificationOption"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="title"
			>
				<div class="commerce-product-option-title" data-id="<%= cpSpecificationOption.getCPSpecificationOptionId() %>">
					<%= HtmlUtil.escape(cpSpecificationOption.getTitle(themeDisplay.getLanguageId())) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="author"
				property="userName"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="use-in-faceted-navigation"
				property="facetable"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-content"
				name="create-date"
				property="createDate"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-content"
				name="modified-date"
				property="modifiedDate"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
			searchContainer="<%= cpSpecificationOptionSearchContainer %>"
		/>

		<liferay-ui:search-paginator
			searchContainer="<%= cpSpecificationOptionSearchContainer %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var cpSpecificationOptionSelectorWrapper = A.one("#<portlet:namespace />cpSpecificationOptionSelectorWrapper");

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />cpSpecificationOptions');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
				{
					data: Liferay.Util.listCheckedExcept(cpSpecificationOptionSelectorWrapper, '<portlet:namespace />allRowIds')
				}
			);
		}
	);
</aui:script>