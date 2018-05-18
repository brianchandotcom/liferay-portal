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