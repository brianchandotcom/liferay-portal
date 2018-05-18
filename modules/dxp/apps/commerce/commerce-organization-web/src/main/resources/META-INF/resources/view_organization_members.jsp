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
CommerceOrganizationMembersDisplayContext commerceOrganizationMembersDisplayContext = (CommerceOrganizationMembersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Organization organization = commerceOrganizationMembersDisplayContext.getCurrentOrganization();
%>

<aui:form action="<%= String.valueOf(commerceOrganizationMembersDisplayContext.getPortletURL()) %>" method="post" name="searchFm">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="users"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "toggleFilter(false);" %>'
				iconCssClass="icon-filter"
				id="filterButton"
				label="filter"
			/>

			<liferay-portlet:renderURL var="addOrganizationURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="addBranch" />
				<portlet:param name="type" value="branch" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</liferay-portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "invite-user") %>'
					type="<%= AddMenuKeys.AddMenuType.PRIMARY %>"
					url="<%= commerceOrganizationMembersDisplayContext.getInviteUserHref() %>"
				/>
			</liferay-frontend:add-menu>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<li>
				<liferay-portlet:renderURLParams varImpl="searchURL" />

				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</li>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "removeUsers();" %>'
				icon="times"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>

	<div class="form-group-autofit hide" id="<portlet:namespace />filterSettings">
		<div class="form-group-item">
			<aui:button cssClass="btn-outline-borderless btn-outline-primary" type="submit" value="apply-filters" />
		</div>
	</div>
</aui:form>

<portlet:actionURL name="inviteUser" var="inviteUserActionURL" />

<aui:form action="<%= inviteUserActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.REMOVE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="organizationId" type="hidden" value="<%= organization.getOrganizationId() %>" />
	<aui:input name="removeUserIds" type="hidden" />

	<div class="container-fluid-1280">
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

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</div>
</aui:form>

<aui:script>
	function <portlet:namespace />inviteUser(uri) {
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
		'<portlet:namespace />closePopup',
		function(dialogId) {
			var dialog = Liferay.Util.Window.getById(dialogId);

			dialog.destroy();
		},
		['liferay-util-window']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />refreshPortlet',
		function() {
			location.href = '<%= currentURL %>';
		},
		['aui-dialog', 'aui-dialog-iframe']
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