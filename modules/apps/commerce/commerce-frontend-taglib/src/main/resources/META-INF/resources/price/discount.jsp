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

<%@ include file="/price/init.jsp" %>

<%
String[] discountPercentages = priceModel.getDiscountPercentages();
%>

<c:if test="<%= !compact %>">
	<span class="price-label">
		<%= LanguageUtil.get(request, "discount") %>
	</span>
	<span class="price-value price-value-discount">
		<c:choose>
			<c:when test="<%= displayDiscountLevels && Validator.isNotNull(discountPercentages) %>">
				<c:forEach items="<%= discountPercentages %>" var="percentage">
					<span class="price-value-percentages">${percentage}</span>
				</c:forEach>
			</c:when>
			<c:otherwise>
				&ndash;<%= priceModel.getDiscountPercentage() %>
			</c:otherwise>
		</c:choose>
	</span>
</c:if>

<span class="price-label">
	<c:choose>
		<c:when test="<%= netPrice %>">
			<%= LanguageUtil.get(request, "net-price") %>
		</c:when>
		<c:otherwise>
			<%= LanguageUtil.get(request, "gross-price") %>
		</c:otherwise>
	</c:choose>
</span>
<span class="price-value price-value-final">
	<%= priceModel.getFinalPrice() %>
</span>