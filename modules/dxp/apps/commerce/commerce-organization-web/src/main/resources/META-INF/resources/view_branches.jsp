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