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
String tabs1 = ParamUtil.getString(request, "tabs1");
String tabs2 = ParamUtil.getString(request, "tabs2", "users");
String tabs3 = ParamUtil.getString(request, "tabs3", "current");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

long roleId = ParamUtil.getLong(request, "roleId");

Role role = RoleServiceUtil.fetchRole(roleId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/edit_role_assignments.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("roleId", String.valueOf(role.getRoleId()));

request.setAttribute("edit_role_assignments.jsp-tabs3", tabs3);

request.setAttribute("edit_role_assignments.jsp-cur", cur);

request.setAttribute("edit_role_assignments.jsp-role", role);

request.setAttribute("edit_role_assignments.jsp-portletURL", portletURL);
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	title="<%= role.getTitle(locale) %>"
/>

<liferay-util:include page="/edit_role_tabs.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="assign-members" />
	<liferay-util:param name="backURL" value="<%= redirect %>" />
</liferay-util:include>

<portlet:actionURL name="editRoleAssignments" var="editRoleAssignmentsURL">
	<portlet:param name="mvcPath" value="/edit_role_assignments.jsp" />
</portlet:actionURL>

<aui:form action="<%= editRoleAssignmentsURL %>" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="tabs3" type="hidden" value="<%= tabs3 %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="roleId" type="hidden" value="<%= role.getRoleId() %>" />

	<liferay-ui:tabs
		names="users,sites,organizations,user-groups"
		param="tabs2"
		url="<%= portletURL.toString() %>"
	/>

	<%
	String usersAdminPortletId = PortletProviderUtil.getPortletId(User.class.getName(), PortletProvider.Action.VIEW);
	%>

	<c:choose>
		<c:when test='<%= tabs2.equals("users") %>'>
			<liferay-util:include page="/edit_role_assignments_users.jsp" portletId="<%= usersAdminPortletId %>" />
		</c:when>
		<c:when test='<%= tabs2.equals("sites") %>'>
			<liferay-util:include page="/edit_role_assignments_sites.jsp" portletId="<%= usersAdminPortletId %>" />
		</c:when>
		<c:when test='<%= tabs2.equals("organizations") %>'>
			<liferay-util:include page="/edit_role_assignments_organizations.jsp" portletId="<%= usersAdminPortletId %>" />
		</c:when>
		<c:when test='<%= tabs2.equals("user-groups") %>'>
			<liferay-util:include page="/edit_role_assignments_user_groups.jsp" portletId="<%= usersAdminPortletId %>" />
		</c:when>
	</c:choose>
</aui:form>

<aui:script>
	function <portlet:namespace />updateRoleGroups(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addGroupIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeGroupIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form);
	}

	function <portlet:namespace />updateRoleUsers(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addUserIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form);
	}
</aui:script>

<%
PortletURL assignMembersURL = renderResponse.createRenderURL();

assignMembersURL.setParameter("mvcPath", "/edit_role_assignments.jsp");
assignMembersURL.setParameter("redirect", redirect);
assignMembersURL.setParameter("roleId", String.valueOf(role.getRoleId()));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "assign-members"), assignMembersURL.toString());

assignMembersURL.setParameter("tabs2", tabs2);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, tabs2), assignMembersURL.toString());
%>