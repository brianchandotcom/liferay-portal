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
ShoppingCart cart = ShoppingUtil.getCart(renderRequest);

Map<ShoppingCartItem, Integer> items = cart.getItems();

ShoppingCoupon coupon = cart.getCoupon();

int altShipping = cart.getAltShipping();
String altShippingName = shoppingGroupServiceOverriddenConfiguration.getAlternativeShippingName(altShipping);

ShoppingOrder order = ShoppingOrderLocalServiceUtil.getLatestOrder(user.getUserId(), themeDisplay.getScopeGroupId());
%>

<portlet:actionURL name="/shopping/checkout" var="checkoutSecondURL">
	<portlet:param name="mvcActionCommand" value="/shopping/checkout" />
</portlet:actionURL>

<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="cart" />
</liferay-util:include>

<aui:form action="<%= checkoutSecondURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.SAVE %>" />
	<aui:input name="billingFirstName" type="hidden" value="<%= order.getBillingFirstName() %>" />
	<aui:input name="billingLastName" type="hidden" value="<%= order.getBillingLastName() %>" />
	<aui:input name="billingEmailAddress" type="hidden" value="<%= order.getBillingEmailAddress() %>" />
	<aui:input name="billingCompany" type="hidden" value="<%= order.getBillingCompany() %>" />
	<aui:input name="billingStreet" type="hidden" value="<%= order.getBillingStreet() %>" />
	<aui:input name="billingCity" type="hidden" value="<%= order.getBillingCity() %>" />
	<aui:input name="billingState" type="hidden" value="<%= order.getBillingState() %>" />
	<aui:input name="billingZip" type="hidden" value="<%= order.getBillingZip() %>" />
	<aui:input name="billingCountry" type="hidden" value="<%= order.getBillingCountry() %>" />
	<aui:input name="billingPhone" type="hidden" value="<%= order.getBillingPhone() %>" />
	<aui:input name="shipToBilling" type="hidden" value="<%= order.isShipToBilling() %>" />
	<aui:input name="shippingFirstName" type="hidden" value="<%= order.getShippingFirstName() %>" />
	<aui:input name="shippingLastName" type="hidden" value="<%= order.getShippingLastName() %>" />
	<aui:input name="shippingEmailAddress" type="hidden" value="<%= order.getShippingEmailAddress() %>" />
	<aui:input name="shippingCompany" type="hidden" value="<%= order.getShippingCompany() %>" />
	<aui:input name="shippingStreet" type="hidden" value="<%= order.getShippingStreet() %>" />
	<aui:input name="shippingCity" type="hidden" value="<%= order.getShippingCity() %>" />
	<aui:input name="shippingState" type="hidden" value="<%= order.getShippingState() %>" />
	<aui:input name="shippingZip" type="hidden" value="<%= order.getShippingZip() %>" />
	<aui:input name="shippingCountry" type="hidden" value="<%= order.getShippingCountry() %>" />
	<aui:input name="shippingPhone" type="hidden" value="<%= order.getShippingPhone() %>" />
	<aui:input name="ccName" type="hidden" value="<%= order.getCcName() %>" />
	<aui:input name="ccType" type="hidden" value="<%= order.getCcType() %>" />
	<aui:input name="ccNumber" type="hidden" value="<%= order.getCcNumber() %>" />
	<aui:input name="ccExpMonth" type="hidden" value="<%= order.getCcExpMonth() %>" />
	<aui:input name="ccExpYear" type="hidden" value="<%= order.getCcExpYear() %>" />
	<aui:input name="ccVerNumber" type="hidden" value="<%= order.getCcVerNumber() %>" />
	<aui:input name="comments" type="hidden" value="<%= order.getComments() %>" />

	<div class="row">
		<div class="col-md-6">
			<div class="well">
				<h4><liferay-ui:message key="billing-address" /></h4>

				<%@ include file="/checkout_second_billing_address.jspf" %>
			</div>
		</div>

		<div class="col-md-6">
			<div class="well">
				<h4><liferay-ui:message key="shipping-address" /></h4>

				<%@ include file="/checkout_second_shipping_address.jspf" %>
			</div>
		</div>
	</div>

	<c:if test="<%= !shoppingGroupServiceOverriddenConfiguration.usePayPal() %>">
		<div class="well">
			<h4><liferay-ui:message key="credit-card" /></h4>

			<table class="lfr-table">
				<tr>
					<th class="text-left">
						<liferay-ui:message key="full-name" />:
					</th>
					<td>
						<%= HtmlUtil.escape(order.getCcName()) %>
					</td>
				</tr>
				<tr>
					<th class="text-left">
						<liferay-ui:message key="type" />:
					</th>
					<td>
						<liferay-ui:message key='<%= "cc_" + HtmlUtil.escape(order.getCcType()) %>' />
					</td>
				</tr>
				<tr>
					<th class="text-left">
						<liferay-ui:message key="number" />:
					</th>
					<td>
						<%= CreditCard.hide(order.getCcNumber()) %>
					</td>
				</tr>
				<tr>
					<th class="text-left">
						<liferay-ui:message key="expiration-date" />:
					</th>
					<td>
						<%= CalendarUtil.getMonths(locale)[order.getCcExpMonth()] %>, <%= order.getCcExpYear() %>
					</td>
				</tr>

				<c:if test="<%= Validator.isNotNull(order.getCcVerNumber()) %>">
					<tr>
						<th class="text-left">
							<liferay-ui:message key="verification-number" />:
						</th>
						<td>
							<%= HtmlUtil.escape(order.getCcVerNumber()) %>
						</td>
					</tr>
				</c:if>
			</table>
		</div>
	</c:if>

	<c:if test="<%= Validator.isNotNull(order.getComments()) %>">
		<div class="well">
			<h4><liferay-ui:message key="comments" /></h4>

			<%= HtmlUtil.escape(order.getComments()) %>
		</div>
	</c:if>

	<%
	StringBundler itemIds = new StringBundler();
	%>

	<liferay-ui:search-container
		total="<%= items.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= ListUtil.fromMapKeys(items) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.shopping.model.ShoppingCartItem"
			modelVar="cartItem"
		>

			<%
			Integer count = items.get(cartItem);

			ShoppingItem item = cartItem.getItem();
			String[] fieldsArray = cartItem.getFieldsArray();

			ShoppingItemField[] itemFields = (ShoppingItemField[])ShoppingItemFieldLocalServiceUtil.getItemFields(item.getItemId()).toArray(new ShoppingItemField[0]);

			for (int j = 0; j < count.intValue(); j++) {
				itemIds.append(cartItem.getCartItemId());
				itemIds.append(",");
			}
			%>

			<liferay-ui:search-container-column-text
				name="sku"
				value="<%= HtmlUtil.escape(item.getSku()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="name"
			>
				<c:choose>
					<c:when test="<%= item.isFields() %>">
						<%= HtmlUtil.escape(item.getName()) %> (<%= HtmlUtil.escape(StringUtil.replace(StringUtil.merge(cartItem.getFieldsArray(), ", "), '=', ": ")) %>)
					</c:when>
					<c:otherwise>
						<%= HtmlUtil.escape(item.getName()) %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SHOPPING_ITEM_SHOW_AVAILABILITY) %>">
				<liferay-ui:search-container-column-text
					name="availability"
				>
					<c:choose>
						<c:when test="<%= ShoppingUtil.isInStock(item, itemFields, fieldsArray, count) %>">
							<div class="alert alert-success">
								<liferay-ui:message key="in-stock" />
							</div>
						</c:when>
						<c:otherwise>
							<div class="alert alert-danger">
								<liferay-ui:message key="out-of-stock" />
							</div>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>
			</c:if>

			<liferay-ui:search-container-column-text
				name="quantity"
				value="<%= count.toString() %>"
			/>

			<liferay-ui:search-container-column-text
				name="price"
				value="<%= currencyFormat.format(ShoppingUtil.calculateActualPrice(item, count.intValue()) / count.intValue()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="total"
				value="<%= currencyFormat.format(ShoppingUtil.calculateActualPrice(item, count.intValue())) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>

	<aui:input name="itemIds" type="hidden" value="<%= itemIds %>" />
	<aui:input name="couponCodes" type="hidden" value="<%= cart.getCouponCodes() %>" />

	<div class="well">
		<table class="lfr-table">
			<tr>
				<th class="text-left">
					<liferay-ui:message key="subtotal" />:
				</th>
				<td>
					<%= currencyFormat.format(ShoppingUtil.calculateActualSubtotal(items)) %>
				</td>
			</tr>
			<tr>
				<th class="text-left">
					<liferay-ui:message key="tax" />:
				</th>
				<td>
					<%= currencyFormat.format(ShoppingUtil.calculateTax(items, order.getBillingState())) %>
				</td>
			</tr>
			<tr>
				<th class="text-left">
					<liferay-ui:message key="shipping" /> <%= Validator.isNotNull(altShippingName) ? "(" + altShippingName + ")" : StringPool.BLANK %>:
				</th>
				<td>
					<%= currencyFormat.format(ShoppingUtil.calculateAlternativeShipping(items, altShipping)) %>
				</td>
			</tr>

			<%
			double insurance = ShoppingUtil.calculateInsurance(items);
			%>

			<c:if test="<%= cart.isInsure() && (insurance > 0) %>">
				<tr>
					<th class="text-left">
						<liferay-ui:message key="insurance" />:
					</th>
					<td>
						<%= currencyFormat.format(insurance) %>
					</td>
				</tr>
			</c:if>

			<c:if test="<%= coupon != null %>">
				<tr>
					<th class="text-left">
						<liferay-ui:message key="coupon-discount" />:
					</th>
					<td>
						<%= currencyFormat.format(ShoppingUtil.calculateCouponDiscount(items, order.getBillingState(), coupon)) %>

						<aui:a href="javascript:;" label='<%= "(" + coupon.getCouponId() + ")" %>' onClick='<%= renderResponse.getNamespace() + "viewCoupon();" %>' />
					</td>
				</tr>
			</c:if>

			<tr>
				<th class="text-left">
					<liferay-ui:message key="total" />:
				</th>
				<td>
					<%= currencyFormat.format(ShoppingUtil.calculateTotal(items, order.getBillingState(), coupon, altShipping, cart.isInsure())) %>
				</td>
			</tr>
		</table>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" value='<%= shoppingGroupServiceOverriddenConfiguration.usePayPal() ? "continue" : "finished" %>' />

		<portlet:actionURL name="/shopping/checkout" var="checkoutURL">
			<portlet:param name="mvcActionCommand" value="/shopping/checkout" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CHECKOUT %>" />
		</portlet:actionURL>

		<aui:button cssClass="btn-lg" href="<%= checkoutURL.toString() %>" value="back" />
	</aui:button-row>
</aui:form>

<c:if test="<%= coupon != null %>">
	<aui:script>
		function <portlet:namespace />viewCoupon() {
			Liferay.Util.openWindow(
				{
					dialog: {
						height: 200,
						width: 280
					},
					id: '<portlet:namespace />viewCoupon',
					refreshWindow: window,
					title: '<%= UnicodeLanguageUtil.get(request, "coupon") %>',
					uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/shopping/view_coupon" /><portlet:param name="couponId" value="<%= String.valueOf(coupon.getCouponId()) %>" /></portlet:renderURL>'
				}
			);
		}
	</aui:script>
</c:if>