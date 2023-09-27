<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<%
BigDecimal quantity = commerceOrderItem.getQuantity();
%>

<aui:form action="<%= editCommerceOrderItemURL %>" method="post" name='<%= randomNamespace + "Fm" %>' portletNamespace="<%= portletNamespace %>">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= PortalUtil.getCurrentURL(request) %>" />
	<aui:input name="commerceOrderItemId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderItemId() %>" />
	<aui:input name="quantity" type="hidden" value="<%= quantity.intValue() %>" />

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
						value="<%= quantity.intValue() %>"
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