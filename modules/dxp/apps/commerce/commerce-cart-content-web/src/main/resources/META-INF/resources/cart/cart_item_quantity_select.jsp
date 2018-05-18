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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceOrderItem commerceOrderItem = (CommerceOrderItem)row.getObject();
%>

<portlet:actionURL name="editCommerceOrderItem" var="editCommerceOrderItemURL" />

<aui:form action="<%= editCommerceOrderItemURL %>" method="post" name='<%= commerceOrderItem.getCommerceOrderItemId() + "fm" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceOrderItemId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderItemId() %>" />
	<aui:input name="quantity" type="hidden" value="<%= commerceOrderItem.getQuantity() %>" />

	<aui:model-context bean="<%= commerceOrderItem %>" model="<%= CommerceOrderItem.class %>" />

	<liferay-commerce:quantity-input CPDefinitionId="<%= commerceOrderItem.getCPDefinitionId() %>" value="<%= commerceOrderItem.getQuantity() %>" />
</aui:form>

<aui:script use="aui-base">
	var form = A.one('#<portlet:namespace /><%= commerceOrderItem.getCommerceOrderItemId() + "fm" %>');

	form.delegate(
		'change',
		function() {
			var quantity = form.one('#<portlet:namespace /><%= commerceOrderItem.getCPDefinitionId() + "Quantity" %>')

			form.one('#<portlet:namespace />quantity').val(quantity.val());

			submitForm(document.<portlet:namespace /><%= commerceOrderItem.getCommerceOrderItemId() + "fm" %>);
		},
		'select'
	);
</aui:script>