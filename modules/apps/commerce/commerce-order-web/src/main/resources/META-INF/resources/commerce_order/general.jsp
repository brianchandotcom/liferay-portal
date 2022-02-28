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

<liferay-portlet:renderURL var="editBillingAddressURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_order_billing_address" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="selectBillingAddressURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order/select_commerce_order_billing_address" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:modal
	id="billing-address-modal"
	refreshPageOnClose="<%= true %>"
	size="lg"
	url="<%= commerceOrder.isOpen() ? selectBillingAddressURL : editBillingAddressURL %>"
/>

<liferay-portlet:renderURL var="editShippingAddressURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_order_shipping_address" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="selectShippingAddressURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order/select_commerce_order_shipping_address" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:modal
	id="shipping-address-modal"
	refreshPageOnClose="<%= true %>"
	size="lg"
	url="<%= commerceOrder.isOpen() ? selectShippingAddressURL : editShippingAddressURL %>"
/>

<liferay-portlet:renderURL var="editPurchaseOrderNumberURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_order_purchase_order_number" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:modal
	id="purchase-order-number-modal"
	refreshPageOnClose="<%= true %>"
	size="lg"
	title='<%= LanguageUtil.get(request, "purchase-order-number") %>'
	url="<%= editPurchaseOrderNumberURL %>"
/>

<liferay-portlet:renderURL var="editRequestedDeliveryDateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_order_requested_delivery_date" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:modal
	id="requested-delivery-date-modal"
	refreshPageOnClose="<%= true %>"
	size="lg"
	title='<%= LanguageUtil.get(request, "requested-delivery-date") %>'
	url="<%= editRequestedDeliveryDateURL %>"
/>

<liferay-portlet:renderURL var="editPrintedNoteURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_order_printed_note" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:modal
	id="printed-note-modal"
	refreshPageOnClose="<%= true %>"
	size="lg"
	title='<%= LanguageUtil.get(request, "printed-note") %>'
	url="<%= editPrintedNoteURL %>"
/>

<liferay-portlet:renderURL var="editPaymentTermsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_order_payment_terms" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:modal
	id="payment-terms-modal"
	refreshPageOnClose="<%= true %>"
	size="xl"
	url="<%= editPaymentTermsURL %>"
/>

<liferay-portlet:renderURL var="editDeliveryTermsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_order_delivery_terms" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:modal
	id="delivery-terms-modal"
	refreshPageOnClose="<%= true %>"
	size="xl"
	url="<%= editDeliveryTermsURL %>"
/>

