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

<%@ include file="/quantity_control/init.jsp" %>

<%
CommerceOrderItem commerceOrderItem = (CommerceOrderItem)request.getAttribute("liferay-commerce-cart:quantity-control:commerceOrderItem");
boolean showInputLabel = (boolean)request.getAttribute("liferay-commerce-cart:quantity-control:showInputLabel");
boolean updateOnChange = (boolean)request.getAttribute("liferay-commerce-cart:quantity-control:updateOnChange");
boolean useSelect = (boolean)request.getAttribute("liferay-commerce-cart:quantity-control:useSelect");

String portletNamespace = PortalUtil.getPortletNamespace(CommercePortletKeys.COMMERCE_CART_CONTENT);
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_commerce_cart_quantity_control_page") + StringPool.UNDERLINE;
%>

<liferay-portlet:actionURL name="/commerce_cart_content/edit_commerce_order_item" portletName="<%= CommercePortletKeys.COMMERCE_CART_CONTENT %>" var="editCommerceOrderItemURL" />

<aui:form action="<%= editCommerceOrderItemURL %>" method="post" name='<%= randomNamespace + "Fm" %>' portletNamespace="<%= portletNamespace %>">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= PortalUtil.getCurrentURL(request) %>" />
	<aui:input name="commerceOrderItemId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderItemId() %>" />
	<aui:input name="quantity" type="hidden" value="<%= commerceOrderItem.getQuantity() %>" />

	<aui:model-context bean="<%= commerceOrderItem %>" model="<%= CommerceOrderItem.class %>" />

	<c:if test="<%= !updateOnChange %>">
		<div class="form-group m-0">
			<div class="input-group">
				<div class="input-group-item input-group-prepend">
	</c:if>

					<liferay-commerce:quantity-input
						CPDefinitionId="<%= commerceOrderItem.getCPDefinitionId() %>"
						name='<%= randomNamespace + "Quantity" %>'
						showLabel="<%= showInputLabel %>"
						useSelect="<%= useSelect %>"
						value="<%= commerceOrderItem.getQuantity() %>"
					/>

	<c:if test="<%= !updateOnChange %>">
				</div>

				<div class="input-group-append input-group-item input-group-item-shrink">
				<aui:button onClick='<%= portletNamespace + randomNamespace + "updateQuantity();" %>' primary="<%= true %>" value="update" />
				</div>
			</div>
		</div>
	</c:if>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<%= portletNamespace + randomNamespace %>updateQuantity',
		() => {
			var A = AUI();

			var form = A.one('#<%= portletNamespace + randomNamespace %>Fm');

			var quantity = form.one(
				'#<%= portletNamespace + randomNamespace %>Quantity'
			);

			form.one('#<%= portletNamespace %>quantity').val(quantity.val());

			submitForm(document.<%= portletNamespace + randomNamespace %>Fm);
		},
		['aui-base']
	);
</aui:script>

<c:if test="<%= updateOnChange %>">
	<aui:script use="aui-base">
		var form = A.one('#<%= portletNamespace + randomNamespace %>Fm');

		form.delegate(
			'change',
			() => {
				<%= portletNamespace + randomNamespace %>updateQuantity();
			},
			'select'
		);
	</aui:script>
</c:if>