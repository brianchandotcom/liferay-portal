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

<%@ include file="/info/item/renderer/commerce_order_item/init.jsp" %>

<%
CommerceOrderItem commerceOrderItem = (CommerceOrderItem)request.getAttribute(CommerceWebKeys.COMMERCE_ORDER_ITEM);
Map<String, String> commerceOrderItemContextMap = (Map<String, String>)request.getAttribute(CommerceWebKeys.COMMERCE_ORDER_ITEM_CONTEXT_MAP);
%>

<table>
	<tr class="order-item-row">
		<td class="order-item-column">
			<img src="<%= HtmlUtil.escape(commerceOrderItemContextMap.get("thumbnailURL")) %>" />
		</td>
		<td class="order-item-column">
			<a href="<%= commerceOrderItemContextMap.get("URL") %>">
				<%= HtmlUtil.escape(commerceOrderItem.getSku()) %>
			</a>
		</td>
		<td class="order-item-column">
			<%= HtmlUtil.escape(commerceOrderItem.getName(languageId)) %>
		</td>
		<td class="order-item-column">
			<%= HtmlUtil.escape(commerceOrderItemContextMap.get("options")) %>
		</td>
		<td class="order-item-column">
			<%= HtmlUtil.escape(commerceOrderItemContextMap.get("unitPrice")) %>
		</td>
		<td class="order-item-column">
			<%= HtmlUtil.escape(commerceOrderItemContextMap.get("promoPrice")) %>
		</td>
		<td class="order-item-column">
			<%= HtmlUtil.escape(commerceOrderItemContextMap.get("discountAmount")) %>
		</td>
		<td class="order-item-column">
			<%= HtmlUtil.escape(String.valueOf(commerceOrderItem.getQuantity())) %>
		</td>
		<td class="order-item-column">
			<%= HtmlUtil.escape(commerceOrderItemContextMap.get("totalPrice")) %>
		</td>
	</tr>
</table>