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
CommerceOrderContentDisplayContext commerceOrderContentDisplayContext = (CommerceOrderContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("commerceOrderContentDisplayContext", commerceOrderContentDisplayContext);

SearchContainer<CommerceOrder> commerceOrderSearchContainer = commerceOrderContentDisplayContext.getSearchContainer();

PortletURL portletURL = commerceOrderContentDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceOrders");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<div class="container-fluid-1280" id="<portlet:namespace />ordersContainer">
	<div class="commerce-orders-container" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			id="commerceOrders"
			iteratorURL="<%= portletURL %>"
			searchContainer="<%= commerceOrderSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.model.CommerceOrder"
				cssClass="entry-display-style"
				keyProperty="CommerceOrderId"
				modelVar="commerceOrder"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="order-number"
					property="commerceOrderId"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="total"
					value="<%= commerceOrderContentDisplayContext.getCommerceOrderTotal(commerceOrder.getCommerceOrderId()) %>"
				/>

				<liferay-ui:search-container-column-status
					cssClass="table-cell-content"
					name="status"
					status="<%= commerceOrder.getStatus() %>"
				/>

				<liferay-ui:search-container-column-date
					cssClass="table-cell-content"
					name="create-date"
					property="createDate"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
				>
					<portlet:renderURL var="viewCommerceOrderItemsURL">
						<portlet:param name="mvcRenderCommandName" value="viewCommerceOrderItems" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
					</portlet:renderURL>

					<liferay-ui:icon
						label="<%= true %>"
						message="view-details"
						url="<%= viewCommerceOrderItemsURL %>"
					/>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="list"
				markupView="lexicon"
				searchContainer="<%= commerceOrderSearchContainer %>"
			/>
		</liferay-ui:search-container>
	</div>
</div>