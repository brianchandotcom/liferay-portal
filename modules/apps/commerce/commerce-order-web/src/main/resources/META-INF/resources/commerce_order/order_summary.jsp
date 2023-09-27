<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();

CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();
%>

<portlet:actionURL name="/commerce_order/edit_commerce_order" var="editCommerceOrderSummaryActionURL" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCommerceOrderSummaryActionURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="orderSummary" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrder.getCommerceOrderId() %>" />

		<aui:input label="subtotal" name="subtotal" suffix="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" type="text" value="<%= commerceCurrency.round(commerceOrder.getSubtotal()) %>" wrapperCssClass="form-group-item">
			<aui:validator name="min">0</aui:validator>
			<aui:validator name="number" />
		</aui:input>

		<aui:input label="subtotal-discount" name="subtotalDiscountAmount" suffix="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" type="text" value="<%= commerceCurrency.round(commerceOrder.getSubtotalDiscountAmount()) %>" wrapperCssClass="form-group-item">
			<aui:validator name="min">0</aui:validator>
			<aui:validator name="number" />
		</aui:input>

		<aui:input label="total-discount" name="totalDiscountAmount" suffix="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" type="text" value="<%= commerceCurrency.round(commerceOrder.getTotalDiscountAmount()) %>" wrapperCssClass="form-group-item">
			<aui:validator name="min">0</aui:validator>
			<aui:validator name="number" />
		</aui:input>

		<aui:input label="promotion-code" name="couponCode" wrapperCssClass="form-group-item" />

		<aui:input label="tax" name="taxAmount" suffix="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" type="text" value="<%= commerceCurrency.round(commerceOrder.getTaxAmount()) %>" wrapperCssClass="form-group-item">
			<aui:validator name="min">0</aui:validator>
			<aui:validator name="number" />
		</aui:input>

		<aui:input label="delivery" name="shippingAmount" suffix="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" type="text" value="<%= commerceCurrency.round(commerceOrder.getShippingAmount()) %>" wrapperCssClass="form-group-item">
			<aui:validator name="min">0</aui:validator>
			<aui:validator name="number" />
		</aui:input>

		<aui:input label="delivery-discount" name="shippingDiscountAmount" suffix="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" type="text" value="<%= commerceCurrency.round(commerceOrder.getShippingDiscountAmount()) %>" wrapperCssClass="form-group-item">
			<aui:validator name="min">0</aui:validator>
			<aui:validator name="number" />
		</aui:input>

		<aui:input label="total" name="total" suffix="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" type="text" value="<%= commerceCurrency.round(commerceOrder.getTotal()) %>" wrapperCssClass="form-group-item">
			<aui:validator name="min">0</aui:validator>
			<aui:validator name="number" />
		</aui:input>
	</aui:form>
</commerce-ui:modal-content>