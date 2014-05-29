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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

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

String keywords = ParamUtil.getString(request, "keywords");

if (organization != null) {
	organizationGroupId = organization.getGroupId();
}

LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

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

if (organization != null) {
	UsersAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);
}
%>

<c:if test="<%= portletName.equals(PortletKeys.USERS_ADMIN) && usersListView.equals(UserConstants.LIST_VIEW_TREE) %>">
	<aui:nav cssClass="nav-tabs">
		<portlet:renderURL var="viewUsersTreeURL">
			<portlet:param name="struts_action" value="/users_admin/view" />
			<portlet:param name="toolbarItem" value="browse" />
			<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_TREE %>" />
			<portlet:param name="saveUsersListView" value="<%= Boolean.TRUE.toString() %>" />
		</portlet:renderURL>

		<aui:nav-item href="<%= viewUsersTreeURL %>" label="browse" selected='<%= toolbarItem.equals("browse") %>' />

		<portlet:renderURL var="viewOrganizationsFlatURL">
			<portlet:param name="struts_action" value="/users_admin/view" />
			<portlet:param name="toolbarItem" value="view-all-organizations" />
			<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS %>" />
			<portlet:param name="saveUsersListView" value="<%= Boolean.TRUE.toString() %>" />
		</portlet:renderURL>

		<aui:nav-item href="<%= viewOrganizationsFlatURL %>" label="all-organizations" selected='<%= toolbarItem.equals("view-all-organizations") %>' />

		<portlet:renderURL var="viewUsersFlatURL">
			<portlet:param name="struts_action" value="/users_admin/view" />
			<portlet:param name="toolbarItem" value="view-all-users" />
			<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_FLAT_USERS %>" />
			<portlet:param name="saveUsersListView" value="<%= Boolean.TRUE.toString() %>" />
		</portlet:renderURL>

		<aui:nav-item href="<%= viewUsersFlatURL %>" label="all-users" selected='<%= toolbarItem.equals("view-all-users") %>' />
	</aui:nav>

	<aui:nav-bar>
		<liferay-util:include page="/html/portlet/users_admin/toolbar.jsp" />

		<aui:nav-bar-search>
			<div class="col-xs-12 form-search">
				<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" />
			</div>
		</aui:nav-bar-search>
	</aui:nav-bar>

	<div id="breadcrumb">
		<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
	</div>
</c:if>

