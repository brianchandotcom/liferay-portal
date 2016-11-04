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
PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
String toolbarItem = GetterUtil.getString(request.getAttribute("view.jsp-toolbarItem"));
String usersListView = GetterUtil.getString(request.getAttribute("view.jsp-usersListView"));

portletURL.setParameter("mvcRenderCommandName", "/users_admin/view");
portletURL.setParameter("organizationId", String.valueOf(organizationId));
portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("usersListView", usersListView);

String displayStyle = ParamUtil.getString(request, "displayStyle");
String keywords = ParamUtil.getString(request, "keywords");
String navigation = ParamUtil.getString(request, "navigation", "all");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(UsersAdminPortletKeys.USERS_ADMIN, "display-style", "list");
}
else {
	portalPreferences.setValue(UsersAdminPortletKeys.USERS_ADMIN, "display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

portletURL.setParameter("displayStyle", displayStyle);
portletURL.setParameter("keywords", keywords);
portletURL.setParameter("navigation", navigation);

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
		<liferay-frontend:management-bar
			includeCheckBox="<%= true %>"
			searchContainerId="organizationUsers"
		>
			<liferay-frontend:management-bar-filters>
				<liferay-frontend:management-bar-navigation
					navigationKeys='<%= new String[] {"all", "active", "inactive"} %>'
					portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
				/>

				<liferay-frontend:management-bar-sort
					orderByCol='<%= "name" %>'
					orderByType="<%= orderByType %>"
					orderColumns='<%= new String[] {"name"} %>'
					portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
				/>
			</liferay-frontend:management-bar-filters>

			<liferay-frontend:management-bar-buttons>
				<liferay-frontend:management-bar-display-buttons
					displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
					portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
					selectedDisplayStyle="<%= displayStyle %>"
				/>
			</liferay-frontend:management-bar-buttons>

			<liferay-frontend:management-bar-action-buttons>
				<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "delete();" %>' icon="trash" id="deleteOrganizations" label="delete" />
			</liferay-frontend:management-bar-action-buttons>
		</liferay-frontend:management-bar>

		<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "search();" %>'>
			<liferay-portlet:renderURLParams varImpl="portletURL" />
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="toolbarItem" type="hidden" value="<%= toolbarItem %>" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
			<aui:input name="deleteOrganizationIds" type="hidden" />
			<aui:input name="deleteUserIds" type="hidden" />

			<c:if test="<%= organization != null %>">

				<%
				long parentOrganizationId = OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

				if (!organization.isRoot()) {
					Organization parentOrganization = organization.getParentOrganization();

					if (OrganizationPermissionUtil.contains(permissionChecker, parentOrganization, ActionKeys.VIEW)) {
						parentOrganizationId = parentOrganization.getOrganizationId();
					}
				}
				%>

				<portlet:renderURL var="headerBackURL">
					<portlet:param name="mvcRenderCommandName" value="/users_admin/view" />
					<portlet:param name="toolbarItem" value="view-all-organizations" />
					<portlet:param name="organizationId" value="<%= String.valueOf(parentOrganizationId) %>" />
					<portlet:param name="usersListView" value="<%= (parentOrganizationId == OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) ? UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS : UserConstants.LIST_VIEW_TREE %>" />
				</portlet:renderURL>

				<%
				portletDisplay.setShowBackIcon(true);
				portletDisplay.setURLBack(Validator.isNotNull(backURL) ? backURL : headerBackURL.toString());

				renderResponse.setTitle(organization.getName());
				%>

			</c:if>

			<c:if test="<%= (portletName.equals(UsersAdminPortletKeys.USERS_ADMIN) && usersListView.equals(UserConstants.LIST_VIEW_TREE)) || portletName.equals(UsersAdminPortletKeys.MY_ORGANIZATIONS) %>">
				<div id="breadcrumb">
					<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
				</div>
			</c:if>

			<liferay-ui:search-container
				emptyResultsMessage="no-results-were-found"
				emptyResultsMessageCssClass="taglib-empty-result-message-header-has-plus-btn"
				headerNames="name,type,status"
				id="organizationUsers"
				iteratorURL="<%= currentURLObj %>"
				orderByComparator='<%= new OrganizationUserNameComparator(orderByType.equals("asc")) %>'
				orderByType="<%= orderByType %>"
				rowChecker="<%= new OrganizationUserChecker(renderResponse) %>"
			>
				<liferay-ui:search-container-results>

					<%
					int status = WorkflowConstants.STATUS_ANY;

					if (navigation.equals("active")) {
						status = WorkflowConstants.STATUS_APPROVED;
					}
					else if (navigation.equals("inactive")) {
						status = WorkflowConstants.STATUS_INACTIVE;
					}

					if (Validator.isNotNull(keywords)) {
						total = OrganizationLocalServiceUtil.searchOrganizationsAndUsersCount(company.getCompanyId(), organizationId, keywords, status, null);

						Sort[] sorts = new Sort[] {new Sort("name", orderByType.equals("desc")), new Sort("lastName", orderByType.equals("desc"))};

						Hits hits = OrganizationLocalServiceUtil.searchOrganizationsAndUsers(company.getCompanyId(), organizationId, keywords, status, null, searchContainer.getStart(), searchContainer.getEnd(), sorts);

						results = new ArrayList<>(hits.getLength());

						List<SearchResult> searchResults = SearchResultUtil.getSearchResults(hits, locale);

						for (SearchResult searchResult : searchResults) {
							String className = searchResult.getClassName();

							if (className.equals(Organization.class.getName())) {
								results.add(OrganizationLocalServiceUtil.fetchOrganization(searchResult.getClassPK()));
							}
							else if (className.equals(User.class.getName())) {
								results.add(UserLocalServiceUtil.fetchUser(searchResult.getClassPK()));
							}
						}
					}
					else {
						total = OrganizationLocalServiceUtil.getOrganizationsAndUsersCount(company.getCompanyId(), organizationId, status);

						results = OrganizationLocalServiceUtil.getOrganizationsAndUsers(company.getCompanyId(), organizationId, status, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
					}

					searchContainer.setTotal(total);
					searchContainer.setResults(results);
					%>

				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="Object"
					modelVar="result"
				>

					<%
					Organization curOrganization = null;
					User user2 = null;

					if (result instanceof Organization) {
						curOrganization = (Organization)result;
					}
					else {
						user2 = (User)result;
					}
					%>

					<%@ include file="/organization/organization_user_search_columns.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" resultRowSplitter="<%= new OrganizationResultRowSplitter() %>" searchContainer="<%= searchContainer %>" />
			</liferay-ui:search-container>
		</aui:form>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info">
			<liferay-ui:message key="you-do-not-belong-to-an-organization-and-are-not-allowed-to-view-other-organizations" />
		</div>
	</c:otherwise>
</c:choose>

<%@ include file="/add_menu.jspf" %>

<aui:script>
	function <portlet:namespace />delete() {
		<portlet:namespace />deleteOrganizations();
	}

	<portlet:namespace />doDeleteOrganizations = function(organizationIds) {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.attr('method', 'post');
		form.fm('deleteOrganizationIds').val(organizationIds);
		form.fm('deleteUserIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds', '<portlet:namespace />rowIdsUser'));

		submitForm(form, '<portlet:actionURL name="/users_admin/delete_organizations_and_users" />');
	};

	AUI.$('#<portlet:namespace />assignUsers').on(
		'click',
		function(event) {
			<portlet:namespace />openSelectUsersDialog('<%= organizationId %>');
		}
	);
</aui:script>

<%!
private long _getParentOrganizationId(HttpServletRequest request, Organization organization, boolean filterManageableOrganizations) {
	if (organization != null) {
		return organization.getOrganizationId();
	}

	if (filterManageableOrganizations) {
		return OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;
	}

	return ParamUtil.getLong(request, "parentOrganizationId", OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);
}
%>