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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all-organizations");

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(UsersAdminPortletKeys.USERS_ADMIN, "display-style", "list");
}
else {
	portalPreferences.setValue(UsersAdminPortletKeys.USERS_ADMIN, "display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

String usersListView = (String)request.getAttribute("view.jsp-usersListView");

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

portletURL.setParameter("displayStyle", displayStyle);

String keywords = ParamUtil.getString(request, "keywords");

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
		ViewOrganizationsManagementToolbarDisplayContext viewOrganizationsManagementToolbarDisplayContext = new ViewOrganizationsManagementToolbarDisplayContext(request, renderRequest, renderResponse, displayStyle);

		SearchContainer searchContainer = viewOrganizationsManagementToolbarDisplayContext.getSearchContainer(organizationParams, filterManageableOrganizations);
		%>

		<clay:management-toolbar
			actionDropdownItems="<%= viewOrganizationsManagementToolbarDisplayContext.getActionDropdownItems() %>"
			clearResultsURL="<%= viewOrganizationsManagementToolbarDisplayContext.getClearResultsURL() %>"
			creationMenu="<%= viewOrganizationsManagementToolbarDisplayContext.getCreationMenu() %>"
			filterDropdownItems="<%= viewOrganizationsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
			itemsTotal="<%= searchContainer.getTotal() %>"
			searchActionURL="<%= viewOrganizationsManagementToolbarDisplayContext.getSearchActionURL() %>"
			searchContainerId="organizations"
			searchFormName="searchFm"
			selectable="<%= true %>"
			showCreationMenu="<%= viewOrganizationsManagementToolbarDisplayContext.showCreationMenu() %>"
			showSearch="<%= true %>"
			sortingOrder="<%= searchContainer.getOrderByType() %>"
			sortingURL="<%= viewOrganizationsManagementToolbarDisplayContext.getSortingURL() %>"
			viewTypeItems="<%= viewOrganizationsManagementToolbarDisplayContext.getViewTypeItems() %>"
		/>

		<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "search();" %>'>
			<liferay-portlet:renderURLParams varImpl="portletURL" />
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="toolbarItem" type="hidden" value="<%= toolbarItem %>" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

			<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-organizations-that-have-suborganizations-or-users" />

			<liferay-ui:search-container
				id="organizations"
				searchContainer="<%= searchContainer %>"
				var="organizationSearchContainer"
			>
				<aui:input name="deleteOrganizationIds" type="hidden" />

				<c:if test="<%= usersListView.equals(UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS) %>">
					<div id="breadcrumb">
						<liferay-ui:breadcrumb
							showCurrentGroup="<%= false %>"
							showGuestGroup="<%= false %>"
							showLayout="<%= false %>"
							showPortletBreadcrumb="<%= true %>"
						/>
					</div>
				</c:if>

				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.Organization"
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
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="<%= displayStyle %>"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info">
			<liferay-ui:message key="you-do-not-belong-to-an-organization-and-are-not-allowed-to-view-other-organizations" />
		</div>
	</c:otherwise>
</c:choose>