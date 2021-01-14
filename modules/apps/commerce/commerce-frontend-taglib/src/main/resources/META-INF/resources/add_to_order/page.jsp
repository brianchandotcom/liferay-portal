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

<%@ include file="/add_to_order/init.jsp" %>

<div class="add-to-cart mb-2" id="<%= addToCartId %>">
	<liferay-util:include page="/add_to_order/skeleton.jsp" servletContext="<%= application %>" />
</div>

<aui:script require="commerce-frontend-js/components/add_to_cart/entry as AddToCart">
	const initialProps = {
		channel: {
			currencyCode: '<%= currencyCode %>',
			id: <%= channelId %>,
		},
		cpInstance: {
			accountId: <%= commerceAccountId %>,
			inCart: <%= inCart %>,
			options: '<%= options %>',
			skuId: <%= skuId %>,
			stockQuantity: <%= stockQuantity %>,
		},
		orderId: <%= orderId %>,
		settings: {
			block: <%= block %>,
			disabled: <%= disabled %>,
			willUpdate: <%= willUpdate %>,
			withQuantity: {
				forceDropdown: true,
			},
		},
		spritemap: '<%= spritemap %>',
	};

	AddToCart.default('<%= addToCartId %>', '<%= addToCartId %>', initialProps);
</aui:script>