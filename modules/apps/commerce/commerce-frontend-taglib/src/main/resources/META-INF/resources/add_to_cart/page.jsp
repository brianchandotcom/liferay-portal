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

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"accountId", commerceAccountId
		).put(
			"addToCartId", addToCartId
		).put(
			"cartId", commerceOrderId
		).put(
			"channel",
			HashMapBuilder.<String, Object>put(
				"currencyCode", commerceCurrencyCode
			).put(
				"groupId", commerceChannelGroupId
			).put(
				"id", commerceChannelId
			).build()
		).put(
			"cpInstance",
			HashMapBuilder.<String, Object>put(
				"availability",
				HashMapBuilder.<String, Object>put(
					"stockQuantity", stockQuantity
				).build()
			).put(
				"backOrderAllowed", (productSettingsModel != null) ? productSettingsModel.isBackOrders() : null
			).put(
				"inCart", inCart
			).put(
				"published", published
			).put(
				"purchasable", purchasable
			).put(
				"skuId", cpInstanceId
			).put(
				"skuOptions", skuOptions
			).put(
				"skuUnitOfMeasure",
				() -> {
					if (cpInstanceUnitOfMeasure == null) {
						return null;
					}
					else {
						return HashMapBuilder.<String, Object>put(
							"incrementalOrderQuantity", cpInstanceUnitOfMeasure.getIncrementalOrderQuantity()
						).put(
							"key", cpInstanceUnitOfMeasure.getKey()
						).put(
							"name", cpInstanceUnitOfMeasure.getName()
						).put(
							"precision", cpInstanceUnitOfMeasure.getPrecision()
						).put(
							"primary", cpInstanceUnitOfMeasure.isPrimary()
						).put(
							"priority", cpInstanceUnitOfMeasure.getPriority()
						).put(
							"rate", cpInstanceUnitOfMeasure.getRate()
						).build();
					}
				}
			).put(
				"stockQuantity", stockQuantity
			).build()
		).put(
			"disabled", disabled
		).put(
			"productId", productId
		).put(
			"settings",
			HashMapBuilder.<String, Object>put(
				"alignment", alignment
			).put(
				"iconOnly", iconOnly
			).put(
				"inline", inline
			).put(
				"namespace", namespace
			).put(
				"productConfiguration",
				() -> {
					if (productSettingsModel == null) {
						return null;
					}
					else {
						return HashMapBuilder.<String, Object>put(
							"alignment", alignment
						).put(
							"allowBackOrder", productSettingsModel.isBackOrders()
						).put(
							"allowedOrderQuantities", productSettingsModel.getAllowedQuantities()
						).put(
							"maxOrderQuantity", productSettingsModel.getMaxQuantity()
						).put(
							"minOrderQuantity", productSettingsModel.getMinQuantity()
						).put(
							"multipleOrderQuantity", productSettingsModel.getMultipleQuantity()
						).build();
					}
				}
			).put(
				"showUnitOfMeasureSelector", showUnitOfMeasureSelector
			).put(
				"size", size
			).build()
		).put(
			"showOrderTypeModal", showOrderTypeModal
		).put(
			"showOrderTypeModalURL", showOrderTypeModalURL
		).build()
	%>'
	module="{addToCart} from commerce-frontend-taglib"
/>