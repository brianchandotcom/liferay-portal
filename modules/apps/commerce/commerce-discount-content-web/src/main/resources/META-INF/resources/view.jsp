<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

String couponCode = null;

CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

if (commerceOrder != null) {
	couponCode = commerceOrder.getCouponCode();
}
%>

<portlet:actionURL name="/commerce_discount_content/apply_commerce_discount_coupon_code" var="applyCommerceDiscountCouponCodeActionURL" />

<aui:form action="<%= applyCommerceDiscountCouponCodeActionURL %>" method="post" name="fm">
	<liferay-ui:error exception="<%= CommerceDiscountCouponCodeException.class %>" message="please-enter-a-valid-coupon-code" />
	<liferay-ui:error exception="<%= CommerceDiscountLimitationTimesException.class %>" message="the-inserted-coupon-code-has-reached-its-usage-limit" />

	<liferay-ui:error exception="<%= CommerceDiscountValidatorException.class %>">

		<%
		CommerceDiscountValidatorException commerceDiscountValidatorException = (CommerceDiscountValidatorException)errorException;
		%>

		<c:if test="<%= commerceDiscountValidatorException != null %>">
			<liferay-ui:message key="<%= commerceDiscountValidatorException.getLocalizedMessage() %>" />
		</c:if>
	</liferay-ui:error>

	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<c:choose>
		<c:when test="<%= Validator.isNull(commerceOrder) %>">
		</c:when>
		<c:when test="<%= Validator.isNotNull(couponCode) %>">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.REMOVE %>" />

			<div class="coupon-code-header">
				<h5><liferay-ui:message key="coupon-code" /></h5>
			</div>

			<div class="coupon-code-body">
				<h3 class="d-inline"><%= HtmlUtil.escape(couponCode) %></h3>

				<a class="d-inline" href="javascript:void(0);" id="<portlet:namespace />couponCodeIconRemove">
					<liferay-ui:icon
						icon="times"
						markupView="lexicon"
						message="remove"
					/>
				</a>
			</div>

			<aui:script use="aui-base">
				A.one('#<portlet:namespace />couponCodeIconRemove').on('click', (event) => {
					event.preventDefault();

					submitForm(document.<portlet:namespace />fm);
				});
			</aui:script>
		</c:when>
		<c:otherwise>
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />

			<aui:input label="" name="couponCode" placeholder="enter-promo-code" type="text" />

			<aui:button type="submit" value="apply" />
		</c:otherwise>
	</c:choose>
</aui:form>