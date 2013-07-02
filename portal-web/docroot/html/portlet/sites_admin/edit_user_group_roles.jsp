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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "current");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

Group group = (Group)request.getAttribute(WebKeys.GROUP);

String groupName = group.getDescriptiveName(locale);

Role role = (Role)request.getAttribute(WebKeys.ROLE);

long roleId = BeanParamUtil.getLong(role, request, "roleId");

int roleType = ParamUtil.getInteger(request, "roleType", RoleConstants.TYPE_SITE);

Organization organization = null;

if (group.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/sites_admin/edit_user_group_roles");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("groupId", String.valueOf(group.getGroupId()));

// Breadcrumbs

if (organization != null) {
	UsersAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);
}
else if (group != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(locale), null);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "assign-user-group-roles"), portletURL.toString());

if (role != null) {
	portletURL.setParameter("roleId", String.valueOf(roleId));

	PortalUtil.addPortletBreadcrumbEntry(request, HtmlUtil.escape(role.getTitle(locale)), currentURL);
}

request.setAttribute("edit_user_group_roles.jsp-tabs1", tabs1);

request.setAttribute("edit_user_group_roles.jsp-cur", cur);

request.setAttribute("edit_user_group_roles.jsp-redirect", redirect);

request.setAttribute("edit_user_group_roles.jsp-group", group);
request.setAttribute("edit_user_group_roles.jsp-groupName", groupName);
request.setAttribute("edit_user_group_roles.jsp-role", role);
request.setAttribute("edit_user_group_roles.jsp-roleId", roleId);
request.setAttribute("edit_user_group_roles.jsp-roleType", roleType);
request.setAttribute("edit_user_group_roles.jsp-organization", organization);

request.setAttribute("edit_user_group_roles.jsp-portletURL", portletURL);
%>

<c:if test="<%= !layout.isTypeControlPanel() %>">
	<liferay-ui:header
		backURL="<%= backURL %>"
		escapeXml="<%= false %>"
		localizeTitle="<%= false %>"
		title="<%= HtmlUtil.escape(groupName) %>"
	/>
</c:if>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />
	<aui:input name="roleId" type="hidden" value="<%= roleId %>" />

	<c:choose>
		<c:when test="<%= role == null %>">
			<liferay-util:include page="/html/portlet/sites_admin/edit_user_group_roles_role.jsp" />
		</c:when>
		<c:otherwise>
			<liferay-util:include page="/html/portlet/sites_admin/edit_user_group_roles_users.jsp" />
		</c:otherwise>
	</c:choose>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />updateUserGroupGroupRoleUsers',
		function(redirect) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "user_group_group_role_users";
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
			document.<portlet:namespace />fm.<portlet:namespace />addUserGroupIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');
			document.<portlet:namespace />fm.<portlet:namespace />removeUserGroupIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/sites_admin/edit_user_group_roles" /></portlet:actionURL>");
		},
		['liferay-util-list-fields']
	);
</aui:script>