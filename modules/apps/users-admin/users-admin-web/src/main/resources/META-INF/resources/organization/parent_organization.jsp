<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long organizationId = ParamUtil.getLong(request, "organizationId");

Organization organization = OrganizationServiceUtil.fetchOrganization(organizationId);

long parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationId", (organization != null) ? organization.getParentOrganizationId() : OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

if (parentOrganizationId <= 0) {
	parentOrganizationId = OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

	if (organization != null) {
		parentOrganizationId = organization.getParentOrganizationId();
	}
}

User selUser = (User)request.getAttribute("user.selUser");

Organization parentOrganization = null;

if ((organization == null) && (parentOrganizationId == OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) && !permissionChecker.isCompanyAdmin()) {
	List<Organization> manageableOrganizations = new ArrayList<Organization>();

	for (Organization curOrganization : selUser.getOrganizations()) {
		if (OrganizationPermissionUtil.contains(permissionChecker, curOrganization, ActionKeys.MANAGE_SUBORGANIZATIONS)) {
			manageableOrganizations.add(curOrganization);
		}
	}

	if (manageableOrganizations.size() == 1) {
		Organization manageableOrganization = manageableOrganizations.get(0);

		parentOrganizationId = manageableOrganization.getOrganizationId();
	}
}

if (parentOrganizationId != OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {
	parentOrganization = OrganizationLocalServiceUtil.fetchOrganization(parentOrganizationId);
}
%>

<liferay-ui:error exception="<%= OrganizationParentException.class %>" message="please-enter-a-valid-parent-organization" />

<liferay-ui:error exception="<%= OrganizationParentException.MustBeRootable.class %>">

	<%
	OrganizationParentException.MustBeRootable mbr = (OrganizationParentException.MustBeRootable)errorException;
	%>

	<liferay-ui:message arguments="<%= mbr.getType() %>" key="an-organization-of-type-x-cannot-be-a-root-organization" />
</liferay-ui:error>

<liferay-ui:error exception="<%= OrganizationParentException.MustHaveValidChildType.class %>">

	<%
	OrganizationParentException.MustHaveValidChildType mhvct = (OrganizationParentException.MustHaveValidChildType)errorException;
	%>

	<liferay-ui:message arguments="<%= new String[] {mhvct.getChildOrganizationType(), mhvct.getParentOrganizationType()} %>" key="an-organization-of-type-x-is-not-allowed-as-a-child-of-type-x" />
</liferay-ui:error>

<liferay-ui:error exception="<%= OrganizationParentException.MustNotHaveChildren.class %>">

	<%
	OrganizationParentException.MustNotHaveChildren mnhc = (OrganizationParentException.MustNotHaveChildren)errorException;
	%>

	<liferay-ui:message arguments="<%= mnhc.getType() %>" key="an-organization-of-type-x-cannot-have-children" />
</liferay-ui:error>

<div>
	<react:component
		module="js/ParentOrganization"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"label", (parentOrganization != null) ? parentOrganization.getName() : ""
			).put(
				"parentOrganizationId", (parentOrganization != null) ? parentOrganization.getOrganizationId() : ""
			).put(
				"selectOrganizationRenderURL", userDisplayContext.getOrganizationItemSelectorURL(false)
			).build()
		%>'
	/>
</div>