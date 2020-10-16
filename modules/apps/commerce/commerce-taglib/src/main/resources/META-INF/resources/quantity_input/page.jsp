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

<%@ include file="/quantity_input/init.jsp" %>

<%
int[] allowedOrderQuantities = (int[])request.getAttribute("liferay-commerce:quantity-input:allowedOrderQuantities");
long cpDefinitionId = (long)request.getAttribute("liferay-commerce:quantity-input:cpDefinitionId");
int maxOrderQuantity = (int)request.getAttribute("liferay-commerce:quantity-input:maxOrderQuantity");
int minOrderQuantity = (int)request.getAttribute("liferay-commerce:quantity-input:minOrderQuantity");
int multipleOrderQuantity = (int)request.getAttribute("liferay-commerce:quantity-input:multipleOrderQuantity");
String name = (String)request.getAttribute("liferay-commerce:quantity-input:name");
boolean showLabel = (boolean)request.getAttribute("liferay-commerce:quantity-input:showLabel");
boolean useSelect = (boolean)request.getAttribute("liferay-commerce:quantity-input:useSelect");
int value = (int)request.getAttribute("liferay-commerce:quantity-input:value");

if (Validator.isNull(name)) {
	name = cpDefinitionId + "Quantity";
}
%>

<div class="commerce-quantity-container">
	<c:choose>
		<c:when test="<%= ArrayUtil.isEmpty(allowedOrderQuantities) && !useSelect %>">
			<aui:input cssClass="commerce-input mb-0 u-wauto" ignoreRequestValue="<%= true %>" label='<%= showLabel ? "quantity" : StringPool.BLANK %>' name="<%= HtmlUtil.escape(name) %>" type="number" value="<%= value %>" wrapperCssClass="mb-0">
				<aui:validator name="number" />
				<aui:validator name="min"><%= minOrderQuantity %></aui:validator>
				<aui:validator name="max"><%= maxOrderQuantity %></aui:validator>
			</aui:input>
		</c:when>
		<c:when test="<%= ArrayUtil.isNotEmpty(allowedOrderQuantities) %>">
			<aui:select cssClass="commerce-input mb-0" ignoreRequestValue="<%= true %>" label='<%= showLabel ? "quantity" : StringPool.BLANK %>' name="<%= HtmlUtil.escape(name) %>" wrapperCssClass="mb-0">

				<%
				for (int curQuantity : allowedOrderQuantities) {
				%>

					<aui:option label="<%= curQuantity %>" selected="<%= curQuantity == value %>" value="<%= curQuantity %>" />

				<%
				}
				%>

			</aui:select>
		</c:when>
		<c:otherwise>
			<aui:select cssClass="commerce-input commerce-input--select u-wauto" ignoreRequestValue="<%= true %>" label='<%= showLabel ? "quantity" : StringPool.BLANK %>' name="<%= HtmlUtil.escape(name) %>">

				<%
				int quantity = 1;

				if (minOrderQuantity > 1) {
					quantity = minOrderQuantity;
				}

				if (multipleOrderQuantity > 1) {
					quantity = multipleOrderQuantity;
				}

				for (int i = 1; i < 10; i++) {
				%>

					<aui:option label="<%= quantity %>" selected="<%= quantity == value %>" value="<%= quantity %>" />

				<%
					if ((maxOrderQuantity > 0) && (quantity == maxOrderQuantity)) {
						break;
					}

					if (multipleOrderQuantity > 1) {
						quantity = quantity + multipleOrderQuantity;
					}
					else {
						quantity++;
					}
				}
				%>

			</aui:select>
		</c:otherwise>
	</c:choose>
</div>