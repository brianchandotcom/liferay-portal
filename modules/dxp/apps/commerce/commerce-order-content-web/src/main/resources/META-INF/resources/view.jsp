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

List<CommerceOrder> results = commerceOrderSearchContainer.getResults();
%>

<liferay-ddm:template-renderer
	className="<%= CommerceOrderContentPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= commerceOrderContentDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= commerceOrderContentDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= results %>"
>
	<div class="container-fluid-1280" id="<portlet:namespace />ordersContainer">
		<div class="commerce-orders-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
				id="commerceOrders"
				iteratorURL="<%= currentURLObj %>"
				searchContainer="<%= commerceOrderSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceOrder"
					cssClass="entry-display-style"
					keyProperty="commerceOrderId"
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
						<liferay-ui:icon
							label="<%= true %>"
							message="view-details"
							url="<%= commerceOrderContentDisplayContext.getCommerceOrderItemsDetailURL(commerceOrder.getCommerceOrderId()) %>"
						/>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="list"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</div>
	</div>
</liferay-ddm:template-renderer>