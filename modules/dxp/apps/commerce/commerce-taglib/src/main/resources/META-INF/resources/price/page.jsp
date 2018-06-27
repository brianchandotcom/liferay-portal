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
CPInstance cpInstance = (CPInstance)request.getAttribute("liferay-commerce:price:cpInstance");
String formattedPrice = (String)request.getAttribute("liferay-commerce:price:formattedPrice");
String formattedPromoPrice = (String)request.getAttribute("liferay-commerce:price:formattedPromoPrice");
boolean showPriceRange = (boolean)request.getAttribute("liferay-commerce:price:showPriceRange");
boolean showPromoPrice = (boolean)request.getAttribute("liferay-commerce:price:showPromoPrice");
%>

<c:choose>
	<c:when test="<%= Validator.isNull(formattedPrice) %>">
	</c:when>
	<c:when test="<%= cpInstance == null %>">
		<span class="product-price">
			<c:if test="<%= !showPriceRange %>">
				<span class="product-price-label">
					<liferay-ui:message key="starting-at" />
				</span>
			</c:if>

			<%= formattedPrice %>
		</span>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="<%= showPromoPrice %>">
				<span class="product-price"><del><%= formattedPrice %></del></span>

				<span class="product-promo-price"><%= formattedPromoPrice %></span>
			</c:when>
			<c:otherwise>
				<span class="product-price"><%= formattedPrice %></span>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>