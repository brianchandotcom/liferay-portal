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
Map<String, Object> contextObjects = new HashMap<>();

CommerceOrderContentDisplayContext commerceOrderContentDisplayContext = (CommerceOrderContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

contextObjects.put("commerceOrderContentDisplayContext", commerceOrderContentDisplayContext);
%>

<liferay-ddm:template-renderer
	className="<%= CommerceOrderContentPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= commerceOrderContentDisplayContext.getDisplayStyle(CommercePortletKeys.COMMERCE_ORDER_CONTENT) %>"
	displayStyleGroupId="<%= commerceOrderContentDisplayContext.getDisplayStyleGroupId(CommercePortletKeys.COMMERCE_ORDER_CONTENT) %>"
	entries="<%= commerceOrderContentDisplayContext.getCommerceOrders() %>"
>
	<div class="container-fluid-1280" id="<portlet:namespace />ordersContainer">
		<div class="commerce-orders-container" id="<portlet:namespace />entriesContainer">
			<commerce-ui:dataset-display
				dataProviderKey="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PLACED_ORDERS %>"
				id="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PLACED_ORDERS %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= renderResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= commerceOrderContentDisplayContext.getPortletURL() %>"
				style="stacked"
			/>
		</div>
	</div>
</liferay-ddm:template-renderer>