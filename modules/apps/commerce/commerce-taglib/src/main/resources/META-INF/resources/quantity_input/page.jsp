<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/quantity_input/init.jsp" %>

<%
BigDecimal[] allowedOrderQuantities = (BigDecimal[])request.getAttribute("liferay-commerce:quantity-input:allowedOrderQuantities");
long cpDefinitionId = (long)request.getAttribute("liferay-commerce:quantity-input:cpDefinitionId");
BigDecimal maxOrderQuantity = (BigDecimal)request.getAttribute("liferay-commerce:quantity-input:maxOrderQuantity");
BigDecimal minOrderQuantity = (BigDecimal)request.getAttribute("liferay-commerce:quantity-input:minOrderQuantity");
BigDecimal multipleOrderQuantity = (BigDecimal)request.getAttribute("liferay-commerce:quantity-input:multipleOrderQuantity");
String name = (String)request.getAttribute("liferay-commerce:quantity-input:name");
boolean showLabel = (boolean)request.getAttribute("liferay-commerce:quantity-input:showLabel");
boolean useSelect = (boolean)request.getAttribute("liferay-commerce:quantity-input:useSelect");
BigDecimal value = (BigDecimal)request.getAttribute("liferay-commerce:quantity-input:value");

if (Validator.isNull(name)) {
	name = cpDefinitionId + "Quantity";
}
%>

<div class="commerce-quantity-container">
	<c:choose>
		<c:when test="<%= ArrayUtil.isEmpty(allowedOrderQuantities) && !useSelect %>">
			<aui:input cssClass="commerce-input mb-0 u-wauto" ignoreRequestValue="<%= true %>" label='<%= showLabel ? "quantity" : StringPool.BLANK %>' name="<%= HtmlUtil.escape(name) %>" step="<%= multipleOrderQuantity %>" type="number" value="<%= value %>" wrapperCssClass="mb-0">
				<aui:validator name="number" />
				<aui:validator name="min"><%= minOrderQuantity %></aui:validator>
				<aui:validator name="max"><%= maxOrderQuantity %></aui:validator>
			</aui:input>
		</c:when>
		<c:when test="<%= ArrayUtil.isNotEmpty(allowedOrderQuantities) %>">
			<aui:select cssClass="commerce-input mb-0" ignoreRequestValue="<%= true %>" label='<%= showLabel ? "quantity" : StringPool.BLANK %>' name="<%= HtmlUtil.escape(name) %>" wrapperCssClass="mb-0">

				<%
				for (BigDecimal curQuantity : allowedOrderQuantities) {
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
				BigDecimal quantity = BigDecimal.ONE;

				if (BigDecimalUtil.gt(minOrderQuantity, BigDecimal.ONE)) {
					quantity = minOrderQuantity;
				}

				if (BigDecimalUtil.gt(multipleOrderQuantity, BigDecimal.ONE)) {
					quantity = multipleOrderQuantity;
				}

				for (int i = 1; i < 10; i++) {
				%>

					<aui:option label="<%= quantity %>" selected="<%= quantity == value %>" value="<%= quantity %>" />

				<%
					if (BigDecimalUtil.gt(maxOrderQuantity, BigDecimal.ZERO) && BigDecimalUtil.eq(quantity, maxOrderQuantity)) {
						break;
					}

					if (BigDecimalUtil.gt(multipleOrderQuantity, BigDecimal.ONE)) {
						quantity = quantity.add(multipleOrderQuantity);
					}
					else {
						quantity = quantity.add(BigDecimal.ONE);
					}
				}
				%>

			</aui:select>
		</c:otherwise>
	</c:choose>
</div>