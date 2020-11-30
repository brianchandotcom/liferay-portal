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

request.setAttribute("view.jsp-filterPerOrganization", false);
%>

<div class="commerce-organization-container" id="<portlet:namespace />entriesContainer">
	<clay:data-set-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"organizationId", String.valueOf(commerceOrganizationDisplayContext.getOrganizationId())
			).build()
		%>'
		dataProviderKey="<%= CommerceOrganizationClayTableDataSetDisplayView.NAME %>"
		id="<%= CommerceOrganizationClayTableDataSetDisplayView.NAME %>"
		itemsPerPage="<%= 10 %>"
		namespace="<%= liferayPortletResponse.getNamespace() %>"
		pageNumber="<%= 1 %>"
		portletURL="<%= commerceOrganizationDisplayContext.getPortletURL() %>"
		showSearch="<%= false %>"
	/>
</div>

<c:if test="<%= commerceOrganizationDisplayContext.hasAddOrganizationPermissions() %>">
	<div class="commerce-cta is-visible">
		<aui:button cssClass="btn-lg" name="addOrganizationButton" primary="<%= true %>" value="add-organization" />
	</div>

	<portlet:actionURL name="editCommerceOrganization" var="editCommerceOrganizationActionURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="organizationId" value="<%= String.valueOf(commerceOrganizationDisplayContext.getOrganizationId()) %>" />
	</portlet:actionURL>

	<aui:form action="<%= editCommerceOrganizationActionURL %>" method="post" name="organizationFm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="organizationId" type="hidden" />
	</aui:form>

	<aui:script require="frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
		function handleAddOrganizationButtonClick(event) {
			event.preventDefault();

			modalCommands.openSimpleInputModal({
				dialogTitle: '<liferay-ui:message key="add-organization" />',
				formSubmitURL: '<%= editCommerceOrganizationActionURL %>',
				mainFieldLabel: '<liferay-ui:message key="name" />',
				mainFieldName: 'name',
				mainFieldPlaceholder: '<liferay-ui:message key="name" />',
				namespace: '<portlet:namespace />',
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg',
			});
		}

		function handleDestroyPortlet() {
			addOrganizationButton.removeEventListener(
				'click',
				handleAddOrganizationButtonClick
			);

			Liferay.detach('destroyPortlet', handleDestroyPortlet);
		}

		var addOrganizationButton = document.getElementById(
			'<portlet:namespace />addOrganizationButton'
		);

		addOrganizationButton.addEventListener(
			'click',
			handleAddOrganizationButtonClick
		);

		Liferay.on('destroyPortlet', handleDestroyPortlet);
	</aui:script>

	<aui:script>
		Liferay.provide(window, 'deleteCommerceOrganization', function (id) {
			document.querySelector('#<portlet:namespace />organizationId').value = id;

			submitForm(document.<portlet:namespace />organizationFm);
		});
	</aui:script>
</c:if>