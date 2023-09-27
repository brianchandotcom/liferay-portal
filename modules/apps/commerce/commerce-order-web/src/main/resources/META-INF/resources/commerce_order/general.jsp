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
				spritemap="<%= themeDisplay.getPathThemeSpritemap() %>"
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
					AccountEntry accountEntry = commerceOrder.getAccountEntry();
					%>

					<commerce-ui:info-box
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "account-info") %>'
					>
						<c:choose>
							<c:when test="<%= Validator.isNull(accountEntry) %>">
								<span class="text-muted">
									<%= StringPool.BLANK %>
								</span>
							</c:when>
							<c:otherwise>
								<p class="mb-0"><%= accountEntry.getName() %></p>
								<p class="mb-0">#<%= accountEntry.getAccountEntryId() %></p>
							</c:otherwise>
						</c:choose>
					</commerce-ui:info-box>

					<%
					String purchaseOrderNumber = commerceOrder.getPurchaseOrderNumber();
					%>

					<commerce-ui:info-box
						actionLabel='<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? LanguageUtil.get(request, Validator.isNull(purchaseOrderNumber) ? "add" : "edit") : null %>'
						actionTargetId="purchase-order-number-modal"
						actionUrl="<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? editPurchaseOrderNumberURL: null %>"
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
						actionLabel='<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? LanguageUtil.get(request, (billingCommerceAddress == null) ? "add" : "edit") : null %>'
						actionTargetId="billing-address-modal"
						actionUrl="<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? editBillingAddressURL: null %>"
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
									<%= HtmlUtil.escape(billingCommerceAddress.getStreet1()) %>
								</p>

								<c:if test="<%= !Validator.isBlank(billingCommerceAddress.getStreet2()) %>">
									<p class="mb-0">
										<%= HtmlUtil.escape(billingCommerceAddress.getStreet2()) %>
									</p>

									<p class="mb-0">
										<%= HtmlUtil.escape(billingCommerceAddress.getStreet3()) %>
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
						actionLabel='<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? LanguageUtil.get(request, (shippingCommerceAddress == null) ? "add" : "edit") : null %>'
						actionTargetId="shipping-address-modal"
						actionUrl="<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? editShippingAddressURL: null %>"
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
									<%= HtmlUtil.escape(shippingCommerceAddress.getStreet1()) %>
								</p>

								<c:if test="<%= !Validator.isBlank(shippingCommerceAddress.getStreet2()) %>">
									<p class="mb-0">
										<%= HtmlUtil.escape(shippingCommerceAddress.getStreet2()) %>
									</p>

									<p class="mb-0">
										<%= HtmlUtil.escape(shippingCommerceAddress.getStreet3()) %>
									</p>
								</c:if>

								<p class="mb-0">
									<%= commerceOrderEditDisplayContext.getDescriptiveAddress(shippingCommerceAddress) %>
								</p>
							</c:otherwise>
						</c:choose>
					</commerce-ui:info-box>

					<commerce-ui:info-box
						actionLabel='<%= commerceOrderEditDisplayContext.hasManageCommerceOrderPaymentTermsPermission() ? LanguageUtil.get(request, (commerceOrder.getPaymentCommerceTermEntryId() == 0) ? "add" : "edit") : null %>'
						actionTargetId="payment-terms-modal"
						actionUrl="<%= commerceOrderEditDisplayContext.hasManageCommerceOrderPaymentTermsPermission() ? editPaymentTermsURL : null %>"
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
						actionLabel='<%= commerceOrderEditDisplayContext.hasManageCommerceOrderDeliveryTermsPermission() ? LanguageUtil.get(request, (commerceOrder.getDeliveryCommerceTermEntryId() == 0) ? "add" : "edit") : null %>'
						actionTargetId="delivery-terms-modal"
						actionUrl="<%= commerceOrderEditDisplayContext.hasManageCommerceOrderDeliveryTermsPermission() ? editDeliveryTermsURL : null %>"
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
						actionLabel='<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? LanguageUtil.get(request, (requestedDeliveryDate == null) ? "add" : "edit") : null %>'
						actionTargetId="requested-delivery-date-modal"
						actionUrl="<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? editRequestedDeliveryDateURL: null %>"
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
						actionLabel='<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? LanguageUtil.get(request, Validator.isNull(printedNote) ? "add" : "edit") : null %>'
						actionTargetId="printed-note-modal"
						actionUrl="<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? editPrintedNoteURL: null %>"
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
			<frontend-data-set:classic-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId())
					).build()
				%>'
				dataProviderKey="<%= CommerceOrderFDSNames.ORDER_ITEMS %>"
				id="<%= CommerceOrderFDSNames.ORDER_ITEMS %>"
				itemsPerPage="<%= 10 %>"
				nestedItemsKey="orderItemId"
				nestedItemsReferenceKey="orderItems"
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
			actionLabel='<%= commerceOrderEditDisplayContext.hasManageCommerceOrderPricesPermission() ? LanguageUtil.get(request, "edit") : null %>'
			actionTargetId="order-summary-modal"
			actionUrl="<%= commerceOrderEditDisplayContext.hasManageCommerceOrderPricesPermission() ? editOrderSummaryURL : null %>"
			title='<%= LanguageUtil.get(request, "order-summary") %>'
		>
			<div id="summary-root"></div>

			<aui:script require="commerce-frontend-js/components/summary/entry as summary">
				summary.default('summary', 'summary-root', {
					apiUrl:
						'/o/headless-commerce-admin-order/v1.0/orders/<%= commerceOrderEditDisplayContext.getCommerceOrderId() %>',
					dataSetDisplayId: '<%= CommerceOrderFDSNames.ORDER_ITEMS %>',
					portletId: '<%= portletDisplay.getRootPortletId() %>',
				});
			</aui:script>
		</commerce-ui:panel>
	</div>
</div>