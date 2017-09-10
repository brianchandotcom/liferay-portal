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

String buttonId = cpDefinition.getCPDefinitionId() + "addToCart";
%>

<liferay-commerce:quantity-input CPDefinitionId="<%= cpDefinition.getCPDefinitionId() %>" useSelect="<%= true %>" />

<aui:button cssClass="btn-lg btn-primary" disabled="<%= !cpDefinition.getCanSellWithoutOptionsCombination() %>" name="<%= buttonId %>" value="add-to-cart" />

<aui:script use="aui-io-request,aui-parse-content,liferay-notification">
	<% if(!cpDefinition.getCanSellWithoutOptionsCombination()){ %>

	Liferay.on(
		'<%= cpDefinition.getCPDefinitionId() %>CPInstance:change',
		function(event) {

			var cartButton = A.one('#<portlet:namespace /><%= buttonId %>');

			if (event.cpInstanceExist) {
				Liferay.Util.toggleDisabled(cartButton, false);
			}
			else {
				Liferay.Util.toggleDisabled(cartButton, true);
			}
		}
	);
	<% } %>

	Liferay.on(
		'<%= cpDefinition.getCPDefinitionId() %>AddToCart',
		function(event) {

			var cpDefinitionId = <%= cpDefinition.getCPDefinitionId() %>;

			var productContent = Liferay.component('<portlet:namespace />' + cpDefinitionId + 'ProductContent');

			var quantityNode = A.one('#<portlet:namespace /><%= cpDefinition.getCPDefinitionId() + "Quantity" %>');

			var quantity = "1";
			var ddmFormValues = "[]";

			if (quantityNode) {
				quantity = quantityNode.val();
			}

			if (productContent) {
				ddmFormValues = JSON.stringify(productContent.getFormValues());
			}

			var data = {
				'_<%= CommercePortletKeys.COMMERCE_CART_CONTENT %>_cpDefinitionId' : cpDefinitionId ,
				'_<%= CommercePortletKeys.COMMERCE_CART_CONTENT %>_ddmFormValues' : ddmFormValues ,
				'_<%= CommercePortletKeys.COMMERCE_CART_CONTENT %>_quantity' : quantity
			};

			A.io.request(
				'<liferay-portlet:actionURL name="addCommerceCartItem" portletName="<%= CommercePortletKeys.COMMERCE_CART_CONTENT %>" ></liferay-portlet:actionURL>',
				{
					data: data,
					on: {
						success: function(event, id, obj) {
							var response = JSON.parse(obj.response);

							if (response.success) {
								Liferay.fire('commerce:productAddedToCart', response);
							}
							else {
								new Liferay.Notification(
									{
										message: '<liferay-ui:message key="an-unexpected-error-occurred" />',
										render: true,
										title: '<liferay-ui:message key="danger" />',
										type: 'danger'
									}
								);
							}
						}
					}
				}
			);
		}
	);

	A.one('#<portlet:namespace /><%= buttonId %>').on(
		'click',
		function(event) {

			var cpDefinitionId = <%= cpDefinition.getCPDefinitionId() %>;

			var productContent = Liferay.component('<portlet:namespace />' + cpDefinitionId + 'ProductContent');

			if (productContent) {
				productContent.validateProduct(
					function(hasError) {
						if (!hasError) {
							Liferay.fire('<%= cpDefinition.getCPDefinitionId() %>AddToCart');
						}
					}
				);
			}
			else {
				Liferay.fire('<%= cpDefinition.getCPDefinitionId() %>AddToCart');
			}

		}
	);
</aui:script>
