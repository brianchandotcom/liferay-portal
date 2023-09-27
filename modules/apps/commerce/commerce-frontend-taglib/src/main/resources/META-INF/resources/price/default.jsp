<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/price/init.jsp" %>

<%
String inactivePriceCssClass = StringPool.BLANK;

if (Validator.isNotNull(priceModel.getDiscount()) || Validator.isNotNull(priceModel.getPromoPrice())) {
	inactivePriceCssClass = " price-value-inactive";
}
%>

<span class="price-label">
	<liferay-ui:message key="list-price" />
</span>
<span class="price-value<%= inactivePriceCssClass %>">
	<c:choose>
		<c:when test="<%= Objects.equals(priceModel.getPrice(), CommercePriceConstants.PRICE_VALUE_PRICE_ON_APPLICATION) %>">
			<liferay-ui:message key="<%= CommercePriceConstants.PRICE_VALUE_PRICE_ON_APPLICATION %>" />
		</c:when>
		<c:otherwise>
			<%= priceModel.getPrice() %>
		</c:otherwise>
	</c:choose>
</span>