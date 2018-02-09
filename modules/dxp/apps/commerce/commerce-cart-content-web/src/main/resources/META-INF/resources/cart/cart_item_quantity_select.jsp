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

CommerceCartItem commerceCartItem = (CommerceCartItem)row.getObject();
%>

<portlet:actionURL name="editCommerceCartItem" var="editCommerceCartItemURL" />

<aui:form action="<%= editCommerceCartItemURL %>" method="post" name='<%= commerceCartItem.getCommerceCartItemId() + "fm" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceCartItemId" type="hidden" value="<%= commerceCartItem.getCommerceCartItemId() %>" />
	<aui:input name="quantity" type="hidden" value="<%= commerceCartItem.getQuantity() %>" />

	<aui:model-context bean="<%= commerceCartItem %>" model="<%= CommerceCartItem.class %>" />

	<liferay-commerce:quantity-input CPDefinitionId="<%= commerceCartItem.getCPDefinitionId() %>" value="<%= commerceCartItem.getQuantity() %>" />
</aui:form>

<aui:script use="aui-base">
	var form = A.one('#<portlet:namespace /><%= commerceCartItem.getCommerceCartItemId() + "fm" %>');

	form.delegate(
		'change',
		function() {
			var quantity = form.one('#<portlet:namespace /><%= commerceCartItem.getCPDefinitionId() + "Quantity" %>')

			form.one('#<portlet:namespace />quantity').val(quantity.val());

			submitForm(document.<portlet:namespace /><%= commerceCartItem.getCommerceCartItemId() + "fm" %>);
		},
		'select'
	);
</aui:script>