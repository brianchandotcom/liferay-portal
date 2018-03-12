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

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceCountries"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceOrganizationBranchesDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceOrganizationBranchesDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceOrganizationBranchesDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= commerceOrganizationBranchesDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceOrganizationBranchesDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<c:if test="<%= commerceOrganizationBranchesDisplayContext.hasManageBranchesPermission() %>">
			<liferay-portlet:renderURL var="addOrganizationURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="addBranch" />
				<portlet:param name="type" value="branch" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</liferay-portlet:renderURL>

			<liferay-frontend:add-menu>
				<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-branch") %>' url='<%= "javascript:"+ renderResponse.getNamespace() +"addBranch('" + addOrganizationURL.toString() + "');" %>' />
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

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
	function <portlet:namespace/>addBranch(uri) {
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
		'closePopup',
		function(dialogId) {
			var dialog = Liferay.Util.Window.getById(dialogId);

			dialog.destroy();
		},
		['liferay-util-window']
	);
</aui:script>