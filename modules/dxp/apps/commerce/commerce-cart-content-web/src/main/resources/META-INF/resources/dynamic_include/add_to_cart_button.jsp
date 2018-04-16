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
CPDefinition cpDefinition = (CPDefinition)request.getAttribute("cpDefinition");
CPInstance cpInstance = (CPInstance)request.getAttribute("cpInstance");

long cpInstanceId = 0;

if (cpInstance != null) {
	cpInstanceId = cpInstance.getCPInstanceId();
}

String productContentId = renderResponse.getNamespace() + cpDefinition.getCPDefinitionId() + "ProductContent";
String quantityInputId = renderResponse.getNamespace() + cpDefinition.getCPDefinitionId() + "Quantity";
%>

<liferay-commerce:quantity-input CPDefinitionId="<%= cpDefinition.getCPDefinitionId() %>" useSelect="<%= true %>" />

<liferay-commerce-cart:add-to-cart
	CPDefinitionId="<%= cpDefinition.getCPDefinitionId() %>"
	CPInstanceId="<%= cpInstanceId %>"
	elementClasses="btn-lg btn-default"
	productContentId='<%= productContentId %>'
	taglibQuantityInputId='<%= quantityInputId %>'
/>