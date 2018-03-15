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
AddBranchDisplayContext addBranchDisplayContext = (AddBranchDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Organization organization = addBranchDisplayContext.getCurrentOrganization();
%>

<portlet:actionURL name="addBranch" var="addBranchActionURL" />

<aui:form action="<%= addBranchActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="organizationId" type="hidden" value="<%= organization.getOrganizationId() %>" />

	<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

	<aui:model-context bean="<%= null %>" model="<%= Organization.class %>" />

	<div class="lfr-form-content">
		<aui:container fluid="<%= true %>">
			<liferay-ui:error exception="<%= DuplicateOrganizationException.class %>" message="the-organization-name-is-already-taken" />

			<liferay-ui:error exception="<%= OrganizationNameException.class %>">
				<liferay-ui:message arguments="<%= new String[] {OrganizationConstants.NAME_LABEL, OrganizationConstants.NAME_GENERAL_RESTRICTIONS, OrganizationConstants.NAME_RESERVED_WORDS} %>" key="the-x-cannot-be-x-or-a-reserved-word-such-as-x" />
			</liferay-ui:error>

			<aui:row>
				<aui:col width="<%= 100 %>">
					<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" />

					<aui:input name="type" type="hidden" value="branch" />
				</aui:col>
			</aui:row>
		</aui:container>
	</div>

	<aui:button-row>
		<aui:button name="save" onClick='<%= renderResponse.getNamespace() + "submitFm();" %>' primary="<%= true %>" value="save" />

		<aui:button name="cancel" onClick='<%= renderResponse.getNamespace() + "closeDialog();" %>' value="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />closeDialog() {
		Liferay.Util.getOpener().<portlet:namespace />closePopup('addBranchDialog');
	}

	Liferay.provide(
		window,
		'<portlet:namespace />submitFm',
		function() {
			var A = AUI();

			var url = '<%= addBranchActionURL.toString() %>';

			A.io.request(
				url,
				{
					form: {
						id: '<portlet:namespace />fm'
					},
					method: 'POST',
					on: {
						success: function() {
							<portlet:namespace />closeDialog();

							Liferay.Util.getOpener().Liferay.Portlet.refresh('#p_p_id<portlet:namespace />');
						}
					}
				}
			);
		},
		['aui-io-request']
	);
</aui:script>