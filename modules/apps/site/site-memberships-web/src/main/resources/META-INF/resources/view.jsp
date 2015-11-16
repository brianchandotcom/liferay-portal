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
String tabs1 = ParamUtil.getString(request, "tabs1", "summary");
String tabs2 = ParamUtil.getString(request, "tabs2", "current");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());

Group group = GroupLocalServiceUtil.getGroup(groupId);

if (group != null) {
	group = StagingUtil.getLiveGroup(group.getGroupId());
}

User selUser = PortalUtil.getSelectedUser(request, false);

long userGroupId = ParamUtil.getLong(request, "userGroupId");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("groupId", String.valueOf(group.getGroupId()));

PortletURL tabsURL = renderResponse.createRenderURL();

tabsURL.setParameter("mvcPath", "/view.jsp");
tabsURL.setParameter("tabs1", tabs1);
tabsURL.setParameter("tabs2", "current");

request.setAttribute("edit_site_assignments.jsp-tabs1", tabs1);
request.setAttribute("edit_site_assignments.jsp-tabs2", tabs2);

request.setAttribute("edit_site_assignments.jsp-cur", cur);

request.setAttribute("edit_site_assignments.jsp-redirect", redirect);

request.setAttribute("edit_site_assignments.jsp-group", group);
request.setAttribute("edit_site_assignments.jsp-selUser", selUser);

request.setAttribute("edit_site_assignments.jsp-portletURL", portletURL);
%>

<c:if test="<%= (selUser == null) && (userGroupId == 0) %>">
	<c:if test='<%= tabs2.equals("available") %>'>
		<liferay-ui:header
			backURL="<%= redirect %>"
			escapeXml="<%= false %>"
			localizeTitle="<%= false %>"
			title='<%= LanguageUtil.get(request, "add-members") + ": " + LanguageUtil.get(request, tabs1) %>'
		/>
	</c:if>

	<c:if test='<%= tabs1.equals("summary") || tabs2.equals("current") %>'>
		<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
			<aui:nav cssClass="navbar-nav">

				<%
				PortletURL summaryURL = PortletURLUtil.clone(portletURL, renderResponse);

				summaryURL.setParameter("tabs1", "summary");
				%>

				<aui:nav-item href="<%= summaryURL.toString() %>" label="summary" selected='<%= tabs1.equals("summary") %>' />

				<%
				PortletURL usersURL = PortletURLUtil.clone(portletURL, renderResponse);

				usersURL.setParameter("tabs1", "users");
				%>

				<aui:nav-item href="<%= usersURL.toString() %>" label="users" selected='<%= tabs1.equals("users") %>' />

				<%
				PortletURL organizationsURL = PortletURLUtil.clone(portletURL, renderResponse);

				organizationsURL.setParameter("tabs1", "organizations");
				%>

				<aui:nav-item href="<%= organizationsURL.toString() %>" label="organizations" selected='<%= tabs1.equals("organizations") %>' />

				<%
				PortletURL userGroupsURL = PortletURLUtil.clone(portletURL, renderResponse);

				userGroupsURL.setParameter("tabs1", "user-groups");
				%>

				<aui:nav-item href="<%= userGroupsURL.toString() %>" label="user-groups" selected='<%= tabs1.equals("user-groups") %>' />
			</aui:nav>
		</aui:nav-bar>
	</c:if>
</c:if>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "submit();" %>'>
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />

	<c:choose>
		<c:when test='<%= tabs1.equals("summary") %>'>
			<aui:input name="keywords" type="hidden" value="" />

			<div class="site-membership-type">
				<liferay-ui:icon
					iconCssClass="icon-signin"
					label="<%= true %>"
					message='<%= LanguageUtil.get(request, "membership-type") + StringPool.COLON + StringPool.SPACE + LanguageUtil.get(request, GroupConstants.getTypeLabel(group.getType())) %>'
				/>

				<liferay-ui:icon-help message='<%= LanguageUtil.get(request, "membership-type-" + GroupConstants.getTypeLabel(group.getType()) + "-help") %>' />

				<c:if test="<%= group.getType() == GroupConstants.TYPE_SITE_RESTRICTED %>">

					<%
					int pendingRequests = MembershipRequestLocalServiceUtil.searchCount(group.getGroupId(), MembershipRequestConstants.STATUS_PENDING);
					%>

					<c:if test="<%= pendingRequests > 0 %>">
						<br />

						<portlet:renderURL var="viewMembershipRequestsURL">
							<portlet:param name="mvcPath" value="/view_membership_requests.jsp" />
							<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
						</portlet:renderURL>

						<liferay-ui:icon
							iconCssClass="icon-tasks"
							label="<%= true %>"
							message='<%= LanguageUtil.format(request, "there-are-x-membership-requests-pending", String.valueOf(pendingRequests), false) %>'
							url="<%= viewMembershipRequestsURL %>"
						/>
					</c:if>
				</c:if>
			</div>

			<liferay-util:include page="/users.jsp" servletContext="<%= application %>" />

			<liferay-util:include page="/organizations.jsp" servletContext="<%= application %>" />

			<liferay-util:include page="/user_groups.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= tabs1.equals("users") %>'>
			<c:choose>
				<c:when test="<%= selUser == null %>">
					<liferay-util:include page="/users.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/users_roles.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test='<%= tabs1.equals("organizations") %>'>
			<liferay-util:include page="/organizations.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= tabs1.equals("user-groups") %>'>
			<c:choose>
				<c:when test="<%= userGroupId == 0 %>">
					<liferay-util:include page="/user_groups.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/user_groups_roles.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>
</aui:form>

<aui:script>
	function <portlet:namespace />submit() {
		var form = AUI.$(document.<portlet:namespace />fm);

		var keywords;
		var url;

		var organizationKeywordsValue = form.fm('<%= DisplayTerms.KEYWORDS %>_organizations').val();
		var userKeywordsValue = form.fm('<%= DisplayTerms.KEYWORDS %>_users').val();
		var userGroupKeywordsValue = form.fm('<%= DisplayTerms.KEYWORDS %>_user_groups').val();

		if (organizationKeywordsValue) {
			keywords = organizationKeywordsValue;

			url = '<portlet:renderURL><portlet:param name="tabs1" value="organizations" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" /></portlet:renderURL>';
		}
		else if (userKeywordsValue) {
			keywords = userKeywordsValue;

			url = '<portlet:renderURL><portlet:param name="tabs1" value="users" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" /></portlet:renderURL>';
		}
		else if (userGroupKeywordsValue) {
			keywords = userGroupKeywordsValue;

			url = '<portlet:renderURL><portlet:param name="tabs1" value="user-groups" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" /></portlet:renderURL>';
		}

		if (keywords) {
			form.fm('<%= DisplayTerms.KEYWORDS %>').val(keywords);
		}

		submitForm(form, url);
	}

	function <portlet:namespace />updateGroupOrganizations(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addOrganizationIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeOrganizationIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="editGroupOrganizations" />');
	}

	function <portlet:namespace />updateGroupUserGroups(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addUserGroupIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserGroupIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="editGroupUserGroups" />');
	}

	function <portlet:namespace />updateGroupUsers(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addUserIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="editGroupUsers" />');
	}

	function <portlet:namespace />updateUserGroupGroupRole(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addRoleIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeRoleIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="editUserGroupGroupRole" />');
	}

	function <portlet:namespace />updateUserGroupRole(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addRoleIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeRoleIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="editUserGroupRole" />');
	}
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(locale), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "assign-members"), currentURL);
%>