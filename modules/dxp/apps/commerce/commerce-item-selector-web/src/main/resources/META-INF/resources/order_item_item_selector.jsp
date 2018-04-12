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
CommerceOrderItemItemSelectorViewDisplayContext commerceOrderItemItemSelectorViewDisplayContext = (CommerceOrderItemItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String itemSelectedEventName = commerceOrderItemItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = commerceOrderItemItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceOrderItems"
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
			orderByCol="<%= commerceOrderItemItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceOrderItemItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />commerceOrderItemSelectorWrapper">
	<liferay-ui:search-container
		id="commerceOrderItems"
		searchContainer="<%= commerceOrderItemItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceOrderItem"
			cssClass="commerce-order-item-row"
			keyProperty="commerceOrderItemId"
			modelVar="commerceOrderItem"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="title"
				value="<%= HtmlUtil.escape(commerceOrderItem.getTitle(languageId)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="quantity"
			>
				<%= HtmlUtil.escape(String.valueOf(commerceOrderItem.getQuantity() - commerceOrderItem.getShippedQuantity())) %>
			</liferay-ui:search-container-column-text>

			<%
			long commerceOrderItemId = commerceOrderItem.getCommerceOrderItemId();
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="warehouse-quantity"
			>
				<%= HtmlUtil.escape(String.valueOf(commerceOrderItemItemSelectorViewDisplayContext.getCommerceWarehouseItemQuantity(commerceOrderItemId))) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="price"
				value="<%= commerceOrderItemItemSelectorViewDisplayContext.getFormattedPrice(commerceOrderItemId) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="user"
				property="userName"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-content"
				name="create-date"
				property="createDate"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
			searchContainer="<%= commerceOrderItemItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>

		<liferay-ui:search-paginator
			searchContainer="<%= commerceOrderItemItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var commerceOrderItemSelectorWrapper = A.one("#<portlet:namespace />commerceOrderItemSelectorWrapper");

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commerceOrderItems');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
				{
					data: Liferay.Util.listCheckedExcept(commerceOrderItemSelectorWrapper, '<portlet:namespace />allRowIds')
				}
			);
		}
	);
</aui:script>