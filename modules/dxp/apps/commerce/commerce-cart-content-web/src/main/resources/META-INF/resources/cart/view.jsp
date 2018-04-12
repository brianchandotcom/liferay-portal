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

SearchContainer<CommerceOrderItem> commerceOrderItemSearchContainer = commerceCartContentDisplayContext.getSearchContainer();

PortletURL portletURL = commerceCartContentDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceOrderItems");

request.setAttribute("view.jsp-portletURL", portletURL);

List<CommerceOrderValidatorResult> commerceOrderValidatorResults = new ArrayList<>();

Map<Long, List<CommerceOrderValidatorResult>> commerceOrderValidatorResultMap = commerceCartContentDisplayContext.getCommerceOrderValidatorResults();
%>

<liferay-ui:error exception="<%= CommerceOrderValidatorException.class %>">

	<%
	CommerceOrderValidatorException cove = (CommerceOrderValidatorException)errorException;

	if (cove != null) {
		commerceOrderValidatorResults = cove.getCommerceOrderValidatorResults();
	}

	for (CommerceOrderValidatorResult commerceOrderValidatorResult : commerceOrderValidatorResults) {
	%>

		<c:choose>
			<c:when test="<%= commerceOrderValidatorResult.hasArgument() %>">
				<liferay-ui:message arguments="<%= commerceOrderValidatorResult.getArgument() %>" key="<%= commerceOrderValidatorResult.getMessage() %>" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="<%= commerceOrderValidatorResult.getMessage() %>" />
			</c:otherwise>
		</c:choose>

	<%
	}
	%>

</liferay-ui:error>

<div class="container-fluid-1280" id="<portlet:namespace />orderItemsContainer">
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

				String thumbnailSrc = commerceCartContentDisplayContext.getCommerceOrderItemThumbnailSrc(commerceOrderItem, themeDisplay);

				List<KeyValuePair> keyValuePairs = commerceCartContentDisplayContext.getKeyValuePairs(commerceOrderItem.getJson(), locale);

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

					<c:if test="<%= !commerceOrderValidatorResultMap.isEmpty() %>">

						<%
						commerceOrderValidatorResults = commerceOrderValidatorResultMap.get(commerceOrderItem.getCommerceOrderItemId());

						for (CommerceOrderValidatorResult commerceOrderValidatorResult : commerceOrderValidatorResults) {
						%>

							<div class="alert-danger commerce-alert-danger">
								<c:choose>
									<c:when test="<%= commerceOrderValidatorResult.hasArgument() %>">
										<liferay-ui:message arguments="<%= commerceOrderValidatorResult.getArgument() %>" key="<%= commerceOrderValidatorResult.getMessage() %>" />
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="<%= commerceOrderValidatorResult.getMessage() %>" />
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
					value="<%= commerceCartContentDisplayContext.getFormattedPrice(commerceOrderItem) %>"
				/>

				<liferay-ui:search-container-column-text>
					<portlet:actionURL name="editCommerceOrderItem" var="deleteURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="commerceOrderItemId" value="<%= String.valueOf(commerceOrderItem.getCommerceOrderItemId()) %>" />
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

			<liferay-ui:search-iterator
				displayStyle="list"
				markupView="lexicon"
				searchContainer="<%= commerceOrderItemSearchContainer %>"
			/>
		</liferay-ui:search-container>
	</div>
</div>