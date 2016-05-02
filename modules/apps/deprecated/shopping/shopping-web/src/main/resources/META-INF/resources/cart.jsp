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

boolean minQuantityMultiple = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SHOPPING_CART_MIN_QTY_MULTIPLE);
%>

<aui:script position="inline">
	var <portlet:namespace />itemsInStock = true;

	function <portlet:namespace />checkout() {
		var form = AUI.$(document.<portlet:namespace />fm);

		if (<portlet:namespace />itemsInStock || confirm('<%= UnicodeLanguageUtil.get(request, "your-cart-has-items-that-are-out-of-stock") %>')) {
			form.fm('<%= Constants.CMD %>').val('<%= Constants.CHECKOUT %>');
			form.fm('redirect').val('<portlet:actionURL name="/shopping/checkout"><portlet:param name="mvcActionCommand" value="/shopping/checkout" /><portlet:param name="cmd" value="<%= Constants.CHECKOUT %>" /></portlet:actionURL>');
			<portlet:namespace />updateCart();
		}
	}

	function <portlet:namespace />updateCart() {
		var count = 0;
		var form = AUI.$(document.<portlet:namespace />fm);
		var invalidSKUs = '';
		var itemIds = '';
		var subtotal = 0;

		<%
		int itemsCount = 0;

		for (ShoppingCartItem cartItem : items.keySet()) {
			ShoppingItem item = cartItem.getItem();

			ShoppingItemPrice[] itemPrices = (ShoppingItemPrice[])ShoppingItemPriceLocalServiceUtil.getItemPrices(item.getItemId()).toArray(new ShoppingItemPrice[0]);

			int maxQuantity = _getMaxQuantity(itemPrices);
		%>

			count = form.fm('item_<%= item.getItemId() %>_<%= itemsCount %>_count').val();

			subtotal += <%= ShoppingUtil.calculateActualPrice(item, 1) %> * count;

			if ((count == '') || isNaN(count) || (count < 0) || ((count > <%= maxQuantity %>) && (<%= maxQuantity %> > 0))) {
				if (invalidSKUs != '') {
					invalidSKUs += ', ';
				}

				invalidSKUs += '<%= HtmlUtil.escapeJS(item.getSku()) %>';
			}

			for (var i = 0; i < count; i++) {
				itemIds += '<%= HtmlUtil.escapeJS(cartItem.getCartItemId()) %>,';
			}

			count = 0;

		<%
			itemsCount++;
		}
		%>

		if (form.fm('<%= Constants.CMD %>').val() == '<%= Constants.CHECKOUT %>') {
			if (subtotal < <%= shoppingGroupServiceOverriddenConfiguration.getMinOrder() %>) {
				form.fm('<%= Constants.CMD %>').val('<%= Constants.UPDATE %>');
				form.fm('redirect').val('<%= currentURL %>');

				alert('<%= UnicodeLanguageUtil.format(request, "your-order-cannot-be-processed-because-it-falls-below-the-minimum-required-amount-of-x", currencyFormat.format(shoppingGroupServiceOverriddenConfiguration.getMinOrder()), false) %>');

				return;
			}
		}

		form.fm('itemIds').val(itemIds);

		if (invalidSKUs == '') {
			submitForm(form);
		}
		else {
			alert('<%= UnicodeLanguageUtil.get(request, "please-enter-valid-quantities-for-the-following-skus") %>' + invalidSKUs);
		}
	}
</aui:script>

<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="/shopping/cart" var="cartURL">
	<portlet:param name="mvcActionCommand" value="/shopping/cart" />
</portlet:actionURL>

