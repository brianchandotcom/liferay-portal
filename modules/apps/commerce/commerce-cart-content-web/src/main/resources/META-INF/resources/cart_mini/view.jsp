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
CommerceCartContentMiniDisplayContext commerceCartContentMiniDisplayContext = (CommerceCartContentMiniDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = HashMapBuilder.<String, Object>put(
	"commerceCartContentMiniDisplayContext", commerceCartContentMiniDisplayContext
).build();

CommerceMoney subtotalCommerceMoney = null;
CommerceDiscountValue subtotalCommerceDiscountValue = null;
CommerceMoney taxValueCommerceMoney = null;
CommerceDiscountValue totalCommerceDiscountValue = null;
CommerceMoney totalOrderCommerceMoney = null;

String priceDisplayType = commerceCartContentMiniDisplayContext.getCommercePriceDisplayType();

CommerceOrderPrice commerceOrderPrice = commerceCartContentMiniDisplayContext.getCommerceOrderPrice();

if (commerceOrderPrice != null) {
	subtotalCommerceMoney = commerceOrderPrice.getSubtotal();
	subtotalCommerceDiscountValue = commerceOrderPrice.getSubtotalDiscountValue();
	taxValueCommerceMoney = commerceOrderPrice.getTaxValue();

	totalCommerceDiscountValue = commerceOrderPrice.getTotalDiscountValue();
	totalOrderCommerceMoney = commerceOrderPrice.getTotal();

	if (priceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {
		subtotalCommerceMoney = commerceOrderPrice.getSubtotalWithTaxAmount();
		subtotalCommerceDiscountValue = commerceOrderPrice.getSubtotalDiscountValueWithTaxAmount();
		totalCommerceDiscountValue = commerceOrderPrice.getTotalDiscountValueWithTaxAmount();
		totalOrderCommerceMoney = commerceOrderPrice.getTotalWithTaxAmount();
	}
}

SearchContainer<CommerceOrderItem> commerceOrderItemSearchContainer = commerceCartContentMiniDisplayContext.getSearchContainer();

PortletURL portletURL = commerceCartContentMiniDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceOrderItems");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-ddm:template-renderer
	className="<%= CommerceCartContentMiniPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= commerceCartContentMiniDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= commerceCartContentMiniDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= commerceOrderItemSearchContainer.getResults() %>"
>
	<ul class="commerce-order-items-header">
		<li class="autofit-row">
			<div class="autofit-col autofit-col-expand">
				<h4 class="commerce-title">
					<liferay-ui:message arguments="<%= commerceCartContentMiniDisplayContext.getCommerceOrderItemsQuantity() %>" key="items-x" translateArguments="<%= false %>" />
				</h4>
			</div>

			<div class="autofit-col">
				<liferay-commerce:order-transitions
					commerceOrderId="<%= commerceCartContentMiniDisplayContext.getCommerceOrderId() %>"
					cssClass="btn commerce-btn"
				/>
			</div>

			<c:if test="<%= commerceCartContentMiniDisplayContext.hasPermission(ActionKeys.VIEW) %>">
				<div class="autofit-col">
					<div><a class="btn commerce-btn" href="<%= commerceCartContentMiniDisplayContext.getCommerceCartPortletURL() %>"><liferay-ui:message key="edit-cart" /></a></div>
				</div>
			</c:if>
		</li>
	</ul>

	<div class="commerce-order-items-body" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			cssClass="list-group-flush"
			id="commerceOrderItems"
			iteratorURL="<%= portletURL %>"
			searchContainer="<%= commerceOrderItemSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.model.CommerceOrderItem"
				keyProperty="CommerceOrderItemId"
				modelVar="commerceOrderItem"
			>

				<%
				CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();
				%>

				<liferay-ui:search-container-column-image
					cssClass="thumbnail-section"
					src="<%= commerceCartContentMiniDisplayContext.getCommerceOrderItemThumbnailSrc(commerceOrderItem) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="autofit-col-expand"
				>
					<div class="description-section">
						<div class="list-group-title">
							<a href="<%= commerceCartContentMiniDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay) %>">
								<%= HtmlUtil.escape(cpDefinition.getName(languageId)) %>
							</a>
						</div>

						<%
						List<KeyValuePair> keyValuePairs = commerceCartContentMiniDisplayContext.getKeyValuePairs(commerceOrderItem.getCPDefinitionId(), commerceOrderItem.getJson(), locale);

						StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

						for (KeyValuePair keyValuePair : keyValuePairs) {
							stringJoiner.add(keyValuePair.getValue());
						}
						%>

						<div class="list-group-subtitle"><liferay-ui:message arguments="<%= HtmlUtil.escape(commerceOrderItem.getSku()) %>" key="sku-x" translateArguments="<%= false %>" /></div>
						<div class="list-group-subtitle"><%= HtmlUtil.escape(stringJoiner.toString()) %></div>

						<%
						CPInstance cpInstance = commerceOrderItem.fetchCPInstance();
						%>

						<c:if test="<%= (cpInstance != null) && Validator.isNotNull(cpInstance.getCPSubscriptionInfo()) %>">
							<div class="list-group-subtitle">
								<commerce-ui:product-subscription-info
									CPInstanceId="<%= commerceOrderItem.getCPInstanceId() %>"
									showDuration="<%= false %>"
								/>
							</div>
						</c:if>
					</div>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text>
					<div class="quantity-section">
						<span class="commerce-quantity"><%= commerceOrderItem.getQuantity() %></span><span class="inline-item-after">x</span>
					</div>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="price"
				>
					<c:if test="<%= commerceCartContentMiniDisplayContext.hasViewPricePermission() %>">

						<%
						CommerceMoney unitPriceCommerceMoney = commerceCartContentMiniDisplayContext.getUnitPriceCommerceMoney(commerceOrderItem);
						CommerceMoney unitPromoPriceCommerceMoney = commerceCartContentMiniDisplayContext.getUnitPromoPriceCommerceMoney(commerceOrderItem);
						%>

						<c:choose>
							<c:when test="<%= commerceCartContentMiniDisplayContext.isUnitPromoPriceActive(commerceOrderItem) %>">
								<%= HtmlUtil.escape(unitPromoPriceCommerceMoney.format(locale)) %>
							</c:when>
							<c:otherwise>
								<%= HtmlUtil.escape(unitPriceCommerceMoney.format(locale)) %>
							</c:otherwise>
						</c:choose>
					</c:if>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="descriptive"
				markupView="lexicon"
				paginate="<%= false %>"
				searchContainer="<%= commerceOrderItemSearchContainer %>"
			/>
		</liferay-ui:search-container>

		<c:if test="<%= commerceCartContentMiniDisplayContext.getCommerceOrderItemsQuantity() > commerceOrderItemSearchContainer.getDelta() %>">
			<ul class="commerce-order-items-header">
				<li class="autofit-row">
					<c:if test="<%= commerceCartContentMiniDisplayContext.hasPermission(ActionKeys.VIEW) %>">
						<a class="btn btn-link commerce-link" href="<%= commerceCartContentMiniDisplayContext.getCommerceCartPortletURL() %>"><liferay-ui:message key="view-more" /></a>
					</c:if>
				</li>
			</ul>
		</c:if>
	</div>

	<ul class="commerce-order-items-footer">
		<li class="autofit-row commerce-tax">
			<c:if test="<%= subtotalCommerceMoney != null %>">
				<div class="autofit-col autofit-col-expand">
					<div class="commerce-description"><liferay-ui:message key="subtotal" /></div>
				</div>

				<div class="autofit-col">
					<div class="commerce-value"><%= HtmlUtil.escape(subtotalCommerceMoney.format(locale)) %></div>
				</div>
			</c:if>

			<c:if test="<%= subtotalCommerceDiscountValue != null %>">

				<%
				CommerceMoney subtotalDiscountAmountCommerceMoney = subtotalCommerceDiscountValue.getDiscountAmount();
				%>

				<div class="autofit-col autofit-col-expand">
					<div class="commerce-description"><liferay-ui:message key="subtotal-discount" /></div>
				</div>

				<div class="commerce-value">
					<%= HtmlUtil.escape(subtotalDiscountAmountCommerceMoney.format(locale)) %>
				</div>

				<div class="commerce-value pl-1">
					(<%= HtmlUtil.escape(commerceCartContentMiniDisplayContext.getLocalizedPercentage(subtotalCommerceDiscountValue.getDiscountPercentage(), locale)) %>)
				</div>
			</c:if>
		</li>
		<li class="autofit-row commerce-tax">
			<c:if test="<%= (taxValueCommerceMoney != null) && priceDisplayType.equals(CommercePricingConstants.TAX_EXCLUDED_FROM_PRICE) %>">
				<div class="autofit-col autofit-col-expand">
					<div class="commerce-description"><liferay-ui:message key="tax" /></div>
				</div>

				<div class="autofit-col">
					<div class="commerce-value"><%= HtmlUtil.escape(taxValueCommerceMoney.format(locale)) %></div>
				</div>
			</c:if>
		</li>
		<li class="autofit-row commerce-total">
			<c:if test="<%= totalCommerceDiscountValue != null %>">

				<%
				CommerceMoney totalDiscountAmountCommerceMoney = totalCommerceDiscountValue.getDiscountAmount();
				%>

				<div class="autofit-col autofit-col-expand">
					<div class="commerce-description"><liferay-ui:message key="total-discount" /></div>
				</div>

				<div class="commerce-value">
					<%= HtmlUtil.escape(totalDiscountAmountCommerceMoney.format(locale)) %>
				</div>

				<div class="commerce-value pl-1">
					(<%= HtmlUtil.escape(commerceCartContentMiniDisplayContext.getLocalizedPercentage(totalCommerceDiscountValue.getDiscountPercentage(), locale)) %>)
				</div>
			</c:if>

			<c:if test="<%= totalOrderCommerceMoney != null %>">
				<div class="autofit-col autofit-col-expand">
					<div class="commerce-description"><liferay-ui:message key="total" /></div>
				</div>

				<div class="autofit-col">
					<div class="commerce-value"><%= HtmlUtil.escape(totalOrderCommerceMoney.format(locale)) %></div>
				</div>
			</c:if>
		</li>
	</ul>

	<%@ include file="/cart_mini/transition.jspf" %>

	<aui:script use="aui-base">
		var orderTransition = A.one('#<portlet:namespace />orderTransition');

		if (orderTransition) {
			orderTransition.delegate(
				'click',
				function (event) {
					<portlet:namespace />transition(event);
				},
				'.transition-link'
			);
		}
	</aui:script>

	<aui:script>
		Liferay.after('commerce:productAddedToCart', function (event) {
			Liferay.Portlet.refresh('#p_p_id<portlet:namespace />');
		});
	</aui:script>
</liferay-ddm:template-renderer>