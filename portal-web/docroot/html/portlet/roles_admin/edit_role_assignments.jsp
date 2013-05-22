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
String tabs1 = ParamUtil.getString(request, "tabs1");
String tabs2 = ParamUtil.getString(request, "tabs2", "users");
String tabs3 = ParamUtil.getString(request, "tabs3", "current");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

Role role = (Role)request.getAttribute(WebKeys.ROLE);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/roles_admin/edit_role_assignments");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("roleId", String.valueOf(role.getRoleId()));

request.setAttribute("edit_role_assignments.jsp-tabs3", tabs3);

request.setAttribute("edit_role_assignments.jsp-cur", cur);

request.setAttribute("edit_role_assignments.jsp-role", role);

request.setAttribute("edit_role_assignments.jsp-portletURL", portletURL);

String addInputId = "addGroupIds";
String cmdVal = "role_groups";
String removeInputId = "removeGroupIds";
String searchContainerId = StringPool.BLANK;
%>

<liferay-util:include page="/html/portlet/roles_admin/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value='<%= (role == null) ? "add" : "view-all" %>' />
</liferay-util:include>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	title="<%= role.getTitle(locale) %>"
/>

<liferay-util:include page="/html/portlet/roles_admin/edit_role_tabs.jsp">
	<liferay-util:param name="tabs1" value="assign-members" />
	<liferay-util:param name="backURL" value="<%= redirect %>" />
</liferay-util:include>

<portlet:actionURL var="editAssignmentsURL">
	<portlet:param name="struts_action" value="/roles_admin/edit_role_assignments" />
</portlet:actionURL>

<aui:form action="<%= editAssignmentsURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="tabs3" type="hidden" value="<%= tabs3 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="roleId" type="hidden" value="<%= role.getRoleId() %>" />

	<liferay-ui:tabs
		names="users,sites,organizations,user-groups"
		param="tabs2"
		url="<%= portletURL.toString() %>"
	/>

	<c:choose>
		<c:when test='<%= tabs2.equals("users") %>'>
			<liferay-util:include page="/html/portlet/users_admin/edit_role_assignments_users.jsp" />

			<%
			addInputId = "addUserIds";
			cmdVal = "role_users";
			removeInputId = "removeUserIds";

			SearchContainer searchContainer = (SearchContainer)request.getAttribute("edit_role_assignments.jsp-searchContainer");

			searchContainerId = searchContainer.getId(request, renderResponse.getNamespace());
			%>
		</c:when>
		<c:when test='<%= tabs2.equals("sites") %>'>
			<liferay-util:include page="/html/portlet/sites_admin/edit_role_assignments_sites.jsp" />

			<%
			SearchContainer searchContainer = (SearchContainer)request.getAttribute("edit_role_assignments.jsp-searchContainer");

			searchContainerId = searchContainer.getId(request, renderResponse.getNamespace());
			%>
		</c:when>
		<c:when test='<%= tabs2.equals("organizations") %>'>
			<liferay-util:include page="/html/portlet/users_admin/edit_role_assignments_organizations.jsp" />

			<%
			SearchContainer searchContainer = (SearchContainer)request.getAttribute("edit_role_assignments.jsp-searchContainer");

			searchContainerId = searchContainer.getId(request, renderResponse.getNamespace());
			%>
		</c:when>
		<c:when test='<%= tabs2.equals("user-groups") %>'>
			<liferay-util:include page="/html/portlet/users_admin/edit_role_assignments_user_groups.jsp" />

			<%
			SearchContainer searchContainer = (SearchContainer)request.getAttribute("edit_role_assignments.jsp-searchContainer");

			searchContainerId = searchContainer.getId(request, renderResponse.getNamespace());
			%>
		</c:when>
	</c:choose>
</aui:form>

<aui:script use="aui-base,liferay-util-list-fields">
	Liferay.Util.updateSearchContainerButton(
		A.one('#<portlet:namespace />updateAssociations'),
		A.one('#<portlet:namespace /><%= searchContainerId %>'),
		document.<portlet:namespace />fm,
		"<portlet:namespace />allRowIds"
	);
</aui:script>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />updateRoles',
		function(assignmentsRedirect) {
			var updateRoleIds = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");

			if (updateRoleIds) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= cmdVal %>";
				document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;
				document.<portlet:namespace />fm.<portlet:namespace /><%= addInputId %>.value = updateRoleIds;
				document.<portlet:namespace />fm.<portlet:namespace /><%= removeInputId %>.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");

				submitForm(document.<portlet:namespace />fm);
			}
		},
		['liferay-util-list-fields']
	);
</aui:script>

<%
PortletURL assignMembersURL = renderResponse.createRenderURL();

assignMembersURL.setParameter("struts_action", "/roles_admin/edit_role_assignments");
assignMembersURL.setParameter("redirect", redirect);
assignMembersURL.setParameter("roleId", String.valueOf(role.getRoleId()));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "assign-members"), assignMembersURL.toString());

assignMembersURL.setParameter("tabs2", tabs2);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, tabs2), assignMembersURL.toString());
%>