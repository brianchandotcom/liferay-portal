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
String redirect = ParamUtil.getString(request, "redirect");

ShoppingCoupon coupon = (ShoppingCoupon)request.getAttribute(WebKeys.SHOPPING_COUPON);

long couponId = BeanParamUtil.getLong(coupon, request, "couponId");

String code = BeanParamUtil.getString(coupon, request, "code");

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if (coupon != null) {
	if (coupon.getEndDate() != null) {
		neverExpire = false;
	}
}

double minOrder = BeanParamUtil.getDouble(coupon, request, "minOrder");
double discount = BeanParamUtil.getDouble(coupon, request, "discount");
String discountType = BeanParamUtil.getString(coupon, request, "discountType");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(((coupon == null) ? LanguageUtil.get(request, "coupon") : coupon.getName()));
%>

<portlet:actionURL name="/shopping/edit_coupon" var="editCouponURL">
	<portlet:param name="mvcActionCommand" value="/shopping/edit_coupon" />
</portlet:actionURL>

<aui:form action="<%= editCouponURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (coupon == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="couponId" type="hidden" value="<%= couponId %>" />

	<liferay-ui:error exception="<%= CouponCodeException.class %>" message="please-enter-a-valid-code" />
	<liferay-ui:error exception="<%= CouponDateException.class %>" message="please-enter-a-start-date-that-comes-before-the-expiration-date" />
	<liferay-ui:error exception="<%= CouponDescriptionException.class %>" message="please-enter-a-valid-description" />
	<liferay-ui:error exception="<%= CouponDiscountException.class %>" message="please-enter-a-valid-number" />
	<liferay-ui:error exception="<%= CouponEndDateException.class %>" message="please-enter-a-valid-expiration-date" />
	<liferay-ui:error exception="<%= CouponMinimumOrderException.class %>" message="please-enter-a-valid-number" />
	<liferay-ui:error exception="<%= CouponNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= CouponStartDateException.class %>" message="please-enter-a-valid-start-date" />
	<liferay-ui:error exception="<%= DuplicateCouponCodeException.class %>" message="please-enter-a-unique-code" />

	<aui:model-context bean="<%= coupon %>" model="<%= ShoppingCoupon.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= (windowState.equals(WindowState.MAXIMIZED) && (coupon != null)) %>" name="name" />

			<aui:input name="description" />

			<c:choose>
				<c:when test="<%= coupon == null %>">
					<aui:input label="autogenerate-code" name="autoCode" type="checkbox" />

					<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="code" />
				</c:when>
				<c:otherwise>
					<aui:input name="code" type="resource" value="<%= code %>" />
				</c:otherwise>
			</c:choose>

			<aui:input name="startDate" />

			<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" label="expiration-date" name="endDate" />

			<aui:input name="active" value="<%= true %>" />
		</aui:fieldset>

		<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="discount">
			<div class="alert alert-info">
				<liferay-ui:message arguments="<%= currencyFormat.format(0) %>" key="coupons-can-be-set-to-only-apply-to-orders-above-a-minimum-amount" translateArguments="<%= false %>" />

				<br /><br />

				<liferay-ui:message key="set-the-discount-amount-and-the-discount-type" />

				<br /><br />

				<liferay-ui:message key="if-the-discount-type-is-free-shipping,-shipping-charges-are-subtracted-from-the-order" />
			</div>

			<aui:input ignoreRequestValue="<%= true %>" label="minimum-order" name="minOrder" size="4" type="text" value="<%= doubleFormat.format(minOrder) %>" />

			<aui:input ignoreRequestValue="<%= true %>" name="discount" size="4" type="text" value="<%= doubleFormat.format(discount) %>" />

			<aui:select name="discountType">

				<%
				for (int i = 0; i < ShoppingCouponConstants.DISCOUNT_TYPES.length; i++) {
				%>

					<aui:option label="<%= ShoppingCouponConstants.DISCOUNT_TYPES[i] %>" selected="<%= discountType.equals(ShoppingCouponConstants.DISCOUNT_TYPES[i]) %>" />

				<%
				}
				%>

			</aui:select>
		</aui:fieldset>

		<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="limits">
			<liferay-ui:error exception="<%= CouponLimitCategoriesException.class %>">

				<%
				List categoryIds = (List)errorException;
				%>

				<liferay-ui:message key="the-following-are-invalid-category-ids" /> <%= HtmlUtil.escape(StringUtil.merge((String[])categoryIds.toArray(new String[0]))) %>
			</liferay-ui:error>

			<liferay-ui:error exception="<%= CouponLimitSKUsException.class %>">

				<%
				List skus = (List)errorException;
				%>

				<liferay-ui:message key="the-following-are-invalid-item-skus" /> <%= HtmlUtil.escape(StringUtil.merge((String[])skus.toArray(new String[0]))) %>
			</liferay-ui:error>

			<aui:input label='<%= LanguageUtil.get(request, "this-coupon-only-applies-to-items-that-are-children-of-this-comma-delimited-list-of-categories") + StringPool.SPACE + LanguageUtil.get(request, "leave-this-blank-if-the-coupon-does-not-check-for-the-parent-categories-of-an-item") %>' name="limitCategories" />

			<aui:input label='<%= LanguageUtil.get(request, "this-coupon-only-applies-to-items-with-a-sku-that-corresponds-to-this-comma-delimited-list-of-item-skus") + StringPool.SPACE + LanguageUtil.get(request, "leave-this-blank-if-the-coupon-does-not-check-for-the-item-sku") %>' name="limitSkus" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.disableToggleBoxes('<portlet:namespace />autoCode', '<portlet:namespace />code', true);
</aui:script>