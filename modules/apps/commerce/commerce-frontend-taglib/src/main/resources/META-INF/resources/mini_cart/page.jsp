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

<%@ include file="/mini_cart/init.jsp" %>

<div class="cart-root" id="<%= miniCartId %>"></div>

<aui:script require="commerce-frontend-js/components/mini_cart/entry as Cart">
	var initialProps = {
		cartActionURLs: {
			checkoutURL: '<%= checkoutURL %>',
			orderDetailURL: '<%= orderDetailURL %>',
		},
		displayDiscountLevels: <%= displayDiscountLevels %>,
		orderId: <%= orderId %>,
		spritemap: '<%= spritemap %>',
		toggleable: <%= toggleable %>,
	};

	var cartViewRendererURL = '<%= cartViewRendererURL %>',
		cartItemsListViewRendererURL = '<%= cartItemsListViewRendererURL %>';

	if (cartViewRendererURL) {
		initialProps.cartView = {
			contentRendererModuleURL: cartViewRendererURL,
		};
	}

	if (cartItemsListViewRendererURL) {
		initialProps.cartItemsListView = {
			contentRendererModuleURL: cartItemsListViewRendererURL,
		};
	}

	Cart.default('<%= miniCartId %>', '<%= miniCartId %>', initialProps);
</aui:script>