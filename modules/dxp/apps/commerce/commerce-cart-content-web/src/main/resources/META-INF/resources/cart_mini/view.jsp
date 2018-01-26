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

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("commerceCartContentMiniDisplayContext", commerceCartContentMiniDisplayContext);

SearchContainer<CommerceCartItem> commerceCartItemSearchContainer = commerceCartContentMiniDisplayContext.getSearchContainer();

PortletURL portletURL = commerceCartContentMiniDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceCartItems");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-ddm:template-renderer
	className="<%= CommerceCartContentMiniPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= commerceCartContentMiniDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= commerceCartContentMiniDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= commerceCartItemSearchContainer.getResults() %>"
>
	<div class="commerce-cart-info">
		<h4><strong><liferay-ui:message key="total" /> <%= HtmlUtil.escape(commerceCartContentMiniDisplayContext.getCommerceCartSubtotal()) %></strong></h4>
	</div>

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

				String thumbnailSrc = commerceCartContentMiniDisplayContext.getCommerceCartItemThumbnailSrc(commerceCartItem, themeDisplay);
				%>

				<liferay-ui:search-container-column-image
					cssClass="table-cell-content"
					src="<%= thumbnailSrc %>"
				/>

				<liferay-ui:search-container-column-text
					colspan="<%= 2 %>"
				>
					<h5>
						<a href="<%= commerceCartContentMiniDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay) %>">
							<%= HtmlUtil.escape(cpDefinition.getTitle(languageId)) %>
						</a>
					</h5>

					<%
					List<KeyValuePair> keyValuePairs = commerceCartContentMiniDisplayContext.getKeyValuePairs(commerceCartItem.getJson(), locale);

					StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

					for (KeyValuePair keyValuePair : keyValuePairs) {
						stringJoiner.add(keyValuePair.getValue());
					}
					%>

					<h6 class="text-default">
						<%= HtmlUtil.escape(stringJoiner.toString()) %>
					</h6>

					<h6 class="text-default">
						<liferay-ui:message arguments="<%= commerceCartItem.getQuantity() %>" key="quantity-x" />
					</h6>

					<h6>
						<%= commerceCartContentMiniDisplayContext.getFormattedPrice(commerceCartItem) %>
					</h6>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" paginate="<%= false %>" searchContainer="<%= commerceCartItemSearchContainer %>" />
		</liferay-ui:search-container>
	</div>
</liferay-ddm:template-renderer>