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
int inactiveUsersCount = GetterUtil.getInteger(request.getAttribute("view.jsp-inactiveUsersCount"));
Organization organization = (Organization)request.getAttribute("view.jsp-organization");
long organizationId = GetterUtil.getLong(request.getAttribute("view.jsp-organizationId"));
long organizationGroupId = GetterUtil.getLong(request.getAttribute("view.jsp-organizationGroupId"));
PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
int status = GetterUtil.getInteger(request.getAttribute("view.jsp-status"));
String toolbarItem = GetterUtil.getString(request.getAttribute("view.jsp-toolbarItem"));
int usersCount = GetterUtil.getInteger(request.getAttribute("view.jsp-usersCount"));
String usersListView = GetterUtil.getString(request.getAttribute("view.jsp-usersListView"));
String viewUsersRedirect = GetterUtil.getString(request.getAttribute("view.jsp-viewUsersRedirect"));

portletURL.setParameter("mvcRenderCommandName", "/users_admin/view");
portletURL.setParameter("organizationId", String.valueOf(organizationId));
portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("usersListView", usersListView);

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String keywords = ParamUtil.getString(request, "keywords");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

if (organization != null) {
	organizationGroupId = organization.getGroupId();
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
		<liferay-frontend:management-bar
			includeCheckBox="<%= true %>"
		>
			<liferay-frontend:management-bar-filters>
				<liferay-frontend:management-bar-navigation
					navigationKeys='<%= new String[] {"all"} %>'
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
					displayViews='<%= new String[] {"list"} %>'
					portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
					selectedDisplayStyle="<%= displayStyle %>"
				/>
			</liferay-frontend:management-bar-buttons>
		</liferay-frontend:management-bar>

		<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "search();" %>'>
			<liferay-portlet:renderURLParams varImpl="portletURL" />
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="toolbarItem" type="hidden" value="<%= toolbarItem %>" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

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

			<%
			if ((status == WorkflowConstants.STATUS_APPROVED) && (usersCount == 0) && (inactiveUsersCount > 0)) {
				status = WorkflowConstants.STATUS_INACTIVE;
			}
			else if ((status == WorkflowConstants.STATUS_INACTIVE) && (usersCount > 0) && (inactiveUsersCount == 0)) {
				status = WorkflowConstants.STATUS_APPROVED;
			}

			if ((organization != null) && !OrganizationPermissionUtil.contains(permissionChecker, organization, ActionKeys.MANAGE_USERS)) {
				inactiveUsersCount = 0;

				status = WorkflowConstants.STATUS_APPROVED;
			}
			%>

			<aui:input disabled="<%= true %>" name="organizationsRedirect" type="hidden" value="<%= backURL %>" />
			<aui:input name="deleteOrganizationIds" type="hidden" />

			<%
			SearchContainer organizationSearch = new OrganizationSearch(renderRequest, "cur1", currentURLObj);

			organizationSearch.setOrderByType(orderByType);
			%>

			<liferay-ui:search-container
				id="organizations"
				searchContainer="<%= organizationSearch %>"
				var="organizationSearchContainer"
			>

				<%
				OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)organizationSearchContainer.getSearchTerms();

				long parentOrganizationId = _getParentOrganizationId(request, organization, filterManageableOrganizations);

				if (organization != null) {
					parentOrganizationId = organization.getOrganizationId();
				}

				LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

				List<Long> excludedOrganizationIds = new ArrayList<Long>();

				excludedOrganizationIds.add(parentOrganizationId);

				organizationParams.put("excludedOrganizationIds", excludedOrganizationIds);
				%>

				<c:choose>
					<c:when test="<%= !searchTerms.hasSearchTerms() && (parentOrganizationId <= 0) && (filterManageableOrganizations) %>">
						<liferay-ui:search-container-results>

							<%
							total = organizations.size();

							organizationSearchContainer.setTotal(total);

							results = ListUtil.subList(organizations, organizationSearchContainer.getStart(), organizationSearchContainer.getEnd());

							organizationSearchContainer.setResults(results);
							%>

						</liferay-ui:search-container-results>
					</c:when>
					<c:otherwise>

						<%
						if (searchTerms.hasSearchTerms()) {
							if (filterManageableOrganizations) {
								organizationParams.put("organizationsTree", organizations);
							}
							else if (parentOrganizationId > 0) {
								List<Organization> organizationsTree = new ArrayList<Organization>();

								Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(parentOrganizationId);

								organizationsTree.add(parentOrganization);

								organizationParams.put("organizationsTree", organizationsTree);
							}

							parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;
						}
						%>

						<liferay-ui:organization-search-container-results organizationParams="<%= organizationParams %>" parentOrganizationId="<%= parentOrganizationId %>" />
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container>

			<%
			SearchContainer userSearch = new UserSearch(renderRequest, "cur2", currentURLObj);

			userSearch.setOrderByType(orderByType);
			%>

			<liferay-ui:search-container
				id="users"
				searchContainer="<%= userSearch %>"
				var="userSearchContainer"
			>

				<%
				LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

				userParams.put("usersOrgs", Long.valueOf(organizationId));
				%>

				<liferay-ui:user-search-container-results userParams="<%= userParams %>" />
			</liferay-ui:search-container>

			<liferay-ui:search-container
				emptyResultsMessage="no-results-were-found"
				emptyResultsMessageCssClass="taglib-empty-result-message-header-has-plus-btn"
				headerNames="name,type"
				iteratorURL="<%= currentURLObj %>"
				total="<%= organizationSearch.getTotal() + userSearch.getTotal() %>"
				var="membersSearchContainer"
			>

				<%
				List<Object> results = new ArrayList<>();

				results.addAll(organizationSearch.getResults());
				results.addAll(userSearch.getResults());

				membersSearchContainer.setResults(ListUtil.subList(results, membersSearchContainer.getStart(), membersSearchContainer.getEnd()));
				%>

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

					<c:choose>
						<c:when test="<%= curOrganization != null %>">
							<liferay-portlet:renderURL varImpl="rowURL">
								<portlet:param name="mvcRenderCommandName" value="/users_admin/view" />
								<portlet:param name="toolbarItem" value="<%= toolbarItem %>" />
								<portlet:param name="organizationId" value="<%= String.valueOf(curOrganization.getOrganizationId()) %>" />
								<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_TREE %>" />
							</liferay-portlet:renderURL>

							<%
							if (!OrganizationPermissionUtil.contains(permissionChecker, curOrganization, ActionKeys.VIEW)) {
								rowURL = null;
							}
							%>

							<%@ include file="/organization/organization_columns.jspf" %>
						</c:when>
						<c:otherwise>
							<liferay-portlet:renderURL varImpl="rowURL">
								<portlet:param name="mvcRenderCommandName" value="/users_admin/edit_user" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="p_u_i_d" value="<%= String.valueOf(user2.getUserId()) %>" />
							</liferay-portlet:renderURL>

							<%
							if (!UserPermissionUtil.contains(permissionChecker, user2.getUserId(), ActionKeys.UPDATE)) {
								rowURL = null;
							}
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								name="name"
								orderable="<%= true %>"
							>
								<aui:a href="<%= String.valueOf(rowURL) %>"><strong><%= user2.getFullName() %></strong></aui:a>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								name="type"
								value='<%= LanguageUtil.get(request, "user") %>'
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/user_action.jsp"
							/>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator markupView="lexicon" resultRowSplitter="<%= new OrganizationResultRowSplitter() %>" searchContainer="<%= membersSearchContainer %>" />
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
	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />delete', '#<portlet:namespace /><%= searchContainerReference.getId(request, "organizationSearchContainer") %>SearchContainer', document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');
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