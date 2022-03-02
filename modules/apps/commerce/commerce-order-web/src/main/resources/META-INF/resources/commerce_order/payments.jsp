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
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();
%>

<div class="row">
	<div class="col-md-6 d-flex">
		<liferay-portlet:renderURL var="editOrderPaymentMethodURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_order_payment_method" />
			<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
		</liferay-portlet:renderURL>

		<commerce-ui:modal
			id="order-payment-method-modal"
			refreshPageOnClose="<%= true %>"
			size="lg"
			title='<%= LanguageUtil.format(request, Validator.isNull(commerceOrder.getCommercePaymentMethodKey()) ? "add-x" : "edit-x", "payment-method", true) %>'
			url="<%= editOrderPaymentMethodURL %>"
		/>

		<commerce-ui:panel
			actionLabel='<%= commerceOrderEditDisplayContext.hasManageCommerceOrderPaymentMethodsPermission() ? LanguageUtil.get(request, Validator.isNull(commerceOrder.getCommercePaymentMethodKey()) ? "add" : "edit") : null %>'
			actionTargetId="order-payment-method-modal"
			actionUrl="<%= commerceOrderEditDisplayContext.hasManageCommerceOrderPaymentMethodsPermission() ? editOrderPaymentMethodURL : null %>"
			elementClasses="flex-fill"
			title='<%= LanguageUtil.get(request, "payment-method") %>'
		>

			<%
			String commerceOrderPaymentMethodDescription = commerceOrderEditDisplayContext.getCommerceOrderPaymentMethodDescription();
			String commerceOrderPaymentMethodName = commerceOrderEditDisplayContext.getCommerceOrderPaymentMethodName();
			%>

			<c:choose>
				<c:when test="<%= Validator.isNull(commerceOrderPaymentMethodName) %>">
					<div class="align-items-center d-flex payment-info">
						<span class="text-muted">
							<liferay-ui:message key="click-add-to-insert" />
						</span>
					</div>
				</c:when>
				<c:otherwise>
					<div class="align-items-center d-flex payment-info">
						<liferay-ui:icon
							iconCssClass="icon-info-sign"
							message="<%= HtmlUtil.escape(commerceOrderPaymentMethodDescription) %>"
						/>

						<span class="ml-3 payment-name">
							<%= HtmlUtil.escape(commerceOrderPaymentMethodName) %>
						</span>
					</div>
				</c:otherwise>
			</c:choose>
		</commerce-ui:panel>
	</div>

	<div class="col-md-6 d-flex">
		<liferay-portlet:renderURL var="editOrderPaymentStatusURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_order_payment_status" />
			<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
		</liferay-portlet:renderURL>

		<commerce-ui:modal
			id="order-payment-status-modal"
			refreshPageOnClose="<%= true %>"
			size="lg"
			title='<%= LanguageUtil.format(request, "edit-x", "payment-status", true) %>'
			url="<%= editOrderPaymentStatusURL %>"
		/>

		<commerce-ui:panel
			actionLabel='<%= LanguageUtil.get(request, "edit") %>'
			actionTargetId="order-payment-status-modal"
			actionUrl="<%= editOrderPaymentStatusURL %>"
			elementClasses="flex-fill"
			title='<%= LanguageUtil.get(request, "payment-status") %>'
		>
			<div class="row">
				<div class="col d-flex">
					<clay:label
						cssClass="align-self-center"
						displayType="<%= CommerceOrderPaymentConstants.getOrderPaymentLabelStyle(commerceOrder.getPaymentStatus()) %>"
						label="<%= LanguageUtil.get(request, CommerceOrderPaymentConstants.getOrderPaymentStatusLabel(commerceOrder.getPaymentStatus())) %>"
						large="<%= true %>"
					/>
				</div>
			</div>
		</commerce-ui:panel>
	</div>

	<div class="col-12">
		<commerce-ui:panel
			bodyClasses="p-0"
			elementClasses="flex-fill"
			title='<%= LanguageUtil.get(request, "transaction-history") %>'
		>
			<clay:data-set-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId())
					).build()
				%>'
				dataProviderKey="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PAYMENTS %>"
				id="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PAYMENTS %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= commerceOrderEditDisplayContext.getCommerceOrderPaymentsPortletURL() %>"
			/>
		</commerce-ui:panel>
	</div>
</div>