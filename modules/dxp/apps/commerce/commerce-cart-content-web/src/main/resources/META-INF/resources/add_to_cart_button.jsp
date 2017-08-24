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

<aui:button cssClass="btn-lg btn-primary" name="add-to-cart" value="add-to-cart" />

<aui:script use="aui-io-request,aui-parse-content,liferay-notification">
	A.one('#<portlet:namespace />add-to-cart').on(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			var productDetail = currentTarget.ancestor(".product-detail");

			var cpDefinitionId = productDetail.attr("data-cp-definition-id");

			var data = {
				'_<%= CommercePortletKeys.COMMERCE_CART_CONTENT %>_cpDefinitionId' : cpDefinitionId ,
				'_<%= CommercePortletKeys.COMMERCE_CART_CONTENT %>_quantity' : 1
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
</aui:script>