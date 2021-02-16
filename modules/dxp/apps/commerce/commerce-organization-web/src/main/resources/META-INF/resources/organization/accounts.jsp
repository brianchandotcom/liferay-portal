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

<div class="commerce-organization-container" id="<portlet:namespace />entriesContainer">
	<clay:data-set-display
		contextParams='<%= HashMapBuilder.<String, String>put("organizationId", String.valueOf(commerceOrganizationDisplayContext.getOrganizationId())).build() %>'
		dataProviderKey="<%= CommerceOrganizationAccountClayTableDataSetDisplayView.NAME %>"
		id="<%= CommerceOrganizationAccountClayTableDataSetDisplayView.NAME %>"
		itemsPerPage="<%= 10 %>"
		namespace="<%= liferayPortletResponse.getNamespace() %>"
		pageNumber="<%= 1 %>"
		portletURL="<%= commerceOrganizationDisplayContext.getPortletURL() %>"
		showSearch="<%= false %>"
	/>
</div>

<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, commerceOrganizationDisplayContext.getOrganizationId(), ActionKeys.UPDATE) %>">
	<portlet:actionURL name="/commerce_account_admin/edit_commerce_account_organization_rel" var="editCommerceAccountOrganizationRelActionURL" />

	<aui:form action="<%= editCommerceAccountOrganizationRelActionURL %>" method="post" name="commerceAccountOrganizationRelFm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="organizationId" type="hidden" />
	</aui:form>

	<aui:script>
		Liferay.provide(window, 'deleteCommerceOrganizationAccount', (id) => {
			document.querySelector('#<portlet:namespace /><%= Constants.CMD %>').value =
				'<%= Constants.REMOVE %>';
			document.querySelector('#<portlet:namespace />organizationId').value = id;

			submitForm(document.<portlet:namespace />commerceAccountOrganizationRelFm);
		});
	</aui:script>
</c:if>