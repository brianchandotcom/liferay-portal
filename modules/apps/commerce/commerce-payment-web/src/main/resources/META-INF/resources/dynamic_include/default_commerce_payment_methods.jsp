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
CommerceAccountEntryDisplay commerceAccountEntryDisplay = CommerceAccountEntryDisplay.of(ParamUtil.getLong(request, "accountEntryId"));

CommercePaymentMethodRegistry commercePaymentMethodRegistry = (CommercePaymentMethodRegistry)request.getAttribute(CommerceWebKeys.COMMERCE_PAYMENT_METHOD_REGISTRY);
%>

<clay:sheet-section
	id='<%= liferayPortletResponse.getNamespace() + "defaultCommercePaymentMethod" %>'
>
	<clay:content-row
		containerElement="h3"
		cssClass="sheet-subtitle"
	>
		<clay:content-col
			containerElement="span"
			expand="<%= true %>"
		>
			<span class="heading-text"><liferay-ui:message key="default-account-commerce-payment-methods" /></span>
		</clay:content-col>
	</clay:content-row>

	<div class="form-group-autofit">
		<div class="form-group-item">
			<div class="sheet-text">
				<liferay-ui:message key="payment-method" />

				<clay:icon
					symbol="credit-card"
				/>
			</div>

			<c:choose>
				<c:when test="<%= Validator.isNull(commerceAccountEntryDisplay.getDefaultCommercePaymentMethodKey()) || (commercePaymentMethodRegistry.getCommercePaymentMethod(commerceAccountEntryDisplay.getDefaultCommercePaymentMethodKey()) == null) %>">
					<span style="margin-bottom: 1rem;"><liferay-ui:message key="use-priority-settings" /></span>
				</c:when>
				<c:otherwise>

					<%
					CommercePaymentMethod commercePaymentMethod = commercePaymentMethodRegistry.getCommercePaymentMethod(commerceAccountEntryDisplay.getDefaultCommercePaymentMethodKey());
					%>

					<h4 style="margin-bottom: 1rem;"><%= commercePaymentMethod.getName(themeDisplay.getLocale()) %>
					</h4>
				</c:otherwise>
			</c:choose>

			<div class="btn-group button-holder">
				<liferay-ui:icon
					cssClass="modify-link"
					label="<%= true %>"
					linkCssClass="btn btn-secondary btn-sm"
					message='<%= Validator.isNull(commerceAccountEntryDisplay.getDefaultCommercePaymentMethodKey()) ? "set-default-commerce-payment-method" : "change" %>'
					method="get"
					url="javascript:;"
				/>

				<c:if test="<%= Validator.isNotNull(commerceAccountEntryDisplay.getDefaultCommercePaymentMethodKey()) %>">
					<portlet:actionURL name="/commerce_payment/edit_account_entry_default_commerce_payment_method" var="removeDefaultCommercePaymentMethodURL">
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="accountEntryId" value="<%= String.valueOf(commerceAccountEntryDisplay.getAccountEntryId()) %>" />
						<portlet:param name="commercePaymentMethodKey" value="" />
					</portlet:actionURL>

					<liferay-ui:icon
						cssClass="btn-two c-ml-sm-3 c-mt-3 c-mt-sm-0"
						label="<%= true %>"
						linkCssClass="btn btn-secondary btn-sm"
						message="remove"
						method="get"
						url="<%= removeDefaultCommercePaymentMethodURL %>"
					/>
				</c:if>
			</div>
		</div>
	</div>
</clay:sheet-section>

<portlet:renderURL var="selectDefaultCommercePaymentMethodURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_payment/edit_account_entry_default_commerce_payment_method" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="accountEntryId" value="<%= String.valueOf(commerceAccountEntryDisplay.getAccountEntryId()) %>" />
</portlet:renderURL>

<portlet:actionURL name="/commerce_payment/edit_account_entry_default_commerce_payment_method" var="updateAccountEntryDefaultCommercePaymentMethodURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="accountEntryId" value="<%= String.valueOf(commerceAccountEntryDisplay.getAccountEntryId()) %>" />
</portlet:actionURL>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"baseSelectDefaultCommercePaymentMethodURL", selectDefaultCommercePaymentMethodURL
		).put(
			"baseUpdateAccountEntryDefaultCommercePaymentMethodURL", updateAccountEntryDefaultCommercePaymentMethodURL.toString()
		).put(
			"defaultCommercePaymentMethodContainerId", liferayPortletResponse.getNamespace() + "defaultCommercePaymentMethod"
		).build()
	%>'
	module="js/DefaultCommercePaymentMethod"
/>