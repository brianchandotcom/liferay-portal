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
CPInstance cpInstance = (CPInstance)request.getAttribute("cpInstance");

boolean disabled = !cpDefinition.isIgnoreSKUCombinations();

long cpInstanceId = 0;

if (cpInstance != null) {
	cpInstanceId = cpInstance.getCPInstanceId();

	disabled = false;
}

String buttonId = cpDefinition.getCPDefinitionId() + "addToCart";

HashMap<String, String> dataMap = new HashMap<>();

dataMap.put("cp-instance-id", String.valueOf(cpInstanceId));
%>

<liferay-commerce:quantity-input CPDefinitionId="<%= cpDefinition.getCPDefinitionId() %>" useSelect="<%= true %>" />

<aui:button cssClass="btn-lg" data="<%= dataMap %>" disabled="<%= disabled %>" name="<%= buttonId %>" value="add-to-cart" />

<aui:script use="aui-io-request,aui-parse-content,liferay-notification">

	<%
	if (!cpDefinition.isIgnoreSKUCombinations()) {
	%>

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

	<%
	}
	%>

	Liferay.on(
		'<%= cpDefinition.getCPDefinitionId() %>AddToCart',
		function(cpInstanceId) {
			var cpDefinitionId = <%= cpDefinition.getCPDefinitionId() %>;

			var addToCartNode = A.one('#<portlet:namespace /><%= buttonId %>');

			var cpInstanceId = addToCartNode.getAttribute('data-cp-instance-id');

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

			if (!cpInstanceId) {
				new Liferay.Notification(
					{
						message: '<liferay-ui:message key="please-select-a-valid-product" />',
						render: true,
						title: '<liferay-ui:message key="danger" />',
						type: 'danger'
					}
				);
			}

			var data = {
				'<%= PortalUtil.getPortletNamespace(CommercePortletKeys.COMMERCE_CART_CONTENT) %>cpDefinitionId': cpDefinitionId,
				'<%= PortalUtil.getPortletNamespace(CommercePortletKeys.COMMERCE_CART_CONTENT) %>ddmFormValues': ddmFormValues,
				'<%= PortalUtil.getPortletNamespace(CommercePortletKeys.COMMERCE_CART_CONTENT) %>cpInstanceId': cpInstanceId,
				'<%= PortalUtil.getPortletNamespace(CommercePortletKeys.COMMERCE_CART_CONTENT) %>quantity': quantity
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
								var validatorErrors = response.validatorErrors;

								if (validatorErrors) {
									A.Array.each(
										validatorErrors,
										function(item, index, validatorErrors) {
											new Liferay.Notification(
												{
													message: Liferay.Language.get(item.message),
													render: true,
													title: '',
													type: 'danger'
												}
											);
										}
									);
								}
								else {
									new Liferay.Notification(
										{
											message: Liferay.Language.get(response.error),
											render: true,
											title: '',
											type: 'danger'
										}
									);
								}
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