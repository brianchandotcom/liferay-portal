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
String tabs1 = ParamUtil.getString(request, "tabs1", "assignees");
String tabs2 = ParamUtil.getString(request, "tabs2", "users");

long passwordPolicyId = ParamUtil.getLong(request, "passwordPolicyId");

PasswordPolicy passwordPolicy = PasswordPolicyLocalServiceUtil.fetchPasswordPolicy(passwordPolicyId);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PasswordPoliciesAdminPortletKeys.PASSWORD_POLICIES_ADMIN, "display-style", "list");
}
else {
	portalPreferences.setValue(PasswordPoliciesAdminPortletKeys.PASSWORD_POLICIES_ADMIN, "display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));

renderResponse.setTitle(passwordPolicy.getName());

PortletURL homeURL = renderResponse.createRenderURL();

homeURL.setParameter("mvcPath", "/view.jsp");

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "password-policies"), homeURL.toString());
PortalUtil.addPortletBreadcrumbEntry(request, passwordPolicy.getName(), null);

EditPasswordPolicyAssignmentsManagementToolbarDisplayContext editPasswordPolicyAssignmentsManagementToolbarDisplayContext = new EditPasswordPolicyAssignmentsManagementToolbarDisplayContext(request, renderRequest, renderResponse, displayStyle, "/edit_password_policy_assignments.jsp");

SearchContainer searchContainer = editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getSearchContainer();
%>

<liferay-util:include page="/edit_password_policy_tabs.jsp" servletContext="<%= application %>" />

<clay:navigation-bar
	navigationItems="<%= passwordPolicyDisplayContext.getEditPasswordPolicyAssignmentsNavigationItems(editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getPortletURL()) %>"
/>

<clay:management-toolbar
	actionDropdownItems="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getClearResultsURL() %>"
	componentId="editPasswordPolicyAssignmentsManagementToolbar"
	filterDropdownItems="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= searchContainer.getTotal() %>"
	searchActionURL="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="passwordPolicyMembers"
	searchFormName="searchFm"
	selectable="<%= true %>"
	showCreationMenu="<%= true %>"
	showSearch="<%= true %>"
	sortingOrder="<%= searchContainer.getOrderByType() %>"
	sortingURL="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= editPasswordPolicyAssignmentsManagementToolbarDisplayContext.getViewTypeItems() %>"
/>

<portlet:actionURL name="editPasswordPolicyAssignments" var="editPasswordPolicyAssignmentsURL" />

<aui:form action="<%= editPasswordPolicyAssignmentsURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="passwordPolicyId" type="hidden" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>" />

	<div id="breadcrumb">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showPortletBreadcrumb="<%= true %>"
		/>
	</div>

	<liferay-ui:search-container
		id="passwordPolicyMembers"
		searchContainer="<%= searchContainer %>"
		var="memberSearchContainer"
	>
		<c:choose>
			<c:when test='<%= tabs2.equals("users") %>'>
				<aui:input name="addUserIds" type="hidden" />
				<aui:input name="removeUserIds" type="hidden" />

				<%@ include file="/user_search_columns.jspf" %>
			</c:when>
			<c:when test='<%= tabs2.equals("organizations") %>'>
				<aui:input name="addOrganizationIds" type="hidden" />
				<aui:input name="removeOrganizationIds" type="hidden" />

				<%@ include file="/organization_search_columns.jspf" %>
			</c:when>
		</c:choose>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script require="frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
	<portlet:renderURL var="selectMembersURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/select_members.jsp" />
		<portlet:param name="tabs1" value="<%= tabs1 %>" />
		<portlet:param name="tabs2" value="<%= tabs2 %>" />
		<portlet:param name="passwordPolicyId" value="<%= String.valueOf(passwordPolicyId) %>" />
	</portlet:renderURL>

	var addAssignees = function(event) {
		var itemSelectorDialog = new ItemSelectorDialog.default({
			eventName: '<portlet:namespace />selectMember',
			title:
				'<liferay-ui:message arguments="<%= HtmlUtil.escape(passwordPolicy.getName()) %>" key="add-assignees-to-x" />',
			url: '<%= selectMembersURL %>'
		});

		itemSelectorDialog.on('selectedItemChange', function(event) {
			var result = event.selectedItem;

			if (result && result.item) {
				var form = document.getElementById('<portlet:namespace />fm');

				if (form) {
					if (result.memberType == 'users') {
						var addUserIdsInput = form.querySelector(
							'#<portlet:namespace />addUserIds'
						);

						if (addUserIdsInput) {
							addUserIdsInput.setAttribute('value', result.item);
						}
					} else if (result.memberType == 'organizations') {
						var addOrganizationIdsInput = form.querySelector(
							'#<portlet:namespace />addOrganizationIds'
						);

						if (addOrganizationIdsInput) {
							addOrganizationIdsInput.setAttribute(
								'value',
								result.item
							);
						}
					}

					submitForm(form);
				}
			}
		});

		itemSelectorDialog.open();
	};

	Liferay.componentReady('editPasswordPolicyAssignmentsManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on('creationButtonClicked', addAssignees);
		}
	);
</aui:script>

<aui:script>
	function <portlet:namespace />deleteOrganizations() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />'
			)
		) {
			var form = document.getElementById('<portlet:namespace />fm');

			if (form) {
				var removeOrganizationIdsInput = form.querySelector(
					'#<portlet:namespace />removeOrganizationIds'
				);

				if (removeOrganizationIdsInput) {
					removeOrganizationIdsInput.setAttribute(
						'value',
						Liferay.Util.listCheckedExcept(
							form,
							'<portlet:namespace />allRowIds'
						)
					);

					submitForm(form);
				}
			}
		}
	}

	function <portlet:namespace />deleteUsers() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />'
			)
		) {
			var form = document.getElementById('<portlet:namespace />fm');

			if (form) {
				var removeUserIdsInput = form.querySelector(
					'#<portlet:namespace />removeUserIds'
				);

				if (removeUserIdsInput) {
					removeUserIdsInput.setAttribute(
						'value',
						Liferay.Util.listCheckedExcept(
							form,
							'<portlet:namespace />allRowIds'
						)
					);

					submitForm(form);
				}
			}
		}
	}
</aui:script>

<%@ include file="/action/delete_password_policy.jspf" %>