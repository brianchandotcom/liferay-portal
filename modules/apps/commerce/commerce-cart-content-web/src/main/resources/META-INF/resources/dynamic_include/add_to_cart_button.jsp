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
CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);
CPInstance cpInstance = cpContentHelper.getDefaultCPInstance(request);

long cpInstanceId = 0;

if (cpInstance != null) {
	cpInstanceId = cpInstance.getCPInstanceId();
}

String productContentId = liferayPortletResponse.getNamespace() + cpCatalogEntry.getCPDefinitionId() + "ProductContent";
String quantityInputId = liferayPortletResponse.getNamespace() + cpCatalogEntry.getCPDefinitionId() + "Quantity";
%>

<liferay-commerce:quantity-input CPDefinitionId="<%= cpCatalogEntry.getCPDefinitionId() %>" useSelect="<%= true %>" />

<liferay-commerce-cart:add-to-cart
	CPDefinitionId="<%= cpCatalogEntry.getCPDefinitionId() %>"
	CPInstanceId="<%= cpInstanceId %>"
	elementClasses="btn-lg btn-secondary"
	productContentId='<%= productContentId %>'
	taglibQuantityInputId='<%= quantityInputId %>'
/>