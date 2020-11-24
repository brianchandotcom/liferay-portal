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
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

long parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationId", OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

if (parentOrganizationId > 0) {
	portletURL.setParameter("parentOrganizationId", String.valueOf(parentOrganizationId));
}
%>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"name", "type"} %>'
			portletURL="<%= portletURL %>"
		/>

		<c:if test='<%= ParamUtil.getBoolean(request, "showSearch", true) %>'>
			<li>
				<liferay-util:include page="/organization_search.jsp" servletContext="<%= application %>" />
			</li>
		</c:if>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid container-fluid-max-xl">
	<liferay-ui:search-container
		searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
		var="organizationSearchContainer"
	>
		<aui:input disabled="<%= true %>" name="organizationsRedirect" type="hidden" value="<%= portletURL.toString() %>" />

		<%
		OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)organizationSearchContainer.getSearchTerms();

		LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

		if (parentOrganizationId <= 0) {
			parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;
		}

		if (portletName.equals(PortletKeys.MY_SITES_DIRECTORY)) {
			List<Group> groups = GroupLocalServiceUtil.search(
				user.getCompanyId(),
				LinkedHashMapBuilder.<String, Object>put(
					"inherit", Boolean.FALSE
				).put(
					"site", Boolean.TRUE
				).put(
					"usersGroups", user.getUserId()
				).build(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			organizationParams.put("organizationsGroups", SitesUtil.filterGroups(groups, PropsValues.MY_SITES_DIRECTORY_SITE_EXCLUDES));
		}
		else if (portletName.equals(PortletKeys.SITE_MEMBERS_DIRECTORY)) {
			organizationParams.put("organizationsGroups", Long.valueOf(themeDisplay.getScopeGroupId()));
		}

		if ((Validator.isNotNull(searchTerms.getKeywords()) || searchTerms.isAdvancedSearch()) && (parentOrganizationId != OrganizationConstants.ANY_PARENT_ORGANIZATION_ID)) {
			organizationParams.put("excludedOrganizationIds", ListUtil.fromArray(parentOrganizationId));

			Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(parentOrganizationId);

			organizationParams.put("organizationsTree", ListUtil.fromArray(parentOrganization));
		}

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(Organization.class);
		%>

		<liferay-ui:search-container-results>
			<c:choose>
				<c:when test="<%= portletName.equals(PortletKeys.DIRECTORY) && indexer.isIndexerEnabled() && PropsValues.ORGANIZATIONS_SEARCH_WITH_INDEX %>">

					<%
					organizationParams.put("expandoAttributes", searchTerms.getKeywords());

					Sort sort = SortFactoryUtil.getSort(Organization.class, organizationSearchContainer.getOrderByCol(), organizationSearchContainer.getOrderByType());

					BaseModelSearchResult<Organization> baseModelSearchResult = null;

					if (searchTerms.isAdvancedSearch()) {
						baseModelSearchResult = OrganizationLocalServiceUtil.searchOrganizations(company.getCompanyId(), parentOrganizationId, searchTerms.getName(), searchTerms.getType(), searchTerms.getStreet(), searchTerms.getCity(), searchTerms.getZip(), searchTerms.getRegionName(), searchTerms.getCountryName(), organizationParams, searchTerms.isAndOperator(), organizationSearchContainer.getStart(), organizationSearchContainer.getEnd(), sort);
					}
					else {
						baseModelSearchResult = OrganizationLocalServiceUtil.searchOrganizations(company.getCompanyId(), parentOrganizationId, searchTerms.getKeywords(), organizationParams, organizationSearchContainer.getStart(), organizationSearchContainer.getEnd(), sort);
					}

					organizationSearchContainer.setResults(baseModelSearchResult.getBaseModels());
					organizationSearchContainer.setTotal(baseModelSearchResult.getLength());
					%>

				</c:when>
				<c:otherwise>

					<%
					if (searchTerms.isAdvancedSearch()) {
						total = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), parentOrganizationId, searchTerms.getName(), searchTerms.getType(), searchTerms.getStreet(), searchTerms.getCity(), searchTerms.getZip(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, searchTerms.isAndOperator());

						results = OrganizationLocalServiceUtil.search(company.getCompanyId(), parentOrganizationId, searchTerms.getName(), searchTerms.getType(), searchTerms.getStreet(), searchTerms.getCity(), searchTerms.getZip(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, searchTerms.isAndOperator(), organizationSearchContainer.getStart(), organizationSearchContainer.getEnd(), organizationSearchContainer.getOrderByComparator());
					}
					else {
						total = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), parentOrganizationId, searchTerms.getKeywords(), searchTerms.getType(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams);

						results = OrganizationLocalServiceUtil.search(company.getCompanyId(), parentOrganizationId, searchTerms.getKeywords(), searchTerms.getType(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, organizationSearchContainer.getStart(), organizationSearchContainer.getEnd(), organizationSearchContainer.getOrderByComparator());
					}

					organizationSearchContainer.setTotal(total);
					organizationSearchContainer.setResults(results);
					%>

				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Organization"
			escapedModel="<%= true %>"
			keyProperty="organizationId"
			modelVar="organization"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/directory/view_organization" />
				<portlet:param name="tabs1" value="<%= HtmlUtil.escape(tabs1) %>" />
				<portlet:param name="redirect" value="<%= organizationSearchContainer.getIteratorURL().toString() %>" />
				<portlet:param name="organizationId" value="<%= String.valueOf(organization.getOrganizationId()) %>" />
			</portlet:renderURL>

			<%@ include file="/organization/search_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>