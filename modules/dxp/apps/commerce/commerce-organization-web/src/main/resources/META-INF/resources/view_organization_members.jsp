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
CommerceOrganizationMembersDisplayContext commerceOrganizationMembersDisplayContext = (CommerceOrganizationMembersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Organization organization = commerceOrganizationMembersDisplayContext.getCurrentOrganization();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="users"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceOrganizationMembersDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceOrganizationMembersDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceOrganizationMembersDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= commerceOrganizationMembersDisplayContext.getPortletURL() %>"
		/>

		<li>
			<aui:form action="<%= String.valueOf(commerceOrganizationMembersDisplayContext.getPortletURL()) %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceOrganizationMembersDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<liferay-frontend:add-menu>
			<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "invite-user") %>' url="<%= commerceOrganizationMembersDisplayContext.getInviteUserHref() %>" />
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "removeUsers();" %>' icon="times" label="remove" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="users-container" id="<portlet:namespace />entriesContainer">
	<portlet:actionURL name="inviteUser" var="inviteUserActionURL" />

	<aui:form action="<%= inviteUserActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.REMOVE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="organizationId" type="hidden" value="<%= organization.getOrganizationId() %>" />
		<aui:input name="removeUserIds" type="hidden" />

		<liferay-ui:search-container
			id="users"
			searchContainer="<%= commerceOrganizationMembersDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.User"
				cssClass="entry-display-style"
				keyProperty="userId"
				modelVar="curUser"
			>

				<%
				String userStatus = "inactive";

				if (curUser.getStatus() == WorkflowConstants.STATUS_APPROVED) {
					userStatus = "active";
				}
				%>

				<liferay-ui:search-container-column-text
					cssClass="important table-cell-content"
					name="name"
					orderable="<%= true %>"
					value="<%= curUser.getFullName() %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="status"
					value="<%= LanguageUtil.get(request, userStatus) %>"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/member_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace/>inviteUser(uri) {
		Liferay.Util.openWindow(
			{
				dialog: {
					centered: true,
					destroyOnClose: true,
					height: 600,
					modal: true,
					width: 700
				},
				dialogIframe: {
					bodyCssClass: 'dialog-with-footer'
				},
				id: 'inviteUserDialog',
				title: '<liferay-ui:message key="invite-users" />',
				uri: uri
			}
		);
	}

	function <portlet:namespace />removeUsers() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-remove-the-selected-users" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('removeUserIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
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