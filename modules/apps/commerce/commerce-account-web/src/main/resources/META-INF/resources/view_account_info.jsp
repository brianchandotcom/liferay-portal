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
%>

<clay:data-set-display
	contextParams='<%= HashMapBuilder.<String, String>put("commerceAccountId", String.valueOf(commerceAccount.getCommerceAccountId())).build() %>'
	dataProviderKey="<%= CommerceAccountOrganizationClayDataSetDataSetDisplayView.NAME %>"
	id="<%= CommerceAccountOrganizationClayDataSetDataSetDisplayView.NAME %>"
	itemsPerPage="<%= 10 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= commerceAccountDisplayContext.getPortletURL() %>"
	style="stacked"
/>

<c:if test="<%= commerceAccountDisplayContext.hasCommerceAccountModelPermissions(CommerceAccountActionKeys.MANAGE_ORGANIZATIONS) %>">
	<div class="commerce-cta is-visible">
		<aui:button cssClass="btn-lg js-invite-user" onClick='<%= liferayPortletResponse.getNamespace() + "openAddOrganizationsModal();" %>' primary="<%= true %>" value="add-organizations" />
	</div>

	<commerce-ui:add-organizations-modal
		componentId="addOrganizationsModal"
	/>

	<portlet:actionURL name="/commerce_account/edit_commerce_account_organization_rel" var="editCommerceAccountOrganizationRelActionURL" />

	<aui:form action="<%= editCommerceAccountOrganizationRelActionURL %>" method="post" name="commerceAccountOrganizationRelFm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ASSIGN %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceAccountId" type="hidden" value="<%= commerceAccountDisplayContext.getCurrentCommerceAccountId() %>" />
		<aui:input name="addOrganizationIds" type="hidden" />
		<aui:input name="organizationId" type="hidden" />
	</aui:form>

	<aui:script>
		Liferay.provide(
			window,
			'<portlet:namespace />openAddOrganizationsModal',
			(evt) => {
				var addOrganizationsModal = Liferay.component('addOrganizationsModal');

				addOrganizationsModal.open();
			}
		);

		Liferay.provide(window, 'deleteCommerceAccountOrganization', (id) => {
			document.querySelector('#<portlet:namespace /><%= Constants.CMD %>').value =
				'<%= Constants.REMOVE %>';
			document.querySelector('#<portlet:namespace />organizationId').value = id;

			submitForm(document.<portlet:namespace />commerceAccountOrganizationRelFm);
		});

		Liferay.componentReady('addOrganizationsModal').then(
			(addOrganizationsModal) => {
				addOrganizationsModal.on('addOrganization', (event) => {
					var orgIds = event
						.map((org) => {
							return org.id;
						})
						.join(',');

					document.querySelector(
						'#<portlet:namespace />addOrganizationIds'
					).value = orgIds;

					addOrganizationsModal.close();

					submitForm(
						document.<portlet:namespace />commerceAccountOrganizationRelFm
					);
				});
			}
		);
	</aui:script>
</c:if>