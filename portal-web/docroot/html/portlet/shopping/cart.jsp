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

ShoppingCart cart = ShoppingUtil.getCart(renderRequest);

Map<ShoppingCartItem, Integer> items = cart.getItems();

ShoppingCoupon coupon = cart.getCoupon();

boolean minQuantityMultiple = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SHOPPING_CART_MIN_QTY_MULTIPLE);
%>

<aui:script position="inline">
	var itemsInStock = true;

	function <portlet:namespace />checkout() {
		if (<%= ShoppingUtil.meetsMinOrder(shoppingPrefs, items) ? "true" : "false" %>) {
			if (!itemsInStock) {
				if (confirm("<%= UnicodeLanguageUtil.get(pageContext, "your-cart-has-items-that-are-out-of-stock") %>")) {
					document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.CHECKOUT %>";
					document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:actionURL><portlet:param name="struts_action" value="/shopping/checkout" /></portlet:actionURL>";
					<portlet:namespace />updateCart();
				}
			}
			else {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.CHECKOUT %>";
				document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:actionURL><portlet:param name="struts_action" value="/shopping/checkout" /></portlet:actionURL>";
				<portlet:namespace />updateCart();
			}
		}
		else {
			alert("<%= UnicodeLanguageUtil.format(pageContext, "your-order-cannot-be-processed-because-it-falls-below-the-minimum-required-amount-of-x", currencyFormat.format(shoppingPrefs.getMinOrder()), false) %>");
		}
	}

	function <portlet:namespace />emptyCart() {
		document.<portlet:namespace />fm.<portlet:namespace />itemIds.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />couponCodes.value = "";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateCart() {
		var itemIds = "";
		var count = 0;

		<%
		int itemsCount= 0;

		for (ShoppingCartItem cartItem : items.keySet()) {
			ShoppingItem item = cartItem.getItem();
		%>

			count = document.<portlet:namespace />fm.<portlet:namespace />item_<%= item.getItemId() %>_<%= itemsCount %>_count.value;

			for (var i = 0; i < count; i++) {
				itemIds += "<%= cartItem.getCartItemId() %>,";
			}

			count = 0;

		<%
			itemsCount++;
		}
		%>

		document.<portlet:namespace />fm.<portlet:namespace />itemIds.value = itemIds;
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<portlet:actionURL var="cartURL">
	<portlet:param name="struts_action" value="/shopping/cart" />
</portlet:actionURL>

<aui:form action="<%= cartURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCart();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="itemIds" type="hidden" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="cart"
	/>

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

			<strong><%= item.getSku() %></strong><c:if test="<%= i + 1 < badItemIds.length %>">,</c:if>

		<%
		}
		%>

	</liferay-ui:error>

	<liferay-ui:error exception="<%= CouponActiveException.class %>" message="the-specified-coupon-is-not-active" />
	<liferay-ui:error exception="<%= CouponEndDateException.class %>" message="the-specified-coupon-is-no-longer-available" />
	<liferay-ui:error exception="<%= CouponStartDateException.class %>" message="the-specified-coupon-is-no-yet-available" />
	<liferay-ui:error exception="<%= NoSuchCouponException.class %>" message="please-enter-a-valid-coupon-code" />

	<%
	SearchContainer searchContainer = new SearchContainer();

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("sku");
	headerNames.add("description");
	headerNames.add("quantity");
	headerNames.add("price");

	searchContainer.setHeaderNames(headerNames);
	searchContainer.setEmptyResultsMessage("your-cart-is-empty");
	searchContainer.setHover(false);

	int total = items.size();

	searchContainer.setTotal(total);

	List resultRows = searchContainer.getResultRows();

	int itemsCount = 0;

	for (Map.Entry<ShoppingCartItem, Integer> entry : items.entrySet()) {
		ShoppingCartItem cartItem = entry.getKey();
		Integer count = entry.getValue();

		ShoppingItem item = cartItem.getItem();

		item = item.toEscapedModel();

		String[] fieldsArray = cartItem.getFieldsArray();

		ShoppingItemField[] itemFields = (ShoppingItemField[])ShoppingItemFieldLocalServiceUtil.getItemFields(item.getItemId()).toArray(new ShoppingItemField[0]);
		ShoppingItemPrice[] itemPrices = (ShoppingItemPrice[])ShoppingItemPriceLocalServiceUtil.getItemPrices(item.getItemId()).toArray(new ShoppingItemPrice[0]);

		if (!SessionErrors.isEmpty(renderRequest)) {
			count = new Integer(ParamUtil.getInteger(request, "item_" + item.getItemId() + "_" + itemsCount + "_count"));
		}

		ResultRow row = new ResultRow(item, item.getItemId(), itemsCount);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/shopping/view_item");
		rowURL.setParameter("itemId", String.valueOf(item.getItemId()));

		// SKU and small image

		StringBundler sb = new StringBundler();

		if (item.isSmallImage()) {
			sb.append("<br />");
			sb.append("<img alt=\"");
			sb.append(item.getSku());
			sb.append("\" border=\"0\" src=\"");

			if (Validator.isNotNull(item.getSmallImageURL())) {
				sb.append(item.getSmallImageURL());
			}
			else {
				sb.append(themeDisplay.getPathImage());
				sb.append("/shopping/item?img_id=");
				sb.append(item.getSmallImageId());
				sb.append("&t=");
				sb.append(WebServerServletTokenUtil.getToken(item.getSmallImageId()));
			}

			sb.append("\">");
		}
		else {
			sb.append(item.getSku());
		}

		row.addText(sb.toString(), rowURL);

		// Description

		sb = new StringBundler();

		sb.append(item.getName());

		if (Validator.isNotNull(item.getDescription())) {
			sb.append("<br />");
			sb.append(item.getDescription());
		}

		/*Properties props = new OrderedProperties();

		PropertiesUtil.load(props, item.getProperties());

		Enumeration enu = props.propertyNames();

		while (enu.hasMoreElements()) {
			String propsKey = (String)enu.nextElement();
			String propsValue = props.getProperty(propsKey, StringPool.BLANK);

			sb.append("<br />");
			sb.append(propsKey);
			sb.append(": ");
			sb.append(propsValue);
		}*/

		if (PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SHOPPING_ITEM_SHOW_AVAILABILITY)) {
			sb.append("<br /><br />");

			if (ShoppingUtil.isInStock(item, itemFields, fieldsArray, count)) {
				sb.append(LanguageUtil.get(pageContext, "availability"));
				sb.append(": ");
				sb.append("<div class=\"alert alert-success\">");
				sb.append(LanguageUtil.get(pageContext, "in-stock"));
				sb.append("</div>");
			}
			else {
				sb.append(LanguageUtil.get(pageContext, "availability"));
				sb.append(": ");
				sb.append("<div class=\"alert alert-error\">");
				sb.append(LanguageUtil.get(pageContext, "out-of-stock"));
				sb.append("</div>");

				sb.append("<script type=\"text/javascript\">");
				sb.append("itemsInStock = false;");
				sb.append("</script>");
			}
		}

		if (fieldsArray.length > 0) {
			sb.append("<br />");
		}

		for (int j = 0; j < fieldsArray.length; j++) {
			int pos = fieldsArray[j].indexOf("=");

			String fieldName = fieldsArray[j].substring(0, pos);
			String fieldValue = fieldsArray[j].substring(pos + 1);

			sb.append("<br />");
			sb.append(fieldName);
			sb.append(": ");
			sb.append(fieldValue);
		}

		if (itemPrices.length > 0) {
			sb.append("<br />");
		}

		for (int j = 0; j < itemPrices.length; j++) {
			ShoppingItemPrice itemPrice = itemPrices[j];

			if (itemPrice.getStatus() == ShoppingItemPriceConstants.STATUS_INACTIVE) {
				continue;
			}

			sb.append("<br />");

			if ((itemPrice.getMinQuantity() == 0) && (itemPrice.getMaxQuantity() == 0)) {
				sb.append(LanguageUtil.get(pageContext, "price"));
				sb.append(": ");
			}
			else if (itemPrice.getMaxQuantity() != 0) {
				sb.append(LanguageUtil.format(pageContext, "price-for-x-to-x-items", new Object[] {"<strong>" + new Integer(itemPrice.getMinQuantity()) + "</strong>", "<strong>" + new Integer(itemPrice.getMaxQuantity()) + "</strong>"}, false));
			}
			else if (itemPrice.getMaxQuantity() == 0) {
				sb.append(LanguageUtil.format(pageContext, "price-for-x-items-and-above", "<strong>" + new Integer(itemPrice.getMinQuantity()) + "</strong>", false));
			}

			if (itemPrice.getDiscount() <= 0) {
				sb.append(currencyFormat.format(itemPrice.getPrice()));
			}
			else {
				sb.append("<strike>");
				sb.append(currencyFormat.format(itemPrice.getPrice()));
				sb.append("</strike> ");
				sb.append("<div class=\"alert alert-success\">");
				sb.append(currencyFormat.format(ShoppingUtil.calculateActualPrice(itemPrice)));
				sb.append("</div> / ");
				sb.append(LanguageUtil.get(pageContext, "you-save"));
				sb.append(": ");
				sb.append("<div class=\"alert alert-error\">");
				sb.append(currencyFormat.format(ShoppingUtil.calculateDiscountPrice(itemPrice)));
				sb.append(" (");
				sb.append(percentFormat.format(itemPrice.getDiscount()));
				sb.append(StringPool.CLOSE_PARENTHESIS);
				sb.append("</div>");
			}
		}

		row.addText(sb.toString(), rowURL);

		// Quantity

		sb.setIndex(0);

		if (minQuantityMultiple && (item.getMinQuantity() > 0)) {
			sb.append("<select name=\"");
			sb.append(renderResponse.getNamespace());
			sb.append("item_");
			sb.append(item.getItemId());
			sb.append("_");
			sb.append(itemsCount);
			sb.append("_count\">");

			sb.append("<option value=\"0\">0</option>");

			for (int j = 1; j <= 10; j++) {
				int curQuantity = item.getMinQuantity() * j;

				sb.append("<option ");

				if (curQuantity == count.intValue()) {
					sb.append("selected ");
				}

				sb.append("value=\"");
				sb.append(curQuantity);
				sb.append("\">");
				sb.append(curQuantity);
				sb.append("</option>");
			}

			sb.append("</select>");
		}
		else {
			sb.append("<input name=\"");
			sb.append(renderResponse.getNamespace());
			sb.append("item_");
			sb.append(item.getItemId());
			sb.append("_");
			sb.append(itemsCount);
			sb.append("_count\" size=\"2\" type=\"text\" value=\"");
			sb.append(count);
			sb.append("\">");
		}

		row.addText(sb.toString());

		// Price

		row.addText(currencyFormat.format(ShoppingUtil.calculateActualPrice(item, count.intValue()) / count.intValue()), rowURL);

		// Add result row

		resultRows.add(row);

		itemsCount++;
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

	<aui:fieldset>

		<%
		double subtotal = ShoppingUtil.calculateSubtotal(items);
		double actualSubtotal = ShoppingUtil.calculateActualSubtotal(items);
		double discountSubtotal = ShoppingUtil.calculateDiscountSubtotal(items);
		%>

		<aui:field-wrapper label="subtotal">
			<c:if test="<%= subtotal == actualSubtotal %>">
				<%= currencyFormat.format(subtotal) %>
			</c:if>

			<c:if test="<%= subtotal != actualSubtotal %>">
				<strike><%= currencyFormat.format(subtotal) %></strike><aui:spacer /><div class="alert alert-success"><%= currencyFormat.format(actualSubtotal) %></div>
			</c:if>
		</aui:field-wrapper>

		<c:if test="<%= subtotal != actualSubtotal %>">
			<aui:field-wrapper label="you-save">
				<div class="alert alert-error">
					<%= currencyFormat.format(discountSubtotal) %> (<%= percentFormat.format(ShoppingUtil.calculateDiscountPercent(items)) %>)
				</div>
			</aui:field-wrapper>
		</c:if>

		<c:choose>
			<c:when test="<%= !shoppingPrefs.useAlternativeShipping() %>">
				<aui:field-wrapper label="shipping">
					<%= currencyFormat.format(ShoppingUtil.calculateShipping(items)) %>
				</aui:field-wrapper>
			</c:when>
			<c:otherwise>
				<aui:select label="shipping" name="alternativeShipping">

					<%
					String[][] alternativeShipping = shoppingPrefs.getAlternativeShipping();

					for (int i = 0; i < 10; i++) {
						String altShippingName = alternativeShipping[0][i];
						String altShippingDelta = alternativeShipping[1][i];

						if (Validator.isNotNull(altShippingName) && Validator.isNotNull(altShippingDelta)) {
					%>

							<aui:option label='<%= LanguageUtil.get(pageContext, altShippingName) + "(" + currencyFormat.format(ShoppingUtil.calculateAlternativeShipping(items, i)) + ")" %>' selected="<%= i == cart.getAltShipping() %>" value="<%= i %>" />

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

			<portlet:renderURL var="viewCouponURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="struts_action" value="/shopping/view_coupon" />
				<portlet:param name="couponId" value="<%= String.valueOf(coupon.getCouponId()) %>" />
			</portlet:renderURL>

			<%
			String taglibOpenCouponWindow = "var viewCouponWindow = window.open('" + viewCouponURL + "', 'viewCoupon', 'directories=no,height=200,location=no,menubar=no,resizable=no,scrollbars=yes,status=no,toolbar=no,width=280'); void(''); viewCouponWindow.focus();";
			%>

			<aui:a href='<%= "javascript:" + taglibOpenCouponWindow %>' label='<%= "(" + LanguageUtil.get(pageContext, "description") + ")" %>' style="font-size: xx-small;" />

			<aui:field-wrapper label="coupon-discount">
				<div class="alert alert-error">
					<%= currencyFormat.format(ShoppingUtil.calculateCouponDiscount(items, coupon)) %>
				</div>
			</aui:field-wrapper>
		</c:if>
	</aui:fieldset>

	<%
	String[] ccTypes = shoppingPrefs.getCcTypes();

	if (shoppingPrefs.usePayPal()) {
	%>

		<img alt="paypal" src="<%= themeDisplay.getPathThemeImages() %>/shopping/cc_paypal.png" />

		<br /><br />

	<%
	}
	else if (!shoppingPrefs.usePayPal() && (ccTypes.length > 0)) {
		for (int i = 0; i < ccTypes.length; i++) {
	%>

			<img alt="<%= ccTypes[i] %>" src="<%= themeDisplay.getPathThemeImages() %>/shopping/cc_<%= ccTypes[i] %>.png" />

	<%
		}
	%>

		<br /><br />

	<%
	}
	%>

	<aui:button-row>
		<aui:button onClick='<%= renderResponse.getNamespace() + "updateCart();" %>' value="update-cart" />

		<aui:button onClick='<%= renderResponse.getNamespace() + "emptyCart();" %>' value="empty-cart" />

		<aui:button onClick='<%= renderResponse.getNamespace() + "checkout();" %>' value="checkout" />
	</aui:button-row>
</aui:form>