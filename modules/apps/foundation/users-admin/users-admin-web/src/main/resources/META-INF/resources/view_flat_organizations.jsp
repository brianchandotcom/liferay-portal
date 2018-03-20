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

boolean hasAddOrganizationPermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_ORGANIZATION);
%>

<c:choose>
	<c:when test="<%= showList %>">

		<%
		SearchContainer searchContainer = new OrganizationSearch(renderRequest, portletURL);

		OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)searchContainer.getSearchTerms();

		if (!searchTerms.isSearch() && hasAddOrganizationPermission) {
			searchContainer.setEmptyResultsMessageCssClass("taglib-empty-result-message-header-has-plus-btn");
		}

		RowChecker rowChecker = new OrganizationChecker(renderResponse);

		rowChecker.setRowIds("rowIdsOrganization");

		searchContainer.setRowChecker(rowChecker);
		%>

		<liferay-frontend:management-bar
			includeCheckBox="<%= true %>"
			searchContainerId="organizations"
		>
			<liferay-frontend:management-bar-filters>
				<liferay-frontend:management-bar-navigation
					navigationKeys='<%= new String[] {"all"} %>'
					portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
				/>

				<liferay-frontend:management-bar-sort
					orderByCol="<%= searchContainer.getOrderByCol() %>"
					orderByType="<%= searchContainer.getOrderByType() %>"
					orderColumns='<%= new String[] {"name", "type"} %>'
					portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
				/>

				<li>
					<aui:form action="<%= portletURL.toString() %>" name="searchFm">
						<liferay-ui:input-search
							markupView="lexicon"
						/>
					</aui:form>
				</li>
			</liferay-frontend:management-bar-filters>

			<liferay-frontend:management-bar-buttons>
				<liferay-frontend:management-bar-display-buttons
					displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
					portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
					selectedDisplayStyle="<%= displayStyle %>"
				/>

				<c:if test="<%= hasAddOrganizationPermission %>">
					<liferay-frontend:add-menu>
						<portlet:renderURL var="viewUsersURL">
							<portlet:param name="toolbarItem" value="<%= toolbarItem %>" />
							<portlet:param name="usersListView" value="<%= usersListView %>" />
						</portlet:renderURL>

						<%
						for (String organizationType : OrganizationLocalServiceUtil.getTypes()) {
						%>

							<portlet:renderURL var="addOrganizationURL">
								<portlet:param name="mvcRenderCommandName" value="/users_admin/edit_organization" />
								<portlet:param name="redirect" value="<%= viewUsersURL %>" />
								<portlet:param name="type" value="<%= organizationType %>" />
							</portlet:renderURL>

							<liferay-frontend:add-menu-item
								title="<%= LanguageUtil.get(request, organizationType) %>"
								url="<%= addOrganizationURL.toString() %>"
							/>

						<%
						}
						%>

					</liferay-frontend:add-menu>
				</c:if>
			</liferay-frontend:management-bar-buttons>

			<liferay-frontend:management-bar-action-buttons>
				<liferay-frontend:management-bar-button
					href='<%= "javascript:" + renderResponse.getNamespace() + "deleteOrganizations();" %>'
					icon="trash"
					id="deleteOrganizations"
					label="delete"
				/>
			</liferay-frontend:management-bar-action-buttons>
		</liferay-frontend:management-bar>

		<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "search();" %>'>
			<liferay-portlet:renderURLParams varImpl="portletURL" />
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="toolbarItem" type="hidden" value="<%= toolbarItem %>" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

			<liferay-ui:search-container
				id="organizations"
				searchContainer="<%= searchContainer %>"
				var="organizationSearchContainer"
			>
				<aui:input disabled="<%= true %>" name="organizationsRedirect" type="hidden" value="<%= portletURL.toString() %>" />
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

				<%
				long parentOrganizationId = OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

				if (Validator.isNotNull(keywords)) {
					parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;
				}
				else {
					parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationId", OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);
				}
				%>

				<liferay-ui:organization-search-container-results
					organizationParams="<%= organizationParams %>"
					parentOrganizationId="<%= parentOrganizationId %>"
				/>

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