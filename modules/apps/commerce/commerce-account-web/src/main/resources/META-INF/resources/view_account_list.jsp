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
CommerceAccountDisplayContext commerceAccountDisplayContext = (CommerceAccountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

request.setAttribute("view.jsp-filterPerAccount", false);
%>

<liferay-ui:error exception="<%= UserEmailAddressException.MustValidate.class %>" message="please-enter-a-valid-email-address" />

<div class="commerce-account-container" id="<portlet:namespace />entriesContainer">
	<clay:data-set-display
		dataProviderKey="<%= CommerceAccountClayDataSetDataSetDisplayView.NAME %>"
		id="<%= CommerceAccountClayDataSetDataSetDisplayView.NAME %>"
		itemsPerPage="<%= 10 %>"
		namespace="<%= liferayPortletResponse.getNamespace() %>"
		pageNumber="<%= 1 %>"
		portletURL="<%= commerceAccountDisplayContext.getPortletURL() %>"
		style="stacked"
	/>
</div>

<c:if test="<%= commerceAccountDisplayContext.hasAddAccountPermissions() %>">
	<div class="commerce-cta is-visible">
		<aui:button cssClass="btn-lg js-invite-user" onClick='<%= liferayPortletResponse.getNamespace() + "openAddAccountModal();" %>' primary="<%= true %>" value="add-account" />
	</div>

	<portlet:actionURL name="/commerce_account/edit_commerce_account" var="editCommerceAccountActionURL" />

	<aui:form action="<%= editCommerceAccountActionURL %>" method="post" name="commerceAccountFm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input name="active" type="hidden" value="<%= true %>" />
		<aui:input name="emailAddresses" type="hidden" />
		<aui:input name="name" type="hidden" />
		<aui:input name="userIds" type="hidden" />
		<aui:input name="commerceAccountId" type="hidden" />
	</aui:form>

	<commerce-ui:add-account-modal
		componentId="addAccountModal"
	/>

	<aui:script>
		Liferay.provide(window, '<portlet:namespace />openAddAccountModal', (evt) => {
			var addAccountModal = Liferay.component('addAccountModal');

			addAccountModal.open();
		});

		Liferay.provide(window, 'setCurrentAccount', (id) => {
			document.querySelector('#<portlet:namespace /><%= Constants.CMD %>').value =
				'setCurrentAccount';
			document.querySelector(
				'#<portlet:namespace />commerceAccountId'
			).value = id;

			submitForm(document.<portlet:namespace />commerceAccountFm);
		});

		Liferay.provide(window, 'toggleActiveCommerceAccount', (id) => {
			document.querySelector('#<portlet:namespace /><%= Constants.CMD %>').value =
				'setActive';
			document.querySelector(
				'#<portlet:namespace />commerceAccountId'
			).value = id;

			submitForm(document.<portlet:namespace />commerceAccountFm);
		});

		Liferay.componentReady('addAccountModal').then((addAccountModal) => {
			addAccountModal.on('AddAccountModalSave', (event) => {
				var existingUserIds = event.administratorsEmail
					.filter((el) => {
						return el.userId;
					})
					.map((usr) => {
						return usr.userId;
					})
					.join(',');

				var newUserEmails = event.administratorsEmail
					.filter((el) => {
						return !el.userId;
					})
					.map((usr) => {
						return usr.email;
					})
					.join(',');

				document.querySelector(
					'#<portlet:namespace />emailAddresses'
				).value = newUserEmails;
				document.querySelector('#<portlet:namespace />name').value =
					event.accountName;
				document.querySelector(
					'#<portlet:namespace />userIds'
				).value = existingUserIds;

				addAccountModal.close();

				submitForm(document.<portlet:namespace />commerceAccountFm);
			});
		});
	</aui:script>
</c:if>