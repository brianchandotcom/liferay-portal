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
CommerceOrganizationOrderDisplayContext commerceOrganizationOrderDisplayContext = (CommerceOrganizationOrderDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrganizationOrderDisplayContext.getCommerceOrder();
%>

<portlet:actionURL name="editCommerceOrder" var="editCommerceOrderActionURL">
	<portlet:param name="mvcRenderCommandName" value="editCommerceOrder" />
</portlet:actionURL>

<div class="b2b-portlet-content-header">
	<liferay-ui:icon
		cssClass="header-back-to"
		icon="order-arrow-down"
		id="TabsBack"
		label="<%= false %>"
		markupView="lexicon"
		message='<%= LanguageUtil.get(resourceBundle, "back") %>'
		method="get"
		url="<%= layout.getRegularURL(request) %>"
	/>

	<div class="autofit-float autofit-row header-title-bar">
		<div class="autofit-col autofit-col-expand">
			<liferay-ui:header
				backURL="<%= redirect %>"
				localizeTitle="<%= false %>"
				showBackURL="<%= false %>"
				title='<%= LanguageUtil.format(request, "order-x", commerceOrder.getCommerceOrderId()) %>'
			/>
		</div>

		<div class="autofit-col">
			<liferay-ui:icon
				icon="print"
				iconCssClass="inline-item inline-item-after"
				label="<%= true %>"
				linkCssClass="lfr-icon-item-reverse link-outline link-outline-borderless link-outline-secondary"
				markupView="lexicon"
				message="print"
				method="get"
				url="javascript:window.print();"
			/>
		</div>

		<div class="autofit-col">

			<%
			request.setAttribute("order_notes.jsp-showLabel", Boolean.TRUE);
			request.setAttribute("order_notes.jsp-taglibLinkCssClass", "link-outline link-outline-borderless link-outline-secondary lfr-icon-item-reverse");
			%>

			<liferay-util:include page="/order_notes.jsp" servletContext="<%= application %>" />
		</div>

		<c:if test="<%= commerceOrder.isOpen() %>">
			<div class="autofit-col">

				<%
				request.setAttribute("order_transition.jsp-commerceOrder", commerceOrder);
				%>

				<liferay-util:include page="/order_transition.jsp" servletContext="<%= application %>" />
			</div>

			<c:if test="<%= commerceOrganizationOrderDisplayContext.hasPermission(commerceOrder, ActionKeys.UPDATE) %>">
				<div class="autofit-col">
					<liferay-ui:icon-menu
						direction="right"
						icon="<%= StringPool.BLANK %>"
						markupView="lexicon"
						message="<%= StringPool.BLANK %>"
						showWhenSingleIcon="<%= true %>"
						triggerCssClass="component-action"
					>
						<portlet:actionURL name="editCommerceOrderItem" var="deleteURL">
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESET %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
						</portlet:actionURL>

						<liferay-ui:icon-delete
							message="delete-all"
							url="<%= deleteURL %>"
						/>
					</liferay-ui:icon-menu>
				</div>
			</c:if>
		</c:if>
	</div>
</div>

<aui:form action="<%= editCommerceOrderActionURL %>" cssClass="order-details-container" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="commerceOrderId" type="hidden" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />

	<div class="autofit-float autofit-row order-details-header">
		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<h3 class="order-details-title"><liferay-ui:message key="order-date" /></h3>

				<div class="order-date order-details-subtitle">
					<%= commerceOrganizationOrderDisplayContext.getCommerceOrderDate(commerceOrder) %>
				</div>

				<div class="order-time">
					<%= commerceOrganizationOrderDisplayContext.getCommerceOrderTime(commerceOrder) %>
				</div>
			</div>
		</div>

		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<h3 class="order-details-title"><liferay-ui:message key="customer" /></h3>

				<div class="customer-name order-details-subtitle">
					<%= HtmlUtil.escape(commerceOrder.getCustomerName()) %>
				</div>

				<div class="customer-id">
					<%= commerceOrder.getCustomerId() %>
				</div>
			</div>
		</div>

		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<h3 class="order-details-title"><liferay-ui:message key="payment" /></h3>

				<%
				String paymentMethodName = commerceOrganizationOrderDisplayContext.getCommerceOrderPaymentMethodName(commerceOrder);
				%>

				<c:if test="<%= Validator.isNotNull(paymentMethodName) %>">
					<div class="order-details-subtitle payment-method-name">
						<%= HtmlUtil.escape(paymentMethodName) %>
					</div>
				</c:if>
			</div>
		</div>

		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<h3 class="order-details-title"><liferay-ui:message key="order-value" /></h3>

				<div class="order-details-subtitle order-value">
					<%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderValue(commerceOrder)) %>
				</div>
			</div>
		</div>

		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<h3 class="order-details-title"><liferay-ui:message key="status" /></h3>

				<div class="order-details-subtitle order-status">
					<%= commerceOrganizationOrderDisplayContext.getCommerceOrderStatus(commerceOrder) %>
				</div>
			</div>
		</div>

		<c:if test="<%= !commerceOrder.isOpen() %>">
			<div class="autofit-col autofit-col-expand order-details-reorder">
				<div class="autofit-section">
					<aui:button icon="icon-refresh" iconAlign="right" onClick='<%= renderResponse.getNamespace() + "reorderCommerceOrder();" %>' primary="<%= true %>" value="reorder" />
				</div>
			</div>
		</c:if>
	</div>

	<div class="autofit-float autofit-row order-details-footer">
		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<strong><liferay-ui:message key="carrier" /></strong>

				<%
				String shippingOptionName = commerceOrganizationOrderDisplayContext.getCommerceOrderShippingOptionName(commerceOrder);
				%>

				<c:if test="<%= Validator.isNotNull(shippingOptionName) %>">
					<span class="inline-item-after"><%= HtmlUtil.escape(shippingOptionName) %></span>
				</c:if>
			</div>
		</div>

		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<strong><liferay-ui:message key="method" /></strong>

				<%
				String shippingMethodName = commerceOrganizationOrderDisplayContext.getCommerceOrderShippingMethodName(commerceOrder);
				%>

				<c:if test="<%= Validator.isNotNull(shippingMethodName) %>">
					<span class="inline-item-after"><%= HtmlUtil.escape(shippingMethodName) %></span>
				</c:if>
			</div>
		</div>

		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<strong><liferay-ui:message key="expected-duration" /></strong>
				<span class="inline-item-after">3 Business Days</span>
			</div>
		</div>
	</div>
