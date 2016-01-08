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
String usersListView = (String)request.getAttribute("view.jsp-usersListView");

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

boolean showList = true;

if (filterManageableOrganizations) {
	List<Organization> userOrganizations = user.getOrganizations(true);

	if (userOrganizations.isEmpty()) {
		showList = false;
	}
	else {
		organizationParams.put("organizationsTree", userOrganizations);
	}
}
%>

<c:choose>
	<c:when test="<%= showList %>">

		<%
		SearchContainer searchContainer = new OrganizationSearch(renderRequest, portletURL);

		RowChecker rowChecker = new RowChecker(renderResponse);

		rowChecker.setRowIds("rowIdsOrganization");

		searchContainer.setRowChecker(rowChecker);
		%>

		<liferay-ui:search-container
			searchContainer="<%= searchContainer %>"
			var="organizationSearchContainer"
		>
			<aui:input disabled="<%= true %>" name="organizationsRedirect" type="hidden" value="<%= portletURL.toString() %>" />
			<aui:input name="deleteOrganizationIds" type="hidden" />

			<c:if test="<%= usersListView.equals(UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS) %>">
				<aui:nav cssClass="nav-tabs">
					<portlet:renderURL var="viewUsersTreeURL">
						<portlet:param name="mvcRenderCommandName" value="/users_admin/view" />
						<portlet:param name="toolbarItem" value="browse" />
						<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_TREE %>" />
						<portlet:param name="saveUsersListView" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:renderURL>

					<aui:nav-item href="<%= viewUsersTreeURL %>" label="browse" />

					<portlet:renderURL var="viewOrganizationsFlatURL">
						<portlet:param name="mvcRenderCommandName" value="/users_admin/view" />
						<portlet:param name="toolbarItem" value="view-all-organizations" />
						<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS %>" />
						<portlet:param name="saveUsersListView" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:renderURL>

					<aui:nav-item href="<%= viewOrganizationsFlatURL %>" label="all-organizations" selected="<%= true %>" />

					<portlet:renderURL var="viewUsersFlatURL">
						<portlet:param name="mvcRenderCommandName" value="/users_admin/view" />
						<portlet:param name="toolbarItem" value="view-all-users" />
						<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_FLAT_USERS %>" />
						<portlet:param name="saveUsersListView" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:renderURL>

					<aui:nav-item href="<%= viewUsersFlatURL %>" label="all-users" />
				</aui:nav>

				<aui:nav-bar>

					<%
					request.setAttribute(WebKeys.SEARCH_CONTAINER, organizationSearchContainer);
					%>

					<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />

					<aui:nav-bar-search>
						<liferay-ui:organization-search-form />
					</aui:nav-bar-search>
				</aui:nav-bar>

				<div id="breadcrumb">
					<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
				</div>
			</c:if>

			<%
			OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)organizationSearchContainer.getSearchTerms();

			long parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationId", OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

			if (parentOrganizationId <= 0) {
				parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;
			}
			%>

			<liferay-ui:organization-search-container-results organizationParams="<%= organizationParams %>" parentOrganizationId="<%= parentOrganizationId %>" />

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.Organization"
				escapedModel="<%= true %>"
				keyProperty="organizationId"
				modelVar="organization"
			>
				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/users_admin/view" />
					<portlet:param name="toolbarItem" value="view-all-organizations" />
					<portlet:param name="redirect" value="<%= organizationSearchContainer.getIteratorURL().toString() %>" />
					<portlet:param name="organizationId" value="<%= String.valueOf(organization.getOrganizationId()) %>" />
					<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_TREE %>" />
				</liferay-portlet:renderURL>

				<%
				if (!OrganizationPermissionUtil.contains(permissionChecker, organization, ActionKeys.VIEW)) {
					rowURL = null;
				}
				%>

				<%@ include file="/organization/search_columns.jspf" %>

				<liferay-ui:search-container-column-jsp
					align="right"
					cssClass="entry-action"
					path="/organization_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<%
			List<Organization> results = searchContainer.getResults();
			%>

			<c:if test="<%= !results.isEmpty() %>">
				<div class="separator"><!-- --></div>

				<aui:button cssClass="delete-organizations" disabled="<%= true %>" name="delete" onClick='<%= renderResponse.getNamespace() + "deleteOrganizations();" %>' value="delete" />
			</c:if>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info">
			<liferay-ui:message key="you-do-not-belong-to-an-organization-and-are-not-allowed-to-view-other-organizations" />
		</div>
	</c:otherwise>
</c:choose>

<aui:script>
	Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />delete', '#<portlet:namespace /><%= searchContainerReference.getId(request, "organizationSearchContainer") %>SearchContainer', document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');
</aui:script>