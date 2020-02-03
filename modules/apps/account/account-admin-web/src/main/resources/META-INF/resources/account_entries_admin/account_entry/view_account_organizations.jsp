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
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);

SearchContainer accountOrganizationSearchContainer = AccountOrganizationSearchContainerFactory.create(accountEntryDisplay.getAccountEntryId(), liferayPortletRequest, liferayPortletResponse);

ViewAccountOrganizationsManagementToolbarDisplayContext viewAccountOrganizationsManagementToolbarDisplayContext = new ViewAccountOrganizationsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountOrganizationSearchContainer);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL())));

renderResponse.setTitle((accountEntryDisplay == null) ? "" : accountEntryDisplay.getName());
%>

<clay:management-toolbar
	displayContext="<%= viewAccountOrganizationsManagementToolbarDisplayContext %>"
/>

<aui:container cssClass="container-fluid container-fluid-max-xl">
	<aui:form method="post" name="fm">
		<aui:input name="accountEntryId" type="hidden" value="<%= accountEntryDisplay.getAccountEntryId() %>" />
		<aui:input name="accountOrganizationIds" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= accountOrganizationSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.Organization"
				keyProperty="organizationId"
				modelVar="accountOrganization"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					name="name"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					name="parent-organization"
					property="parentOrganizationName"
				/>

				<liferay-ui:search-container-column-text>
					<portlet:actionURL name="/account_admin/remove_account_organizations" var="removeAccountOrganizationsURL">
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
						<portlet:param name="accountOrganizationIds" value="<%= String.valueOf(accountOrganization.getOrganizationId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon-delete
						confirmation="are-you-sure-you-want-to-remove-this-organization"
						icon="times-circle"
						message="remove"
						showIcon="<%= true %>"
						url="<%= removeAccountOrganizationsURL %>"
					/>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</aui:container>

<liferay-frontend:component
	componentId="<%= viewAccountOrganizationsManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="account_entries_admin/js/AccountOrganizationsManagementToolbarDefaultEventHandler.es"
/>