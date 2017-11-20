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
CommercePriceListQualificationTypeItemSelectorViewDisplayContext commercePriceListQualificationTypeItemSelectorViewDisplayContext = (CommercePriceListQualificationTypeItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String itemSelectedEventName = commercePriceListQualificationTypeItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = commercePriceListQualificationTypeItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commercePriceListQualificationTypes"
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
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />commercePriceListQualificationTypeSelectorWrapper">
	<liferay-ui:search-container
		id="commercePriceListQualificationTypes"
		searchContainer="<%= commercePriceListQualificationTypeItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.price.CommercePriceListQualificationType"
			cssClass="commerce-price-list-qualification-type-row"
			keyProperty="key"
			modelVar="commercePriceListQualificationType"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="key"
			>
				<div id="<portlet:namespace /><%= commercePriceListQualificationType.getKey() %>">
					<%= HtmlUtil.escape(commercePriceListQualificationType.getLabel(request)) %>
				</div>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" searchContainer="<%= commercePriceListQualificationTypeItemSelectorViewDisplayContext.getSearchContainer() %>" />

		<liferay-ui:search-paginator searchContainer="<%= commercePriceListQualificationTypeItemSelectorViewDisplayContext.getSearchContainer() %>" />
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var commercePriceListQualificationTypeSelectorWrapper = A.one("#<portlet:namespace />commercePriceListQualificationTypeSelectorWrapper");

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commercePriceListQualificationTypes');

	searchContainer.on(
		'rowToggled',
		function(event) {
			var arr = [];

			var data = Liferay.Util.listCheckedExcept(commercePriceListQualificationTypeSelectorWrapper, '<portlet:namespace />allRowIds');

			var dataArray = data.split(',');

			A.Array.each(
				dataArray,
				function(item, index, dataArray) {
					var label = item;

					var node = A.one('#<portlet:namespace />'+ item);

					if (node) {
						label = node.text();
					}

					arr.push(
						{
							key: item,
							label: label
						}
					);
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