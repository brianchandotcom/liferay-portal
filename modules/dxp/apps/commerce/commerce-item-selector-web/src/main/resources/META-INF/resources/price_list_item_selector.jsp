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
			className="com.liferay.commerce.price.list.model.CommercePriceList"
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