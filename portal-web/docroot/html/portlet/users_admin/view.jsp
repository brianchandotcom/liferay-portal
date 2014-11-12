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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "browse");

String redirect = ParamUtil.getString(request, "redirect");
String viewUsersRedirect = ParamUtil.getString(request, "viewUsersRedirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

int status = ParamUtil.getInteger(request, "status", WorkflowConstants.STATUS_APPROVED);

String usersListView = ParamUtil.get(request, "usersListView", UserConstants.LIST_VIEW_TREE);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/users_admin/view_users");
portletURL.setParameter("usersListView", usersListView);

if (Validator.isNotNull(viewUsersRedirect)) {
	portletURL.setParameter("viewUsersRedirect", viewUsersRedirect);
}

String portletURLString = portletURL.toString();

request.setAttribute("view.jsp-usersListView", usersListView);

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-ui:error exception="<%= CompanyMaxUsersException.class %>" message="unable-to-activate-user-because-that-would-exceed-the-maximum-number-of-users-allowed" />
<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-organizations-that-have-suborganizations-or-users" />
<liferay-ui:error exception="<%= RequiredUserException.class %>" message="you-cannot-delete-or-deactivate-yourself" />

<aui:form action="<%= portletURLString %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "search();" %>'>
	<liferay-portlet:renderURLParams varImpl="portletURL" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="toolbarItem" type="hidden" value="<%= toolbarItem %>" />
	<aui:input name="redirect" type="hidden" value="<%= portletURLString %>" />

	<%
	long organizationGroupId = 0;

	int inactiveUsersCount = 0;
	int usersCount = 0;

	long organizationId = ParamUtil.getLong(request, "organizationId", OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

	Organization organization = null;

	if (organizationId != 0) {
		organization = OrganizationServiceUtil.getOrganization(organizationId);
	}

	if (organization != null) {
		inactiveUsersCount = UserLocalServiceUtil.getOrganizationUsersCount(organizationId, WorkflowConstants.STATUS_INACTIVE);
		usersCount = UserLocalServiceUtil.getOrganizationUsersCount(organizationId, WorkflowConstants.STATUS_APPROVED);
	}
	else {
		LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

		if (!usersListView.equals(UserConstants.LIST_VIEW_FLAT_USERS)) {
			userParams.put("noOrganizations", Boolean.TRUE);
			userParams.put("usersOrgsCount", 0);
		}

		inactiveUsersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), null, WorkflowConstants.STATUS_INACTIVE, userParams);
		usersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED, userParams);
	}
	%>

	<c:choose>
		<c:when test="<%= usersListView.equals(UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS) %>">
			<liferay-util:include page="/html/portlet/users_admin/view_flat_organizations.jsp" />
		</c:when>
		<c:when test="<%= usersListView.equals(UserConstants.LIST_VIEW_FLAT_USERS) %>">

			<%
			boolean organizationContextView = false;
			%>

			<%@ include file="/html/portlet/users_admin/view_flat_users.jspf" %>
		</c:when>
		<c:otherwise>

			<%
			request.setAttribute("view.jsp-backURL", backURL);
			request.setAttribute("view.jsp-inactiveUsersCount", inactiveUsersCount);
			request.setAttribute("view.jsp-organization", organization);
			request.setAttribute("view.jsp-organizationGroupId", organizationGroupId);
			request.setAttribute("view.jsp-organizationId", organizationId);
			request.setAttribute("view.jsp-portletURL", portletURL);
			request.setAttribute("view.jsp-status", status);
			request.setAttribute("view.jsp-toolbarItem", toolbarItem);
			request.setAttribute("view.jsp-usersCount", usersCount);
			request.setAttribute("view.jsp-usersListView", usersListView);
			request.setAttribute("view.jsp-viewUsersRedirect", viewUsersRedirect);
			%>

			<liferay-util:include page="/html/portlet/users_admin/view_tree.jsp" />
		</c:otherwise>
	</c:choose>
</aui:form>

