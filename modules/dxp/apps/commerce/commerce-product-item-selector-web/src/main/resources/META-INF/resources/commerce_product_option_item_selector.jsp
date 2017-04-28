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
PortletURL myportletURL = (PortletURL)request.getAttribute("portletURL");
List<CPOption> cpOptions = (List<CPOption>)request.getAttribute("cpOptions");
String itemSelectedEventName = (String)request.getAttribute("itemSelectedEventName");
%>

<div id="<portlet:namespace />cpOptionSelectorWrapper">
	<liferay-ui:search-container
		emptyResultsMessage="no-options-were-found"
		iteratorURL="<%= myportletURL %>"
		total='<%= GetterUtil.getInteger(request.getAttribute("total")) %>'
	>
		<liferay-ui:search-container-results
			results="<%= cpOptions %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CPOption"
			cssClass="commerce-product-option-row" modelVar="cpOption"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
			>
				<div class="commerce-product-option-name"
					data-id="<%= cpOption.getCPOptionId() %>"
					<%= cpOption.getName() %>
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

		<liferay-ui:search-iterator markupView="lexicon" />

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

			var data =
				{
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