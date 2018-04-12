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
String redirect = ParamUtil.getString(request, "redirect");

CommerceOrderItemContentDisplayContext commerceOrderItemContentDisplayContext = (CommerceOrderItemContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderItemContentDisplayContext.getCommerceOrder();

SearchContainer<CommerceOrderItem> commerceOrderItemSearchContainer = commerceOrderItemContentDisplayContext.getSearchContainer();

PortletURL portletURL = commerceOrderItemContentDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceOrderItems");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<div class="commerce-orders-container container-fluid-1280" id="<portlet:namespace />orderItemsContainer">
	<liferay-ui:header
		backURL="<%= redirect %>"
		title="back"
	/>

	<div class="col-md-8" id="<portlet:namespace />entriesContainer">
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

				String cpDefinitionURL = StringPool.BLANK;
				String thumbnailSrc = StringPool.BLANK;

				if (cpDefinition != null) {
					cpDefinitionURL = commerceOrderItemContentDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay);
					thumbnailSrc = cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);
				}
				%>

				<liferay-ui:search-container-column-image
					cssClass="table-cell-content"
					name="product"
					src="<%= thumbnailSrc %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= cpDefinitionURL %>"
					name="description"
					value="<%= HtmlUtil.escape(commerceOrderItem.getTitle(languageId)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="quantity"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="price"
					value="<%= commerceOrderItemContentDisplayContext.getFormattedPrice(commerceOrderItem.getCommerceOrderItemId()) %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="list"
				markupView="lexicon"
				searchContainer="<%= commerceOrderItemSearchContainer %>"
			/>
		</liferay-ui:search-container>
	</div>

	<div class="col-md-4">
		<h4><liferay-ui:message key="order-number" /> <%= HtmlUtil.escape(String.valueOf(commerceOrder.getCommerceOrderId())) %></h4>

		<h5>
			<liferay-ui:message key="order-status" /> <aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= commerceOrder.getStatus() %>" /></h5>
		</h5>

		<h5>
			<liferay-ui:message key="order-date" /> <%= HtmlUtil.escape(String.valueOf(commerceOrder.getCreateDate())) %>
		</h5>
	</div>
</div>