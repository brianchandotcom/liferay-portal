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
String backURL = GetterUtil.getString(request.getAttribute("view.jsp-backURL"));
Organization organization = (Organization)request.getAttribute("view.jsp-organization");
long organizationId = GetterUtil.getLong(request.getAttribute("view.jsp-organizationId"));
String toolbarItem = GetterUtil.getString(request.getAttribute("view.jsp-toolbarItem"));
String usersListView = GetterUtil.getString(request.getAttribute("view.jsp-usersListView"));

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(UsersAdminPortletKeys.USERS_ADMIN, "display-style", "list");
}
else {
	portalPreferences.setValue(UsersAdminPortletKeys.USERS_ADMIN, "display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

List<Organization> organizations = new ArrayList<Organization>();

if (filterManageableOrganizations) {
	organizations = user.getOrganizations(true);
}

if (organizationId != OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {
	organizations.clear();

	organizations.add(OrganizationLocalServiceUtil.getOrganization(organizationId));
}

boolean showList = true;

if (filterManageableOrganizations && organizations.isEmpty()) {
	showList = false;
}

PortletURL homeURL = renderResponse.createRenderURL();

homeURL.setParameter("mvcPath", "/view.jsp");
homeURL.setParameter("toolbarItem", "view-all-organizations");
homeURL.setParameter("usersListView", UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "users-and-organizations"), homeURL.toString());

if (organization != null) {
	UsersAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);
}
%>

<c:choose>
	<c:when test="<%= showList %>">

		<%
		ViewTreeManagementToolbarDisplayContext viewTreeManagementToolbarDisplayContext = new ViewTreeManagementToolbarDisplayContext(request, renderRequest, renderResponse, organization, displayStyle);

		SearchContainer searchContainer = viewTreeManagementToolbarDisplayContext.getSearchContainer();
		%>

		<clay:management-toolbar
			actionDropdownItems="<%= viewTreeManagementToolbarDisplayContext.getActionDropdownItems() %>"
			clearResultsURL="<%= viewTreeManagementToolbarDisplayContext.getClearResultsURL() %>"
			componentId="viewTreeManagementToolbar"
			creationMenu="<%= viewTreeManagementToolbarDisplayContext.getCreationMenu() %>"
			filterDropdownItems="<%= viewTreeManagementToolbarDisplayContext.getFilterDropdownItems() %>"
			filterLabelItems="<%= viewTreeManagementToolbarDisplayContext.getFilterLabelItems() %>"
			itemsTotal="<%= searchContainer.getTotal() %>"
			searchActionURL="<%= viewTreeManagementToolbarDisplayContext.getSearchActionURL() %>"
			searchContainerId="organizationUsers"
			searchFormName="searchFm"
			selectable="<%= true %>"
			showCreationMenu="<%= viewTreeManagementToolbarDisplayContext.showCreationMenu() %>"
			showSearch="<%= true %>"
			sortingOrder="<%= searchContainer.getOrderByType() %>"
			sortingURL="<%= viewTreeManagementToolbarDisplayContext.getSortingURL() %>"
			viewTypeItems="<%= viewTreeManagementToolbarDisplayContext.getViewTypeItems() %>"
		/>

		<aui:form cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "search();" %>'>
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="toolbarItem" type="hidden" value="<%= toolbarItem %>" />
			<aui:input name="redirect" type="hidden" value="<%= viewTreeManagementToolbarDisplayContext.getPortletURL().toString() %>" />
			<aui:input name="onErrorRedirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteOrganizationIds" type="hidden" />
			<aui:input name="deleteUserIds" type="hidden" />
			<aui:input name="removeOrganizationIds" type="hidden" />
			<aui:input name="removeUserIds" type="hidden" />

			<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-organizations-that-have-suborganizations-or-users" />
			<liferay-ui:error exception="<%= RequiredUserException.class %>" message="you-cannot-delete-or-deactivate-yourself" />

			<c:if test="<%= organization != null %>">

				<%
				portletDisplay.setShowBackIcon(true);
				portletDisplay.setURLBack(Validator.isNotNull(backURL) ? backURL : UsersAdminPortletURLUtil.createParentOrganizationViewTreeURL(organizationId, renderResponse));

				renderResponse.setTitle(organization.getName());
				%>

			</c:if>

			<c:if test="<%= (portletName.equals(UsersAdminPortletKeys.USERS_ADMIN) && usersListView.equals(UserConstants.LIST_VIEW_TREE)) || portletName.equals(UsersAdminPortletKeys.MY_ORGANIZATIONS) %>">
				<div id="breadcrumb">
					<liferay-ui:breadcrumb
						showCurrentGroup="<%= false %>"
						showGuestGroup="<%= false %>"
						showLayout="<%= false %>"
						showPortletBreadcrumb="<%= true %>"
					/>
				</div>
			</c:if>

			<liferay-ui:search-container
				id="organizationUsers"
				searchContainer="<%= searchContainer %>"
				var="organizationUserSearchContainer"
			>
				<liferay-ui:search-container-row
					className="Object"
					modelVar="result"
				>

					<%
					Organization curOrganization = null;
					Map<String, Object> rowData = new HashMap<String, Object>();
					User user2 = null;

					if (result instanceof Organization) {
						curOrganization = (Organization)result;

						rowData.put("actions", StringUtil.merge(viewTreeManagementToolbarDisplayContext.getAvailableActions(curOrganization)));
					}
					else {
						user2 = (User)result;

						rowData.put("actions", StringUtil.merge(viewTreeManagementToolbarDisplayContext.getAvailableActions(user2)));
					}

					row.setData(rowData);
					%>

					<%@ include file="/organization/organization_user_search_columns.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="<%= displayStyle %>"
					markupView="lexicon"
					resultRowSplitter="<%= new OrganizationResultRowSplitter() %>"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</c:when>
	<c:otherwise>
		<clay:alert
			message='<%= LanguageUtil.get(request, "you-do-not-belong-to-an-organization-and-are-not-allowed-to-view-other-organizations") %>'
			style="info"
			title='<%= LanguageUtil.get(request, "info") + ":" %>'
		/>
	</c:otherwise>
</c:choose>

<aui:script>
	function <portlet:namespace />delete(organizationsRedirect) {
		<portlet:namespace />deleteOrganizations(organizationsRedirect);
	}

	<portlet:namespace />doDeleteOrganizations = function(
		organizationIds,
		organizationsRedirect
	) {
		var form = document.<portlet:namespace />fm;

		if (organizationsRedirect) {
			Liferay.Util.setFormValues(form, {
				redirect: organizationsRedirect
			});
		}

		Liferay.Util.postForm(form, {
			data: {
				deleteOrganizationIds: organizationIds,
				deleteUserIds: Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds',
					'<portlet:namespace />rowIdsUser'
				)
			},
			url:
				'<portlet:actionURL name="/users_admin/delete_organizations_and_users" />'
		});
	};

	<portlet:actionURL name="/users_admin/edit_organization_assignments" var="removeOrganizationsAndUsersURL">
		<portlet:param name="assignmentsRedirect" value="<%= currentURL %>" />
		<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
	</portlet:actionURL>

	function <portlet:namespace />removeOrganizationsAndUsers() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.postForm(form, {
			data: {
				removeOrganizationIds: Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds',
					'<portlet:namespace />rowIdsOrganization'
				),
				removeUserIds: Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds',
					'<portlet:namespace />rowIdsUser'
				)
			},
			url: '<%= removeOrganizationsAndUsersURL.toString() %>'
		});
	}

	var selectUsers = function(organizationId) {
		<portlet:namespace />openSelectUsersDialog(organizationId);
	};

	var ACTIONS = {
		selectUsers: selectUsers
	};

	Liferay.componentReady('viewTreeManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on('creationMenuItemClicked', function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action](itemData.organizationId);
			}
		});
	});
</aui:script>