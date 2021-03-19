<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrganizationDisplayContext commerceOrganizationDisplayContext = (CommerceOrganizationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:data-set-display
	contextParams='<%= HashMapBuilder.<String, String>put("organizationId", String.valueOf(commerceOrganizationDisplayContext.getOrganizationId())).build() %>'
	dataProviderKey="<%= CommerceOrganizationUserClayTableDataSetDisplayView.NAME %>"
	id="<%= CommerceOrganizationUserClayTableDataSetDisplayView.NAME %>"
	itemsPerPage="<%= 10 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= commerceOrganizationDisplayContext.getPortletURL() %>"
	showSearch="<%= false %>"
/>

<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, commerceOrganizationDisplayContext.getOrganizationId(), ActionKeys.ASSIGN_MEMBERS) %>">
	<div class="commerce-cta is-visible">
		<aui:button cssClass="btn-lg js-invite-user" onClick='<%= liferayPortletResponse.getNamespace() + "openUserInvitationModal();" %>' primary="<%= true %>" value="invite-user" />
	</div>

	<commerce-ui:user-invitation-modal
		componentId="userInvitationModal"
	/>

	<portlet:actionURL name="/commerce_organization/invite_user" var="inviteUserActionURL" />

	<aui:form action="<%= inviteUserActionURL %>" method="post" name="inviteUserFm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ASSIGN %>" />

		<aui:input
			name="redirect"
			type="hidden"
			value='<%=
				PortletURLBuilder.create(
					currentURLObj
				).setParameter(
					PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL", backURL
				).build()
			%>'
		/>

		<aui:input name="organizationId" type="hidden" value="<%= commerceOrganizationDisplayContext.getOrganizationId() %>" />
		<aui:input name="userId" type="hidden" />
		<aui:input name="userIds" type="hidden" />
		<aui:input name="emailAddresses" type="hidden" />
	</aui:form>

	<aui:script>
		Liferay.provide(
			window,
			'<portlet:namespace />openUserInvitationModal',
			(evt) => {
				var userInvitationModal = Liferay.component('userInvitationModal');
				userInvitationModal.open();
			}
		);

		Liferay.provide(window, 'deleteCommerceOrganizationUser', (id) => {
			document.querySelector('#<portlet:namespace /><%= Constants.CMD %>').value =
				'<%= Constants.REMOVE %>';
			document.querySelector('#<portlet:namespace />userId').value = id;

			submitForm(document.<portlet:namespace />inviteUserFm);
		});

		Liferay.componentReady('userInvitationModal').then((userInvitationModal) => {
			userInvitationModal.on('inviteUserToAccount', (users) => {
				var existingUsersIds = users
					.filter((el) => {
						return el.userId;
					})
					.map((usr) => {
						return usr.userId;
					})
					.join(',');

				var newUsersEmails = users
					.filter((el) => {
						return !el.userId;
					})
					.map((usr) => {
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