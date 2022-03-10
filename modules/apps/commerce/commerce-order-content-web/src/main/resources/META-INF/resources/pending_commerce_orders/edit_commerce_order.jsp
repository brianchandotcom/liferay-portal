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
CommerceOrder commerceOrder = commerceOrderContentDisplayContext.getCommerceOrder();

long billingCommerceAddressId = BeanParamUtil.getLong(commerceOrder, request, "billingAddressId");
long shippingCommerceAddressId = BeanParamUtil.getLong(commerceOrder, request, "shippingAddressId");

CommerceAddress billingCommerceAddress = commerceOrder.getBillingAddress();
CommerceAddress shippingCommerceAddress = commerceOrder.getShippingAddress();

CommerceOrderPrice commerceOrderPrice = commerceOrderContentDisplayContext.getCommerceOrderPrice();

CommerceMoney shippingValueCommerceMoney = commerceOrderPrice.getShippingValue();
CommerceDiscountValue shippingCommerceDiscountValue = commerceOrderPrice.getShippingDiscountValue();
CommerceMoney subtotalCommerceMoney = commerceOrderPrice.getSubtotal();
CommerceDiscountValue subtotalCommerceDiscountValue = commerceOrderPrice.getSubtotalDiscountValue();
CommerceMoney taxValueCommerceMoney = commerceOrderPrice.getTaxValue();
CommerceDiscountValue totalCommerceDiscountValue = commerceOrderPrice.getTotalDiscountValue();
CommerceMoney totalOrderCommerceMoney = commerceOrderPrice.getTotal();

String priceDisplayType = commerceOrderContentDisplayContext.getCommercePriceDisplayType();

