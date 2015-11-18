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

<%@ include file="/html/portlet/roles_admin/init.jsp" %>

<%
String p_u_i_d = ParamUtil.getString(request, "p_u_i_d");
int step = ParamUtil.getInteger(request, "step");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectOrganizationRole");

User selUser = PortalUtil.getSelectedUser(request);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/roles_admin/select_organization_role");

if (selUser != null) {
	portletURL.setParameter("p_u_i_d", String.valueOf(selUser.getUserId()));
}

portletURL.setParameter("eventName", eventName);

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

<aui:form action="<%= portletURL.toString() %>" method="post" name="selectOrganizationRoleFm">
	<c:choose>
		<c:when test="<%= step == 1 %>">
			<aui:input name="organizationId" type="hidden" />

			<liferay-ui:header
				title="organization-roles"
			/>

			<div class="alert alert-info">
				<liferay-ui:message key="please-select-an-organization-to-which-you-will-assign-an-organization-role" />
			</div>

			<%
			portletURL.setParameter("step", "1");
			%>

			<liferay-ui:search-container
				searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
				total="<%= organizations.size() %>"
			>
				<liferay-ui:search-container-results
					results="<%= ListUtil.subList(organizations, searchContainer.getStart(), searchContainer.getEnd()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.Organization"
					escapedModel="<%= true %>"
					keyProperty="organizationId"
					modelVar="organization"
				>

					<liferay-ui:search-container-column-text
						name="name"
						orderable="<%= true %>"
						property="name"
					/>

					<liferay-ui:search-container-column-text
						buffer="buffer"
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
						name="type"
						orderable="<%= true %>"
						value="<%= LanguageUtil.get(pageContext, organization.getType()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="city"
						property="address.city"
					/>

					<liferay-ui:search-container-column-text
						name="region"
						property="address.region.name"
					/>

					<liferay-ui:search-container-column-text
						name="country"
						property="address.country.name"
					/>

					<liferay-ui:search-container-column-text>

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						Group group = organization.getGroup();

						data.put("groupid", group.getGroupId());

						data.put("organizationid", organization.getOrganizationId());
						%>

						<aui:button cssClass="organization-selector-button" data="<%= data %>" value="choose" />
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>

			<aui:script use="aui-base">
				A.one('#<portlet:namespace />selectOrganizationRoleFm').delegate(
					'click',
					function(event) {
						var organizationId = event.currentTarget.attr('data-organizationid');

						document.<portlet:namespace />selectOrganizationRoleFm.<portlet:namespace />organizationId.value = organizationId;

						<%
						portletURL.setParameter("resetCur", Boolean.TRUE.toString());
						portletURL.setParameter("step", "2");
						%>

						submitForm(document.<portlet:namespace />selectOrganizationRoleFm, "<%= portletURL.toString() %>");
					},
					'.organization-selector-button'
				);
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

						searchContainer.setTotal(total);

						results = ListUtil.subList(roles, searchContainer.getStart(), searchContainer.getEnd());
					}
					else {
						total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {RoleConstants.TYPE_ORGANIZATION});

						searchContainer.setTotal(total);

						results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {RoleConstants.TYPE_ORGANIZATION}, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
					}

					searchContainer.setResults(results);
					%>

				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.Role"
					keyProperty="roleId"
					modelVar="role"
				>
					<liferay-util:param name="className" value="<%= RolesAdminUtil.getCssClassName(role) %>" />
					<liferay-util:param name="classHoverName" value="<%= RolesAdminUtil.getCssClassName(role) %>" />

					<liferay-ui:search-container-column-text
						name="title"
						value="<%= HtmlUtil.escape(role.getTitle(locale)) %>"
					/>

					<liferay-ui:search-container-column-text>
						<c:if test="<%= Validator.isNull(p_u_i_d) || OrganizationMembershipPolicyUtil.isRoleAllowed((selUser != null) ? selUser.getUserId() : 0, organization.getOrganizationId(), role.getRoleId()) %>">

							<%
							Map<String, Object> data = new HashMap<String, Object>();

							data.put("groupdescriptivename", organization.getGroup().getDescriptiveName(locale));
							data.put("groupid", organization.getGroupId());
							data.put("roleid", role.getRoleId());
							data.put("roletitle", role.getTitle(locale));
							data.put("searchcontainername", "organizationRoles");

							boolean disabled = false;

							List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(selUser.getUserId());

							for (UserGroupRole userGroupRole : userGroupRoles) {
								if ((organization.getGroupId() == userGroupRole.getGroupId()) && (userGroupRole.getRoleId() == role.getRoleId())) {
									disabled = true;

									break;
								}
							}
							%>

							<aui:button cssClass="selector-button" data="<%= data %>" disabled="<%= disabled %>" value="choose" />
						</c:if>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</c:when>
	</c:choose>
</aui:form>

<aui:script use="aui-base">
	var Util = Liferay.Util;

	var openingLiferay = Util.getOpener().Liferay;

	openingLiferay.fire(
		'<portlet:namespace />syncOrganizationRoles',
		{
			selectors: A.all('.selector-button')
		}
	);

	Util.selectEntityHandler('#<portlet:namespace />selectOrganizationRoleFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>