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
%>

<clay:sheet-section
	id='<%= liferayPortletResponse.getNamespace() + "defaultCommerceTermEntries" %>'
>
	<clay:content-row
		containerElement="h3"
		cssClass="sheet-subtitle"
	>
		<clay:content-col
			containerElement="span"
			expand="<%= true %>"
		>
			<span class="heading-text"><liferay-ui:message key="default-account-terms-and-conditions" /></span>
		</clay:content-col>
	</clay:content-row>

	<div class="form-group-autofit">
		<div class="form-group-item">
			<div class="sheet-text">
				<liferay-ui:message key="payment" />

				<clay:icon
					symbol="credit-card"
				/>
			</div>

			<%
			CommerceTermEntry defaultPaymentCommerceTermEntry = commerceAccountEntryDisplay.getDefaultPaymentCommerceTermEntry();
			%>

			<c:choose>
				<c:when test="<%= defaultPaymentCommerceTermEntry == null %>">
					<span style="margin-bottom: 1rem;"><liferay-ui:message key="use-priority-settings" /></span>
				</c:when>
				<c:otherwise>
					<h4 style="margin-bottom: 1rem;"><%= defaultPaymentCommerceTermEntry.getName() %>
						<c:if test="<%= !defaultPaymentCommerceTermEntry.isActive() %>">
							<strong onmouseover="Liferay.Portal.ToolTip.show(this, 'Inactive term');" style="color: orange;">
								<clay:icon
									symbol="warning"
								/>
							</strong>
						</c:if>
					</h4>
				</c:otherwise>
			</c:choose>

			<div class="btn-group button-holder">
				<liferay-ui:icon
					cssClass="modify-link"
					data='<%=
						HashMapBuilder.<String, Object>put(
							"type", CommerceTermEntryConstants.TYPE_PAYMENT_TERMS
						).build()
					%>'
					label="<%= true %>"
					linkCssClass="btn btn-secondary btn-sm"
					message='<%= (commerceAccountEntryDisplay.getDefaultPaymentCommerceTermEntry() == null) ? "set-default-payment-commerce-terms-entry" : "change" %>'
					method="get"
					url="javascript:;"
				/>

				<c:if test="<%= commerceAccountEntryDisplay.getDefaultPaymentCommerceTermEntry() != null %>">
					<portlet:actionURL name="/commerce_term_entry/edit_account_entry_default_commerce_term_entry" var="removeDefaultPaymentCommerceTermEntryURL">
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="accountEntryId" value="<%= String.valueOf(commerceAccountEntryDisplay.getAccountEntryId()) %>" />
						<portlet:param name="commerceTermEntryId" value="0" />
						<portlet:param name="type" value="<%= CommerceTermEntryConstants.TYPE_PAYMENT_TERMS %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						cssClass="btn-two c-ml-sm-3 c-mt-3 c-mt-sm-0"
						label="<%= true %>"
						linkCssClass="btn btn-secondary btn-sm"
						message="remove"
						method="get"
						url="<%= removeDefaultPaymentCommerceTermEntryURL %>"
					/>
				</c:if>
			</div>
		</div>

		<div class="form-group-item">
			<div class="sheet-text">
				<liferay-ui:message key="delivery" />

				<clay:icon
					symbol="truck"
				/>
			</div>

			<%
			CommerceTermEntry defaultDeliveryCommerceTermEntry = commerceAccountEntryDisplay.getDefaultDeliveryCommerceTermEntry();
			%>

			<c:choose>
				<c:when test="<%= defaultDeliveryCommerceTermEntry == null %>">
					<span style="margin-bottom: 1rem;"><liferay-ui:message key="use-priority-settings" /></span>
				</c:when>
				<c:otherwise>
					<h4 style="margin-bottom: 1rem;"><%= defaultDeliveryCommerceTermEntry.getName() %>
						<c:if test="<%= !defaultDeliveryCommerceTermEntry.isActive() %>">
							<strong onmouseover="Liferay.Portal.ToolTip.show(this, 'Inactive term');" style="color: orange;">
								<clay:icon
									symbol="warning"
								/>
							</strong>
						</c:if>
					</h4>
				</c:otherwise>
			</c:choose>

			<div class="btn-group button-holder">
				<liferay-ui:icon
					cssClass="modify-link"
					data='<%=
						HashMapBuilder.<String, Object>put(
							"type", CommerceTermEntryConstants.TYPE_DELIVERY_TERMS
						).build()
					%>'
					label="<%= true %>"
					linkCssClass="btn btn-secondary btn-sm"
					message='<%= (commerceAccountEntryDisplay.getDefaultDeliveryCommerceTermEntry() == null) ? "set-default-delivery-commerce-terms-entry" : "change" %>'
					method="get"
					url="javascript:;"
				/>

				<c:if test="<%= commerceAccountEntryDisplay.getDefaultDeliveryCommerceTermEntry() != null %>">
					<portlet:actionURL name="/commerce_term_entry/edit_account_entry_default_commerce_term_entry" var="removeDefaultDeliveryCommerceTermEntryURL">
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="accountEntryId" value="<%= String.valueOf(commerceAccountEntryDisplay.getAccountEntryId()) %>" />
						<portlet:param name="commerceTermEntryId" value="0" />
						<portlet:param name="type" value="<%= CommerceTermEntryConstants.TYPE_DELIVERY_TERMS %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						cssClass="btn-two c-ml-sm-3 c-mt-3 c-mt-sm-0"
						label="<%= true %>"
						linkCssClass="btn btn-secondary btn-sm"
						message="remove"
						method="get"
						url="<%= removeDefaultDeliveryCommerceTermEntryURL %>"
					/>
				</c:if>
			</div>
		</div>
	</div>
</clay:sheet-section>

<portlet:renderURL var="selectDefaultCommerceTermEntryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_term_entry/edit_account_entry_default_commerce_term_entry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="accountEntryId" value="<%= String.valueOf(commerceAccountEntryDisplay.getAccountEntryId()) %>" />
</portlet:renderURL>

<portlet:actionURL name="/commerce_term_entry/edit_account_entry_default_commerce_term_entry" var="updateAccountEntryDefaultCommerceTermEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="accountEntryId" value="<%= String.valueOf(commerceAccountEntryDisplay.getAccountEntryId()) %>" />
</portlet:actionURL>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"baseSelectDefaultCommerceTermEntryURL", selectDefaultCommerceTermEntryURL
		).put(
			"baseUpdateAccountEntryDefaultCommerceTermEntryURL", updateAccountEntryDefaultCommerceTermEntryURL.toString()
		).put(
			"defaultCommerceTermEntriesContainerId", liferayPortletResponse.getNamespace() + "defaultCommerceTermEntries"
		).build()
	%>'
	module="js/DefaultCommerceTermEntries"
/>