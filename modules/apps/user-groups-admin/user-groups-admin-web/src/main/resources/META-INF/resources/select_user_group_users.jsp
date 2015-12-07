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

String redirect = ParamUtil.getString(request, "redirect");

long userGroupId = ParamUtil.getLong(request, "userGroupId");

UserGroup userGroup = UserGroupServiceUtil.fetchUserGroup(userGroupId);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(UserGroupsAdminPortletKeys.USER_GROUPS_ADMIN, "users-display-style", "list");
}
else {
	portalPreferences.setValue(UserGroupsAdminPortletKeys.USER_GROUPS_ADMIN, "users-display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectUsers");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/select_user_group_users.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("userGroupId", String.valueOf(userGroup.getUserGroupId()));
portletURL.setParameter("eventName", eventName);

PortletURL searchURL = PortletURLUtil.clone(portletURL, renderResponse);

SearchContainer userSearchContainer = new UserSearch(renderRequest, searchURL);

UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

if (filterManageableOrganizations) {
	userParams.put("usersOrgsTree", user.getOrganizations());
}

RowChecker rowChecker = new SetUserUserGroupChecker(renderResponse, userGroup);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item cssClass="active" label="users" />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" name="searchFm">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<c:if test="<%= UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams) > 0 %>">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="users"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
				portletURL="<%= portletURL %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-sort
				orderByCol="<%= userSearchContainer.getOrderByCol() %>"
				orderByType="<%= userSearchContainer.getOrderByType() %>"
				orderColumns='<%= new String[] {"first-name", "screen-name"} %>'
				portletURL="<%= portletURL %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons />
	</liferay-frontend:management-bar>
</c:if>

<aui:form cssClass="container-fluid-1280" method="post" name="fm">
	<liferay-ui:search-container
		id="users"
		rowChecker="<%= rowChecker %>"
		searchContainer="<%= userSearchContainer %>"
	>
		<liferay-ui:user-search-container-results userParams="<%= userParams %>" />

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.User"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>
			<%@ include file="/user_search_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	var A = AUI();

	var <portlet:namespace />userIds = [];

	$('input[name="<portlet:namespace />rowIds"]').on(
		'change',
		function(event) {
			var target = event.target;

			if (target.checked) {
				<portlet:namespace />userIds.push(target.value);
			}
			else {
				A.Array.removeItem(<portlet:namespace />userIds, target.value);
			}

			var result = {};

			if (<portlet:namespace />userIds.length > 0) {
				result = {
					data: {
						value: <portlet:namespace />userIds.join(',')
					}
				};
			}

			Liferay.Util.getOpener().Liferay.fire('<%= HtmlUtil.escapeJS(eventName) %>', result);
		}
	);
</aui:script>