<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

long parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationId", OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

if (parentOrganizationId > 0) {
	portletURL.setParameter("parentOrganizationId", String.valueOf(parentOrganizationId));
}
%>

<liferay-ui:search-container
	searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
	var="organizationSearch"
>
	<aui:input disabled="<%= true %>" name="organizationsRedirect" type="hidden" value="<%= portletURL.toString() %>" />

	<liferay-ui:search-form
		page="/html/portlet/directory/organization_search.jsp"
	/>

	<%
	OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)organizationSearch.getSearchTerms();

	LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

	if (parentOrganizationId <= 0) {
		parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;
	}

	if (portletName.equals(PortletKeys.MY_SITES_DIRECTORY)) {
		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<String, Object>();

		groupParams.put("inherit", Boolean.FALSE);
		groupParams.put("site", Boolean.TRUE);
		groupParams.put("usersGroups", user.getUserId());

		List<Group> groups = GroupLocalServiceUtil.search(user.getCompanyId(), groupParams, QueryUtil.ALL_POS,QueryUtil.ALL_POS);

		organizationParams.put("organizationsGroups", SitesUtil.filterGroups(groups, PropsValues.MY_SITES_DIRECTORY_SITE_EXCLUDES));
	}
	else if (portletName.equals(PortletKeys.SITE_MEMBERS_DIRECTORY)) {
		organizationParams.put("organizationsGroups", new Long(themeDisplay.getScopeGroupId()));
	}

	if (Validator.isNotNull(searchTerms.getKeywords()) || searchTerms.isAdvancedSearch()) {
		if (parentOrganizationId != OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {
			List<Long> excludedOrganizationIds = new ArrayList<Long>();

			excludedOrganizationIds.add(parentOrganizationId);

			organizationParams.put("excludedOrganizationIds", excludedOrganizationIds);

			Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(parentOrganizationId);

			List<Organization> organizations = new ArrayList<Organization>();

			organizations.add(parentOrganization);

			organizationParams.put("organizationsTree", organizations);
		}
	}
	%>

	<liferay-ui:search-container-results>
		<c:choose>
			<c:when test="<%= portletName.equals(PortletKeys.DIRECTORY) && PropsValues.ORGANIZATIONS_INDEXER_ENABLED && PropsValues.ORGANIZATIONS_SEARCH_WITH_INDEX %>">
				<%@ include file="/html/portlet/users_admin/organization_search_results_index.jspf" %>
			</c:when>
			<c:otherwise>
				<%@ include file="/html/portlet/users_admin/organization_search_results_database.jspf" %>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Organization"
		escapedModel="<%= true %>"
		keyProperty="organizationId"
		modelVar="organization"
	>
		<portlet:renderURL var="rowURL">
			<portlet:param name="struts_action" value="/directory/view_organization" />
			<portlet:param name="tabs1" value="<%= HtmlUtil.escape(tabs1) %>" />
			<portlet:param name="redirect" value="<%= organizationSearch.getIteratorURL().toString() %>" />
			<portlet:param name="organizationId" value="<%= String.valueOf(organization.getOrganizationId()) %>" />
		</portlet:renderURL>

		<%@ include file="/html/portlet/directory/organization/search_columns.jspf" %>
	</liferay-ui:search-container-row>

	<div class="separator"><!-- --></div>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>