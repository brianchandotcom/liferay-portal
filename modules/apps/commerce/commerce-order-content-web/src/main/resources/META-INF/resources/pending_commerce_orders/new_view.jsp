<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrder commerceOrder = commerceOrderContentDisplayContext.getCommerceOrder();
%>

<liferay-portlet:renderURL var="editBillingAddressURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/edit_commerce_order_billing_address" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderContentDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="selectBillingAddressURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/select_commerce_order_billing_address" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderContentDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:modal
	id="billing-address-modal"
	refreshPageOnClose="<%= true %>"
	size="lg"
	url="<%= commerceOrder.isOpen() ? selectBillingAddressURL : editBillingAddressURL %>"
/>

<liferay-portlet:renderURL var="editShippingAddressURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/edit_commerce_order_shipping_address" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderContentDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="selectShippingAddressURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/select_commerce_order_shipping_address" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderContentDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:modal
	id="shipping-address-modal"
	refreshPageOnClose="<%= true %>"
	size="lg"
	url="<%= commerceOrder.isOpen() ? selectShippingAddressURL : editShippingAddressURL %>"
/>

<liferay-portlet:renderURL var="editPurchaseOrderNumberURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/edit_commerce_order_purchase_order_number" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderContentDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:modal
	id="purchase-order-number-modal"
	refreshPageOnClose="<%= true %>"
	size="lg"
	title='<%= LanguageUtil.get(request, "purchase-order-number") %>'
	url="<%= editPurchaseOrderNumberURL %>"
/>

<liferay-portlet:renderURL var="editRequestedDeliveryDateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/edit_commerce_order_requested_delivery_date" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderContentDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:modal
	id="requested-delivery-date-modal"
	refreshPageOnClose="<%= true %>"
	size="lg"
	title='<%= LanguageUtil.get(request, "requested-delivery-date") %>'
	url="<%= editRequestedDeliveryDateURL %>"
/>

<liferay-portlet:renderURL var="viewPaymentTermsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order_content/view_commerce_order_payment_terms" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderContentDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:modal
	id="payment-terms-modal"
	refreshPageOnClose="<%= true %>"
	size="xl"
	url="<%= viewPaymentTermsURL %>"
/>

<liferay-portlet:renderURL var="viewDeliveryTermsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order_content/view_commerce_order_delivery_terms" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderContentDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:modal
	id="delivery-terms-modal"
	refreshPageOnClose="<%= true %>"
	size="xl"
	url="<%= viewDeliveryTermsURL %>"
/>

<liferay-ui:error embed="<%= false %>" exception="<%= CommerceOrderBillingAddressException.class %>" message="the-order-selected-needs-a-billing-address" />
<liferay-ui:error embed="<%= false %>" exception="<%= CommerceOrderPaymentMethodException.class %>" message="please-select-a-valid-payment-method" />
<liferay-ui:error embed="<%= false %>" exception="<%= CommerceOrderShippingAddressException.class %>" message="the-order-selected-needs-a-shipping-address" />
<liferay-ui:error embed="<%= false %>" exception="<%= CommerceOrderShippingMethodException.class %>" message="please-select-a-valid-shipping-method" />
<liferay-ui:error embed="<%= false %>" exception="<%= CommerceOrderStatusException.class %>" message="this-order-cannot-be-transitioned" />