<c:choose>
	<c:when test="<%= showList %>">

		<%
		int organizationsCount = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), _getParentOrganizationId(request, organization, filterManageableOrganizations), null, null, null, null, organizationParams);
		%>

		<c:if test="<%= (organization != null) %>">

			<%
			long parentOrganizationId = OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;
			String parentOrganizationName = LanguageUtil.get(pageContext, "users-and-organizations-home");

			if (!organization.isRoot()) {
				Organization parentOrganization = organization.getParentOrganization();

				if (OrganizationPermissionUtil.contains(permissionChecker, parentOrganization, ActionKeys.VIEW)) {
					parentOrganizationId = parentOrganization.getOrganizationId();
					parentOrganizationName = parentOrganization.getName();
				}
			}
			%>

			<portlet:renderURL var="headerBackURL">
				<portlet:param name="struts_action" value="/users_admin/view" />
				<portlet:param name="toolbarItem" value="<%= toolbarItem %>" />
				<portlet:param name="organizationId" value="<%= String.valueOf(parentOrganizationId) %>" />
			</portlet:renderURL>

			<liferay-ui:header
				backLabel="<%= parentOrganizationName %>"
				backURL="<%= Validator.isNotNull(backURL) ? backURL : headerBackURL.toString() %>"
				localizeTitle="<%= false %>"
				title="<%= organization.getName() %>"
			/>
		</c:if>

		<aui:row>
			<aui:col cssClass="lfr-asset-column lfr-asset-column-details" width="<%= (organization != null) ? 75 : 100 %>">
				<liferay-ui:panel-container extended="<%= false %>" id="usersAdminOrganizationPanelContainer" persistState="<%= true %>">
					<c:if test="<%= organization != null %>">
						<aui:input name="organizationId" type="hidden" value="<%= organizationId %>" />

						<span class="entry-categories">
							<liferay-ui:asset-categories-summary
								className="<%= Organization.class.getName() %>"
								classPK="<%= organization.getOrganizationId() %>"
								portletURL="<%= renderResponse.createRenderURL() %>"
							/>
						</span>

						<span class="entry-tags">
							<liferay-ui:asset-tags-summary
								className="<%= Organization.class.getName() %>"
								classPK="<%= organization.getOrganizationId() %>"
								portletURL="<%= renderResponse.createRenderURL() %>"
							/>
						</span>

						<%
						request.setAttribute(WebKeys.ORGANIZATION, organization);

						request.setAttribute("addresses.className", Organization.class.getName());
						request.setAttribute("addresses.classPK", organizationId);
						request.setAttribute("emailAddresses.className", Organization.class.getName());
						request.setAttribute("emailAddresses.classPK", organizationId);
						request.setAttribute("phones.className", Organization.class.getName());
						request.setAttribute("phones.classPK", organizationId);
						request.setAttribute("websites.className", Organization.class.getName());
						request.setAttribute("websites.classPK", organizationId);
						%>

						<div class="organization-information">
							<div class="section">
								<liferay-util:include page="/html/portlet/directory/common/additional_email_addresses.jsp" />
							</div>

							<div class="section">
								<liferay-util:include page="/html/portlet/directory/common/websites.jsp" />
							</div>

							<div class="section">
								<liferay-util:include page="/html/portlet/directory/organization/addresses.jsp" />
							</div>

							<div class="section">
								<liferay-util:include page="/html/portlet/directory/organization/phone_numbers.jsp" />
							</div>

							<div class="section">
								<liferay-util:include page="/html/portlet/directory/organization/comments.jsp" />
							</div>
						</div>

						<br />

						<liferay-ui:custom-attributes-available className="<%= Organization.class.getName() %>">
							<liferay-ui:custom-attribute-list
								className="<%= Organization.class.getName() %>"
								classPK="<%= organization.getOrganizationId() %>"
								editable="<%= false %>"
								label="<%= true %>"
							/>
						</liferay-ui:custom-attributes-available>
					</c:if>

					<%
					boolean showOrganizations = false;
					boolean showUsers = true;

					if ((organization == null) && !PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_USER) && !PortalPermissionUtil.contains(permissionChecker, ActionKeys.IMPERSONATE)) {
						showOrganizations = true;
						showUsers = false;
					}

					if (organizationsCount > 0) {
						showOrganizations = true;
					}

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

					<aui:input disabled="<%= true %>" name="organizationsRedirect" type="hidden" value="<%= portletURL.toString() %>" />
					<aui:input name="deleteOrganizationIds" type="hidden" />
					<aui:input name="status" type="hidden" value="<%= status %>" />

					<c:if test="<%= showOrganizations %>">
						<liferay-util:buffer var="organizationsPanelTitle">

							<%
							String organizationsTitle = null;

							if (Validator.isNotNull(keywords)) {
								organizationsTitle = LanguageUtil.get(pageContext, "organizations");
							}
							else if (organization == null) {
								organizationsTitle = LanguageUtil.get(pageContext, filterManageableOrganizations ? "my-organizations" : "top-level-organizations");
							}
							else if (organizationsCount == 1) {
								organizationsTitle = LanguageUtil.format(pageContext, "x-suborganization", String.valueOf(organizationsCount), false);
							}
							else {
								organizationsTitle = LanguageUtil.format(pageContext, "x-suborganizations", String.valueOf(organizationsCount), false);
							}
							%>

							<%= organizationsTitle %>

						</liferay-util:buffer>

						<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="usersAdminOrganizationsPanel" persistState="<%= true %>" title="<%= organizationsPanelTitle %>">

							<%
							SearchContainer searchContainer = new OrganizationSearch(renderRequest, "cur1", currentURLObj);

							RowChecker rowChecker = new RowChecker(renderResponse);

							rowChecker.setRowIds("rowIdsOrganization");

							searchContainer.setRowChecker(rowChecker);
							%>

							<liferay-ui:search-container
								searchContainer="<%= searchContainer %>"
								var="organizationSearchContainer"
							>

								<%
								OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)organizationSearchContainer.getSearchTerms();

								long parentOrganizationId = _getParentOrganizationId(request, organization, filterManageableOrganizations);

								if (organization != null) {
									parentOrganizationId = organization.getOrganizationId();
								}

								List<Long> excludedOrganizationIds = new ArrayList<Long>();

								excludedOrganizationIds.add(parentOrganizationId);

								organizationParams.put("excludedOrganizationIds", excludedOrganizationIds);
								%>

								<liferay-ui:search-container-results>
									<c:choose>
										<c:when test="<%= !searchTerms.hasSearchTerms() && (parentOrganizationId <= 0) && (filterManageableOrganizations) %>">

											<%
											total = organizations.size();

											searchContainer.setTotal(total);

											results = ListUtil.subList(organizations, searchContainer.getStart(), searchContainer.getEnd());

											searchContainer.setResults(results);
											%>

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

											<c:choose>
												<c:when test="<%= PropsValues.ORGANIZATIONS_INDEXER_ENABLED && PropsValues.ORGANIZATIONS_SEARCH_WITH_INDEX %>">
													<%@ include file="/html/portlet/users_admin/organization_search_results_index.jspf" %>
												</c:when>
												<c:otherwise>
													<%@ include file="/html/portlet/users_admin/organization_search_results_database.jspf" %>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>

									<c:if test="<%= !results.isEmpty() %>">
										<aui:button cssClass="delete-organizations" disabled="<%= true %>" name="delete" onClick='<%= renderResponse.getNamespace() + "deleteOrganizations();" %>' value="delete" />
									</c:if>
								</liferay-ui:search-container-results>

								<liferay-ui:search-container-row
									className="com.liferay.portal.model.Organization"
									escapedModel="<%= true %>"
									keyProperty="organizationId"
									modelVar="curOrganization"
								>
									<liferay-portlet:renderURL varImpl="rowURL">
										<portlet:param name="struts_action" value="/users_admin/view" />
										<portlet:param name="toolbarItem" value="<%= toolbarItem %>" />
										<portlet:param name="organizationId" value="<%= String.valueOf(curOrganization.getOrganizationId()) %>" />
										<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_TREE %>" />
									</liferay-portlet:renderURL>

									<%
									if (!OrganizationPermissionUtil.contains(permissionChecker, curOrganization, ActionKeys.VIEW)) {
										rowURL = null;
									}
									%>

									<%@ include file="/html/portlet/users_admin/organization/organization_columns.jspf" %>
								</liferay-ui:search-container-row>

								<liferay-ui:search-iterator />
							</liferay-ui:search-container>
						</liferay-ui:panel>
					</c:if>

					<c:if test="<%= showUsers %>">
						<liferay-util:buffer var="usersPanelTitle">

							<%
							boolean active = false;

							if (status == WorkflowConstants.STATUS_APPROVED) {
								active = true;
							}

							String usersTitle = null;

							if (Validator.isNotNull(keywords) || ((organization == null) && (organizationsCount == 0))) {
								usersTitle = LanguageUtil.get(pageContext, (active ? "users" : "inactive-users"));
							}
							else if (organization == null) {
								usersTitle = LanguageUtil.get(pageContext, (active ? "users-without-an-organization" : "inactive-users-without-an-organization"));
							}
							else if ((usersCount == 0) && (inactiveUsersCount == 0)) {
								usersTitle = StringPool.BLANK;
							}
							else {
								if ((active && (usersCount == 1)) || (!active && (inactiveUsersCount == 1))) {
									usersTitle = LanguageUtil.format(pageContext, (active ? "x-user" : "x-inactive-user"), String.valueOf((active ? usersCount : inactiveUsersCount)), false);
								}
								else {
									usersTitle = LanguageUtil.format(pageContext, (active ? "x-users" : "x-inactive-users"), String.valueOf((active ? usersCount : inactiveUsersCount)), false);
								}
							}
							%>

							<%= usersTitle %>
						</liferay-util:buffer>

						<c:if test="<%= (organization != null) || (usersCount != 0) || (inactiveUsersCount == 0) %>">

							<%
							boolean organizationContextView = true;
							%>

							<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="usersAdminUsersPanel" persistState="<%= true %>" title="<%= usersPanelTitle %>">
								<%@ include file="/html/portlet/users_admin/view_flat_users.jspf" %>
							</liferay-ui:panel>
						</c:if>
					</c:if>
				</liferay-ui:panel-container>
			</aui:col>

			<c:if test="<%= organization != null %>">
				<aui:col cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>" width="<%= 25 %>">
					<div class="lfr-asset-summary">
						<img alt="<%= HtmlUtil.escape(organization.getName()) %>" class="avatar" src='<%= (organization != null) ? themeDisplay.getPathImage() + "/organization_logo?img_id=" + organization.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(organization.getLogoId()) : "" %>' />

						<div class="lfr-asset-name">
							<h4><%= HtmlUtil.escape(organization.getName()) %></h4>
						</div>
					</div>

					<%
					request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

					request.setAttribute("view_organizations_tree.jsp-organization", organization);
					%>

					<liferay-util:include page="/html/portlet/users_admin/organization_action.jsp" />
				</aui:col>

			</c:if>
		</aui:row>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info">
			<liferay-ui:message key="you-do-not-belong-to-an-organization-and-are-not-allowed-to-view-other-organizations" />
		</div>
	</c:otherwise>
</c:choose>

<aui:script>
	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />delete', '#<portlet:namespace /><%= searchContainerReference.getId("organizationSearchContainer") %>SearchContainer', document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');
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