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
List cpOptions = (List)request.getAttribute("cpOptions");
int cpOptionsCount = GetterUtil.getInteger(request.getAttribute("cpOptionsCount"));
String displayStyle = GetterUtil.getString(request.getAttribute("displayStyle"));
String emptyResultsMessage = GetterUtil.getString(request.getAttribute("emptyResultsMessage"));
String itemSelectedEventName = GetterUtil.getString(request.getAttribute("itemSelectedEventName"));
PortletURL portletURL = (PortletURL)request.getAttribute("portletURL");

SearchContainer searchContainer = new SearchContainer(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse), null, emptyResultsMessage);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpOptions"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>

		<%
		PortletURL sortURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

		String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
		String orderByType = ParamUtil.getString(request, "orderByType", "asc");
		%>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= sortURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />cpOptionSelectorWrapper">
	<liferay-ui:search-container
		id="cpOptions"
		rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
		searchContainer="<%= searchContainer %>"
		total="<%= cpOptionsCount %>"
		var="listSearchContainer"
	>
		<liferay-ui:search-container-results
			results="<%= cpOptions %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CPOption"
			cssClass="commerce-product-option-row"
			modelVar="cpOption"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
			>
				<div class="commerce-product-option-name"
					data-id="<%= cpOption.getCPOptionId() %>"
					<%= HtmlUtil.escape(cpOption.getName()) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="facetable"
				property="facetable"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="sku-contributor"
				property="skuContributor"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</liferay-ui:search-container>
</div>

<aui:script use="product-option-item-selector">
	var cpOptionSelectorWrapper = A.one("#<portlet:namespace />cpOptionSelectorWrapper");

	cpOptionSelectorWrapper.delegate(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			A.all('.commerce-product-option-row').removeClass('active');

			currentTarget.addClass('active');

			var cpOptionName = currentTarget.one('.commerce-product-option-name');

			var cpOptionId = cpOptionName.attr('data-id');

			var data = {
				data: {
					returnType: 'COMMERCE-PRODUCT-OPTION',
					value: {
						id: cpOptionId
					}
				}
			};

			Liferay.Util.getOpener().Liferay.fire('<%= itemSelectedEventName %>', data);
		},
		'.commerce-product-option-row'
	);

</aui:script>