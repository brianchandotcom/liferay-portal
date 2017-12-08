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
dataMap.put("cp-instance-id", "");
%>

<aui:button cssClass="btn-lg" data="<%= dataMap %>" name="add-to-wish-list" value="add-to-wish-list" />

<aui:script use="aui-io-request,aui-parse-content,liferay-notification">
	A.one('#<portlet:namespace />add-to-wish-list').on(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			var cpDefinitionId = currentTarget.getAttribute('data-cp-definition-id');

			var cpInstanceId = currentTarget.getAttribute('data-cp-instance-id');

			var productContent = Liferay.component('<portlet:namespace />' + cpDefinitionId + 'ProductContent');

			var ddmFormValues = JSON.stringify(productContent.getFormValues());

			var data = {
				'<%= PortalUtil.getPortletNamespace(CommercePortletKeys.COMMERCE_CART_CONTENT) %>cpDefinitionId': cpDefinitionId,
				'<%= PortalUtil.getPortletNamespace(CommercePortletKeys.COMMERCE_CART_CONTENT) %>ddmFormValues': ddmFormValues,
				'<%= PortalUtil.getPortletNamespace(CommercePortletKeys.COMMERCE_CART_CONTENT) %>cpInstanceId': cpInstanceId,
				'<%= PortalUtil.getPortletNamespace(CommercePortletKeys.COMMERCE_CART_CONTENT) %>quantity': 1,
				'<%= PortalUtil.getPortletNamespace(CommercePortletKeys.COMMERCE_CART_CONTENT) %>type': <%= CommerceCartConstants.TYPE_WISH_LIST %>
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