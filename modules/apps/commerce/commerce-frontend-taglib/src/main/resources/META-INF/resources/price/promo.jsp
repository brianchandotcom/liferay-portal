<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/price/init.jsp" %>

<%
String inactivePriceCssClass = StringPool.BLANK;

if (Validator.isNotNull(priceModel.getDiscount())) {
	inactivePriceCssClass = " price-value-inactive";
}
%>

<span class="price-label">
	<liferay-ui:message key="promotion-price" />
</span>
<span class="price-value price-value-promo<%= inactivePriceCssClass %>">
	<%= priceModel.getPromoPrice() %>
</span>