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

CommerceAccount commerceAccount = commerceAccountDisplayContext.getCurrentCommerceAccount();

Map<String, String> contextParams = new HashMap<>();

contextParams.put("commerceAccountId", String.valueOf(commerceAccount.getCommerceAccountId()));

PortletURL portletURL = currentURLObj;

portletURL.setParameter(PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL", backURL);
%>

<commerce-ui:dataset-display
	contextParams="<%= contextParams %>"
	dataProviderKey="<%= CommerceAccountUserClayDataSetDataSetDisplayView.NAME %>"
	id="<%= CommerceAccountUserClayDataSetDataSetDisplayView.NAME %>"
	itemsPerPage="<%= 10 %>"
	namespace="<%= renderResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= commerceAccountDisplayContext.getPortletURL() %>"
	style="stacked"
/>

<c:if test="<%= commerceAccountDisplayContext.hasCommerceAccountModelPermissions(CommerceAccountActionKeys.MANAGE_MEMBERS) %>">
	<div class="commerce-cta is-visible">
		<aui:button cssClass="btn-lg btn-primary js-invite-user" onClick='<%= renderResponse.getNamespace() + "openUserInvitationModal();" %>' value="invite-user" />
	</div>

	<commerce-ui:user-invitation-modal
		componentId="userInvitationModal"
	/>

	<portlet:actionURL name="inviteUser" var="inviteUserActionURL" />

	<aui:form action="<%= inviteUserActionURL %>" method="post" name="inviteUserFm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ASSIGN %>" />
		<aui:input name="redirect" type="hidden" value="<%= portletURL %>" />
		<aui:input name="commerceAccountId" type="hidden" value="<%= commerceAccountDisplayContext.getCurrentCommerceAccountId() %>" />
		<aui:input name="userId" type="hidden" />
		<aui:input name="userIds" type="hidden" />
		<aui:input name="emailAddresses" type="hidden" />
	</aui:form>

	<aui:script>
		Liferay.provide(
			window,
			'<portlet:namespace />openUserInvitationModal',
			function(evt) {
				const userInvitationModal = Liferay.component('userInvitationModal');
				userInvitationModal.open();
			}
		);

		Liferay.provide(window, 'removeCommerceAccountUser', function(id) {
			document.querySelector('#<portlet:namespace /><%= Constants.CMD %>').value =
				'<%= Constants.REMOVE %>';
			document.querySelector('#<portlet:namespace />userId').value = id;

			submitForm(document.<portlet:namespace />inviteUserFm);
		});

		Liferay.componentReady('userInvitationModal').then(function(
			userInvitationModal
		) {
			userInvitationModal.on('inviteUserToAccount', function(users) {
				let existingUsersIds = users
					.filter(function(el) {
						return el.userId;
					})
					.map(function(usr) {
						return usr.userId;
					})
					.join(',');

				let newUsersEmails = users
					.filter(function(el) {
						return !el.userId;
					})
					.map(function(usr) {
						return usr.email;
					})
					.join(',');

				document.querySelector(
					'#<portlet:namespace />userIds'
				).value = existingUsersIds;
				document.querySelector(
					'#<portlet:namespace />emailAddresses'
				).value = newUsersEmails;

				userInvitationModal.close();

				submitForm(document.<portlet:namespace />inviteUserFm);
			});
		});
	</aui:script>
</c:if>