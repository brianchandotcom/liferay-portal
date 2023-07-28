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
			actionLabel='<%= commerceOrderEditDisplayContext.hasManageCommerceOrderPaymentStatusesPermission() ? LanguageUtil.get(request, "edit") : null %>'
			actionTargetId="order-payment-status-modal"
			actionUrl="<%= commerceOrderEditDisplayContext.hasManageCommerceOrderPaymentStatusesPermission() ? editOrderPaymentStatusURL: null %>"
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
			<frontend-data-set:classic-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId())
					).build()
				%>'
				dataProviderKey="<%= CommerceOrderFDSNames.PAYMENTS %>"
				id="<%= CommerceOrderFDSNames.PAYMENTS %>"
				itemsPerPage="<%= 10 %>"
			/>
		</commerce-ui:panel>
	</div>
</div>