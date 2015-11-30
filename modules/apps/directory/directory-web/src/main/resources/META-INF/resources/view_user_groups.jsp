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
PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
%>

<liferay-ui:search-container
	searchContainer="<%= new UserGroupSearch(renderRequest, portletURL) %>"
>
	<aui:input disabled="<%= true %>" name="userGroupsRedirect" type="hidden" value="<%= portletURL.toString() %>" />

	<liferay-ui:input-search />

	<%
	UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)searchContainer.getSearchTerms();

	LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

	if (portletName.equals(PortletKeys.MY_SITES_DIRECTORY)) {
		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<String, Object>();

		groupParams.put("inherit", Boolean.FALSE);
		groupParams.put("site", Boolean.TRUE);
		groupParams.put("usersGroups", user.getUserId());

		List<Group> groups = GroupLocalServiceUtil.search(user.getCompanyId(), groupParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		userGroupParams.put("userGroupsGroups", SitesUtil.filterGroups(groups, PropsValues.MY_SITES_DIRECTORY_SITE_EXCLUDES));
	}
	else if (portletName.equals(PortletKeys.SITE_MEMBERS_DIRECTORY)) {
		userGroupParams.put("userGroupsGroups", Long.valueOf(themeDisplay.getScopeGroupId()));
	}
	%>

	<liferay-ui:user-group-search-container-results
		searchTerms="<%= searchTerms %>"
		useIndexer="<%= portletName.equals(PortletKeys.DIRECTORY) %>"
		userGroupParams="<%= userGroupParams %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.UserGroup"
		escapedModel="<%= true %>"
		keyProperty="userGroupId"
		modelVar="userGroup"
	>
		<liferay-ui:search-container-column-text
			name="name"
			orderable="<%= true %>"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			name="description"
			orderable="<%= true %>"
			property="description"
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			cssClass="entry-action"
			path="/user_group_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<div class="separator"><!-- --></div>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>