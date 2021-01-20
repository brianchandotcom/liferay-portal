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
CommerceDiscountValue commerceDiscountValue = (CommerceDiscountValue)request.getAttribute("commerce-ui:price:commerceDiscountValue");
long cpInstanceId = (long)request.getAttribute("commerce-ui:price:cpInstanceId");
DecimalFormat decimalFormat = (DecimalFormat)request.getAttribute("commerce-ui:price:decimalFormat");
boolean displayDiscountLevels = (boolean)request.getAttribute("commerce-ui:price:displayDiscountLevels");
boolean displayOneLine = (boolean)request.getAttribute("commerce-ui:price:displayOneLine");
String discountLabel = (String)request.getAttribute("commerce-ui:price:discountLabel");
boolean netPrice = (boolean)request.getAttribute("commerce-ui:price:netPrice");
String formattedFinalPrice = (String)request.getAttribute("commerce-ui:price:formattedFinalPrice");
String formattedPrice = (String)request.getAttribute("commerce-ui:price:formattedPrice");
String formattedPromoPrice = (String)request.getAttribute("commerce-ui:price:formattedPromoPrice");
String promoPriceLabel = (String)request.getAttribute("commerce-ui:price:promoPriceLabel");
boolean showDiscount = (boolean)request.getAttribute("commerce-ui:price:showDiscount");
boolean showDiscountAmount = (boolean)request.getAttribute("commerce-ui:price:showDiscountAmount");
boolean showPercentage = (boolean)request.getAttribute("commerce-ui:price:showPercentage");
boolean showPriceRange = (boolean)request.getAttribute("commerce-ui:price:showPriceRange");

boolean showStartingAt = true;

if ((showPriceRange && (cpInstanceId <= 0)) || (cpInstanceId >= 0)) {
	showStartingAt = false;
}

String promoPriceClass = "";

if (Validator.isNull(formattedPromoPrice)) {
	promoPriceClass = "hide";
}

String promoPriceActiveClass = "";

if ((commerceDiscountValue != null) && showDiscount) {
	promoPriceActiveClass = " price-value-inactive";
}

String discountClass = "";

if (!showDiscount) {
	discountClass = "hide";
}
%>

<c:choose>
	<c:when test="<%= displayOneLine %>">
		<span class="price-value <%= (Validator.isNotNull(formattedFinalPrice) && formattedFinalPrice.equals(formattedPrice)) ? "" : "price-value-inactive mr-2" %>">
			<%= formattedPrice %>
		</span>
		<span class="price-value price-value-final <%= (Validator.isNotNull(formattedFinalPrice) && !formattedFinalPrice.equals(formattedPrice)) ? "" : "hide" %>">
			<%= formattedFinalPrice %>
		</span>
	</c:when>
	<c:otherwise>
		<span class="price-label" data-text-cp-instance-price-label>
			<span class="<%= showStartingAt ? "hide" : "" %>">
				<liferay-ui:message key="list-price" />
			</span>
			<span class="<%= showStartingAt ? "" : "hide" %>">
				<liferay-ui:message key="starting-at" />
			</span>
		</span>
		<span class="product-promo-price <%= Validator.isNull(formattedPromoPrice) ? "" : "price-value-inactive" %>" data-text-cp-instance-price>
			<%= formattedPrice %>
		</span>
		<span class="price-label <%= promoPriceClass %>" data-text-cp-instance-promo-price-label>
			<%= Validator.isNull(promoPriceLabel) ? LanguageUtil.get(request, "sale-price") : promoPriceLabel %>
		</span>
		<span class="product-price <%= promoPriceClass + promoPriceActiveClass %>" data-text-cp-instance-promo-price>
			<%= formattedPromoPrice %>
		</span>

		<c:if test="<%= commerceDiscountValue != null %>">

			<%
			CommerceMoney discountAmountCommerceMoney = commerceDiscountValue.getDiscountAmount();
			%>

			<span class="price-label <%= discountClass %>">
				<%= Validator.isNull(discountLabel) ? LanguageUtil.get(request, "discount") : discountLabel %>
			</span>

			<c:if test="<%= showDiscountAmount %>">
				<span class="product-price price-value-discount <%= discountClass %>" data-text-cp-instance-discount-amount><%= HtmlUtil.escape(discountAmountCommerceMoney.format(locale)) %></span>
			</c:if>

			<c:if test="<%= showPercentage %>">

				<%
				BigDecimal[] percentages = commerceDiscountValue.getPercentages();

				decimalFormat.setPositiveSuffix(StringPool.PERCENT);
				%>

				<c:choose>
					<c:when test="<%= displayDiscountLevels && !ArrayUtil.isEmpty(percentages) %>">
						<span class="discount-percentage-level1 price-value-discount <%= discountClass %>" data-text-cp-instance-discount-percentage-level-1><%= decimalFormat.format(percentages[0]) %></span>

						<c:if test="<%= percentages[1].compareTo(BigDecimal.ZERO) > 0 %>">
							<span class="discount-percentage-level2 price-value-discount <%= discountClass %>" data-text-cp-instance-discount-percentage-level-2><%= decimalFormat.format(percentages[1]) %></span>
						</c:if>

						<c:if test="<%= percentages[2].compareTo(BigDecimal.ZERO) > 0 %>">
							<span class="discount-percentage-level3 price-value-discount <%= discountClass %>" data-text-cp-instance-discount-percentage-level-3><%= decimalFormat.format(percentages[2]) %></span>
						</c:if>

						<c:if test="<%= percentages[3].compareTo(BigDecimal.ZERO) > 0 %>">
							<span class="discount-percentage-level4 price-value-discount <%= discountClass %>" data-text-cp-instance-discount-percentage-level-4><%= decimalFormat.format(percentages[3]) %></span>
						</c:if>
					</c:when>
					<c:otherwise>
						<span class="discount-percentage price-value price-value-discount <%= discountClass %>" data-text-cp-instance-discount-percentage><%= decimalFormat.format(commerceDiscountValue.getDiscountPercentage()) %></span>
					</c:otherwise>
				</c:choose>
			</c:if>

			<c:if test="<%= Validator.isNotNull(formattedFinalPrice) %>">
				<span class="price-label">
					<%= netPrice ? LanguageUtil.get(request, "net-price") : LanguageUtil.get(request, "gross-price") %>
				</span>
				<span class="product-price price-value-big" data-text-cp-instance-final-price><%= formattedFinalPrice %></span>
			</c:if>
		</c:if>
	</c:otherwise>
</c:choose>