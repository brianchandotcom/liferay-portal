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

SearchContainer<CommerceCartItem> commerceCartItemSearchContainer = commerceCartContentDisplayContext.getSearchContainer();

PortletURL portletURL = commerceCartContentDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceCartItems");

request.setAttribute("view.jsp-portletURL", portletURL);

List<CommerceCartValidatorResult> commerceCartValidatorResults = new ArrayList<>();

Map<Long, List<CommerceCartValidatorResult>> commerceCartValidatorResultMap = commerceCartContentDisplayContext.getCommerceCartValidatorResults();
%>

<liferay-ui:error exception="<%= CommerceCartValidatorException.class %>">

	<%
	CommerceCartValidatorException ccve = (CommerceCartValidatorException)errorException;

	if (ccve != null) {
		commerceCartValidatorResults = ccve.getCommerceCartValidatorResults();
	}

	for (CommerceCartValidatorResult commerceCartValidatorResult : commerceCartValidatorResults) {
	%>

		<c:choose>
			<c:when test="<%= commerceCartValidatorResult.hasArgument() %>">
				<liferay-ui:message arguments="<%= commerceCartValidatorResult.getArgument() %>" key="<%= commerceCartValidatorResult.getMessage() %>" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="<%= commerceCartValidatorResult.getMessage() %>" />
			</c:otherwise>
		</c:choose>

	<%
	}
	%>

</liferay-ui:error>

<div class="container-fluid-1280" id="<portlet:namespace />cartItemsContainer">
	<div class="commerce-cart-items-container" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			id="commerceCartItems"
			iteratorURL="<%= portletURL %>"
			searchContainer="<%= commerceCartItemSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.model.CommerceCartItem"
				cssClass="entry-display-style"
				keyProperty="CommerceCartItemId"
				modelVar="commerceCartItem"
			>

				<%
				CPDefinition cpDefinition = commerceCartItem.getCPDefinition();

				String thumbnailSrc = commerceCartContentDisplayContext.getCommerceCartItemThumbnailSrc(commerceCartItem, themeDisplay);

				List<KeyValuePair> keyValuePairs = commerceCartContentDisplayContext.parseJSONString(commerceCartItem.getJson(), locale);

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
					<a href="<%= commerceCartContentDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay) %>">
						<%= HtmlUtil.escape(cpDefinition.getTitle(languageId)) %>
					</a>

					<h6 class="text-default">
						<%= HtmlUtil.escape(stringJoiner.toString()) %>
					</h6>

					<c:if test="<%= !commerceCartValidatorResultMap.isEmpty() %>">

						<%
						commerceCartValidatorResults = commerceCartValidatorResultMap.get(commerceCartItem.getCommerceCartItemId());

						for (CommerceCartValidatorResult commerceCartValidatorResult : commerceCartValidatorResults) {
						%>

							<div class="alert-danger commerce-alert-danger">
								<c:choose>
									<c:when test="<%= commerceCartValidatorResult.hasArgument() %>">
										<liferay-ui:message arguments="<%= commerceCartValidatorResult.getArgument() %>" key="<%= commerceCartValidatorResult.getMessage() %>" />
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="<%= commerceCartValidatorResult.getMessage() %>" />
									</c:otherwise>
								</c:choose>
							</div>

						<%
						}
						%>

					</c:if>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="price"
					value="<%= commerceCartContentDisplayContext.getFormattedPrice(commerceCartItem) %>"
				/>

				<liferay-ui:search-container-column-text>
					<portlet:actionURL name="editCommerceCartItem" var="deleteURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="commerceCartItemId" value="<%= String.valueOf(commerceCartItem.getCommerceCartItemId()) %>" />
						<portlet:param name="type" value="<%= String.valueOf(CommerceConstants.COMMERCE_CART_TYPE_CART) %>" />
					</portlet:actionURL>

					<liferay-ui:icon-delete
						label="<%= true %>"
						url="<%= deleteURL %>"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-jsp
					name="quantity"
					path="/cart/cart_item_quantity_select.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" searchContainer="<%= commerceCartItemSearchContainer %>" />
		</liferay-ui:search-container>
	</div>
</div>