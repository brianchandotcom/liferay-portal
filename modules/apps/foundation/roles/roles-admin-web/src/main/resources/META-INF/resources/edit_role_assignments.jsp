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
String tabs1 = ParamUtil.getString(request, "tabs1", "users");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

long roleId = ParamUtil.getLong(request, "roleId");

Role role = RoleServiceUtil.fetchRole(roleId);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(RolesAdminPortletKeys.ROLES_ADMIN, "assignees-display-style", "list");
}
else {
	portalPreferences.setValue(RolesAdminPortletKeys.ROLES_ADMIN, "assignees-display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/edit_role_assignments.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", "current");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("roleId", String.valueOf(role.getRoleId()));
portletURL.setParameter("displayStyle", displayStyle);
portletURL.setParameter("orderByCol", orderByCol);
portletURL.setParameter("orderByType", orderByType);

request.setAttribute("edit_role_assignments.jsp-tabs2", "current");

request.setAttribute("edit_role_assignments.jsp-cur", cur);

request.setAttribute("edit_role_assignments.jsp-role", role);

request.setAttribute("edit_role_assignments.jsp-displayStyle", displayStyle);

request.setAttribute("edit_role_assignments.jsp-portletURL", portletURL);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(role.getTitle(locale));

String breadcrumbKey = null;

if (role.getType() == RoleConstants.TYPE_SITE) {
	breadcrumbKey = "site-roles";
}
else if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
	breadcrumbKey = "organization-roles";
}
else {
	breadcrumbKey = "regular-roles";
}

String breadcrumbTitle = LanguageUtil.get(request, breadcrumbKey);

PortalUtil.addPortletBreadcrumbEntry(request, breadcrumbTitle, redirect);

PortalUtil.addPortletBreadcrumbEntry(request, role.getName(), currentURL);
%>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item id="addAssignees" title='<%= LanguageUtil.format(request, "add-x", tabs1) %>' url="javascript:;" />
</liferay-frontend:add-menu>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		PortletURL entityPortletURL = PortletURLUtil.clone(portletURL, renderResponse);

		entityPortletURL.setParameter("tabs1", "users");
		%>

		<aui:nav-item href="<%= entityPortletURL.toString() %>" label="users" selected='<%= tabs1.equals("users") %>' />

		<%
		entityPortletURL.setParameter("tabs1", "sites");
		%>

		<aui:nav-item href="<%= entityPortletURL.toString() %>" label="sites" selected='<%= tabs1.equals("sites") %>' />

		<%
		entityPortletURL.setParameter("tabs1", "organizations");
		%>

		<aui:nav-item href="<%= entityPortletURL.toString() %>" label="organizations" selected='<%= tabs1.equals("organizations") %>' />

		<%
		entityPortletURL.setParameter("tabs1", "user-groups");
		%>

		<aui:nav-item href="<%= entityPortletURL.toString() %>" label="user-groups" selected='<%= tabs1.equals("user-groups") %>' />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" name="searchFm">
			<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="assigneesSearch"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="unsetRoleAssignments" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="editRoleAssignments" var="editRoleAssignmentsURL">
	<portlet:param name="mvcPath" value="/edit_role_assignments.jsp" />
</portlet:actionURL>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="current" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="roleId" type="hidden" value="<%= role.getRoleId() %>" />
	<aui:input name="addUserIds" type="hidden" />
	<aui:input name="removeUserIds" type="hidden" />
	<aui:input name="addGroupIds" type="hidden" />
	<aui:input name="removeGroupIds" type="hidden" />

	<%
	String portletId = PortletProviderUtil.getPortletId(User.class.getName(), PortletProvider.Action.VIEW);
	%>

	<liferay-ui:breadcrumb
		showLayout="<%= false %>"
		showPortletBreadcrumb="<%= true %>"
	/>

	<c:choose>
		<c:when test='<%= tabs1.equals("users") %>'>
			<liferay-util:include page="/edit_role_assignments_users.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= tabs1.equals("sites") %>'>
			<liferay-util:include page="/edit_role_assignments_sites.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= tabs1.equals("organizations") %>'>
			<liferay-util:include page="/edit_role_assignments_organizations.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= tabs1.equals("user-groups") %>'>
			<liferay-util:include page="/edit_role_assignments_user_groups.jsp" servletContext="<%= application %>" />
		</c:when>
	</c:choose>
</aui:form>

<aui:script use="liferay-item-selector-dialog,liferay-portlet-url">
	var form = AUI.$(document.<portlet:namespace />fm);

	<portlet:renderURL var="selectAssigneesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/select_assignees.jsp" />
		<portlet:param name="roleId" value="<%= String.valueOf(roleId) %>" />
		<portlet:param name="displayStyle" value="<%= displayStyle %>" />
		<portlet:param name="tabs1" value="<%= tabs1 %>" />
	</portlet:renderURL>

	AUI.$('#<portlet:namespace />addAssignees').on(
		'click',
		function(event) {
			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<portlet:namespace />selectAssignees',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								var assignmentsRedirect = Liferay.PortletURL.createURL('<%= portletURL.toString() %>');

								if (selectedItem.type === 'users') {
									form.fm('addUserIds').val(selectedItem.value);
								}
								else {
									form.fm('addGroupIds').val(selectedItem.value);
								}

								assignmentsRedirect.setParameter('tabs1', selectedItem.type);

								form.fm('redirect').val(assignmentsRedirect.toString());

								submitForm(form, '<%= editRoleAssignmentsURL %>');
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= HtmlUtil.escape(role.getName()) %>" key="add-assignees-to-x" />',
					url: '<%= selectAssigneesURL %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

	AUI.$('#<portlet:namespace />unsetRoleAssignments').on(
		'click',
		function() {
			var assigneeType = '<%= HtmlUtil.escapeJS(tabs1) %>';
			var ids = Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds');

			form.fm('assignmentsRedirect').val('<%= portletURL.toString() %>');

			if (assigneeType === 'users') {
				form.fm('removeUserIds').val(ids);
			}
			else {
				form.fm('removeGroupIds').val(ids);
			}

			submitForm(form, '<%= editRoleAssignmentsURL %>');
		}
	);
</aui:script>

<%
PortletURL assignMembersURL = renderResponse.createRenderURL();

assignMembersURL.setParameter("mvcPath", "/edit_role_assignments.jsp");
assignMembersURL.setParameter("redirect", redirect);
assignMembersURL.setParameter("roleId", String.valueOf(role.getRoleId()));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "assign-members"), assignMembersURL.toString());

assignMembersURL.setParameter("tabs1", tabs1);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, tabs1), assignMembersURL.toString());
%>