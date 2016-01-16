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
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

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

		RowChecker rowChecker = new EmptyOnClickRowChecker(renderResponse);

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
					portletURL="<%= renderResponse.createRenderURL() %>"
				/>

				<liferay-frontend:management-bar-sort
					orderByCol="<%= searchContainer.getOrderByCol() %>"
					orderByType="<%= searchContainer.getOrderByType() %>"
					orderColumns='<%= new String[] {"name", "type"} %>'
					portletURL="<%= portletURL %>"
				/>
			</liferay-frontend:management-bar-filters>

			<liferay-frontend:management-bar-buttons>
				<liferay-frontend:management-bar-display-buttons
					displayViews='<%= new String[] {"list"} %>'
					portletURL="<%= renderResponse.createRenderURL() %>"
					selectedDisplayStyle="<%= displayStyle %>"
				/>
			</liferay-frontend:management-bar-buttons>

			<liferay-frontend:management-bar-action-buttons>
				<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteOrganizations();" %>' icon="trash" id="deleteOrganizations" label="delete" />
			</liferay-frontend:management-bar-action-buttons>
		</liferay-frontend:management-bar>

		<liferay-ui:search-container
			id="organizations"
			searchContainer="<%= searchContainer %>"
			var="organizationSearchContainer"
		>
			<aui:input disabled="<%= true %>" name="organizationsRedirect" type="hidden" value="<%= portletURL.toString() %>" />
			<aui:input name="deleteOrganizationIds" type="hidden" />

			<c:if test="<%= usersListView.equals(UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS) %>">
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

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
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