<aui:form action="<%= cartURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "updateCart();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="itemIds" type="hidden" />

	<liferay-ui:error exception="<%= CartMinQuantityException.class %>">

		<%
		CartMinQuantityException cmqe = (CartMinQuantityException)errorException;

		long[] badItemIds = StringUtil.split(cmqe.getMessage(), 0L);
		%>

		<liferay-ui:message key="all-quantities-must-be-greater-than-the-minimum-quantity-of-the-item" /><br />

		<c:if test="<%= minQuantityMultiple %>">
			<br />

			<liferay-ui:message key="all-quantities-must-be-a-multiple-of-the-minimum-quantity-of-the-item" /><br />
		</c:if>

		<br />

		<liferay-ui:message key="please-reenter-your-quantity-for-the-items-with-the-following-skus" />

		<%
		for (int i = 0; i < badItemIds.length; i++) {
			ShoppingItem item = ShoppingItemServiceUtil.getItem(badItemIds[i]);
		%>

			<strong><%= HtmlUtil.escape(item.getSku()) %></strong><c:if test="<%= i + 1 < badItemIds.length %>">,</c:if>

		<%
		}
		%>

	</liferay-ui:error>

	<liferay-ui:error exception="<%= CouponActiveException.class %>" message="the-specified-coupon-is-not-active" />
	<liferay-ui:error exception="<%= CouponEndDateException.class %>" message="the-specified-coupon-is-no-longer-available" />
	<liferay-ui:error exception="<%= CouponStartDateException.class %>" message="the-specified-coupon-is-no-yet-available" />
	<liferay-ui:error exception="<%= NoSuchCouponException.class %>" message="please-enter-a-valid-coupon-code" />

	<liferay-ui:search-container
		emptyResultsMessage="your-cart-is-empty"
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

			item = item.toEscapedModel();

			String[] fieldsArray = cartItem.getFieldsArray();

			ShoppingItemField[] itemFields = (ShoppingItemField[])ShoppingItemFieldLocalServiceUtil.getItemFields(item.getItemId()).toArray(new ShoppingItemField[0]);
			ShoppingItemPrice[] itemPrices = (ShoppingItemPrice[])ShoppingItemPriceLocalServiceUtil.getItemPrices(item.getItemId()).toArray(new ShoppingItemPrice[0]);

			String fieldName = "item_" + item.getItemId() + "_" + index + "_count";

			if (!SessionErrors.isEmpty(renderRequest)) {
				count = Integer.valueOf(ParamUtil.getInteger(request, fieldName));
			}
			%>

			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/shopping/view_item" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="itemId" value="<%= String.valueOf(item.getItemId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				name="cart"
			>
				<c:choose>
					<c:when test="<%= item.isSmallImage() %>">
						<img alt="<%= HtmlUtil.escapeAttribute(item.getSku()) %>" src="<%= item.getShoppingItemImageURL(themeDisplay) %>" />
					</c:when>
					<c:otherwise>
						<%= item.getSku() %>
					</c:otherwise>
				</c:choose>

				<h5><a href="<%= rowURL.toString() %>"><%= item.getName() %></a></h5>

				<h6 class="default-text"><%= item.getDescription() %></h6>

				<c:if test="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SHOPPING_ITEM_SHOW_AVAILABILITY) %>">
					<liferay-ui:message key="availability" />:

					<c:choose>
						<c:when test="<%= ShoppingUtil.isInStock(item, itemFields, fieldsArray, count) %>">
							<div class="alert alert-success"><liferay-ui:message key="in-stock" /></div>
						</c:when>
						<c:otherwise>
							<div class="alert alert-danger"><liferay-ui:message key="out-of-stock" /></div>

							<aui:script position="inline">
								<portlet:namespace />itemsInStock = false;
							</aui:script>
						</c:otherwise>
					</c:choose>
				</c:if>

				<span>

					<%
					for (String field : fieldsArray) {
						String[] values = StringUtil.split(field, '=');
					%>

						<%= HtmlUtil.escape(values[0]) %>: <%= HtmlUtil.escape(values[1]) %>

					<%
					}
					%>

				</span>

				<span>

					<%
					for (ShoppingItemPrice itemPrice : itemPrices) {
						if (itemPrice.getStatus() == ShoppingItemPriceConstants.STATUS_INACTIVE) {
							continue;
						}
					%>

						<c:choose>
							<c:when test="<%= (itemPrice.getMinQuantity() == 0) && (itemPrice.getMaxQuantity() == 0) %>">
								<liferay-ui:message key="price" />:
							</c:when>
							<c:when test="<%= itemPrice.getMaxQuantity() != 0 %>">
								<liferay-ui:message arguments='<%= new Object[] {"<strong>" + Integer.valueOf(itemPrice.getMinQuantity()) + "</strong>", "<strong>" + Integer.valueOf(itemPrice.getMaxQuantity()) + "</strong>"} %>' key="price-for-x-to-x-items" translateArguments="<%= false %>" />
							</c:when>
							<c:when test="<%= itemPrice.getMaxQuantity() == 0 %>">
								<liferay-ui:message arguments='<%= "<strong>" + Integer.valueOf(itemPrice.getMinQuantity()) + "</strong>" %>' key="price-for-x-items-and-above" translateArguments="<%= false %>" />
							</c:when>
						</c:choose>

						<c:choose>
							<c:when test="<%= itemPrice.getDiscount() <= 0 %>">
								<%= currencyFormat.format(itemPrice.getPrice()) %>
							</c:when>
							<c:otherwise>
								<strike><%= currencyFormat.format(itemPrice.getPrice()) %></strike>

								<div class="alert alert-success">
									<%= currencyFormat.format(ShoppingUtil.calculateActualPrice(itemPrice)) %>
								</div>

								<liferay-ui:message key="you-save" />:

								<div class="alert alert-danger">
									<%= currencyFormat.format(ShoppingUtil.calculateDiscountPrice(itemPrice)) %>(<%= percentFormat.format(itemPrice.getDiscount()) %>)
								</div>
							</c:otherwise>
						</c:choose>

					<%
					}
					%>

				</span>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="quantity"
			>

				<%
				int maxQuantity = _getMaxQuantity(itemPrices);
				%>

				<c:choose>
					<c:when test="<%= minQuantityMultiple && (item.getMinQuantity() > 1) && (maxQuantity != 0) %>">
						<aui:select label="" name="<%= fieldName %>">
							<aui:option label="0" />

							<%
							for (int j = 1; j <= (maxQuantity / item.getMinQuantity()); j++) {
								int curQuantity = item.getMinQuantity() * j;
							%>

								<aui:option label="<%= curQuantity %>" selected="<%= curQuantity == count.intValue() %>" />

							<%
							}
							%>

						</aui:select>
					</c:when>
					<c:otherwise>
						<aui:input label="" name="<%= fieldName %>" size="2" type="text" value="<%= count %>" />
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="price"
				value="<%= currencyFormat.format(ShoppingUtil.calculateActualPrice(item, count.intValue()) / count.intValue()) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />
	</liferay-ui:search-container>

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>

			<%
			double subtotal = ShoppingUtil.calculateSubtotal(items);
			double actualSubtotal = ShoppingUtil.calculateActualSubtotal(items);
			double discountSubtotal = ShoppingUtil.calculateDiscountSubtotal(items);
			%>

			<c:choose>
				<c:when test="<%= subtotal == actualSubtotal %>">
					<aui:input name="subtotal" type="resource" value="<%= currencyFormat.format(subtotal) %>" />
				</c:when>
				<c:otherwise>
					<aui:field-wrapper label="subtotal">
						<div class="alert alert-success">
							<strike><%= currencyFormat.format(subtotal) %></strike> <%= currencyFormat.format(actualSubtotal) %>
						</div>
					</aui:field-wrapper>
				</c:otherwise>
			</c:choose>

			<c:if test="<%= subtotal != actualSubtotal %>">
				<aui:field-wrapper label="you-save">
					<div class="alert alert-danger">
						<%= currencyFormat.format(discountSubtotal) %> (<%= percentFormat.format(ShoppingUtil.calculateDiscountPercent(items)) %>)
					</div>
				</aui:field-wrapper>
			</c:if>

			<c:choose>
				<c:when test="<%= !shoppingGroupServiceOverriddenConfiguration.useAlternativeShipping() %>">
					<aui:input name="shipping" type="resource" value="<%= currencyFormat.format(ShoppingUtil.calculateShipping(items)) %>" />
				</c:when>
				<c:otherwise>
					<aui:select label="shipping" name="alternativeShipping">

						<%
						String[][] alternativeShipping = shoppingGroupServiceOverriddenConfiguration.getAlternativeShipping();

						for (int i = 0; i < 10; i++) {
							String altShippingName = alternativeShipping[0][i];
							String altShippingDelta = alternativeShipping[1][i];

							if (Validator.isNotNull(altShippingName) && Validator.isNotNull(altShippingDelta)) {
						%>

								<aui:option label='<%= LanguageUtil.get(request, altShippingName) + "(" + currencyFormat.format(ShoppingUtil.calculateAlternativeShipping(items, i)) + ")" %>' selected="<%= i == cart.getAltShipping() %>" value="<%= i %>" />

						<%
							}
						}
						%>

					</aui:select>
				</c:otherwise>
			</c:choose>

			<%
			double insurance = ShoppingUtil.calculateInsurance(items);
			%>

			<c:if test="<%= insurance > 0 %>">
				<aui:select label="insurance" name="insure">
					<aui:option label="none" selected="<%= !cart.isInsure() %>" value="0" />
					<aui:option label="<%= currencyFormat.format(insurance) %>" selected="<%= cart.isInsure() %>" value="1" />
				</aui:select>
			</c:if>

			<aui:input label="coupon-code" name="couponCodes" size="30" style="text-transform: uppercase;" type="text" value="<%= cart.getCouponCodes() %>" />

			<c:if test="<%= coupon != null %>">
				<aui:a href="javascript:;" label='<%= "(" + LanguageUtil.get(request, "description") + ")" %>' onClick='<%= renderResponse.getNamespace() + "viewCoupon();" %>' style="font-size: xx-small;" />

				<aui:field-wrapper label="coupon-discount">
					<div class="alert alert-danger">
						<%= currencyFormat.format(ShoppingUtil.calculateCouponDiscount(items, coupon)) %>
					</div>
				</aui:field-wrapper>
			</c:if>
		</aui:fieldset>
	</aui:fieldset-group>

	<%
	String[] ccTypes = shoppingGroupServiceOverriddenConfiguration.getCcTypes();

	if (shoppingGroupServiceOverriddenConfiguration.usePayPal()) {
	%>

		<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="paypal" />" src="<%= themeDisplay.getPathThemeImages() %>/shopping/cc_paypal.png" />

		<br /><br />

	<%
	}
	else if (!shoppingGroupServiceOverriddenConfiguration.usePayPal() && (ccTypes.length > 0)) {
		for (int i = 0; i < ccTypes.length; i++) {
	%>

			<img alt="<%= HtmlUtil.escapeAttribute(ccTypes[i]) %>" src="<%= themeDisplay.getPathThemeImages() %>/shopping/cc_<%= HtmlUtil.escapeAttribute(ccTypes[i]) %>.png" />

		<%
		}
		%>

		<br /><br />

	<%
	}
	%>

	<aui:button-row>
		<aui:button cssClass="btn-lg" onClick='<%= renderResponse.getNamespace() + "updateCart();" %>' value="update-cart" />

		<aui:button cssClass="btn-lg" disabled="<%= items.isEmpty() %>" onClick='<%= renderResponse.getNamespace() + "checkout();" %>' type="submit" value="checkout" />
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

<%!
private static int _getMaxQuantity(ShoppingItemPrice[] itemPrices) {
	int maxQuantity = 0;

	for (ShoppingItemPrice itemPrice : itemPrices) {
		if (itemPrice.getMaxQuantity() == 0) {
			return 0;
		}

		if (maxQuantity < itemPrice.getMaxQuantity()) {
			maxQuantity = itemPrice.getMaxQuantity();
		}
	}

	return maxQuantity;
}
%>