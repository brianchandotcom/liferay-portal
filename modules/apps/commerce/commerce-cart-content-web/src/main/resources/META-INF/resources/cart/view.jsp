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
	<div class="commerce-order-items container-fluid container-fluid-max-xl" id="<portlet:namespace />orderItemsContainer">
		<div class="commerce-order-items-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
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
					CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

					long cpDefinitionId = 0;

					String thumbnailSrc = StringPool.BLANK;

					StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

					if (cpInstance != null) {
						CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

						cpDefinitionId = cpDefinition.getCPDefinitionId();

						thumbnailSrc = commerceCartContentDisplayContext.getCommerceOrderItemThumbnailSrc(commerceOrderItem);

						List<KeyValuePair> keyValuePairs = commerceCartContentDisplayContext.getKeyValuePairs(commerceOrderItem.getCPDefinitionId(), commerceOrderItem.getJson(), locale);

						for (KeyValuePair keyValuePair : keyValuePairs) {
							stringJoiner.add(keyValuePair.getValue());
						}
					}
					%>

					<liferay-ui:search-container-column-image
						name="product"
						src="<%= thumbnailSrc %>"
					/>

					<liferay-ui:search-container-column-text
						name="description"
					>
						<a class="font-weight-bold" href="<%= (cpDefinitionId == 0) ? StringPool.BLANK : commerceCartContentDisplayContext.getCPDefinitionURL(cpDefinitionId, themeDisplay) %>">
							<%= HtmlUtil.escape(commerceOrderItem.getName(languageId)) %>
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

					<liferay-ui:search-container-column-text
						name="price"
					>
						<c:if test="<%= commerceCartContentDisplayContext.hasViewPricePermission() %>">

							<%
							CommerceMoney unitPriceCommerceMoney = commerceCartContentDisplayContext.getUnitPriceCommerceMoney(commerceOrderItem);
							CommerceMoney unitPromoPriceCommerceMoney = commerceCartContentDisplayContext.getUnitPromoPriceCommerceMoney(commerceOrderItem);
							%>

							<c:choose>
								<c:when test="<%= commerceCartContentDisplayContext.isUnitPromoPriceActive(commerceOrderItem) %>">
									<%= HtmlUtil.escape(unitPromoPriceCommerceMoney.format(locale)) %>
								</c:when>
								<c:otherwise>
									<%= HtmlUtil.escape(unitPriceCommerceMoney.format(locale)) %>
								</c:otherwise>
							</c:choose>
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="discount"
					>
						<c:if test="<%= commerceCartContentDisplayContext.hasViewPricePermission() %>">

							<%
							CommerceMoney discountAmountCommerceMoney = commerceCartContentDisplayContext.getDiscountAmountCommerceMoney(commerceOrderItem);
							%>

							<%= HtmlUtil.escape(discountAmountCommerceMoney.format(locale)) %>
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
							CommerceMoney finalPriceCommerceMoney = commerceCartContentDisplayContext.getFinalPriceCommerceMoney(commerceOrderItem);
							%>

							<%= HtmlUtil.escape(finalPriceCommerceMoney.format(locale)) %>

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
		Liferay.after('current-order-updated', function (event) {
			Liferay.Portlet.refresh('#p_p_id<portlet:namespace />');
		});
	</aui:script>
</liferay-ddm:template-renderer>