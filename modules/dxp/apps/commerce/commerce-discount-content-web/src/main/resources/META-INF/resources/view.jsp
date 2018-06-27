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

<%@ include file="/init.jsp" %>

<%
CommerceDiscountCouponCodeHelper commerceDiscountCouponCodeHelper = (CommerceDiscountCouponCodeHelper)request.getAttribute(CommerceDiscountWebKeys.COMMERCE_DISCOUNT_COUPON_CODE_HELPER);

String couponCode = commerceDiscountCouponCodeHelper.getCommerceDiscountCouponCode(request);
%>

<portlet:actionURL name="applyCommerceDiscountCouponCode" var="applyCommerceDiscountCouponCodeActionURL" />

<aui:form action="<%= applyCommerceDiscountCouponCodeActionURL %>" method="post" name="fm">
	<liferay-ui:error exception="<%= CommerceDiscountCouponCodeException.class %>" message="please-enter-a-valid-coupon-code" />

	<c:choose>
		<c:when test="<%= Validator.isNotNull(couponCode) %>">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.REMOVE %>" />

			<div class="coupon-code-header">
				<h5><liferay-ui:message key="coupon-code" /></h5>
			</div>

			<div class="coupon-code-body">
				<h3 class="d-inline"><%= HtmlUtil.escape(couponCode) %></h3>

				<a class="d-inline" href="javascript:;" id="<portlet:namespace />couponCodeIconRemove">
					<liferay-ui:icon
						icon="times"
						markupView="lexicon"
						message="remove"
					/>
				</a>
			</div>

			<aui:script use="aui-base">
				A.one('#<portlet:namespace />couponCodeIconRemove').on(
					'click',
					function(event) {
						event.preventDefault();

						submitForm(document.<portlet:namespace />fm);
					}
				);
			</aui:script>
		</c:when>
		<c:otherwise>
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />

			<aui:input name="couponCode" placeholder="enter-promo-code" type="text" />

			<aui:button type="submit" value="apply" />
		</c:otherwise>
	</c:choose>
</aui:form>