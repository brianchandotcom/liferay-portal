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
String allowedCartQuantity = (String)request.getAttribute("liferay-commerce:quantity-input:allowedCartQuantity");
CPDefinition cpDefinition = (CPDefinition)request.getAttribute("liferay-commerce:quantity-input:cpDefinition");
int maxCartQuantity = (int)request.getAttribute("liferay-commerce:quantity-input:maxCartQuantity");
int minCartQuantity = (int)request.getAttribute("liferay-commerce:quantity-input:minCartQuantity");
int multipleCartQuantity = (int)request.getAttribute("liferay-commerce:quantity-input:multipleCartQuantity");
boolean useSelect = (boolean)request.getAttribute("liferay-commerce:quantity-input:useSelect");
int value = (int)request.getAttribute("liferay-commerce:quantity-input:value");

long cpDefinitionId = cpDefinition.getCPDefinitionId();

int[] allowedCartQuantities = null;

if (Validator.isNotNull(allowedCartQuantity)) {
	allowedCartQuantities = StringUtil.split(allowedCartQuantity, 0);

	Arrays.sort(allowedCartQuantities);
}
%>

<div class="commerce-quantity-container">
	<c:choose>
		<c:when test="<%= !useSelect %>">
			<aui:input ignoreRequestValue="<%= true %>" label="quantity" name='<%= cpDefinitionId + "Quantity" %>' type="number" value="<%= value %>">
				<aui:validator name="number" />
				<aui:validator name="min"><%= minCartQuantity %></aui:validator>
				<aui:validator name="max"><%= maxCartQuantity %></aui:validator>
			</aui:input>
		</c:when>
		<c:when test="<%= allowedCartQuantities != null %>">
			<aui:select ignoreRequestValue="<%= true %>" label="quantity" name='<%= cpDefinitionId + "Quantity" %>'>

				<%
				for (int curQuantity : allowedCartQuantities) {
				%>

					<aui:option label="<%= curQuantity %>" selected="<%= curQuantity == value %>" value="<%= curQuantity %>" />

				<%
				}
				%>

			</aui:select>
		</c:when>
		<c:otherwise>
			<aui:select ignoreRequestValue="<%= true %>" label="quantity" name='<%= cpDefinitionId + "Quantity" %>'>

				<%
				int quantity = 1;

				if (minCartQuantity > 1) {
					quantity = minCartQuantity;
				}

				if (multipleCartQuantity > 1) {
					quantity = multipleCartQuantity;
				}

				for (int i = 1; i < 10; i++) {
				%>

					<aui:option label="<%= quantity %>" selected="<%= quantity == value %>" value="<%= quantity %>" />

				<%
					if ((maxCartQuantity > 0) && (quantity == maxCartQuantity)) {
						break;
					}

					if (multipleCartQuantity > 1) {
						quantity = quantity + multipleCartQuantity;
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