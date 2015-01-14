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

<liferay-ui:icon-menu showExpanded="<%= view %>" showWhenSingleIcon="<%= true %>">

	<%
	ThemeDisplay siteThemeDisplay = (ThemeDisplay)themeDisplay.clone();

	siteThemeDisplay.setScopeGroupId(group.getGroupId());

	PortletURL siteAdministrationURL = PortalUtil.getSiteAdministrationURL(renderResponse, siteThemeDisplay);
	%>

	<c:if test="<%= siteAdministrationURL != null %>">
		<liferay-ui:icon
			image="configuration"
			message="site-administration"
			method="get"
			url="<%= siteAdministrationURL.toString() %>"
		/>
	</c:if>

	<c:if test="<%= hasUpdatePermission %>">

		<%
		int childSitesCount = GroupLocalServiceUtil.getGroupsCount(company.getCompanyId(), group.getGroupId(), true);
		%>

		<c:if test="<%= (childSitesCount > 0) && (row != null) %>">
			<liferay-portlet:renderURL var="viewSubsitesURL">
				<portlet:param name="struts_action" value="/sites_admin/view" />
				<portlet:param name="backURL" value="<%= StringPool.SLASH + currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
				<portlet:param name="sitesListView" value="<%= SiteConstants.LIST_VIEW_TREE %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon
				image="view"
				message="view-child-sites"
				url="<%= viewSubsitesURL %>"
			/>
		</c:if>

		<c:if test="<%= !group.isCompany() && (PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_COMMUNITY) || GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ADD_COMMUNITY)) %>">
			<liferay-portlet:renderURL varImpl="addSiteURL">
				<portlet:param name="struts_action" value="/sites_admin/edit_site" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="parentGroupSearchContainerPrimaryKeys" value="<%= String.valueOf(group.getGroupId()) %>" />
				<portlet:param name="showPrototypes" value="<%= Boolean.TRUE.toString() %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon
				image="site_icon"
				message="add-child-site"
				method="get"
				url="<%= addSiteURL.toString() %>"
			/>
		</c:if>
	</c:if>

	<c:if test="<%= group.isCompany() && hasUpdatePermission %>">
		<liferay-portlet:renderURL portletName="<%= PortletKeys.LAYOUTS_ADMIN %>" var="exportURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="struts_action" value="/layouts_admin/export_layouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="rootNodeName" value="<%= group.getDescriptiveName(locale) %>" />
		</liferay-portlet:renderURL>

		<%
		String taglibExportURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "exportDialog', title: '" + HtmlUtil.escapeJS(LanguageUtil.get(pageContext, "export")) + "', uri: '" + HtmlUtil.escapeJS(exportURL.toString()) + "'});";
		%>

		<liferay-ui:icon
			image="export"
			message="export"
			url="<%= taglibExportURL %>"
		/>

		<liferay-portlet:renderURL portletName="<%= PortletKeys.LAYOUTS_ADMIN %>" var="importURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="struts_action" value="/layouts_admin/import_layouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VALIDATE %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="rootNodeName" value="<%= group.getDescriptiveName(locale) %>" />
		</liferay-portlet:renderURL>

		<%
		String taglibImportURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "importDialog', title: '" + HtmlUtil.escapeJS(LanguageUtil.get(pageContext, "import")) + "', uri: '" + HtmlUtil.escapeJS(importURL.toString()) + "'});";
		%>

		<liferay-ui:icon
			image="download"
			message="import"
			url="<%= taglibImportURL %>"
		/>

		<liferay-ui:staging extended="<%= true %>" groupId="<%= group.getGroupId() %>" onlyActions="<%= true %>" showManageBranches="<%= false %>" />
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

	<c:if test="<%= !group.isCompany() && GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.DELETE) && !PortalUtil.isSystemGroup(group.getName()) %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/sites_admin/edit_site" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>
</liferay-ui:icon-menu>