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
String tabs1 = ParamUtil.getString(request, "tabs1", "current");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());

Group group = GroupLocalServiceUtil.getGroup(groupId);

if (group != null) {
	group = StagingUtil.getLiveGroup(group.getGroupId());
}

String groupDescriptiveName = group.getDescriptiveName(locale);

long roleId = ParamUtil.getLong(request, "roleId");

Role role = RoleLocalServiceUtil.fetchRole(roleId);

if (role != null) {
	String roleName = role.getName();

	if (roleName.equals(RoleConstants.SITE_MEMBER)) {
		throw new NoSuchRoleException();
	}
}

int roleType = ParamUtil.getInteger(request, "roleType", RoleConstants.TYPE_SITE);

Organization organization = null;

if (group.isOrganization()) {
	roleType = RoleConstants.TYPE_ORGANIZATION;

	organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());
}

String className = ParamUtil.getString(request, "className", User.class.getName());

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("className", className);
portletURL.setParameter("groupId", String.valueOf(group.getGroupId()));

if (role != null) {
	portletURL.setParameter("roleId", String.valueOf(roleId));
}

request.setAttribute("edit_roles.jsp-tabs1", tabs1);

request.setAttribute("edit_roles.jsp-cur", cur);

request.setAttribute("edit_roles.jsp-redirect", redirect);

request.setAttribute("edit_roles.jsp-className", className);
request.setAttribute("edit_roles.jsp-group", group);
request.setAttribute("edit_roles.jsp-groupDescriptiveName", groupDescriptiveName);
request.setAttribute("edit_roles.jsp-role", role);
request.setAttribute("edit_roles.jsp-roleId", roleId);
request.setAttribute("edit_roles.jsp-roleType", roleType);
request.setAttribute("edit_roles.jsp-organization", organization);

request.setAttribute("edit_roles.jsp-portletURL", portletURL);
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />
	<aui:input name="roleId" type="hidden" value="<%= roleId %>" />

	<c:choose>
		<c:when test="<%= role == null %>">
			<liferay-util:include page="/edit_roles.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="<%= className.equals(User.class.getName()) %>">
					<liferay-util:include page="/edit_roles_users.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/edit_roles_user_groups.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />updateUserGroupGroupRoleUsers(redirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('redirect').val(redirect);
		form.fm('addUserGroupIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserGroupIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="editUserGroupGroupRoleUsers" />');
	}

	function <portlet:namespace />updateUserGroupRoleUsers(redirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('redirect').val(redirect);
		form.fm('addUserIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="editUserGroupRoleUsers" />');
	}
</aui:script>