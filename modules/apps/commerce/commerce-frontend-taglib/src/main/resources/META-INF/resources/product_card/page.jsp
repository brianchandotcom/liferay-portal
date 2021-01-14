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

<%@ include file="/product_card/init.jsp" %>

<liferay-util:dynamic-include key="com.liferay.commerce.product.content.web#/add_to_cart#pre" />

<div class="card d-flex flex-column product-card">
	<div class="aspect-ratio aspect-ratio-4-to-3 card-item-first">
		<a href="<%= productDetailURL %>">
			<liferay-util:include page="/product_card/product_image.jsp" servletContext="<%= application %>" />
		</a>
	</div>

	<div class="card-body d-flex flex-column justify-content-between py-2">
		<liferay-util:include page="/product_card/product_information.jsp" servletContext="<%= application %>" />

		<div>
			<liferay-util:include page="/product_card/add_to_cart.jsp" servletContext="<%= application %>" />

			<div class="autofit-float autofit-row autofit-row-center compare-wishlist">
				<liferay-util:include page="/product_card/compare.jsp" servletContext="<%= application %>" />
				<liferay-util:include page="/product_card/add_to_wish_list.jsp" servletContext="<%= application %>" />
			</div>
		</div>
	</div>
</div>

<liferay-util:dynamic-include key="com.liferay.commerce.product.content.web#/add_to_cart#post" />