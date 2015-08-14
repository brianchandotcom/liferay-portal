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
String tabs2 = ParamUtil.getString(request, "tabs2", "current");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

long userGroupId = ParamUtil.getLong(request, "userGroupId");

UserGroup userGroup = UserGroupServiceUtil.fetchUserGroup(userGroupId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/edit_user_group_assignments.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("userGroupId", String.valueOf(userGroup.getUserGroupId()));
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	title="<%= userGroup.getName() %>"
/>

<liferay-ui:tabs
	names="current,available"
	param="tabs2"
	url="<%= portletURL.toString() %>"
/>

<portlet:actionURL name="editUserGroupAssignments" var="editUserGroupAssignmentsURL" />

<aui:form action="<%= editUserGroupAssignmentsURL %>" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="userGroupId" type="hidden" value="<%= userGroup.getUserGroupId() %>" />
	<aui:input name="addUserIds" type="hidden" />
	<aui:input name="removeUserIds" type="hidden" />

	<liferay-ui:search-container
		rowChecker="<%= new UserUserGroupChecker(renderResponse, userGroup) %>"
		searchContainer="<%= new UserSearch(renderRequest, portletURL) %>"
		var="userSearchContainer"
	>
		<liferay-ui:user-search-form />

		<%
		UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

		LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

		if (filterManageableOrganizations) {
			userParams.put("usersOrgsTree", user.getOrganizations());
		}

		if (tabs2.equals("current")) {
			userParams.put("usersUserGroups", Long.valueOf(userGroup.getUserGroupId()));
		}
		%>

		<liferay-ui:user-search-container-results userParams="<%= userParams %>" />

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.User"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>
			<liferay-ui:search-container-column-text
				name="name"
				property="fullName"
			/>

			<liferay-ui:search-container-column-text
				name="screen-name"
				property="screenName"
			/>
		</liferay-ui:search-container-row>

		<div class="separator"><!-- --></div>

		<%
		String taglibOnClick = renderResponse.getNamespace() + "updateUserGroupUsers('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
		%>

		<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />updateUserGroupUsers(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addUserIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form);
	}
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, userGroup.getName(), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "assign-members"), currentURL);
%>