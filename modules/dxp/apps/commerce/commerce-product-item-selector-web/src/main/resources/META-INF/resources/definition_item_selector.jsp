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
CPDefinitionItemSelectorViewDisplayContext cpDefinitionItemSelectorViewDisplayContext = (CPDefinitionItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer cpDefinitionSearchContainer = cpDefinitionItemSelectorViewDisplayContext.getSearchContainer();

String displayStyle = cpDefinitionItemSelectorViewDisplayContext.getDisplayStyle();

String itemSelectedEventName = cpDefinitionItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = cpDefinitionItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpDefinitions"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpDefinitionItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpDefinitionItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"title", "modified-date", "display-date"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />cpDefinitionSelectorWrapper">
	<liferay-ui:search-container
		id="cpDefinitions"
		searchContainer="<%= cpDefinitionSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CPDefinition"
			cssClass="commerce-product-definition-row"
			keyProperty="CPDefinitionId"
			modelVar="cpDefinition"
		>

			<%
			Map<String, Object> data = new HashMap<>();

			data.put("cp-definition-id", cpDefinition.getCPDefinitionId());
			data.put("title", cpDefinition.getTitle(themeDisplay.getLanguageId()));

			row.setData(data);

			CPType cpType = cpDefinitionItemSelectorViewDisplayContext.getCPType(cpDefinition.getProductTypeName());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="title"
			>
				<div class="commerce-product-definition-title" data-id="<%= cpDefinition.getCPDefinitionId() %>">
					<%= HtmlUtil.escape(cpDefinition.getTitle(themeDisplay.getLanguageId())) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="type"
			>
				<%= cpType.getLabel(locale) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="author"
				property="userName"
			/>

			<liferay-ui:search-container-column-status
				cssClass="table-cell-content"
				name="status"
				status="<%= cpDefinition.getStatus() %>"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-content"
				name="modified-date"
				property="modifiedDate"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-content"
				name="display-date"
				property="displayDate"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
			searchContainer="<%= cpDefinitionSearchContainer %>"
		/>

		<liferay-ui:search-paginator
			searchContainer="<%= cpDefinitionSearchContainer %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var cpDefinitionSelectorWrapper = A.one("#<portlet:namespace />cpDefinitionSelectorWrapper");

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />cpDefinitions');

	searchContainer.on(
		'rowToggled',
		function(event) {
			var allSelectedElements = event.elements.allSelectedElements
			var arr = [];

			allSelectedElements.each(
				function() {
					var row = this.ancestor('tr');

					var data = row.getDOM().dataset;

					arr.push({cpDefinitionId : data.cpDefinitionId, title : data.title});
				}
			);

			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
				{
					data: arr
				}
			);
		}
	);
</aui:script>