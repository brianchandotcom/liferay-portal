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
String displayStyle = ParamUtil.getString(request, "displayStyle", "descriptive");

String tabs1 = (String)request.getAttribute("edit_site_assignments.jsp-tabs1");
String tabs2 = (String)request.getAttribute("edit_site_assignments.jsp-tabs2");

String redirect = (String)request.getAttribute("edit_site_assignments.jsp-redirect");

int cur = (Integer)request.getAttribute("edit_site_assignments.jsp-cur");

Group group = (Group)request.getAttribute("edit_site_assignments.jsp-group");

long userGroupId = ParamUtil.getLong(request, "userGroupId");

UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(userGroupId);

PortletURL portletURL = (PortletURL)request.getAttribute("edit_site_assignments.jsp-portletURL");

portletURL.setParameter("userGroupId", String.valueOf(userGroupId));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);
renderResponse.setTitle(LanguageUtil.get(request, "edit-site-roles-for-user-group") + ": " + HtmlUtil.escape(userGroup.getName()));
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="site-roles" selected="<%= true %>" />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" name="searchFm">
			<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	checkBoxContainerId="userGroupGroupRoleRoleSearchContainer"
	includeCheckBox="<%= true %>"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />
	<aui:input name="userGroupId" type="hidden" value="<%= userGroupId %>" />
	<aui:input name="addRoleIds" type="hidden" />
	<aui:input name="removeRoleIds" type="hidden" />

	<%
	PortletURL updateRoleAssignmentsURL = renderResponse.createRenderURL();

	updateRoleAssignmentsURL.setParameter("tabs1", tabs1);
	updateRoleAssignmentsURL.setParameter("tabs2", tabs2);
	updateRoleAssignmentsURL.setParameter("cur", String.valueOf(cur));
	updateRoleAssignmentsURL.setParameter("redirect", redirect);
	updateRoleAssignmentsURL.setParameter("userGroupId", String.valueOf(userGroupId));
	updateRoleAssignmentsURL.setParameter("groupId", String.valueOf(group.getGroupId()));

	String taglibOnClick = renderResponse.getNamespace() + "updateUserGroupGroupRole('" + updateRoleAssignmentsURL.toString() + "');";
	%>

	<aui:button-row cssClass="text-center">
		<aui:button cssClass="btn-lg btn-primary" onClick="<%= taglibOnClick %>" value="update-associations" />
	</aui:button-row>

	<liferay-ui:search-container
		id="userGroupGroupRoleRole"
		rowChecker="<%= new UserGroupGroupRoleRoleChecker(renderResponse, userGroup, group) %>"
		searchContainer="<%= new RoleSearch(renderRequest, portletURL) %>"
	>
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
			cssClass="selected"
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

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />updateUserGroupGroupRole(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addRoleIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeRoleIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="editUserGroupGroupRole" />');
	}
</aui:script>