<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
String target = ParamUtil.getString(request, "target");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/users_admin/select_organization");

if (Validator.isNotNull(target)) {
	portletURL.setParameter("target", target);
}
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<liferay-ui:header
		title="organizations"
	/>

	<liferay-ui:search-container
		searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
	>
		<liferay-ui:search-form
			page="/html/portlet/users_admin/organization_search.jsp"
		/>

		<%
		OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)searchContainer.getSearchTerms();
		%>

		<liferay-ui:search-container-results>

			<%
			LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

			if (filterManageableOrganizations) {
				organizationParams.put("organizationsTree", user.getOrganizations());
			}

			if (searchTerms.isAdvancedSearch()) {
				results = OrganizationLocalServiceUtil.search(company.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, searchTerms.getName(), searchTerms.getType(), searchTerms.getStreet(), searchTerms.getCity(), searchTerms.getZip(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
			}
			else {
				results = OrganizationLocalServiceUtil.search(company.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, searchTerms.getKeywords(), searchTerms.getType(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
			}

			if (searchTerms.isAdvancedSearch()) {
				total = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, searchTerms.getName(), searchTerms.getType(), searchTerms.getStreet(), searchTerms.getCity(), searchTerms.getZip(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, searchTerms.isAndOperator());
			}
			else {
				total = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, searchTerms.getKeywords(), searchTerms.getType(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams);
			}

			pageContext.setAttribute("results", results);
			pageContext.setAttribute("total", total);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Organization"
			escapedModel="<%= true %>"
			keyProperty="organizationId"
			modelVar="organization"
		>

			<%
			String rowHREF = null;

			if (OrganizationPermissionUtil.contains(permissionChecker, organization.getOrganizationId(), ActionKeys.ASSIGN_MEMBERS)) {
				StringBundler sb = new StringBundler(10);

				sb.append("javascript:opener.");
				sb.append(renderResponse.getNamespace());
				sb.append("selectOrganization('");
				sb.append(organization.getOrganizationId());
				sb.append("', '");
				sb.append(organization.getGroup().getGroupId());
				sb.append("', '");
				sb.append(UnicodeFormatter.toString(organization.getName()));
				sb.append("', '");
				sb.append(UnicodeLanguageUtil.get(pageContext, organization.getType()));
				sb.append("', '");
				sb.append(target);
				sb.append("');");
				sb.append("window.close();");

				rowHREF = sb.toString();
			}
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="name"
				orderable="<%= true %>"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				buffer="buffer"
				href="<%= rowHREF %>"
				name="parent-organization"
			>

				<%
				String parentOrganizationName = StringPool.BLANK;

				if (organization.getParentOrganizationId() > 0) {
					try {
						Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(organization.getParentOrganizationId());

						parentOrganizationName = parentOrganization.getName();
					}
					catch (Exception e) {
					}
				}

				buffer.append(HtmlUtil.escape(parentOrganizationName));
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="type"
				orderable="<%= true %>"
				value="<%= LanguageUtil.get(pageContext, organization.getType()) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="city"
				property="address.city"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="region"
				property="address.region.name"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="country"
				property="address.country.name"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
</aui:script>