<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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