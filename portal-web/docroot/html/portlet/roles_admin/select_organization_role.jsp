<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/roles_admin/init.jsp" %>

<%
int step = ParamUtil.getInteger(request, "step");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/roles_admin/select_organization_role");

User selUser = null;
long uniqueOrganizationId = 0;

List<Organization> organizations = null;

String organizationIds = ParamUtil.getString(request, "organizationIds");

portletURL.setParameter("organizationIds", organizationIds);

if (step == 1) {
	organizations = OrganizationLocalServiceUtil.getOrganizations(StringUtil.split(organizationIds, 0L));

	if (filterManageableOrganizations) {
		organizations = UsersAdminUtil.filterOrganizations(permissionChecker, organizations);
	}

	if (organizations.size() == 1) {
		step = 2;

		uniqueOrganizationId = organizations.get(0).getOrganizationId();
	}
}
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<c:choose>
		<c:when test="<%= step == 1 %>">
			<aui:input name="organizationId" type="hidden" />

			<liferay-ui:header
				title="organization-roles"
			/>

			<div class="portlet-msg-info">
				<liferay-ui:message key="please-select-an-organization-to-which-you-will-assign-an-organization-role" />
			</div>

			<%
			portletURL.setParameter("step", "1");
			%>

			<liferay-ui:search-container
				searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
			>
				<liferay-ui:search-container-results>

					<%
					total = organizations.size();
					results = ListUtil.subList(organizations, searchContainer.getStart(), searchContainer.getEnd());

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
					StringBundler sb = new StringBundler(5);

					sb.append("javascript:");
					sb.append(renderResponse.getNamespace());
					sb.append("selectOrganization('");
					sb.append(organization.getOrganizationId());
					sb.append("', '");
					sb.append(organization.getGroup().getGroupId());
					sb.append("');");

					String rowHREF = sb.toString();
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

			<aui:script>
				function <portlet:namespace />selectOrganization(organizationId, groupId) {
					document.<portlet:namespace />fm.<portlet:namespace />organizationId.value = organizationId;

					<%
					portletURL.setParameter("resetCur", Boolean.TRUE.toString());
					portletURL.setParameter("step", "2");
					%>

					submitForm(document.<portlet:namespace />fm, "<%= portletURL.toString() %>");
				}
			</aui:script>
		</c:when>

		<c:when test="<%= step == 2 %>">

			<%
			long organizationId = ParamUtil.getLong(request, "organizationId", uniqueOrganizationId);
			%>

			<aui:input name="step" type="hidden" value="2" />
			<aui:input name="organizationId" type="hidden" value="<%= String.valueOf(organizationId) %>" />

			<liferay-ui:header
				title="organization-roles"
			/>

			<%
			Organization organization = OrganizationServiceUtil.getOrganization(organizationId);

			portletURL.setParameter("step", "1");

			String breadcrumbs = "<a href=\"" + portletURL.toString() + "\">" + LanguageUtil.get(pageContext, "organizations") + "</a> &raquo; " + HtmlUtil.escape(organization.getName());
			%>

			<div class="breadcrumbs">
				<%= breadcrumbs %>
			</div>

			<%
			portletURL.setParameter("step", "2");
			portletURL.setParameter("organizationId", String.valueOf(organizationId));
			%>

			<liferay-ui:search-container
				headerNames="name"
				searchContainer="<%= new RoleSearch(renderRequest, portletURL) %>"
			>
				<liferay-ui:search-form
					page="/html/portlet/roles_admin/role_search.jsp"
				/>

				<%
				RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();
				%>

				<liferay-ui:search-container-results>

					<%
					if (filterManageableRoles) {
						List<Role> roles = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {RoleConstants.TYPE_ORGANIZATION}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, searchContainer.getOrderByComparator());

						roles = UsersAdminUtil.filterGroupRoles(permissionChecker, organization.getGroup().getGroupId(), roles);

						total = roles.size();
						results = ListUtil.subList(roles, searchContainer.getStart(), searchContainer.getEnd());
					}
					else {
						results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {RoleConstants.TYPE_ORGANIZATION}, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
						total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {RoleConstants.TYPE_ORGANIZATION});
					}

					pageContext.setAttribute("results", results);
					pageContext.setAttribute("total", total);
					%>

				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.Role"
					keyProperty="roleId"
					modelVar="role"
				>
					<liferay-util:param name="className" value="<%= RolesAdminUtil.getCssClassName(role) %>" />
					<liferay-util:param name="classHoverName" value="<%= RolesAdminUtil.getCssClassName(role) %>" />

					<%
					StringBundler sb = new StringBundler(14);

					sb.append("javascript:opener.");
					sb.append(renderResponse.getNamespace());
					sb.append("selectRole('");
					sb.append(role.getRoleId());
					sb.append("', '");
					sb.append(UnicodeFormatter.toString(role.getTitle(locale)));
					sb.append("', '");
					sb.append("organizationRoles");
					sb.append("', '");
					sb.append(UnicodeFormatter.toString(organization.getGroup().getDescriptiveName(locale)));
					sb.append("', '");
					sb.append(organization.getGroup().getGroupId());
					sb.append("');");
					sb.append("window.close();");

					String rowHREF = sb.toString();
					%>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="title"
						value="<%= HtmlUtil.escape(role.getTitle(locale)) %>"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>

			<aui:script>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
			</aui:script>
		</c:when>
	</c:choose>
</aui:form>