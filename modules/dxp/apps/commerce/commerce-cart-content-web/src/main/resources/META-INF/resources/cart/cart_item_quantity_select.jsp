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
int type = ParamUtil.getInteger(request, "type", CommerceCartConstants.COMMERCE_CART_TYPE_CART);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceCartItem commerceCartItem = (CommerceCartItem)row.getObject();

String quantityColumnContainer = "commerce-cart-item-quantity-column-container" + row.getRowId();
%>

<portlet:actionURL name="editCommerceCartItem" var="editCommerceCartItemURL" />

<div id="<portlet:namespace /><%= quantityColumnContainer %>">
	<aui:form action="<%= editCommerceCartItemURL %>" method="post" name="editCommerceCartItemQuantityFm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceCartItemId" type="hidden" value="<%= commerceCartItem.getCommerceCartItemId() %>" />
		<aui:input name="type" type="hidden" value="<%= type %>" />

		<aui:input bean="<%= commerceCartItem %>" cssClass="commerce-cart-item-quantity-input" data-quantityColumnContainer="<%= quantityColumnContainer %>" label="<%= StringPool.BLANK %>" name="quantity" onChange="javascript:;" />

		<aui:button cssClass="commerce-cart-item-quantity-button hide" name="refreshButton" onClick="javascript:;" value="refresh" />
	</aui:form>
</div>

<aui:script>
	var quantityInput = $('#<portlet:namespace /><%= quantityColumnContainer %> .commerce-cart-item-quantity-input');

	quantityInput.on(
		'change',
		function(event) {
			var curTarget = event.currentTarget;

			var quantityColumnContainer = curTarget.getAttribute('data-quantityColumnContainer');

			var refreshButton = $('#<portlet:namespace />' + quantityColumnContainer + ' .commerce-cart-item-quantity-button');

			refreshButton.removeClass('hide');
		}
	);

	$('#<portlet:namespace /><%= quantityColumnContainer %> .commerce-cart-item-quantity-button').on(
		'click',
		function(event) {
			event.preventDefault();

			var editCommerceCartItemQuantityFm = $('#<portlet:namespace /><%= quantityColumnContainer %> #<portlet:namespace />editCommerceCartItemQuantityFm');

			submitForm(editCommerceCartItemQuantityFm);
		}
	);
</aui:script>