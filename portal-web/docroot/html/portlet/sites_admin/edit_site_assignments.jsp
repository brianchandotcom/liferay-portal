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
String tabs1 = ParamUtil.getString(request, "tabs1", "summary");
String tabs2 = ParamUtil.getString(request, "tabs2", "current");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");
boolean showBackURL = ParamUtil.getBoolean(request, "showBackURL", true);

Group group = (Group)request.getAttribute(WebKeys.GROUP);

if (group != null) {
	group = StagingUtil.getLiveGroup(group.getGroupId());
}

User selUser = PortalUtil.getSelectedUser(request, false);

long userGroupId = ParamUtil.getLong(request, "userGroupId");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/sites_admin/edit_site_assignments");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("groupId", String.valueOf(group.getGroupId()));

PortletURL tabsURL = renderResponse.createRenderURL();

tabsURL.setParameter("struts_action", "/sites_admin/edit_site_assignments");
tabsURL.setParameter("tabs1", tabs1);
tabsURL.setParameter("tabs2", "current");
tabsURL.setParameter("redirect", redirect);
tabsURL.setParameter("showBackURL", String.valueOf(showBackURL));

request.setAttribute("edit_site_assignments.jsp-tabs1", tabs1);
request.setAttribute("edit_site_assignments.jsp-tabs2", tabs2);

request.setAttribute("edit_site_assignments.jsp-cur", cur);

request.setAttribute("edit_site_assignments.jsp-redirect", redirect);

request.setAttribute("edit_site_assignments.jsp-group", group);
request.setAttribute("edit_site_assignments.jsp-selUser", selUser);

request.setAttribute("edit_site_assignments.jsp-portletURL", portletURL);
%>

<c:choose>
	<c:when test="<%= (selUser == null) && (userGroupId == 0) %>">
		<c:choose>
			<c:when test='<%= tabs2.equals("available") %>'>
				<liferay-ui:header
					backURL="<%= redirect %>"
					escapeXml="<%= false %>"
					localizeTitle="<%= false %>"
					title='<%= LanguageUtil.get(pageContext, "add-members") + ": " + LanguageUtil.get(pageContext, tabs1) %>'
				/>
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/html/portlet/sites_admin/edit_site_assignments_toolbar.jsp">
					<liferay-util:param name="toolbarItem" value='<%= tabs2.equals("available") ? "add-role" : null %>' />
				</liferay-util:include>
			</c:otherwise>
		</c:choose>

		<c:if test='<%= tabs1.equals("summary") || tabs2.equals("current") %>'>
			<liferay-ui:tabs
				names="summary,users,organizations,user-groups"
				param="tabs1"
				url="<%= tabsURL.toString() %>"
			/>
		</c:if>
	</c:when>
