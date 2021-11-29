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

<%@ include file="/add_to_cart/init.jsp" %>

<%
String spacer = size.equals("sm") ? "1" : "3";
String spaceDirection = GetterUtil.getBoolean(inline) ? "ml" : "mt";

String buttonCssClasses = "btn btn-add-to-cart btn-" + size + " " + spaceDirection + "-" + spacer;

String selectorCssClasses = "form-control quantity-selector form-control-" + size;
String wrapperCssClasses = "add-to-cart-wrapper align-items-center d-flex";

if (GetterUtil.getBoolean(iconOnly)) {
	buttonCssClasses = buttonCssClasses.concat(" icon-only");
}

if (!GetterUtil.getBoolean(inline)) {
	wrapperCssClasses = wrapperCssClasses.concat(" flex-column");
}

if (alignment.equals("center")) {
	wrapperCssClasses = wrapperCssClasses.concat(" align-items-center");
}

if (alignment.equals("full-width")) {
	buttonCssClasses = buttonCssClasses.concat(" btn-block");
	wrapperCssClasses = wrapperCssClasses.concat(" align-items-center");
}
%>

<div class="add-to-cart mb-2" id="<%= addToCartId %>">
	<div class="<%= wrapperCssClasses %>">
		<div class="<%= selectorCssClasses %> skeleton"></div>

		<button class="<%= buttonCssClasses %> skeleton">
			<liferay-ui:message key="add-to-cart" />
		</button>
	</div>
</div>

<aui:script require="commerce-frontend-js/components/add_to_cart/entry as AddToCart">
	const props = {
		accountId: <%= commerceAccountId %>,
		channel: {
			currencyCode: '<%= commerceCurrencyCode %>',
			groupId: <%= commerceChannelGroupId %>,
			id: <%= commerceChannelId %>,
		},
		cpInstance: {
			inCart: <%= inCart %>,
			options: <%= options %> || [],
			skuId: <%= cpInstanceId %>,
			stockQuantity: <%= stockQuantity %>,
		},
		cartId: <%= commerceOrderId %>,
		disabled: <%= disabled %>,
		settings: {
			alignment: '<%= alignment %>',
			iconOnly: <%= iconOnly %>,
			inline: <%= inline %>,
			namespace: '<%= namespace %>',
			size: '<%= size %>',
		},
	};

	<c:if test="<%= productSettingsModel != null %>">

		<%
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
		%>

		props.settings.quantityDetails = <%= jsonSerializer.serializeDeep(productSettingsModel) %>;
	</c:if>

	AddToCart.default('<%= addToCartId %>', '<%= addToCartId %>', props);
</aui:script>