if (priceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {
	shippingValueCommerceMoney = commerceOrderPrice.getShippingValueWithTaxAmount();
	shippingCommerceDiscountValue = commerceOrderPrice.getShippingDiscountValueWithTaxAmount();
	subtotalCommerceMoney = commerceOrderPrice.getSubtotalWithTaxAmount();
	subtotalCommerceDiscountValue = commerceOrderPrice.getSubtotalDiscountValueWithTaxAmount();
	totalCommerceDiscountValue = commerceOrderPrice.getTotalDiscountValueWithTaxAmount();
	totalOrderCommerceMoney = commerceOrderPrice.getTotalWithTaxAmount();
}

CommerceAccount commerceAccount = commerceOrderContentDisplayContext.getCommerceAccount();

if (commerceOrder != null) {
	commerceAccount = commerceOrder.getCommerceAccount();
}

List<CommerceAddress> shippingAddresses = commerceOrderContentDisplayContext.getShippingCommerceAddresses(commerceAccount.getCommerceAccountId(), commerceAccount.getCompanyId());
List<CommerceAddress> billingAddresses = commerceOrderContentDisplayContext.getBillingCommerceAddresses(commerceAccount.getCommerceAccountId(), commerceAccount.getCompanyId());

List<String> errorMessages = (List<String>)request.getAttribute(CommerceWebKeys.COMMERCE_ORDER_ERROR_MESSAGES);

boolean hasViewBillingAddressPermission = commerceOrderContentDisplayContext.hasViewBillingAddressPermission(permissionChecker, commerceAccount);
%>

<c:if test="<%= (errorMessages != null) && !errorMessages.isEmpty() %>">
	<script>
		Liferay.Util.openModal({
			bodyHTML: '<%= errorMessages.get(0) %>',
			title: '<liferay-ui:message key="warning" />',
			center: true,
			size: 'm',
			status: 'warning',
		});
	</script>
</c:if>

<portlet:actionURL name="/commerce_open_order_content/edit_commerce_order" var="editCommerceOrderActionURL">
	<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/edit_commerce_order" />
</portlet:actionURL>

<aui:form action="<%= editCommerceOrderActionURL %>" cssClass="order-details-container" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceOrderId" type="hidden" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />

	<liferay-ui:error embed="<%= false %>" exception="<%= CommerceOrderImporterTypeException.class %>" message="the-import-process-failed" />

	<liferay-ui:error embed="<%= false %>" exception="<%= CommerceOrderImporterTypeException.class %>">

		<%
		String commerceOrderImporterTypeKey = (String)SessionErrors.get(renderRequest, CommerceOrderImporterTypeException.class);
		%>

		<c:choose>
			<c:when test="<%= Validator.isNull(commerceOrderImporterTypeKey) %>">
				<liferay-ui:message key="the-import-process-failed" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message arguments="<%= commerceOrderImporterTypeKey %>" key="the-x-could-not-be-imported" />
			</c:otherwise>
		</c:choose>
	</liferay-ui:error>

	<liferay-ui:error exception="<%= CommerceOrderValidatorException.class %>">

		<%
		List<CommerceOrderValidatorResult> commerceOrderValidatorResults = new ArrayList<>();

		CommerceOrderValidatorException commerceOrderValidatorException = (CommerceOrderValidatorException)errorException;

		if (commerceOrderValidatorException != null) {
			commerceOrderValidatorResults = commerceOrderValidatorException.getCommerceOrderValidatorResults();
		}

		for (CommerceOrderValidatorResult commerceOrderValidatorResult : commerceOrderValidatorResults) {
		%>

			<liferay-ui:message key="<%= HtmlUtil.escape(commerceOrderValidatorResult.getLocalizedMessage()) %>" />

		<%
		}
		%>

	</liferay-ui:error>

	<liferay-ui:error embed="<%= false %>" key="notImportedRowsCount">

		<%
		int notImportedRowsCount = (int)SessionErrors.get(renderRequest, "notImportedRowsCount");
		%>

		<c:choose>
			<c:when test="<%= notImportedRowsCount > 1 %>">
				<liferay-ui:message arguments="<%= notImportedRowsCount %>" key="x-rows-were-not-imported" translateArguments="<%= false %>" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="1-row-was-not-imported" />
			</c:otherwise>
		</c:choose>
	</liferay-ui:error>

	<liferay-ui:success key="importedRowsCount">

		<%
		int importedRowsCount = (int)SessionMessages.get(renderRequest, "importedRowsCount");
		%>

		<c:choose>
			<c:when test="<%= importedRowsCount > 1 %>">
				<liferay-ui:message arguments="<%= importedRowsCount %>" key="x-rows-were-imported-successfully" translateArguments="<%= false %>" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="1-row-was-imported-successfully" />
			</c:otherwise>
		</c:choose>
	</liferay-ui:success>

	<aui:model-context bean="<%= commerceOrder %>" model="<%= CommerceOrder.class %>" />

	<div class="commerce-panel">
		<div class="commerce-panel__content">
			<div class="align-items-center row">
				<div class="col-md-3">
					<div class="commerce-order-title">
						<%= HtmlUtil.escape(commerceAccount.getName()) %>
					</div>
				</div>

				<div class="col-md-3">
					<dl class="commerce-list">
						<dt><liferay-ui:message key="total" /></dt>
						<dd><%= HtmlUtil.escape(totalOrderCommerceMoney.format(locale)) %></dd>
					</dl>
				</div>

				<div class="col-md-3">
					<dl class="commerce-list">
						<dt><liferay-ui:message key="notes" /></dt>
						<dd>

							<%
							request.setAttribute("order_notes.jsp-showLabel", Boolean.TRUE);
							request.setAttribute("order_notes.jsp-taglibLinkCssClass", "link-outline link-outline-borderless link-outline-secondary lfr-icon-item-reverse");
							%>

							<liferay-util:include page="/pending_commerce_orders/order_notes.jsp" servletContext="<%= application %>" />
						</dd>
					</dl>
				</div>
			</div>
		</div>

		<div class="commerce-panel__content">
			<div class="align-items-center row">
				<div class="col-md-3">
					<dl class="commerce-list">
						<dt><liferay-ui:message key="account-id" /></dt>
						<dd><%= commerceAccount.getCommerceAccountId() %></dd>
					</dl>
				</div>

				<div class="col-md-3">
					<dl class="commerce-list">
						<dt><liferay-ui:message key="order-id" /></dt>
						<dd><%= commerceOrder.getCommerceOrderId() %></dd>
					</dl>
				</div>

				<div class="col-md-3">
					<dl class="commerce-list">
						<dt><liferay-ui:message key="order-type" /></dt>
						<dd><%= commerceOrderContentDisplayContext.getCommerceOrderTypeName(languageId) %></dd>
					</dl>
				</div>

				<div class="col-md-3">
					<dl class="commerce-list">
						<dt><liferay-ui:message key="order-date" /></dt>
						<dd>
							<%= commerceOrderContentDisplayContext.getCommerceOrderDate(commerceOrder) %>
							<%= commerceOrderContentDisplayContext.getCommerceOrderTime(commerceOrder) %>
						</dd>
					</dl>
				</div>
			</div>
		</div>
	</div>

	<c:if test="<%= commerceOrderContentDisplayContext.isShowPurchaseOrderNumber() %>">
		<div class="row">
			<div class="col-md-12">
				<div class="commerce-panel">
					<div class="commerce-panel__title"><liferay-ui:message key="purchase-order-number" /></div>
					<div class="commerce-panel__content">
						<div class="row">
							<div class="col-md-6">
								<dl class="commerce-list">
									<c:choose>
										<c:when test="<%= commerceOrderContentDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) %>">
											<aui:input cssClass="commerce-input" inlineField="<%= true %>" label="" name="purchaseOrderNumber" wrappedField="<%= false %>" />
										</c:when>
										<c:otherwise>
											<%= HtmlUtil.escape(commerceOrder.getPurchaseOrderNumber()) %>
										</c:otherwise>
									</c:choose>
								</dl>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>

	<div class="row">
		<div class="col-md-6">
			<div class="commerce-panel">
				<div class="commerce-panel__title"><liferay-ui:message key="billing-address" /></div>
				<div class="commerce-panel__content">
					<div class="row">
						<div class="col-md-12">
							<c:if test="<%= hasViewBillingAddressPermission %>">
								<c:choose>
									<c:when test="<%= commerceOrderContentDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) %>">
										<dl class="commerce-list">
											<aui:select cssClass="commerce-input" inlineField="<%= true %>" label="" name="billingAddressId" showEmptyOption="<%= true %>" wrappedField="<%= false %>">

												<%
												for (CommerceAddress commerceAddress : billingAddresses) {
												%>

													<aui:option label="<%= HtmlUtil.escape(commerceAddress.getName()) %>" selected="<%= billingCommerceAddressId == commerceAddress.getCommerceAddressId() %>" value="<%= commerceAddress.getCommerceAddressId() %>" />

												<%
												}
												%>

											</aui:select>
										</dl>
									</c:when>
									<c:otherwise>
										<c:if test="<%= billingCommerceAddress != null %>">
											<p><%= HtmlUtil.escape(billingCommerceAddress.getStreet1()) %></p>

											<c:if test="<%= !Validator.isBlank(billingCommerceAddress.getStreet2()) %>">
												<p><%= HtmlUtil.escape(billingCommerceAddress.getStreet2()) %></p>
											</c:if>

											<c:if test="<%= !Validator.isBlank(billingCommerceAddress.getStreet3()) %>">
												<p><%= HtmlUtil.escape(billingCommerceAddress.getStreet3()) %></p>
											</c:if>

											<p><%= HtmlUtil.escape(billingCommerceAddress.getCity() + StringPool.SPACE + billingCommerceAddress.getZip()) %></p>
										</c:if>
									</c:otherwise>
								</c:choose>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-6">
			<div class="commerce-panel">
				<div class="commerce-panel__title"><liferay-ui:message key="shipping-address" /></div>
				<div class="commerce-panel__content">
					<div class="row">
						<div class="col-md-12">
							<c:choose>
								<c:when test="<%= commerceOrderContentDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) %>">
									<dl class="commerce-list">
										<aui:select cssClass="commerce-input" inlineField="<%= true %>" label="" name="shippingAddressId" showEmptyOption="<%= true %>" wrappedField="<%= false %>">

											<%
											for (CommerceAddress commerceAddress : shippingAddresses) {
											%>

												<aui:option label="<%= HtmlUtil.escape(commerceAddress.getName()) %>" selected="<%= shippingCommerceAddressId == commerceAddress.getCommerceAddressId() %>" value="<%= commerceAddress.getCommerceAddressId() %>" />

											<%
											}
											%>

										</aui:select>
									</dl>
								</c:when>
								<c:otherwise>
									<c:if test="<%= shippingCommerceAddress != null %>">
										<p><%= HtmlUtil.escape(shippingCommerceAddress.getStreet1()) %></p>

										<c:if test="<%= !Validator.isBlank(shippingCommerceAddress.getStreet2()) %>">
											<p><%= HtmlUtil.escape(shippingCommerceAddress.getStreet2()) %></p>
										</c:if>

										<c:if test="<%= !Validator.isBlank(shippingCommerceAddress.getStreet3()) %>">
											<p><%= HtmlUtil.escape(shippingCommerceAddress.getStreet3()) %></p>
										</c:if>

										<p><%= HtmlUtil.escape(shippingCommerceAddress.getCity() + StringPool.SPACE + shippingCommerceAddress.getZip()) %></p>
									</c:if>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<liferay-ui:icon-menu
		direction="right"
		icon="<%= StringPool.BLANK %>"
		markupView="lexicon"
		message="<%= StringPool.BLANK %>"
		showWhenSingleIcon="<%= true %>"
		triggerCssClass="btn btn-lg btn-monospaced btn-primary position-fixed thumb-menu"
	>

		<%
		PortletURL viewCommerceOrderImporterTypeURL = PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCRenderCommandName(
			"/commerce_open_order_content/view_commerce_order_importer_type"
		).setParameter(
			"commerceOrderId", commerceOrder.getCommerceOrderId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildPortletURL();

		for (CommerceOrderImporterType commerceOrderImporterType : commerceOrderContentDisplayContext.getCommerceImporterTypes(commerceOrder)) {
			String commerceOrderImporterTypeKey = commerceOrderImporterType.getKey();
			String commerceOrderImporterTypeLabel = commerceOrderImporterType.getLabel(locale);

			viewCommerceOrderImporterTypeURL.setParameter("commerceOrderImporterTypeKey", commerceOrderImporterTypeKey);

			String viewCommerceOrderImporterTypeURLString = viewCommerceOrderImporterTypeURL.toString();
		%>

			<liferay-ui:icon
				cssClass="<%= commerceOrderImporterTypeKey %>"
				message="<%= commerceOrderImporterTypeLabel %>"
				url="<%= viewCommerceOrderImporterTypeURLString %>"
			/>

			<liferay-frontend:component
				context='<%=
					HashMapBuilder.<String, Object>put(
						"commerceOrderImporterTypeKey", commerceOrderImporterTypeKey
					).put(
						"title", commerceOrderImporterTypeLabel
					).put(
						"url", viewCommerceOrderImporterTypeURLString
					).build()
				%>'
				module="js/edit_commerce_order"
			/>

		<%
		}
		%>

		<c:if test="<%= commerceOrderContentDisplayContext.hasModelPermission(commerceOrder, ActionKeys.DELETE) %>">
			<portlet:actionURL name="/commerce_open_order_content/edit_commerce_order" var="deleteURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete
				message="delete"
				url="<%= deleteURL %>"
			/>

			<portlet:actionURL name="/commerce_open_order_content/edit_commerce_order_item" var="deleteOrderContentURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESET %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete
				message="remove-all-items"
				url="<%= deleteOrderContentURL %>"
			/>

			<liferay-ui:icon
				icon="print"
				message="print"
				target="_blank"
				url="<%= commerceOrderContentDisplayContext.getExportCommerceOrderReportURL() %>"
			/>
		</c:if>
	</liferay-ui:icon-menu>

	<div class="commerce-cta is-visible">
		<c:if test="<%= commerceOrderContentDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) %>">
			<clay:button
				cssClass="btn-fixed btn-secondary"
				displayType="secondary"
				label='<%= LanguageUtil.get(request, "save") %>'
				small="<%= false %>"
				type="submit"
			/>
		</c:if>

		<liferay-commerce:order-transitions
			commerceOrderId="<%= commerceOrder.getCommerceOrderId() %>"
			cssClass="btn btn-fixed btn-lg btn-primary ml-3"
		/>
	</div>
</aui:form>

<div class="row">
	<div class="col-md-9">

		<%
		java.util.Map<String, String> contextParams = new java.util.HashMap<>();

		contextParams.put("commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId()));
		%>

		<clay:data-set-display
			contextParams="<%= contextParams %>"
			dataProviderKey="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDER_ITEMS %>"
			id="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDER_ITEMS %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			nestedItemsKey="orderItemId"
			nestedItemsReferenceKey="orderItems"
			pageNumber="<%= 1 %>"
			portletURL="<%= commerceOrderContentDisplayContext.getPortletURL() %>"
			style="stacked"
		/>
	</div>

	<div class="col-md-3">
		<div class="commerce-panel">
			<div class="commerce-panel__content">
				<dl class="commerce-list">
					<dt><liferay-ui:message key="subtotal" /></dt>
					<dd class="text-right"><%= HtmlUtil.escape(subtotalCommerceMoney.format(locale)) %></dd>

					<c:if test="<%= (subtotalCommerceDiscountValue != null) && (BigDecimal.ZERO.compareTo(subtotalCommerceDiscountValue.getDiscountPercentage()) < 0) %>">

						<%
						CommerceMoney subtotalDiscountAmountCommerceMoney = subtotalCommerceDiscountValue.getDiscountAmount();
						%>

						<dt><liferay-ui:message key="subtotal-discount" /></dt>
						<dd class="text-right"><%= HtmlUtil.escape(subtotalDiscountAmountCommerceMoney.format(locale)) %></dd>
						<dt></dt>
						<dd class="text-right"><%= HtmlUtil.escape(commerceOrderContentDisplayContext.getLocalizedPercentage(subtotalCommerceDiscountValue.getDiscountPercentage(), locale)) %></dd>
					</c:if>

					<dt><liferay-ui:message key="delivery" /></dt>
					<dd class="text-right"><%= HtmlUtil.escape(shippingValueCommerceMoney.format(locale)) %></dd>

					<c:if test="<%= (shippingCommerceDiscountValue != null) && (BigDecimal.ZERO.compareTo(shippingCommerceDiscountValue.getDiscountPercentage()) < 0) %>">

						<%
						CommerceMoney shippingDiscountAmountCommerceMoney = shippingCommerceDiscountValue.getDiscountAmount();
						%>

						<dt><liferay-ui:message key="delivery-discount" /></dt>
						<dd class="text-right"><%= HtmlUtil.escape(shippingDiscountAmountCommerceMoney.format(locale)) %></dd>
						<dt></dt>
						<dd class="text-right"><%= HtmlUtil.escape(commerceOrderContentDisplayContext.getLocalizedPercentage(shippingCommerceDiscountValue.getDiscountPercentage(), locale)) %></dd>
					</c:if>

					<c:if test="<%= priceDisplayType.equals(CommercePricingConstants.TAX_EXCLUDED_FROM_PRICE) %>">
						<dt><liferay-ui:message key="tax" /></dt>
						<dd class="text-right"><%= HtmlUtil.escape(taxValueCommerceMoney.format(locale)) %></dd>
					</c:if>

					<c:if test="<%= (totalCommerceDiscountValue != null) && (BigDecimal.ZERO.compareTo(totalCommerceDiscountValue.getDiscountPercentage()) < 0) %>">

						<%
						CommerceMoney totalDiscountAmountCommerceMoney = totalCommerceDiscountValue.getDiscountAmount();
						%>

						<dt><liferay-ui:message key="total-discount" /></dt>
						<dd class="text-right"><%= HtmlUtil.escape(totalDiscountAmountCommerceMoney.format(locale)) %></dd>
						<dt></dt>
						<dd class="text-right"><%= HtmlUtil.escape(commerceOrderContentDisplayContext.getLocalizedPercentage(totalCommerceDiscountValue.getDiscountPercentage(), locale)) %></dd>
					</c:if>
				</dl>
			</div>

			<div class="commerce-panel__content">
				<dl class="commerce-list">
					<dt><liferay-ui:message key="total" /></dt>
					<dd class="text-right"><%= HtmlUtil.escape(totalOrderCommerceMoney.format(locale)) %></dd>
				</dl>
			</div>
		</div>
	</div>
</div>

<portlet:actionURL name="/commerce_open_order_content/edit_commerce_order" var="editCommerceOrderURL" />

<%@ include file="/pending_commerce_orders/transition.jspf" %>

<aui:script use="aui-base">
	var orderTransition = A.one('#<portlet:namespace />orderTransition');

	if (orderTransition) {
		orderTransition.delegate(
			'click',
			(event) => {
				<portlet:namespace />transition(event);
			},
			'.transition-link'
		);
	}
</aui:script>