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
CommercePriceListItemSelectorViewDisplayContext commercePriceListItemSelectorViewDisplayContext = (CommercePriceListItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String itemSelectedEventName = commercePriceListItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = commercePriceListItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commercePriceLists"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commercePriceListItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= commercePriceListItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date", "display-date"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />commercePriceListSelectorWrapper">
	<liferay-ui:search-container
		id="commercePriceLists"
		searchContainer="<%= commercePriceListItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommercePriceList"
			cssClass="commerce-price-list-row"
			keyProperty="commercePriceListId"
			modelVar="commercePriceList"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="author"
				property="userName"
			/>

			<liferay-ui:search-container-column-status
				cssClass="table-cell-content"
				name="status"
				status="<%= commercePriceList.getStatus() %>"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-content"
				name="create-date"
				property="createDate"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-content"
				name="display-date"
				property="displayDate"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
			searchContainer="<%= commercePriceListItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>

		<liferay-ui:search-paginator
			searchContainer="<%= commercePriceListItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var commercePriceListSelectorWrapper = A.one("#<portlet:namespace />commercePriceListSelectorWrapper");

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commercePriceLists');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
				{
					data: Liferay.Util.listCheckedExcept(commercePriceListSelectorWrapper, '<portlet:namespace />allRowIds')
				}
			);
		}
	);
</aui:script>