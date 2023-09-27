<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);
%>

<clay:sheet-section
	id='<%= liferayPortletResponse.getNamespace() + "defaultAddresses" %>'
>
	<clay:content-row
		containerElement="h3"
		cssClass="sheet-subtitle"
	>
		<clay:content-col
			containerElement="span"
			expand="<%= true %>"
		>
			<span class="heading-text"><liferay-ui:message key="default-account-addresses" /></span>
		</clay:content-col>
	</clay:content-row>

	<div class="form-group-autofit">
		<div class="form-group-item">
			<div class="sheet-text">
				<liferay-ui:message key="billing" />

				<clay:icon
					symbol="credit-card"
				/>
			</div>

			<%
			Address defaultBillingAddress = accountEntryDisplay.getDefaultBillingAddress();
			%>

			<address id="<portlet:namespace />billingAddress">
				<c:choose>
					<c:when test="<%= defaultBillingAddress == null %>">
						<liferay-ui:message key="not-set" />
					</c:when>
					<c:otherwise>
						<h4><%= HtmlUtil.escape(defaultBillingAddress.getName()) %></h4>

						<liferay-text-localizer:address-display
							address="<%= defaultBillingAddress %>"
						/>

						<c:if test="<%= Validator.isNotNull(defaultBillingAddress.getPhoneNumber()) %>">
							<span class="autofit-row"><%= HtmlUtil.escape(defaultBillingAddress.getPhoneNumber()) %></span>
						</c:if>
					</c:otherwise>
				</c:choose>
			</address>

			<div class="btn-group button-holder">
				<liferay-ui:icon
					cssClass="btn-one modify-link"
					data='<%=
						HashMapBuilder.<String, Object>put(
							"type", "billing"
						).build()
					%>'
					label="<%= true %>"
					linkCssClass="btn btn-secondary btn-sm"
					message='<%= (accountEntryDisplay.getDefaultBillingAddress() == null) ? "set-default-address" : "change" %>'
					method="get"
					url="javascript:void(0);"
				/>

				<c:if test="<%= accountEntryDisplay.getDefaultBillingAddress() != null %>">
					<portlet:actionURL name="/account_admin/update_account_entry_default_address" var="removeDefaultBillingAddressURL">
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
						<portlet:param name="addressId" value="0" />
						<portlet:param name="type" value="billing" />
					</portlet:actionURL>

					<liferay-ui:icon
						cssClass="btn-two c-ml-sm-3 c-mt-3 c-mt-sm-0"
						label="<%= true %>"
						linkCssClass="btn btn-secondary btn-sm"
						message="remove"
						method="get"
						url="<%= removeDefaultBillingAddressURL %>"
					/>
				</c:if>
			</div>
		</div>

		<div class="form-group-item">
			<div class="sheet-text">
				<liferay-ui:message key="shipping" />

				<clay:icon
					symbol="truck"
				/>
			</div>

			<%
			Address defaultShippingAddress = accountEntryDisplay.getDefaultShippingAddress();
			%>

			<address>
				<c:choose>
					<c:when test="<%= defaultShippingAddress == null %>">
						<liferay-ui:message key="not-set" />
					</c:when>
					<c:otherwise>
						<h4><%= HtmlUtil.escape(defaultShippingAddress.getName()) %></h4>

						<liferay-text-localizer:address-display
							address="<%= defaultShippingAddress %>"
						/>

						<c:if test="<%= Validator.isNotNull(defaultShippingAddress.getPhoneNumber()) %>">
							<span class="autofit-row"><%= HtmlUtil.escape(defaultShippingAddress.getPhoneNumber()) %></span>
						</c:if>
					</c:otherwise>
				</c:choose>
			</address>

			<div class="btn-group button-holder">
				<liferay-ui:icon
					cssClass="modify-link"
					data='<%=
						HashMapBuilder.<String, Object>put(
							"type", "shipping"
						).build()
					%>'
					label="<%= true %>"
					linkCssClass="btn btn-secondary btn-sm"
					message='<%= (accountEntryDisplay.getDefaultShippingAddress() == null) ? "set-default-address" : "change" %>'
					method="get"
					url="javascript:void(0);"
				/>

				<c:if test="<%= accountEntryDisplay.getDefaultShippingAddress() != null %>">
					<portlet:actionURL name="/account_admin/update_account_entry_default_address" var="removeDefaultShippingAddressURL">
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
						<portlet:param name="addressId" value="0" />
						<portlet:param name="type" value="shipping" />
					</portlet:actionURL>

					<liferay-ui:icon
						cssClass="btn-two c-ml-sm-3 c-mt-3 c-mt-sm-0"
						label="<%= true %>"
						linkCssClass="btn btn-secondary btn-sm"
						message="remove"
						method="get"
						url="<%= removeDefaultShippingAddressURL %>"
					/>
				</c:if>
			</div>
		</div>
	</div>
</clay:sheet-section>

<portlet:renderURL var="selectDefaultAddressURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/account_entries_admin/account_entry/select_default_address.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
</portlet:renderURL>

<portlet:actionURL name="/account_admin/update_account_entry_default_address" var="updateAccountEntryDefaultAddressURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
</portlet:actionURL>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"baseSelectDefaultAddressURL", selectDefaultAddressURL
		).put(
			"baseUpdateAccountEntryDefaultAddressesURL", updateAccountEntryDefaultAddressURL
		).put(
			"defaultAddressesContainerId", liferayPortletResponse.getNamespace() + "defaultAddresses"
		).build()
	%>'
	module="account_entries_admin/js/DefaultAddresses"
/>