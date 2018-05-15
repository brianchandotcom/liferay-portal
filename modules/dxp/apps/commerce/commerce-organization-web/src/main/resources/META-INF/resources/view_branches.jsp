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
CommerceOrganizationBranchesDisplayContext commerceOrganizationBranchesDisplayContext = (CommerceOrganizationBranchesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<aui:form action="" method="post" name="searchFm">
	<liferay-frontend:management-bar
		includeCheckBox="<%= false %>"
		searchContainerId="organizations"
	>
		<liferay-frontend:management-bar-buttons>
			<c:if test="<%= commerceOrganizationBranchesDisplayContext.hasManageBranchesPermission() %>">
				<liferay-frontend:add-menu
					inline="<%= true %>"
				>
					<liferay-frontend:add-menu-item
						cssClass="add-branch-action"
						title='<%= LanguageUtil.get(request, "add-branch") %>'
						type="<%= AddMenuKeys.AddMenuType.PRIMARY %>"
						url="#"
					/>
				</liferay-frontend:add-menu>
			</c:if>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<li>
				<liferay-portlet:renderURLParams varImpl="searchURL" />

				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</li>
		</liferay-frontend:management-bar-filters>
	</liferay-frontend:management-bar>
</aui:form>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		id="organizations"
		searchContainer="<%= commerceOrganizationBranchesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Organization"
			cssClass="entry-display-style"
			keyProperty="organizationId"
			modelVar="organization"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				value="<%= organization.getName() %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="path"
			>
				<%= HtmlUtil.escape(commerceOrganizationBranchesDisplayContext.getPath(organization)) %> > <strong><%= HtmlUtil.escape(organization.getName()) %></strong>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/branch_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<portlet:actionURL name="addBranch" var="addBranchURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
	<portlet:param name="organizationId" value="<%= String.valueOf(commerceOrganizationBranchesDisplayContext.getCurrentOrganizationId()) %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="type" value="<%= CommerceOrganizationConstants.TYPE_BRANCH %>" />
</portlet:actionURL>

<c:if test="<%= commerceOrganizationBranchesDisplayContext.hasManageBranchesPermission() %>">
	<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">

		var addSiteActionOptionQueryClickHandler = dom.delegate(
			document.body,
			'click',
			'.add-branch-action',
			function(event) {
				modalCommands.openSimpleInputModal(
					{
						dialogTitle: '<liferay-ui:message key="add-branch" />',
						formSubmitURL: '<%= addBranchURL %>',
						idFieldName: 'organizationId',
						idFieldValue: '<%= commerceOrganizationBranchesDisplayContext.getCurrentOrganizationId() %>',
						mainFieldName: 'name',
						mainFieldLabel: '<liferay-ui:message key="name" />',
						namespace: '<portlet:namespace />',
						spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
					}
				);
			}
		);

		function handleDestroyPortlet () {
			addSiteActionOptionQueryClickHandler.removeListener();

			Liferay.detach('destroyPortlet', handleDestroyPortlet);
		}

		Liferay.on('destroyPortlet', handleDestroyPortlet);
	</aui:script>
</c:if>