</aui:form>

<liferay-portlet:actionURL name="editCommerceOrderItem" var="editCommerceOrderItemURL" />

<liferay-ui:search-container
	cssClass="order-details-table"
	searchContainer="<%= commerceOrganizationOrderDisplayContext.getCommerceOrderItemsSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.commerce.model.CommerceOrderItem"
		keyProperty="commerceOrderItemId"
		modelVar="commerceOrderItem"
	>
		<liferay-ui:search-container-column-text
			property="sku"
		/>

		<liferay-ui:search-container-column-text
			name="title"
			value="<%= HtmlUtil.escape(commerceOrderItem.getTitle(locale)) %>"
		/>

		<%
		List<KeyValuePair> keyValuePairs = commerceOrganizationOrderDisplayContext.getKeyValuePairs(commerceOrderItem.getJson(), locale);

		StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

		for (KeyValuePair keyValuePair : keyValuePairs) {
			stringJoiner.add(keyValuePair.getValue());
		}
		%>

		<liferay-ui:search-container-column-text
			name="description"
			value="<%= HtmlUtil.escape(stringJoiner.toString()) %>"
		/>

		<c:choose>
			<c:when test="<%= commerceOrder.isOpen() && CommerceOrderPermission.contains(permissionChecker, commerceOrder, ActionKeys.UPDATE) %>">
				<liferay-ui:search-container-column-text
					cssClass="order-item-quantity"
					name="quantity"
				>
					<aui:form action="<%= editCommerceOrderItemURL %>" method="post" name='<%= commerceOrderItem.getCommerceOrderItemId() + "fm" %>'>
						<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
						<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
						<aui:input name="commerceOrderItemId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderItemId() %>" />

						<aui:model-context bean="<%= commerceOrderItem %>" model="<%= CommerceOrderItem.class %>" />

						<div class="form-group">
							<div class="input-group">
								<div class="input-group-item input-group-prepend">
									<liferay-commerce:quantity-input name="quantity" CPDefinitionId="<%= commerceOrderItem.getCPDefinitionId() %>" value="<%= commerceOrderItem.getQuantity() %>" useSelect="<%= false %>" />
								</div>

								<div class="input-group-append input-group-item input-group-item-shrink">
									<clay:button
										label='<%= LanguageUtil.get(resourceBundle, "update") %>'
										style="secondary"
										type="submit"
									/>
								</div>
							</div>
						</div>
					</aui:form>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:otherwise>
				<liferay-ui:search-container-column-text
					property="quantity"
				/>
			</c:otherwise>
		</c:choose>

		<liferay-ui:search-container-column-text
			name="price"
			value="<%= commerceOrganizationOrderDisplayContext.getCommerceOrderItemPrice(commerceOrderItem) %>"
		/>

		<c:if test="<%= commerceOrganizationOrderDisplayContext.hasPermission(commerceOrder, ActionKeys.UPDATE) && commerceOrder.isOpen() %>">
			<liferay-ui:search-container-column-text>
				<portlet:actionURL name="editCommerceOrderItem" var="deleteURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="commerceOrderItemId" value="<%= String.valueOf(commerceOrderItem.getCommerceOrderItemId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					icon="times"
					markupView="lexicon"
					url="<%= deleteURL %>"
				/>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<portlet:actionURL name="editCommerceOrder" var="editCommerceOrderURL" />

<%@ include file="/transition.jspf" %>

<aui:script>
	function <portlet:namespace />reorderCommerceOrder() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'reorder';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<aui:script use="aui-base">
	var orderTransition = A.one('#<portlet:namespace />orderTransition');

	orderTransition.delegate(
		'click',
		function(event) {
			<portlet:namespace />transition(event);
		},
		'.transition-link'
	);
</aui:script>