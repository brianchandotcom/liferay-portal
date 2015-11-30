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
String displayStyle = ParamUtil.getString(request, "displayStyle", "icon");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectUserGroups");
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL viewUserGroupsURL = renderResponse.createRenderURL();

viewUserGroupsURL.setParameter("mvcPath", "/select_user_groups.jsp");
viewUserGroupsURL.setParameter("groupId", String.valueOf(siteMembershipsDisplayContext.getGroupId()));
viewUserGroupsURL.setParameter("eventName", eventName);

UserGroupSiteMembershipChecker rowChecker = new UserGroupSiteMembershipChecker(renderResponse, siteMembershipsDisplayContext.getGroup());

UserGroupSearch userGroupSearch = new UserGroupSearch(renderRequest, PortletURLUtil.clone(viewUserGroupsURL, renderResponse));

UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)userGroupSearch.getSearchTerms();

LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

int userGroupsCount = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams);

userGroupSearch.setTotal(userGroupsCount);

List<UserGroup> userGroups = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams, userGroupSearch.getStart(), userGroupSearch.getEnd(), userGroupSearch.getOrderByComparator());

userGroupSearch.setResults(userGroups);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<c:if test="<%= userGroupsCount > 0 %>">
		<aui:nav-bar-search>
			<aui:form action="<%= viewUserGroupsURL.toString() %>" name="searchFm">
				<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<c:if test="<%= userGroupsCount > 0 %>">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="userGroups"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
				portletURL="<%= PortletURLUtil.clone(viewUserGroupsURL, renderResponse) %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(viewUserGroupsURL, renderResponse) %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns='<%= new String[] {"name", "description"} %>'
				portletURL="<%= PortletURLUtil.clone(viewUserGroupsURL, renderResponse) %>"
			/>
		</liferay-frontend:management-bar-filters>
	</liferay-frontend:management-bar>
</c:if>

<aui:form cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="userGroups"
		rowChecker="<%= rowChecker %>"
		searchContainer="<%= userGroupSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.model.UserGroup"
			escapedModel="<%= true %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>

			<%
			boolean selectUserGroup = true;
			%>

			<%@ include file="/user_group_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	var Util = Liferay.Util;

	var form = AUI.$(document.<portlet:namespace />fm);

	$('input[name="<portlet:namespace />rowIds"]').on(
		'change',
		function(event) {
			var values = {
				data: {
					addUserGroupIds: Util.listCheckedExcept(form, '<portlet:namespace />allRowIds')
				}
			};

			Util.getOpener().Liferay.fire('<%= HtmlUtil.escapeJS(eventName) %>', values);
		}
	);
</aui:script>