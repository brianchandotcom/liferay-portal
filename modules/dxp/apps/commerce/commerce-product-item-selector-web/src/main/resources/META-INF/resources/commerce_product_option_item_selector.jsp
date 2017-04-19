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
List<CommerceProductOption> commerceProductOptions = (List<CommerceProductOption>)request.getAttribute("commerceProductOptions");
String itemSelectedEventName = (String)request.getAttribute("itemSelectedEventName");
%>

<div id="<portlet:namespace />commerceProductOptionSelectorWrapper">
	<liferay-ui:search-container
		emptyResultsMessage="no-product-options-were-found"
		iteratorURL="<%= myportletURL %>"
		total='<%= GetterUtil.getInteger(request.getAttribute("total")) %>'
	>
		<liferay-ui:search-container-results
			results="<%= commerceProductOptions %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CommerceProductOption"
			cssClass="commerce-product-option-row" modelVar="commerceProductOption"
		>
			<liferay-ui:search-container-column-text>
				<div class="commerce-product-option-name"
					data-id="<%= commerceProductOption.getCommerceProductOptionId() %>"
					<%= commerceProductOption.getName() %>
				</div>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</liferay-ui:search-container>
</div>

<aui:script use="product-option-item-selector">
	var commerceProductOptionSelectorWrapper = A.one("#<portlet:namespace />commerceProductOptionSelectorWrapper");

	commerceProductOptionSelectorWrapper.delegate(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			A.all('.commerce-product-option-row').removeClass('active');

			currentTarget.addClass('active');

			var commerceProductOptionName = currentTarget.one('.commerce-product-option-name');

			var commerceProductOptionId = pon.attr('data-id');

			var data =
				{
					data: {
						returnType: 'COMMERCE-PRODUCT-OPTION',
						value: {
							id: commerceProductOptionId
						}
					}
				};

			Liferay.Util.getOpener().Liferay.fire('<%= itemSelectedEventName %>', data);
		},
		'.commerce-product-option-row'
	);
</aui:script>