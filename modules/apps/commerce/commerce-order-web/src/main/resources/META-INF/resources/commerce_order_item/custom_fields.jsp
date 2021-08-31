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
<!-- test custom_fields.jsp -->

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrderItem commerceOrderItem = commerceOrderEditDisplayContext.getCommerceOrderItem();
%>

<liferay-portlet:actionURL name="/commerce_order/edit_commerce_order_item" var="editCommerceOrderItemActionURL" />

<commerce-ui:panel
	title='<%= LanguageUtil.get(request, "custom-fields") %>'
>
	<aui:form action="<%= editCommerceOrderItemActionURL %>" method="post" name="fm">
		<aui:fieldset>
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="customFields" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderId() %>" />
			<aui:input name="commerceOrderItemId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderItemId() %>" />
			<aui:input bean="<%= commerceOrderItem %>" name="quantity" type="hidden" />

			<liferay-ui:error-marker
				key="<%= WebKeys.ERROR_SECTION %>"
				value="custom-fields"
			/>

			<aui:model-context bean="<%= commerceOrderItem %>" model="<%= CommerceOrderItem.class %>" />

			<liferay-expando:custom-attribute-list
				className="<%= CommerceOrderItem.class.getName() %>"
				classPK="<%= (commerceOrderItem != null) ? commerceOrderItem.getCommerceOrderItemId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:fieldset>
	</aui:form>
</commerce-ui:panel>