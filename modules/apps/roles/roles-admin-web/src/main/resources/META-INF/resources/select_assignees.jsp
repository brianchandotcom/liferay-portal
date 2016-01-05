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
String tabs2 = ParamUtil.getString(request, "tabs2", "users");
String tabs3 = ParamUtil.getString(request, "tabs3", "available");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

long roleId = ParamUtil.getLong(request, "roleId");

Role role = RoleServiceUtil.fetchRole(roleId);

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectAssignees");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/select_assignees.jsp");
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", "available");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("roleId", String.valueOf(role.getRoleId()));
portletURL.setParameter("displayStyle", displayStyle);
portletURL.setParameter("orderByCol", orderByCol);
portletURL.setParameter("orderByType", orderByType);

request.setAttribute("edit_role_assignments.jsp-tabs3", "available");

request.setAttribute("edit_role_assignments.jsp-cur", cur);

request.setAttribute("edit_role_assignments.jsp-role", role);

request.setAttribute("edit_role_assignments.jsp-displayStyle", displayStyle);

request.setAttribute("edit_role_assignments.jsp-portletURL", portletURL);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		portletURL.setParameter("tabs2", "users");
		%>

		<aui:nav-item href="<%= portletURL.toString() %>" label="users" selected='<%= tabs2.equals("users") %>' />

		<%
		portletURL.setParameter("tabs2", "sites");
		%>

		<aui:nav-item href="<%= portletURL.toString() %>" label="sites" selected='<%= tabs2.equals("sites") %>' />

		<%
		portletURL.setParameter("tabs2", "organizations");
		%>

		<aui:nav-item href="<%= portletURL.toString() %>" label="organizations" selected='<%= tabs2.equals("organizations") %>' />

		<%
		portletURL.setParameter("tabs2", "user-groups");
		%>

		<aui:nav-item href="<%= portletURL.toString() %>" label="user-groups" selected='<%= tabs2.equals("user-groups") %>' />

		<%
		portletURL.setParameter("tabs2", tabs2);
		%>
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" name="searchFm">
			<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" placeholder='<%= LanguageUtil.get(request, "keywords") %>' />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<portlet:actionURL name="editRoleAssignments" var="editRoleAssignmentsURL">
	<portlet:param name="mvcPath" value="/edit_role_assignments.jsp" />
</portlet:actionURL>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="tabs3" type="hidden" value="<%= tabs3 %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="roleId" type="hidden" value="<%= role.getRoleId() %>" />

	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="assigneesSearch"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-filters>
				<liferay-frontend:management-bar-sort
					orderByCol="<%= orderByCol %>"
					orderByType="<%= orderByType %>"
					orderColumns='<%= new String[] {"name"} %>'
					portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
				/>
			</liferay-frontend:management-bar-filters>

			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>
	</liferay-frontend:management-bar>

	<c:choose>
		<c:when test='<%= tabs2.equals("users") %>'>
			<liferay-util:include page="/edit_role_assignments_users.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= tabs2.equals("sites") %>'>
			<liferay-util:include page="/edit_role_assignments_sites.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= tabs2.equals("organizations") %>'>
			<liferay-util:include page="/edit_role_assignments_organizations.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= tabs2.equals("user-groups") %>'>
			<liferay-util:include page="/edit_role_assignments_user_groups.jsp" servletContext="<%= application %>" />
		</c:when>
	</c:choose>
</aui:form>

<aui:script>
	var A = AUI();

	var <portlet:namespace />assigneeIds = [];

	$('input[name="<portlet:namespace />rowIds"]').on(
		'change',
		function(event) {
			var target = event.target;

			if (target.checked) {
				<portlet:namespace />assigneeIds.push(target.value);
			}
			else {
				A.Array.removeItem(<portlet:namespace />assigneeIds, target.value);
			}

			var result = {};

			if (<portlet:namespace />assigneeIds.length > 0) {
				result = {
					data: {
						type: '<%= tabs2 %>',
						value: <portlet:namespace />assigneeIds.join(',')
					}
				};
			}

			Liferay.Util.getOpener().Liferay.fire('<%= HtmlUtil.escapeJS(eventName) %>', result);
		}
	);
</aui:script>