<div class="row">
	<div class="col-12 mb-4">
		<commerce-ui:header
			actions="<%= commerceOrderContentDisplayContext.getHeaderActionModels() %>"
			bean="<%= commerceOrder %>"
			beanIdLabel="id"
			dropdownItems="<%= commerceOrderContentDisplayContext.getDropdownItems() %>"
			externalReferenceCode="<%= commerceOrder.getExternalReferenceCode() %>"
			model="<%= CommerceOrder.class %>"
			thumbnailUrl="<%= commerceOrderContentDisplayContext.getCommerceAccountThumbnailURL() %>"
			title="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>"
			transitionPortletURL="<%= commerceOrderContentDisplayContext.getTransitionOrderPortletURL() %>"
		/>
	</div>

	<c:if test="<%= !commerceOrder.isOpen() %>">
		<div class="col-12 mb-4">
			<commerce-ui:step-tracker
				spritemap="<%= themeDisplay.getPathThemeSpritemap() %>"
				steps="<%= commerceOrderContentDisplayContext.getOrderSteps() %>"
			/>
		</div>
	</c:if>

	<div class="col-12">
		<commerce-ui:panel
			elementClasses="flex-fill"
			title='<%= LanguageUtil.get(request, "details") %>'
		>
			<div class="row vertically-divided">
				<div class="col-xl-4">

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
						<%= HtmlUtil.escape(commerceOrderContentDisplayContext.fetchCommerceChannel().getName()) %>
					</commerce-ui:info-box>
				</div>

				<div class="col-xl-4">

					<%
					CommerceAddress billingCommerceAddress = commerceOrder.getBillingAddress();
					%>

					<c:if test="<%= commerceOrderContentDisplayContext.hasViewBillingAddressPermission(permissionChecker, accountEntry) %>">
						<commerce-ui:info-box
							actionLabel='<%= commerceOrderContentDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? LanguageUtil.get(request, (billingCommerceAddress == null) ? "add" : "edit") : "" %>'
							actionTargetId='<%= commerceOrderContentDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? "billing-address-modal" : "" %>'
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
									</c:if>

									<c:if test="<%= !Validator.isBlank(billingCommerceAddress.getStreet2()) %>">
										<p class="mb-0">
											<%= billingCommerceAddress.getStreet3() %>
										</p>
									</c:if>

									<p class="mb-0">
										<%= commerceOrderContentDisplayContext.getDescriptiveAddress(billingCommerceAddress) %>
									</p>
								</c:otherwise>
							</c:choose>
						</commerce-ui:info-box>
					</c:if>

					<%
					CommerceAddress shippingCommerceAddress = commerceOrder.getShippingAddress();
					%>

					<commerce-ui:info-box
						actionLabel='<%= commerceOrderContentDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? LanguageUtil.get(request, (shippingCommerceAddress == null) ? "add" : "edit") : "" %>'
						actionTargetId='<%= commerceOrderContentDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? "shipping-address-modal" : "" %>'
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
								</c:if>

								<c:if test="<%= !Validator.isBlank(shippingCommerceAddress.getStreet3()) %>">
									<p class="mb-0">
										<%= shippingCommerceAddress.getStreet3() %>
									</p>
								</c:if>

								<p class="mb-0">
									<%= commerceOrderContentDisplayContext.getDescriptiveAddress(shippingCommerceAddress) %>
								</p>
							</c:otherwise>
						</c:choose>
					</commerce-ui:info-box>

					<commerce-ui:info-box
						actionLabel='<%= (commerceOrderContentDisplayContext.hasManageCommerceOrderPaymentTermsPermission() && (commerceOrder.getPaymentCommerceTermEntryId() > 0)) ? LanguageUtil.get(request, "view") : null %>'
						actionTargetId="payment-terms-modal"
						actionUrl="<%= (commerceOrderContentDisplayContext.hasManageCommerceOrderPaymentTermsPermission() && (commerceOrder.getPaymentCommerceTermEntryId() > 0)) ? viewPaymentTermsURL : null %>"
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "payment-terms") %>'
					>
						<c:if test="<%= commerceOrder.getPaymentCommerceTermEntryId() > 0 %>">
							<p class="mb-0">
								<%= commerceOrder.getPaymentCommerceTermEntryName() %>
							</p>
						</c:if>
					</commerce-ui:info-box>

					<commerce-ui:info-box
						actionLabel='<%= (commerceOrderContentDisplayContext.hasManageCommerceOrderDeliveryTermsPermission() && (commerceOrder.getDeliveryCommerceTermEntryId() > 0)) ? LanguageUtil.get(request, "view") : null %>'
						actionTargetId="delivery-terms-modal"
						actionUrl="<%= (commerceOrderContentDisplayContext.hasManageCommerceOrderDeliveryTermsPermission() && (commerceOrder.getDeliveryCommerceTermEntryId() > 0)) ? viewDeliveryTermsURL : null %>"
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "delivery-terms") %>'
					>
						<c:if test="<%= commerceOrder.getDeliveryCommerceTermEntryId() > 0 %>">
							<p class="mb-0">
								<%= commerceOrder.getDeliveryCommerceTermEntryName() %>
							</p>
						</c:if>
					</commerce-ui:info-box>
				</div>

				<div class="col-xl-4">
					<c:if test="<%= commerceOrder.getOrderDate() != null %>">
						<commerce-ui:info-box
							elementClasses="py-3"
							title='<%= LanguageUtil.get(request, "order-date") %>'
						>
							<%= commerceOrderContentDisplayContext.formatCommerceOrderDate(commerceOrder.getOrderDate()) %>
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
								<%= commerceOrderContentDisplayContext.formatCommerceOrderDate(requestedDeliveryDate) %>
							</c:otherwise>
						</c:choose>
					</commerce-ui:info-box>

					<commerce-ui:info-box
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "order-type") %>'
					>
						<%= HtmlUtil.escape(commerceOrderContentDisplayContext.getCommerceOrderTypeName(LanguageUtil.getLanguageId(locale))) %>
					</commerce-ui:info-box>

					<portlet:renderURL var="editCommerceOrderNotesURL">
						<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/edit_commerce_order_notes" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
					</portlet:renderURL>

					<commerce-ui:info-box
						actionLabel='<%= LanguageUtil.get(request, "edit") %>'
						actionUrl="<%= editCommerceOrderNotesURL %>"
						elementClasses="py-3"
						title='<%= LanguageUtil.get(request, "notes") %>'
					/>
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
				dataProviderKey="<%= CommerceOrderFDSNames.PENDING_ORDER_ITEMS %>"
				id="<%= CommerceOrderFDSNames.PENDING_ORDER_ITEMS %>"
				itemsPerPage="<%= 10 %>"
				nestedItemsKey="orderItemId"
				nestedItemsReferenceKey="orderItems"
			/>
		</commerce-ui:panel>
	</div>

	<div class="col-12">
		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "order-summary") %>'
		>
			<div id="summary-root"></div>

			<aui:script require="commerce-frontend-js/components/summary/entry as summary">
				summary.default('summary', 'summary-root', {
					apiUrl:
						'/o/headless-commerce-admin-order/v1.0/orders/<%= commerceOrderContentDisplayContext.getCommerceOrderId() %>',
					dataSetDisplayId: '<%= CommerceOrderFDSNames.PENDING_ORDER_ITEMS %>',
					portletId: '<%= portletDisplay.getRootPortletId() %>',
				});
			</aui:script>
		</commerce-ui:panel>
	</div>
</div>

<%@ include file="/pending_commerce_orders/request_quote.jspf" %>

<liferay-frontend:component
	module="js/view"
/>