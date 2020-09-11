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
CommerceCartContentDisplayContext commerceCartContentDisplayContext = (CommerceCartContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = HashMapBuilder.<String, Object>put(
	"commerceCartContentDisplayContext", commerceCartContentDisplayContext
).build();

SearchContainer<CommerceOrderItem> commerceOrderItemSearchContainer = commerceCartContentDisplayContext.getSearchContainer();

PortletURL portletURL = commerceCartContentDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceOrderItems");

request.setAttribute("view.jsp-portletURL", portletURL);

List<CommerceOrderValidatorResult> commerceOrderValidatorResults = new ArrayList<>();

Map<Long, List<CommerceOrderValidatorResult>> commerceOrderValidatorResultMap = commerceCartContentDisplayContext.getCommerceOrderValidatorResults();
%>

<liferay-ui:error exception="<%= CommerceOrderValidatorException.class %>">

	<%
	CommerceOrderValidatorException commerceOrderValidatorException = (CommerceOrderValidatorException)errorException;

	if (commerceOrderValidatorException != null) {
		commerceOrderValidatorResults = commerceOrderValidatorException.getCommerceOrderValidatorResults();
	}

	for (CommerceOrderValidatorResult commerceOrderValidatorResult : commerceOrderValidatorResults) {
	%>

		<liferay-ui:message key="<%= commerceOrderValidatorResult.getLocalizedMessage() %>" />

	<%
	}
	%>

</liferay-ui:error>

<liferay-ddm:template-renderer
	className="<%= CommerceCartContentPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= commerceCartContentDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= commerceCartContentDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= commerceOrderItemSearchContainer.getResults() %>"
>
	<div class="commerce-order-items container-fluid-1280" id="<portlet:namespace />orderItemsContainer">
		<div class="commerce-order-items-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
				id="commerceOrderItems"
				iteratorURL="<%= portletURL %>"
				searchContainer="<%= commerceOrderItemSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceOrderItem"
					cssClass="entry-display-style"
					keyProperty="CommerceOrderItemId"
					modelVar="commerceOrderItem"
				>

					<%
					CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

					String thumbnailSrc = commerceCartContentDisplayContext.getCommerceOrderItemThumbnailSrc(commerceOrderItem);

					List<KeyValuePair> keyValuePairs = commerceCartContentDisplayContext.getKeyValuePairs(commerceOrderItem.getCPDefinitionId(), commerceOrderItem.getJson(), locale);

					StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

					for (KeyValuePair keyValuePair : keyValuePairs) {
						stringJoiner.add(keyValuePair.getValue());
					}
					%>

					<liferay-ui:search-container-column-image
						name="product"
						src="<%= thumbnailSrc %>"
					/>

					<liferay-ui:search-container-column-text
						name="description"
					>
						<a class="font-weight-bold" href="<%= commerceCartContentDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay) %>">
							<%= HtmlUtil.escape(cpDefinition.getName(languageId)) %>
						</a>

						<h6 class="text-default">
							<%= HtmlUtil.escape(stringJoiner.toString()) %>
						</h6>

						<c:if test="<%= !commerceOrderValidatorResultMap.isEmpty() %>">

							<%
							commerceOrderValidatorResults = commerceOrderValidatorResultMap.get(commerceOrderItem.getCommerceOrderItemId());

							for (CommerceOrderValidatorResult commerceOrderValidatorResult : commerceOrderValidatorResults) {
							%>

								<div class="alert-danger commerce-alert-danger">
									<liferay-ui:message key="<%= commerceOrderValidatorResult.getLocalizedMessage() %>" />
								</div>

							<%
							}
							%>

						</c:if>
					</liferay-ui:search-container-column-text>

					<%
					String commercePriceDisplayType = commerceCartContentDisplayContext.getCommercePriceDisplayType();
					%>

					<liferay-ui:search-container-column-text
						name="list-price"
					>
						<c:if test="<%= commerceCartContentDisplayContext.hasViewPricePermission() %>">

							<%
							CommerceMoney unitPriceMoney = commerceOrderItem.getUnitPriceMoney();

							if (commercePriceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {
								unitPriceMoney = commerceOrderItem.getUnitPriceWithTaxAmountMoney();
							}
							%>

							<%= HtmlUtil.escape(unitPriceMoney.format(locale)) %>
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="quantity-control-column"
						name="quantity"
					>
						<liferay-commerce-cart:quantity-control
							commerceOrderItemId="<%= commerceOrderItem.getCommerceOrderItemId() %>"
							useSelect="<%= false %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="total"
					>
						<c:if test="<%= commerceCartContentDisplayContext.hasViewPricePermission() %>">

							<%
							CommerceMoney finalPriceMoney = commerceOrderItem.getFinalPriceMoney();

							if (commercePriceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {
								finalPriceMoney = commerceOrderItem.getFinalPriceWithTaxAmountMoney();
							}
							%>

							<%= HtmlUtil.escape(finalPriceMoney.format(locale)) %>

							<commerce-ui:product-subscription-info
								CPInstanceId="<%= commerceOrderItem.getCPInstanceId() %>"
								showDuration="<%= false %>"
							/>
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text>
						<c:if test="<%= commerceCartContentDisplayContext.hasPermission(ActionKeys.UPDATE) %>">
							<liferay-ui:icon-delete
								label="<%= true %>"
								url="<%= commerceCartContentDisplayContext.getDeleteURL(commerceOrderItem) %>"
							/>
						</c:if>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="list"
					markupView="lexicon"
					searchContainer="<%= commerceOrderItemSearchContainer %>"
				/>
			</liferay-ui:search-container>
		</div>
	</div>

	<aui:script>
		Liferay.after('commerce:productAddedToCart', function (event) {
			Liferay.Portlet.refresh('#p_p_id<portlet:namespace />');
		});
	</aui:script>
</liferay-ddm:template-renderer>