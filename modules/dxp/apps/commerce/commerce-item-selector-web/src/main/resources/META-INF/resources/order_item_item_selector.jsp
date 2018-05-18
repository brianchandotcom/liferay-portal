<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
				name="name"
				value="<%= HtmlUtil.escape(commerceOrderItem.getName(languageId)) %>"
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