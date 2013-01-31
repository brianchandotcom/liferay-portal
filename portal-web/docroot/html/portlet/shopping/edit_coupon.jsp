<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/shopping/init.jsp" %>

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

String limitCategories = BeanParamUtil.getString(coupon, request, "limitCategories");
String limitSkus = BeanParamUtil.getString(coupon, request, "limitSkus");
double minOrder = BeanParamUtil.getDouble(coupon, request, "minOrder");
double discount = BeanParamUtil.getDouble(coupon, request, "discount");
String discountType = BeanParamUtil.getString(coupon, request, "discountType");
%>

<portlet:actionURL var="editCouponURL">
	<portlet:param name="struts_action" value="/shopping/edit_coupon" />
</portlet:actionURL>

<aui:form action="<%= editCouponURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCoupon();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="couponId" type="hidden" value="<%= couponId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="coupon"
	/>

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

	<aui:fieldset>
		<c:choose>
			<c:when test="<%= coupon == null %>">
				<aui:input name="code" />

				<aui:input label="autogenerate-code" name="autoCode" type="checkbox" />
			</c:when>
			<c:otherwise>
				<aui:field-wrapper label="code">
					<%= HtmlUtil.escape(code) %>
				</aui:field-wrapper>
			</c:otherwise>
		</c:choose>

		<aui:input name="name" />

		<aui:input name="description" />

		<aui:input name="startDate" />

		<aui:input checkBox="true" disabled="<%= neverExpire %>" label="expiration-date" name="endDate" />

		<aui:input name="active" value="<%= Boolean.TRUE %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>

	<liferay-ui:panel-container extended="<%= true %>" id="shoppingEditCouponPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="shoppingEditCouponDiscountPanel" persistState="<%= true %>" title="discount">
			<liferay-ui:message arguments="<%= currencyFormat.format(0) %>" key="coupons-can-be-set-to-only-apply-to-orders-above-a-minimum-amount" translateArguments="<%= false %>" />

			<br /><br />

			<liferay-ui:message key="set-the-discount-amount-and-the-discount-type" />

			<br /><br />

			<liferay-ui:message key="if-the-discount-type-is-free-shipping,-then-shipping-charges-are-subtracted-from-the-order" />

			<aui:fieldset>
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
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="shoppingEditCouponLimitsPanel" persistState="<%= true %>" title="limits">
			<liferay-ui:error exception="<%= CouponLimitCategoriesException.class %>">

				<%
				List categoryIds = (List)errorException;
				%>

				<liferay-ui:message key="the-following-are-invalid-category-ids" /> <%= StringUtil.merge((String[])categoryIds.toArray(new String[0])) %>
			</liferay-ui:error>

			<liferay-ui:error exception="<%= CouponLimitSKUsException.class %>">

				<%
				List skus = (List)errorException;
				%>

				<liferay-ui:message key="the-following-are-invalid-item-skus" /> <%= StringUtil.merge((String[])skus.toArray(new String[0])) %>
			</liferay-ui:error>

			<aui:fieldset>
				<aui:input label='<%= LanguageUtil.get(pageContext, "this-coupon-only-applies-to-items-that-are-children-of-this-comma-delimited-list-of-categories") + StringPool.SPACE + LanguageUtil.get(pageContext, "leave-this-blank-if-the-coupon-does-not-check-for-the-parent-categories-of-an-item") %>' name="limitCategories" />

				<aui:input label='<%= LanguageUtil.get(pageContext, "this-coupon-only-applies-to-items-with-a-sku-that-corresponds-to-this-comma-delimited-list-of-item-skus") + StringPool.SPACE + LanguageUtil.get(pageContext, "leave-this-blank-if-the-coupon-does-not-check-for-the-item-sku") %>' name="limitSkus" />
			</aui:fieldset>
		</liferay-ui:panel>
	</liferay-ui:panel-container>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCoupon() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (coupon == null) ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.Util.disableToggleBoxes('<portlet:namespace />autoCodeCheckbox', '<portlet:namespace />code', true);

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= (coupon == null) ? "code" : "name" %>);
	</c:if>
</aui:script>