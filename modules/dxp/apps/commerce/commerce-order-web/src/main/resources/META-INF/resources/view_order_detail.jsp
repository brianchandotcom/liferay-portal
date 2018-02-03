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
CommerceOrderDetailDisplayContext commerceOrderDetailDisplayContext = (CommerceOrderDetailDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceOrderItem> commerceOrderItemsSearchContainer = commerceOrderDetailDisplayContext.getSearchContainer();

CommerceOrder commerceOrder = commerceOrderDetailDisplayContext.getCommerceOrder();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.format(request, "order-x", commerceOrderDetailDisplayContext.getCommerceOrderId()));
%>

<div class="container-fluid-1280">
	<div class="commerce-order-details-container">
		<h1><%= LanguageUtil.get(request, "order") + StringPool.SPACE + commerceOrderDetailDisplayContext.getCommerceOrderId() %></h1>

		<portlet:renderURL var="viewCommerceOrderNotesURL">
			<portlet:param name="mvcRenderCommandName" value="viewCommerceOrderNotes" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderDetailDisplayContext.getCommerceOrderId()) %>" />
		</portlet:renderURL>

		<%
		int commerceOrderNotesCount = commerceOrderDetailDisplayContext.getCommerceOrderNotesCount();

		String taglibIconCssClass = "icon-file-text";

		if (commerceOrderNotesCount <= 0) {
			taglibIconCssClass += " no-notes";
		}

		String taglibMessage = null;

		if (commerceOrderNotesCount == 1) {
			taglibMessage = LanguageUtil.get(request, "1-note");
		}
		else {
			taglibMessage = LanguageUtil.format(request, "x-notes", commerceOrderNotesCount, false);
		}
		%>

		<liferay-ui:icon
			cssClass="notes-icon"
			iconCssClass="<%= taglibIconCssClass %>"
			label="notes"
			message="<%= taglibMessage %>"
			method="get"
			url="<%= viewCommerceOrderNotesURL %>"
		/>

		<div class="commerce-order-details-header row">
			<div class="col-md-2">
				<h3><%= LanguageUtil.get(request, "order-date") %></h3>

				<%= commerceOrderDetailDisplayContext.getCommerceOrderDateTime() %>
			</div>

			<div class="col-md-2">
				<h3><%= LanguageUtil.get(request, "customer") %></h3>

				<%= HtmlUtil.escape(commerceOrderDetailDisplayContext.getCommerceOrderCustomerName()) %>

				<%= commerceOrder.getOrderUserId() %>
			</div>

			<div class="col-md-2">
				<h3><%= LanguageUtil.get(request, "payment") %></h3>

				<%= HtmlUtil.escape(commerceOrderDetailDisplayContext.getCommerceOrderPaymentMethodName()) %>
			</div>

			<div class="col-md-2">
				<h3><%= LanguageUtil.get(request, "order-value") %></h3>

				<%= HtmlUtil.escape(commerceOrderDetailDisplayContext.getCommerceOrderTotal()) %>
			</div>

			<div class="col-md-2">
				<h3><%= LanguageUtil.get(request, "status") %></h3>

				<%= HtmlUtil.escape(commerceOrderDetailDisplayContext.getCommerceOrderStatusLabel()) %>
			</div>

			<div class="col-md-2">
				<aui:button name="redoCommerceOrder" primary="<%= true %>" value="reorder" />
			</div>
		</div>

		<div class="commerce-order-details-footer row">
			<div class="col-md-4">
				<%= LanguageUtil.format(request, "x-x", new String[] {"carrier", commerceOrderDetailDisplayContext.getCommerceOrderShippingOptionName()}, true) %>
			</div>

			<div class="col-md-4">
				<%= LanguageUtil.format(request, "x-x", new String[] {"method", commerceOrderDetailDisplayContext.getCommerceOrderShippingMethodName()}, true) %>
			</div>

			<div class="col-md-4">
				<%= LanguageUtil.format(request, "x-x", new String[] {"expected-duration", commerceOrderDetailDisplayContext.getCommerceOrderShippingDuration()}, true) %>
			</div>
		</div>
	</div>

	<liferay-ui:search-container
		searchContainer="<%= commerceOrderItemsSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceOrderItem"
			escapedModel="<%= true %>"
			keyProperty="commerceOrderItemId"
			modelVar="commerceOrderItem"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="sku"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="title"
				value="<%= HtmlUtil.escape(commerceOrderItem.getTitle(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="quantity"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="price"
				value="<%= commerceOrderDetailDisplayContext.getCommerceOrderItemPrice(commerceOrderItem) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />
	</liferay-ui:search-container>
</div>