<aui:script>
	function <portlet:namespace />deleteOrganization(organizationId) {
		<portlet:namespace />doDeleteOrganization('<%= Organization.class.getName() %>', organizationId);
	}

	function <portlet:namespace />doDeleteOrganization(className, id) {
		var ids = id;

		var status = <%= WorkflowConstants.STATUS_INACTIVE %>;

		<portlet:namespace />getUsersCount(
			className, ids, status,
			function(event, id, obj) {
				var responseData = this.get('responseData');
				var count = parseInt(responseData);

				if (count > 0) {
					status = <%= WorkflowConstants.STATUS_APPROVED %>;

					<portlet:namespace />getUsersCount(
						className, ids, status,
						function(event, id, obj) {
							responseData = this.get('responseData');
							count = parseInt(responseData);

							if (count > 0) {
								if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
									<portlet:namespace />doDeleteOrganizations(ids);
								}
							}
							else {
								var message = null;

								if (id && (id.toString().split(',').length > 1)) {
									message = '<%= UnicodeLanguageUtil.get(request, "one-or-more-organizations-are-associated-with-deactivated-users.-do-you-want-to-proceed-with-deleting-the-selected-organizations-by-automatically-unassociating-the-deactivated-users") %>';
								}
								else {
									message = '<%= UnicodeLanguageUtil.get(request, "the-selected-organization-is-associated-with-deactivated-users.-do-you-want-to-proceed-with-deleting-the-selected-organization-by-automatically-unassociating-the-deactivated-users") %>';
								}

								if (confirm(message)) {
									<portlet:namespace />doDeleteOrganizations(ids);
								}
							}
						}
					);
				}
				else {
					if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
						<portlet:namespace />doDeleteOrganizations(ids);
					}
				}
			}
		);
	}

	function <portlet:namespace />doDeleteOrganizations(organizationIds) {
		document.<portlet:namespace />fm.method = 'post';
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>';
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = document.<portlet:namespace />fm.<portlet:namespace />organizationsRedirect.value;
		document.<portlet:namespace />fm.<portlet:namespace />deleteOrganizationIds.value = organizationIds;

		submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/users_admin/edit_organization" /></portlet:actionURL>');
	}

	function <portlet:namespace />search() {
		document.<portlet:namespace />fm.method = 'post';
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '';

		submitForm(document.<portlet:namespace />fm, '<%= portletURLString %>');
	}

	function <portlet:namespace />showUsers(status) {

		<%
		PortletURL showUsersURL = renderResponse.createRenderURL();

		showUsersURL.setParameter("struts_action", "/users_admin/view_users");
		showUsersURL.setParameter("usersListView", usersListView);

		long organizationId = ParamUtil.getLong(request, "organizationId", OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

		if (organizationId != OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {
			showUsersURL.setParameter("organizationId", String.valueOf(organizationId));
		}

		if (Validator.isNotNull(viewUsersRedirect)) {
			showUsersURL.setParameter("viewUsersRedirect", viewUsersRedirect);
		}
		%>

		location.href = Liferay.Util.addParams('<portlet:namespace />status=' + status.value, '<%= showUsersURL.toString() %>');
	}

	Liferay.provide(
		window,
		'<portlet:namespace />deleteOrganizations',
		function() {
			<portlet:namespace />doDeleteOrganization(
				'<%= Organization.class.getName() %>',
				Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds', '<portlet:namespace />rowIdsOrganization')
			);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />deleteUsers',
		function(cmd) {
			if (((cmd == '<%= Constants.DEACTIVATE %>') && confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-deactivate-the-selected-users") %>')) || ((cmd == '<%= Constants.DELETE %>') && confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-permanently-delete-the-selected-users") %>')) || (cmd == '<%= Constants.RESTORE %>')) {
				document.<portlet:namespace />fm.method = 'post';
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
				document.<portlet:namespace />fm.<portlet:namespace />redirect.value = document.<portlet:namespace />fm.<portlet:namespace />usersRedirect.value;
				document.<portlet:namespace />fm.<portlet:namespace />deleteUserIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds', '<portlet:namespace />rowIdsUser');

				submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/users_admin/edit_user" /></portlet:actionURL>');
			}
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />getUsersCount',
		function(className, ids, status, callback) {
			var A = AUI();

			A.io.request(
				'<%= themeDisplay.getPathMain() %>/users_admin/get_users_count',
				{
					data: {
						className: className,
						ids: ids,
						status: status
					},
					on: {
						success: callback
					}
				}
			);
		},
		['aui-io']
	);
</aui:script>