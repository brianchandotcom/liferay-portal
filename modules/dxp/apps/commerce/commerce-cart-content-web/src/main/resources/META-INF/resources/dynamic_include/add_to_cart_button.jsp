<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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