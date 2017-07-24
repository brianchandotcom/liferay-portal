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

<aui:script use="aui-io-request,aui-parse-content">
	A.one('#<portlet:namespace />add-to-cart').on(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			var productDetail = currentTarget.ancestor(".product-detail");

			var cpDefinitionId = productDetail.attr("data-cp-definition-id");

			var data = {
				'_<%= CommerceCartPortletKeys.COMMERCE_CART_CONTENT %>_cpDefinitionId' : cpDefinitionId ,
				'_<%= CommerceCartPortletKeys.COMMERCE_CART_CONTENT %>_quantity' : 1
			};

			A.io.request(
				'<liferay-portlet:resourceURL id="editCommerceCartItem" portletName="<%= CommerceCartPortletKeys.COMMERCE_CART_CONTENT %>" ></liferay-portlet:resourceURL>',
				{
					data: data,
					on: {
						failure: function() {
							alert("failure");
						},
						success: function(event, id, obj) {
							alert("success");
						}
					}
				}
			);
		}
	);
</aui:script>