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

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib") + StringPool.UNDERLINE;
%>

<%
if (cpContentListEntryModel.getSkuId() == 0) {
%>

	<div class="add-to-cart d-flex my-2 pt-5" id="<%= randomNamespace + "add_to_cart" %>">
		<a class="btn btn-block btn-secondary" href="<%= productDetailURL %>" role="button" style="margin-top: 0.35rem;">
			<%= LanguageUtil.get(request, "view-all-variants") %>
		</a>
	</div>

<%
}
else {
%>

	<commerce-ui:add-to-order
		block="<%= true %>"
		channelId="<%= cpContentListEntryModel.getChannelId() %>"
		commerceAccountId="<%= cpContentListEntryModel.getAccountId() %>"
		currencyCode="<%= cpContentListEntryModel.getCurrencyCode() %>"
		disabled="<%= cpContentListEntryModel.getStockQuantity() == 0 %>"
		inCart="<%= cpContentListEntryModel.isInCart() %>"
		orderId="<%= cpContentListEntryModel.getOrderId() %>"
		skuId="<%= cpContentListEntryModel.getSkuId() %>"
		spritemap="<%= spritemap %>"
		stockQuantity="<%= cpContentListEntryModel.getStockQuantity() %>"
	/>

<%
}
%>