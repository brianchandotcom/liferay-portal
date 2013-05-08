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

<%@ include file="/html/portlet/user_groups_admin/init.jsp" %>

<%
String viewUserGroupsRedirect = ParamUtil.getString(request, "viewUserGroupsRedirect");
String backURL = ParamUtil.getString(request, "backURL", viewUserGroupsRedirect);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/user_groups_admin/view");

if (Validator.isNotNull(viewUserGroupsRedirect)) {
	portletURL.setParameter("viewUserGroupsRedirect", viewUserGroupsRedirect);
}

pageContext.setAttribute("portletURL", portletURL);

String portletURLString = portletURL.toString();
%>

<liferay-ui:error exception="<%= RequiredUserGroupException.class %>" message="you-cannot-delete-user-groups-that-have-users" />

<aui:form action="<%= portletURLString %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= portletURLString %>" />

	<liferay-ui:header
		title="user-groups"
	/>

	<%@ include file="/html/portlet/user_groups_admin/view_flat_user_groups.jspf" %>

</aui:form>

<aui:script>
	function <portlet:namespace />deleteUserGroup(userGroupId) {
		<portlet:namespace />doDeleteUserGroup('<%= UserGroup.class.getName() %>', userGroupId);
	}

	function <portlet:namespace />doDeleteUserGroup(className, id) {
		var ids = id;

		var status = <%= WorkflowConstants.STATUS_INACTIVE %>

		<portlet:namespace />getUsersCount(
			className, ids, status,
			function(event, id, obj) {
				var responseData = this.get('responseData');
				var count = parseInt(responseData);

				if (count > 0) {
					status = <%= WorkflowConstants.STATUS_APPROVED %>

					<portlet:namespace />getUsersCount(
						className, ids, status,
						function(event, id, obj) {
							responseData = this.get('responseData')
							count = parseInt(responseData);

							if (count > 0) {
								if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this") %>')) {
									<portlet:namespace />doDeleteUserGroups(ids);
								}
							}
							else {
								var message = null;

								if (id && (id.toString().split(",").length > 1)) {
									message = '<%= UnicodeLanguageUtil.get(pageContext, "one-or-more-user-groups-are-associated-with-deactivated-users.-do-you-want-to-proceed-with-deleting-the-selected-user-groups-by-automatically-unassociating-the-deactivated-users") %>';
								}
								else {
									message = '<%= UnicodeLanguageUtil.get(pageContext, "the-selected-user-group-is-associated-with-deactivated-users.-do-you-want-to-proceed-with-deleting-the-selected-user-group-by-automatically-unassociating-the-deactivated-users") %>';
								}

								if (confirm(message)) {
									<portlet:namespace />doDeleteUserGroups(ids);
								}
							}
						}
					);
				}
				else {
					if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this") %>')) {
						<portlet:namespace />doDeleteUserGroups(ids);
					}
				}
			}
		);
	}

	function <portlet:namespace />doDeleteUserGroups(userGroupIds) {
		document.<portlet:namespace />fm.method = "post";
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = document.<portlet:namespace />fm.<portlet:namespace />userGroupsRedirect.value;
		document.<portlet:namespace />fm.<portlet:namespace />deleteUserGroupIds.value = userGroupIds;
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/user_groups_admin/edit_user_group" /></portlet:actionURL>");
	}

	Liferay.provide(
		window,
		'<portlet:namespace />deleteUserGroups',
		function() {
			var userGroupIds = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");

			if (!userGroupIds) {
				return;
			}

			<portlet:namespace />doDeleteUserGroup('<%= UserGroup.class.getName() %>', userGroupIds);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />getUsersCount',
		function(className, ids, status, callback) {
			var A = AUI();

			A.io.request(
				'<%= themeDisplay.getPathMain() %>/user_groups_admin/get_users_count',
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