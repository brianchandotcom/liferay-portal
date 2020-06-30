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

String datasetDisplayKey = commerceOrderListDisplayContext.getDatasetDisplayKey();

Map<String, String> contextParams = new HashMap<>();

contextParams.put("activeTab", commerceOrderListDisplayContext.getActiveTab());
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= commerceOrderListDisplayContext.getNavigationItems() %>"
/>

<commerce-ui:dataset-display
	contextParams="<%= contextParams %>"
	dataProviderKey="<%= datasetDisplayKey %>"
	id="<%= datasetDisplayKey %>"
	itemsPerPage="<%= 20 %>"
	namespace="<%= renderResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= commerceOrderListDisplayContext.getPortletURL() %>"
	style="fluid"
/>