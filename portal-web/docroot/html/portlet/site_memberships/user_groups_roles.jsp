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

<%@ include file="/html/portlet/site_memberships/init.jsp" %>

<%
String tabs1 = (String)request.getAttribute("edit_site_assignments.jsp-tabs1");
String tabs2 = (String)request.getAttribute("edit_site_assignments.jsp-tabs2");

String redirect = (String)request.getAttribute("edit_site_assignments.jsp-redirect");

int cur = (Integer)request.getAttribute("edit_site_assignments.jsp-cur");

Group group = (Group)request.getAttribute("edit_site_assignments.jsp-group");

long userGroupId = ParamUtil.getLong(request, "userGroupId");

UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(userGroupId);

PortletURL portletURL = (PortletURL)request.getAttribute("edit_site_assignments.jsp-portletURL");

portletURL.setParameter("userGroupId", String.valueOf(userGroupId));
%>

<aui:input name="userGroupId" type="hidden" value="<%= userGroupId %>" />
<aui:input name="addRoleIds" type="hidden" />
<aui:input name="removeRoleIds" type="hidden" />

<liferay-ui:header
	backURL="<%= redirect %>"
	escapeXml="<%= false %>"
	localizeTitle="<%= false %>"
	title='<%= LanguageUtil.get(request, "edit-site-roles-for-user-group") + ": " + HtmlUtil.escape(userGroup.getName()) %>'
/>

<liferay-ui:search-container
	rowChecker="<%= new UserGroupGroupRoleRoleChecker(renderResponse, userGroup, group) %>"
	searchContainer="<%= new RoleSearch(renderRequest, portletURL) %>"
>

	<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" cssClass="col-xs-12 form-search" placeholder="keywords" />

	<%
	PortletURL updateRoleAssignmentsURL = renderResponse.createRenderURL();

	updateRoleAssignmentsURL.setParameter("tabs1", tabs1);
	updateRoleAssignmentsURL.setParameter("tabs2", tabs2);
	updateRoleAssignmentsURL.setParameter("cur", String.valueOf(cur));
	updateRoleAssignmentsURL.setParameter("redirect", redirect);
	updateRoleAssignmentsURL.setParameter("userGroupId", String.valueOf(userGroupId));
	updateRoleAssignmentsURL.setParameter("groupId", String.valueOf(group.getGroupId()));
	%>

	<div class="separator"><!-- --></div>

	<%
	String taglibOnClick = renderResponse.getNamespace() + "updateUserGroupGroupRole('" + updateRoleAssignmentsURL.toString() + "');";
	%>

	<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

	<liferay-ui:search-container-results>

		<%
		RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();

		List<Role> roles = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), new Integer[] {RoleConstants.TYPE_SITE}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, searchContainer.getOrderByComparator());

		roles = UsersAdminUtil.filterGroupRoles(permissionChecker, group.getGroupId(), roles);

		total = roles.size();

		searchContainer.setTotal(total);

		results = ListUtil.subList(roles, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);
		%>

	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Role"
		keyProperty="roleId"
		modelVar="role"
	>
		<liferay-ui:search-container-column-text
			name="title"
		>
			<liferay-ui:icon
				iconCssClass="<%= RolesAdminUtil.getIconCssClass(role) %>"
				label="<%= true %>"
				message="<%= HtmlUtil.escape(role.getTitle(locale)) %>"
			/>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="type"
			value="<%= LanguageUtil.get(request, role.getTypeLabel()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="description"
			value="<%= HtmlUtil.escape(role.getDescription(locale)) %>"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator/>
</liferay-ui:search-container>