<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/add_to_cart/init.jsp" %>

<%
String spaceDirection = GetterUtil.getBoolean(inline) ? "ml" : "mt";
String spacer = size.equals("sm") ? "1" : "3";

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
		cartId: <%= commerceOrderId %>,
		channel: {
			currencyCode: '<%= commerceCurrencyCode %>',
			groupId: <%= commerceChannelGroupId %>,
			id: <%= commerceChannelId %>,
		},
		cpInstance: {
			inCart: <%= inCart %>,
			skuId: <%= cpInstanceId %>,
			skuOptions: <%= skuOptions %> || [],
			stockQuantity: <%= stockQuantity %>,
			<c:if test="<%= cpInstanceUnitOfMeasure != null %>">
				skuUnitOfMeasure: {
					incrementalOrderQuantity: <%= cpInstanceUnitOfMeasure.getIncrementalOrderQuantity() %>,
					key: '<%= HtmlUtil.escapeJS(cpInstanceUnitOfMeasure.getKey()) %>',
					name: '<%= HtmlUtil.escapeJS(cpInstanceUnitOfMeasure.getName()) %>',
					precision: <%= cpInstanceUnitOfMeasure.getPrecision() %>,
					primary: <%= cpInstanceUnitOfMeasure.isPrimary() %>,
					priority: <%= cpInstanceUnitOfMeasure.getPriority() %>,
					rate: <%= cpInstanceUnitOfMeasure.getRate() %>,
				},
			</c:if>
		},
		disabled: <%= disabled %>,
		productId: <%= productId %>,
		settings: {
			alignment: '<%= alignment %>',
			iconOnly: <%= iconOnly %>,
			inline: <%= inline %>,
			namespace: '<%= namespace %>',
			showUnitOfMeasureSelector: <%= showUnitOfMeasureSelector %>,
			size: '<%= size %>',
		},
		showOrderTypeModal: <%= showOrderTypeModal %>,
		showOrderTypeModalURL: '<%= showOrderTypeModalURL %>',
	};

	<c:if test="<%= productSettingsModel != null %>">

		<%
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
		%>

		const productConfiguration = <%= jsonSerializer.serializeDeep(productSettingsModel) %>;

		props.settings.productConfiguration = {
			allowBackOrder: productConfiguration.backOrders,
			allowedOrderQuantities: productConfiguration.allowedQuantities,
			maxOrderQuantity: productConfiguration.maxQuantity,
			minOrderQuantity: productConfiguration.minQuantity,
			multipleOrderQuantity: productConfiguration.multipleQuantity,
		};
	</c:if>

	AddToCart.default('<%= addToCartId %>', '<%= addToCartId %>', props);
</aui:script>