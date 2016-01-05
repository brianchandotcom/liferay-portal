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

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

long roleId = ParamUtil.getLong(request, "roleId");

Role role = RoleServiceUtil.fetchRole(roleId);

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/edit_role_assignments.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", "current");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("roleId", String.valueOf(role.getRoleId()));
portletURL.setParameter("displayStyle", displayStyle);
portletURL.setParameter("orderByCol", orderByCol);
portletURL.setParameter("orderByType", orderByType);

request.setAttribute("edit_role_assignments.jsp-tabs3", "current");

request.setAttribute("edit_role_assignments.jsp-cur", cur);

request.setAttribute("edit_role_assignments.jsp-role", role);

request.setAttribute("edit_role_assignments.jsp-displayStyle", displayStyle);

request.setAttribute("edit_role_assignments.jsp-portletURL", portletURL);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(role.getTitle(locale));

int type = role.getType();

String rootBreadcrumbKey = null;

if (type == RoleConstants.TYPE_SITE) {
	rootBreadcrumbKey = "site-roles";
}
else if (type == RoleConstants.TYPE_ORGANIZATION) {
	rootBreadcrumbKey = "organization-roles";
}
else {
	rootBreadcrumbKey = "regular-roles";
}

String rootBreadcrumbEntry = LanguageUtil.get(request, rootBreadcrumbKey);

PortalUtil.addPortletBreadcrumbEntry(request, rootBreadcrumbEntry, redirect);
PortalUtil.addPortletBreadcrumbEntry(request, role.getName(), currentURL);
%>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item id="addUsers" title='<%= LanguageUtil.get(request, "add-assignees") %>' url="javascript:;" />
</liferay-frontend:add-menu>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= portletURL.toString() %>" label="assignees" selected="<%= true %>" />
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
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="tabs3" type="hidden" value="current" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="roleId" type="hidden" value="<%= role.getRoleId() %>" />
	<aui:input name="addUserIds" type="hidden" />
	<aui:input name="removeUserIds" type="hidden" />
	<aui:input name="addGroupIds" type="hidden" />
	<aui:input name="removeGroupIds" type="hidden" />

	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="assigneesSearch"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-filters>
				<liferay-frontend:management-bar-navigation
					navigationKeys='<%= new String[] {"users", "sites", "organizations", "user-groups"} %>'
					navigationParam="tabs2"
					portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
				/>

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

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href="javascript:;" iconCssClass="icon-trash" id="unsetRoleAssignments" label="delete" />
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>

	<%
	String portletId = PortletProviderUtil.getPortletId(User.class.getName(), PortletProvider.Action.VIEW);
	%>

	<liferay-ui:breadcrumb
		showLayout="<%= false %>"
		showPortletBreadcrumb="<%= true %>"
	/>

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

<aui:script use="liferay-item-selector-dialog">
	var form = AUI.$(document.<portlet:namespace />fm);

	<portlet:renderURL var="selectAssigneesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/select_assignees.jsp" />
		<portlet:param name="roleId" value="<%= String.valueOf(roleId) %>" />
		<portlet:param name="displayStyle" value="<%= displayStyle %>" />
		<portlet:param name="tabs2" value="<%= tabs2 %>" />
	</portlet:renderURL>

	$('#<portlet:namespace />addUsers').on(
		'click',
		function(event) {
			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<portlet:namespace />selectAssignees',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								if (selectedItem.type === 'users') {
									form.fm('addUserIds').val(selectedItem.value);
								}
								else {
									form.fm('addGroupIds').val(selectedItem.value);
								}

								form.fm('assignmentsRedirect').val('<%= portletURL.toString() %>');

								submitForm(form, '<%= editRoleAssignmentsURL %>');
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= role.getName() %>" key="add-assignees-to-x" />',
					url: '<%= selectAssigneesURL %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

	$('#<portlet:namespace />unsetRoleAssignments').on(
		'click',
		function() {
			var ids = Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds');

			form.fm('assignmentsRedirect').val('<%= portletURL.toString() %>');

			if ('<%= tabs2 %>' === 'users') {
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

assignMembersURL.setParameter("tabs2", tabs2);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, tabs2), assignMembersURL.toString());
%>