<div class="row">
	<c:if test="<%= !commerceOrder.isOpen() %>">
		<div class="col-12 mb-4">
			<commerce-ui:step-tracker
				spritemap='<%= themeDisplay.getPathThemeImages() + "/clay/icons.svg" %>'
				steps="<%= commerceOrderEditDisplayContext.getOrderSteps() %>"
			/>
		</div>
	</c:if>

	<div class="col-12">
		<commerce-ui:panel
			elementClasses="flex-fill"
			title='<%= LanguageUtil.get(request, "details") %>'
		>
			<div class="row vertically-divided">
				<div class="col-xl-3">

					<%
					CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();
					%>

					<commerce-ui:info-box
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "account-info") %>'
					>
						<c:choose>
							<c:when test="<%= Validator.isNull(commerceAccount) %>">
								<span class="text-muted">
									<%= StringPool.BLANK %>
								</span>
							</c:when>
							<c:otherwise>
								<p class="mb-0"><%= commerceAccount.getName() %></p>
								<p class="mb-0">#<%= commerceAccount.getCommerceAccountId() %></p>
							</c:otherwise>
						</c:choose>
					</commerce-ui:info-box>

					<%
					String purchaseOrderNumber = commerceOrder.getPurchaseOrderNumber();
					%>

					<commerce-ui:info-box
						actionLabel='<%= LanguageUtil.get(request, Validator.isNull(purchaseOrderNumber) ? "add" : "edit") %>'
						actionTargetId="purchase-order-number-modal"
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "purchase-order-number") %>'
					>
						<c:choose>
							<c:when test="<%= Validator.isNull(purchaseOrderNumber) %>">
								<span class="text-muted">
									<liferay-ui:message key="click-add-to-insert" />
								</span>
							</c:when>
							<c:otherwise>
								<%= HtmlUtil.escape(purchaseOrderNumber) %>
							</c:otherwise>
						</c:choose>
					</commerce-ui:info-box>

					<commerce-ui:info-box
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "channel") %>'
					>
						<%= HtmlUtil.escape(commerceOrderEditDisplayContext.getCommerceChannelName()) %>
					</commerce-ui:info-box>
				</div>

				<div class="col-xl-3">

					<%
					CommerceAddress billingCommerceAddress = commerceOrder.getBillingAddress();
					%>

					<commerce-ui:info-box
						actionLabel='<%= LanguageUtil.get(request, (billingCommerceAddress == null) ? "add" : "edit") %>'
						actionTargetId="billing-address-modal"
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "billing-address") %>'
					>
						<c:choose>
							<c:when test="<%= billingCommerceAddress == null %>">
								<span class="text-muted">
									<liferay-ui:message key="click-add-to-insert" />
								</span>
							</c:when>
							<c:otherwise>
								<p class="mb-0">
									<%= billingCommerceAddress.getStreet1() %>
								</p>

								<c:if test="<%= !Validator.isBlank(billingCommerceAddress.getStreet2()) %>">
									<p class="mb-0">
										<%= billingCommerceAddress.getStreet2() %>
									</p>

									<p class="mb-0">
										<%= billingCommerceAddress.getStreet3() %>
									</p>
								</c:if>

								<p class="mb-0">
									<%= commerceOrderEditDisplayContext.getDescriptiveAddress(billingCommerceAddress) %>
								</p>
							</c:otherwise>
						</c:choose>
					</commerce-ui:info-box>

					<%
					CommerceAddress shippingCommerceAddress = commerceOrder.getShippingAddress();
					%>

					<commerce-ui:info-box
						actionLabel='<%= LanguageUtil.get(request, (shippingCommerceAddress == null) ? "add" : "edit") %>'
						actionTargetId="shipping-address-modal"
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "shipping-address") %>'
					>
						<c:choose>
							<c:when test="<%= shippingCommerceAddress == null %>">
								<span class="text-muted">
									<liferay-ui:message key="click-add-to-insert" />
								</span>
							</c:when>
							<c:otherwise>
								<p class="mb-0">
									<%= shippingCommerceAddress.getStreet1() %>
								</p>

								<c:if test="<%= !Validator.isBlank(shippingCommerceAddress.getStreet2()) %>">
									<p class="mb-0">
										<%= shippingCommerceAddress.getStreet2() %>
									</p>

									<p class="mb-0">
										<%= shippingCommerceAddress.getStreet3() %>
									</p>
								</c:if>

								<p class="mb-0">
									<%= commerceOrderEditDisplayContext.getDescriptiveAddress(shippingCommerceAddress) %>
								</p>
							</c:otherwise>
						</c:choose>
					</commerce-ui:info-box>

					<commerce-ui:info-box
						actionLabel='<%= LanguageUtil.get(request, (commerceOrder.getPaymentCommerceTermEntryId() == 0) ? "add" : "edit") %>'
						actionTargetId="payment-terms-modal"
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "payment-terms") %>'
					>
						<c:choose>
							<c:when test="<%= commerceOrder.getPaymentCommerceTermEntryId() == 0 %>">
								<span class="text-muted">
									<liferay-ui:message key="click-add-to-insert" />
								</span>
							</c:when>
							<c:otherwise>
								<p class="mb-0">
									<%= commerceOrder.getPaymentCommerceTermEntryName() %>
								</p>
							</c:otherwise>
						</c:choose>
					</commerce-ui:info-box>

					<commerce-ui:info-box
						actionLabel='<%= LanguageUtil.get(request, (commerceOrder.getDeliveryCommerceTermEntryId() == 0) ? "add" : "edit") %>'
						actionTargetId="delivery-terms-modal"
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "delivery-terms") %>'
					>
						<c:choose>
							<c:when test="<%= commerceOrder.getDeliveryCommerceTermEntryId() == 0 %>">
								<span class="text-muted">
									<liferay-ui:message key="click-add-to-insert" />
								</span>
							</c:when>
							<c:otherwise>
								<p class="mb-0">
									<%= commerceOrder.getDeliveryCommerceTermEntryName() %>
								</p>
							</c:otherwise>
						</c:choose>
					</commerce-ui:info-box>
				</div>

				<%
				String printedNote = commerceOrder.getPrintedNote();
				%>

				<div class="col-xl-3">
					<c:if test="<%= commerceOrder.getOrderDate() != null %>">
						<commerce-ui:info-box
							elementClasses="py-3"
							title='<%= LanguageUtil.get(request, "order-date") %>'
						>
							<%= commerceOrderEditDisplayContext.getCommerceOrderDateTime(commerceOrder.getOrderDate()) %>
						</commerce-ui:info-box>
					</c:if>

					<%
					Date requestedDeliveryDate = commerceOrder.getRequestedDeliveryDate();
					%>

					<commerce-ui:info-box
						actionLabel='<%= LanguageUtil.get(request, (requestedDeliveryDate == null) ? "add" : "edit") %>'
						actionTargetId="requested-delivery-date-modal"
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "requested-delivery-date") %>'
					>
						<c:choose>
							<c:when test="<%= requestedDeliveryDate == null %>">
								<span class="text-muted">
									<liferay-ui:message key="click-add-to-insert" />
								</span>
							</c:when>
							<c:otherwise>
								<%= commerceOrderEditDisplayContext.getCommerceOrderDateTime(requestedDeliveryDate) %>
							</c:otherwise>
						</c:choose>
					</commerce-ui:info-box>

					<commerce-ui:info-box
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "order-type") %>'
					>
						<%= HtmlUtil.escape(commerceOrderEditDisplayContext.getCommerceOrderTypeName(LanguageUtil.getLanguageId(locale))) %>
					</commerce-ui:info-box>
				</div>

				<div class="col-xl-3">
					<commerce-ui:info-box
						actionLabel='<%= LanguageUtil.get(request, Validator.isNull(printedNote) ? "add" : "edit") %>'
						actionTargetId="printed-note-modal"
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "printed-note") %>'
					>
						<c:choose>
							<c:when test="<%= Validator.isNull(printedNote) %>">
								<span class="text-muted">
									<liferay-ui:message key="click-add-to-insert" />
								</span>
							</c:when>
							<c:otherwise>
								<%= HtmlUtil.escape(printedNote) %>
							</c:otherwise>
						</c:choose>
					</commerce-ui:info-box>

					<c:if test="<%= Validator.isNotNull(commerceOrder.getAdvanceStatus()) %>">
						<commerce-ui:info-box
							elementClasses="py-3"
							title='<%= LanguageUtil.get(request, "external-order-status") %>'
						>
							<%= HtmlUtil.escape(commerceOrder.getAdvanceStatus()) %>
						</commerce-ui:info-box>
					</c:if>
				</div>
			</div>
		</commerce-ui:panel>
	</div>

	<div class="col-12">
		<commerce-ui:panel
			bodyClasses="p-0"
			title='<%= LanguageUtil.get(request, "items") %>'
		>
			<clay:data-set-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId())
					).build()
				%>'
				dataProviderKey="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_ORDER_ITEMS %>"
				id="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_ORDER_ITEMS %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				nestedItemsKey="orderItemId"
				nestedItemsReferenceKey="orderItems"
				pageNumber="<%= 1 %>"
				portletURL="<%= commerceOrderEditDisplayContext.getCommerceOrderItemsPortletURL() %>"
			/>
		</commerce-ui:panel>
	</div>

	<div class="col-12">
		<liferay-portlet:renderURL var="editOrderSummaryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_order_summary" />
			<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
		</liferay-portlet:renderURL>

		<commerce-ui:modal
			id="order-summary-modal"
			refreshPageOnClose="<%= true %>"
			size="lg"
			title='<%= LanguageUtil.get(request, "order-summary") %>'
			url="<%= editOrderSummaryURL %>"
		/>

		<commerce-ui:panel
			actionLabel='<%= LanguageUtil.get(request, "edit") %>'
			actionTargetId="order-summary-modal"
			actionUrl="<%= editOrderSummaryURL %>"
			title='<%= LanguageUtil.get(request, "order-summary") %>'
		>
			<div id="summary-root"></div>

			<aui:script require="commerce-frontend-js/components/summary/entry as summary">
				summary.default('summary', 'summary-root', {
					apiUrl:
						'/o/headless-commerce-admin-order/v1.0/orders/<%= commerceOrderEditDisplayContext.getCommerceOrderId() %>',
					datasetDisplayId:
						'<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_ORDER_ITEMS %>',
					portletId: '<%= portletDisplay.getRootPortletId() %>',
				});
			</aui:script>
		</commerce-ui:panel>
	</div>
</div>