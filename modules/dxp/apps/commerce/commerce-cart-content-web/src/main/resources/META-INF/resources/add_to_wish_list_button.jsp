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
HashMap<String, String> dataMap = new HashMap<>();
dataMap.put("cp-definition-id", "");
%>

<aui:button cssClass="btn-lg" data="<%= dataMap %>" name="add-to-wishlist" value="add-to-wishlist" />

<aui:script use="aui-io-request,aui-parse-content,liferay-notification">
	A.one('#<portlet:namespace />add-to-wishlist').on(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			var cpDefinitionId = currentTarget.getAttribute('data-cp-definition-id')

			var productContent = Liferay.component('<portlet:namespace />' + cpDefinitionId + 'ProductContent');

			var ddmFormValues = JSON.stringify(productContent.getFormValues());

			var data = {
				'_<%= CommercePortletKeys.COMMERCE_CART_CONTENT %>_cpDefinitionId' : cpDefinitionId ,
				'_<%= CommercePortletKeys.COMMERCE_CART_CONTENT %>_ddmFormValues' : ddmFormValues ,
				'_<%= CommercePortletKeys.COMMERCE_CART_CONTENT %>_quantity' : 1 ,
				'_<%= CommercePortletKeys.COMMERCE_CART_CONTENT %>_type' : <%= CommerceConstants.COMMERCE_CART_TYPE_WISH_LIST %>
			};

			A.io.request(
				'<liferay-portlet:actionURL name="addCommerceCartItem" portletName="<%= CommercePortletKeys.COMMERCE_CART_CONTENT %>" ></liferay-portlet:actionURL>',
				{
					data: data,
					on: {
						success: function(event, id, obj) {
							var response = JSON.parse(obj.response);

							if (response.success) {
								Liferay.fire('commerce:productAddedToWishList', response);
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