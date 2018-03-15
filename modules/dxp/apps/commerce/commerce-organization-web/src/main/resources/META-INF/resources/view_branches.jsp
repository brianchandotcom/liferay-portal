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
		includeCheckBox="<%= true %>"
		searchContainerId="organizations"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "toggleFilter(false);" %>' iconCssClass="icon-filter" id="filterButton" label="filter" />

			<c:if test="<%= commerceOrganizationBranchesDisplayContext.hasManageBranchesPermission() %>">
				<liferay-portlet:renderURL var="addOrganizationURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcRenderCommandName" value="addBranch" />
					<portlet:param name="type" value="branch" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</liferay-portlet:renderURL>

				<liferay-frontend:add-menu inline="<%= true %>">
					<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-branch") %>' type="<%= AddMenuKeys.AddMenuType.PRIMARY %>" url='<%= "javascript:"+ renderResponse.getNamespace() +"addBranch('" + addOrganizationURL.toString() + "');" %>' />
				</liferay-frontend:add-menu>
			</c:if>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<li>
				<liferay-portlet:renderURLParams varImpl="searchURL" />

				<liferay-ui:input-search markupView="lexicon" />
			</li>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteBranch();" %>' icon="times" label="delete" />
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>

	<div class="form-group-autofit hide" id="<portlet:namespace />filterSettings">
		<div class="form-group-item">
			<aui:button cssClass="btn-outline-borderless btn-outline-primary" type="submit" value="apply-filters" />
		</div>
	</div>
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

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</div>

<aui:script>
	function <portlet:namespace />addBranch(uri) {
		Liferay.Util.openWindow(
			{
				dialog: {
					centered: true,
					destroyOnClose: true,
					height: 600,
					modal: true,
					width: 600
				},
				dialogIframe: {
					bodyCssClass: 'dialog-with-footer'
				},
				id: 'addBranchDialog',
				title: '<liferay-ui:message key="add-branch" />',
				uri: uri
			}
		);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />closePopup',
		function(dialogId) {
			var dialog = Liferay.Util.Window.getById(dialogId);

			dialog.destroy();
		},
		['liferay-util-window']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />toggleFilter',
		function(state) {
			var A = AUI();

			var filterButton = A.one('#<portlet:namespace />filterButton');
			var filterSettings = A.one('#<portlet:namespace />filterSettings');

			if (filterButton && filterSettings) {
				filterButton.toggleClass('active');

				filterSettings.toggle();
			}
		},
		['aui-base']
	);
</aui:script>