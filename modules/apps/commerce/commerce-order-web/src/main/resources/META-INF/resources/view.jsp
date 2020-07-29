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
CommerceOrderListDisplayContext commerceOrderListDisplayContext = (CommerceOrderListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:headless-data-set-display
	apiURL="/o/headless-commerce-admin-order/v1.0/orders?nestedFields=account,channel"
	bulkActionDropdownItems="<%= commerceOrderListDisplayContext.getBulkActionDropdownItems() %>"
	clayDataSetActionDropdownItems="<%= commerceOrderListDisplayContext.getClayDataSetActionDropdownItems() %>"
	formId="fm"
	id="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_ALL_ORDERS %>"
	itemsPerPage="<%= 20 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= commerceOrderListDisplayContext.getPortletURL() %>"
	selectedItemsKey="id"
	selectionType="multiple"
	sortItemList="<%= commerceOrderListDisplayContext.getSortItemList() %>"
	style="fluid"
/>