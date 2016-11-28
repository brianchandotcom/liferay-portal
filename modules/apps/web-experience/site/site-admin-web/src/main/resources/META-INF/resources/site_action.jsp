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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Group group = null;

boolean organizationUser = false;
boolean userGroupUser = false;

if (row != null) {
	group = (Group)row.getObject();

	organizationUser = GetterUtil.getBoolean(row.getParameter("organizationUser"));
	userGroupUser = GetterUtil.getBoolean(row.getParameter("userGroupUser"));
}
else {
	group = (Group)request.getAttribute("info_panel.jsp-group");

	List<String> organizationNames = SitesUtil.getOrganizationNames(group, user);

	organizationUser = !organizationNames.isEmpty();

	List<String> userGroupNames = SitesUtil.getUserGroupNames(group, user);

	userGroupUser = !userGroupNames.isEmpty();
}

boolean hasUpdatePermission = GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.UPDATE);
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcPath" value="/edit_site.jsp" />
		<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		label="<%= true %>"
		message="edit"
		url="<%= editURL %>"
	/>

	<c:if test="<%= hasUpdatePermission %>">

		<%
		int childSitesCount = siteAdminDisplayContext.getChildSitesCount(group);
		%>

		<c:if test="<%= (childSitesCount > 0) && (row != null) %>">
			<liferay-portlet:renderURL var="viewSubsitesURL">
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon
				message="view-child-sites"
				url="<%= viewSubsitesURL %>"
			/>
		</c:if>

		<c:if test="<%= siteAdminDisplayContext.hasAddChildSitePermission(group) %>">
			<liferay-portlet:renderURL varImpl="addSiteURL">
				<portlet:param name="mvcPath" value="/edit_site.jsp" />
				<portlet:param name="parentGroupSearchContainerPrimaryKeys" value="<%= String.valueOf(group.getGroupId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon
				message="add-child-site"
				method="get"
				url="<%= addSiteURL.toString() %>"
			/>
		</c:if>
	</c:if>

	<c:if test="<%= group.getPublicLayoutsPageCount() > 0 %>">
		<liferay-ui:icon
			message="go-to-public-pages"
			method="get"
			target="_blank"
			url="<%= group.getDisplayURL(themeDisplay, false) %>"
		/>
	</c:if>

	<c:if test="<%= group.getPrivateLayoutsPageCount() > 0 %>">
		<liferay-ui:icon
			message="go-to-private-pages"
			method="get"
			target="_blank"
			url="<%= group.getDisplayURL(themeDisplay, true) %>"
		/>
	</c:if>

	<c:if test="<%= siteAdminDisplayContext.hasEditAssignmentsPermission(group, organizationUser, userGroupUser) %>">
		<portlet:actionURL name="editGroupAssignments" var="leaveURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="removeUserIds" value="<%= String.valueOf(user.getUserId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="leave"
			url="<%= leaveURL %>"
		/>
	</c:if>

	<c:if test="<%= !group.isCompany() && !group.isGuest() && hasUpdatePermission %>">
		<c:choose>
			<c:when test="<%= group.isActive() %>">
				<portlet:actionURL name="deactivate" var="activateURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon-deactivate url="<%= activateURL %>" />
			</c:when>
			<c:otherwise>
				<portlet:actionURL name="activate" var="activateURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					message="activate"
					url="<%= activateURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="<%= siteAdminDisplayContext.hasDeleteGroupPermission(group) %>">
		<portlet:actionURL name="deleteGroups" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>
</liferay-ui:icon-menu>