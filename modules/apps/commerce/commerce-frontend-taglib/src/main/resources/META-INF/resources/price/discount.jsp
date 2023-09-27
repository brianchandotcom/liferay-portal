<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/price/init.jsp" %>

<%
String[] discountPercentages = priceModel.getDiscountPercentages();
%>

<c:if test="<%= !compact %>">
	<span class="price-label">
		<liferay-ui:message key="discount" />
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
			<liferay-ui:message key="net-price" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="gross-price" />
		</c:otherwise>
	</c:choose>
</span>
<span class="price-value price-value-final">
	<%= priceModel.getFinalPrice() %>
</span>