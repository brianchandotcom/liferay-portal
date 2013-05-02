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
	group = (Group)request.getAttribute("view_tree.jspf-site");

	GroupSearchTerms searchTerms = (GroupSearchTerms)request.getAttribute("view_tree.jspf-searchTerms");

	organizationUser = SitesUtil.isOrganizationUser(company.getCompanyId(), group, user, new ArrayList<String>());
	userGroupUser = SitesUtil.isUserGroupUser(company.getCompanyId(), group, user, new ArrayList<String>());
}

boolean hasUpdatePermission = GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.UPDATE);

boolean view = false;

if (row == null) {
	view = true;
}
%>

<liferay-ui:icon-menu showExpanded="<%= view %>" showWhenSingleIcon="<%= view %>">
	<c:if test="<%= hasUpdatePermission %>">
		<liferay-portlet:renderURL doAsGroupId="<%= group.getGroupId() %>" portletName="<%= PortletKeys.SITE_SETTINGS %>" var="editURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			image="edit"
			message="edit-settings"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= group.isCompany() && hasUpdatePermission %>">
		<liferay-ui:staging extended="<%= true %>" groupId="<%= group.getGroupId() %>" onlyActions="<%= true %>" showManageBranches="<%= false %>" />
	</c:if>

	<c:if test="<%= !group.isCompany() && GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.MANAGE_LAYOUTS) %>">
		<liferay-portlet:renderURL doAsGroupId="<%= group.getGroupId() %>" portletName="<%= PortletKeys.GROUP_PAGES %>" var="managePagesURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			image="pages"
			message="manage-pages"
			url="<%= managePagesURL %>"
		/>
	</c:if>

	<c:if test="<%= !group.isCompany() && GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
		<liferay-portlet:renderURL doAsGroupId="<%= group.getGroupId() %>" portletName="<%= PortletKeys.SITE_MEMBERSHIPS_ADMIN %>" var="assignMembersURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			image="assign"
			message="manage-memberships"
			url="<%= assignMembersURL %>"
		/>
	</c:if>

	<c:if test="<%= group.getPublicLayoutsPageCount() > 0 %>">
		<portlet:actionURL var="viewPublicPagesURL">
			<portlet:param name="struts_action" value="/sites_admin/page" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			image="view"
			message="go-to-public-pages"
			target="_blank"
			url="<%= viewPublicPagesURL %>"
		/>
	</c:if>

	<c:if test="<%= group.getPrivateLayoutsPageCount() > 0 %>">
		<portlet:actionURL var="viewPrivatePagesURL">
			<portlet:param name="struts_action" value="/sites_admin/page" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			image="view"
			message="go-to-private-pages"
			target="_blank"
			url="<%= viewPrivatePagesURL %>"
		/>
	</c:if>

	<c:if test="<%= !group.isCompany() && (!(organizationUser || userGroupUser) && ((group.getType() == GroupConstants.TYPE_SITE_OPEN) || (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED)) && GroupLocalServiceUtil.hasUserGroup(user.getUserId(), group.getGroupId())) && !SiteMembershipPolicyUtil.isMembershipRequired(user.getUserId(), group.getGroupId()) %>">
		<portlet:actionURL var="leaveURL">
			<portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" />
			<portlet:param name="<%= Constants.CMD %>" value="group_users" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="removeUserIds" value="<%= String.valueOf(user.getUserId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			image="leave"
			url="<%= leaveURL %>"
		/>
	</c:if>

	<c:if test="<%= !group.isCompany() && hasUpdatePermission %>">
		<portlet:actionURL var="activateURL">
			<portlet:param name="struts_action" value="/sites_admin/edit_site" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= group.isActive() ? Constants.DEACTIVATE : Constants.RESTORE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
		</portlet:actionURL>

		<c:choose>
			<c:when test="<%= group.isActive() %>">
				<liferay-ui:icon-deactivate url="<%= activateURL %>" />
			</c:when>
			<c:otherwise>
				<liferay-ui:icon
					image="activate"
					url="<%= activateURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="<%= !group.isCompany() && GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.DELETE) %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/sites_admin/edit_site" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>

	<c:if test="<%= !group.isCompany() && (PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_COMMUNITY) || GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.MANAGE_SUBGROUPS)) %>">

		<%
		List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
		%>

		<liferay-portlet:renderURL varImpl="addSiteURL">
			<portlet:param name="struts_action" value="/sites_admin/edit_site" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="parentGroupSearchContainerPrimaryKeys" value="<%= String.valueOf(group.getGroupId()) %>" />
		</liferay-portlet:renderURL>

		<%
		addSiteURL.setParameter("showPrototypes", "0");
		%>

		<liferay-ui:icon
			image="site_icon"
			message='<%= LanguageUtil.format(pageContext, "add-x", "blank-site") %>'
			method="get"
			url="<%= addSiteURL.toString() %>"
		/>

		<%
		addSiteURL.setParameter("showPrototypes", "1");

		for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
			addSiteURL.setParameter("layoutSetPrototypeId", String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId()));
		%>

			<liferay-ui:icon
				image="site_icon"
				message='<%= LanguageUtil.format(pageContext, "add-x", HtmlUtil.escape(layoutSetPrototype.getName(locale))) %>'
				method="get"
				url="<%= addSiteURL.toString() %>"
			/>

		<%
		}
		%>

	</c:if>
</liferay-ui:icon-menu>