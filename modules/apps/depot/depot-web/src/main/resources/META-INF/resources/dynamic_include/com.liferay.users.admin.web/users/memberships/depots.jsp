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
DepotAdminMembershipsDisplayContext
	depotAdminMembershipsDisplayContext = new DepotAdminMembershipsDisplayContext(liferayPortletRequest, liferayPortletResponse);

User selUser = depotAdminMembershipsDisplayContext.getUser();
List<Group> groups = depotAdminMembershipsDisplayContext.getDepots();

currentURLObj.setParameter("historyKey", renderResponse.getNamespace() + "repositories");
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="repositories"
/>

<liferay-ui:membership-policy-error />

<h3 class="autofit-row sheet-subtitle">
	<span class="autofit-col autofit-col-expand">
		<span class="heading-text"><liferay-ui:message key="repositories" /></span>
	</span>
</h3>

<liferay-util:buffer
	var="removeGroupIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="addGroupIds" type="hidden" />
<aui:input name="deleteGroupIds" type="hidden" />

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	cssClass="lfr-search-container-sites"
	curParam="depotsCur"
	emptyResultsMessage="this-user-does-not-belong-to-a-repository"
	headerNames="name,roles,null"
	iteratorURL="<%= currentURLObj %>"
	total="<%= groups.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= groups.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Group"
		escapedModel="<%= true %>"
		keyProperty="groupId"
		modelVar="group"
		rowIdProperty="friendlyURL"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="name"
		/>

		<%
		List<UserGroupRole> userGroupRoles = new ArrayList<>();
		int userGroupRolesCount = 0;

		if (selUser != null) {
			userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(selUser.getUserId(), group.getGroupId(), 0, PropsValues.USERS_ADMIN_ROLE_COLUMN_LIMIT);
			userGroupRolesCount = UserGroupRoleLocalServiceUtil.getUserGroupRolesCount(selUser.getUserId(), group.getGroupId());
		}
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="roles"
			value="<%= HtmlUtil.escape(UsersAdminUtil.getUserColumnText(locale, userGroupRoles, UsersAdmin.USER_GROUP_ROLE_TITLE_ACCESSOR, userGroupRolesCount)) %>"
		/>

	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>