</c:choose>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "submit();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />

	<c:choose>
		<c:when test='<%= tabs1.equals("summary") %>'>
			<aui:input name="keywords" type="hidden" value="" />

			<div class="site-membership-type">
				<liferay-ui:icon
					image="assign"
					label="<%= true %>"
					message='<%= LanguageUtil.get(pageContext, "membership-type") + StringPool.COLON + StringPool.SPACE + LanguageUtil.get(pageContext, GroupConstants.getTypeLabel(group.getType())) %>'
				/>

				<liferay-ui:icon-help message='<%= LanguageUtil.get(pageContext, "membership-type-" + GroupConstants.getTypeLabel(group.getType()) + "-help") %>' />

				<c:if test="<%= group.getType() == GroupConstants.TYPE_SITE_RESTRICTED %>">

					<%
					int pendingRequests = MembershipRequestLocalServiceUtil.searchCount(group.getGroupId(), MembershipRequestConstants.STATUS_PENDING);
					%>

					<c:if test="<%= pendingRequests > 0 %>">
						<portlet:renderURL var="viewMembershipRequestsURL">
							<portlet:param name="struts_action" value="/sites_admin/view_membership_requests" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
						</portlet:renderURL>

						<br />

						<liferay-ui:icon
							image="manage_task"
							label="<%= true %>"
							message='<%= LanguageUtil.format(pageContext, "there-are-x-membership-requests-pending", String.valueOf(pendingRequests), false) %>'
							url="<%= viewMembershipRequestsURL %>"
						/>
					</c:if>
				</c:if>
			</div>

			<liferay-util:include page="/html/portlet/sites_admin/edit_site_assignments_users.jsp" />

			<liferay-util:include page="/html/portlet/sites_admin/edit_site_assignments_organizations.jsp" />

			<liferay-util:include page="/html/portlet/sites_admin/edit_site_assignments_user_groups.jsp" />
		</c:when>
		<c:when test='<%= tabs1.equals("users") %>'>
			<c:choose>
				<c:when test="<%= selUser == null %>">
					<liferay-util:include page="/html/portlet/sites_admin/edit_site_assignments_users.jsp" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/html/portlet/sites_admin/edit_site_assignments_users_roles.jsp" />
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test='<%= tabs1.equals("organizations") %>'>
			<liferay-util:include page="/html/portlet/sites_admin/edit_site_assignments_organizations.jsp" />
		</c:when>
		<c:when test='<%= tabs1.equals("user-groups") %>'>
			<c:choose>
				<c:when test="<%= userGroupId == 0 %>">
					<liferay-util:include page="/html/portlet/sites_admin/edit_site_assignments_user_groups.jsp" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/html/portlet/sites_admin/edit_site_assignments_user_groups_roles.jsp" />
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />submit',
		function() {
			var organizationKeywords = document.<portlet:namespace />fm.<portlet:namespace /><%= DisplayTerms.KEYWORDS %>_organizations;

			if (organizationKeywords && organizationKeywords.value) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= DisplayTerms.KEYWORDS %>.value = organizationKeywords.value;

				submitForm(document.<portlet:namespace />fm, "<portlet:renderURL><portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" /><portlet:param name="tabs1" value="organizations" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" /></portlet:renderURL>");
			}

			var userKeywords = document.<portlet:namespace />fm.<portlet:namespace /><%= DisplayTerms.KEYWORDS %>_users;

			if (userKeywords && userKeywords.value) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= DisplayTerms.KEYWORDS %>.value = userKeywords.value;

				submitForm(document.<portlet:namespace />fm, "<portlet:renderURL><portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" /><portlet:param name="tabs1" value="users" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" /></portlet:renderURL>");
			}

			var userGroupKeywords = document.<portlet:namespace />fm.<portlet:namespace /><%= DisplayTerms.KEYWORDS %>_user_groups;

			if (userGroupKeywords && userGroupKeywords.value) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= DisplayTerms.KEYWORDS %>.value = userGroupKeywords.value;

				submitForm(document.<portlet:namespace />fm, "<portlet:renderURL><portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" /><portlet:param name="tabs1" value="user-groups" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" /></portlet:renderURL>");
			}

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateGroupOrganizations',
		function(assignmentsRedirect) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "group_organizations";
			document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;
			document.<portlet:namespace />fm.<portlet:namespace />addOrganizationIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');
			document.<portlet:namespace />fm.<portlet:namespace />removeOrganizationIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" /></portlet:actionURL>");
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateGroupUserGroups',
		function(assignmentsRedirect) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "group_user_groups";
			document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;
			document.<portlet:namespace />fm.<portlet:namespace />addUserGroupIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');
			document.<portlet:namespace />fm.<portlet:namespace />removeUserGroupIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" /></portlet:actionURL>");
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateGroupUsers',
		function(assignmentsRedirect) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "group_users";
			document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;
			document.<portlet:namespace />fm.<portlet:namespace />addUserIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');
			document.<portlet:namespace />fm.<portlet:namespace />removeUserIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" /></portlet:actionURL>");
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateUserGroupGroupRole',
		function(assignmentsRedirect) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "user_group_group_role";
			document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;
			document.<portlet:namespace />fm.<portlet:namespace />addRoleIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');
			document.<portlet:namespace />fm.<portlet:namespace />removeRoleIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" /></portlet:actionURL>");
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateUserGroupRole',
		function(assignmentsRedirect) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "user_group_role";
			document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;
			document.<portlet:namespace />fm.<portlet:namespace />addRoleIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');
			document.<portlet:namespace />fm.<portlet:namespace />removeRoleIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" /></portlet:actionURL>");
		},
		['liferay-util-list-fields']
	);
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(locale), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "assign-members"), currentURL);
%>